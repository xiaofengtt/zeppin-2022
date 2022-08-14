/**
 * 
 */
package cn.product.score.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.Team;

/**
 * 球队管理
 */

@Controller
@RequestMapping(value = "/back/team")
public class TeamController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6350615849921017288L;

	/**
	 * 根据条件查询列表
	 * @param name
	 * @param category
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "category", message = "所属赛事", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Team team, String category, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("teamService", "list");
		params.addParams("team", null, team);
		params.addParams("category", null, category);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		InputParams params = new InputParams("teamService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param name
	 * @param shortname
	 * @param category
	 * @param icon
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING, required = true)
	@ActionParam(key = "category", message = "所属赛事", type = DataType.STRING)
	@ActionParam(key = "icon", message = "图标", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(Team team) {
		InputParams params = new InputParams("teamService", "add");
		params.addParams("team", null, team);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param name
	 * @param icon
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING, required = true)
	@ActionParam(key = "icon", message = "图标", type = DataType.STRING)
	@ResponseBody
	public Result edit(Team team) {
		InputParams params = new InputParams("teamService", "edit");
		params.addParams("team", null, team);
		return this.execute(params);
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		InputParams params = new InputParams("teamService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 球员列表
	 * @param name
	 * @param team
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/playerList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "team", message = "所属球队", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result playerList(String name, String team, String status, Integer pageNum, Integer pageSize, String sort) {
		InputParams params = new InputParams("teamService", "playerList");
		params.addParams("name", null, name);
		params.addParams("team", null, team);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 球员信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/playerGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "名称", type = DataType.STRING, required = true)
	@ResponseBody
	public Result playerGet(String uuid) {
		InputParams params = new InputParams("teamService", "playerGet");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 编辑
	 * @param uuid
	 * @param cnname
	 * @return
	 */
	@RequestMapping(value = "/playerEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "cnname", message = "名称", type = DataType.STRING, required = true)
	@ResponseBody
	public Result playerEdit(String uuid, String cnname) {
		InputParams params = new InputParams("teamService", "playerEdit");
		params.addParams("uuid", null, uuid);
		params.addParams("cnname", null, cnname);
		return this.execute(params);
	}
}
