package com.product.worldpay.service.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.ApiResult;
import com.product.worldpay.controller.base.ApiResult.ApiResultStatus;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.CompanyChannelDao;
import com.product.worldpay.dao.CompanyDao;
import com.product.worldpay.dao.UserWithdrawDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.Company;
import com.product.worldpay.entity.Company.CompanyStatus;
import com.product.worldpay.entity.CompanyChannel;
import com.product.worldpay.entity.CompanyChannel.CompanyChannelStatus;
import com.product.worldpay.entity.CompanyChannel.CompanyChannelType;
import com.product.worldpay.entity.UserWithdraw;
import com.product.worldpay.entity.UserWithdraw.UserWithdrawStatus;
import com.product.worldpay.locker.Locker;
import com.product.worldpay.locker.ZkCuratorLocker;
import com.product.worldpay.service.api.ApisWithdrawService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.PaymentUtil;
import com.product.worldpay.util.Utlity;
import com.product.worldpay.util.api.ApiResultUtlity.ApiResultCode;
import com.product.worldpay.util.api.PaymentException;


/**
 * 提现api接口
 */

@Service("apisWithdrawService")
public class ApisWithdrawServiceImpl implements ApisWithdrawService{
	
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private CompanyChannelDao companyChannelDao;
	
	@Autowired
    private Locker locker;
	
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
		String data = paramsMap.get("data").toString();
		String passbackParams = paramsMap.get("passbackParams") == null ? null : paramsMap.get("passbackParams").toString();
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
			if(c == null || !CompanyStatus.NORMAL.equals(c.getStatus())){
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
			if(!c.getUuid().equals(cc.getCompany()) || !CompanyChannelType.WITHDRAW.equals(cc.getType())){
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
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			try{
				dataMap = JSONUtils.json2map(data);
			}catch (Exception e){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_DATA_ERROR);
				return;
			}
			dataMap.put("title", title);
			
			//验签
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("sign", sign);
			signMap.put("company", company);
			signMap.put("channel", channel);
			signMap.put("notifyUrl", notifyUrl);
			signMap.put("timestamp", timestamp);
			signMap.put("amount", amount);
			signMap.put("orderNum", orderNum);
			signMap.put("data", data);
			
			if(!PaymentUtil.checkApisSign(signMap, c.getSystemPublic())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_SIGN_ERROR);
				return;
			}
			
			//订单号
			Map<String, Object>searchMap = new HashMap<>();
			searchMap.put("company", c.getUuid());
			searchMap.put("companyOrderNum", orderNum);
			Integer orderNumCount = this.userWithdrawDao.getCountByParams(searchMap);
			if(orderNumCount > 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_ORDERNUM_REPEAT);
				return;
			}
			
			BigDecimal poundage = BigDecimal.ZERO;
			if(cc.getPoundage() != null){
				poundage = cc.getPoundage();
			}
			if(cc.getPoundageRate() != null){
				poundage = totalAmount.multiply(cc.getPoundageRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			
			UserWithdraw uw = new UserWithdraw();
			uw.setUuid(UUID.randomUUID().toString());
			uw.setCompany(c.getUuid());
			uw.setCompanyChannel(channel);
			uw.setChannel(cc.getChannel());
			uw.setCurrency(cc.getCurrency());
			uw.setOrderNum(Utlity.getOrderNum(Utlity.BILL_ROLE_USER,ch.getCode(),Utlity.BILL_TYPE_WITHDRAW));
			uw.setAmount(totalAmount.subtract(poundage));
			uw.setPoundage(poundage);
			uw.setTotalAmount(totalAmount);
			uw.setTransData(JSONUtils.obj2json(dataMap));
			uw.setCompanyOrderNum(orderNum);
			uw.setCompanyNotifyUrl(notifyUrl);
			uw.setCompanyData(passbackParams);
			uw.setStatus(UserWithdrawStatus.CHECKING);
			uw.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			//企业银行卡
			if(Utlity.CHANNEL_WITHDRAW_BANKCARD_COMPANY.equals(ch.getCode())){
				if(dataMap.get("bank") == null || dataMap.get("bankcard") == null || dataMap.get("holder") == null){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_DATA_ERROR);
					return;
				}
				
				locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
					this.userWithdrawDao.insertWithdraw(uw);
				});
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", uw.getCompanyOrderNum());
				resultData.put("paymentOrderNum", uw.getOrderNum());
				resultData.put("currency", uw.getCurrency());
				resultData.put("amount", uw.getAmount().toString());
				resultData.put("poundage", uw.getPoundage().toString());
				resultData.put("passbackParams", uw.getCompanyData());
				resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
				
				result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CREATE_SUCCESS);
				return;
				
			}else if(Utlity.CHANNEL_WITHDRAW_PAYPAL.equals(ch.getCode())){
				if(dataMap.get("account") == null){
					result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CREATE_DATA_ERROR);
					return;
				}
				
				locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
					this.userWithdrawDao.insertWithdraw(uw);
				});
				resultData.put("company", company);
				resultData.put("channel", channel);
				resultData.put("timestamp", System.currentTimeMillis() + "");
				resultData.put("orderNum", uw.getCompanyOrderNum());
				resultData.put("paymentOrderNum", uw.getOrderNum());
				resultData.put("currency", uw.getCurrency());
				resultData.put("amount", uw.getAmount().toString());
				resultData.put("poundage", uw.getPoundage().toString());
				resultData.put("passbackParams", uw.getCompanyData());
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
		String orderNum = paramsMap.get("orderNum") == "" ? null : paramsMap.get("orderNum").toString();
		String paymentOrderNum = paramsMap.get("paymentOrderNum") == "" ? null : paramsMap.get("paymentOrderNum").toString();
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
			List<UserWithdraw> uwList = this.userWithdrawDao.getListByParams(searchMap);
			if(uwList == null || uwList.size() == 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_QUERY_OREDER_NULL);
				return;
			}
			UserWithdraw uw = uwList.get(0);
			resultData.put("company", company);
			resultData.put("channel", uw.getCompanyChannel());
			resultData.put("timestamp", System.currentTimeMillis() + "");
			resultData.put("orderNum", uw.getCompanyOrderNum());
			resultData.put("paymentOrderNum", uw.getOrderNum());
			resultData.put("currency", uw.getCurrency());
			resultData.put("amount", uw.getTotalAmount().toString());
			resultData.put("passbackParams", uw.getCompanyData());
			resultData.put("failReason", (uw.getFailReason() == null ? "" : uw.getFailReason()));
			resultData.put("status", uw.getStatus());
			
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
		String orderNum = paramsMap.get("orderNum") == "" ? null : paramsMap.get("orderNum").toString();
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
			List<UserWithdraw> uwList = this.userWithdrawDao.getListByParams(searchMap);
			if(uwList == null || uwList.size() == 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_NULL);
				return;
			}
			UserWithdraw uw = uwList.get(0);
			
			if(UserWithdrawStatus.SUCCESS.equals(uw.getStatus()) || UserWithdrawStatus.CHECKED.equals(uw.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_DISABLE);
				return;
			}
			if(UserWithdrawStatus.CLOSE.equals(uw.getStatus())){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_OREDER_ALREADY);
				return;
			}
			
			List<String> errorList = new ArrayList<String>();
			locker.lock(ZkCuratorLocker.ACCOUNT_UPDATE_LOCK, ()-> {
				try {
					this.userWithdrawDao.processOrder(uw, UserWithdrawStatus.CLOSE);
				} catch (PaymentException e) {
					errorList.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if(errorList.size() > 0){
				result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
				return;
			}
			
			resultData.put("company", company);
			resultData.put("channel", uw.getCompanyChannel());
			resultData.put("timestamp", System.currentTimeMillis() + "");
			resultData.put("orderNum", uw.getCompanyOrderNum());
			resultData.put("paymentOrderNum", uw.getOrderNum());
			resultData.put("currency", uw.getCurrency());
			resultData.put("amount", uw.getTotalAmount().toString());
			resultData.put("passbackParams", uw.getCompanyData());
			resultData.put("status", UserWithdrawStatus.CLOSE);
			
			resultData.put("sign", PaymentUtil.getApisSign(resultData, c.getSystemPublic()));
			result.setData(resultData, ApiResultStatus.SUCCESS, ApiResultCode.TRADE_CLOSE_SUCCESS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(resultData, ApiResultStatus.FAIL, ApiResultCode.TRADE_CLOSE_FAIL);
			return;
		}
	}
}