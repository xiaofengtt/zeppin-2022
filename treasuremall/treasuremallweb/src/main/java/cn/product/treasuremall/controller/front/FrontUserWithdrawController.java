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
import cn.product.treasuremall.vo.front.FrontUserVO;
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
	@ActionParam(key = "amount", message = "提现金额", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message = "扣减金币", type = DataType.STRING, required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "提现银行卡编号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message = "手机验证码", required = true)
	@ResponseBody
	protected Result withdraw(String bankcard, String mobileCode, String amount, String dAmount, HttpServletRequest request){
		synchronized(this){
			FrontUserVO fu = getFrontUser(request);
			if(fu == null) {
				return ResultManager.createFailResult("用户未登录！");
			}
			
			//日活统计
			InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
			dailyParams.addParams("frontUser", null, fu.getUuid());
			this.execute(dailyParams);
			
			InputParams params = new InputParams("frontUserWithdrawService", "withdraw");
			params.addParams("bankcard", null, bankcard);
			params.addParams("mobileCode", null, mobileCode);
			params.addParams("amount", null, amount);
			params.addParams("dAmount", null, dAmount);
			params.addParams("frontUser", null, fu.getUuid());
			params.addParams("mobile", null, fu.getMobile());
			return this.execute(params);
		}
	}
}
