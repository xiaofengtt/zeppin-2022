/**
 * 
 */
package cn.product.treasuremall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;

/**
 * 用户须知
 */

@Controller
@RequestMapping(value = "/front/explanation")
@Api(tags= {"explanation"})
public class FrontExplanationController extends BaseController {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6665714126728213204L;

	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	@ResponseBody
	public Result recharge() {
		InputParams params = new InputParams("frontExplanationService", "recharge");
		return this.execute(params);
	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	@ResponseBody
	public Result withdraw() {
		InputParams params = new InputParams("frontExplanationService", "withdraw");
		return this.execute(params);
	}
}
