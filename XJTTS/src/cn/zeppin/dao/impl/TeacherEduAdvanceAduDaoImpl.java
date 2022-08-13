package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherEduAdvanceAduDao;
import cn.zeppin.entity.TeacherEduAdvanceAdu;

public class TeacherEduAdvanceAduDaoImpl extends
		BaseDaoImpl<TeacherEduAdvanceAdu, Integer> implements ITeacherEduAdvanceAduDao {

	@Override
	public List<TeacherEduAdvanceAdu> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherEduAdvanceAdu teaa where 1=1");
		if(params != null){
			if(params.containsKey("teacherEduAdvance")){
				sb.append(" and teaa.teacherEduAdvance=");
				sb.append(params.get("teacherEduAdvance"));
			}
			
			if(params.containsKey("checker")){
				sb.append(" and teaa.checker=");
				sb.append(params.get("checker"));
			}
			
			if(params.containsKey("type")){
				sb.append(" and teaa.type=");
				sb.append(params.get("type"));
			}
			
			if(params.containsKey("reason")){
				sb.append(" and teaa.reason like '%");
				sb.append(params.get("reason"));
				sb.append("%'");
			}
		}
		
		sb.append("order by teaa.");
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherEduAdvanceAdu teaa where 1=1");
		if(params != null){
			if(params.containsKey("teacherEduAdvance")){
				sb.append(" and teaa.teacherEduAdvance=");
				sb.append(params.get("teacherEduAdvance"));
			}
			
			if(params.containsKey("checker")){
				sb.append(" and teaa.checker=");
				sb.append(params.get("checker"));
			}
			
			if(params.containsKey("type")){
				sb.append(" and teaa.type=");
				sb.append(params.get("type"));
			}
			
			if(params.containsKey("reason")){
				sb.append(" and teaa.reason like '%");
				sb.append(params.get("reason"));
				sb.append("%'");
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
