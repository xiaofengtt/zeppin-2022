package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrProSummary entity. @author MyEclipse Persistence Tools
 */

public class PrProSummary extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeUnit peUnit;
	private PeProApplyno peProApplyno;
	private String summaryFile;
	private Date inputDate;
	private Date lastUploadDate;

	// Constructors

	/** default constructor */
	public PrProSummary() {
	}

	/** full constructor */
	public PrProSummary(PeUnit peUnit, PeProApplyno peProApplyno,
			String summaryFile, Date inputDate, Date lastUploadDate) {
		this.peUnit = peUnit;
		this.peProApplyno = peProApplyno;
		this.summaryFile = summaryFile;
		this.inputDate = inputDate;
		this.lastUploadDate = lastUploadDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

	public String getSummaryFile() {
		return this.summaryFile;
	}

	public void setSummaryFile(String summaryFile) {
		this.summaryFile = summaryFile;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getLastUploadDate() {
		return this.lastUploadDate;
	}

	public void setLastUploadDate(Date lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

}