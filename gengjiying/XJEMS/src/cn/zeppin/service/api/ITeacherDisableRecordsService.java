package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeacherDisableRecords;

public interface ITeacherDisableRecordsService {
	public List<TeacherDisableRecords> searchTeacherDisableRecords(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length);

	public TeacherDisableRecords add(TeacherDisableRecords teacherDisableRecords);
}
