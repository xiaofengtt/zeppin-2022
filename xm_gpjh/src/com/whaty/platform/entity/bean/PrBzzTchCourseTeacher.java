package com.whaty.platform.entity.bean;

/**
 * PrBzzTchCourseTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrBzzTchCourseTeacher extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String fkCourseId;
	private String fkTeacherId;
	private String flagTeacherType;
	private String flagBak;

	// Constructors

	/** default constructor */
	public PrBzzTchCourseTeacher() {
	}

	/** full constructor */
	public PrBzzTchCourseTeacher(String fkCourseId, String fkTeacherId,
			String flagTeacherType, String flagBak) {
		this.fkCourseId = fkCourseId;
		this.fkTeacherId = fkTeacherId;
		this.flagTeacherType = flagTeacherType;
		this.flagBak = flagBak;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

	public String getFkTeacherId() {
		return this.fkTeacherId;
	}

	public void setFkTeacherId(String fkTeacherId) {
		this.fkTeacherId = fkTeacherId;
	}

	public String getFlagTeacherType() {
		return this.flagTeacherType;
	}

	public void setFlagTeacherType(String flagTeacherType) {
		this.flagTeacherType = flagTeacherType;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}