package com.whaty.platform.standard.aicc.operation;

import java.util.List;

public abstract class AiccCourseManage {
	
	private String courseId;
	
	public abstract List getCourseTree();

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
