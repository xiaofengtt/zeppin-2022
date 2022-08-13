package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherTrainingReplace;

public interface ITeacherTrainingReplaceService extends
		IBaseService<TeacherTrainingReplace, Integer> {
	
	public List<TeacherTrainingReplace> getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap, int offset, int length);
	
	public int getRecordsListByParams(Map<String, Object> params, Map<String, Object> sortMap);

}
