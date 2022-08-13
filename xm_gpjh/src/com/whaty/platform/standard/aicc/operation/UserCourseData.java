package com.whaty.platform.standard.aicc.operation;

import com.whaty.platform.standard.aicc.model.AiccData;

public class UserCourseData extends AiccData{

	private String completePercent="0";
	
	private AiccCourse aiccCourse;
	

	
	public AiccCourse getAiccCourse() {
		return aiccCourse;
	}

	public void setAiccCourse(AiccCourse aiccCourse) {
		this.aiccCourse = aiccCourse;
	}

	public UserCourseData() {
		super();
		
	}

	public String getCompletePercent() {
		return completePercent;
	}

	public void setCompletePercent(String completePercent) {
		this.completePercent = completePercent;
	}
}
