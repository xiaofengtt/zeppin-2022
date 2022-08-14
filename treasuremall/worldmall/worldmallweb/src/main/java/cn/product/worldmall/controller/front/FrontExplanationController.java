/**
 * 
 */
package cn.product.worldmall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
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
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result recharge(String demoFlag, String version, String channel) {
		InputParams params = new InputParams("frontExplanationService", "recharge");
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
		return this.execute(params);
	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result withdraw(String demoFlag, String version, String channel) {
		InputParams params = new InputParams("frontExplanationService", "withdraw");
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
		return this.execute(params);
	}
}
