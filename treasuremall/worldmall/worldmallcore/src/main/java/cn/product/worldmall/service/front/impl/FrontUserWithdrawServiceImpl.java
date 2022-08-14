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
    	//20201126新增货币相关参数
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
    	if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//黑名单校验
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
		
		//20200820去掉银行校验
//		Bank bank = this.bankDao.get(fub.getBank());
//		if(bank == null){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("提现银行错误!");
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
    		if(PaypalUtil.SUPPORT_CURRENCIES.indexOf(currency) < 0) {//币种支持校验
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
		
		//获取汇率
		//获取金币汇率
		SystemParam sprate = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sprate != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sprate.getParamValue()));
		}
		//获取手续费率
		SystemParam sppoundage = this.systemParamDao.get(SystemParamKey.WITHDRAW_POUNDAGE);
		BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(0.03));
		if(sppoundage != null) {
			poundageRate = BigDecimal.valueOf(Double.valueOf(sppoundage.getParamValue())).setScale(2, BigDecimal.ROUND_DOWN);
		}
		
		//20200421新增手续费免费提现额度计算
		BigDecimal scoreBalance = fua.getScoreBalance();
		//20200612增加积分消耗按系统设置参数生效
		SystemParam scorerate = this.systemParamDao.get(SystemParamKey.WITHDRAW_SCOREAMOUNT);//提现积分消耗
		BigDecimal scoreRate = BigDecimal.valueOf(2);
		if(scorerate != null) {
			scoreRate = BigDecimal.valueOf(Double.valueOf(scorerate.getParamValue()));
		}
		
		BigDecimal dAmount = BigDecimal.valueOf(Double.parseDouble(dAmountStr));//金币数
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));//钱数
		
		BigDecimal poundageAmount = amount;
//		BigDecimal scoreAmount = amount.multiply(BigDecimal.ONE).setScale(0, BigDecimal.ROUND_UP);//计算提现额度1元1积分 保留整数位 小数向上取整
//		if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {//有积分，免费提现1元1积分
//			if(scoreAmount.compareTo(scoreBalance) > 0) {
//				poundageAmount = amount.subtract(scoreBalance.divide(BigDecimal.ONE));
//				scoreBalance = BigDecimal.ZERO;
//			} else {
//				poundageAmount = BigDecimal.ZERO;
//				scoreBalance = scoreBalance.subtract(scoreAmount);//免费提现额度（积分）充足，则扣减积分
//			}
//		}
		
		BigDecimal scoreAmount = amount.multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//计算提现额度1元1积分 保留整数位 小数向上取整
		if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {//有积分，免费提现1元1积分
			if(scoreAmount.compareTo(scoreBalance) > 0) {
				poundageAmount = amount.subtract(scoreBalance.divide(scoreRate));
				scoreBalance = BigDecimal.ZERO;
			} else {
				poundageAmount = BigDecimal.ZERO;
				scoreBalance = scoreBalance.subtract(scoreAmount);//免费提现额度（积分）充足，则扣减积分
			}
		}
		//计算提现手续费（法币）
		BigDecimal poundage = poundageAmount.multiply(poundageRate).setScale(2, BigDecimal.ROUND_UP);
		
		//计算提现金额是否正确 扣除金币数=提现金额*汇率*+手续费
		//需计算免费提现额度--暂不处理
		if(dAmount.compareTo(amount.multiply(rate).add(poundage.multiply(rate))) != 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Wrong withdrawal amount!");
			return;
		}
		
		//20201126增加本国货币和美元的对等判断
		//计算本国货币，四舍五入保留整数位
//		BigDecimal currentAmount = amount.multiply(BigDecimal.valueOf(Double.valueOf(currencyRate))).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal usAmount = BigDecimal.valueOf(Double.valueOf(currencyAmount)).divide(BigDecimal.valueOf(Double.valueOf(currencyRate)), 2, BigDecimal.ROUND_HALF_UP);
		if(usAmount.compareTo(amount) != 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Withdrawal amount is incorrect!");
			return;
		}
		
		//计算最大可提现额
		BigDecimal maxUsAmount = fua.getBalance().divide(rate);
		BigDecimal maxScoreAmount = fua.getScoreBalance().divide(scoreRate);
		BigDecimal maxOtherAmount = maxUsAmount.subtract(maxScoreAmount).divide(poundageRate.add(BigDecimal.ONE),2, BigDecimal.ROUND_DOWN);
		BigDecimal maxAmount = maxScoreAmount.add(maxOtherAmount).multiply(BigDecimal.valueOf(Double.valueOf(currencyRate))).setScale(2, BigDecimal.ROUND_HALF_UP);
		//判断是否超出最大额度-替换下边的余额验证
		if(maxAmount.compareTo(BigDecimal.valueOf(Double.valueOf(currencyAmount))) == -1) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Account balance exceeded!");
			return;
		}
		
		if(fua.getBalance().compareTo(dAmount) == -1) {
			dAmount = fua.getBalance();
		}
//		//验证账户余额是否充足
//		BigDecimal total = fua.getBalance();
//		if(total.compareTo(dAmount) == -1){
//			result.setStatus(ResultStatusType.FAILED);
//			result.setMessage("Account balance exceeded!");
//			return;
//		}
		
		//获取提现账号
		SystemParam spwithdraw = this.systemParamDao.get(SystemParamKey.WITHDRAW_CAPITAL_ACCOUNT);
		if(spwithdraw == null) {
			logger.info("---------------------未配置提现操作账号-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
		CapitalAccount ca = this.capitalAccountDao.get(spwithdraw.getParamValue());
		if(ca == null) {
			logger.info("---------------------提现操作账号错误-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
//		//获取渠道ID
//		String channel = UnionPayUtlity.CHANNEL_WITHDRAW_ALI;
//		if(!Utlity.checkStringNull(ca.getData())) {
//			Map<String, Object> data = JSONUtils.json2map(ca.getData());
//			channel = data.get("withdrawChannel").toString();
//		}
		
		//封装提现订单
		FrontUserWithdrawOrder fuwo = new FrontUserWithdrawOrder();
		fuwo.setUuid(UUID.randomUUID().toString());
		fuwo.setFrontUser(fu.getUuid());
		fuwo.setFrontUserShowId(fu.getShowId());
		fuwo.setOrderNum(Utlity.getOrderNum());
		fuwo.setOrderType(FrontUserWithdrawOrderType.USER_WITHDRAW);
		fuwo.setReduceDAmount(dAmount);
		fuwo.setAmount(amount.add(poundage));
		fuwo.setPoundage(poundage);//法币
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
		
		//20200518修改提现流程
		/*
		 * 用户提现记录直接入库
		 * 等待管理员审核通过后，向提现接口发起提现
		 * 后续流程不变
		 * 增加用户体现信息展示
		 */
		//发起提现
//		Map<String, Object> requestMap = new HashMap<String, Object>();
//		requestMap.put("bank", bank.getName());
//		requestMap.put("bankcard", fub.getAccountNumber());
//		requestMap.put("holder", fub.getAccountHolder());
//		requestMap.put("channel", channel);
//		requestMap.put("title", "用户提现");
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
