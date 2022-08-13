package com.whaty.platform.test.history;

import java.util.HashMap;

import com.whaty.platform.Items;

public abstract class PaperHistory implements Items {

	private String id;

	private String userId;

	private String testPaperId;

	private String testDate;

	private String testResult;

	private String score;
	
	private String status;

	private String openCourseId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTestPaperId() {
		return testPaperId;
	}

	public void setTestPaperId(String testPaperId) {
		this.testPaperId = testPaperId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	protected abstract void setTestResult(HashMap answers);

	protected abstract HashMap getTestResultMap();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpenCourseId() {
		return openCourseId;
	}

	public void setOpenCourseId(String openCourseId) {
		this.openCourseId = openCourseId;
	}
}
