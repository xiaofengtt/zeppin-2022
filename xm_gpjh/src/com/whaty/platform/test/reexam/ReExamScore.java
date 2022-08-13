package com.whaty.platform.test.reexam;

import com.whaty.platform.Items;

public abstract class ReExamScore implements Items {
	private String score;

	private ReExamUser examUser;

	private ReExamCourse examCourse;

	public ReExamCourse getExamCourse() {
		return examCourse;
	}

	public void setExamCourse(ReExamCourse examCourse) {
		this.examCourse = examCourse;
	}

	public ReExamUser getExamUser() {
		return examUser;
	}

	public void setExamUser(ReExamUser examUser) {
		this.examUser = examUser;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
