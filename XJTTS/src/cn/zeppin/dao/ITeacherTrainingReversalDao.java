package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherTrainingReversal;

public interface ITeacherTrainingReversalDao extends IBaseDao<TeacherTrainingReversal, Integer> {
	
	public List<TeacherTrainingReversal> getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap, int offset, int length);
	
	public int getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap);

}
