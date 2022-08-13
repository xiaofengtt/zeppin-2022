package cn.zeppin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingAssigned;
@SuppressWarnings("rawtypes")
public interface ITeacherTrainingAssginedService extends IBaseService<TeacherTrainingAssigned, Integer>{
	public List<String> getProjectYearList();
	public List<String> getProjectNameByParams(HashMap<String, String> searchMap);
	public Integer getCountByParams(HashMap<String,String> searchMap);
	public List getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer start, Integer limit);
	public void addOtherTrainingRecords(String year , ProjectType projectType , String projectName , String shortName, Integer rowCount ,List<HashMap<String,Object>> infomationList);
}
