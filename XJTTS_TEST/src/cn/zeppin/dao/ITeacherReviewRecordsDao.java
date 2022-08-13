package cn.zeppin.dao;
	
import java.util.List;

import cn.zeppin.entity.TeacherReviewRecords;

public interface ITeacherReviewRecordsDao extends IBaseDao<TeacherReviewRecords, Integer> {
	
	public List<TeacherReviewRecords> getTeacherReviewRecordsByTeacherId(int teacherId);
	
}
