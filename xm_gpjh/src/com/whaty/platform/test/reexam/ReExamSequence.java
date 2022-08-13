package com.whaty.platform.test.reexam;

public abstract class ReExamSequence {

	private String id;

	private String title;

	private String startDate;

	private String endDate;

	private String note;

	private String batchId;

	private String basicSequenceId;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBasicSequenceId() {
		return basicSequenceId;
	}

	public void setBasicSequenceId(String basicSequenceId) {
		this.basicSequenceId = basicSequenceId;
	}

}
