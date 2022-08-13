package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzAssess entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzAssess extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private PeBzzCourseFeedback peBzzCourseFeedback;
	private String fkStudentId;
	private Date dataDate;
	private String assess;

	// Constructors

	/** default constructor */
	public PeBzzAssess() {
	}

	/** full constructor */
	public PeBzzAssess(PrBzzTchOpencourse prBzzTchOpencourse,
			PeBzzCourseFeedback peBzzCourseFeedback, String fkStudentId,
			Date dataDate, String assess) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.peBzzCourseFeedback = peBzzCourseFeedback;
		this.fkStudentId = fkStudentId;
		this.dataDate = dataDate;
		this.assess = assess;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return this.prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public PeBzzCourseFeedback getPeBzzCourseFeedback() {
		return this.peBzzCourseFeedback;
	}

	public void setPeBzzCourseFeedback(PeBzzCourseFeedback peBzzCourseFeedback) {
		this.peBzzCourseFeedback = peBzzCourseFeedback;
	}

	public String getFkStudentId() {
		return this.fkStudentId;
	}

	public void setFkStudentId(String fkStudentId) {
		this.fkStudentId = fkStudentId;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getAssess() {
		return this.assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}

}