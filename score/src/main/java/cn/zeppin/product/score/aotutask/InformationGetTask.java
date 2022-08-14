package cn.zeppin.product.score.aotutask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.score.entity.Admin.AdminUuid;
import cn.zeppin.product.score.entity.Category;
import cn.zeppin.product.score.entity.Category.CategoryMainUuid;
import cn.zeppin.product.score.entity.Category.CategoryStatus;
import cn.zeppin.product.score.entity.CategoryStanding;
import cn.zeppin.product.score.entity.CategoryStanding.CategoryStandingStatus;
import cn.zeppin.product.score.entity.CategoryTopscore;
import cn.zeppin.product.score.entity.InfoPlayers;
import cn.zeppin.product.score.entity.InfoPlayers.InfoPlayersStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Team;
import cn.zeppin.product.score.entity.Team.TeamStatus;
import cn.zeppin.product.score.service.CategoryService;
import cn.zeppin.product.score.service.CategoryStandingService;
import cn.zeppin.product.score.service.CategoryTopscoreService;
import cn.zeppin.product.score.service.InfoCategoryTeamService;
import cn.zeppin.product.score.service.InfoPlayersService;
import cn.zeppin.product.score.service.InfoTeamPlayersService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.service.TeamService;
import cn.zeppin.product.score.util.GetInfoUtil;
import cn.zeppin.product.score.util.ImageUtils;
import cn.zeppin.product.score.util.Utlity;

@Component
public class InformationGetTask { 
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private InfoPlayersService infoPlayersService;
	
	@Autowired
	private InfoTeamPlayersService infoTeamPlayersService;
	
	@Autowired
	private InfoCategoryTeamService infoCategoryTeamService;
	
	@Autowired
	private CategoryStandingService categoryStandingService;
	
	@Autowired
	private CategoryTopscoreService categoryTopscoreService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private TeamService teamService;
	
	/**
	 * 通过接口获取国家
	 */
//	@Scheduled(cron="0 0/2 *  * * * ")
	@SuppressWarnings("unchecked")
	public void GetInforCountryTask() {

		
		try {
			Map<String, Object> result = GetInfoUtil.getCountryInfoList();
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
				List<Map<String, String>> listDatas = (List<Map<String, String>>) result.get("result");
				if(listDatas != null && listDatas.size() > 0){
					//获取顶级菜单项
					Category parent = categoryService.get(CategoryMainUuid.FOOTBALL);
					if(parent == null){
						parent = new Category();
						parent.setUuid(CategoryMainUuid.FOOTBALL);
						parent.setScode("000");
					}
					List<Category> listResult = new ArrayList<Category>();
					List<Category> listResultUpdate = new ArrayList<Category>();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("parent", parent.getUuid());
					Integer count = categoryService.getCountByParams(params);
					int i = 0;
					for(Map<String, String> data : listDatas){
						int fomatcode = count;
						String country_key = data.get("country_key");
						String country_name = data.get("country_name");
						
						Category cat = new Category();
						
						params.clear();
						params.put("interfaceid", country_key);
						int countnew = categoryService.getCountByParams(params);
						if(countnew > 0){
							params.clear();
							params.put("interfaceid", country_key);
							List<Category> list = categoryService.getListByParams(params);
							cat = list.get(0);
							cat.setShortname(country_name);
							listResultUpdate.add(cat);
							continue;
						}
						
						//计算scode
						i++;
						fomatcode+=i;
						String scode = String.format("%03d", fomatcode);
						
						//准备存储
						cat.setUuid(UUID.randomUUID().toString());
						cat.setInterfaceid(country_key);
						cat.setName(country_name);
						cat.setShortname(country_name);
						cat.setLevel(2);
						cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
						cat.setCreator(AdminUuid.SPIDER);
						cat.setScode(parent.getScode()+scode);
						cat.setStatus(CategoryStatus.NORMAL);
						cat.setParent(parent.getUuid());
						listResult.add(cat);
					}
					
					//批量入库
					if(!listResult.isEmpty()){
						for(Category cat : listResult){
							categoryService.insert(cat);
						}
					}
					if(!listResultUpdate.isEmpty()){
						for(Category cat : listResultUpdate){
							categoryService.update(cat);
						}
					}
//					categoryService.insertList(listResult);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 通过接口获取联赛
	 */
//	@Scheduled(cron="0 0/2 *  * * * ")
	@SuppressWarnings("unchecked")
	public void GetInforLeaguesTask() {

		
		try {
			Map<String, Object> result = GetInfoUtil.getLeaguesInfoList();
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
				List<Map<String, String>> listDatas = (List<Map<String, String>>) result.get("result");
				if(listDatas != null && listDatas.size() > 0){
					List<Category> listResult = new ArrayList<Category>();
					Map<String, Map<String, Object>> mapCountry = new HashMap<String, Map<String, Object>>();
					
					for(Map<String, String> data : listDatas){
						String league_key = data.get("league_key");
						String league_name = data.get("league_name");
						String country_key = data.get("country_key");
						
						Category cat = new Category();
						if(mapCountry.containsKey(country_key)){

							Map<String, Object> mapKey = mapCountry.get(country_key);
							Category parent = (Category) mapKey.get("parent");
							List<Category> childList = (List<Category>) mapKey.get("childList");
							
							//计算scode
							int count = Integer.parseInt(mapKey.get("count").toString());
							
							count += childList.size() + 1;
							String scode = String.format("%03d", count);
							
							cat.setUuid(UUID.randomUUID().toString());
							cat.setInterfaceid(league_key);
							cat.setName(league_name);
							cat.setShortname(league_name);
							cat.setLevel(3);
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							cat.setCreator(AdminUuid.SPIDER);
							cat.setScode(parent.getScode()+scode);
							cat.setStatus(CategoryStatus.NORMAL);
							cat.setParent(parent.getUuid());
							
							childList.add(cat);
							mapKey.put("childList", childList);
							mapCountry.put(country_key, mapKey);
						} else {
							
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("interfaceid", country_key);
							List<Category> parents = categoryService.getListByParams(params);
							if(parents != null && parents.size() > 0){
								Category parent = parents.get(0);
								Map<String, Object> mapKey = new HashMap<String, Object>();
								List<Category> childList = new ArrayList<Category>();
								mapKey.put("parent", parent);
								
								//查询当前子集个数 并生成scode
								params.clear();
								params.put("parent", parent.getUuid());
								int count = categoryService.getCountByParams(params);
								mapKey.put("count", count);
								
								count++;
								String scode = String.format("%03d", count);
								
								cat.setUuid(UUID.randomUUID().toString());
								cat.setInterfaceid(league_key);
								cat.setName(league_name);
								cat.setShortname(league_name);
								cat.setLevel(3);
								cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
								cat.setCreator(AdminUuid.SPIDER);
								cat.setScode(parent.getScode()+scode);
								cat.setStatus(CategoryStatus.NORMAL);
								cat.setParent(parent.getUuid());
								
								childList.add(cat);
								mapKey.put("childList", childList);
								mapCountry.put(country_key, mapKey);
							}
						}
						if(!Utlity.checkStringNull(cat.getUuid())){
							listResult.add(cat);
						}
					}
					
					//批量入库
					if(!listResult.isEmpty()){
						for(Category cat : listResult){
							categoryService.insert(cat);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//定时更新主流联赛队伍数据
//	@Scheduled(cron="10 8 0/8  * * * ")
	@SuppressWarnings("unchecked")
	public void GetInforTeamTask() {
		String[] listLea = GetInfoUtil.LEAGUES_UUID.clone();
		
		for(String uuid : listLea){
			Category cat = this.categoryService.get(uuid);
			try {
				Map<String, Object> result = GetInfoUtil.getTeamInfoList(cat.getInterfaceid());
				
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
					if(listDatas != null && listDatas.size() > 0){
						
						for(Map<String, Object> data : listDatas){
							this.teamService.batchProcess(data, cat);
						}
					}
				}
				cat.setFlagGet(true);
				this.categoryService.update(cat);
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//更新队伍 带图标 （不更队员）
	@SuppressWarnings("unchecked")
//	@Scheduled(cron="30 11 11  * * * ")
	public void GetInforTeamIconTash() {
		String[] listLea = GetInfoUtil.LEAGUES_SECONDARY_UUID.clone();
		for(String uuid : listLea){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("category", uuid);
			params.put("noIcon", true);
			List<Team> listTeam = this.teamService.getListByParams(params);
			for(Team team : listTeam){
				try {
					if(!Utlity.checkStringNull(team.getInterfaceid())){
						Map<String, Object> result = GetInfoUtil.getTeamInfoByIdList(team.getInterfaceid());
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
							if(listDatas != null && listDatas.size() > 0){
								
								for(Map<String, Object> data : listDatas){
									String team_name = data.get("team_name") == null ? "" : (String)data.get("team_name");
									String team_logo = data.get("team_logo") == null ? "" : (String)data.get("team_logo");
									
									team.setShortname(team_name);
									//下载网路图片到本地存储--暂时不换
									String resourceId = "";
									if(!Utlity.checkStringNull(team_logo)){
										Map<String, Object> resource = ImageUtils.downloadPicture(team_logo);
										if("ok".equals(resource.get("success"))){
											Resource res = (Resource) resource.get("result");
											resourceId = res.getUuid();
											this.resourceService.insert(res);
											
											team.setIcon(resourceId);
											this.teamService.update(team);
										}
									}else{
										team.setIcon(resourceId);
										this.teamService.update(team);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//	@Scheduled(cron="10 2 *  * * * ")
	@SuppressWarnings("unchecked")
	public void GetInforStandingsTask() {
		try {
			String[] listLea = GetInfoUtil.LEAGUES_UUID.clone();
			
			for(String leaUuid: listLea){
				Category cat = this.categoryService.get(leaUuid);
				Map<String, Object> result = GetInfoUtil.getStandingsInfoList(cat.getInterfaceid());
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
					Map<String, Object> mapDatas = (Map<String, Object>) result.get("result");
					if(mapDatas != null){
						List<Map<String, Object>> listDatas = (List<Map<String, Object>>)mapDatas.get("total");
						if(listDatas == null || listDatas.size() == 0){
							listDatas = mapDatas.get("home") == null ? listDatas : (List<Map<String, Object>>)mapDatas.get("home");
						}
						
						if(listDatas != null && listDatas.size() > 0){
							String season = "";
							List<CategoryStanding> csList = new ArrayList<CategoryStanding>();
							
							for(Map<String, Object> datas : listDatas){
								season = datas.get("league_season").toString(); 
								
								//取球队
								String teamId = datas.get("team_key").toString();
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("interfaceid", teamId);
								params.put("status", TeamStatus.NORMAL);
								List<Team> teams = this.teamService.getListByParams(params);
								if(teams == null || teams.size() == 0){
									continue;
								}
								Team team = teams.get(0);
								
								CategoryStanding cs = new CategoryStanding();
								cs.setUuid(UUID.randomUUID().toString());
								cs.setCategory(leaUuid);
								cs.setTeam(team.getUuid());
								cs.setSeason(season);
								cs.setRound(datas.get("league_round").toString());
								cs.setPlace(Integer.valueOf(datas.get("standing_place").toString()));
								cs.setPlayed(datas.get("standing_P").toString());
								cs.setWon(datas.get("standing_W").toString());
								cs.setDrawn(datas.get("standing_D").toString());
								cs.setLost(datas.get("standing_L").toString());
								cs.setScored(datas.get("standing_F").toString());
								cs.setAgainst(datas.get("standing_A").toString());
								cs.setDifference(datas.get("standing_GD").toString());
								cs.setPoint(datas.get("standing_PTS").toString());
								cs.setStatus(CategoryStandingStatus.NORMAL);
								cs.setCreatetime(new Timestamp(System.currentTimeMillis()));
								
								csList.add(cs);
							}
							
							this.categoryStandingService.updateByCategory(leaUuid, season, csList);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Scheduled(cron="10 3 *  * * * ")
	@SuppressWarnings("unchecked")
	public void GetInforTopscoresTask() {
		try {
			String[] listLea = GetInfoUtil.LEAGUES_UUID.clone();
			
			for(String leaUuid: listLea){
				Category cat = this.categoryService.get(leaUuid);
				Map<String, Object> result = GetInfoUtil.getTopscoresInfoList(cat.getInterfaceid());
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
					if(listDatas != null && listDatas.size() > 0){
						List<CategoryTopscore> ctList = new ArrayList<CategoryTopscore>();
						
						for(Map<String, Object> datas : listDatas){
							CategoryTopscore ct = new CategoryTopscore();
							ct.setUuid(UUID.randomUUID().toString());
							
							//取球员
							String playerId = datas.get("player_key").toString();
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("interfaceid", playerId);
							params.put("status", InfoPlayersStatus.NORMAL);
							List<InfoPlayers> players = this.infoPlayersService.getListByParams(params);
							if(players == null || players.size() == 0){
								ct.setPlayername(datas.get("player_name").toString());
							}else{
								InfoPlayers player = players.get(0);
								ct.setPlayer(player.getUuid());
								ct.setPlayername(player.getName());
							}
							
							//取球队
							String teamId = datas.get("team_key").toString();
							params.clear();
							params.put("interfaceid", teamId);
							params.put("status", TeamStatus.NORMAL);
							List<Team> teams = this.teamService.getListByParams(params);
							if(teams == null || teams.size() == 0){
								continue;
							}
							Team team = teams.get(0);
							
							ct.setCategory(leaUuid);
							ct.setTeam(team.getUuid());
							ct.setPlace(Integer.valueOf(datas.get("player_place").toString()));
							ct.setGoals(datas.get("goals").toString());
							ct.setStatus(CategoryStandingStatus.NORMAL);
							ct.setCreatetime(new Timestamp(System.currentTimeMillis()));
							
							ctList.add(ct);
						}
						this.categoryTopscoreService.updateByCategory(leaUuid, ctList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}