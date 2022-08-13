package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeFeeLevel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeFeeLevel extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String flagDefault;
	private String flagBak;
	private Double feePercredit;
	private Double oweFeeLimit;
	private String note;
	private Set peStudents = new HashSet(0);
	private Set prEduMajorSiteFeeLevels = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeFeeLevel() {
	}

	/** minimal constructor */
	public PeFeeLevel(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeFeeLevel(String name, String flagDefault, String flagBak,
			Double feePercredit, Double oweFeeLimit, String note,
			Set peStudents, Set prEduMajorSiteFeeLevels) {
		this.name = name;
		this.flagDefault = flagDefault;
		this.flagBak = flagBak;
		this.feePercredit = feePercredit;
		this.oweFeeLimit = oweFeeLimit;
		this.note = note;
		this.peStudents = peStudents;
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlagDefault() {
		return this.flagDefault;
	}

	public void setFlagDefault(String flagDefault) {
		this.flagDefault = flagDefault;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Double getFeePercredit() {
		return this.feePercredit;
	}

	public void setFeePercredit(Double feePercredit) {
		this.feePercredit = feePercredit;
	}

	public Double getOweFeeLimit() {
		return this.oweFeeLimit;
	}

	public void setOweFeeLimit(Double oweFeeLimit) {
		this.oweFeeLimit = oweFeeLimit;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

	public Set getPrEduMajorSiteFeeLevels() {
		return this.prEduMajorSiteFeeLevels;
	}

	public void setPrEduMajorSiteFeeLevels(Set prEduMajorSiteFeeLevels) {
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
	}

}