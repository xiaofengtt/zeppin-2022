package cn.product.payment.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.BaseController;
import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.controller.base.Result;


/**
 * 转发服务器网关
 */

@Controller
@RequestMapping(value = "/transfer/recharge")
public class TransferRechargeController extends BaseController{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7285928812168412562L;

	/**
	 * 获取订单信息（已废弃转移）
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	@ActionParam(key = "token", message="签名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String token){
		InputParams params = new InputParams("transferRechargeService", "get");
		params.addParams("token", null, token);
		return this.apiExecute(params);
	}
	
	/**
	 * 完成订单（已废弃转移）
	 * @param token
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value="/finish",method=RequestMethod.POST)
	@ActionParam(key = "token", message="签名", type = DataType.STRING, required = true)
	@ActionParam(key = "orderid", message="订单号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result finish(String token, String orderid){
		InputParams params = new InputParams("transferRechargeService", "finish");
		params.addParams("token", null, token);
		params.addParams("orderid", null, orderid);
		return this.apiExecute(params);
	}
}