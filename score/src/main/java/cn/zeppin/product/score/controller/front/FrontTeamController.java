/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.entity.InfoPlayers.InfoPlayersStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.InfoPlayersService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.CategoryVO;
import cn.zeppin.product.score.vo.front.InfoPlayersVO;
import cn.zeppin.product.score.vo.front.TeamDetailVO;

/**
 * 球队接口
 */

@Controller
@RequestMapping(value = "/front/team")
public class FrontTeamController extends BaseController{

	@Autowired
	private InfoPlayersService infoPlayersService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private FrontUserHistoryService frontUserHistoryService;
	
	@RequestMapping(value = "/getHistory", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result getHistory(String uuid) {
		List<FrontUserHistory> fuh = frontUserHistoryService.getListByParams(new HashMap<String, Object>());
		
		return ResultManager.createDataResult(fuh);
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
		Team team = teamService.get(uuid);
		if(team == null){
			return ResultManager.createFailResult("球队不存在");
		}
		
		TeamDetailVO teamVO = new TeamDetailVO(team);
		if(!Utlity.checkStringNull(team.getCategory())){
			String[] categorys = team.getCategory().split(",");
			for(String cate : categorys){
				Category category = this.categoryService.get(cate);
				if(category != null){
					CategoryVO categoryVO = new CategoryVO(category);
					teamVO.getCategoryList().add(categoryVO);
				}
			}
		}
		
		if(!Utlity.checkStringNull(team.getIcon())){
			Resource resource = this.resourceService.get(team.getIcon());
			if(resource != null){
				teamVO.setIconUrl(resource.getUrl());
			}
		}
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("team", uuid);
		searchMap.put("status", InfoPlayersStatus.NORMAL);
		searchMap.put("sort", "number+0");
		
		List<InfoPlayers> playerlist = infoPlayersService.getListByParams(searchMap);
		List<InfoPlayersVO> playerVOList = new LinkedList<InfoPlayersVO>();
		for(InfoPlayers ip : playerlist){
			InfoPlayersVO ipVO = new InfoPlayersVO(ip);
			playerVOList.add(ipVO);
		}
		teamVO.setPlayersList(playerVOList);
		
		return ResultManager.createDataResult(teamVO);
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
		InfoPlayers player = infoPlayersService.get(uuid);
		if(player == null){
			return ResultManager.createFailResult("球员不存在");
		}
		InfoPlayersVO vo = new InfoPlayersVO(player);
		return ResultManager.createDataResult(vo);
	}
}
