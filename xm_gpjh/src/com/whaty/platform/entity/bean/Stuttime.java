package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * Stuttime entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Stuttime extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private Date startDate;
	private String fkSsouserId;
	private Date endDate;

	// Constructors

	/** default constructor */
	public Stuttime() {
	}

	/** full constructor */
	public Stuttime(PrBzzTchOpencourse prBzzTchOpencourse, Date startDate,
			String fkSsouserId, Date endDate) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.startDate = startDate;
		this.fkSsouserId = fkSsouserId;
		this.endDate = endDate;
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

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getFkSsouserId() {
		return this.fkSsouserId;
	}

	public void setFkSsouserId(String fkSsouserId) {
		this.fkSsouserId = fkSsouserId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}