/**
 * 
 */
package cn.product.score.controller.back;

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
@RequestMapping(value = "/back/match")
public class MatchController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3736850943403323654L;

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
	@ActionParam(key = "hometeam", message = "主队", type = DataType.STRING)
	@ActionParam(key = "awayteam", message = "客队", type = DataType.STRING)
	@ActionParam(key = "starttime", message = "开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message = "结束时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String category, String hometeam, String awayteam, String starttime, String endtime, String status, 
			Integer pageNum, Integer pageSize, String sort, HttpServletRequest request) {
		
		InputParams params = new InputParams("matchService", "list");
		params.addParams("category", null, category);
		params.addParams("hometeam", null, hometeam);
		params.addParams("awayteam", null, awayteam);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
}
