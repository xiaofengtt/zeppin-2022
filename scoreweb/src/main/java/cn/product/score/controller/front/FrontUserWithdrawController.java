/**
 * 
 */
package cn.product.score.controller.front;

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
@RequestMapping(value = "/front/userWithdraw")
public class FrontUserWithdrawController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4795430020449467296L;
	/**
	 * 获取手续费
	 * @return
	 */
	@RequestMapping(value = "/poundage", method = RequestMethod.GET)
	@ResponseBody
	protected Result poundage(){
		InputParams params = new InputParams("frontUserWithdrawService", "poundage");
		return this.execute(params);
	}
	/**
	 * 用户提现
	 * @param price
	 * @param bankcard
	 * @param mobileCode
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ActionParam(key = "price", type = DataType.STRING, message = "提现金额", required = true)
	@ActionParam(key = "bankcard", type = DataType.STRING, message = "提现银行卡编号", required = true)
	@ActionParam(key = "mobileCode", type = DataType.STRING, message = "手机验证码", required = true)
	@ResponseBody
	protected Result withdraw(String bankcard, String mobileCode, String price, HttpServletRequest request){
		synchronized(this){
			FrontUser fu = getFrontUser(request);
			if(fu == null) {
				return ResultManager.createFailResult("用户未登录！");
			}
			InputParams params = new InputParams("frontUserWithdrawService", "withdraw");
			params.addParams("bankcard", null, bankcard);
			params.addParams("mobileCode", null, mobileCode);
			params.addParams("price", null, price);
			params.addParams("fu", null, fu.getUuid());
			params.addParams("mobile", null, fu.getMobile());
			return this.execute(params);
		}
	}
}
