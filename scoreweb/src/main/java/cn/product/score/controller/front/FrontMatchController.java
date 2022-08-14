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
 * 赛事接口
 */

@Controller
@RequestMapping(value = "/front/match")
public class FrontMatchController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1989070397737138859L;

	/**
	 * 赛事列表
	 * @param category
	 * @param team
	 * @param teamToTeam
	 * @param starttime
	 * @param endtime
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ActionParam(key = "team", message = "球队", type = DataType.STRING)
	@ActionParam(key = "teamToTeam", message = "历史交锋", type = DataType.STRING)
	@ActionParam(key = "starttime", message = "开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message = "结束时间", type = DataType.STRING)
	@ActionParam(key = "status", message = "比赛状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String category, String team, String teamToTeam, String starttime, String endtime, String status, Integer pageNum, Integer pageSize, String sort,
			HttpServletRequest request) {
		
		InputParams params = new InputParams("frontMatchService", "list");
		params.addParams("category", null, category);
		params.addParams("team", null, team);
		params.addParams("teamToTeam", null, teamToTeam);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
		
	}
	
	/**
	 * 关注的赛事列表
	 * @param starttime
	 * @param endtime
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/concrenlist", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message = "开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message = "结束时间", type = DataType.STRING)
	@ActionParam(key = "status", message = "比赛状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result concrenlist(String starttime, String endtime, String status, Integer pageNum, Integer pageSize, String sort,
			HttpServletRequest request) {
		
		InputParams params = new InputParams("frontMatchService", "concrenlist");
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
		
	}
	
	/**
	 * 赛事详细信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		
		InputParams params = new InputParams("frontMatchService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
}
