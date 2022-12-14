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
			result.setMessage("??????????????????????????? ?????????????????????");
			return;
		}
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????? ?????????????????????");
			return;
		}
		FrontUserBankcard fub = this.frontUserBankcardDao.get(bankcard);
		if(fub == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????!");
			return;
		}
		if(!frontUser.equals(fub.getFrontUser())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????????????????!");
			return;
		}
		if(!FrontUserBankcardStatus.NORMAL.equals(fub.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????????????????!");
			return;
		}
		
		Bank bank = this.bankDao.get(fub.getBank());
		if(bank == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????!");
			return;
		}
		mobileCode = Base64Util.getFromBase64(mobileCode);
		
		amountStr = Base64Util.getFromBase64(amountStr);
		if (!Utlity.isPositiveCurrency(amountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????!");
			return;
		}
		
		dAmountStr = Base64Util.getFromBase64(dAmountStr);
		if (!Utlity.isPositiveCurrency(dAmountStr)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("???????????????????????????!");
			return;
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
		BigDecimal poundageRate = BigDecimal.valueOf(Double.valueOf(0.003));
		if(sppoundage != null) {
			poundageRate = BigDecimal.valueOf(Double.valueOf(sppoundage.getParamValue())).setScale(2, BigDecimal.ROUND_DOWN);
		}
		BigDecimal dAmount = BigDecimal.valueOf(Double.parseDouble(dAmountStr));//?????????
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));//??????
		//?????????????????????????????????
		BigDecimal poundage = amount.multiply(poundageRate);
		
		//?????????????????????????????? ???????????????=????????????*??????*+?????????
		//???????????????????????????--????????????
		if(dAmount.compareTo(amount.multiply(rate).add(poundage.multiply(rate))) != 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????!");
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
			result.setMessage("?????????????????????");
			return;
		}
		if(!MobileCodeTypes.FUNDCODE.equals(mc.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		if(!mobileCode.equals(mc.getCode())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("????????????????????????");
			return;
		}
		
		//??????????????????????????????
		BigDecimal total = fua.getBalance();
		if(total.compareTo(dAmount) == -1){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????!");
			return;
		}
		
		//??????????????????
		SystemParam spwithdraw = this.systemParamDao.get(SystemParamKey.WITHDRAW_CAPITAL_ACCOUNT);
		if(spwithdraw == null) {
			logger.info("---------------------???????????????????????????-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????!");
			return;
		}
		CapitalAccount ca = this.capitalAccountDao.get(spwithdraw.getParamValue());
		if(ca == null) {
			logger.info("---------------------????????????????????????-----------------------");
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("??????????????????????????????!");
			return;
		}
		//????????????ID
		String channel = UnionPayUtlity.CHANNEL_WITHDRAW_ALI;
		if(!Utlity.checkStringNull(ca.getData())) {
			Map<String, Object> data = JSONUtils.json2map(ca.getData());
			channel = data.get("withdrawChannel").toString();
		}
		
		//??????????????????
		FrontUserWithdrawOrder fuwo = new FrontUserWithdrawOrder();
		fuwo.setUuid(UUID.randomUUID().toString());
		fuwo.setFrontUser(fu.getUuid());
		fuwo.setFrontUserShowId(fu.getShowId());
		fuwo.setOrderNum(Utlity.getOrderNum());
		fuwo.setOrderType(FrontUserWithdrawOrderType.USER_WITHDRAW);
//		fuwo.setOrderChannel(Constants.);
		fuwo.setReduceDAmount(dAmount);
		fuwo.setAmount(amount);
		fuwo.setPoundage(poundage);//??????
		fuwo.setActualAmount(amount.subtract(poundage));
		fuwo.setType(FrontUserWithdrawOrderType.USER_WITHDRAW);
		fuwo.setCapitalAccount(ca.getUuid());
		fuwo.setFrontUserBankcard(fub.getUuid());
		fuwo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		fuwo.setStatus(FrontUserWithdrawOrderStatus.NORMAL);
		fuwo.setTransData(transData);
		fuwo.setRemark("????????????");
		
		
		//????????????
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("bank", bank.getName());
		requestMap.put("bankcard", fub.getAccountNumber());
		requestMap.put("holder", fub.getAccountHolder());
		requestMap.put("channel", channel);
		requestMap.put("title", "????????????");
		requestMap.put("orderNum", fuwo.getOrderNum());
		requestMap.put("totalAmount", amount.multiply(BigDecimal.valueOf(Double.valueOf(100))));
		requestMap.put("passbackParams", fuwo.getUuid());
		
		try {
			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwithdraw(requestMap);
			if((Boolean)resultMap.get("result")) {
				this.frontUserWithdrawOrderDao.withdraw(fuwo);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("???????????????");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("?????????????????????????????????");
			return;
		}
	}
}
