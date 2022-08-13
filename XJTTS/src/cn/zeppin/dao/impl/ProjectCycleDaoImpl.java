package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectCycleDao;
import cn.zeppin.entity.ProjectCycle;

public class ProjectCycleDaoImpl extends
		BaseDaoImpl<ProjectCycle, Integer> implements IProjectCycleDao {

	@Override
	public List<ProjectCycle> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from ProjectCycle pc where 1=1");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and pc.id=");
				sb.append(params.get("id"));
			}
			if(params.containsKey("name")){
				sb.append(" and pc.name like '%");
				sb.append(params.get("name"));
				sb.append("%'");
			}
			
			if(params.containsKey("status")){
				sb.append(" and pc.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and pc.status>-1");
			}
			
			if(params.containsKey("startyear")){
				sb.append(" and pc.startyear='");
				sb.append(params.get("startyear"));
				sb.append("'");
			}
			
			if(params.containsKey("endyear")){
				sb.append(" and pc.endyear='");
				sb.append(params.get("endyear"));
				sb.append("'");
			}

			if(params.containsKey("projectyear")){
				sb.append(" and (pc.startyear <= '");
				sb.append(params.get("projectyear"));
				sb.append("' and pc.endyear >= '");
				sb.append(params.get("projectyear"));
				sb.append("')");
			}
		}
		
		sb.append("order by pc.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from ProjectCycle pc where 1=1");
		if(params != null){
			if(params.containsKey("name")){
				sb.append(" and pc.name like '%");
				sb.append(params.get("name"));
				sb.append("%'");
			}
			
			if(params.containsKey("status")){
				sb.append(" and pc.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and pc.status>-1");
			}
			
			if(params.containsKey("startyear")){
				sb.append(" and pc.startyear='");
				sb.append(params.get("startyear"));
				sb.append("'");
			}
			
			if(params.containsKey("endyear")){
				sb.append(" and pc.endyear='");
				sb.append(params.get("endyear"));
				sb.append("'");
			}
			
			if(params.containsKey("projectyear")){
				sb.append(" and (pc.startyear >= '");
				sb.append(params.get("projectyear"));
				sb.append("' and pc.endyear <= '");
				sb.append(params.get("projectyear"));
				sb.append("')");
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
