package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeInfoNewsId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeInfoNewsId extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String fkNewsTypeId;
	private String fkManagerId;
	private String title;
	private String flagNewsStatus;
	private String flagIsactive;
	private Date reportDate;
	private Date submitDate;
	private String confirmManagerId;
	private Long readCount;
	private String note;
	private String picLink;
	private String isnew;
	private String summary;

	// Constructors

	/** default constructor */
	public PeInfoNewsId() {
	}

	/** minimal constructor */
	public PeInfoNewsId(String id, String title) {
		this.id = id;
		this.title = title;
	}

	/** full constructor */
	public PeInfoNewsId(String id, String fkNewsTypeId, String fkManagerId,
			String title, String flagNewsStatus, String flagIsactive,
			Date reportDate, Date submitDate, String confirmManagerId,
			Long readCount, String note, String picLink, String isnew,
			String summary) {
		this.id = id;
		this.fkNewsTypeId = fkNewsTypeId;
		this.fkManagerId = fkManagerId;
		this.title = title;
		this.flagNewsStatus = flagNewsStatus;
		this.flagIsactive = flagIsactive;
		this.reportDate = reportDate;
		this.submitDate = submitDate;
		this.confirmManagerId = confirmManagerId;
		this.readCount = readCount;
		this.note = note;
		this.picLink = picLink;
		this.isnew = isnew;
		this.summary = summary;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkNewsTypeId() {
		return this.fkNewsTypeId;
	}

	public void setFkNewsTypeId(String fkNewsTypeId) {
		this.fkNewsTypeId = fkNewsTypeId;
	}

	public String getFkManagerId() {
		return this.fkManagerId;
	}

	public void setFkManagerId(String fkManagerId) {
		this.fkManagerId = fkManagerId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFlagNewsStatus() {
		return this.flagNewsStatus;
	}

	public void setFlagNewsStatus(String flagNewsStatus) {
		this.flagNewsStatus = flagNewsStatus;
	}

	public String getFlagIsactive() {
		return this.flagIsactive;
	}

	public void setFlagIsactive(String flagIsactive) {
		this.flagIsactive = flagIsactive;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getConfirmManagerId() {
		return this.confirmManagerId;
	}

	public void setConfirmManagerId(String confirmManagerId) {
		this.confirmManagerId = confirmManagerId;
	}

	public Long getReadCount() {
		return this.readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPicLink() {
		return this.picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public String getIsnew() {
		return this.isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeInfoNewsId))
			return false;
		PeInfoNewsId castOther = (PeInfoNewsId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getFkNewsTypeId() == castOther.getFkNewsTypeId()) || (this
						.getFkNewsTypeId() != null
						&& castOther.getFkNewsTypeId() != null && this
						.getFkNewsTypeId().equals(castOther.getFkNewsTypeId())))
				&& ((this.getFkManagerId() == castOther.getFkManagerId()) || (this
						.getFkManagerId() != null
						&& castOther.getFkManagerId() != null && this
						.getFkManagerId().equals(castOther.getFkManagerId())))
				&& ((this.getTitle() == castOther.getTitle()) || (this
						.getTitle() != null
						&& castOther.getTitle() != null && this.getTitle()
						.equals(castOther.getTitle())))
				&& ((this.getFlagNewsStatus() == castOther.getFlagNewsStatus()) || (this
						.getFlagNewsStatus() != null
						&& castOther.getFlagNewsStatus() != null && this
						.getFlagNewsStatus().equals(
								castOther.getFlagNewsStatus())))
				&& ((this.getFlagIsactive() == castOther.getFlagIsactive()) || (this
						.getFlagIsactive() != null
						&& castOther.getFlagIsactive() != null && this
						.getFlagIsactive().equals(castOther.getFlagIsactive())))
				&& ((this.getReportDate() == castOther.getReportDate()) || (this
						.getReportDate() != null
						&& castOther.getReportDate() != null && this
						.getReportDate().equals(castOther.getReportDate())))
				&& ((this.getSubmitDate() == castOther.getSubmitDate()) || (this
						.getSubmitDate() != null
						&& castOther.getSubmitDate() != null && this
						.getSubmitDate().equals(castOther.getSubmitDate())))
				&& ((this.getConfirmManagerId() == castOther
						.getConfirmManagerId()) || (this.getConfirmManagerId() != null
						&& castOther.getConfirmManagerId() != null && this
						.getConfirmManagerId().equals(
								castOther.getConfirmManagerId())))
				&& ((this.getReadCount() == castOther.getReadCount()) || (this
						.getReadCount() != null
						&& castOther.getReadCount() != null && this
						.getReadCount().equals(castOther.getReadCount())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getPicLink() == castOther.getPicLink()) || (this
						.getPicLink() != null
						&& castOther.getPicLink() != null && this.getPicLink()
						.equals(castOther.getPicLink())))
				&& ((this.getIsnew() == castOther.getIsnew()) || (this
						.getIsnew() != null
						&& castOther.getIsnew() != null && this.getIsnew()
						.equals(castOther.getIsnew())))
				&& ((this.getSummary() == castOther.getSummary()) || (this
						.getSummary() != null
						&& castOther.getSummary() != null && this.getSummary()
						.equals(castOther.getSummary())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37
				* result
				+ (getFkNewsTypeId() == null ? 0 : this.getFkNewsTypeId()
						.hashCode());
		result = 37
				* result
				+ (getFkManagerId() == null ? 0 : this.getFkManagerId()
						.hashCode());
		result = 37 * result
				+ (getTitle() == null ? 0 : this.getTitle().hashCode());
		result = 37
				* result
				+ (getFlagNewsStatus() == null ? 0 : this.getFlagNewsStatus()
						.hashCode());
		result = 37
				* result
				+ (getFlagIsactive() == null ? 0 : this.getFlagIsactive()
						.hashCode());
		result = 37
				* result
				+ (getReportDate() == null ? 0 : this.getReportDate()
						.hashCode());
		result = 37
				* result
				+ (getSubmitDate() == null ? 0 : this.getSubmitDate()
						.hashCode());
		result = 37
				* result
				+ (getConfirmManagerId() == null ? 0 : this
						.getConfirmManagerId().hashCode());
		result = 37 * result
				+ (getReadCount() == null ? 0 : this.getReadCount().hashCode());
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37 * result
				+ (getPicLink() == null ? 0 : this.getPicLink().hashCode());
		result = 37 * result
				+ (getIsnew() == null ? 0 : this.getIsnew().hashCode());
		result = 37 * result
				+ (getSummary() == null ? 0 : this.getSummary().hashCode());
		return result;
	}

}