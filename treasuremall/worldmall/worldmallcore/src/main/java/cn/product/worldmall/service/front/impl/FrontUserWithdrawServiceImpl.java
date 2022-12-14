package cn.product.worldmall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.BankDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserBankcardDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.MobileCodeDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserBankcard;
import cn.product.worldmall.entity.FrontUserBankcard.FrontUserBankcardStatus;
import cn.product.worldmall.entity.FrontUserWithdrawOrder;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderStatus;
import cn.product.worldmall.entity.FrontUserWithdrawOrder.FrontUserWithdrawOrderType;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.service.front.FrontUserWithdrawService;
import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.paypal.PaypalUtil;

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
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	//20201126????????????????????????
    	String currency = paramsMap.get("currency") == null ? "" : paramsMap.get("currency").toString();
    	String currencyRate = paramsMap.get("currencyRate") == null ? "" : paramsMap.get("currencyRate").toString();
    	String currencyAmount = paramsMap.get("currencyAmount") == null ? "" : paramsMap.get("currencyAmount").toString();
    	
    	if(!Utlity.checkStringNull(transData)) {
    		transData = Base64Util.getFromBase64(transData);
    	}
    	
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
    	if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//???????????????
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Invalid operation, please contact customer service!");
			return;
		}
    	if(!FrontUserType.NORMAL.equals(fu.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Invalid operation, please contact customer service!");
			return;
		}
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		FrontUserBankcard fub = this.frontUserBankcardDao.getPaypal(frontUser);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Unbound paypol!");
			return;
		}
//		if(!frontUser.equals(fub.getFrontUser())){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("Paypol account information is wrong!");
//			return;
//		}
		if(!FrontUserBankcardStatus.NORMAL.equals(fub.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Paypol account information is wrong!");
			return;
		}
		
		//20200820??????????????????
//		Bank bank = this.bankDao.get(fub.getBank());
//		if(bank == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("??????????????????!");
//			return;
//		}
		
		amountStr = Base64Util.getFromBase64(amountStr);
		if (!Utlity.isPositiveCurrency(amountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Wrong withdrawal amount!");
			return;
		}
		
		dAmountStr = Base64Util.getFromBase64(dAmountStr);
		if (!Utlity.isPositiveCurrency(dAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Deductions number of coins input error!");
			return;
		}
		
		if(!Utlity.checkStringNull(currency)) {
    		currency = Base64Util.getFromBase64(currency);
    		if(PaypalUtil.SUPPORT_CURRENCIES.indexOf(currency) < 0) {//??????????????????
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("Unsupported currency!");
    			return;
    		}
    	}
    	if(!Utlity.checkStringNull(currencyRate)) {
    		currencyRate = Base64Util.getFromBase64(currencyRate);
    	}
    	if(!Utlity.checkStringNull(currencyAmount)) {
    		currencyAmount = Base64Util.getFromBase64(currencyAmount);
    	}
		
		//????????????
		//??????????????????
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sprate != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
		}
		//??????????????????
		SystemParam sppoundage = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);
		BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(0.03));
		if(sppoundage != null) {
			poundageRate = BigDecimal.valueOf(Double.valueOf(sppoundage.getParamValue())).setScale(2, BigDecimal.ROUND_DOWN);
		}
		
		//20200421???????????????????????????????????????
		BigDecimal scoreBalance = fua.getScoreBalance();
		//20200612?????????????????????????????????????????????
		SystemParam scorerate = this.systemParamDao.get(SystemParamKey.WITHDRAW_SCOREAMOUNT);//??????????????????
		BigDecimal scoreRate = BigDecimal.valueOf(2);
		if(scorerate != null) {
			scoreRate = BigDecimal.valueOf(Double.valueOf(scorerate.getParamValue()));
		}
		
		BigDecimal dAmount = BigDecimal.valueOf(Double.parseDouble(dAmountStr));//?????????
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));//??????
		
		BigDecimal poundageAmount = amount;
//		BigDecimal scoreAmount = amount.multiply(BigDecimal.ONE).setScale(0, BigDecimal.ROUND_UP);//??????????????????1???1?????? ??????????????? ??????????????????
//		if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {//????????????????????????1???1??????
//			if(scoreAmount.compareTo(scoreBalance) > 0) {
//				poundageAmount = amount.subtract(scoreBalance.divide(BigDecimal.ONE));
//				scoreBalance = BigDecimal.ZERO;
//			} else {
//				poundageAmount = BigDecimal.ZERO;
//				scoreBalance = scoreBalance.subtract(scoreAmount);//??????????????????????????????????????????????????????
//			}
//		}
		
		BigDecimal scoreAmount = amount.multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//??????????????????1???1?????? ??????????????? ??????????????????
		if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {//????????????????????????1???1??????
			if(scoreAmount.compareTo(scoreBalance) > 0) {
				poundageAmount = amount.subtract(scoreBalance.divide(scoreRate));
				scoreBalance = BigDecimal.ZERO;
			} else {
				poundageAmount = BigDecimal.ZERO;
				scoreBalance = scoreBalance.subtract(scoreAmount);//??????????????????????????????????????????????????????
			}
		}
		//?????????????????????????????????
		BigDecimal poundage = poundageAmount.multiply(poundageRate).setScale(2, BigDecimal.ROUND_UP);
		
		//?????????????????????????????? ???????????????=????????????*??????*+?????????
		//???????????????????????????--????????????
		if(dAmount.compareTo(amount.multiply(rate).add(poundage.multiply(rate))) != 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Wrong withdrawal amount!");
			return;
		}
		
		//20201126??????????????????????????????????????????
		//????????????????????????????????????????????????
//		BigDecimal currentAmount = amount.multiply(BigDecimal.valueOf(Double.valueOf(currencyRate))).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal usAmount = BigDecimal.valueOf(Double.valueOf(currencyAmount)).divide(BigDecimal.valueOf(Double.valueOf(currencyRate)), 2, BigDecimal.ROUND_HALF_UP);
		if(usAmount.compareTo(amount) != 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Withdrawal amount is incorrect!");
			return;
		}
		
		//????????????????????????
		BigDecimal maxUsAmount = fua.getBalance().divide(rate);
		BigDecimal maxScoreAmount = fua.getScoreBalance().divide(scoreRate);
		BigDecimal maxOtherAmount = maxUsAmount.subtract(maxScoreAmount).divide(poundageRate.add(BigDecimal.ONE),2, BigDecimal.ROUND_DOWN);
		BigDecimal maxAmount = maxScoreAmount.add(maxOtherAmount).multiply(BigDecimal.valueOf(Double.valueOf(currencyRate))).setScale(2, BigDecimal.ROUND_HALF_UP);
		//??????????????????????????????-???????????????????????????
		if(maxAmount.compareTo(BigDecimal.valueOf(Double.valueOf(currencyAmount))) == -1) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Account balance exceeded!");
			return;
		}
		
		if(fua.getBalance().compareTo(dAmount) == -1) {
			dAmount = fua.getBalance();
		}
//		//??????????????????????????????
//		BigDecimal total = fua.getBalance();
//		if(total.compareTo(dAmount) == -1){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("Account balance exceeded!");
//			return;
//		}
		
		//??????????????????
		SystemParam spwithdraw = this.systemParamDao.get(SystemParamKey.WITHDRAW_CAPITAL_ACCOUNT);
		if(spwithdraw == null) {
			logger.info("---------------------???????????????????????????-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
		CapitalAccount ca = this.capitalAccountDao.get(spwithdraw.getParamValue());
		if(ca == null) {
			logger.info("---------------------????????????????????????-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
//		//????????????ID
//		String channel = UnionPayUtlity.CHANNEL_WITHDRAW_ALI;
//		if(!Utlity.checkStringNull(ca.getData())) {
//			Map<String, Object> data = JSONUtils.json2map(ca.getData());
//			channel = data.get("withdrawChannel").toString();
//		}
		
		//??????????????????
		FrontUserWithdrawOrder fuwo = new FrontUserWithdrawOrder();
		fuwo.setUuid(UUID.randomUUID().toString());
		fuwo.setFrontUser(fu.getUuid());
		fuwo.setFrontUserShowId(fu.getShowId());
		fuwo.setOrderNum(Utlity.getOrderNum());
		fuwo.setOrderType(FrontUserWithdrawOrderType.USER_WITHDRAW);
		fuwo.setReduceDAmount(dAmount);
		fuwo.setAmount(amount.add(poundage));
		fuwo.setPoundage(poundage);//??????
		fuwo.setActualAmount(amount);
		fuwo.setType(FrontUserWithdrawOrderType.USER_WITHDRAW);
		fuwo.setCapitalAccount(ca.getUuid());
		fuwo.setFrontUserBankcard(fub.getUuid());
		fuwo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuwo.setStatus(FrontUserWithdrawOrderStatus.NORMAL);
		fuwo.setTransData(transData);
		fuwo.setRemark("User withdrawal");
		fuwo.setCurrency(currency);
		fuwo.setCurrencyRate(currencyRate);
		fuwo.setCurrencyAmount(currencyAmount);
		
		//20200518??????????????????
		/*
		 * ??????????????????????????????
		 * ????????????????????????????????????????????????????????????
		 * ??????????????????
		 * ??????????????????????????????
		 */
		//????????????
//		Map<String, Object> requestMap = new HashMap<String, Object>();
//		requestMap.put("bank", bank.getName());
//		requestMap.put("bankcard", fub.getAccountNumber());
//		requestMap.put("holder", fub.getAccountHolder());
//		requestMap.put("channel", channel);
//		requestMap.put("title", "????????????");
//		requestMap.put("orderNum", fuwo.getOrderNum());
//		requestMap.put("totalAmount", amount.multiply(BigDecimal.valueOf(Double.valueOf(100))));
//		requestMap.put("passbackParams", fuwo.getUuid());
		
		try {
//			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwithdraw(requestMap);
//			if((Boolean)resultMap.get("result")) {
			this.frontUserWithdrawOrderDao.withdraw(fuwo);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
//			} else {
//				result.setStatus(ResultStatusType.FAILED);
//				result.setMessage(resultMap.get("message").toString());
//				return;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
	}
}
