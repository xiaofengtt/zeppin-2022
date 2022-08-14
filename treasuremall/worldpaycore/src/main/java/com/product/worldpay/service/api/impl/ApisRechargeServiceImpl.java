package com.product.worldpay.service.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.ApiResult;
import com.product.worldpay.controller.base.ApiResult.ApiResultStatus;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ChannelAccountDailyDao;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.CompanyChannelAccountDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.dao.UserRechargeDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.ChannelAccount.ChannelAccountStatus;
import com.product.worldpay.entity.ChannelAccountDaily;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyChannel.CompanyChannelStatus;
import com.product.worldpay.entity.CompanyChannel.CompanyChannelType;
import com.product.worldpay.entity.CompanyChannelAccount;
import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.entity.UserRecharge.UserRechargeStatus;
import com.product.worldpay.service.api.ApisRechargeService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.PaymentUtil;
import com.product.worldpay.util.Utlity;
import com.product.worldpay.util.api.ApiResultUtlity.ApiResultCode;
import com.product.worldpay.util.paypal.PaypalAccount;
import com.product.worldpay.util.paypal.PaypalClient;
import com.product.worldpay.util.stripe.StripeUtil;

/**
 * 充值api接口
 */

@Service("apisRechargeService")
public class ApisRechargeServiceImpl  implements ApisRechargeService{
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private CompanyChannelAccountDao companyChannelAccountDao;
	
	@Autowired
	private ChannelAccountDailyDao channelAccountDailyDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	public void createOrder(InputParams params, ApiResult result){
		Map<String, Object> paramsMap = params.getParams();
		String sign = paramsMap.get("sign").toString();
		String company = paramsMap.get("company").toString();
		String channel = paramsMap.get("channel").toString();
		String notifyUrl = paramsMap.get("notifyUrl").toString();
		String timestamp = paramsMap.get("timestamp").toString();
		String title = paramsMap.get("title").toString();
		String amount = paramsMap.get("amount").toString();
		String orderNum = paramsMap.get("orderNum").toString();
		String returnUrl = paramsMap.get("returnUrl") == null ? null : paramsMap.get("returnUrl").toString();
		String passbackParams = paramsMap.get("passbackParams") == null ? null : paramsMap.get("passbackParams").toString();
		String timeout = paramsMap.get("timeout") == null ? null : paramsMap.get("timeout").toString();
		String ip = paramsMap.get("ip") == null ? null : paramsMap.get("ip").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			//Timestamp
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMESTAMP_TIMEOUT);
				return;
			}
			
			//商户
			Company c = this.companyDao.getByCode(company);
			if(c == null){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_COMPANY_ERROR);
				return;
			}
			if(c.getWhiteUrl() != null && !"".equals(c.getWhiteUrl()) && !"*".equals(c.getWhiteUrl())){
				String[] whiteUrls = c.getWhiteUrl().split("/");
				boolean flag = false;
				for(String whiteUrl : whiteUrls){
					if(ip.indexOf(whiteUrl.trim()) > -1){
						flag = true;
					}
				}
				if(!flag){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_WHITEURL_ERROR);
					return;
				}
			}
			
			//渠道
			CompanyChannel cc = this.companyChannelDao.get(channel);
			if(cc == null || CompanyChannelStatus.DELETE.equals(cc.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
				return;
			}
			if(!c.getUuid().equals(cc.getCompany()) || !CompanyChannelType.RECHARGE.equals(cc.getType())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
				return;
			}
			if(!CompanyChannelStatus.NORMAL.equals(cc.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_DISABLE);
				return;
			}
			Channel ch = this.channelDao.get(cc.getChannel());
			if(ch == null || !CompanyChannelStatus.NORMAL.equals(ch.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_DISABLE);
				return;
			}
			//金额
			if(!Utlity.isNumeric(amount)){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_ILLEGAL);
				return;
			}
			BigDecimal totalAmount = new BigDecimal(amount);
			if(totalAmount.compareTo(cc.getMax()) > 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_MORE);
				return;
			}
			if(totalAmount.compareTo(cc.getMin()) < 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_AMOUNT_LESS);
				return;
			}
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("channel", channel);
			signMap.put("notifyUrl", notifyUrl);
			signMap.put("timestamp", timestamp);
			signMap.put("amount", amount);
			signMap.put("orderNum", orderNum);
			
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_SIGN_ERROR);
				return;
			}
			
			
			//订单号
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			searchMap.put("companyOrderNum", orderNum);
			Integer orderNumCount = this.userRechargeDao.getCountByParams(searchMap);
			if(orderNumCount > 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_ORDERNUM_REPEAT);
				return;
			}
			
			ChannelAccount ca = null;
			//渠道账户
			searchMap.clear();
			searchMap.put("companyChannel", cc.getUuid());
			List<CompanyChannelAccount> ccaList = this.companyChannelAccountDao.getListByParams(searchMap);
			if(ccaList.size() > 0){
				searchMap.clear();
				searchMap.put("companyChannel", cc.getUuid());
				searchMap.put("status", ChannelAccountStatus.NORMAL);
				List<ChannelAccount> caList = this.companyChannelAccountDao.getChannelAccountListByParams(searchMap);
				if(caList == null || caList.size() == 0){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
					return;
				}
				
				ca = getRandomAccount(caList, totalAmount);
			}else{
				searchMap.clear();
				searchMap.put("channel", ch.getUuid());
				searchMap.put("status", ChannelAccountStatus.NORMAL);
				List<ChannelAccount> caList = this.channelAccountDao.getListByParams(searchMap);
				if(caList == null || caList.size() == 0){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
					return;
				}
				
				ca = getRandomAccount(caList, totalAmount);
			}
			
			
			if(ca == null){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_EMPTY);
				return;
			}
			
			Long timeoutL;
			//超时时间
			if(timeout == null){
				timeoutL = Utlity.BILL_DEFAULT_TIMEOUT;
			}else{
				if(!Utlity.isNumeric(timeout)){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMEOUT_ILLEGAL);
					return;
				}
				timeoutL = Long.valueOf(timeout);
				if(timeoutL < Utlity.BILL_MIN_TIMEOUT){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_TIMEOUT_LESS);
					return;
				}
			}
			
			Map<String, Object> transDataMap = new HashMap<>();
			transDataMap.put("title", title);
			transDataMap.put("returnUrl", returnUrl);
			
			BigDecimal poundage = BigDecimal.ZERO;
			if(cc.getPoundage() != null){
				poundage = cc.getPoundage();
			}
			if(cc.getPoundageRate() != null){
				poundage = totalAmount.multiply(cc.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			
			UserRecharge ur = new UserRecharge();
			ur.setUuid(UUID.randomUUID().toString());
			ur.setCompany(c.getUuid());
			ur.setCompanyChannel(channel);
			ur.setChannel(cc.getChannel());
			ur.setChannelAccount(ca.getUuid());
			ur.setCurrency(ca.getCurrency());
			ur.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_USER,ch.getCode(),Utlity.BILL_TYPE_RECHARGE));
			ur.setTimeout(new Timestamp(Long.parseLong(timestamp) + timeoutL));
			ur.setTotalAmount(totalAmount);
			ur.setPoundage(poundage);
			ur.setAmount(totalAmount.subtract(poundage));
			ur.setTransData(JSONUtils.obj2json(transDataMap));
			ur.setCompanyOrderNum(orderNum);
			ur.setCompanyNotifyUrl(notifyUrl);
			ur.setCompanyData(passbackParams);
			ur.setStatus(UserRechargeStatus.NORMAL);
			ur.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			if(Utlity.CHANNEL_RECHARGE_PAYPAL.equals(ch.getCode())){
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				PaypalAccount paypalAccount = new PaypalAccount(acParams.get("clientId").toString(), acParams.get("secret").toString(), ca.getTransferUrl());
				
				String transAmount = totalAmount.divide(new BigDecimal(100)).toString();
				Map<String, String> orderInfo = PaypalClient.createOrder(paypalAccount, title, transAmount, ch.getCurrency(), returnUrl);
				if(orderInfo.get("approvalLink") == null || orderInfo.get("payperId") == null){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					return;
				}
				
				transDataMap.put("payperId", orderInfo.get("payperId").toString());
				ur.setTransData(JSONUtils.obj2json(transDataMap));
				
				this.userRechargeDao.insert(ur);
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("currency", ur.getCurrency());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				resultData.put("data", orderInfo.get("approvalLink").toString());
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else if(Utlity.CHANNEL_RECHARGE_STRIPE.equals(ch.getCode())){
				Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
				String session = StripeUtil.createOrder(ur, acParams.get("secret").toString());
				if("".equals(session)){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
					return;
				}
				
				transDataMap.put("session", session);
				ur.setTransData(JSONUtils.obj2json(transDataMap));
				
				this.userRechargeDao.insert(ur);
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", ur.getCompanyOrderNum());
				resultData.put("paymentOrderNum", ur.getOrderNum());
				resultData.put("currency", ur.getCurrency());
				resultData.put("amount", ur.getTotalAmount().toString());
				resultData.put("poundage", ur.getPoundage().toString());
				resultData.put("passbackParams", ur.getCompanyData());
				resultData.put("data", session);
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
			}else{
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_CHANNEL_ERROR);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_FAIL);
			return;
		}
	}
	
	
	public void queryOrder(InputParams params, ApiResult result){
		Map<String, Object> paramsMap = params.getParams();
		String sign = paramsMap.get("sign").toString();
		String company = paramsMap.get("company").toString();
		String timestamp = paramsMap.get("timestamp").toString();
		String orderNum = paramsMap.get("orderNum") == null ? "" : paramsMap.get("orderNum").toString();
		String paymentOrderNum = paramsMap.get("paymentOrderNum") == null ? "" : paramsMap.get("paymentOrderNum").toString();
		String ip = paramsMap.get("ip") == null ? null : paramsMap.get("ip").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			//Timestamp
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 60000){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_TIMESTAMP_TIMEOUT);
				return;
			}
			
			//商户
			Company c = this.companyDao.getByCode(company);
			if(c == null){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_COMPANY_ERROR);
				return;
			}
			if(c.getWhiteUrl() != null && !"".equals(c.getWhiteUrl()) && !"*".equals(c.getWhiteUrl())){
				String[] whiteUrls = c.getWhiteUrl().split("/");
				boolean flag = false;
				for(String whiteUrl : whiteUrls){
					if(ip.indexOf(whiteUrl.trim()) > -1){
						flag = true;
					}
				}
				if(!flag){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_WHITEURL_ERROR);
					return;
				}
			}
			
			//订单号
			if(Utlity.checkStringNull(orderNum) && Utlity.checkStringNull(paymentOrderNum)){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_ORDERNUM_EMPTY);
				return;
			}
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("timestamp", timestamp);
			signMap.put("orderNum", orderNum);
			signMap.put("paymentOrderNum", paymentOrderNum);
			
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_SIGN_ERROR);
				return;
			}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			if(!Utlity.checkStringNull(orderNum)){
				searchMap.put("companyOrderNum", orderNum);
			}
			if(!Utlity.checkStringNull(paymentOrderNum)){
				searchMap.put("orderNum", paymentOrderNum);
			}
			List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
			if(urList == null || urList.size() == 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_OREDER_NULL);
				return;
			}
			UserRecharge ur = urList.get(0);
			resultData.put("company", company);
			resultData.put("channel", ur.getCompanyChannel());
			resultData.put("timestamp", System.currentTimeMillis() + "");
			resultData.put("orderNum", ur.getCompanyOrderNum());
			resultData.put("paymentOrderNum", ur.getOrderNum());
			resultData.put("currency", ur.getCurrency());
			resultData.put("amount", ur.getTotalAmount().toString());
			resultData.put("passbackParams", ur.getCompanyData());
			resultData.put("status", ur.getStatus());
			
			resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
			result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_QUERY_SUCCESS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_FAIL);
			return;
		}
	}
	
	public void closeOrder(InputParams params, ApiResult result){
		Map<String, Object> paramsMap = params.getParams();
		String sign = paramsMap.get("sign").toString();
		String company = paramsMap.get("company").toString();
		String timestamp = paramsMap.get("timestamp").toString();
		String orderNum = paramsMap.get("orderNum") == null ? "" : paramsMap.get("orderNum").toString();
		String paymentOrderNum = paramsMap.get("paymentOrderNum") == "" ? null : paramsMap.get("paymentOrderNum").toString();
		String ip = paramsMap.get("ip") == null ? null : paramsMap.get("ip").toString();
		
		Map<String, String> resultData = new HashMap<String, String>();
		try {
			//Timestamp
			if((System.currentTimeMillis() - Long.parseLong(timestamp)) > 300000){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_TIMESTAMP_TIMEOUT);
				return;
			}
			
			//商户
			Company c = this.companyDao.getByCode(company);
			if(c == null){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_COMPANY_ERROR);
				return;
			}
			if(c.getWhiteUrl() != null && !"".equals(c.getWhiteUrl()) && !"*".equals(c.getWhiteUrl())){
				String[] whiteUrls = c.getWhiteUrl().split("/");
				boolean flag = false;
				for(String whiteUrl : whiteUrls){
					if(ip.indexOf(whiteUrl.trim()) > -1){
						flag = true;
					}
				}
				if(!flag){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_WHITEURL_ERROR);
					return;
				}
			}
			
			//订单号
			if(Utlity.checkStringNull(orderNum) && Utlity.checkStringNull(paymentOrderNum)){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_ORDERNUM_EMPTY);
				return;
			}
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("timestamp", timestamp);
			signMap.put("orderNum", orderNum);
			signMap.put("paymentOrderNum", paymentOrderNum);
			
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_SIGN_ERROR);
				return;
			}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("company", c.getUuid());
			if(!Utlity.checkStringNull(orderNum)){
				searchMap.put("companyOrderNum", orderNum);
			}
			if(!Utlity.checkStringNull(paymentOrderNum)){
				searchMap.put("orderNum", paymentOrderNum);
			}
			List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
			if(urList == null || urList.size() == 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_NULL);
				return;
			}
			UserRecharge ur = urList.get(0);
			
			if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_DISABLE);
				return;
			}
			if(UserRechargeStatus.CLOSE.equals(ur.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_ALREADY);
				return;
			}
			this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.CLOSE);
			
			resultData.put("company", company);
			resultData.put("channel", ur.getCompanyChannel());
			resultData.put("timestamp", System.currentTimeMillis() + "");
			resultData.put("orderNum", ur.getCompanyOrderNum());
			resultData.put("paymentOrderNum", ur.getOrderNum());
			resultData.put("currency", ur.getCurrency());
			resultData.put("amount", ur.getTotalAmount().toString());
			resultData.put("passbackParams", ur.getCompanyData());
			resultData.put("status", UserRechargeStatus.CLOSE);
			
			resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
			result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CLOSE_SUCCESS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
			return;
		}
	}
	
	private ChannelAccount getRandomAccount(List<ChannelAccount> caList, BigDecimal amount){
		if(caList.size() == 0){
			return null;
		}
		
		Random random = new Random();
		Integer randomInt = 0;
		if(caList.size() > 1){
			randomInt = random.nextInt(caList.size() - 1);
		}
		ChannelAccount ca = caList.get(randomInt);
		
		//日额度
		ChannelAccountDaily cad = this.channelAccountDailyDao.get(ca.getUuid());
		if(cad != null){
			if(cad.getAmount().add(amount).compareTo(ca.getDailyMax()) > 0){
				ca.setStatus(ChannelAccountStatus.SUSPEND);
				this.channelAccountDao.update(ca);
				caList.remove(ca);
				return getRandomAccount(caList, amount);
			}
		}
		
		//总额度
		if(ca.getBalance().add(amount).compareTo(ca.getTotalMax()) > 0){
			ca.setStatus(ChannelAccountStatus.DISABLE);
			this.channelAccountDao.update(ca);
			caList.remove(ca);
			return getRandomAccount(caList, amount);
		}
		return ca;
	}
}