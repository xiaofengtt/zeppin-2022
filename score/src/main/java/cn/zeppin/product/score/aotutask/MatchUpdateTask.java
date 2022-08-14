package cn.zeppin.product.score.aotutask;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.entity.InfoMatch.InfoMatchStatus;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.entity.Team.TeamStatus;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.InfoMatchService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.GetInfoUtil;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;

@Component
public class MatchUpdateTask {
	
	@Autowired
	private InfoMatchService infoMatchService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private CategoryService categoryService;
	
//	@Scheduled(cron="30 * *  * * * ")
	public void updateLivingTask() throws ParseException{
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String, Object> result = GetInfoUtil.getLivingInfoList(params);
			
			Calendar fromTime = Calendar.getInstance();
			Calendar toTime = Calendar.getInstance();
			saveMatchData(result, fromTime, toTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Scheduled(cron="30 26 17  * * * ")
//	@Scheduled(cron="20 1 *  * * * ")
	public void updateMatchHistory() throws ParseException{
		Calendar time = Calendar.getInstance();
		Calendar deadLine = Calendar.getInstance();
		deadLine.add(Calendar.DATE, -5);
		time.add(Calendar.DATE, 1);
		while(deadLine.before(time)){
			time.add(Calendar.DATE, -1);
			getMatchInfo((Calendar)time.clone(), (Calendar)time.clone());
		}
	}
	
//	@Scheduled(cron="20 2 *  * * * ")
	public void updateMatchFuture() throws ParseException{
		Calendar deadLine = Calendar.getInstance();
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, 10);
		while(deadLine.before(time)){
			time.add(Calendar.DATE, -1);
			getMatchInfo((Calendar)time.clone(), (Calendar)time.clone());
		}
	}
	
	private void getMatchInfo(Calendar fromTime, Calendar toTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("from", sdf.format(fromTime.getTime()));
			params.put("to", sdf.format(toTime.getTime()));
			Map<String, Object> result = GetInfoUtil.getFixturesInfoList(params);
			
			saveMatchData(result, fromTime, toTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  
	@SuppressWarnings("unchecked")
	private void saveMatchData(Map<String, Object> result, Calendar starttime,Calendar endtime) throws ParseException{
		Boolean flag = false;
		//接口是否请求成功
		if(result.containsKey("success")){
			Integer success = Integer.parseInt(result.get("success").toString());
			if(success == 1){
				flag = true;
			}
		}
		
		//判读是否有返回数据
		if(flag && result.containsKey("result")){
			List<Map<String, Object>> listDatas = (List<Map<String, Object>>) result.get("result");
			List<InfoMatch> matchList = new ArrayList<InfoMatch>();
			
			Map<String, String> teamNameMap = new HashMap<String, String>();
			Map<String, String> teamIdMap = new HashMap<String, String>();
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("status", TeamStatus.NORMAL);
			List<Team> teamList = this.teamService.getListByParams(searchParams);
			for(Team team : teamList){
				teamNameMap.put(team.getShortname(), team.getUuid());
				teamIdMap.put(team.getInterfaceid(), team.getUuid());
			}


			Map<String, String> categoryMap = new HashMap<String, String>();
			List<Category> categoryList = this.categoryService.getListByParams(searchParams);
			for(Category cate: categoryList){
				if(!Utlity.checkStringNull(cate.getInterfaceid())){
					categoryMap.put(cate.getInterfaceid(), cate.getUuid());
				}
			}
			
			
			for(Map<String, Object> datas : listDatas){
				InfoMatch match = new InfoMatch();
				String categoryKey = datas.get("league_key") == null ? "" : datas.get("league_key").toString();
				if(Utlity.checkStringNull(categoryKey)){
					continue;
				}
				
				String categoryUuid = categoryMap.get(categoryKey);
				if(Utlity.checkStringNull(categoryUuid)){
					continue;
				}
				
				match.setInterfaceid(datas.get("event_key").toString());
				match.setCategory(categoryUuid);
				
				String season = datas.get("league_season") == null ? "" : datas.get("league_season").toString();
				if(!Utlity.checkStringNull(season)){
					match.setSeason(season);
				}
				
				String round = datas.get("league_round") == null ? "" : datas.get("league_round").toString();
				if(!Utlity.checkStringNull(round)){
					match.setRound(round);
				}
				
				String status = datas.get("event_status") == null ? "" : datas.get("event_status").toString();
				switch (status){
					case "Finished":
						match.setStatus(InfoMatchStatus.FINISHED);
						break;
					case "Postponed":
						match.setStatus(InfoMatchStatus.POSTPONED);
						break;
					case "Half Time":
						match.setStatus(InfoMatchStatus.LIVING);
						match.setProgress("中场休息");
						break;
					case "After Pen.":
						match.setStatus(InfoMatchStatus.FINISHED);
						break;
					case "After Extra Time":
						match.setStatus(InfoMatchStatus.FINISHED);
						break;
					case "After ET":
						match.setStatus(InfoMatchStatus.FINISHED);
						break;
					case "":
						match.setStatus(InfoMatchStatus.UNSTART);
						break;
					default:
						match.setStatus(InfoMatchStatus.LIVING);
						match.setProgress(status.replace("\u00a0", ""));
				}
				
				Timestamp matchTime = null;
				if(datas.get("event_time") != null){
					String timeStr = datas.get("event_date").toString() + " " + datas.get("event_time").toString() +":00";
					matchTime = new Timestamp(Utlity.getCurrentTime(Utlity.timeRomeToBeijng(timeStr)).getTime());
				}else{
					String timeStr = datas.get("event_date").toString() + " 00:00:00";
					matchTime = new Timestamp(Utlity.getCurrentTime(timeStr).getTime());
				}
				match.setTime(matchTime);
				
//				if(teamMap.get(datas.get("home_team_key").toString()) == null || teamMap.get(datas.get("away_team_key").toString()) == null){
//					break;
//				}
				String homeUuid = null;
				String awayUuid = null;
				if(Utlity.checkStringNull(datas.get("event_home_team").toString())){
					for(String key : teamNameMap.keySet()){
						if(key.indexOf(datas.get("event_home_team").toString()) > -1){
							homeUuid = teamNameMap.get(key);
						}
						if(key.indexOf(datas.get("event_away_team").toString()) > -1){
							awayUuid = teamNameMap.get(key);
						}
					}
				}
				for(String key : teamIdMap.keySet()){
					if(datas.get("home_team_key") != null){
						if(datas.get("home_team_key").toString().equals(key)){
							homeUuid = teamIdMap.get(key);
						}
					}
					if(datas.get("away_team_key") != null){
						if(datas.get("away_team_key").toString().equals(key)){
							awayUuid = teamIdMap.get(key);
						}
					}
				}
				if(homeUuid == null || awayUuid == null){
					continue;
				}
				match.setHometeam(homeUuid);
				match.setAwayteam(awayUuid);
				
				match.setFinalresult(datas.get("event_final_result") == null ? "" : datas.get("event_final_result").toString().replace("\u00a0", " "));
				if(!Utlity.checkStringNull(datas.get("event_ft_result") == null ? "" : datas.get("event_ft_result").toString())){
					match.setFinalresult(datas.get("event_ft_result").toString().replace("\u00a0", " "));
				}
				if(!Utlity.checkStringNull(datas.get("event_penalty_result") == null ? "" : datas.get("event_penalty_result").toString())){
					match.setPenaltyresult(datas.get("event_penalty_result").toString().replace("\u00a0", " "));
				}
				
				Map<String, Object> lineups = (Map<String, Object>) datas.get("lineups");
				if(lineups != null){
					Map<String, Object> homeLineups = (Map<String, Object>)lineups.get("home_team");
					if(homeLineups != null){
						if(homeLineups.get("starting_lineups") != null){
							List<Map<String, Object>> homeStarting = (List<Map<String, Object>>)homeLineups.get("starting_lineups");
							match.setHomestarting(JSONUtils.obj2json(homeStarting));
						}
						if(homeLineups.get("substitutes") != null){
							List<Map<String, Object>> homeSubstitutes = (List<Map<String, Object>>)homeLineups.get("substitutes");
							match.setHomesubstitutes(JSONUtils.obj2json(homeSubstitutes));
						}
						if(homeLineups.get("coaches") != null){
							List<Map<String, Object>> homeCoaches = (List<Map<String, Object>>)homeLineups.get("coaches");
							match.setHomecoaches(JSONUtils.obj2json(homeCoaches));
						}
						
					}
					
					Map<String, Object> awayLineups = (Map<String, Object>)lineups.get("away_team");
					if(awayLineups != null){
						if(awayLineups.get("starting_lineups") != null){
							List<Map<String, Object>> awayStarting = (List<Map<String, Object>>)awayLineups.get("starting_lineups");
							match.setAwaystarting(JSONUtils.obj2json(awayStarting));
						}
						if(awayLineups.get("substitutes") != null){
							List<Map<String, Object>> awaySubstitutes = (List<Map<String, Object>>)awayLineups.get("substitutes");
							match.setAwaysubstitutes(JSONUtils.obj2json(awaySubstitutes));
						}
						if(awayLineups.get("coaches") != null){
							List<Map<String, Object>> awayCoaches = (List<Map<String, Object>>)awayLineups.get("coaches");
							match.setAwaycoaches(JSONUtils.obj2json(awayCoaches));
						}
					}
				}
				

				if(datas.get("goalscorers") != null){
					List<Map<String, Object>> goals = (List<Map<String, Object>>)datas.get("goalscorers");
					match.setGoals(JSONUtils.obj2json(goals));
				}
				
				if(datas.get("cards") != null){
					List<Map<String, Object>> cards = (List<Map<String, Object>>)datas.get("cards");
					match.setCards(JSONUtils.obj2json(cards));
				}
				
				if(datas.get("substitutes") != null){
					List<Map<String, Object>> substitutes = (List<Map<String, Object>>)datas.get("substitutes");
					match.setSubstitutes(JSONUtils.obj2json(substitutes));
				}
				
				if(datas.get("statistics") != null){
					List<Map<String, Object>> statistics = (List<Map<String, Object>>)datas.get("statistics");
					match.setStatistics(JSONUtils.obj2json(statistics));
				}
				
				matchList.add(match);
			}
			
			searchParams.clear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			starttime.add(Calendar.DATE, -1);
			endtime.add(Calendar.DATE, 1);
			searchParams.put("starttime", sdf.format(starttime.getTime()) + " 00:00:00");
			searchParams.put("endtime", sdf.format(endtime.getTime()) + " 23:59:59");
			List<InfoMatch> matchs = this.infoMatchService.getListByParams(searchParams);
			Map<String, String> matchsMap = new HashMap<String, String>();                                                                                                                             
			
			for(InfoMatch match : matchs){
				matchsMap.put(match.getInterfaceid(), match.getUuid());
			}
			
			for(InfoMatch match : matchList){
				if(matchsMap.containsKey(match.getInterfaceid())){
					InfoMatch dbMatch = this.infoMatchService.get(matchsMap.get(match.getInterfaceid()));
					dbMatch.setSeason(match.getSeason());
					dbMatch.setRound(match.getRound());
					dbMatch.setTime(match.getTime());
					dbMatch.setProgress(match.getProgress());
					dbMatch.setHometeam(match.getHometeam());
					dbMatch.setAwayteam(match.getAwayteam());
					dbMatch.setFinalresult(match.getFinalresult());
					dbMatch.setPenaltyresult(match.getPenaltyresult());
					dbMatch.setHomestarting(match.getHomestarting());
					dbMatch.setHomesubstitutes(match.getHomesubstitutes());
					dbMatch.setHomecoaches(match.getHomecoaches());
					dbMatch.setAwaystarting(match.getAwaystarting());
					dbMatch.setAwaysubstitutes(match.getAwaysubstitutes());
					dbMatch.setAwaycoaches(match.getAwaycoaches());
					dbMatch.setGoals(match.getGoals());
					dbMatch.setCards(match.getCards());
					dbMatch.setSubstitutes(match.getSubstitutes());
					dbMatch.setStatistics(match.getStatistics());
					dbMatch.setStatus(match.getStatus());
					this.infoMatchService.update(dbMatch);
				}else{
					match.setUuid(UUID.randomUUID().toString());
					match.setCreatetime(new Timestamp(System.currentTimeMillis()));
					this.infoMatchService.insert(match);
				}
			}
		}
	}
}
