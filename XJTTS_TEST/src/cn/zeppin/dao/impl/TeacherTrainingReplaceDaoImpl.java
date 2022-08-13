package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingReplaceDao;
import cn.zeppin.entity.TeacherTrainingReplace;

public class TeacherTrainingReplaceDaoImpl extends BaseDaoImpl<TeacherTrainingReplace, Integer> implements
		ITeacherTrainingReplaceDao {

	@Override
	public List<TeacherTrainingReplace> getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append(" from TeacherTrainingReplace ttre where 1=1 ");
		
		
		if(params != null && params.size()>0){
			if(params.containsKey("projectId") && params.get("projectId") != null && Integer.parseInt(params.get("projectId").toString()) > 0){
				sb.append(" and ttre.project=");
				sb.append(params.get("projectId"));
			}
			if(params.containsKey("subjectId") && params.get("subjectId") != null && Integer.parseInt(params.get("subjectId").toString()) > 0){
				sb.append(" and ttre.subject=");
				sb.append(params.get("subjectId"));
			}
			if(params.containsKey("trainCollege") && params.get("trainCollege") != null && Integer.parseInt(params.get("trainCollege").toString()) > 0){
				sb.append(" and ttre.trainCollege=");
				sb.append(params.get("trainCollege"));
			}
			if(params.containsKey("status") && params.get("status") != null && Integer.parseInt(params.get("status").toString()) > -1){
				sb.append(" and ttre.replaceStatus=");
				sb.append(params.get("status"));
			}
			if(params.containsKey("teacher") && params.get("teacher") != null && Integer.parseInt(params.get("teacher").toString()) > -1){
				sb.append(" and ttre.teacher=");
				sb.append(params.get("teacher"));
			}
		}
		
		if (sortMap != null && sortMap.size() > 0) {
			sb.append(" order by ");
			for (String key : sortMap.keySet()) {
				String value = (String)sortMap.get(key);
				sb.append("ttre." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		
		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append("select count(*) from TeacherTrainingReplace ttre where 1=1 ");
		
		
		if(params != null && params.size()>0){
			if(params.containsKey("projectId") && params.get("projectId") != null && Integer.parseInt(params.get("projectId").toString()) > 0){
				sb.append(" and ttre.project=");
				sb.append(params.get("projectId"));
			}
			if(params.containsKey("subjectId") && params.get("subjectId") != null && Integer.parseInt(params.get("subjectId").toString()) > 0){
				sb.append(" and ttre.subject=");
				sb.append(params.get("subjectId"));
			}
			if(params.containsKey("trainCollege") && params.get("trainCollege") != null && Integer.parseInt(params.get("trainCollege").toString()) > 0){
				sb.append(" and ttre.trainCollege=");
				sb.append(params.get("trainCollege"));
			}
			if(params.containsKey("status") && params.get("status") != null && Integer.parseInt(params.get("status").toString()) > -1){
				sb.append(" and ttre.replaceStatus=");
				sb.append(params.get("status"));
			}
			
			if(params.containsKey("teacher") && params.get("teacher") != null && Integer.parseInt(params.get("teacher").toString()) > -1){
				sb.append(" and ttre.teacher=");
				sb.append(params.get("teacher"));
			}
		}
		
		if (sortMap != null && sortMap.size() > 0) {
			sb.append(" order by ");
			for (String key : sortMap.keySet()) {
				String value = (String)sortMap.get(key);
				sb.append("ttre." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		int result = ((Long) this.getObjectByHql(sb.toString(), null)).intValue();
		return result;
	}

}
