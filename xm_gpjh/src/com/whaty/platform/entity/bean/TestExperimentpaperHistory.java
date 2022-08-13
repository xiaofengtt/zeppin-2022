package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestExperimentpaperHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestExperimentpaperHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private Date testDate;
	private String testResult;
	private String testpaperId;
	private String score;
	private String ischeck;

	// Constructors

	/** default constructor */
	public TestExperimentpaperHistory() {
	}

	/** minimal constructor */
	public TestExperimentpaperHistory(String userId, Date testDate,
			String testpaperId, String score) {
		this.userId = userId;
		this.testDate = testDate;
		this.testpaperId = testpaperId;
		this.score = score;
	}

	/** full constructor */
	public TestExperimentpaperHistory(String userId, Date testDate,
			String testResult, String testpaperId, String score, String ischeck) {
		this.userId = userId;
		this.testDate = testDate;
		this.testResult = testResult;
		this.testpaperId = testpaperId;
		this.score = score;
		this.ischeck = ischeck;
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

	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

}