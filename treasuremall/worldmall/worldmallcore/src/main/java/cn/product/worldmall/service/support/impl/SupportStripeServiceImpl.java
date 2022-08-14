package cn.product.worldmall.service.support.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.model.Charge;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.controller.base.TransactionException;
import cn.product.worldmall.dao.CapitalAccountDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.entity.FrontUserRechargeOrder.FrontUserRechargeOrderStatus;
import cn.product.worldmall.service.support.SupportStripeService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.stripe.StripeUtil;

@Service("supportStripeService")
public class SupportStripeServiceImpl implements SupportStripeService{
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;

	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
	@Override
	public void load(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String orderid = paramsMap.get("orderid") == null ? null : paramsMap.get("orderid").toString();
		
		FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(orderid);
		if(furo == null){
			result.setMessage("invalid order");
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())){
			result.setMessage("order can not load");
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
		if(ca == null){
			result.setMessage("invalid channel");
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		
		Map<String, Object> transDataMap = JSONUtils.json2map(furo.getTransData());
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", transDataMap.get("title"));
		dataMap.put("currency", furo.getCurrency());
		dataMap.put("totalAmount", furo.getCurrencyAmount());
		dataMap.put("orderNum", furo.getOrderNum());
		dataMap.put("returnUrl", transDataMap.get("returnUrl"));
		dataMap.put("publicKey", acParams.get("publicKey"));
		dataMap.put("sessionUuid", transDataMap.get("sessionUuid"));
		
		result.setData(dataMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void checkout(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		String orderid = paramsMap.get("orderid") == null ? null : paramsMap.get("orderid").toString();
		String stripeToken = paramsMap.get("stripeToken") == null ? null : paramsMap.get("stripeToken").toString();
		String stripeEmail = paramsMap.get("stripeEmail") == null ? null : paramsMap.get("stripeEmail").toString();
		
		FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(orderid);
		Map<String, Object> transData = JSONUtils.json2map(furo.getTransData());
		result.setData(transData.get("returnUrl").toString());
		
		if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
//		Webhook.constructEvent(payload, sigHeader, secret);
		CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		if(!CapitalAccountStatus.NORMAL.equals(ca.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		try {
			Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
			Charge charge = StripeUtil.excuteOrder(furo, furo.getCurrency() == null ? "USD" : furo.getCurrency(), acParams.get("secret").toString(), stripeToken);
			if(charge == null){
				result.setStatus(ResultStatusType.FAILED);
				return;
			}
			if("succeeded".equals(charge.getStatus())){
				transData.put("orderid", charge.getId());
				transData.put("email", stripeEmail);
				furo.setTransData(JSONUtils.obj2json(transData));
				this.frontUserHistoryDao.rechargeByWorldpay(furo, FrontUserRechargeOrderStatus.CHECKED);
			}else{
				result.setStatus(ResultStatusType.FAILED);
				return;
			}
			result.setStatus(ResultStatusType.SUCCESS);
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
	}
}
