package com.whaty.platform.test.exam;

import com.whaty.platform.Items;

public abstract class ExamBatch implements Items {
	private String id;

	private String name;

	private String startDate;

	private String endDate;

	private String status;
	
	private String ExamRoomStartDate;//分配考场开始时间
	
	private String ExamRoomEndDate ;//分配考场结束时间

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

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExamRoomEndDate() {
		return ExamRoomEndDate;
	}

	public void setExamRoomEndDate(String examRoomEndDate) {
		ExamRoomEndDate = examRoomEndDate;
	}

	public String getExamRoomStartDate() {
		return ExamRoomStartDate;
	}

	public void setExamRoomStartDate(String examRoomStartDate) {
		ExamRoomStartDate = examRoomStartDate;
	}

}
