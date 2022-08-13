package com.whaty.platform.entity.bean;

/**
 * PrTchOpencourseCourseware entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchOpencourseCourseware extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrTchOpencourse prTchOpencourse;
	private String fkCoursewareId;

	// Constructors

	/** default constructor */
	public PrTchOpencourseCourseware() {
	}

	/** full constructor */
	public PrTchOpencourseCourseware(PrTchOpencourse prTchOpencourse,
			String fkCoursewareId) {
		this.prTchOpencourse = prTchOpencourse;
		this.fkCoursewareId = fkCoursewareId;
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

	public String getFkCoursewareId() {
		return this.fkCoursewareId;
	}

	public void setFkCoursewareId(String fkCoursewareId) {
		this.fkCoursewareId = fkCoursewareId;
	}

}