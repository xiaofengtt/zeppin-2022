package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IOtherTrainingRecordsDao;
import cn.zeppin.entity.OtherTrainingRecords;

public class OtherTrainingRecordsDaoImpl extends BaseDaoImpl<OtherTrainingRecords, Integer> implements IOtherTrainingRecordsDao {

	@SuppressWarnings("unchecked")
	public List<String> getProjectYearList(){
		String sql = "select year from other_training_records group by year order by year ";
    	return this.getListBySQL(sql, null);
    }
    
	@SuppressWarnings("unchecked")
	public List<String> getProjectNameByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select project_name from other_training_records where 1=1");
		if(searchMap.get("year")!=null && !searchMap.get("year").equals("0")){
			sb.append(" and year='").append(searchMap.get("year")).append("'");
		}
		sb.append(" group by project_name order by year desc");
    	return this.getListBySQL(sb.toString(), null);
	}
	
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from OtherTrainingRecords where 1=1");
		if(searchMap.get("year")!=null && !searchMap.get("year").equals("0")){
			sb.append(" and year='").append(searchMap.get("year")).append("'");
		}
		if(searchMap.get("projectType")!=null && !searchMap.get("projectType").equals("0")){
			sb.append(" and projectType=").append(searchMap.get("projectType"));
		}
		if(searchMap.get("projectName")!=null && !searchMap.get("projectName").equals("0")){
			sb.append(" and projectName='").append(searchMap.get("projectName")).append("'");
		}
		if(searchMap.get("trainingSubject")!=null && !searchMap.get("trainingSubject").equals("0")){
			sb.append(" and trainingSubject=").append(searchMap.get("trainingSubject"));
		}
		if(searchMap.get("trainingCollege")!=null && !searchMap.get("trainingCollege").equals("0")){
			sb.append(" and trainingCollege=").append(searchMap.get("trainingCollege"));
		}
		if(searchMap.get("teacher")!=null && !searchMap.get("teacher").equals("0")){
			sb.append(" and teacher=").append(searchMap.get("teacher"));
		}
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	public List<OtherTrainingRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from OtherTrainingRecords where 1=1");
		if(searchMap.get("year")!=null && !searchMap.get("year").equals("0")){
			sb.append(" and year='").append(searchMap.get("year")).append("'");
		}
		if(searchMap.get("projectName")!=null && !searchMap.get("projectName").equals("0")){
			sb.append(" and projectName='").append(searchMap.get("projectName")).append("'");
		}
		if(searchMap.get("trainingSubject")!=null && !searchMap.get("trainingSubject").equals("0")){
			sb.append(" and trainingSubject=").append(searchMap.get("trainingSubject"));
		}
		if(searchMap.get("trainingCollege")!=null && !searchMap.get("trainingCollege").equals("0")){
			sb.append(" and trainingCollege=").append(searchMap.get("trainingCollege"));
		}
		if(offset==null || length==null){
			return this.getListByHSQL(sb.toString());
		}else{
			return this.getListForPage(sb.toString(), offset, length);
		}
	}
}
