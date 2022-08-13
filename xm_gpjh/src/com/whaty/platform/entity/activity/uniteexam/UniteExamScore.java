package com.whaty.platform.entity.activity.uniteexam;

import com.whaty.platform.entity.user.Student;

public abstract class UniteExamScore implements com.whaty.platform.Items {
	private String id;

	private Student student;

	private UniteExamCourse course;

	private String score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UniteExamCourse getCourse() {
		return course;
	}

	public void setCourse(UniteExamCourse course) {
		this.course = course;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
