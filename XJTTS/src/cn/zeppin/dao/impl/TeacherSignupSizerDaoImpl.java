package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherSignupSizerDao;
import cn.zeppin.entity.TeacherSignupSizer;

public class TeacherSignupSizerDaoImpl extends
		BaseDaoImpl<TeacherSignupSizer, Integer> implements ITeacherSignupSizerDao {

	@Override
	public List<TeacherSignupSizer> getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from TeacherSignupSizer tss where 1=1");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and tss.id=");
				sb.append(params.get("id"));
			}
			if(params.containsKey("name")){
				sb.append(" and tss.name like '%");
				sb.append(params.get("name"));
				sb.append("%'");
			}
			if(params.containsKey("status")){
				sb.append(" and tss.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and tss.status>-1");
			}
		}
		if(sort != null && !"".equals(sort)){
			sb.append(" order by tss.");
		}else{
			sb.append(" order by tss.weight asc");
		}
		
		sb.append(sort);
		
		return this.getListForPage(sb.toString(), start, limit);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from TeacherSignupSizer tss where 1=1");
		if(params != null){
			if(params.containsKey("id")){
				sb.append(" and tss.id=");
				sb.append(params.get("id"));
			}
			if(params.containsKey("name")){
				sb.append(" and tss.name like '%");
				sb.append(params.get("name"));
				sb.append("%'");
			}
			if(params.containsKey("status")){
				sb.append(" and tss.status=");
				sb.append(params.get("status"));
			}else{
				sb.append(" and tss.status>-1");
			}
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
