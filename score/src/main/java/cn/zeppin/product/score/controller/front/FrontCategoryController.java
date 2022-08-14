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
import cn.zeppin.product.score.entity.Category.CategoryStatus;
import cn.zeppin.product.score.entity.CategoryStanding;
import cn.zeppin.product.score.entity.CategoryTopscore;
import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.CategoryStandingService;
import cn.zeppin.product.score.service.CategoryTopscoreService;
import cn.zeppin.product.score.service.InfoPlayersService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.front.CategoryStandingVO;
import cn.zeppin.product.score.vo.front.CategoryTopscoreVO;
import cn.zeppin.product.score.vo.front.CategoryVO;
import cn.zeppin.product.score.vo.front.TeamVO;

/**
 * 分类管理
 */

@Controller
@RequestMapping(value = "/front/category")
public class FrontCategoryController extends BaseController{

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private InfoPlayersService infoPlayersService;
	
	@Autowired
	private CategoryStandingService categoryStandingService;
	
	@Autowired
	private CategoryTopscoreService categoryTopscoreService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 赛事分类列表
	 * @param name
	 * @param shortname
	 * @param parent
	 * @param scode
	 * @param level
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "名称", type = DataType.STRING)
	@ActionParam(key = "shortname", message = "英文名称", type = DataType.STRING)
	@ActionParam(key = "parent", message = "父级", type = DataType.STRING)
	@ActionParam(key = "scode", message = "编码", type = DataType.STRING)
	@ActionParam(key = "level", message = "级别", type = DataType.NUMBER)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(Category category, Integer pageNum, Integer pageSize) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", category.getName());
		searchMap.put("shortname", category.getShortname());
		searchMap.put("parent", category.getParent());
		searchMap.put("scode", category.getScode());
		searchMap.put("level", category.getLevel());
		searchMap.put("status", CategoryStatus.NORMAL);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "level,scode");
		
		List<Category> list = categoryService.getListByParams(searchMap);
		List<CategoryVO> voList = new LinkedList<CategoryVO>();
		for(Category cate : list){
			CategoryVO cateVO = new CategoryVO(cate);
			if(cate.getIcon() != null){
				Resource res = resourceService.get(cate.getIcon());
				if(res != null){
					cateVO.setIconUrl(res.getUrl());
				}
			}
			addCategory(voList, cateVO);
		}
		
		return ResultManager.createDataResult(voList);
	}
	
	/**
	 * 赛事分类详细信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		Category category = categoryService.get(uuid);
		if(category == null){
			return ResultManager.createFailResult("分类不存在");
		}
		if(!CategoryStatus.NORMAL.equals(category.getStatus())){
			return ResultManager.createFailResult("分类处于未开放状态");
		}
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("scode", category.getScode());
		searchMap.put("status", CategoryStatus.NORMAL);
		searchMap.put("sort", "level,scode");
		
		List<Category> list = categoryService.getListByParams(searchMap);
		List<CategoryVO> voList = new LinkedList<CategoryVO>();
		for(Category cate : list){
			CategoryVO cateVO = new CategoryVO(cate);
			if(cate.getIcon() != null){
				Resource res = resourceService.get(cate.getIcon());
				if(res != null){
					cateVO.setIconUrl(res.getUrl());
				}
			}
			addCategory(voList, cateVO);
		}
		
		return ResultManager.createDataResult(voList.get(0));
	}
	
	/**
	 * 积分榜
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/standingList", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "赛事", type = DataType.STRING, required = true)
	@ResponseBody
	public Result standingList(String category) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("sort", "round, place");
		
		List<CategoryStanding> list = categoryStandingService.getListByParams(searchMap);
		List<CategoryStandingVO> voList = new LinkedList<CategoryStandingVO>();
		for(CategoryStanding cs : list){
			CategoryStandingVO csVO = new CategoryStandingVO(cs);
			
			Team team = this.teamService.get(cs.getTeam());
			csVO.setTeamName(team.getName());
			
			if(team.getIcon() != null){
				Resource icon = this.resourceService.get(team.getIcon());
				if(icon != null){
					csVO.setTeamIconUrl(icon.getUrl());
				}
			}
			
			voList.add(csVO);
		}
		
		return ResultManager.createDataResult(voList);
	}
	
	/**
	 * 射手榜
	 * @param category
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/topscoreList", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "赛事", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message = "排序", type = DataType.STRING)
	@ResponseBody
	public Result topscoreList(String category, String sort) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("sort", sort);
		
		List<CategoryTopscore> list = categoryTopscoreService.getListByParams(searchMap);
		List<CategoryTopscoreVO> voList = new LinkedList<CategoryTopscoreVO>();
		for(CategoryTopscore cs : list){
			CategoryTopscoreVO csVO = new CategoryTopscoreVO(cs);
			
			Team team = this.teamService.get(cs.getTeam());
			csVO.setTeamName(team.getName());
			
			if(!Utlity.checkStringNull(cs.getPlayer())){
				InfoPlayers ip = this.infoPlayersService.get(cs.getPlayer());
				if(ip != null){
					csVO.setPlayerName(ip.getCnname());
				}
			}else{
				csVO.setPlayerName(cs.getPlayername());
			}
			
			voList.add(csVO);
		}
		
		return ResultManager.createDataResult(voList);
	}
	
	//多级分类添加
	static Boolean addCategory(List<CategoryVO> voList, CategoryVO vo){
		Boolean flag = false;
		for(CategoryVO parent : voList){
			if(vo.getScode().startsWith(parent.getScode())){
				flag = true;
				addCategory(parent.getChildren(), vo);
				break;
			}
		}
		if(!flag){
			voList.add(vo);
		}
		return flag;
	}
	
	/**
	 * 球队列表
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/teamList", method = RequestMethod.GET)
	@ActionParam(key = "category", message = "分类", type = DataType.STRING)
	@ResponseBody
	public Result teamList(String category) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		
		Integer totalCount = teamService.getCountByParams(searchMap);
		List<Team> list = teamService.getListByParams(searchMap);
		List<TeamVO> voList = new LinkedList<TeamVO>();
		for(Team team : list){
			TeamVO teamVO = new TeamVO(team);
			if(!Utlity.checkStringNull(team.getIcon())){
				Resource resource = this.resourceService.get(team.getIcon());
				if(resource != null){
					teamVO.setIconUrl(resource.getUrl());
				}
			}
			voList.add(teamVO);
		}
		
		return ResultManager.createDataResult(voList, totalCount);
	}
}
