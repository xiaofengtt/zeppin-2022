package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrStuChangeSite entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStuChangeSite extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private PeSite peSiteByFkNewSiteId;
	private PeSite peSiteByFkOldSiteId;
	private Date CDate;
	private String note;

	// Constructors

	/** default constructor */
	public PrStuChangeSite() {
	}

	/** full constructor */
	public PrStuChangeSite(PeStudent peStudent, PeSite peSiteByFkNewSiteId,
			PeSite peSiteByFkOldSiteId, Date CDate, String note) {
		this.peStudent = peStudent;
		this.peSiteByFkNewSiteId = peSiteByFkNewSiteId;
		this.peSiteByFkOldSiteId = peSiteByFkOldSiteId;
		this.CDate = CDate;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public PeSite getPeSiteByFkNewSiteId() {
		return this.peSiteByFkNewSiteId;
	}

	public void setPeSiteByFkNewSiteId(PeSite peSiteByFkNewSiteId) {
		this.peSiteByFkNewSiteId = peSiteByFkNewSiteId;
	}

	public PeSite getPeSiteByFkOldSiteId() {
		return this.peSiteByFkOldSiteId;
	}

	public void setPeSiteByFkOldSiteId(PeSite peSiteByFkOldSiteId) {
		this.peSiteByFkOldSiteId = peSiteByFkOldSiteId;
	}

	public Date getCDate() {
		return this.CDate;
	}

	public void setCDate(Date CDate) {
		this.CDate = CDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}