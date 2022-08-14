/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserConcern;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserConcernService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.FrontUserConcernVO;

/**
 * 关注接口
 */

@Controller
@RequestMapping(value = "/front/concren")
public class FrontUserConcernController extends BaseController{

	@Autowired
	private FrontUserConcernService frontUserConcernService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ResourceService resourceService;
	
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
		FrontUser fu = getFrontUser(request);
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("user", fu.getUuid());
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserConcernService.getCountByParams(searchMap);
		List<FrontUserConcern> list = frontUserConcernService.getListByParams(searchMap);
		List<FrontUserConcernVO> voList = new LinkedList<FrontUserConcernVO>();
		for(FrontUserConcern fuc : list){
			FrontUserConcernVO fucVO = new FrontUserConcernVO(fuc);
			
			Team team = this.teamService.get(fuc.getTeam());
			fucVO.setTeamName(team.getName());
			if(!Utlity.checkStringNull(team.getIcon())){
				Resource resource = this.resourceService.get(team.getIcon());
				if(resource != null){
					fucVO.setTeamIconUrl(resource.getUrl());
				}
			}
			voList.add(fucVO);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
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
		FrontUser fu = getFrontUser(request);
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("user", fu.getUuid());
		searchMap.put("team", team);
		List<FrontUserConcern> list = frontUserConcernService.getListByParams(searchMap);
		if(list.size() > 0){
			return ResultManager.createDataResult(list.get(0));
		}
		return ResultManager.createDataResult(new FrontUserConcern());
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
		FrontUser fu = getFrontUser(request);
		
		Team t = this.teamService.get(team);
		if(t == null){
			return ResultManager.createFailResult("球队不存在！");
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("user", fu.getUuid());
		searchMap.put("team", team);
		Integer count = this.frontUserConcernService.getCountByParams(searchMap);
		if(count > 0){
			return ResultManager.createFailResult("球队已关注！");
		}
		
		FrontUserConcern fuc = new FrontUserConcern();
		fuc.setUuid(UUID.randomUUID().toString());
		fuc.setUser(fu.getUuid());
		fuc.setTeam(team);
		fuc.setCategory(t.getCategory());
		fuc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.frontUserConcernService.insert(fuc);
		return ResultManager.createDataResult(fuc);
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
		FrontUser fu = getFrontUser(request);
		
		FrontUserConcern fuc = this.frontUserConcernService.get(userConcern);
		if(fuc == null || !fuc.getUser().equals(fu.getUuid())){
			return ResultManager.createFailResult("未找到关注数据！");
		}
		
		this.frontUserConcernService.delete(userConcern);
		return ResultManager.createSuccessResult("取消关注成功!");
	}
}
