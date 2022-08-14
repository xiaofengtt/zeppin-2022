/**
 * 
 */
package cn.zeppin.product.score.controller.back;

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
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.InfoMatch.InfoMatchStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.MatchVO;

/**
 * 赛事接口
 */

@Controller
@RequestMapping(value = "/back/match")
public class MatchController extends BaseController{

	@Autowired
	private InfoMatchService infoMatchService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ResourceService resourceService;
	
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
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("hometeam", hometeam);
		searchMap.put("awayteam", awayteam);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("status", InfoMatchStatus.UNSTART);
		
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
}
