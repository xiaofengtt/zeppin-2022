package cn.product.score.service.back.impl;

import java.math.BigDecimal;
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
import cn.product.score.dao.FrontUserBetDetailDao;
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
import cn.product.score.entity.GuessingMatchOdds.GuessingMatchOddsStatus;
import cn.product.score.entity.GuessingMatchType;
import cn.product.score.entity.GuessingMatchType.GuessingMatchTypeStatus;
import cn.product.score.service.back.GuessingMatchOddsService;
import cn.product.score.entity.InfoMatch;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.util.Utlity;
import cn.product.score.vo.back.BetSumVO;
import cn.product.score.vo.back.GuessingMatchOddsChangeVO;
import cn.product.score.vo.back.GuessingMatchTypeVO;
import cn.product.score.vo.back.GuessingMatchVO;
import cn.product.score.vo.back.MatchVO;

@Service("guessingMatchOddsDao")
public class GuessingMatchOddsServiceImpl implements GuessingMatchOddsService{
	
	@Autowired
	private GuessingMatchOddsDao guessingMatchOddsDao;
	@Autowired
    private GuessingMatchDao guessingMatchDao;
	
	@Autowired
    private GuessingMatchTypeDao guessingMatchTypeDao;
	
	@Autowired
    private FrontUserBetDetailDao frontUserBetDetailDao;
	
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
	public void controlList(InputParams params, DataResult<Object> result) {
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", GuessingMatchStatus.PUBLISH);
		List<GuessingMatch> gmList = guessingMatchDao.getListByParams(searchMap);
		
		List<GuessingMatchVO> gmvoList = new ArrayList<GuessingMatchVO>();
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
			
			Admin creator = this.adminDao.get(gm.getCreator());
			if(creator != null){
				gmvo.setCreator(creator.getRealname());
			}
			
			Map<String, Object> paramsls = new HashMap<String, Object>();
			paramsls.put("guessingMatch", gm.getUuid());
			List<GuessingMatchTypeVO> gmtvoList = new ArrayList<GuessingMatchTypeVO>();
			List<GuessingMatchType> gmtList = this.guessingMatchTypeDao.getListByParams(paramsls);
			
			for(GuessingMatchType gmt : gmtList){
				GuessingMatchTypeVO gmtvo = new GuessingMatchTypeVO(gmt);
				
				paramsls.clear();
				paramsls.put("guessingMatchType", gmt.getUuid());
				paramsls.put("status", GuessingMatchOddsStatus.NORMAL);
				paramsls.put("sort", "spread, result");
				List<GuessingMatchOdds> oddsList = this.guessingMatchOddsDao.getListByParams(paramsls);
				gmtvo.setOddsList(oddsList);
				
				gmtvoList.add(gmtvo);
			}
			
			gmvo.setOddsDetail(gmtvoList);
			gmvoList.add(gmvo);
		}
		result.setData(gmvoList);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String guessingMatch = paramsMap.get("guessingMatch") == null ? "" : paramsMap.get("guessingMatch").toString();
		
		GuessingMatch gm = guessingMatchDao.get(guessingMatch);
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("赛事不存在！");
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
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("guessingMatch", guessingMatch);
		List<GuessingMatchTypeVO> gmtvoList = new ArrayList<GuessingMatchTypeVO>();
		List<GuessingMatchType> gmtList = this.guessingMatchTypeDao.getListByParams(paramsls);
		
		for(GuessingMatchType gmt : gmtList){
			GuessingMatchTypeVO gmtvo = new GuessingMatchTypeVO(gmt);
			
			paramsls.clear();
			paramsls.put("guessingMatchType", gmt.getUuid());
			paramsls.put("status", GuessingMatchOddsStatus.NORMAL);
			paramsls.put("sort", "spread, result");
			List<GuessingMatchOdds> oddsList = this.guessingMatchOddsDao.getListByParams(paramsls);
			gmtvo.setOddsList(oddsList);
			
			gmtvoList.add(gmtvo);
		}
		
		gmvo.setOddsDetail(gmtvoList);
		result.setData(gmvo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void getBetSum(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String guessingMatch = paramsMap.get("guessingMatch") == null ? "" : paramsMap.get("guessingMatch").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("guessingMatch", guessingMatch);
		
		List<BetSumVO> betSumList = this.frontUserBetDetailDao.getBetSumList(paramsls);
		result.setData(betSumList);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void addType(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		GuessingMatchType guessingMatchType = (GuessingMatchType) paramsMap.get("guessingMatchType");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String betEndtime = paramsMap.get("betEndtime") == null ? "" : paramsMap.get("betEndtime").toString();
		String[] odds = (String[]) paramsMap.get("odds");
		
		GuessingMatch gm = this.guessingMatchDao.get(guessingMatchType.getGuessingMatch());
		if(gm == null || GuessingMatchStatus.DELETE.equals(gm.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜赛事不存在！");
			return;
		}
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("guessingMatch", gm.getUuid());
		paramsls.put("type", guessingMatchType.getType());
		paramsls.put("oddsType", guessingMatchType.getOddsType());
		Integer count = this.guessingMatchTypeDao.getCountByParams(paramsls);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("当前赛事已有该类型赔率信息！");
			return;
		}
		
		guessingMatchType.setUuid(UUID.randomUUID().toString());
		guessingMatchType.setEndtime(Timestamp.valueOf(betEndtime));
		guessingMatchType.setStatus(GuessingMatchTypeStatus.NORMAL);
		guessingMatchType.setCreator(admin);
		guessingMatchType.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		List<GuessingMatchOdds> oddsList = new ArrayList<GuessingMatchOdds>();
		for(String odd : odds){
			String[] oddDetails = odd.split("@_@");
			if(oddDetails.length != 3){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("赔率信息不正确！");
				return;
			}
			String spread = Utlity.checkStringNull(oddDetails[0]) ? null : oddDetails[0];
			String results = oddDetails[1];
			String oddsValue = oddDetails[2];
			if(!Utlity.isNumeric(oddsValue)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("赔率值信息不正确！");
				return;
			}
			
			GuessingMatchOdds gmo = new GuessingMatchOdds();
			gmo.setUuid(UUID.randomUUID().toString());
			gmo.setGuessingMatch(guessingMatchType.getGuessingMatch());
			gmo.setGuessingMatchType(guessingMatchType.getUuid());
			gmo.setSpread(spread == null ? "" : spread);
			gmo.setResult(results);
			gmo.setOdds(BigDecimal.valueOf(Double.valueOf(oddsValue)));
			gmo.setStatus(GuessingMatchOddsStatus.NORMAL);
			gmo.setCreator(guessingMatchType.getCreator());
			gmo.setCreatetime(guessingMatchType.getCreatetime());
			
			oddsList.add(gmo);
		}
		
		guessingMatchTypeDao.addGuessingMatchType(guessingMatchType, oddsList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void editType(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		GuessingMatchType guessingMatchType = (GuessingMatchType) paramsMap.get("guessingMatchType");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String betEndtime = paramsMap.get("betEndtime") == null ? "" : paramsMap.get("betEndtime").toString();
		String[] odds = (String[]) paramsMap.get("odds");
		
		GuessingMatchType gmt = guessingMatchTypeDao.get(guessingMatchType.getUuid());
		if(gmt == null  || GuessingMatchTypeStatus.DELETE.equals(gmt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜类型不存在！");
			return;
		}
		
		if(GuessingMatchTypeStatus.PUBLISH.equals(gmt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜类型已发布！");
			return;
		}
		
		gmt.setOddsType(guessingMatchType.getOddsType());
		gmt.setType(guessingMatchType.getType());
		gmt.setMaxMoney(guessingMatchType.getMaxMoney());
		gmt.setFlagSingle(guessingMatchType.getFlagSingle());
		gmt.setEndtime(Timestamp.valueOf(betEndtime));
		gmt.setCreator(admin);
		gmt.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		List<GuessingMatchOdds> oddsList = new ArrayList<GuessingMatchOdds>();
		for(String odd : odds){
			String[] oddDetails = odd.split("@_@");
			if(oddDetails.length != 3){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("赔率信息不正确！");
				return;
			}
			String spread = Utlity.checkStringNull(oddDetails[0]) ? null : oddDetails[0];
			String results = oddDetails[1];
			String oddsValue = oddDetails[2];
			if(!Utlity.isNumeric(oddsValue)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("赔率值信息不正确！");
				return;
			}
			
			GuessingMatchOdds gmo = new GuessingMatchOdds();
			gmo.setUuid(UUID.randomUUID().toString());
			gmo.setGuessingMatch(gmt.getGuessingMatch());
			gmo.setGuessingMatchType(gmt.getUuid());
			gmo.setSpread(spread == null ? "" : spread);
			gmo.setResult(results);
			gmo.setOdds(BigDecimal.valueOf(Double.valueOf(oddsValue)));
			gmo.setStatus(GuessingMatchOddsStatus.NORMAL);
			gmo.setCreator(gmt.getCreator());
			gmo.setCreatetime(gmt.getCreatetime());
			
			oddsList.add(gmo);
		}
		
		guessingMatchTypeDao.updateGuessingMatchType(gmt, oddsList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void publishType(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		GuessingMatchType gmt = guessingMatchTypeDao.get(uuid);
		if(gmt == null || GuessingMatchTypeStatus.DELETE.equals(gmt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜类型不存在！");
			return;
		}
		
		if(!GuessingMatchStatus.NORMAL.equals(gmt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("无法再次发布！");
			return;
		}
		GuessingMatch gm = guessingMatchDao.get(gmt.getGuessingMatch());
		gmt.setStatus(GuessingMatchTypeStatus.PUBLISH);
		gm.setStatus(GuessingMatchStatus.PUBLISH);
		
		guessingMatchTypeDao.publishGuessingMatchType(gmt, gm);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void deleteType(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		GuessingMatchType gmt = guessingMatchTypeDao.get(uuid);
		if(gmt == null || GuessingMatchTypeStatus.DELETE.equals(gmt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜类型不存在！");
			return;
		}
		
		if(!GuessingMatchStatus.NORMAL.equals(gmt.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该类型无法删除！");
			return;
		}
		gmt.setStatus(GuessingMatchTypeStatus.DELETE);
		
		guessingMatchTypeDao.deleteGuessingMatchType(gmt);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void historyList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		GuessingMatchOdds gmo = guessingMatchOddsDao.get(uuid);
		if(gmo == null || GuessingMatchOddsStatus.DELETE.equals(gmo.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("竞猜赔率不存在！");
			return;
		}
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("guessingMatchType", gmo.getGuessingMatchType());
		paramsls.put("spread", gmo.getSpread());
		paramsls.put("result", gmo.getResult());
		paramsls.put("sort", "createtime desc");
		
		List<GuessingMatchOdds> gmoList = this.guessingMatchOddsDao.getListByParams(paramsls);
		
		result.setData(gmoList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void update(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String[] datas = (String[]) paramsMap.get("datas");
		
		List<GuessingMatchOddsChangeVO> changevoList = new ArrayList<GuessingMatchOddsChangeVO>();
		for(String data : datas){
			String[] dataArray = data.split("@_@");
			if(dataArray.length != 2){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("数据格式有误！");
				return;
			}
			
			String uuid = dataArray[0];
			String oddsValue = dataArray[1];
			if(!Utlity.isNumeric(oddsValue)){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("数据格式有误！");
				return;
			}
			BigDecimal odds = new BigDecimal(oddsValue);
			
			GuessingMatchOdds gmo = guessingMatchOddsDao.get(uuid);
			if(gmo == null || GuessingMatchOddsStatus.DELETE.equals(gmo.getStatus())){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("竞猜赔率不存在！");
				return;
			}
			
			gmo.setStatus(GuessingMatchOddsStatus.DISABLE);
			GuessingMatchOdds gmoNew = new GuessingMatchOdds();
			gmoNew.setUuid(UUID.randomUUID().toString());
			gmoNew.setGuessingMatch(gmo.getGuessingMatch());
			gmoNew.setGuessingMatchType(gmo.getGuessingMatchType());
			gmoNew.setSpread(gmo.getSpread());
			gmoNew.setResult(gmo.getResult());
			gmoNew.setOdds(odds);
			gmoNew.setStatus(GuessingMatchOddsStatus.NORMAL);
			gmoNew.setCreator(admin);
			gmoNew.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			GuessingMatchOddsChangeVO changevo = new GuessingMatchOddsChangeVO();
			changevo.setOldData(gmo);
			changevo.setNewData(gmoNew);
			
			changevoList.add(changevo);
		}
		
		this.guessingMatchOddsDao.updateOdds(changevoList);
		result.setData(changevoList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
}
