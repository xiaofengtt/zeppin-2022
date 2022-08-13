package com.whaty.platform.entity.bean;

/**
 * PrTraingCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTraingCourse extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeTeacher peTeacher;
	private String fkTraingPlanId;
	private String fkCourseId;

	// Constructors

	/** default constructor */
	public PrTraingCourse() {
	}

	/** full constructor */
	public PrTraingCourse(PeTeacher peTeacher, String fkTraingPlanId,
			String fkCourseId) {
		this.peTeacher = peTeacher;
		this.fkTraingPlanId = fkTraingPlanId;
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

	public String getFkTraingPlanId() {
		return this.fkTraingPlanId;
	}

	public void setFkTraingPlanId(String fkTraingPlanId) {
		this.fkTraingPlanId = fkTraingPlanId;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

}