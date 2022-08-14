/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserConcern;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserConcernService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.MatchDetailVO;
import cn.zeppin.product.score.vo.front.MatchVO;

/**
 * 赛事接口
 */

@Controller
@RequestMapping(value = "/front/match")
public class FrontMatchController extends BaseController{

	@Autowired
	private InfoMatchService infoMatchService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ResourceService resourceService;

	@Autowired
	private FrontUserConcernService frontUserConcernService;
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
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("team", team);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("status", status);
		if(!Utlity.checkStringNull(teamToTeam)){
			String[] teams = teamToTeam.split(",");
			if(teams.length == 2){
				searchMap.put("T2TA", teams[0]);
				searchMap.put("T2TB", teams[1]);
			}
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = infoMatchService.getCountByParams(searchMap);
		List<InfoMatch> list = infoMatchService.getListByParams(searchMap);
		List<MatchVO> voList = new LinkedList<MatchVO>();
		for(InfoMatch match : list){
			MatchVO matchVO = new MatchVO(match);
			
			Category cat = this.categoryService.get(match.getCategory());
			matchVO.setCategoryName(cat.getName());
			
			Team homeTeam = this.teamService.get(match.getHometeam());
			matchVO.setHometeamName(homeTeam.getName());
			if(!Utlity.checkStringNull(homeTeam.getIcon())){
				Resource resource = this.resourceService.get(homeTeam.getIcon());
				if(resource != null){
					matchVO.setHometeamIconUrl(resource.getUrl());
				}
			}
			
			Team awayTeam = this.teamService.get(match.getAwayteam());
			matchVO.setAwayteamName(awayTeam.getName());
			if(!Utlity.checkStringNull(awayTeam.getIcon())){
				Resource resource = this.resourceService.get(awayTeam.getIcon());
				if(resource != null){
					matchVO.setAwayteamIconUrl(resource.getUrl());
				}
			}
			
			voList.add(matchVO);
		}
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
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
		
		FrontUser fu = getFrontUser(request);
		
		//获取关注列表
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("user", fu.getUuid());
		List<FrontUserConcern> listConcern = frontUserConcernService.getListByParams(searchMap);
		StringBuilder sblist = new StringBuilder();
		if(listConcern != null && !listConcern.isEmpty()) {
			for(FrontUserConcern fuc : listConcern) {
				sblist.append("'").append(fuc.getTeam()).append("',");
			}
			sblist.delete(sblist.length() - 1, sblist.length());
		} else {
			return ResultManager.createDataResult(new LinkedList<MatchVO>(), pageNum, pageSize, 0);
		}
		
		//查询条件
		searchMap.clear();
		if(sblist.length() > 0) {
			searchMap.put("concern", sblist.toString());
		}
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("status", status);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = infoMatchService.getCountByParams(searchMap);
		List<InfoMatch> list = infoMatchService.getListByParams(searchMap);
		List<MatchVO> voList = new LinkedList<MatchVO>();
		for(InfoMatch match : list){
			MatchVO matchVO = new MatchVO(match);
			
			Category cat = this.categoryService.get(match.getCategory());
			matchVO.setCategoryName(cat.getName());
			
			Team homeTeam = this.teamService.get(match.getHometeam());
			matchVO.setHometeamName(homeTeam.getName());
			if(!Utlity.checkStringNull(homeTeam.getIcon())){
				Resource resource = this.resourceService.get(homeTeam.getIcon());
				if(resource != null){
					matchVO.setHometeamIconUrl(resource.getUrl());
				}
			}
			
			Team awayTeam = this.teamService.get(match.getAwayteam());
			matchVO.setAwayteamName(awayTeam.getName());
			if(!Utlity.checkStringNull(awayTeam.getIcon())){
				Resource resource = this.resourceService.get(awayTeam.getIcon());
				if(resource != null){
					matchVO.setAwayteamIconUrl(resource.getUrl());
				}
			}
			
			voList.add(matchVO);
		}
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
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
		InfoMatch match = infoMatchService.get(uuid);
		if(match == null){
			return ResultManager.createFailResult("比赛不存在");
		}
		
		MatchDetailVO matchVO = new MatchDetailVO(match);
		
		Category cat = this.categoryService.get(match.getCategory());
		matchVO.setCategoryName(cat.getName());
		
		Team homeTeam = this.teamService.get(match.getHometeam());
		matchVO.setHometeamName(homeTeam.getName());
		if(!Utlity.checkStringNull(homeTeam.getIcon())){
			Resource resource = this.resourceService.get(homeTeam.getIcon());
			if(resource != null){
				matchVO.setHometeamIconUrl(resource.getUrl());
			}
		}
		
		Team awayTeam = this.teamService.get(match.getAwayteam());
		matchVO.setAwayteamName(awayTeam.getName());
		if(!Utlity.checkStringNull(awayTeam.getIcon())){
			Resource resource = this.resourceService.get(awayTeam.getIcon());
			if(resource != null){
				matchVO.setAwayteamIconUrl(resource.getUrl());
			}
		}
		
		return ResultManager.createDataResult(matchVO);
	}
}
