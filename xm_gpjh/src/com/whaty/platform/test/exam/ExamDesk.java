package com.whaty.platform.test.exam;

import com.whaty.platform.Items;

public abstract class ExamDesk implements Items{
	private ExamUser examUser;

	private ExamCourse examCourse;

	private ExamRoom examRoom;

	private String deskNo;

	public String getDeskNo() {
		return deskNo;
	}

	public void setDeskNo(String deskNo) {
		this.deskNo = deskNo;
	}

	public ExamCourse getExamCourse() {
		return examCourse;
	}

	public void setExamCourse(ExamCourse examCourse) {
		this.examCourse = examCourse;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public ExamUser getExamUser() {
		return examUser;
	}

	public void setExamUser(ExamUser examUser) {
		this.examUser = examUser;
	}

}
