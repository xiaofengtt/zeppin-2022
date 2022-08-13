package com.whaty.platform.entity.bean;

/**
 * PrPcOpencourseTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcOpencourseTeacher extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrPcOpencourse prPcOpencourse;
	private String fkTeacherId;

	// Constructors

	/** default constructor */
	public PrPcOpencourseTeacher() {
	}

	/** full constructor */
	public PrPcOpencourseTeacher(PrPcOpencourse prPcOpencourse,
			String fkTeacherId) {
		this.prPcOpencourse = prPcOpencourse;
		this.fkTeacherId = fkTeacherId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public String getFkTeacherId() {
		return this.fkTeacherId;
	}

	public void setFkTeacherId(String fkTeacherId) {
		this.fkTeacherId = fkTeacherId;
	}

}