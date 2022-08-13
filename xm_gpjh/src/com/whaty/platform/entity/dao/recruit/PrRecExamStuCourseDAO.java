package com.whaty.platform.entity.dao.recruit;

import java.util.List;

import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PrRecExamStuCourseDAO extends AbstractEntityDao<PrRecExamStuCourse,String> {
	
	
	/**
	 * 从学生表PeRecStudent和关系表PR_REC_COURSE_MAJOR_EDUTYPE中获取：学生id和课程id
	 */
	public List getStudentAndCourseId();
	
	/**
	 * 通过考点id和课程id获得相应考点参与该课程考试的考场号
	 * @param sitemanagerId
	 * @param courseId
	 * @return
	 */
	public List getRoomNos(String sitemanagerId,String courseId);
	/**
	 * 从数据库中删除已经存在的且处于当前活动批次下的学生课程关系记录
	 */
	public void removeActiveStudents();

}
