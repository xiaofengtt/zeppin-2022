package cn.zeppin.service;
	
import java.util.List;

import cn.zeppin.entity.TeacherReviewRecords;

public interface ITeacherReviewRecordsService extends IBaseService<TeacherReviewRecords, Integer> {
	
	public List<TeacherReviewRecords> getTeacherReviewRecordsByTeacherId(int teacherId);
	
}
