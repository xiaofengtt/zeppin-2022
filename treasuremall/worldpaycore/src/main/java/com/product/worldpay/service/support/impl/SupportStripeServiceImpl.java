package com.product.worldpay.service.support.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ChannelAccountDao;
import com.product.worldpay.dao.UserRechargeDao;
import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.entity.UserRecharge.UserRechargeStatus;
import com.product.worldpay.service.support.SupportStripeService;
import com.product.worldpay.util.JSONUtils;
import com.product.worldpay.util.stripe.StripeUtil;

@Service("supportStripeService")
public class SupportStripeServiceImpl implements SupportStripeService{
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Override
	public void webhook(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String orderid = paramsMap.get("orderid") == null ? null : paramsMap.get("orderid").toString();
		
		UserRecharge ur = this.userRechargeDao.get(orderid);
		if(ur == null){
			result.setMessage("invalid order");
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		if(UserRechargeStatus.SUCCESS.equals(ur.getStatus())){
			result.setMessage("order can not load");
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		ChannelAccount ca = this.channelAccountDao.get(ur.getChannelAccount());
		if(ca == null){
			result.setMessage("invalid channel");
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		String session = StripeUtil.createOrder(ur, acParams.get("publicKey").toString());
		
		Map<String, Object> transDataMap = JSONUtils.json2map(ur.getTransData());
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("form", session);
		dataMap.put("title", transDataMap.get("title"));
		dataMap.put("currency", ur.getCurrency());
		dataMap.put("totalAmount", ur.getTotalAmount());
		dataMap.put("orderNum", ur.getOrderNum());
		dataMap.put("returnUrl", transDataMap.get("returnUrl"));
		
		result.setData(dataMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
