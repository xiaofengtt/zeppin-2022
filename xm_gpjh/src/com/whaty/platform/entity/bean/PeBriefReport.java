package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBriefReport entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBriefReport extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeProApply peProApply;
	private String reportName;
	private String reportFile;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public PeBriefReport() {
	}

	/** full constructor */
	public PeBriefReport(PeProApply peProApply, String reportName,
			String reportFile, Date uploadDate) {
		this.peProApply = peProApply;
		this.reportName = reportName;
		this.reportFile = reportFile;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeProApply getPeProApply() {
		return this.peProApply;
	}

	public void setPeProApply(PeProApply peProApply) {
		this.peProApply = peProApply;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportFile() {
		return this.reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}