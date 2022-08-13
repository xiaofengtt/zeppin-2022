package com.whaty.platform.test.reexam;

import com.whaty.platform.Items;

public abstract class ReExamRoom implements Items {
	private String id;

	private String name;

	private String address;

	private String roomNo;

	private String roomNum;

	private ReExamCourse examCourse;

	private String teacher;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ReExamCourse getExamCourse() {
		return examCourse;
	}

	public void setExamCourse(ReExamCourse examCourse) {
		this.examCourse = examCourse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

}
