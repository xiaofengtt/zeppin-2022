package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestExamcourseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestExamcourseInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date SDate;
	private Date EDate;
	private String testBatchId;
	private String courseId;
	private String examsequenceId;
	private String examType;
	private String courseType;

	// Constructors

	/** default constructor */
	public TestExamcourseInfo() {
	}

	/** minimal constructor */
	public TestExamcourseInfo(String name, String testBatchId, String courseId) {
		this.name = name;
		this.testBatchId = testBatchId;
		this.courseId = courseId;
	}

	/** full constructor */
	public TestExamcourseInfo(String name, Date SDate, Date EDate,
			String testBatchId, String courseId, String examsequenceId,
			String examType, String courseType) {
		this.name = name;
		this.SDate = SDate;
		this.EDate = EDate;
		this.testBatchId = testBatchId;
		this.courseId = courseId;
		this.examsequenceId = examsequenceId;
		this.examType = examType;
		this.courseType = courseType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSDate() {
		return this.SDate;
	}

	public void setSDate(Date SDate) {
		this.SDate = SDate;
	}

	public Date getEDate() {
		return this.EDate;
	}

	public void setEDate(Date EDate) {
		this.EDate = EDate;
	}

	public String getTestBatchId() {
		return this.testBatchId;
	}

	public void setTestBatchId(String testBatchId) {
		this.testBatchId = testBatchId;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExamsequenceId() {
		return this.examsequenceId;
	}

	public void setExamsequenceId(String examsequenceId) {
		this.examsequenceId = examsequenceId;
	}

	public String getExamType() {
		return this.examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getCourseType() {
		return this.courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

}