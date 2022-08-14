/**
 * 
 */
package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.api.base.ResultManager;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.front.FrontUserVO;
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
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result getAmountSet(Integer pageNum, Integer pageSize, String sorts) {
		
		InputParams params = new InputParams("frontUserRechargeService", "getAmountSet");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sorts);
		return this.execute(params);
	}
	
//	/**
//	 * 银行卡充值
//	 * @return
//	 */
//	@RequestMapping(value = "/byBankcard", method = RequestMethod.POST)
//	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
//	@ActionParam(key = "fee", message = "金额", type = DataType.POSITIVE_CURRENCY, required = true)
//	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING, required = true)
//	@ResponseBody
//	public Result byBankcard(HttpServletRequest request, String capitalAccount, BigDecimal fee, String transData) {
//		FrontUser fu = getFrontUser(request);
//		if(fu == null) {
//			return ResultManager.createFailResult("用户未登录！");
//		}
//		
//		InputParams params = new InputParams("frontUserRechargeService", "byBankcard");
//		params.addParams("fu", null, fu.getUuid());
//		params.addParams("capitalAccount", null, capitalAccount);
//		params.addParams("fee", null, fee);
//		params.addParams("transData", null, transData);
//		return this.execute(params);
//	}
//	
//	/**
//	 * 支付宝对公充值
//	 * @return
//	 */
//	@RequestMapping(value = "/byAlipay", method = RequestMethod.POST)
//	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
//	@ActionParam(key = "fee", message = "金额", type = DataType.POSITIVE_CURRENCY, required = true)
//	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING, required = true)
//	@ResponseBody
//	public Result byAlipay(HttpServletRequest request, String capitalAccount, BigDecimal fee, String transData) {
//		FrontUser fu = getFrontUser(request);
//		if(fu == null) {
//			return ResultManager.createFailResult("用户未登录！");
//		}
//		
//		InputParams params = new InputParams("frontUserRechargeService", "byAlipay");
//		params.addParams("fu", null, fu.getUuid());
//		params.addParams("capitalAccount", null, capitalAccount);
//		params.addParams("fee", null, fee);
//		params.addParams("transData", null, transData);
//		return this.execute(params);
//	}
	
	/**
	 * 聚合支付充值
	 * @return
	 */
	@ApiOperation(value = "充值（聚合支付宝渠道充值）", notes = "充值（聚合支付宝渠道充值）")
	@RequestMapping(value = "/byUnion", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "充值金额", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "到账金币", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "是否自定义", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "返回地址链接", type = DataType.STRING)
	@ResponseBody
	public Result byUnion(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
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
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "充值金额", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "到账金币", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "是否自定义", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "返回地址链接", type = DataType.STRING)
	@ResponseBody
	public Result byAcicpay(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
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
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message = "充值金额", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "到账金币", type = DataType.STRING, required = true)
	@ActionParam(key = "isFree", message = "是否自定义", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING)
	@ActionParam(key = "returnUrl", message = "返回地址链接", type = DataType.STRING, required = true)
	@ResponseBody
	public Result byJinzun(HttpServletRequest request, String capitalAccount, String amount, String dAmount, String transData, Boolean isFree, String returnUrl) {
		FrontUserVO fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
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
}
