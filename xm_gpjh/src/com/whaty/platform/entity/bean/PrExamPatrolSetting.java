package com.whaty.platform.entity.bean;

/**
 * PrExamPatrolSetting entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrExamPatrolSetting extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeExamPatrol peExamPatrol;
	private PeSite peSite;
	private PeSemester peSemester;

	// Constructors

	/** default constructor */
	public PrExamPatrolSetting() {
	}

	/** full constructor */
	public PrExamPatrolSetting(PeExamPatrol peExamPatrol, PeSite peSite,
			PeSemester peSemester) {
		this.peExamPatrol = peExamPatrol;
		this.peSite = peSite;
		this.peSemester = peSemester;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeExamPatrol getPeExamPatrol() {
		return this.peExamPatrol;
	}

	public void setPeExamPatrol(PeExamPatrol peExamPatrol) {
		this.peExamPatrol = peExamPatrol;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

}