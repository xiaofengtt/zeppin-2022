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
import cn.product.score.dao.FrontUserConcernDao;
import cn.product.score.dao.InfoMatchDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.FrontUserConcern;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontMatchService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.MatchDetailVO;
import cn.product.score.vo.front.MatchVO;

@Service("frontMatchService")
public class FrontMatchServiceImpl implements FrontMatchService{

	@Autowired
	private InfoMatchDao infoMatchDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private FrontUserConcernDao frontUserConcernDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String category = paramsMap.get("category") == null ? "" : paramsMap.get("category").toString();
		String team = paramsMap.get("team") == null ? "" : paramsMap.get("team").toString();
		String teamToTeam = paramsMap.get("teamToTeam") == null ? "" : paramsMap.get("teamToTeam").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
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
		
		Integer totalCount = infoMatchDao.getCountByParams(searchMap);
		List<InfoMatch> list = infoMatchDao.getListByParams(searchMap);
		List<MatchVO> voList = new LinkedList<MatchVO>();
		for(InfoMatch match : list){
			MatchVO matchVO = new MatchVO(match);
			
			Category cat = this.categoryDao.get(match.getCategory());
			matchVO.setCategoryName(cat.getName());
			
			Team homeTeam = this.teamDao.get(match.getHometeam());
			matchVO.setHometeamName(homeTeam.getName());
			if(!Utlity.checkStringNull(homeTeam.getIcon())){
				Resource resource = this.resourceDao.get(homeTeam.getIcon());
				if(resource != null){
					matchVO.setHometeamIconUrl(resource.getUrl());
				}
			}
			
			Team awayTeam = this.teamDao.get(match.getAwayteam());
			matchVO.setAwayteamName(awayTeam.getName());
			if(!Utlity.checkStringNull(awayTeam.getIcon())){
				Resource resource = this.resourceDao.get(awayTeam.getIcon());
				if(resource != null){
					matchVO.setAwayteamIconUrl(resource.getUrl());
				}
			}
			
			voList.add(matchVO);
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void concrenlist(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		
		//获取关注列表
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("user", fu);
		List<FrontUserConcern> listConcern = frontUserConcernDao.getListByParams(searchMap);
		StringBuilder sblist = new StringBuilder();
		if(listConcern != null && !listConcern.isEmpty()) {
			for(FrontUserConcern fuc : listConcern) {
				sblist.append("'").append(fuc.getTeam()).append("',");
			}
			sblist.delete(sblist.length() - 1, sblist.length());
		} else {
			result.setData(new LinkedList<MatchVO>());
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("success");
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setTotalResultCount(0);
			return;
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
		
		Integer totalCount = infoMatchDao.getCountByParams(searchMap);
		List<InfoMatch> list = infoMatchDao.getListByParams(searchMap);
		List<MatchVO> voList = new LinkedList<MatchVO>();
		for(InfoMatch match : list){
			MatchVO matchVO = new MatchVO(match);
			
			Category cat = this.categoryDao.get(match.getCategory());
			matchVO.setCategoryName(cat.getName());
			
			Team homeTeam = this.teamDao.get(match.getHometeam());
			matchVO.setHometeamName(homeTeam.getName());
			if(!Utlity.checkStringNull(homeTeam.getIcon())){
				Resource resource = this.resourceDao.get(homeTeam.getIcon());
				if(resource != null){
					matchVO.setHometeamIconUrl(resource.getUrl());
				}
			}
			
			Team awayTeam = this.teamDao.get(match.getAwayteam());
			matchVO.setAwayteamName(awayTeam.getName());
			if(!Utlity.checkStringNull(awayTeam.getIcon())){
				Resource resource = this.resourceDao.get(awayTeam.getIcon());
				if(resource != null){
					matchVO.setAwayteamIconUrl(resource.getUrl());
				}
			}
			
			voList.add(matchVO);
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		InfoMatch match = infoMatchDao.get(uuid);
		if(match == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("比赛不存在");
			return;
		}
		
		MatchDetailVO matchVO = new MatchDetailVO(match);
		
		Category cat = this.categoryDao.get(match.getCategory());
		matchVO.setCategoryName(cat.getName());
		
		Team homeTeam = this.teamDao.get(match.getHometeam());
		matchVO.setHometeamName(homeTeam.getName());
		if(!Utlity.checkStringNull(homeTeam.getIcon())){
			Resource resource = this.resourceDao.get(homeTeam.getIcon());
			if(resource != null){
				matchVO.setHometeamIconUrl(resource.getUrl());
			}
		}
		
		Team awayTeam = this.teamDao.get(match.getAwayteam());
		matchVO.setAwayteamName(awayTeam.getName());
		if(!Utlity.checkStringNull(awayTeam.getIcon())){
			Resource resource = this.resourceDao.get(awayTeam.getIcon());
			if(resource != null){
				matchVO.setAwayteamIconUrl(resource.getUrl());
			}
		}
		
		result.setData(matchVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

}
