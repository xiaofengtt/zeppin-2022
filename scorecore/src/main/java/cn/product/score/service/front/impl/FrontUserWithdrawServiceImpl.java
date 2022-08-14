package cn.product.score.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.BankDao;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBankcardDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.MobileCodeDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.entity.Bank;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBankcard;
import cn.product.score.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.MobileCode;
import cn.product.score.entity.MobileCode.MobileCodeStatus;
import cn.product.score.entity.MobileCode.MobileCodeTypes;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;
import cn.product.score.service.front.FrontUserWithdrawService;
import cn.product.score.util.Base64Util;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;

@Service("frontUserWithdrawService")
public class FrontUserWithdrawServiceImpl implements FrontUserWithdrawService{
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
	private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;

	@Override
	public void poundage(InputParams params, DataResult<Object> result) {
		SystemParam sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);
		Map<String,Object> resultMap = JSONUtils.json2map(sp.getParamValue());
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void withdraw(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String bankcard = paramsMap.get("bankcard") == null ? "" : paramsMap.get("bankcard").toString();
    	String mobileCode = paramsMap.get("mobileCode") == null ? "" : paramsMap.get("mobileCode").toString();
    	String price = paramsMap.get("price") == null ? "" : paramsMap.get("price").toString();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		FrontUserBankcard fub = this.frontUserBankcardDao.get(bankcard);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("未绑定该银行卡!");
			return;
		}
		if(!fu.equals(fub.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现银行卡信息错误!");
			return;
		}
		if(!FrontUserBankcardStatus.NORMAL.equals(fub.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现银行卡信息错误!");
			return;
		}
		
		Bank bank = this.bankDao.get(fub.getBank());
		if(bank == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现银行错误!");
			return;
		}
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		price = Base64Util.getFromBase64(price);
		if (!Utlity.isPositiveCurrency(price)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现金额输入错误!");
			return;
		}
		BigDecimal pay = BigDecimal.valueOf(Double.parseDouble(price));
		if(pay.compareTo(Utlity.REAPAL_MAX_WITHDRAW) == 1){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现金额不能高于500w!");
			return;
		}
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("mobile", mobile);
		paramsls.put("status", MobileCodeStatus.NORMAL);
		paramsls.put("type", MobileCodeTypes.FUNDCODE);
		List<MobileCode> lstMobileCode = this.mobileCodeDao.getListByParams(paramsls);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("请先获取验证码");
			return;
		}
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		if(!mobileCode.equals(mc.getCode())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("验证码输入错误！");
			return;
		}
		
		//验证账户余额是否充足
		BigDecimal total = fua.getBalanceFree();
		if(total.compareTo(pay.add(Utlity.WITHDRAW_POUNDAGE)) == -1){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("超出账户余额!");
			return;
		}
		
		BigDecimal poundage = BigDecimal.ZERO;
		SystemParam sp = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);
		Map<String, Object> poundageMap = JSONUtils.json2map(sp.getParamValue());
		for(String key : poundageMap.keySet()){
			String[] keys = key.split("-");
			String value = poundageMap.get(key).toString();
			BigDecimal min = BigDecimal.valueOf(Double.valueOf(keys[0]));
			BigDecimal max = BigDecimal.valueOf(Double.valueOf(keys[1]));
			if(min.compareTo(pay) <= 0 && max.compareTo(pay) >= 0){
				if(value.indexOf("%") > -1){
					String rateStr = value.replace("%", "");
					BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(rateStr)).multiply(BigDecimal.valueOf(0.01));
					poundage = pay.multiply(poundageRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				}else{
					poundage = BigDecimal.valueOf(Double.valueOf(value));
				}
				break;
			}
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fu);
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setIncome(BigDecimal.ZERO);
		fuh.setPay(pay);
		fuh.setPoundage(poundage);
		fuh.setType(FrontUserHistoryType.USER_WITHDRAW);
		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.frontUserHistoryDao.withdraw(fuh);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("提现成功！");
	}

}
