package com.product.worldpay.service.support.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.UserRechargeDao;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.entity.UserRecharge.UserRechargeStatus;
import com.product.worldpay.service.support.SupportPaypalService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.paypal.PaypalAccount;
import com.product.worldpay.util.paypal.PaypalClient;

@Service("supportPaypalService")
public class SupportPaypalServiceImpl implements SupportPaypalService{
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Override
	public void execute(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String paymentId = paramsMap.get("paymentId") == null ? null : paramsMap.get("paymentId").toString();
		String payerId = paramsMap.get("payerId") == null ? null : paramsMap.get("payerId").toString();
		
		if(paymentId == null || payerId == null){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("channel", "1075f929-5f6d-4d54-a64a-f728e0c92d04");
		searchMap.put("transData", paymentId);
		List<UserRecharge> urList = this.userRechargeDao.getListByParams(searchMap);
		if(urList.size() != 1){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		UserRecharge ur = this.userRechargeDao.get(urList.get(0).getUuid());
		Map<String, Object> transData = JSONUtils.json2map(ur.getTransData());
		result.setData(transData.get("returnUrl").toString());
		
		if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		ChannelAccount ca = this.channelAccountDao.get(ur.getChannelAccount());
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		PaypalAccount paypalAccount = new PaypalAccount(acParams.get("clientId").toString(), acParams.get("secret").toString(), ca.getTransferUrl());
		
		try {
			HttpResponse<Order> response = PaypalClient.excuteOrder(paypalAccount, paymentId, payerId);
			System.out.print(response);
			if("COMPLETED".equals(response.result().status())){
				this.userRechargeDao.processOrder(ur, null, UserRechargeStatus.SUCCESS);
			}else{
				result.setStatus(ResultStatusType.FAILED);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
