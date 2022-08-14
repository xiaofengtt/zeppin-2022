package cn.product.score.service.front.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.CategoryStandingDao;
import cn.product.score.dao.CategoryTopscoreDao;
import cn.product.score.dao.InfoPlayersDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.Category.CategoryStatus;
import cn.product.score.entity.CategoryStanding;
import cn.product.score.entity.CategoryTopscore;
import cn.product.score.entity.InfoPlayers;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontCategoryService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.CategoryStandingVO;
import cn.product.score.vo.front.CategoryTopscoreVO;
import cn.product.score.vo.front.CategoryVO;
import cn.product.score.vo.front.TeamVO;

@Service("frontCategoryService")
public class FrontCategoryServiceImpl implements FrontCategoryService{
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private InfoPlayersDao infoPlayersDao;
	
	@Autowired
	private CategoryStandingDao categoryStandingDao;
	
	@Autowired
	private CategoryTopscoreDao categoryTopscoreDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		Category category = (Category)paramsMap.get("category");
		
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
		
		List<Category> list = categoryDao.getListByParams(searchMap);
		List<CategoryVO> voList = new LinkedList<CategoryVO>();
		for(Category cate : list){
			CategoryVO cateVO = new CategoryVO(cate);
			if(cate.getIcon() != null){
				Resource res = resourceDao.get(cate.getIcon());
				if(res != null){
					cateVO.setIconUrl(res.getUrl());
				}
			}
			addCategory(voList, cateVO);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		Category category = categoryDao.get(uuid);
		if(category == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("分类不存在");
			return;
		}
		if(!CategoryStatus.NORMAL.equals(category.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("分类处于未开放状态");
			return;
		}
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("scode", category.getScode());
		searchMap.put("status", CategoryStatus.NORMAL);
		searchMap.put("sort", "level,scode");
		
		List<Category> list = categoryDao.getListByParams(searchMap);
		List<CategoryVO> voList = new LinkedList<CategoryVO>();
		for(Category cate : list){
			CategoryVO cateVO = new CategoryVO(cate);
			if(cate.getIcon() != null){
				Resource res = resourceDao.get(cate.getIcon());
				if(res != null){
					cateVO.setIconUrl(res.getUrl());
				}
			}
			addCategory(voList, cateVO);
		}
		
		result.setData(voList.get(0));
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void standingList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String category = paramsMap.get("category") == null ? "" : paramsMap.get("category").toString();
    	
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("sort", "round, place");
		
		List<CategoryStanding> list = categoryStandingDao.getListByParams(searchMap);
		List<CategoryStandingVO> voList = new LinkedList<CategoryStandingVO>();
		for(CategoryStanding cs : list){
			CategoryStandingVO csVO = new CategoryStandingVO(cs);
			
			Team team = this.teamDao.get(cs.getTeam());
			csVO.setTeamName(team.getName());
			
			if(team.getIcon() != null){
				Resource icon = this.resourceDao.get(team.getIcon());
				if(icon != null){
					csVO.setTeamIconUrl(icon.getUrl());
				}
			}
			
			voList.add(csVO);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void topscoreList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String category = paramsMap.get("category") == null ? "" : paramsMap.get("category").toString();
    	String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
    	
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("sort", sort);
		
		List<CategoryTopscore> list = categoryTopscoreDao.getListByParams(searchMap);
		List<CategoryTopscoreVO> voList = new LinkedList<CategoryTopscoreVO>();
		for(CategoryTopscore cs : list){
			CategoryTopscoreVO csVO = new CategoryTopscoreVO(cs);
			
			Team team = this.teamDao.get(cs.getTeam());
			csVO.setTeamName(team.getName());
			
			if(!Utlity.checkStringNull(cs.getPlayer())){
				InfoPlayers ip = this.infoPlayersDao.get(cs.getPlayer());
				if(ip != null){
					csVO.setPlayerName(ip.getCnname());
				}
			}else{
				csVO.setPlayerName(cs.getPlayername());
			}
			
			voList.add(csVO);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void teamList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String category = paramsMap.get("category") == null ? "" : paramsMap.get("category").toString();
    	
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		
		Integer totalCount = teamDao.getCountByParams(searchMap);
		List<Team> list = teamDao.getListByParams(searchMap);
		List<TeamVO> voList = new LinkedList<TeamVO>();
		for(Team team : list){
			TeamVO teamVO = new TeamVO(team);
			if(!Utlity.checkStringNull(team.getIcon())){
				Resource resource = this.resourceDao.get(team.getIcon());
				if(resource != null){
					teamVO.setIconUrl(resource.getUrl());
				}
			}
			voList.add(teamVO);
		}
		
		result.setData(voList);
		result.setTotalResultCount(totalCount);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
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
}
