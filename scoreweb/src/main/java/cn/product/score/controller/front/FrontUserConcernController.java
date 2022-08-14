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
import cn.product.score.controller.BaseController;

/**
 * 关注接口
 */

@Controller
@RequestMapping(value = "/front/concren")
public class FrontUserConcernController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4264183004231073950L;

	/**
	 * 关注列表
	 * @param category
	 * @param team
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "赛事", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String category, Integer pageNum, Integer pageSize, String sort, HttpServletRequest request) {
		InputParams params = new InputParams("frontUserConcernService", "list");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("category", null, category);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 检查是否已关注
	 * @param team
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String team, HttpServletRequest request) {
		InputParams params = new InputParams("frontUserConcernService", "check");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("team", null, team);
		return this.execute(params);
		
	}
	
	/**
	 * 添加关注
	 * @param team
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String team, HttpServletRequest request) {
		InputParams params = new InputParams("frontUserConcernService", "add");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("team", null, team);
		return this.execute(params);
	}
	
	/**
	 * 取消关注
	 * @param userConcern
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	@ActionParam(key = "userConcern", message = "关注球队", type = DataType.STRING, required = true)
	@ResponseBody
	public Result cancel(String userConcern, HttpServletRequest request) {
		InputParams params = new InputParams("frontUserConcernService", "cancel");
		params.addParams("fu", null, getFrontUser(request).getUuid());
		params.addParams("userConcern", null, userConcern);
		return this.execute(params);
	}
}
