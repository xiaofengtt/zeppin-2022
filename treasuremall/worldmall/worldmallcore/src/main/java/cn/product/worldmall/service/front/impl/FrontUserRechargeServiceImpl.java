package cn.product.worldmall.service.front.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.ActivityInfoDao;
import cn.product.worldmall.dao.ActivityInfoFirstchargePrizeDao;
import cn.product.worldmall.dao.ActivityInfoRechargePrizeDao;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.CapitalPlatformDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeAmountSetDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoStatus;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize;
import cn.product.worldmall.entity.ActivityInfoFirstchargePrize.ActivityInfoFirstchargePrizeType;
import cn.product.worldmall.entity.ActivityInfoRechargePrize;
import cn.product.worldmall.entity.ActivityInfoRechargePrize.ActivityInfoRechargePrizeType;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalPlatform;
import cn.product.worldmall.entity.CapitalPlatform.CapitalPlatformTransType;
import cn.product.worldmall.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.worldmall.entity.FrontUserRechargeAmountSet;
import cn.product.worldmall.entity.FrontUserRechargeAmountSet.FrontUserRechargeAmountSetStatus;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.worldmall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderType;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.service.front.FrontUserRechargeService;
import cn.product.worldmall.util.Base64Util;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.jinzun.JinzunUtlity;
import cn.product.worldmall.util.paypal.PaypalAccount;
import cn.product.worldmall.util.paypal.PaypalClient;
import cn.product.worldmall.util.paypal.PaypalUtil;
import cn.product.worldmall.util.stripe.StripeUtil;
import cn.product.worldmall.util.unionpay.UnionPayUtlity;
import cn.product.worldmall.util.xingda.AcicpayUtlity;

@Service("frontUserRechargeService")
public class FrontUserRechargeServiceImpl implements FrontUserRechargeService{
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private FrontUserRechargeAmountSetDao frontUserRechargeAmountSetDao;
	
	@Autowired
    private SystemParamDao systemParamDao;
	
	@Autowired
    private ActivityInfoDao activityInfoDao;
	
	@Autowired
    private ActivityInfoFirstchargePrizeDao activityInfoFirstchargePrizeDao;
	
	@Autowired
    private ActivityInfoRechargePrizeDao activityInfoRechargePrizeDao;


	@Override
	public void getAmountSet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserRechargeAmountSetDao.getCountByParams(searchMap);
		
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("Successful!");
		return;
	}

	@Override
	public void byBankcard(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void byAlipay(InputParams params, DataResult<Object> result) {
	}

	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.UNION.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		//??????union????????????-????????????????????? ???????????????form????????????
		Map<String, Object> paramsls = new HashMap<String, Object>();//????????????
		//????????????ID
		String channel = UnionPayUtlity.CHANNEL_RECHARGE_ALI;
		if(!Utlity.checkStringNull(ca.getData())) {
			Map<String, Object> data = JSONUtils.json2map(ca.getData());
			channel = data.get("rechargeChannel").toString();
		}
		paramsls.put("channel", channel);
		paramsls.put("title", "Recharge");
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount.multiply(BigDecimal.valueOf(Double.valueOf(100))));
		paramsls.put("passbackParams", furo.getUuid());
		paramsls.put("information", "");
		paramsls.put("clientIp", ip);
		
		//20200629??????????????????????????????????????????
		if(CapitalPlatformTransType.ALIPAY_BANKCARD.equals(cp.getTransType())) {
			if(Utlity.checkStringNull(transData)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Please fill in the correct payer information!");
				return;
			}
			paramsls.put("holder", transData);
		}
		
		
		if(!Utlity.checkStringNull(returnUrl)) {
			paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		}
		try {
			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwap(paramsls);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("result", true);
			if((Boolean)resultMap.get("result")) {
				this.frontUserRechargeOrderDao.insert(furo);
				result.setData(resultMap.get("response"));
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
	}

	/**
	 * ????????????????????????
	 * ????????????????????????:????????????????????????
	 */
	@Override
	public void byAcicpay(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.ACICPAY.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		
		//?????????????????????????????????????????????????????????????????????
		Map<String, Object> paramsls = new HashMap<String, Object>();//????????????
		//????????????ID
//		String codeNo = AcicpayUtlity.CHANNEL_BANKNO_ALIPAYCODE;
//		if(!Utlity.checkStringNull(ca.getData())) {
//			Map<String, Object> data = JSONUtils.json2map(ca.getData());
//			codeNo = data.get("alipaycode").toString();
//		}
		String codeNo = ca.getAccountNum();
		paramsls.put("codeNo", codeNo);
		paramsls.put("createtime", Utlity.timeSpanToString(furo.getCreatetime()));
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount);
		paramsls.put("orderId", furo.getUuid());
		paramsls.put("name", "Recharge");
		if(!Utlity.checkStringNull(returnUrl)) {
			paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		}
		
		try {
			Map<String, String> resultMap = AcicpayUtlity.createOrderInfo(paramsls);
			this.frontUserRechargeOrderDao.insert(furo);
			result.setData(resultMap);
			result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}		
	}

	/**
	 * Jinzun??????????????????
	 * 
	 */
	@Override
	public void byJinzun(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.JINZUN.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		//?????????????????????????????????????????????????????????????????????
		Map<String, Object> paramsls = new HashMap<String, Object>();//????????????
		
		String codeNo = ca.getAccountNum();
		paramsls.put("codeNo", codeNo);
		paramsls.put("createtime", System.currentTimeMillis() + "");
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount);
		paramsls.put("orderId", furo.getUuid());
		paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		
		try {
			Map<String, Object> resultMap = JinzunUtlity.createOrder(paramsls);
			if((Boolean)resultMap.get("request")){
				this.frontUserRechargeOrderDao.insert(furo);
				result.setData(resultMap);
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
			}else{
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}		
	}
	
	private void  setActivityInfo(FrontUserRechargeOrder furo, String frontUser, String capitalAccount, BigDecimal amount) {
		StringBuilder activity = new StringBuilder();
		Map<String, Object> prize = new HashMap<String, Object>();
		Boolean isActivity = false;
		if(isActivity(frontUser, capitalAccount, ActivityInfoName.FIRSTCHARGE, amount, prize)) {
			activity.append(ActivityInfoName.FIRSTCHARGE);
			isActivity = true;
		}
		if(isActivity(frontUser, capitalAccount, ActivityInfoName.RECHARGE, null, prize)) {
			if(activity.length() > 0) {
				activity.append(",").append(ActivityInfoName.RECHARGE);
			} else {
				activity.append(ActivityInfoName.RECHARGE);
			}
			isActivity = true;
		}
		if(isActivity(frontUser, capitalAccount, ActivityInfoName.RECOMMEND, null, null)) {
			if(activity.length() > 0) {
				activity.append(",").append(ActivityInfoName.RECOMMEND);
			} else {
				activity.append(ActivityInfoName.RECOMMEND);
			}
			isActivity = true;
		}
		furo.setIsActivity(isActivity);
		furo.setActivityId(activity.toString());
		furo.setPrize(JSONUtils.obj2json(prize));
	}
	
	private boolean isActivity(String frontUser, String capitalAccount, String activityId, BigDecimal amount, Map<String, Object> prize){
		boolean isActivity = false;
		ActivityInfo ai = this.activityInfoDao.get(activityId);
		if(ai != null && ActivityInfoStatus.NORMAL.equals(ai.getStatus())) {//????????????
			if(ActivityInfoName.FIRSTCHARGE.equals(activityId)) {//????????????

				boolean isFirst = true;
//				//?????????????????????
//				Map<String, Object> searchMap = new HashMap<String, Object>();
//				searchMap.put("frontUser", frontUser);
//				searchMap.put("status", FrontUserRechargeOrderStatus.CHECKED);
//				Integer totalCount = this.frontUserRechargeOrderDao.getCountByParams(searchMap);
//				if(totalCount != null && totalCount > 0) {
//					isFirst = false;
//				}
				//20200610????????????????????????????????????
				Boolean isPartakeFirstcharge = this.frontUserRechargeOrderDao.isPartakeFirstcharge(frontUser);
				if(isPartakeFirstcharge) {
					isFirst = false;
				}
				List<ActivityInfoFirstchargePrize> list = activityInfoFirstchargePrizeDao.getListByActivityInfo(activityId);
				if(list != null && list.size() > 0) {
					for(ActivityInfoFirstchargePrize aifp : list) {
						if(isFirst && (Utlity.checkStringNull(aifp.getCapitalAccount()) || capitalAccount.equals(aifp.getCapitalAccount())) && amount.compareTo(aifp.getAmount()) == 0) {//????????????
							isActivity = true;
							if(ActivityInfoFirstchargePrizeType.VOUCHER.equals(aifp.getPrizeType())) {
								prize.put(Constants.ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER, aifp.getPrize());
							}
						}
					}
				}
			} else if (ActivityInfoName.RECHARGE.equals(activityId)) {//??????????????????
				List<ActivityInfoRechargePrize> list = activityInfoRechargePrizeDao.getListByActivityInfo(activityId);
				if(list != null && list.size() > 0) {
					for(ActivityInfoRechargePrize airp : list) {
						if(capitalAccount.equals(airp.getCapitalAccount())) {
							isActivity = true;
							if(ActivityInfoRechargePrizeType.VOUCHER.equals(airp.getPrizeType())) {
								prize.put(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_VOUCHER, airp.getPrize());
							} else if(ActivityInfoRechargePrizeType.GOLD.equals(airp.getPrizeType())) {
								prize.put(Constants.ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_GOLD, airp.getPrize());
							}
						}
					}
				}
			} else if (ActivityInfoName.RECOMMEND.equals(activityId)) {//????????????-???????????????????????????
				//????????????
				//??????:??????????????????????????????????????????????????????????????????????????????????????????
				//????????????????????????????????????????????????????????????
				//???????????????????????????????????????????????????????????????????????????
				//????????????????????????????????????????????????????????????????????????????????????????????????
				FrontUser fu = this.frontUserDao.get(frontUser);
				//??????????????????????????????
				if(!Utlity.checkStringNull(fu.getAgent())) {//????????????
					long timeline = 1000*60*60*24*7L;
					if(Utlity.checkOrderTime(fu.getCreatetime().getTime(), System.currentTimeMillis(), timeline)) {//??????7?????????
						if(this.frontUserRechargeOrderDao.isInSevenDayFirstcharge(fu)) {//??????7?????????????????????
							isActivity = true;
						}
					} else {
						isActivity = true;
					}
				}
			}
		}
		
		return isActivity;
	}
	
	private Boolean isHaveRechargeTimes(String frontUser, String capitalAccount, Integer totalTimes) {
		//???????????????????????????????????????????????????
		Integer userReceiveTimes = totalTimes;
		if(totalTimes.intValue() > -1) {
			
			Integer count = this.frontUserRechargeOrderDao.getCountByParams4Days(frontUser, capitalAccount, 0);
			userReceiveTimes = totalTimes.intValue() - count.intValue();
			if(userReceiveTimes > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public void byWorldpay(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.WORLDPAY.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		//??????union????????????-????????????????????? ???????????????form????????????
		Map<String, Object> paramsls = new HashMap<String, Object>();//????????????
		//????????????ID
		String channel = UnionPayUtlity.CHANNEL_RECHARGE_ALI;
		if(!Utlity.checkStringNull(ca.getData())) {
			Map<String, Object> data = JSONUtils.json2map(ca.getData());
			channel = data.get("rechargeChannel").toString();
		}
		paramsls.put("channel", channel);
		paramsls.put("title", "Recharge");
		paramsls.put("orderNum", furo.getOrderNum());
		paramsls.put("totalAmount", amount.multiply(BigDecimal.valueOf(Double.valueOf(100))));
		paramsls.put("passbackParams", furo.getUuid());
		paramsls.put("information", "");
		paramsls.put("clientIp", ip);
		
		//20200629??????????????????????????????????????????
		if(CapitalPlatformTransType.ALIPAY_BANKCARD.equals(cp.getTransType())) {
			if(Utlity.checkStringNull(transData)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Please fill in the correct payer information!");
				return;
			}
			paramsls.put("holder", transData);
		}
		
		
		if(!Utlity.checkStringNull(returnUrl)) {
			paramsls.put("returnUrl", Base64Util.getFromBase64(returnUrl));
		}
		try {
			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwap(paramsls);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("result", true);
			if((Boolean)resultMap.get("result")) {
				this.frontUserRechargeOrderDao.insert(furo);
				result.setData(resultMap.get("response"));
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}
	}

	@Override
	public void byCreditcard(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
//    	Boolean isFree = paramsMap.get("isFree") == null ? false : Boolean.valueOf(paramsMap.get("isFree").toString());
//    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
//    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.CREDIT.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		try {
//			Map<String, Object> resultMap = UnionPayUtlity.createOrder4APIwap(paramsls);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("result", true);
			if((Boolean)resultMap.get("result")) {
				this.frontUserRechargeOrderDao.insert(furo);
				result.setData(resultMap.get("response"));
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("Successful!");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage(resultMap.get("message").toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}		
	}

	@Override
	public void byPaypal(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
//    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	//20201126????????????????????????
    	String currency = paramsMap.get("currency") == null ? "" : paramsMap.get("currency").toString();
    	String currencyRate = paramsMap.get("currencyRate") == null ? "" : paramsMap.get("currencyRate").toString();
    	String currencyAmount = paramsMap.get("currencyAmount") == null ? "" : paramsMap.get("currencyAmount").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
//    	if(!Utlity.checkStringNull(currency)) {
//    		currency = Base64Util.getFromBase64(currency);
//    		if(PaypalUtil.SUPPORT_CURRENCIES.indexOf(currency) < 0) {//??????????????????
//    			result.setStatus(ResultStatusType.FAILED);
//    			result.setMessage("Unsupported currency!");
//    			return;
//    		}
//    	}
    	if(!Utlity.checkStringNull(currencyRate)) {
    		currencyRate = Base64Util.getFromBase64(currencyRate);
    	}
    	if(!Utlity.checkStringNull(currencyAmount)) {
    		currencyAmount = Base64Util.getFromBase64(currencyAmount);
    	}
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		if(!Utlity.checkStringNull(currency)) {
    		currency = Base64Util.getFromBase64(currency);
    		String supportCurrencies = acParams.get("currencies").toString();
    		if(supportCurrencies.indexOf(currency) < 0) {//??????????????????
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("Unsupported currency!");
    			return;
    		}
    		String undc = acParams.get("undc").toString();
    		if(undc.indexOf(currency) > -1) {//?????????????????????????????????
    			currencyAmount = BigDecimal.valueOf(Double.valueOf(currencyAmount)).intValue() + "";
    		}
    	}
		
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.PAYPAL.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		
		//20201126??????????????????????????????????????????
		//????????????????????????????????????????????????
		BigDecimal currentAmount = amount.multiply(BigDecimal.valueOf(Double.valueOf(currencyRate))).setScale(0, BigDecimal.ROUND_HALF_UP);
		if(currentAmount.compareTo(currentAmount) != 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge amount is incorrect!");
			return;
		}
		
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		furo.setCurrency(currency);
		furo.setCurrencyRate(currencyRate);
		furo.setCurrencyAmount(currencyAmount);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		try {
			Map<String, Object> transDataMap = new HashMap<>();
			transDataMap.put("title", "Recharge");
			transDataMap.put("returnUrl", returnUrl);
			
			PaypalAccount paypalAccount = new PaypalAccount(acParams.get("clientId").toString(), acParams.get("secret").toString(), Utlity.TANSFER_URL);
			
			String transAmount = currencyAmount.toString();
			Map<String, String> orderInfo = PaypalClient.createOrder(paypalAccount, "Recharge", transAmount, currency, returnUrl);
			if(orderInfo.get("approvalLink") == null || orderInfo.get("payperId") == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Failed to create recharge order!");
				return;
//				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
//				return;
			}
			
			transDataMap.put("payperId", orderInfo.get("payperId").toString());
			furo.setTransData(JSONUtils.obj2json(transDataMap));
			this.frontUserRechargeOrderDao.insert(furo);
			
			if(CapitalPlatformTransType.CREDIT.equals(ca.getTransType())) {//????????????????????????URL??????
				String link = orderInfo.get("approvalLink").toString();
				if(link.indexOf("?") > -1) {
					link += "&" + PaypalUtil.CREDIT_URLPARAMS;
				} else {
					link += "?" + PaypalUtil.CREDIT_URLPARAMS;
				}
				result.setData(link);
			} else {
				result.setData(orderInfo.get("approvalLink").toString());
			}
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}		
	}

	@Override
	public void byStripe(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String capitalAccount = paramsMap.get("capitalAccount") == null ? "" : paramsMap.get("capitalAccount").toString();
    	String amountStr = paramsMap.get("amount") == null ? "" : paramsMap.get("amount").toString();
    	String dAmountStr = paramsMap.get("dAmount") == null ? "" : paramsMap.get("dAmount").toString();
    	String returnUrl = paramsMap.get("returnUrl") == null ? "" : paramsMap.get("returnUrl").toString();
//    	String ip = paramsMap.get("ip") == null ? "" : paramsMap.get("ip").toString();
    	
    	//20201126????????????????????????
    	String currency = paramsMap.get("currency") == null ? "" : paramsMap.get("currency").toString();
    	String currencyRate = paramsMap.get("currencyRate") == null ? "" : paramsMap.get("currencyRate").toString();
    	String currencyAmount = paramsMap.get("currencyAmount") == null ? "" : paramsMap.get("currencyAmount").toString();
    	
    	amountStr = Base64Util.getFromBase64(amountStr);
    	dAmountStr = Base64Util.getFromBase64(dAmountStr);
    	BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountStr));
    	BigDecimal dAmount = BigDecimal.valueOf(Double.valueOf(dAmountStr));
    	if(amount.compareTo(BigDecimal.ZERO) <= 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Incorrect recharge amount!");
			return;
    	}
    	String transData = paramsMap.get("transData") == null ? "" : paramsMap.get("transData").toString();
    	
    	if(!Utlity.checkStringNull(currencyRate)) {
    		currencyRate = Base64Util.getFromBase64(currencyRate);
    	}
    	if(!Utlity.checkStringNull(currencyAmount)) {
    		currencyAmount = Base64Util.getFromBase64(currencyAmount);
    	}
    	
		CapitalAccount ca = this.capitalAccountDao.get(capitalAccount);
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		boolean flagCent = true;
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		if(!Utlity.checkStringNull(currency)) {
    		currency = Base64Util.getFromBase64(currency);
    		String supportCurrencies = acParams.get("currencies").toString();
    		if(supportCurrencies.indexOf(currency.toLowerCase()) < 0) {//??????????????????
    			result.setStatus(ResultStatusType.FAILED);
    			result.setMessage("Unsupported currency!");
    			return;
    		}
    		
    		String undc = acParams.get("undc").toString();
    		if(undc.indexOf(currency.toLowerCase()) > -1) {//?????????????????????????????????
    			flagCent = false;
    		}
    	}
		//????????????????????????
		Boolean isUse = isHaveRechargeTimes(frontUser, capitalAccount, ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive());
		if(!isUse) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Accounts receivable channels arrive daily limit, please choose another way to recharge!");
			return;
		}
		
		CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
		if(cp == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		if(!CapitalPlatformType.STRIPE.equals(cp.getType())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge channels in error, please choose another way to recharge!");
			return;
		}
		
		//??????????????????
		if(ca.getMin().compareTo(amount) > 0) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Minimum recharge:"+ca.getMin().toString());
			return;
		}
		
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUser);
		if(fua == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		if(!FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User account status abnormality, please contact customer service!");
			return;
		}
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("User status abnormality, please contact customer service!");
			return;
		}
		
		//20201126??????????????????????????????????????????
		//????????????????????????????????????????????????
		BigDecimal currentAmount = amount.multiply(BigDecimal.valueOf(Double.valueOf(currencyRate))).setScale(0, BigDecimal.ROUND_HALF_UP);
		if(currentAmount.compareTo(currentAmount) != 0) {
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Recharge amount is incorrect!");
			return;
		}
		
		//????????????????????????
//		if(isFree) {//??????????????????
		//??????????????????
		SystemParam sp = this.systemParamDao.get(SystemParamKey.GOLD_EXCHANGE_RATE);
		BigDecimal rate = BigDecimal.ONE;
		if(sp != null) {
			rate = BigDecimal.valueOf(Double.valueOf(sp.getParamValue()));
		}
		//?????????????????????????????????????????????????????????
		Map<String, Object> searchMap = new HashMap<String, Object>();
		
		searchMap.put("status", FrontUserRechargeAmountSetStatus.NORMAL);
		searchMap.put("sort", "amount asc");
		List<FrontUserRechargeAmountSet> list = this.frontUserRechargeAmountSetDao.getListByParams(searchMap);
		if(list != null && list.size() > 0) {
			//???????????????????????????
			if(amount.compareTo(list.get(0).getAmount()) < 0) {//?????????????????????
				dAmount = amount.multiply(rate);
			} else if (amount.compareTo(list.get(list.size() - 1).getAmount()) > 0) {//???????????????
				//
				dAmount = amount.multiply(rate).add(list.get(list.size() - 1).getGiveDAmount());
				
			} else {//?????????????????????
				
				for(int i = 0; i < list.size(); i++) {
					FrontUserRechargeAmountSet furas = list.get(i);
					//????????????
					if(amount.compareTo(list.get(i).getAmount()) == 0) {//????????????
						dAmount = furas.getdAmount().add(list.get(i).getGiveDAmount());
						break;
					}
					if(amount.compareTo(list.get(i).getAmount()) > 0 && amount.compareTo(list.get(i + 1).getAmount()) < 0) {
						dAmount = amount.multiply(rate).add(list.get(i).getGiveDAmount());
						break;
					}
				}
			}
		} else {
			dAmount = amount.multiply(rate);
		}
//		}
		
		//???????????????
		Long orderNum = Utlity.getOrderNum();
		
		//??????????????????
		FrontUserRechargeOrder furo = new FrontUserRechargeOrder();
		furo.setUuid(UUID.randomUUID().toString());
		furo.setFrontUser(frontUser);
		furo.setFrontUserShowId(fu.getShowId());
		furo.setOrderNum(orderNum);
		furo.setOrderType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setOrderChannel(ca.getUuid());
		furo.setAmount(amount);
		furo.setIncreaseDAmount(dAmount);
		furo.setType(FrontUserRechargeOrderType.USER_RECHARGE);
		furo.setCapitalAccount(ca.getUuid());
		furo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		furo.setStatus(FrontUserRechargeOrderStatus.NORMAL);
		furo.setTransData(transData);
		furo.setRemark("Users recharge Remarks");
		furo.setIsFirsttime(false);
		furo.setCurrency(currency);
		furo.setCurrencyRate(currencyRate);
		furo.setCurrencyAmount(currencyAmount);
		
		//20200603?????????????????????????????????????????????
		setActivityInfo(furo, frontUser, capitalAccount, amount);
		
		try {
			Map<String, Object> transDataMap = new HashMap<>();
			transDataMap.put("title", "Recharge");
			transDataMap.put("returnUrl", returnUrl);
			furo.setTransData(JSONUtils.obj2json(transDataMap));
			
			//??????Stripe???createOrder??????????????????sessionId
//			Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
			String session = StripeUtil.createOrder(furo, flagCent, acParams.get("secret").toString(), PaymentMethodType.CARD);
			if("".equals(session)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("Failed to create recharge order!");
				return;
			}
			
			transDataMap.put("sessionUuid", session);
			furo.setTransData(JSONUtils.obj2json(transDataMap));
			
			this.frontUserRechargeOrderDao.insert(furo);

			result.setData(Utlity.TANSFER_URL+StripeUtil.CHANNEL_RECHARGE_STRIPE_PATH+"?orderid=" + furo.getUuid());
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("Successful!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Network busy, please try again later!");
			return;
		}		
	}
	public static void main(String[] args) {
		String c = "TVlS";
		System.out.println(Base64Util.getFromBase64(c));
		
		Currency cu = Currency.getInstance("EUR");
		System.out.println(cu.getSymbol());
		System.out.println(cu.getCurrencyCode());
		System.out.println(cu.getDefaultFractionDigits());
		System.out.println(cu.getNumericCode());
		
		String currencyAmount = "103.00";
		currencyAmount = BigDecimal.valueOf(Double.valueOf(currencyAmount)).intValue() + "";
		System.out.println(currencyAmount);
	}
}
