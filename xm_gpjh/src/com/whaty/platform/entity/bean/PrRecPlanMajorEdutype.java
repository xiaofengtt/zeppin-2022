package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrRecPlanMajorEdutype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrRecPlanMajorEdutype extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeMajor peMajor;
	private PeRecruitplan peRecruitplan;
	private PeEdutype peEdutype;
	private Set prRecPlanMajorSites = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrRecPlanMajorEdutype() {
	}

	/** full constructor */
	public PrRecPlanMajorEdutype(PeMajor peMajor, PeRecruitplan peRecruitplan,
			PeEdutype peEdutype, Set prRecPlanMajorSites) {
		this.peMajor = peMajor;
		this.peRecruitplan = peRecruitplan;
		this.peEdutype = peEdutype;
		this.prRecPlanMajorSites = prRecPlanMajorSites;
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

	public PeRecruitplan getPeRecruitplan() {
		return this.peRecruitplan;
	}

	public void setPeRecruitplan(PeRecruitplan peRecruitplan) {
		this.peRecruitplan = peRecruitplan;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

	public Set getPrRecPlanMajorSites() {
		return this.prRecPlanMajorSites;
	}

	public void setPrRecPlanMajorSites(Set prRecPlanMajorSites) {
		this.prRecPlanMajorSites = prRecPlanMajorSites;
	}

}