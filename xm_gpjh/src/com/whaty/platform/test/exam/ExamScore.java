package com.whaty.platform.test.exam;

import com.whaty.platform.Items;

public abstract class ExamScore implements Items {
	private String score;

	private ExamUser examUser;

	private ExamCourse examCourse;

	public ExamCourse getExamCourse() {
		return examCourse;
	}

	public void setExamCourse(ExamCourse examCourse) {
		this.examCourse = examCourse;
	}

	public ExamUser getExamUser() {
		return examUser;
	}

	public void setExamUser(ExamUser examUser) {
		this.examUser = examUser;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
