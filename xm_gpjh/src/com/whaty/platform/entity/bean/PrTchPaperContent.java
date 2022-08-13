package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrTchPaperContent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchPaperContent extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrTchStuPaper prTchStuPaper;
	private String url;
	private String note;
	private Date actionDate;
	private String flagActionUser;
	private String flagPaperSection;

	// Constructors

	/** default constructor */
	public PrTchPaperContent() {
	}

	/** minimal constructor */
	public PrTchPaperContent(PrTchStuPaper prTchStuPaper, Date actionDate,
			String flagActionUser, String flagPaperSection) {
		this.prTchStuPaper = prTchStuPaper;
		this.actionDate = actionDate;
		this.flagActionUser = flagActionUser;
		this.flagPaperSection = flagPaperSection;
	}

	/** full constructor */
	public PrTchPaperContent(PrTchStuPaper prTchStuPaper, String url,
			String note, Date actionDate, String flagActionUser,
			String flagPaperSection) {
		this.prTchStuPaper = prTchStuPaper;
		this.url = url;
		this.note = note;
		this.actionDate = actionDate;
		this.flagActionUser = flagActionUser;
		this.flagPaperSection = flagPaperSection;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrTchStuPaper getPrTchStuPaper() {
		return this.prTchStuPaper;
	}

	public void setPrTchStuPaper(PrTchStuPaper prTchStuPaper) {
		this.prTchStuPaper = prTchStuPaper;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getFlagActionUser() {
		return this.flagActionUser;
	}

	public void setFlagActionUser(String flagActionUser) {
		this.flagActionUser = flagActionUser;
	}

	public String getFlagPaperSection() {
		return this.flagPaperSection;
	}

	public void setFlagPaperSection(String flagPaperSection) {
		this.flagPaperSection = flagPaperSection;
	}

}