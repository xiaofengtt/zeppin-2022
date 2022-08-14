package cn.product.score.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.GuessingMatchDao;
import cn.product.score.dao.GuessingMatchOddsDao;
import cn.product.score.dao.GuessingMatchTypeDao;
import cn.product.score.dao.InfoMatchDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.Category;
import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatch.GuessingMatchStatus;
import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.GuessingMatchType;
import cn.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.product.score.service.back.GuessingMatchService;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.GuessingMatchVO;
import cn.product.score.vo.back.MatchVO;
import cn.product.score.vo.back.StatusCountVO;

@Service("guessingMatchService")
public class GuessingMatchServiceImpl implements GuessingMatchService{
	
	@Autowired
	private GuessingMatchDao guessingMatchDao;
	
	@Autowired
    private GuessingMatchTypeDao guessingMatchTypeDao;
	
	@Autowired
    private GuessingMatchOddsDao guessingMatchOddsDao;
	
	@Autowired
    private CategoryDao categoryDao;
	
	@Autowired
    private InfoMatchDao infoMatchDao;
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
    private AdminDao adminDao;

	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		GuessingMatch gm = guessingMatchDao.get(uuid);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("赛事不存在");
			return;
		}
		
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
		}
		
		Admin creator = this.adminDao.get(gm.getCreator());
		if(creator != null){
			gmvo.setCreator(creator.getRealname());
		}
		
		result.setData(gmvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		if(!Utlity.checkStringNull(status) && !"all".equals(status)){
			paramsls.put("status", status);
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = guessingMatchDao.getCountByParams(paramsls);
		List<GuessingMatch> gmList = guessingMatchDao.getListByParams(paramsls);
		
		List<GuessingMatchVO> voList = new ArrayList<GuessingMatchVO>();
		for(GuessingMatch gm : gmList){
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
			}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("guessingMatch", match.getUuid());
			List<GuessingMatchType> gmtList = this.guessingMatchTypeDao.getListByParams(searchMap);
			Map<String, Object> guessingTypeMap = new HashMap<String, Object>();
			for(GuessingMatchType gmt : gmtList){
				guessingTypeMap.put(gmt.getType(), true);
			}
			gmvo.setGuessingTypeArray(guessingTypeMap.keySet().toArray());
			
			Admin creator = this.adminDao.get(gm.getCreator());
			if(creator != null){
				gmvo.setCreator(creator.getRealname());
			}
			voList.add(gmvo);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		GuessingMatch guessingMatch = (GuessingMatch) paramsMap.get("guessingMatch");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		InfoMatch im = this.infoMatchDao.get(guessingMatch.getInfoMatch());
		if(im == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("赛事不存在");
			return;
		}
		
		guessingMatch.setUuid(UUID.randomUUID().toString());
		guessingMatch.setTime(im.getTime());
		guessingMatch.setCategory(im.getCategory());
		guessingMatch.setStatus(GuessingMatchStatus.NORMAL);
		guessingMatch.setSort(0);
		guessingMatch.setCreator(admin);
		guessingMatch.setCreatetime(new Timestamp(System.currentTimeMillis()));
		guessingMatchDao.insert(guessingMatch);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
		result.setData(guessingMatch);
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
		GuessingMatch gm = guessingMatchDao.get(uuid);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("赛事不存在");
			return;
		}
		
		if(!GuessingMatchStatus.NORMAL.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("赛事无法删除！");
			return;
		}
		
		gm.setStatus(GuessingMatchStatus.DELETE);
		this.guessingMatchDao.deleteGuessingMatch(gm);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}

	@Override
	public void finish(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String[] rightArray = (String[]) paramsMap.get("rightArray");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		GuessingMatch gm = guessingMatchDao.get(uuid);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜赛事不存在");
			return;
		}
		
		if(!GuessingMatchStatus.WAITING.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜赛事现在无法结算！");
			return;
		}
		
		List<GuessingMatchOdds> rightList = new ArrayList<GuessingMatchOdds>();
		for(String gmoUuid : rightArray){
			GuessingMatchOdds gmo = this.guessingMatchOddsDao.get(gmoUuid);
			rightList.add(gmo);
		}
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("guessingMatch", uuid);
		List<GuessingMatchOdds> gmoList = this.guessingMatchOddsDao.getListByParams(paramsls);
		
		for(GuessingMatchOdds gmo : gmoList){
			String gmoSpread = gmo.getSpread() == null ? "" : gmo.getSpread();
			
			Boolean isRight = false;
			for(GuessingMatchOdds right : rightList){
				String rightSpread = right.getSpread() == null ? "" : right.getSpread();
				if(gmo.getGuessingMatchType().equals(right.getGuessingMatchType()) 
						&& gmoSpread.equals(rightSpread) && gmo.getResult().equals(right.getResult())){
					isRight = true;
					break;
				}
			}
			gmo.setIsRight(isRight);
		}
		
		paramsls.put("status", GuessingMatchTypeStatus.PUBLISH);
		List<GuessingMatchType> gmtList = this.guessingMatchTypeDao.getListByParams(paramsls);
		
		for(GuessingMatchType gmt : gmtList){
			gmt.setStatus(GuessingMatchTypeStatus.FINISHED);
		}
		
		gm.setStatus(GuessingMatchStatus.FINISHED);
		gm.setOperator(admin);
		gm.setOperatetime(new Timestamp(System.currentTimeMillis()));
		
		this.guessingMatchDao.finishGuessingMatch(gm, gmtList, gmoList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("竞猜结果录入成功！");
	}

	@Override
	public void statusList(InputParams params, DataResult<Object> result) {
		List<StatusCountVO> list = guessingMatchDao.getStatusList();
		result.setStatus(ResultStatusType.SUCCESS);
		result.setData(list);
		result.setTotalResultCount(list.size());
	}
	
}
