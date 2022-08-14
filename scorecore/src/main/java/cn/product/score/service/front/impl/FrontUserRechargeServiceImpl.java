package cn.product.score.service.front.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.service.front.FrontUserRechargeService;
import cn.product.score.util.Utlity;
import cn.product.score.util.alipay.AliUtlity;

@Service("frontUserRechargeService")
public class FrontUserRechargeServiceImpl implements FrontUserRechargeService{
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;

	@Override
	public void byBankcard(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不存在！");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户有误，请选择其他充值方式！");
			return;
		}
		
		if(!CapitalPlatformType.COMPANY_BANKCARD.equals(cp.getType()) && !CapitalPlatformType.PERSONAL_BANKCARD.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道有误，请选择其他充值方式！");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fu);
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setIncome(fee);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setPoundage(fee.multiply(ca.getPoundageRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		fuh.setTransData(transData);
		fuh.setCapitalAccount(capitalAccount);
		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.frontUserHistoryDao.recharge(fuh, cp.getType());
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}

	@Override
	public void byAlipay(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	BigDecimal fee = paramsMap.get("fee") == null ? null : BigDecimal.valueOf(Double.valueOf(paramsMap.get("fee").toString()));
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户不存在！");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("渠道账户有误，请选择其他充值方式！");
			return;
		}
		
		if(!CapitalPlatformType.COMPANY_ALIPAY.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("充值渠道有误，请选择其他充值方式！");
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.getByFrontUser(fu);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		
		//生成订单号
		String orderNum = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_APP,Utlity.getOrderTypeByPlatformType(cp.getType()),Utlity.BILL_PURPOSE_RECHARGE);
		
		FrontUserHistory fuh = new FrontUserHistory();
		fuh.setUuid(UUID.randomUUID().toString());
		fuh.setFrontUser(fu);
		fuh.setFrontUserAccount(fua.getUuid());
		fuh.setIncome(fee);
		fuh.setPay(BigDecimal.ZERO);
		fuh.setPoundage(fee.multiply(ca.getPoundageRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
		fuh.setType(FrontUserHistoryType.USER_RECHARGE);
		fuh.setTransData(transData);
		fuh.setCapitalAccount(capitalAccount);
		fuh.setStatus(FrontUserHistoryStatus.NORMAL);
		fuh.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuh.setOrderNum(orderNum);
		
		//调用支付宝wap支付接口 返回form表单数据
		Map<String, Object> paramsls = new HashMap<String, Object>();//封装参数
		try {
			paramsls.put("passback_params", URLEncoder.encode(fuh.getUuid(), "UTF-8"));
			paramsls.put("time_expire", Utlity.timeSpanToStringLess(new Timestamp(fuh.getCreatetime().getTime()+5*60*1000)));//订单超时时间5分钟
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paramsls.put("out_trade_no", fuh.getOrderNum());
		paramsls.put("total_amount", fee);
		
		Map<String, Object> resultMap = AliUtlity.createOrder4wap(paramsls);
		if ((boolean)resultMap.get("request")) {
			if((boolean)resultMap.get("result")) {
				this.frontUserHistoryDao.recharge(fuh, cp.getType());
				result.setData(resultMap.get("response"));
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作失败！");
			return;
		}
	}

}
