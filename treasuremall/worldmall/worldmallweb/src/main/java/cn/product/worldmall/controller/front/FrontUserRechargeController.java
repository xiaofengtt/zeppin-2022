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
import cn.product.worldmall.util.Utlity;
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
@RequestMapping(value = "/front/userRecharge")
@Api(tags= {"userRecharge"})
public class FrontUserRechargeController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2284176303538616110L;

	/**
	 * 获取充值金额配置
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@ApiOperation(value = "获取充值金额配置", notes = "获取充值金额配置")
	@RequestMapping(value = "/getAmountSet", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result getAmountSet(Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("frontUserRechargeService", "getAmountSet");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
	
	/**
	 * 聚合支付充值
	 * @return
	 */
	@ApiOperation(value = "充值（聚合支付宝渠道充值）", notes = "充值（聚合支付宝渠道充值）")
	@RequestMapping(value = "/byUnion", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "capitalAccount", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "is free", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "message", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "returnUrl", type = DataType.STRING)
	@ResponseBody
	public Result byUnion(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserRechargeService", "byUnion");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("transData", null, transData);
		params.addParams("isFree", null, isFree);
		params.addParams("returnUrl", null, returnUrl);
		params.addParams("ip", null, Utlity.getIpAddr(request));
//		params.addParams("ip", null, "42.200.228.8");
		return this.execute(params);
	}
	
	/**
	 * 聚合支付充值
	 * @return
	 */
	@ApiOperation(value = "充值（兴达支付充值）", notes = "充值（兴达支付充值）")
	@RequestMapping(value = "/byAcicpay", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "capitalAccount", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "is free", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "message", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "returnUrl", type = DataType.STRING)
	@ResponseBody
	public Result byAcicpay(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserRechargeService", "byAcicpay");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("transData", null, transData);
		params.addParams("isFree", null, isFree);
		params.addParams("returnUrl", null, returnUrl);
		return this.execute(params);
	}
	
	/**
	 * 金樽支付充值
	 * @return
	 */
	@ApiOperation(value = "充值（Jinzun支付充值）", notes = "充值（Jinzun支付充值）")
	@RequestMapping(value = "/byJinzun", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "capitalAccount", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "is free", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "message", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "returnUrl", type = DataType.STRING, required = true)
	@ResponseBody
	public Result byJinzun(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserRechargeService", "byJinzun");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("transData", null, transData);
		params.addParams("isFree", null, isFree);
		params.addParams("returnUrl", null, returnUrl);
		return this.execute(params);
	}
	
	/**
	 * 聚合支付充值
	 * @return
	 */
	@ApiOperation(value = "充值（信用卡渠道充值）", notes = "充值（信用卡渠道充值）")
	@RequestMapping(value = "/byCreditcard", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "capitalAccount", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "is free", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "message", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "returnUrl", type = DataType.STRING)
	@ResponseBody
	public Result byCreditcard(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserRechargeService", "byCreditcard");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("transData", null, transData);
		params.addParams("isFree", null, isFree);
		params.addParams("returnUrl", null, returnUrl);
		params.addParams("ip", null, Utlity.getIpAddr(request));
//		params.addParams("ip", null, "42.200.228.8");
		return this.execute(params);
	}	
	
	/**
	 * paypal充值
	 * @return
	 */
	@ApiOperation(value = "充值（paypal渠道充值）", notes = "充值（paypal渠道充值）")
	@RequestMapping(value = "/byPaypal", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "capitalAccount", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "is free", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "message", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "returnUrl", type = DataType.STRING)
	@ActionParam(key = "currency", message = "currency", type = DataType.STRING)
	@ActionParam(key = "currencyRate", message = "currencyRate", type = DataType.STRING)
	@ActionParam(key = "currencyAmount", message = "currencyAmount", type = DataType.STRING)
	@ResponseBody
	public Result byPaypal(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl
			, String currency, String currencyRate, String currencyAmount) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserRechargeService", "byPaypal");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("transData", null, transData);
		params.addParams("isFree", null, isFree);
		params.addParams("returnUrl", null, returnUrl);
		params.addParams("ip", null, Utlity.getIpAddr(request));
//		params.addParams("ip", null, "42.200.228.8");
		params.addParams("currency", null, currency);
		params.addParams("currencyRate", null, currencyRate);
		params.addParams("currencyAmount", null, currencyAmount);
		return this.execute(params);
	}
	
	/**
	 * paypal充值
	 * @return
	 */
	@ApiOperation(value = "充值（stripe渠道充值）", notes = "充值（stripe渠道充值）")
	@RequestMapping(value = "/byStripe", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "capitalAccount", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "is free", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "message", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "returnUrl", type = DataType.STRING)
	@ActionParam(key = "currency", message = "currency", type = DataType.STRING)
	@ActionParam(key = "currencyRate", message = "currencyRate", type = DataType.STRING)
	@ActionParam(key = "currencyAmount", message = "currencyAmount", type = DataType.STRING)
	@ResponseBody
	public Result byStripe(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl
			, String currency, String currencyRate, String currencyAmount) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("The user is not logged in!");
		}
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, fu.getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontUserRechargeService", "byStripe");
		params.addParams("frontUser", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("transData", null, transData);
		params.addParams("isFree", null, isFree);
		params.addParams("returnUrl", null, returnUrl);
		params.addParams("ip", null, Utlity.getIpAddr(request));
//		params.addParams("ip", null, "42.200.228.8");
		params.addParams("currency", null, currency);
		params.addParams("currencyRate", null, currencyRate);
		params.addParams("currencyAmount", null, currencyAmount);
		return this.execute(params);
	}
}
