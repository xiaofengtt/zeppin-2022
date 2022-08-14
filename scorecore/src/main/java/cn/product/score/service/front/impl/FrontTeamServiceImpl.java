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
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.InfoPlayersDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.InfoPlayers;
import cn.product.score.entity.InfoPlayers.InfoPlayersStatus;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontTeamService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.CategoryVO;
import cn.product.score.vo.front.InfoPlayersVO;
import cn.product.score.vo.front.TeamDetailVO;

@Service("frontTeamService")
public class FrontTeamServiceImpl implements FrontTeamService{

	@Autowired
	private InfoPlayersDao infoPlayersDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Override
	public void getHistory(InputParams params, DataResult<Object> result) {
//		Map<String, Object> paramsMap = params.getParams();
//    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		List<FrontUserHistory> fuh = frontUserHistoryDao.getListByParams(new HashMap<String, Object>());
		
		result.setData(fuh);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Team team = teamDao.get(uuid);
		if(team == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球队不存在");
			return;
		}
		
		TeamDetailVO teamVO = new TeamDetailVO(team);
		if(!Utlity.checkStringNull(team.getCategory())){
			String[] categorys = team.getCategory().split(",");
			for(String cate : categorys){
				Category category = this.categoryDao.get(cate);
				if(category != null){
					CategoryVO categoryVO = new CategoryVO(category);
					teamVO.getCategoryList().add(categoryVO);
				}
			}
		}
		
		if(!Utlity.checkStringNull(team.getIcon())){
			Resource resource = this.resourceDao.get(team.getIcon());
			if(resource != null){
				teamVO.setIconUrl(resource.getUrl());
			}
		}
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("team", uuid);
		searchMap.put("status", InfoPlayersStatus.NORMAL);
		searchMap.put("sort", "number+0");
		
		List<InfoPlayers> playerlist = infoPlayersDao.getListByParams(searchMap);
		List<InfoPlayersVO> playerVOList = new LinkedList<InfoPlayersVO>();
		for(InfoPlayers ip : playerlist){
			InfoPlayersVO ipVO = new InfoPlayersVO(ip);
			playerVOList.add(ipVO);
		}
		teamVO.setPlayersList(playerVOList);
		
		result.setData(teamVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void playerGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		InfoPlayers player = infoPlayersDao.get(uuid);
		if(player == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球员不存在");
			return;
		}
		InfoPlayersVO vo = new InfoPlayersVO(player);
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}
	

}
