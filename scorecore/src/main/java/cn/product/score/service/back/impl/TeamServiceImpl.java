package cn.product.score.service.back.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.InfoCategoryTeamDao;
import cn.product.score.dao.InfoPlayersDao;
import cn.product.score.dao.InfoTeamPlayersDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.InfoPlayers;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.service.back.TeamService;
import cn.product.score.vo.back.InfoPlayersVO;
import cn.product.score.vo.back.TeamVO;

@Service("teamDao")
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private InfoPlayersDao infoPlayersDao;
	
	@Autowired
	private InfoCategoryTeamDao infoCategoryTeamDao;
	
	@Autowired
	private InfoTeamPlayersDao infoTeamPlayersDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		Team team = (Team)paramsMap.get("Team");
		String category = paramsMap.get("category") == null ? "" : paramsMap.get("category").toString();
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", team.getName());
		searchMap.put("category", category);
		searchMap.put("status", team.getStatus());
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalCount = teamDao.getCountByParams(searchMap);
		List<Team> list = teamDao.getListByParams(searchMap);
		List<TeamVO> voList = new LinkedList<TeamVO>();
		for(Team te : list){
			TeamVO teamVO = new TeamVO(te);
			if(te.getIcon() != null){
				Resource res = resourceDao.get(te.getIcon());
				if(res != null){
					teamVO.setIconUrl(res.getUrl());
				}
			}
			Admin admin = adminDao.get(te.getCreator());
			if(admin != null){
				teamVO.setCreatorName(admin.getRealname());
			}
			voList.add(teamVO);
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
    	
		Team team = teamDao.get(uuid);
		if(team == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球队不存在");
			return;
		}
		
		TeamVO teamVO = new TeamVO(team);
		if(team.getIcon() != null){
			Resource res = resourceDao.get(team.getIcon());
			if(res != null){
				teamVO.setIconUrl(res.getUrl());
			}
		}
		Admin admin = adminDao.get(team.getCreator());
		if(admin != null){
			teamVO.setCreatorName(admin.getRealname());
		}
		
		result.setData(teamVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Team team = (Team) paramsMap.get("team");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		team.setUuid(UUID.randomUUID().toString());
		team.setCreator(admin);
		team.setCreatetime(new Timestamp(System.currentTimeMillis()));
		teamDao.insertTeam(team);
		
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Team team = (Team) paramsMap.get("team");
		
		Team te = this.teamDao.get(team.getUuid());
		if(te == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球队不存在");
			return;
		}
		
		te.setName(team.getName());
		te.setIcon(team.getIcon());
		teamDao.updateTeam(te);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Team team = this.teamDao.get(uuid);
		if(team == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球队不存在");
			return;
		}
		teamDao.deleteTeam(team);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("删除成功！");
	}

	@Override
	public void playerList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String team = paramsMap.get("team") == null ? "" : paramsMap.get("team").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("name", name);
		searchMap.put("team", team);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = infoPlayersDao.getCountByParams(searchMap);
		List<InfoPlayers> list = infoPlayersDao.getListByParams(searchMap);
		List<InfoPlayersVO> voList = new LinkedList<InfoPlayersVO>();
		for(InfoPlayers ip : list){
			InfoPlayersVO ipVO = new InfoPlayersVO(ip);
			voList.add(ipVO);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void playerGet(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		InfoPlayers ip = infoPlayersDao.get(uuid);
		if(ip == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球员不存在");
			return;
		}
		
		InfoPlayersVO ipVO = new InfoPlayersVO(ip);
		result.setData(ipVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void playerEdit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String cnname = paramsMap.get("cnname") == null ? "" : paramsMap.get("cnname").toString();
		InfoPlayers ip = infoPlayersDao.get(uuid);
		if(ip == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球员不存在");
			return;
		}
		
		ip.setCnname(cnname);
		infoPlayersDao.update(ip);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("保存成功！");
	}
	
}
