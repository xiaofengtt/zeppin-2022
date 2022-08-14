package cn.product.score.service.front.impl;

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
import cn.product.score.dao.CategoryDao;
import cn.product.score.dao.FrontUserConcernDao;
import cn.product.score.dao.ResourceDao;
import cn.product.score.dao.TeamDao;
import cn.product.score.entity.FrontUserConcern;
import cn.product.score.entity.Resource;
import cn.product.score.entity.Team;
import cn.product.score.service.front.FrontUserConcernService;
import cn.product.score.util.Utlity;
import cn.product.score.vo.front.FrontUserConcernVO;

@Service("frontUserConcernService")
public class FrontUserConcernServiceImpl implements FrontUserConcernService{
	
	@Autowired
	private FrontUserConcernDao frontUserConcernDao;
	
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
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("category", category);
		searchMap.put("user", fu);
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalCount = frontUserConcernDao.getCountByParams(searchMap);
		List<FrontUserConcern> list = frontUserConcernDao.getListByParams(searchMap);
		List<FrontUserConcernVO> voList = new LinkedList<FrontUserConcernVO>();
		for(FrontUserConcern fuc : list){
			FrontUserConcernVO fucVO = new FrontUserConcernVO(fuc);
			
			Team team = this.teamDao.get(fuc.getTeam());
			fucVO.setTeamName(team.getName());
			if(!Utlity.checkStringNull(team.getIcon())){
				Resource resource = this.resourceDao.get(team.getIcon());
				if(resource != null){
					fucVO.setTeamIconUrl(resource.getUrl());
				}
			}
			voList.add(fucVO);
		}
		
		result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String team = paramsMap.get("team") == null ? "" : paramsMap.get("team").toString();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("user", fu);
		searchMap.put("team", team);
		List<FrontUserConcern> list = frontUserConcernDao.getListByParams(searchMap);
		if(list.size() > 0){
			result.setData(list.get(0));
			result.setStatus(ResultStatusType.SUCCESS);
			return;
		}
		result.setData(new FrontUserConcern());
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String team = paramsMap.get("team") == null ? "" : paramsMap.get("team").toString();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		
		Team t = this.teamDao.get(team);
		if(t == null){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球队不存在！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("user", fu);
		searchMap.put("team", team);
		Integer count = this.frontUserConcernDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("球队已关注！");
			return;
		}
		
		FrontUserConcern fuc = new FrontUserConcern();
		fuc.setUuid(UUID.randomUUID().toString());
		fuc.setUser(fu);
		fuc.setTeam(team);
		fuc.setCategory(t.getCategory());
		fuc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.frontUserConcernDao.insert(fuc);
		result.setData(fuc);
		result.setStatus(ResultStatusType.SUCCESS);
		return;
	}

	@Override
	public void cancel(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String userConcern = paramsMap.get("userConcern") == null ? "" : paramsMap.get("userConcern").toString();
		String fu = paramsMap.get("fu") == null ? "" : paramsMap.get("fu").toString();
		
		FrontUserConcern fuc = this.frontUserConcernDao.get(userConcern);
		if(fuc == null || !fuc.getUser().equals(fu)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("未找到关注数据！");
			return;
		}
		
		this.frontUserConcernDao.delete(userConcern);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("取消关注成功");
		return;
	}
}
