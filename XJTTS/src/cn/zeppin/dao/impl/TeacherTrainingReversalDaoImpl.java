package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingReversalDao;
import cn.zeppin.entity.TeacherTrainingReversal;

public class TeacherTrainingReversalDaoImpl extends BaseDaoImpl<TeacherTrainingReversal, Integer> implements
		ITeacherTrainingReversalDao {

	@Override
	public List<TeacherTrainingReversal> getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append(" from TeacherTrainingReversal ttre where 1=1 ");
		
		
		if(params != null && params.size()>0){
			if(params.containsKey("projectId") && params.get("projectId") != null && Integer.parseInt(params.get("projectId").toString()) > 0){
				sb.append(" and ttre.project=");
				sb.append(params.get("projectId"));
			}
			if(params.containsKey("subjectId") && params.get("subjectId") != null && Integer.parseInt(params.get("subjectId").toString()) > 0){
				sb.append(" and ttre.trainingSubject=");
				sb.append(params.get("subjectId"));
			}
			if(params.containsKey("classes") && params.get("classes") != null && Integer.parseInt(params.get("classes").toString()) > 0){
				sb.append(" and ttre.oldClasses=");
				sb.append(params.get("classes"));
			}
			if(params.containsKey("trainingCollege") && params.get("trainingCollege") != null && Integer.parseInt(params.get("trainingCollege").toString()) > 0){
				sb.append(" and ttre.trainingCollege=");
				sb.append(params.get("trainingCollege"));
			}
			if(params.containsKey("status") && params.get("status") != null && Integer.parseInt(params.get("status").toString()) > -2){
				sb.append(" and ttre.status=");
				sb.append(params.get("status"));
			}
			if(params.containsKey("teacher") && params.get("teacher") != null && Integer.parseInt(params.get("teacher").toString()) > 0){
				sb.append(" and ttre.teacher=");
				sb.append(params.get("teacher"));
			}
			if(params.containsKey("teacherTrainingRecords") && params.get("teacherTrainingRecords") != null && Integer.parseInt(params.get("teacherTrainingRecords").toString()) > -1){
				sb.append(" and ttre.teacherTrainingRecords=");
				sb.append(params.get("teacherTrainingRecords"));
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
		
		sb.append("select count(*) from TeacherTrainingReversal ttre where 1=1 ");
		
		
		if(params != null && params.size()>0){
			if(params.containsKey("projectId") && params.get("projectId") != null && Integer.parseInt(params.get("projectId").toString()) > 0){
				sb.append(" and ttre.project=");
				sb.append(params.get("projectId"));
			}
			if(params.containsKey("subjectId") && params.get("subjectId") != null && Integer.parseInt(params.get("subjectId").toString()) > 0){
				sb.append(" and ttre.trainingSubject=");
				sb.append(params.get("subjectId"));
			}
			if(params.containsKey("classes") && params.get("classes") != null && Integer.parseInt(params.get("classes").toString()) > 0){
				sb.append(" and ttre.oldClasses=");
				sb.append(params.get("classes"));
			}
			if(params.containsKey("trainingCollege") && params.get("trainingCollege") != null && Integer.parseInt(params.get("trainingCollege").toString()) > 0){
				sb.append(" and ttre.trainingCollege=");
				sb.append(params.get("trainingCollege"));
			}
			if(params.containsKey("status") && params.get("status") != null && Integer.parseInt(params.get("status").toString()) > -2){
				sb.append(" and ttre.status=");
				sb.append(params.get("status"));
			}
			
			if(params.containsKey("teacher") && params.get("teacher") != null && Integer.parseInt(params.get("teacher").toString()) > 0){
				sb.append(" and ttre.teacher=");
				sb.append(params.get("teacher"));
			}
			
			if(params.containsKey("teacherTrainingRecords") && params.get("teacherTrainingRecords") != null && Integer.parseInt(params.get("teacherTrainingRecords").toString()) > 0){
				sb.append(" and ttre.teacherTrainingRecords=");
				sb.append(params.get("teacherTrainingRecords"));
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
