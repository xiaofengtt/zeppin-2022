package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeDocument entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeDocument extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String fkSsoId;
	private String flagDel;
	private String flagBak;
	private Date sendDate;
	private String note;

	// Constructors

	/** default constructor */
	public PeDocument() {
	}

	/** minimal constructor */
	public PeDocument(String title) {
		this.title = title;
	}

	/** full constructor */
	public PeDocument(String title, String fkSsoId, String flagDel,
			String flagBak, Date sendDate, String note) {
		this.title = title;
		this.fkSsoId = fkSsoId;
		this.flagDel = flagDel;
		this.flagBak = flagBak;
		this.sendDate = sendDate;
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

	public String getFkSsoId() {
		return this.fkSsoId;
	}

	public void setFkSsoId(String fkSsoId) {
		this.fkSsoId = fkSsoId;
	}

	public String getFlagDel() {
		return this.flagDel;
	}

	public void setFlagDel(String flagDel) {
		this.flagDel = flagDel;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}