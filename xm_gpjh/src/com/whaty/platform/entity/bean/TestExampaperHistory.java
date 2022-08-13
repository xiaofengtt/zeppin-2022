package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestExampaperHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestExampaperHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private Date testDate;
	private String testResult;
	private String testpaperId;
	private String score;

	// Constructors

	/** default constructor */
	public TestExampaperHistory() {
	}

	/** minimal constructor */
	public TestExampaperHistory(String userId, Date testDate,
			String testpaperId, String score) {
		this.userId = userId;
		this.testDate = testDate;
		this.testpaperId = testpaperId;
		this.score = score;
	}

	/** full constructor */
	public TestExampaperHistory(String userId, Date testDate,
			String testResult, String testpaperId, String score) {
		this.userId = userId;
		this.testDate = testDate;
		this.testResult = testResult;
		this.testpaperId = testpaperId;
		this.score = score;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTestDate() {
		return this.testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getTestResult() {
		return this.testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTestpaperId() {
		return this.testpaperId;
	}

	public void setTestpaperId(String testpaperId) {
		this.testpaperId = testpaperId;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}