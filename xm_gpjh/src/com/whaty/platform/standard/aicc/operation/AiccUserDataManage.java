package com.whaty.platform.standard.aicc.operation;

import java.util.List;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.model.Objectives;

public abstract class AiccUserDataManage {
	
	private AiccUserDataManagePriv userpriv; 
	
	public AiccUserDataManagePriv getUserpriv() {
		return userpriv;
	}

	public void setUserpriv(AiccUserDataManagePriv userpriv) {
		this.userpriv = userpriv;
	}

	public abstract List getCoursesDataOfUser() throws AiccException;
	
	public abstract UserCourseData getCourseDataOfUser(String courseId) throws AiccException;
	
	public abstract List getCourses() throws AiccException;
	
	public abstract AiccCourse getCourse(String course_id) throws AiccException;
	
	public abstract List getAUsDataOfUser(String course_id) throws AiccException;
	
	public abstract Objectives getObjectivesDataOfUser(String course_id,String ausystem_id) throws AiccException;

}
