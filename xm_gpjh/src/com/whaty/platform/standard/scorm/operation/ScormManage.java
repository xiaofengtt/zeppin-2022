package com.whaty.platform.standard.scorm.operation;

import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;


public abstract class ScormManage {

	public abstract List getScormCourses();
	
	public abstract int addCourse(String courseId,String CourseTitle,String controlType);
	
	public abstract ScormCourse getScormCourse(String courseId) throws ScormException;
	
	public abstract List getUserCourses(String userId) throws ScormException;
	
	public abstract List getCoursesItems(String courseId)throws ScormException;
	
	public abstract List getCoursesScos(String courseId)throws ScormException;
	
	public abstract List getUserScos(String userId,String courseId)throws ScormException;
	
	public abstract int updateUserScoStatus(String userId,String courseId,String systemId, String lessonStatus,String lessonExit,String lessonEntry,String lessonTime) throws ScormException;
	
	public abstract List getUserScoDatas(String userId,String couseId,String systemId) throws ScormException;

	public abstract UserScoData getUserScoData(String userId,String couseId,String systemId) throws ScormException;

	public abstract SCODataManager getFromDB(String userId,String CourseId,String SystemId) throws ScormException;
	
	public abstract void putIntoDB(SCODataManager scoData,String userId,String CourseId,String SystemId,String openId) throws ScormException;
	
	public int updateUserScoStatus(String userId,String courseId,String systemId, String lessonStatus) throws ScormException{
		return updateUserScoStatus(userId,courseId,systemId, lessonStatus,null,null,null);
	}
	public abstract String getScoIdByItem(String itemid,String FK_SCORM_COURSE_ID);
}
