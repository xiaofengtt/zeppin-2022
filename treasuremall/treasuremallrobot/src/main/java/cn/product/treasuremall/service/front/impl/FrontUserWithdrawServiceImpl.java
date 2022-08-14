package cn.product.treasuremall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.BankDao;
import cn.product.treasuremall.dao.CapitalAccountDao;
import cn.product.treasuremall.dao.CapitalPlatformDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserBankcardDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.dao.FrontUserWithdrawOrderDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.Bank;
import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserBankcard;
import cn.product.treasuremall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.treasuremall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderType;
import cn.product.treasuremall.entity.MobileCode;
import cn.product.treasuremall.entity.MobileCode.MobileCodeStatus;
import cn.product.treasuremall.entity.MobileCode.MobileCodeTypes;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.service.front.FrontUserWithdrawService;
import cn.product.treasuremall.util.Base64Util;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.util.unionpay.UnionPayUtlity;

@Service("frontUserWithdrawService")
public class FrontUserWithdrawServiceImpl implements FrontUserWithdrawService{
	
	public static Logger logger = LoggerFactory.getLogger(FrontUserWithdrawServiceImpl.class);
	
	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
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
	public void withdraw(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String bankcard = paramsMap.get("bankcard") == null ? "" : paramsMap.get("bankcard").toString();
    	String mobileCode = paramsMap.get("mobileCode") == null ? "" : paramsMap.get("mobileCode").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	if(!Utlity.checkStringNull(transData)) {
    		transData = Base64Util.getFromBase64(transData);
    	}
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
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
		if(!frontUser.equals(fub.getFrontUser())){
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
		
		amountStr = Base64Util.getFromBase64(amountStr);
		if (!Utlity.isPositiveCurrency(amountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现金额输入错误!");
			return;
		}
		
		dAmountStr = Base64Util.getFromBase64(dAmountStr);
		if (!Utlity.isPositiveCurrency(dAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("扣减金币数输入错误!");
			return;
		}
		
		//获取汇率
		//获取金币汇率
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sprate != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
		}
		//获取手续费率
		SystemParam sppoundage = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);
		BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(0.003));
		if(sppoundage != null) {
			poundageRate = BigDecimal.valueOf(Double.valueOf(sppoundage.getParamValue())).setScale(2, BigDecimal.ROUND_DOWN);
		}
		BigDecimal dAmount = BigDecimal.valueOf(Double.parseDouble(dAmountStr));//金币数
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));//钱数
		//计算提现手续费（法币）
		BigDecimal poundage = amount.multiply(poundageRate);
		
		//计算提现金额是否正确 扣除金币数=提现金额*汇率*+手续费
		//需计算免费提现额度--暂不处理
		if(dAmount.compareTo(amount.multiply(rate).add(poundage.multiply(rate))) != 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("提现金额错误!");
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
		BigDecimal total = fua.getBalance();
		if(total.compareTo(dAmount) == -1){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("超出账户余额!");
			return;
		}
		
		//获取提现账号
		SystemParam spwithdraw = this.systemParamDao.get(SystemParamKey.WITHDRAW_CAPITAL_ACCOUNT);
		if(spwithdraw == null) {
			logger.info("---------------------未配置提现操作账号-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试!");
			return;
		}
		CapitalAccount ca = this.capitalAccountDao.get(spwithdraw.getParamValue());
		if(ca == null) {
			logger.info("---------------------提现操作账号错误-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试!");
			return;
		}
		//获取渠道ID
		String channel = UnionPayUtlity.CHANNEL_WITHDRAW_ALI;
		if(!Utlity.checkStringNull(ca.getData())) {
			Map<String, Object> data = JSONUtils.json2map(ca.getData());
			channel = data.get("withdrawChannel").toString();
		}
		
		//封装提现订单
		FrontUserWithdrawOrder fuwo = new FrontUserWithdrawOrder();
		fuwo.setUuid(UUID.randomUUID().toString());
		fuwo.setFrontUser(fu.getUuid());
		fuwo.setFrontUserShowId(fu.getShowId());
		fuwo.setOrderNum(Utlity.getOrderNum());
		fuwo.setOrderType(FrontUserWithdrawOrderType.USER_WITHDRAW);
//		fuwo.setOrderChannel(Constants.);
		fuwo.setReduceDAmount(dAmount);
		fuwo.setAmount(amount);
		fuwo.setPoundage(poundage);//法币
		fuwo.setActualAmount(amount.subtract(poundage));
		fuwo.setType(FrontUserWithdrawOrderType.USER_WITHDRAW);
		fuwo.setCapitalAccount(ca.getUuid());
		fuwo.setFrontUserBankcard(fub.getUuid());
		fuwo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuwo.setStatus(FrontUserWithdrawOrderStatus.NORMAL);
		fuwo.setTransData(transData);
		fuwo.setRemark("用户提现");
		
		
		//发起提现
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("bank", bank.getName());
		requestMap.put("bankcard", fub.getAccountNumber());
		requestMap.put("holder", fub.getAccountHolder());
		requestMap.put("channel", channel);
		requestMap.put("title", "用户提现");
		requestMap.put("orderNum", fuwo.getOrderNum());
		requestMap.put("totalAmount", amount.multiply(BigDecimal.valueOf(Double.valueOf(100))));
		requestMap.put("passbackParams", fuwo.getUuid());
		
		try {
			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwithdraw(requestMap);
			if((Boolean)resultMap.get("result")) {
				this.frontUserWithdrawOrderDao.withdraw(fuwo);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试！");
			return;
		}
	}
}
