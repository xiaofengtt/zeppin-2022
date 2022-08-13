package com.whaty.platform.entity.recruit;

import com.whaty.platform.Items;

public abstract class RecruitTestDesk implements Items {
	private String id = "";

	private int numByRoom = 0;

	private int numByCourse = 0;

	private RecruitTestRoom testRoom;

	private RecruitTestCourse testCourse;

	private RecruitStudent student;

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

	public int getNumByCourse() {
		return numByCourse;
	}

	public void setNumByCourse(int numByCourse) {
		this.numByCourse = numByCourse;
	}

	public int getNumByRoom() {
		return numByRoom;
	}

	public void setNumByRoom(int numByRoom) {
		this.numByRoom = numByRoom;
	}

	public RecruitTestCourse getTestCourse() {
		return testCourse;
	}

	public void setTestCourse(RecruitTestCourse testCourse) {
		this.testCourse = testCourse;
	}

	public RecruitTestRoom getTestRoom() {
		return testRoom;
	}

	public void setTestRoom(RecruitTestRoom testRoom) {
		this.testRoom = testRoom;
	}
}
