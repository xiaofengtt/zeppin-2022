package com.whaty.platform.test.reexam;

import com.whaty.platform.Items;
import com.whaty.platform.test.TestUser;

public abstract class ReExamUser implements Items {
	private TestUser testUser;

	private ReExamBatch examBatch;

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

	public ReExamBatch getExamBatch() {
		return examBatch;
	}

	public void setExamBatch(ReExamBatch examBatch) {
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
