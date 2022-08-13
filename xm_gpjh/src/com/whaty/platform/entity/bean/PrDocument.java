package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrDocument entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrDocument extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String fkDocumentId;
	private String fkSsoId;
	private String flagRead;
	private String flagDel;
	private Date readDate;

	// Constructors

	/** default constructor */
	public PrDocument() {
	}

	/** full constructor */
	public PrDocument(String fkDocumentId, String fkSsoId, String flagRead,
			String flagDel, Date readDate) {
		this.fkDocumentId = fkDocumentId;
		this.fkSsoId = fkSsoId;
		this.flagRead = flagRead;
		this.flagDel = flagDel;
		this.readDate = readDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkDocumentId() {
		return this.fkDocumentId;
	}

	public void setFkDocumentId(String fkDocumentId) {
		this.fkDocumentId = fkDocumentId;
	}

	public String getFkSsoId() {
		return this.fkSsoId;
	}

	public void setFkSsoId(String fkSsoId) {
		this.fkSsoId = fkSsoId;
	}

	public String getFlagRead() {
		return this.flagRead;
	}

	public void setFlagRead(String flagRead) {
		this.flagRead = flagRead;
	}

	public String getFlagDel() {
		return this.flagDel;
	}

	public void setFlagDel(String flagDel) {
		this.flagDel = flagDel;
	}

	public Date getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

}