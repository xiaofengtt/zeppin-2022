package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITeacherDisableRecordsDao;
import cn.zeppin.entity.TeacherDisableRecords;
import cn.zeppin.service.api.ITeacherDisableRecordsService;

public class TeacherDisableRecordsService implements ITeacherDisableRecordsService {
	private ITeacherDisableRecordsDao teacherDisableRecordsDao;

	public ITeacherDisableRecordsDao getTeacherDisableRecordsDao() {
		return teacherDisableRecordsDao;
	}

	public void setTeacherDisableRecordsDao(ITeacherDisableRecordsDao teacherDisableRecordsDao) {
		this.teacherDisableRecordsDao = teacherDisableRecordsDao;
	}

	@Override
	public List<TeacherDisableRecords> searchTeacherDisableRecords(Map<String, Object> searchMap,
			Map<String, String> sortParams, int offset, int length) {
		return this.teacherDisableRecordsDao.searchTeacherDisableRecords(searchMap, sortParams, offset, length);
	}

	@Override
	public TeacherDisableRecords add(TeacherDisableRecords teacherDisableRecords) {

		return this.getTeacherDisableRecordsDao().save(teacherDisableRecords);
	}
}
