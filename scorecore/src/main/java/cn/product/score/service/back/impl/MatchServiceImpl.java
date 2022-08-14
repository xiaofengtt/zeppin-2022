package cn.product.score.service.back.impl;

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
import cn.product.score.dao.InfoMatchDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.InfoMatch.InfoMatchStatus;
import cn.product.score.service.back.MatchService;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.MatchVO;

@Service("matchService")
public class MatchServiceImpl implements MatchService{
	

	@Autowired
	private InfoMatchDao infoMatchDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String category = paramsMap.get("category") == null ? "" : paramsMap.get("category").toString();
		String hometeam = paramsMap.get("hometeam") == null ? "" : paramsMap.get("hometeam").toString();
		String awayteam = paramsMap.get("awayteam") == null ? "" : paramsMap.get("awayteam").toString();
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
//		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
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
	
}
