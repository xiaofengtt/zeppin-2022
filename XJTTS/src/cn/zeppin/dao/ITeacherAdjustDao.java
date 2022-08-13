package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherAdjust;
@SuppressWarnings("rawtypes")
public interface ITeacherAdjustDao extends IBaseDao<TeacherAdjust, Integer> {
	
	
	public List getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap, int offset, int length);
	
	public int getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap);

}
