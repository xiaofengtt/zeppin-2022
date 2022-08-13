/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public abstract class ScormCourse {

	private String courseId;

	private String courseTitle;

	private String controlType;

	private String version;

	private String description;
	
	//����μ�������
	private String navigate;
	
	private String address;
	
	private String course_code;

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public abstract List getItems();

	public String getNavigate() {
		return navigate;
	}

	public void setNavigate(String navigate) {
		this.navigate = navigate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	
}
