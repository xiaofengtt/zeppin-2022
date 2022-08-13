package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherExamRecordsDao;
import cn.zeppin.entity.TeacherExamRecords;

public class TeacherExamRecordsDaoImpl extends BaseDaoImpl<TeacherExamRecords, Integer> implements ITeacherExamRecordsDao {

	@SuppressWarnings("unchecked")
	public List<String> getYearList(){
		String sql = "select year from teacher_exam_records group by year order by year ";
    	return this.getListBySQL(sql, null);
    }
    
	@SuppressWarnings("unchecked")
	public List<String> getExamByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select exam from teacher_exam_records where 1=1");
		if(searchMap.get("year")!=null && !searchMap.get("year").equals("0")){
			sb.append(" and year='").append(searchMap.get("year")).append("'");
		}
		sb.append(" group by exam order by year desc");
    	return this.getListBySQL(sb.toString(), null);
	}
	
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from TeacherExamRecords where 1=1");
		if(searchMap.get("year")!=null && !searchMap.get("year").equals("0")){
			sb.append(" and year='").append(searchMap.get("year")).append("'");
		}
		if(searchMap.get("exam")!=null && !searchMap.get("exam").equals("0")){
			sb.append(" and exam='").append(searchMap.get("exam")).append("'");
		}
		if(searchMap.get("teacher")!=null && !searchMap.get("teacher").equals("0")){
			sb.append(" and teacher=").append(searchMap.get("teacher"));
		}
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	public List<TeacherExamRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherExamRecords where 1=1");
		if(searchMap.get("year")!=null && !searchMap.get("year").equals("0")){
			sb.append(" and year='").append(searchMap.get("year")).append("'");
		}
		if(searchMap.get("exam")!=null && !searchMap.get("exam").equals("0")){
			sb.append(" and exam='").append(searchMap.get("exam")).append("'");
		}
		if(searchMap.get("teacher")!=null && !searchMap.get("teacher").equals("0")){
			sb.append(" and teacher=").append(searchMap.get("teacher"));
		}
		if(offset==null || length==null){
			return this.getListByHSQL(sb.toString());
		}else{
			return this.getListForPage(sb.toString(), offset, length);
		}
	}
}
