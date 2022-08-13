package cn.zeppin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherExamRecords;

public interface ITeacherExamRecordsService extends IBaseService<TeacherExamRecords, Integer>{
	public List<String> getYearList();
	public List<String> getExamByParams(HashMap<String, String> searchMap);
	public Integer getCountByParams(HashMap<String,String> searchMap);
	public List<TeacherExamRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer start, Integer limit);
	public void addTeacherExamRecords(String year, String exam, Integer rowCount ,List<HashMap<String,Object>> infomationList);
}
