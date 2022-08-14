package cn.product.worldmall.service.support.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

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
import cn.product.worldmall.service.support.SupportPaypalService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.util.paypal.PaypalAccount;
import cn.product.worldmall.util.paypal.PaypalClient;

@Service("supportPaypalService")
public class SupportPaypalServiceImpl implements SupportPaypalService{
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private CapitalAccountDao capitalAccountDao;
	
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
//		searchMap.put("channel", "1075f929-5f6d-4d54-a64a-f728e0c92d04");
		searchMap.put("transData", paymentId);
		List<FrontUserRechargeOrder> urList = this.frontUserRechargeOrderDao.getListByParams(searchMap);
		if(urList.size() != 1){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		FrontUserRechargeOrder furo = this.frontUserRechargeOrderDao.get(urList.get(0).getUuid());
		Map<String, Object> transData = JSONUtils.json2map(furo.getTransData());
		result.setData(transData.get("returnUrl").toString());
		
		if(FrontUserRechargeOrderStatus.CHECKED.equals(furo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		CapitalAccount ca = this.capitalAccountDao.get(furo.getCapitalAccount());
		if(ca == null){
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		if(!CapitalAccountStatus.NORMAL.equals(ca.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		
		Map<String, Object> acParams = JSONUtils.json2map(ca.getData());
		PaypalAccount paypalAccount = new PaypalAccount(acParams.get("clientId").toString(), acParams.get("secret").toString(), Utlity.TANSFER_URL);
		
		try {
			HttpResponse<Order> response = PaypalClient.excuteOrder(paypalAccount, paymentId, payerId);
			System.out.print(response);
			if("COMPLETED".equals(response.result().status())){
				this.frontUserHistoryDao.rechargeByWorldpay(furo, FrontUserRechargeOrderStatus.CHECKED);
			}else{
				result.setStatus(ResultStatusType.FAILED);
				return;
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			return;
		}
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}
}
