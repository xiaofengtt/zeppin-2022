package com.whaty.platform.entity.bean;

/**
 * OnlinetestCoursePaper entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OnlinetestCoursePaper extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String paperId;
	private String testcourseId;

	// Constructors

	/** default constructor */
	public OnlinetestCoursePaper() {
	}

	/** full constructor */
	public OnlinetestCoursePaper(String paperId, String testcourseId) {
		this.paperId = paperId;
		this.testcourseId = testcourseId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaperId() {
		return this.paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getTestcourseId() {
		return this.testcourseId;
	}

	public void setTestcourseId(String testcourseId) {
		this.testcourseId = testcourseId;
	}

}