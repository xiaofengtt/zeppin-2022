package cn.zeppin.dao.impl;
	
import java.util.List;

import cn.zeppin.dao.ITeacherReviewRecordsDao;
import cn.zeppin.entity.TeacherReviewRecords;

public class TeacherReviewRecordsDaoImpl extends BaseDaoImpl<TeacherReviewRecords, Integer> implements ITeacherReviewRecordsDao {

	@Override
	public List<TeacherReviewRecords> getTeacherReviewRecordsByTeacherId(int teacherId) {
		
		String hql = "from TeacherReviewRecords t where t.teacher=" + teacherId+" order by t.checkTime ASC";
		return this.getListByHSQL(hql);
		
	}
}
