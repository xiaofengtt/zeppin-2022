package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrRecPlanMajorSite entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrRecPlanMajorSite extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrRecPlanMajorEdutype prRecPlanMajorEdutype;
	private PeSite peSite;
	private Set peRecStudents = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrRecPlanMajorSite() {
	}

	/** full constructor */
	public PrRecPlanMajorSite(PrRecPlanMajorEdutype prRecPlanMajorEdutype,
			PeSite peSite, Set peRecStudents) {
		this.prRecPlanMajorEdutype = prRecPlanMajorEdutype;
		this.peSite = peSite;
		this.peRecStudents = peRecStudents;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrRecPlanMajorEdutype getPrRecPlanMajorEdutype() {
		return this.prRecPlanMajorEdutype;
	}

	public void setPrRecPlanMajorEdutype(
			PrRecPlanMajorEdutype prRecPlanMajorEdutype) {
		this.prRecPlanMajorEdutype = prRecPlanMajorEdutype;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public Set getPeRecStudents() {
		return this.peRecStudents;
	}

	public void setPeRecStudents(Set peRecStudents) {
		this.peRecStudents = peRecStudents;
	}

}