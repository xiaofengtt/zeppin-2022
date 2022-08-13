package com.whaty.platform.courseware.basic;

import com.whaty.platform.config.CoursewareType;

public abstract class WhatyOnlineCourseware extends Courseware{

	private String templateId;
	
	private String buildFlag;

	
	private String coursewareAbsPath;
	
	private String coursewareRefUri;
	
	
	
	
	
	public WhatyOnlineCourseware() {
		super();
		this.setCoursewareType(CoursewareType.WHATYONLINE);
	}
	
	
	public String getCoursewareAbsPath() {
		return coursewareAbsPath;
	}



	public void setCoursewareAbsPath(String coursewareAbsPath) {
		this.coursewareAbsPath = coursewareAbsPath;
	}



	public String getBuildFlag() {
		return buildFlag;
	}


	public void setBuildFlag(String buildFlag) {
		this.buildFlag = buildFlag;
	}


	public String getCoursewareRefUri() {
		return coursewareRefUri;
	}



	public void setCoursewareRefUri(String coursewareRefUri) {
		this.coursewareRefUri = coursewareRefUri;
	}



	public String getTemplateId() {
		return templateId;
	}



	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	
	
}
