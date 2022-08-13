package cn.zeppin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.OtherTrainingRecords;

public interface IOtherTrainingRecordsDao extends IBaseDao<OtherTrainingRecords, Integer> {
	public List<String> getProjectYearList();
	public List<String> getProjectNameByParams(HashMap<String, String> searchMap);
	public Integer getCountByParams(HashMap<String,String> searchMap);
	public List<OtherTrainingRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer start, Integer limit);
}
