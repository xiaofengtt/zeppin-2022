package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IIdentifyStudyhourDao;
import cn.zeppin.entity.IdentifyStudyhour;

public class IdentifyStudyhourDaoImpl extends BaseDaoImpl<IdentifyStudyhour, Integer> implements IIdentifyStudyhourDao {

	@Override
	public List<IdentifyStudyhour> getListByParams(Map<String, Object> params, int start, int limit, String sort) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from IdentifyStudyhour ids where 1=1");
		if(params != null){
			if(params.containsKey("projectCycle")){
				sb.append(" and ids.projectCycle=");
				sb.append(params.get("projectCycle"));
			}
			
			if(params.containsKey("projectType")){
				sb.append(" and ids.projectType=");
				sb.append(params.get("projectType"));
			}
			
			if(params.containsKey("project")){
				if("empty".equals(params.get("project"))){
					sb.append(" and ids.project is null");
				}else{
					sb.append(" and ids.project=");
					sb.append(params.get("project"));
				}
			}
			
			if (params.containsKey("year")) {
				if("empty".equals(params.get("year"))){
					sb.append(" and ids.year is null");
				}else{
					sb.append(" and ids.year='");
					sb.append(params.get("year")+"'");
				}
			}
			
			if (params.containsKey("trainingSubject")) {
				if("empty".equals(params.get("trainingSubject"))){
					sb.append(" and ids.trainingSubject is null");
				}else{
					sb.append(" and ids.trainingSubject=");
					sb.append(params.get("trainingSubject"));
				}
			}
			
			if(params.containsKey("status")){
				sb.append(" and ids.status=");
				sb.append(params.get("status"));
			}
		}
		
		sb.append(" order by ids.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from IdentifyStudyhour ids where 1=1");
		if(params != null){
			if(params.containsKey("projectCycle")){
				sb.append(" and ids.projectCycle=");
				sb.append(params.get("projectCycle"));
			}
			
			if(params.containsKey("projectType")){
				sb.append(" and ids.projectType=");
				sb.append(params.get("projectType"));
			}
			
			if(params.containsKey("project")){
				if("empty".equals(params.get("project"))){
					sb.append(" and ids.project is null");
				}else{
					sb.append(" and ids.project=");
					sb.append(params.get("project"));
				}
			}
			
			if (params.containsKey("year")) {
				if("empty".equals(params.get("year"))){
					sb.append(" and ids.year is null");
				}else{
					sb.append(" and ids.year='");
					sb.append(params.get("year")+"'");
				}
			}
			
			if (params.containsKey("trainingSubject")) {
				if("empty".equals(params.get("trainingSubject"))){
					sb.append(" and ids.trainingSubject is null");
				}else{
					sb.append(" and ids.trainingSubject=");
					sb.append(params.get("trainingSubject"));
				}
			}
			
			if(params.containsKey("status")){
				sb.append(" and ids.status=");
				sb.append(params.get("status"));
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
