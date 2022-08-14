package cn.product.payment.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.util.Utlity;

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
	
	/**
	 * 创建充值订单
	 * @param sign
	 * @param company
	 * @param channel
	 * @param notifyUrl
	 * @param timestamp
	 * @param title
	 * @param amount
	 * @param orderNum
	 * @param passbackParams
	 * @param returnUrl
	 * @param timeout
	 * @param clientIp
	 * @param holder
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "channel", message="渠道ID", type = DataType.STRING, required = true)
	@ActionParam(key = "notifyUrl", message="回调地址", type = DataType.STRING, required = true)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
	@ActionParam(key = "title", message="订单标题", type = DataType.STRING, required = true, maxLength = 256)
	@ActionParam(key = "amount", message="付款金额", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "passbackParams", message="回传参数", type = DataType.STRING, maxLength = 512)
	@ActionParam(key = "returnUrl", message="返回地址", type = DataType.STRING, maxLength = 100)
	@ActionParam(key = "timeout", message="超时时间", type = DataType.STRING, maxLength = 11)
	@ActionParam(key = "clientIp", message="客户端IP", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "holder", message="付款人", type = DataType.STRING, maxLength = 20)
	@ResponseBody
	public Result createOrder(String sign, String company, String channel, String notifyUrl, String timestamp, String title, String amount,
			String orderNum, String passbackParams, String returnUrl, String timeout, String clientIp, String holder, HttpServletRequest request){
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
		params.addParams("clientIp", null, clientIp);
		params.addParams("holder", null, holder);
		params.addParams("ip", null, Utlity.getIpAddr(request));
		return this.apiExecute(params);
	}
	
	/**
	 * 查询充值订单
	 * @param sign
	 * @param company
	 * @param orderNum
	 * @param paymentOrderNum
	 * @param timestamp
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "paymentOrderNum", message="平台订单号", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
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
	
	/**
	 * 关闭充值订单
	 * @param sign
	 * @param company
	 * @param orderNum
	 * @param paymentOrderNum
	 * @param timestamp
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/closeOrder",method=RequestMethod.POST)
	@ActionParam(key = "sign", message="签名", type = DataType.STRING)
	@ActionParam(key = "company", message="商户ID", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="唯一订单号", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "paymentOrderNum", message="平台订单号", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "timestamp", message="时间戳", type = DataType.STRING, required = true, maxLength = 13)
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