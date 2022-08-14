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
import cn.product.treasuremall.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

/**
 * 前端拉皮条排行榜
 */

@Controller
@RequestMapping(value = "/front/recommend")
@Api(tags= {"recommend"})
public class FrontRecommendController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1947633938873668804L;

	/**
	 * 排行榜列表
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "获取排行榜", notes = "获取排行榜")
	@RequestMapping(value = "/rankingList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result rankingList(Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("frontRecommendService", "rankingList");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 被邀请人列表
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "被邀请人列表", notes = "被邀请人列表")
	@RequestMapping(value = "/agentList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result agentList(HttpServletRequest request, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("frontRecommendService", "agentList");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 邀请获利列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @param starttime
	 * @param endtime
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "邀请获利列表", notes = "邀请获利列表")
	@RequestMapping(value = "/awardList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "starttime", message="开始时间", type = DataType.STRING)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.STRING)
	@ActionParam(key = "sort", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result awardList(HttpServletRequest request, Integer pageNum, Integer pageSize, String starttime, String endtime, String sort) {
		InputParams params = new InputParams("frontRecommendService", "awardList");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
