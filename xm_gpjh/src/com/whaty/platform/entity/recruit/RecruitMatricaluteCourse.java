package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitMatricaluteCourse implements Items {
	private String id = "";

	private RecruitTestCourse testCourse = null;

	private RecruitMatricaluteCondition matricaluteCondition = null;

	private String score = "";
	
	private String coureName = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RecruitMatricaluteCondition getMatricaluteCondition() {
		return matricaluteCondition;
	}

	public void setMatricaluteCondition(
			RecruitMatricaluteCondition matricaluteCondition) {
		this.matricaluteCondition = matricaluteCondition;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public RecruitTestCourse getTestCourse() {
		return testCourse;
	}

	public void setTestCourse(RecruitTestCourse testCourse) {
		this.testCourse = testCourse;
	}

	public String getCoureName() {
		return coureName;
	}

	public void setCoureName(String coureName) {
		this.coureName = coureName;
	}
	

}
