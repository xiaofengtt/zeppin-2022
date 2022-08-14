/**
 * 
 */
package cn.product.worldmall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import io.swagger.annotations.Api;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

/**
 * 前端地区
 */

@Controller
@RequestMapping(value = "/front/area")
@Api(tags= {"area"})
public class FrontAreaController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -687444890753909265L;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "level", message = "level", type = DataType.STRING)
	@ActionParam(key = "pid", message = "parent", type = DataType.STRING)
	@ResponseBody
	public Result list(String level, String pid) {
		
		InputParams params = new InputParams("frontAreaService", "list");
		params.addParams("level", null, level);
		params.addParams("pid", null, pid);
		return this.execute(params);
	}

	@RequestMapping(value = "/country", method = RequestMethod.GET)
	@ResponseBody
	public Result country() {
		InputParams params = new InputParams("frontAreaService", "country");
		return this.execute(params);
	}

	@RequestMapping(value = "/currency", method = RequestMethod.GET)
	@ResponseBody
	public Result currency() {
		InputParams params = new InputParams("frontAreaService", "currency");
		return this.execute(params);
	}
}
