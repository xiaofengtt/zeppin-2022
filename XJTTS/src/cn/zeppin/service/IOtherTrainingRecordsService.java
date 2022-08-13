package cn.zeppin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.OtherTrainingRecords;
import cn.zeppin.entity.ProjectType;

public interface IOtherTrainingRecordsService extends IBaseService<OtherTrainingRecords, Integer>{
	public List<String> getProjectYearList();
	public List<String> getProjectNameByParams(HashMap<String, String> searchMap);
	public Integer getCountByParams(HashMap<String,String> searchMap);
	public List<OtherTrainingRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer start, Integer limit);
	public void addOtherTrainingRecords(String year , ProjectType projectType , String projectName , String shortName, Integer rowCount ,List<HashMap<String,Object>> infomationList);
}
