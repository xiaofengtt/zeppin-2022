package com.whaty.platform.test.exam;

import com.whaty.platform.Items;
import com.whaty.platform.test.TestUser;

public abstract class ExamUser implements Items {
	private TestUser testUser;

	private ExamBatch examBatch;

	private String examcode;

	private String note;

	private String status;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExamBatch getExamBatch() {
		return examBatch;
	}

	public void setExamBatch(ExamBatch examBatch) {
		this.examBatch = examBatch;
	}

	public String getExamcode() {
		return examcode;
	}

	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TestUser getTestUser() {
		return testUser;
	}

	public void setTestUser(TestUser testUser) {
		this.testUser = testUser;
	}

}
