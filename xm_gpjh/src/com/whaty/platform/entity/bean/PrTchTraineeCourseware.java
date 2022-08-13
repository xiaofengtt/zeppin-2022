package com.whaty.platform.entity.bean;

/**
 * PrTchTraineeCourseware entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchTraineeCourseware extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTchCourseware peTchCourseware;
	private String fkTraineeId;

	// Constructors

	/** default constructor */
	public PrTchTraineeCourseware() {
	}

	/** full constructor */
	public PrTchTraineeCourseware(PeTchCourseware peTchCourseware,
			String fkTraineeId) {
		this.peTchCourseware = peTchCourseware;
		this.fkTraineeId = fkTraineeId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchCourseware getPeTchCourseware() {
		return this.peTchCourseware;
	}

	public void setPeTchCourseware(PeTchCourseware peTchCourseware) {
		this.peTchCourseware = peTchCourseware;
	}

	public String getFkTraineeId() {
		return this.fkTraineeId;
	}

	public void setFkTraineeId(String fkTraineeId) {
		this.fkTraineeId = fkTraineeId;
	}

}