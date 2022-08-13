package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestExambatchInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestExambatchInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date SDate;
	private Date EDate;
	private String status;
	private Date examroomSDate;
	private Date examroomEDate;

	// Constructors

	/** default constructor */
	public TestExambatchInfo() {
	}

	/** minimal constructor */
	public TestExambatchInfo(String name) {
		this.name = name;
	}

	/** full constructor */
	public TestExambatchInfo(String name, Date SDate, Date EDate,
			String status, Date examroomSDate, Date examroomEDate) {
		this.name = name;
		this.SDate = SDate;
		this.EDate = EDate;
		this.status = status;
		this.examroomSDate = examroomSDate;
		this.examroomEDate = examroomEDate;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getExamroomSDate() {
		return this.examroomSDate;
	}

	public void setExamroomSDate(Date examroomSDate) {
		this.examroomSDate = examroomSDate;
	}

	public Date getExamroomEDate() {
		return this.examroomEDate;
	}

	public void setExamroomEDate(Date examroomEDate) {
		this.examroomEDate = examroomEDate;
	}

}