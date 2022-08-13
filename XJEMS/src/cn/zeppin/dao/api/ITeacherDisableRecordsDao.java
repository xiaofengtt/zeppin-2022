package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherDisableRecords;

public interface ITeacherDisableRecordsDao extends IBaseDAO<TeacherDisableRecords, Short> {
	public List<TeacherDisableRecords> searchTeacherDisableRecords(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length);
}
