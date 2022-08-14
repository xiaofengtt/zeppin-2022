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
import cn.product.treasuremall.entity.FrontUser.FrontUserStatus;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
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
import cn.product.treasuremall.util.Utlity;

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
    	
    	mobileCode = Base64Util.getFromBase64(mobileCode);
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
		
    	FrontUser fu = this.frontUserDao.get(frontUser);
    	if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户账户状态异常， 请联系管理员！");
			return;
		}
    	if(FrontUserStatus.BLACKLIST.equals(fu.getStatus())){//黑名单校验
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作无效， 请联系管理员！");
			return;
		}
    	if(!FrontUserType.NORMAL.equals(fu.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作无效， 请联系管理员！");
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
			result.setMessage("提现金额错误!");
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
//		fuwo.setOrderChannel(Constants.);
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
		fuwo.setRemark("用户提现");
		
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
			result.setMessage("操作成功！");
//			} else {
//				result.setStatus(ResultStatusType.FAILED);
//				result.setMessage(resultMap.get("message").toString());
//				return;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("网络繁忙，请稍后重试！");
			return;
		}
	}
//	public static void main(String[] args) {
//		
//		BigDecimal dAmount = BigDecimal.valueOf(190);
//		BigDecimal amount = dAmount.divide(BigDecimal.valueOf(1.06),2, BigDecimal.ROUND_DOWN);
////		BigDecimal end = amount.divide(BigDecimal.valueOf(1.06),2, BigDecimal.ROUND_DOWN);
////		BigDecimal mo = amount.subtract(end);
//		
//		BigDecimal scoreRate = BigDecimal.valueOf(2);
//		BigDecimal scoreAmount = amount.multiply(scoreRate).setScale(0, BigDecimal.ROUND_UP);//计算提现额度1元1积分 保留整数位 小数向上取整
//		BigDecimal scoreBalance = BigDecimal.valueOf(0);
//		BigDecimal poundageAmount = amount;
//		if(scoreBalance.compareTo(BigDecimal.ZERO) > 0) {//有积分，免费提现1元1积分
//			if(scoreAmount.compareTo(scoreBalance) > 0) {
//				poundageAmount = amount.subtract(scoreBalance.divide(scoreRate));
//				scoreBalance = BigDecimal.ZERO;
//			} else {
//				poundageAmount = BigDecimal.ZERO;
//				scoreBalance = scoreBalance.subtract(scoreAmount);//免费提现额度（积分）充足，则扣减积分
//			}
//		}
//		BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(0.06));
////		if(sppoundage != null) {
////			poundageRate = BigDecimal.valueOf(Double.valueOf(sppoundage.getParamValue())).setScale(2, BigDecimal.ROUND_DOWN);
////		}
//		//计算提现手续费（法币）
//		BigDecimal poundage = poundageAmount.multiply(poundageRate).setScale(2, BigDecimal.ROUND_UP);
//		
//		//计算提现金额是否正确 扣除金币数=提现金额*汇率*+手续费
//		//需计算免费提现额度--暂不处理
//		BigDecimal rate = BigDecimal.ONE;
//		if(dAmount.compareTo(amount.multiply(rate).add(poundage.multiply(rate))) != 0) {
//			System.out.println(true);
//		}else {
//			System.out.println(false);
//		}
//	}
}
