package cn.product.payment.controller.transfer;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.util.JSONUtils;
import cn.product.payment.util.alipay.AliUtlity;
import cn.product.payment.util.wechat.WechatUtlity;

/**
 * ali转发接口
 */

@Controller
@RequestMapping(value = "/transfer/recharge")
public class TransferRechargeController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1591377501791075250L;

	/**
	 * 转发支付宝创建订单
	 * @param acParamsStr
	 * @param aliParamsStr
	 * @return
	 */
	@RequestMapping(value="/aliCreateOrder",method=RequestMethod.POST)
	@ActionParam(key = "acParamsStr", message="商户信息", type = DataType.STRING, required = true)
	@ActionParam(key = "aliParamsStr", message="订单信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result aliCreateOrder(String acParamsStr, String aliParamsStr){
		Map<String, Object> acParams = JSONUtils.json2map(acParamsStr);
		Map<String, String> aliParams = JSONUtils.json2strmap(aliParamsStr);
		Map<String, Object> aliResult = AliUtlity.createOrder4wap(acParams, aliParams);
		return ResultManager.createDataResult(JSONUtils.obj2json(aliResult));
	}
	
	/**
	 * 转发支付宝订单查询
	 * @param acParamsStr
	 * @param orderNum
	 * @return
	 */
	@RequestMapping(value="/aliQueryOrder",method=RequestMethod.POST)
	@ActionParam(key = "acParamsStr", message="商户信息", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="订单号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result aliQueryOrder(String acParamsStr, String orderNum){
		Map<String, Object> acParams = JSONUtils.json2map(acParamsStr);
		Map<String, Object> aliResult = AliUtlity.getOrderInfo(acParams, orderNum);
		return ResultManager.createDataResult(JSONUtils.obj2json(aliResult));
	}
	
	/**
	 * 转发微信创建订单
	 * @param acParamsStr
	 * @param wechatParamsStr
	 * @return
	 */
	@RequestMapping(value="/wechatCreateOrder",method=RequestMethod.POST)
	@ActionParam(key = "acParamsStr", message="商户信息", type = DataType.STRING, required = true)
	@ActionParam(key = "wechatParamsStr", message="订单信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result wechatCreateOrder(String acParamsStr, String wechatParamsStr){
		Map<String, Object> acParams = JSONUtils.json2map(acParamsStr);
		Map<String, String> wechatParams = JSONUtils.json2strmap(wechatParamsStr);
		Map<String, Object> wechatResult = WechatUtlity.createOrder(acParams, wechatParams);
		return ResultManager.createDataResult(JSONUtils.obj2json(wechatResult));
	}
	
	/**
	 * 转发微信订单查询
	 * @param acParamsStr
	 * @param orderNum
	 * @return
	 */
	@RequestMapping(value="/wechatQueryOrder",method=RequestMethod.POST)
	@ActionParam(key = "acParamsStr", message="商户信息", type = DataType.STRING, required = true)
	@ActionParam(key = "orderNum", message="订单号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result wechatQueryOrder(String acParamsStr, String orderNum){
		Map<String, Object> acParams = JSONUtils.json2map(acParamsStr);
		Map<String, Object> wechatResult = WechatUtlity.queryOrder(acParams, orderNum);
		return ResultManager.createDataResult(JSONUtils.obj2json(wechatResult));
	}
	
	/**
	 * 获取支付宝收款码页面信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/aliCodeGet",method=RequestMethod.POST)
	@ActionParam(key = "token", message="签名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result aliCodeGet(String token){
		InputParams params = new InputParams("transferRechargeService", "aliGet");
		params.addParams("token", null, token);
		return this.execute(params);
	}
	
	/**
	 * 支付宝收款码完成支付按钮
	 * @param token
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value="/aliCodeFinish",method=RequestMethod.POST)
	@ActionParam(key = "token", message="签名", type = DataType.STRING, required = true)
	@ActionParam(key = "orderid", message="订单号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result aliCodeFinish(String token, String orderid){
		InputParams params = new InputParams("transferRechargeService", "aliFinish");
		params.addParams("token", null, token);
		params.addParams("orderid", null, orderid);
		return this.execute(params);
	}
	
	/**
	 * 获取微信收款码页面信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/wechatCodeGet",method=RequestMethod.POST)
	@ActionParam(key = "token", message="签名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result wechatCodeGet(String token){
		InputParams params = new InputParams("transferRechargeService", "wechatGet");
		params.addParams("token", null, token);
		return this.execute(params);
	}
	
	/**
	 * 微信收款码完成支付按钮
	 * @param token
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value="/wechatCodeFinish",method=RequestMethod.POST)
	@ActionParam(key = "token", message="签名", type = DataType.STRING, required = true)
	@ActionParam(key = "orderid", message="订单号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result wechatCodeFinish(String token, String orderid){
		InputParams params = new InputParams("transferRechargeService", "wechatFinish");
		params.addParams("token", null, token);
		params.addParams("orderid", null, orderid);
		return this.execute(params);
	}
}