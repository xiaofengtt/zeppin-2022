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
@RequestMapping(value = "/front/userWithdraw")
@Api(tags= {"userWithdraw"})
public class FrontUserWithdrawController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4795430020449467296L;

	/**
	 * 用户提现
	 * @param price
	 * @param bankcard
	 * @param mobileCode
	 * @return
	 */
	@ApiOperation(value = "提现（聚合支付提现）", notes = "提现（聚合支付提现）")
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ActionParam(key = "amount", message = "amount", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "amount gold", type = DataType.STRING, required = true)
	@ActionParam(key = "currency", message = "currency", type = DataType.STRING)
	@ActionParam(key = "currencyRate", message = "currencyRate", type = DataType.STRING)
	@ActionParam(key = "currencyAmount", message = "currencyAmount", type = DataType.STRING)
	@ResponseBody
	protected Result withdraw(String amount, String dAmount, HttpServletRequest request, String currency, String currencyRate, String currencyAmount){
		synchronized(this){
			FrontUserVO fu = getFrontUser(request);
			if(fu == null) {
				return ResultManager.createFailResult("The user is not logged in!");
			}
			
			//日活统计
			InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
			dailyParams.addParams("frontUser", null, fu.getUuid());
			this.execute(dailyParams);
			
			InputParams params = new InputParams("frontUserWithdrawService", "withdraw");
			params.addParams("amount", null, amount);
			params.addParams("dAmount", null, dAmount);
			params.addParams("frontUser", null, fu.getUuid());
			params.addParams("mobile", null, fu.getMobile());
			params.addParams("currency", null, currency);
			params.addParams("currencyRate", null, currencyRate);
			params.addParams("currencyAmount", null, currencyAmount);
			return this.execute(params);
		}
	}
}
