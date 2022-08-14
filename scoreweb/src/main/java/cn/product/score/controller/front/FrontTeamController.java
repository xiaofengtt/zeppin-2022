/**
 * 
 */
package cn.product.score.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;

/**
 * 球队接口
 */

@Controller
@RequestMapping(value = "/front/team")
public class FrontTeamController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6899871862070821521L;

	@RequestMapping(value = "/getHistory", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result getHistory(String uuid) {
		
		InputParams params = new InputParams("frontTeamService", "getHistory");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 球队信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("frontTeamService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 球员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/playerGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result playerGet(String uuid) {
		
		InputParams params = new InputParams("frontTeamService", "playerGet");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
