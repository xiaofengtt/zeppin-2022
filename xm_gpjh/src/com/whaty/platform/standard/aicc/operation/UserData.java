package com.whaty.platform.standard.aicc.operation;

import java.util.List;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.model.Objectives;

public abstract class UserData {
	private String student_id;
	private String student_name;
	
	public abstract List getCoursesData() throws AiccException;
	
	public abstract UserCourseData getCourseData(String courseId) throws AiccException;

	public abstract List getAUsData(String course_id) throws AiccException;
	
	public abstract Objectives getObjectivsData(String course_id, String ausystem_id) throws AiccException;
	
	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
}
