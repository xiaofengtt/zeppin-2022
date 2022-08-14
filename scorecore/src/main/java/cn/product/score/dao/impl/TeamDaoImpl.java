package cn.product.score.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.InfoCategoryTeamDao;
import cn.product.score.dao.InfoPlayersDao;
import cn.product.score.dao.InfoTeamPlayersDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Admin.AdminUuid;
import cn.product.score.entity.Category;
import cn.product.score.entity.InfoCategoryTeam;
import cn.product.score.entity.InfoCategoryTeam.InfoCategoryTeamStatus;
import cn.product.score.entity.InfoPlayers;
import cn.product.score.entity.InfoPlayers.InfoPlayersStatus;
import cn.product.score.entity.InfoTeamPlayers;
import cn.product.score.entity.InfoTeamPlayers.InfoTeamPlayersStatus;
import cn.product.score.entity.Team;
import cn.product.score.entity.Team.TeamStatus;
import cn.product.score.mapper.TeamMapper;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;

@Component
public class TeamDaoImpl implements TeamDao{
	
	@Autowired
	private TeamMapper teamMapper;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private InfoCategoryTeamDao infoCategoryTeamDao;
	
	@Autowired
	private InfoPlayersDao infoPlayersDao;
	
	@Autowired
	private InfoTeamPlayersDao infoTeamPlayersDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return teamMapper.getCountByParams(params);
	}
	
	@Override
	public List<Team> getListByParams(Map<String, Object> params) {
		return teamMapper.getListByParams(params);
	}

	@Override
	@Transactional
	public void insertTeam(Team team) {
		String[] categorys = team.getCategory().split(",");
		for(String category : categorys){
			Category cate = this.categoryDao.get(category);
			if(cate != null){
				InfoCategoryTeam ict = new InfoCategoryTeam();
				ict.setUuid(UUID.randomUUID().toString());
				ict.setCategory(cate.getUuid());
				ict.setCategoryinterid(cate.getInterfaceid());
				ict.setTeam(team.getUuid());
				ict.setTeaminterid(team.getInterfaceid());
				ict.setStatus(InfoCategoryTeamStatus.NORMAL);
				ict.setCreator(team.getCreator());
				ict.setCreatetime(new Timestamp(System.currentTimeMillis()));
				this.infoCategoryTeamDao.insert(ict);
			}
		}
		this.insert(team);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "infoCategoryTeam", allEntries = true),@CacheEvict(value = "team", key = "'team:' + #team.uuid")})
	public void updateTeam(Team team) {
		this.infoCategoryTeamDao.deleteByTeam(team.getUuid());
		String[] categorys = team.getCategory().split(",");
		for(String category : categorys){
			Category cate = this.categoryDao.get(category);
			if(cate != null){
				InfoCategoryTeam ict = new InfoCategoryTeam();
				ict.setUuid(UUID.randomUUID().toString());
				ict.setCategory(cate.getUuid());
				ict.setCategoryinterid(cate.getInterfaceid());
				ict.setTeam(team.getUuid());
				ict.setTeaminterid(team.getInterfaceid());
				ict.setStatus(InfoCategoryTeamStatus.NORMAL);
				ict.setCreator(team.getCreator());
				ict.setCreatetime(new Timestamp(System.currentTimeMillis()));
				this.infoCategoryTeamDao.insert(ict);
			}
		}
		this.update(team);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "infoCategoryTeam", allEntries = true),@CacheEvict(value = "team", key = "'team:' + #team.uuid")})
	public void deleteTeam(Team team) {
		this.infoCategoryTeamDao.deleteByTeam(team.getUuid());
		team.setStatus(TeamStatus.DELETE);
		this.update(team);
	}
	
	@Override
	@Cacheable(cacheNames="team",key="'team:' + #key")
	public Team get(String key) {
		return teamMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(Team team) {
		return teamMapper.insert(team);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "team", key = "'team:' + #key")})
	public int delete(String key) {
		return teamMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "team", key = "'team:' + #team.uuid")})
	public int update(Team team) {
		return teamMapper.updateByPrimaryKey(team);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "infoCategoryTeam", allEntries = true),@CacheEvict(value = "team", allEntries = true)
	,@CacheEvict(value = "infoPlayers", allEntries = true),@CacheEvict(value = "infoTeamPlayers", allEntries = true)})
	public void batchProcess(Map<String, Object> data, Category cat) {
		Map<String, InfoPlayers> mapPla = new HashMap<String, InfoPlayers>();
		Map<String, InfoTeamPlayers> mapItp = new HashMap<String, InfoTeamPlayers>();
		Map<String, InfoPlayers> mapPlaUpdate = new HashMap<String, InfoPlayers>();
		
		String team_key = (String) data.get("team_key");
		String team_name = (String) data.get("team_name");
//		String team_logo = data.get("team_logo") == null ? "" : (String)data.get("team_logo");
		List<Map<String, Object>> players = data.get("players") == null ? null : (List<Map<String, Object>>)data.get("players");
		List<Map<String, String>> coaches = data.get("coaches") == null ? null : (List<Map<String, String>>)data.get("coaches");
		
		String coachesStr = "";
		if(coaches != null && coaches.size() > 0){
			List<Map<String, String>> coaches_new = new ArrayList<Map<String,String>>();
			for(Map<String, String> coache : coaches){
				coache.put("name", coache.get("coache_name") == null ? "" : coache.get("coache_name"));
				coaches_new.add(coache);
			}
			coachesStr = JSONUtils.obj2json(coaches_new);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		int count = 0;
		
		params.clear();
		params.put("teaminterid", team_key);
		params.put("categoryinterid", cat.getInterfaceid());
		int countict = infoCategoryTeamDao.getCountByParams(params);
		
		Team team = new Team();
		
		params.clear();
		params.put("interfaceid", team_key);
		params.put("status", TeamStatus.NORMAL);
		count = teamMapper.getCountByParams(params);
		
		if(count > 0){
			params.clear();
			params.put("interfaceid", team_key);
			params.put("status", TeamStatus.NORMAL);
			List<Team> list = teamMapper.getListByParams(params);
			team = list.get(0);
			
			//更新联赛
			if(team.getCategory() == null){
				team.setCategory(cat.getUuid());
			} else {
				if(team.getCategory().indexOf(cat.getUuid()) == -1){
					team.setCategory(team.getCategory() +","+cat.getUuid());
				}
			}
			
			//更新教练
			if(coaches != null && coaches.size() > 0){
				String jsonStr = team.getCoaches();
				if(!Utlity.checkStringNull(jsonStr)){
					List<Map<String, String>> coachesNew = JSONUtils.json2obj(jsonStr, List.class);
					List<Map<String, String>> updateMap = new ArrayList<Map<String,String>>();
					
					if(coachesNew.size() > 5){
						coachesNew = coachesNew.subList(0, 4);
					}
					
					for(Map<String, String> map : coachesNew){
						for(Map<String, String> coach : coaches){
							String coach_name = coach.get("coach_name") == null ? "" : coach.get("coach_name");
							String coach_country = coach.get("coach_country") == null ? "" : coach.get("coach_country");
							String coach_age = coach.get("coach_name") == null ? "" : coach.get("coach_age");
							if(!coach_name.equals(map.containsKey("coach_name"))){
								map.put("coach_name", coach_name);
								map.put("coach_country", coach_country);
								map.put("coach_age", coach_age);
								map.put("name", coach_name);
								updateMap.add(map);
							} else {
								updateMap.add(coach);
							}
						}
					}
					String coachesStr1 = "";
					if(updateMap != null && updateMap.size() > 0){
						coachesStr1 = JSONUtils.obj2json(updateMap);
					}
					team.setCoaches(coachesStr1);
				}
			} else {
				team.setCoaches("");
			}
			this.update(team);
		} else {//新球队
			//准备入库
			team.setUuid(UUID.randomUUID().toString());
			team.setStatus(TeamStatus.NORMAL);
			team.setCreator(AdminUuid.SPIDER);
			team.setCreatetime(new Timestamp(System.currentTimeMillis()));
			team.setCategory(cat.getUuid());
			
			//下载网路图片到本地存储
//			String resourceId = "";
//			if(!Utlity.checkStringNull(team_logo)){
//				Map<String, Object> resource = ImageUtils.downloadPicture(team_logo);
//				if("ok".equals(resource.get("success"))){
//					Resource res = (Resource) resource.get("result");
////					listResource.add(res);
//					this.resourceDao.insert(res);//球队图片入库
//					
//					resourceId = res.getUuid();
//				}
//			}
//			team.setIcon(resourceId);
			team.setInterfaceid(team_key);
			team.setName(team_name);
			team.setShortname(team_name);
			
			team.setCoaches(coachesStr);
			
			this.insert(team);
		}
		
		//是否有联赛关系
		if(countict == 0){//无
			InfoCategoryTeam ict = new InfoCategoryTeam();
			ict.setUuid(UUID.randomUUID().toString());
			ict.setStatus(InfoTeamPlayersStatus.NORMAL);
			ict.setCreator(AdminUuid.SPIDER);
			ict.setCreatetime(new Timestamp(System.currentTimeMillis()));
			ict.setCategory(cat.getUuid());
			ict.setTeam(team.getUuid());
			ict.setTeaminterid(team.getInterfaceid());
			ict.setCategoryinterid(cat.getInterfaceid());
			
			this.infoCategoryTeamDao.insert(ict);
		}
		
		//遍历生成球员数据
		if(players != null && players.size() > 0){
			for(Map<String, Object> player : players){
				String player_key = String.valueOf(player.get("player_key"));
				String player_name = (String) player.get("player_name");
				String player_number = player.get("player_number") == null ? "" : (String)player.get("player_number");
				String player_country = player.get("player_country") == null ? "" : (String)player.get("player_country");;
				String player_type = player.get("player_type") == null ? "" : (String)player.get("player_type");;
				String player_age = player.get("player_age") == null ? "" : (String)player.get("player_age");;
				String player_match_played = player.get("player_match_played") == null ? "" : (String)player.get("player_match_played");;
				String player_goals = player.get("player_goals") == null ? "" : (String)player.get("player_goals");;
				String player_yellow_cards = player.get("player_yellow_cards") == null ? "" : (String)player.get("player_yellow_cards");;
				String player_red_cards = player.get("player_red_cards") == null ? "" : (String)player.get("player_red_cards");;
				
				InfoPlayers ipl = new InfoPlayers();
				
				if(mapPla.containsKey(player_key) && mapItp.containsKey(team_key + "_" + player_key)){
					continue;
				} else if (mapPla.containsKey(player_key) && !mapItp.containsKey(team_key + "_" + player_key)){
					ipl = mapPla.get(player_key);
					
					InfoTeamPlayers itp = new InfoTeamPlayers();
					itp.setUuid(UUID.randomUUID().toString());
					itp.setStatus(InfoTeamPlayersStatus.NORMAL);
					itp.setCreator(AdminUuid.SPIDER);
					itp.setCreatetime(new Timestamp(System.currentTimeMillis()));
					itp.setInfoplayers(ipl.getUuid());
					itp.setTeam(team.getUuid());
					mapItp.put(team_key + "_" + player_key, itp);
				} else if (!mapPla.containsKey(player_key) && !mapItp.containsKey(team_key + "_" + player_key)) {
					//球员入库
					params.clear();
					params.put("interfaceid", player_key);
					params.put("status", InfoPlayersStatus.NORMAL);
					int countpl = infoPlayersDao.getCountByParams(params);
					
					if(countpl > 0){
						params.clear();
						params.put("interfaceid", player_key);
						params.put("status", InfoPlayersStatus.NORMAL);
						List<InfoPlayers> list = infoPlayersDao.getListByParams(params);
						ipl = list.get(0);
						ipl.setAge(player_age);
						ipl.setCountry(player_country);
						ipl.setGoals(player_goals);
						ipl.setInterfaceid(player_key);
						ipl.setMatchplayed(player_match_played);
						ipl.setName(player_name);
						ipl.setNumber(player_number);
						ipl.setRedcards(player_red_cards);
						ipl.setYellowcards(player_yellow_cards);
						ipl.setType(player_type);
						mapPlaUpdate.put(ipl.getUuid(), ipl);
					} else {
						//直接删除球队球员关系 重新入库
						ipl.setUuid(UUID.randomUUID().toString());
						ipl.setStatus(InfoPlayersStatus.NORMAL);
						ipl.setCreator(AdminUuid.SPIDER);
						ipl.setCreatetime(new Timestamp(System.currentTimeMillis()));
						
						ipl.setCnname(player_name);
						ipl.setAge(player_age);
						ipl.setCountry(player_country);
						ipl.setGoals(player_goals);
						ipl.setInterfaceid(player_key);
						ipl.setMatchplayed(player_match_played);
						ipl.setName(player_name);
						ipl.setNumber(player_number);
						ipl.setRedcards(player_red_cards);
						ipl.setYellowcards(player_yellow_cards);
						ipl.setType(player_type);
						mapPla.put(player_key, ipl);
					}
					
					InfoTeamPlayers itp = new InfoTeamPlayers();
					itp.setUuid(UUID.randomUUID().toString());
					itp.setStatus(InfoTeamPlayersStatus.NORMAL);
					itp.setCreator(AdminUuid.SPIDER);
					itp.setCreatetime(new Timestamp(System.currentTimeMillis()));
					itp.setInfoplayers(ipl.getUuid());
					itp.setTeam(team.getUuid());
					
					mapItp.put(team_key + "_" + player_key, itp);
				}
			}
		}
		
		
		//删除球队球员关系
		this.infoTeamPlayersDao.deleteByTeam(team.getUuid());
		
		//入库
		if(!mapPla.isEmpty()){
			for (Map.Entry<String, InfoPlayers> m : mapPla.entrySet()) {
				this.infoPlayersDao.insert(m.getValue());
			}
		}
		
		if(!mapPlaUpdate.isEmpty()){
			for (Map.Entry<String, InfoPlayers> m : mapPlaUpdate.entrySet()) {
				this.infoPlayersDao.update(m.getValue());
			}
		}
		
		if(!mapItp.isEmpty()){
			for (Map.Entry<String, InfoTeamPlayers> m : mapItp.entrySet()) {
				this.infoTeamPlayersDao.insert(m.getValue());
			}
		}

	}
}
