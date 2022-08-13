package com.whaty.platform.entity.bean;

/**
 * PrTchCourseTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchCourseTeacher extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTeacher peTeacher;
	private String fkCourseId;

	// Constructors

	/** default constructor */
	public PrTchCourseTeacher() {
	}

	/** full constructor */
	public PrTchCourseTeacher(PeTeacher peTeacher, String fkCourseId) {
		this.peTeacher = peTeacher;
		this.fkCourseId = fkCourseId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTeacher getPeTeacher() {
		return this.peTeacher;
	}

	public void setPeTeacher(PeTeacher peTeacher) {
		this.peTeacher = peTeacher;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

}