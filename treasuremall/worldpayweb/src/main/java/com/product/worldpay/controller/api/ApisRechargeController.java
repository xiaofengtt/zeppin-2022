package com.product.worldpay.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.ActionParam;
import com.product.worldpay.controller.base.ActionParam.DataType;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;
import com.product.worldpay.util.Utlity;

/**
 * 充值api接口
 */

@Controller
@RequestMapping(value = "/apis/recharge")
public class ApisRechargeController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1502261340971393638L;

	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="Sign", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="Store ID", type = DataType.STRING, required = true)
	@ActionParam(key = "channel", message="Channel ID", type = DataType.STRING, required = true)
	@ActionParam(key = "notifyUrl", message="Notify Url", type = DataType.STRING, required = true)
	@ActionParam(key = "timestamp", message="Timestamp", type = DataType.STRING, required = true, maxLength = 13)
	@ActionParam(key = "title", message="Order Title", type = DataType.STRING, required = true, maxLength = 256)
	@ActionParam(key = "amount", message="Amount", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "orderNum", message="order number", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "passbackParams", message="Passback Params", type = DataType.STRING, maxLength = 512)
	@ActionParam(key = "returnUrl", message="Return Url", type = DataType.STRING, maxLength = 100)
	@ActionParam(key = "timeout", message="Timeout length", type = DataType.STRING, maxLength = 11)
	@ResponseBody
	public Result createOrder(String sign, String company, String channel, String notifyUrl, String timestamp, String title, String amount,
			String orderNum, String passbackParams, String returnUrl, String timeout, HttpServletRequest request){
		InputParams params = new InputParams("apisRechargeService", "createOrder");
		params.addParams("sign", null, sign);
		params.addParams("company", null, company);
		params.addParams("channel", null, channel);
		params.addParams("notifyUrl", null, notifyUrl);
		params.addParams("timestamp", null, timestamp);
		params.addParams("title", null, title);
		params.addParams("amount", null, amount);
		params.addParams("orderNum", null, orderNum);
		params.addParams("passbackParams", null, passbackParams);
		params.addParams("returnUrl", null, returnUrl);
		params.addParams("timeout", null, timeout);
		params.addParams("ip", null, Utlity.getIpAddr(request));
		return this.apiExecute(params);
	}
	
	@RequestMapping(value="/queryOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="Sign", type = DataType.STRING)
	@ActionParam(key = "company", message="Store ID", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="order number", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "paymentOrderNum", message="payment order number", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "timestamp", message="Timestamp", type = DataType.STRING, required = true, maxLength = 13)
	@ResponseBody
	public Result queryOrder(String sign, String company, String orderNum, String paymentOrderNum, String timestamp, 
			HttpServletRequest request){
		InputParams params = new InputParams("apisRechargeService", "queryOrder");
		params.addParams("sign", null, sign);
		params.addParams("company", null, company);
		params.addParams("orderNum", null, orderNum);
		params.addParams("paymentOrderNum", null, paymentOrderNum);
		params.addParams("timestamp", null, timestamp);
		params.addParams("ip", null, Utlity.getIpAddr(request));
		return this.apiExecute(params);
	}
	
	@RequestMapping(value="/closeOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="Sign", type = DataType.STRING)
	@ActionParam(key = "company", message="Store ID", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="order number", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "paymentOrderNum", message="payment order number", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "timestamp", message="Timestamp", type = DataType.STRING, required = true, maxLength = 13)
	@ResponseBody
	public Result closeOrder(String sign, String company, String orderNum, String paymentOrderNum, String timestamp, 
			HttpServletRequest request){
		InputParams params = new InputParams("apisRechargeService", "closeOrder");
		params.addParams("sign", null, sign);
		params.addParams("company", null, company);
		params.addParams("orderNum", null, orderNum);
		params.addParams("paymentOrderNum", null, paymentOrderNum);
		params.addParams("timestamp", null, timestamp);
		params.addParams("ip", null, Utlity.getIpAddr(request));
		return this.apiExecute(params);
	}
}