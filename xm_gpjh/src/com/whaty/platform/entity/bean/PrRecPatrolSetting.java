package com.whaty.platform.entity.bean;

/**
 * PrRecPatrolSetting entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrRecPatrolSetting extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeExamPatrol peExamPatrol;
	private PeSite peSite;
	private PeRecruitplan peRecruitplan;

	// Constructors

	/** default constructor */
	public PrRecPatrolSetting() {
	}

	/** full constructor */
	public PrRecPatrolSetting(PeExamPatrol peExamPatrol, PeSite peSite,
			PeRecruitplan peRecruitplan) {
		this.peExamPatrol = peExamPatrol;
		this.peSite = peSite;
		this.peRecruitplan = peRecruitplan;
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

	public PeRecruitplan getPeRecruitplan() {
		return this.peRecruitplan;
	}

	public void setPeRecruitplan(PeRecruitplan peRecruitplan) {
		this.peRecruitplan = peRecruitplan;
	}

}