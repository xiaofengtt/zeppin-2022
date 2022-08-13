package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitStudentScore implements Items {
	private String id;

	private RecruitStudent student;

	private RecruitTestCourse testCourse;

	private String score;

	private String status;

	public RecruitTestCourse getTestCourse() {
		return testCourse;
	}

	public void setTestCourse(RecruitTestCourse testCourse) {
		this.testCourse = testCourse;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RecruitStudent getStudent() {
		return student;
	}

	public void setStudent(RecruitStudent student) {
		this.student = student;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
