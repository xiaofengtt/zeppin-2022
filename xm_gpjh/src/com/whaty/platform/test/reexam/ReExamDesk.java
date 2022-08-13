package com.whaty.platform.test.reexam;

import com.whaty.platform.Items;

public abstract class ReExamDesk implements Items{
	private ReExamUser examUser;

	private ReExamCourse examCourse;

	private ReExamRoom examRoom;

	private String deskNo;

	public String getDeskNo() {
		return deskNo;
	}

	public void setDeskNo(String deskNo) {
		this.deskNo = deskNo;
	}

	public ReExamCourse getExamCourse() {
		return examCourse;
	}

	public void setExamCourse(ReExamCourse examCourse) {
		this.examCourse = examCourse;
	}

	public ReExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ReExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public ReExamUser getExamUser() {
		return examUser;
	}

	public void setExamUser(ReExamUser examUser) {
		this.examUser = examUser;
	}

}
