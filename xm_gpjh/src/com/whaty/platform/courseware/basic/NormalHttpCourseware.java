package com.whaty.platform.courseware.basic;

import com.whaty.platform.config.CoursewareType;

public abstract class NormalHttpCourseware extends Courseware {
	
	private String httpLink;

	public NormalHttpCourseware() {
		this.setCoursewareType(CoursewareType.NORMALHTTP);
	}

	public String getHttpLink() {
		return httpLink;
	}

	public void setHttpLink(String httpLink) {
		this.httpLink = httpLink;
	}
	
	
	
}
