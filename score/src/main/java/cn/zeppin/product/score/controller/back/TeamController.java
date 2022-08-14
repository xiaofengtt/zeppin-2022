/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.InfoCategoryTeamService;
import cn.zeppin.product.score.service.InfoPlayersService;
import cn.zeppin.product.score.service.InfoTeamPlayersService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.vo.back.InfoPlayersVO;
import cn.zeppin.product.score.vo.back.TeamVO;

/**
 * 球队管理
 */

@Controller
@RequestMapping(value = "/back/team")
public class TeamController extends BaseController{
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private InfoPlayersService infoPlayersService;
	
	@Autowired
	private InfoCategoryTeamService infoCategoryTeamService;
	
	@Autowired
	private InfoTeamPlayersService infoTeamPlayersService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ResourceService resourceService;
	
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
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", team.getName());
		searchMap.put("category", category);
		searchMap.put("status", team.getStatus());
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalCount = teamService.getCountByParams(searchMap);
		List<Team> list = teamService.getListByParams(searchMap);
		List<TeamVO> voList = new LinkedList<TeamVO>();
		for(Team te : list){
			TeamVO teamVO = new TeamVO(te);
			if(te.getIcon() != null){
				Resource res = resourceService.get(te.getIcon());
				if(res != null){
					teamVO.setIconUrl(res.getUrl());
				}
			}
			Admin admin = adminService.get(te.getCreator());
			if(admin != null){
				teamVO.setCreatorName(admin.getRealname());
			}
			voList.add(teamVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
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
		Team team = teamService.get(uuid);
		if(team == null){
			return ResultManager.createFailResult("球队不存在");
		}
		
		TeamVO teamVO = new TeamVO(team);
		if(team.getIcon() != null){
			Resource res = resourceService.get(team.getIcon());
			if(res != null){
				teamVO.setIconUrl(res.getUrl());
			}
		}
		Admin admin = adminService.get(team.getCreator());
		if(admin != null){
			teamVO.setCreatorName(admin.getRealname());
		}
		
		return ResultManager.createDataResult(teamVO);
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
		Admin admin = this.getCurrentOperator();
		
		team.setUuid(UUID.randomUUID().toString());
		team.setCreator(admin.getUuid());
		team.setCreatetime(new Timestamp(System.currentTimeMillis()));
		teamService.insertTeam(team);
		
		return ResultManager.createSuccessResult("保存成功！");
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
		Team te = this.teamService.get(team.getUuid());
		if(te == null){
			return ResultManager.createFailResult("赛事不存在！");
		}
		
		te.setName(team.getName());
		te.setIcon(team.getIcon());
		teamService.updateTeam(te);
		return ResultManager.createSuccessResult("保存成功！");
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
		Team team = this.teamService.get(uuid);
		if(team == null){
			return ResultManager.createFailResult("赛事不存在！");
		}
		teamService.deleteTeam(team);
		return ResultManager.createSuccessResult("删除成功！");
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
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", name);
		searchMap.put("team", team);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = infoPlayersService.getCountByParams(searchMap);
		List<InfoPlayers> list = infoPlayersService.getListByParams(searchMap);
		List<InfoPlayersVO> voList = new LinkedList<InfoPlayersVO>();
		for(InfoPlayers ip : list){
			InfoPlayersVO ipVO = new InfoPlayersVO(ip);
			voList.add(ipVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
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
		InfoPlayers ip = infoPlayersService.get(uuid);
		if(ip == null){
			return ResultManager.createFailResult("球员不存在");
		}
		
		InfoPlayersVO ipVO = new InfoPlayersVO(ip);
		return ResultManager.createDataResult(ipVO);
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
		InfoPlayers ip = infoPlayersService.get(uuid);
		if(ip == null){
			return ResultManager.createFailResult("球员不存在");
		}
		
		ip.setCnname(cnname);
		infoPlayersService.update(ip);
		return ResultManager.createSuccessResult("保存成功！");
	}
}
