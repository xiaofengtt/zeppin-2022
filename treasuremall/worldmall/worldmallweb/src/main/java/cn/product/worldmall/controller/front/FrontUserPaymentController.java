/**
 * 
 */
package cn.product.worldmall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.vo.front.FrontUserVO;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.api.base.ResultManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/payment")
@Api(tags= {"payment"})
public class FrontUserPaymentController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2501901567957903730L;

	/**
	 * 用户下单
	 * @return
	 */
	@ApiOperation(value = "用户下单", notes = "用户下单：buyCount、dAmount、actualDAmount三个参数需要Base64加密/增加isAll参数取值（true/false）")
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "Prize ID", type = DataType.STRING, required = true)
	@ActionParam(key = "buyCount", message = "buyCount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "dAmount", type = DataType.STRING, required = true)
	@ActionParam(key = "actualDAmount", message = "actualDAmount", type = DataType.STRING, required = true)
	@ActionParam(key = "voucher", message = "Voucher ID", type = DataType.STRING)
	@ActionParam(key = "isAll", message = "isAll", type = DataType.BOOLEAN)
	@ActionParam(key = "paymentGroup", message = "paymentGroup", type = DataType.STRING)
	@ResponseBody
	public Result placeOrder(HttpServletRequest request, String uuid, String buyCount, String dAmount
			, String actualDAmount, String voucher, Boolean isAll, String paymentGroup) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserPaymentService", "placeOrder");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("uuid", null, uuid);
		params.addParams("buyCount", null, buyCount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("actualDAmount", null, actualDAmount);
		params.addParams("voucher", null, voucher);
		params.addParams("isAll", null, isAll);
		params.addParams("paymentGroup", null, paymentGroup);
		return this.execute(params);
	}
}
