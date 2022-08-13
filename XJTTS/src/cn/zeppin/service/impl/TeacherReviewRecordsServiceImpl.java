package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.ITeacherReviewRecordsDao;
import cn.zeppin.entity.TeacherReviewRecords;
import cn.zeppin.service.ITeacherReviewRecordsService;
	
public class TeacherReviewRecordsServiceImpl extends BaseServiceImpl<TeacherReviewRecords, Integer> implements ITeacherReviewRecordsService {

	private ITeacherReviewRecordsDao teacherReviewRecordsDao;
	

	public ITeacherReviewRecordsDao getTeacherReviewRecordsDao() {
		return teacherReviewRecordsDao;
	}

	public void setTeacherReviewRecordsDao(
			ITeacherReviewRecordsDao teacherReviewRecordsDao) {
		this.teacherReviewRecordsDao = teacherReviewRecordsDao;
	}

	@Override
	public List<TeacherReviewRecords> getTeacherReviewRecordsByTeacherId(
			int teacherId) {
		// TODO Auto-generated method stub
		return this.teacherReviewRecordsDao.getTeacherReviewRecordsByTeacherId(teacherId);
	}

	@Override
	public TeacherReviewRecords add(TeacherReviewRecords t) {
		// TODO Auto-generated method stub
		return this.teacherReviewRecordsDao.add(t);
	}

	@Override
	public TeacherReviewRecords update(TeacherReviewRecords t) {
		// TODO Auto-generated method stub
		return this.teacherReviewRecordsDao.update(t);
	}

	@Override
	public void delete(TeacherReviewRecords t) {
		// TODO Auto-generated method stub
		this.teacherReviewRecordsDao.delete(t);
	}

	

}
