package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PePcNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcNews extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String fkManagerId;
	private String flagIssue;
	private String flagBak;
	private Date issueDatetime;
	private String note;

	// Constructors

	/** default constructor */
	public PePcNews() {
	}

	/** full constructor */
	public PePcNews(String title, String fkManagerId, String flagIssue,
			String flagBak, Date issueDatetime, String note) {
		this.title = title;
		this.fkManagerId = fkManagerId;
		this.flagIssue = flagIssue;
		this.flagBak = flagBak;
		this.issueDatetime = issueDatetime;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFkManagerId() {
		return this.fkManagerId;
	}

	public void setFkManagerId(String fkManagerId) {
		this.fkManagerId = fkManagerId;
	}

	public String getFlagIssue() {
		return this.flagIssue;
	}

	public void setFlagIssue(String flagIssue) {
		this.flagIssue = flagIssue;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Date getIssueDatetime() {
		return this.issueDatetime;
	}

	public void setIssueDatetime(Date issueDatetime) {
		this.issueDatetime = issueDatetime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}