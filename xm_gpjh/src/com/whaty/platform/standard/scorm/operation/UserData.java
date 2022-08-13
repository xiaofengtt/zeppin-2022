/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;

/**
 * @author Administrator
 *
 */
public abstract class UserData {
	private String student_id;
	private String student_name;
	
	public abstract List getSelectedCourses() throws ScormException;

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
