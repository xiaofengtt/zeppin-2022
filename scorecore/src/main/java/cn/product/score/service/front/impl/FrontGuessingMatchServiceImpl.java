package cn.product.score.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.GuessingMatchDao;
import cn.product.score.dao.GuessingMatchOddsDao;
import cn.product.score.dao.GuessingMatchTypeDao;
import cn.product.score.dao.InfoMatchDao;
import cn.product.score.dao.MobileCodeDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Category;
import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.GuessingMatchOdds.GuessingMatchOddsStatus;
import cn.product.score.entity.GuessingMatchType;
import cn.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.Resource;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontGuessingMatchService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.CategoryCountVO;
import cn.product.score.vo.front.GuessingMatchTypeVO;
import cn.product.score.vo.front.GuessingMatchVO;
import cn.product.score.vo.front.MatchVO;

@Service("frontGuessingMatchService")
public class FrontGuessingMatchServiceImpl implements FrontGuessingMatchService{
	
	@Autowired
    private CategoryDao categoryDao;
	
	@Autowired
    private InfoMatchDao infoMatchDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Autowired
	private GuessingMatchOddsDao guessingMatchOddsDao;

	@Autowired
	private GuessingMatchDao guessingMatchDao;
	
	@Autowired
	private GuessingMatchTypeDao guessingMatchTypeDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
    	String[] category = (String[]) paramsMap.get("category");
    	String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
    	
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("status", GuessingMatchTypeStatus.PUBLISH);
		if(category != null){
			String cateStr = "(";
			for(String cate : category){
				cateStr = cateStr + "'" + cate + "',";
			}
			if(category != null && category.length > 0){
				cateStr = cateStr.substring(0, cateStr.length() - 1);
			}
			cateStr = cateStr + ")";
			paramsls.put("category", cateStr);
		}
		paramsls.put("type", type);
		paramsls.put("sort", sort);
		paramsls.put("deadline", "1");
		
		List<GuessingMatchType> gmtList = guessingMatchTypeDao.getListByParams(paramsls);
		SystemParam sp = this.systemParamDao.get(SystemParamKey.BET_USER_RELOAD_DURATION);
		
		List<GuessingMatchVO> voList = new ArrayList<GuessingMatchVO>();
		for(GuessingMatchType gmt : gmtList){
			GuessingMatch gm = guessingMatchDao.get(gmt.getGuessingMatch());
			GuessingMatchVO gmvo = new GuessingMatchVO(gm);
			
			InfoMatch match = this.infoMatchDao.get(gm.getInfoMatch());
			if(match != null){
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
				gmvo.setMatchDetail(matchVO);
				
				List<GuessingMatchTypeVO> gmtvoList = new ArrayList<GuessingMatchTypeVO>();
				GuessingMatchTypeVO gmtvo = new GuessingMatchTypeVO(gmt);
				
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("guessingMatchType", gmt.getUuid());
				searchMap.put("status",GuessingMatchOddsStatus.NORMAL);
				searchMap.put("sort", "spread, result");
				List<GuessingMatchOdds> oddsList = this.guessingMatchOddsDao.getListByParams(searchMap);
				gmtvo.setOddsList(oddsList);
				gmtvoList.add(gmtvo);
				
				gmvo.setOddsDetail(gmtvoList);
				gmvo.setReloadDuration(sp.getParamValue());
				
				voList.add(gmvo);
			}
		}
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void categoryList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
    	
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("type", type);
		
		List<CategoryCountVO> voList = this.guessingMatchTypeDao.getCategoryList(searchMap);
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
