package com.whaty.platform.entity.bean;

/**
 * PrTchOpencourseTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchOpencourseTeacher extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrTchOpencourse prTchOpencourse;
	private String fkTeacherId;

	// Constructors

	/** default constructor */
	public PrTchOpencourseTeacher() {
	}

	/** full constructor */
	public PrTchOpencourseTeacher(PrTchOpencourse prTchOpencourse,
			String fkTeacherId) {
		this.prTchOpencourse = prTchOpencourse;
		this.fkTeacherId = fkTeacherId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrTchOpencourse getPrTchOpencourse() {
		return this.prTchOpencourse;
	}

	public void setPrTchOpencourse(PrTchOpencourse prTchOpencourse) {
		this.prTchOpencourse = prTchOpencourse;
	}

	public String getFkTeacherId() {
		return this.fkTeacherId;
	}

	public void setFkTeacherId(String fkTeacherId) {
		this.fkTeacherId = fkTeacherId;
	}

}