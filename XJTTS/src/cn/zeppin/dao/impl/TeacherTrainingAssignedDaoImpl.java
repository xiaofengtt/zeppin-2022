package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingAssignedDao;
import cn.zeppin.entity.TeacherTrainingAssigned;
@SuppressWarnings("rawtypes")
public class TeacherTrainingAssignedDaoImpl extends BaseDaoImpl<TeacherTrainingAssigned, Integer> implements ITeacherTrainingAssignedDao {

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
		sb.append("select count(*) from TeacherTrainingAssigned tta,Teacher t,Project p,TrainingSubject ts where 1=1 ");
		sb.append(" and tta.teacher=t.id and tta.project=p.id and tta.subject=ts.id ");
		
		if(searchMap.get("project")!=null && !searchMap.get("project").equals("0")){
			sb.append(" and tta.project=").append(searchMap.get("project"));
		}
		if(searchMap.get("subject")!=null && !searchMap.get("subject").equals("0")){
			sb.append(" and tta.subject=").append(searchMap.get("subject"));
		}
		if(searchMap.get("status")!=null){
			sb.append(" and tta.status=").append(searchMap.get("status"));
		}
		if(searchMap.get("teacher")!=null && !searchMap.get("teacher").equals("0")){
			sb.append(" and tta.teacher=").append(searchMap.get("teacher"));
		}
		
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	public List getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from TeacherTrainingAssigned tta,Teacher t,Project p,TrainingSubject ts where 1=1 ");
		
		sb.append(" and tta.teacher=t.id and tta.project=p.id and tta.subject=ts.id ");
		
		if(searchMap.get("project")!=null && !searchMap.get("project").equals("0")){
			sb.append(" and tta.project=").append(searchMap.get("project"));
		}
		if(searchMap.get("subject")!=null && !searchMap.get("subject").equals("0")){
			sb.append(" and tta.subject=").append(searchMap.get("subject"));
		}
		if(searchMap.get("status")!=null){
			sb.append(" and tta.status=").append(searchMap.get("status"));
		}
		if(searchMap.get("teacher")!=null && !searchMap.get("teacher").equals("0")){
			sb.append(" and tta.teacher=").append(searchMap.get("teacher"));
		}
		
		sb.append(" order by tta.createtime ");
		
		if(offset==null || length==null){
			return this.getListByHSQL(sb.toString());
		}else{
			return this.getListForPage(sb.toString(), offset, length);
		}
	}
}
