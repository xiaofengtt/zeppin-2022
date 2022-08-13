package com.whaty.platform.entity.bean;

/**
 * PrEduMajorSiteFeeLevel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrEduMajorSiteFeeLevel extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeMajor peMajor;
	private PeFeeLevel peFeeLevel;
	private PeSite peSite;
	private PeEdutype peEdutype;

	// Constructors

	/** default constructor */
	public PrEduMajorSiteFeeLevel() {
	}

	/** full constructor */
	public PrEduMajorSiteFeeLevel(PeMajor peMajor, PeFeeLevel peFeeLevel,
			PeSite peSite, PeEdutype peEdutype) {
		this.peMajor = peMajor;
		this.peFeeLevel = peFeeLevel;
		this.peSite = peSite;
		this.peEdutype = peEdutype;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeFeeLevel getPeFeeLevel() {
		return this.peFeeLevel;
	}

	public void setPeFeeLevel(PeFeeLevel peFeeLevel) {
		this.peFeeLevel = peFeeLevel;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

}