/**
 * 
 */
package cn.product.score.controller.front;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.api.base.ResultManager;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.FrontUser;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/userRecharge")
public class FrontUserRechargeController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2284176303538616110L;

	/**
	 * 银行卡充值
	 * @return
	 */
	@RequestMapping(value = "/byBankcard", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message = "金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result byBankcard(HttpServletRequest request, String capitalAccount, BigDecimal fee, String transData) {
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		InputParams params = new InputParams("frontUserRechargeService", "byBankcard");
		params.addParams("fu", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("fee", null, fee);
		params.addParams("transData", null, transData);
		return this.execute(params);
	}
	
	/**
	 * 支付宝对公充值
	 * @return
	 */
	@RequestMapping(value = "/byAlipay", method = RequestMethod.POST)
	@ActionParam(key = "capitalAccount", message = "渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message = "金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "transData", message = "交易信息", type = DataType.STRING, required = true)
	@ResponseBody
	public Result byAlipay(HttpServletRequest request, String capitalAccount, BigDecimal fee, String transData) {
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		InputParams params = new InputParams("frontUserRechargeService", "byAlipay");
		params.addParams("fu", null, fu.getUuid());
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("fee", null, fee);
		params.addParams("transData", null, transData);
		return this.execute(params);
	}
}
