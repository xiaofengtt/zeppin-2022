package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeProImplemt entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeProImplemt extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeProApply peProApply;
	private Date startcourseDate;
	private String noticeName;
	private String briefReportName;
	private String briefReportFile;
	private String noticeContent;
	private Date lastUploadDate;
	private Date courseModifyDate;

	// Constructors

	/** default constructor */
	public PeProImplemt() {
	}

	/** full constructor */
	public PeProImplemt(PeProApply peProApply, Date startcourseDate,
			String noticeName, String briefReportName, String briefReportFile,
			String noticeContent, Date lastUploadDate, Date courseModifyDate) {
		this.peProApply = peProApply;
		this.startcourseDate = startcourseDate;
		this.noticeName = noticeName;
		this.briefReportName = briefReportName;
		this.briefReportFile = briefReportFile;
		this.noticeContent = noticeContent;
		this.lastUploadDate = lastUploadDate;
		this.courseModifyDate = courseModifyDate;
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

	public Date getStartcourseDate() {
		return this.startcourseDate;
	}

	public void setStartcourseDate(Date startcourseDate) {
		this.startcourseDate = startcourseDate;
	}

	public String getNoticeName() {
		return this.noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getBriefReportName() {
		return this.briefReportName;
	}

	public void setBriefReportName(String briefReportName) {
		this.briefReportName = briefReportName;
	}

	public String getBriefReportFile() {
		return this.briefReportFile;
	}

	public void setBriefReportFile(String briefReportFile) {
		this.briefReportFile = briefReportFile;
	}

	public String getNoticeContent() {
		return this.noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Date getLastUploadDate() {
		return this.lastUploadDate;
	}

	public void setLastUploadDate(Date lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

	public Date getCourseModifyDate() {
		return this.courseModifyDate;
	}

	public void setCourseModifyDate(Date courseModifyDate) {
		this.courseModifyDate = courseModifyDate;
	}

}