/**
 * 
 */
package cn.product.treasuremall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

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
}
