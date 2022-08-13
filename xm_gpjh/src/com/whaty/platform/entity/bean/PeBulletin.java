package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBulletin entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBulletin extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagIstop;
	private PeManager peManager;
	private EnumConst enumConstByFlagIsvalid;
	private String title;
	private Date publishDate;
	private Date updateDate;
	private String scopeString;
	private String note;

	// Constructors

	/** default constructor */
	public PeBulletin() {
	}

	/** minimal constructor */
	public PeBulletin(String title) {
		this.title = title;
	}

	/** full constructor */
	public PeBulletin(EnumConst enumConstByFlagIstop, PeManager peManager,
			EnumConst enumConstByFlagIsvalid, String title, Date publishDate,
			Date updateDate, String scopeString, String note) {
		this.enumConstByFlagIstop = enumConstByFlagIstop;
		this.peManager = peManager;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.title = title;
		this.publishDate = publishDate;
		this.updateDate = updateDate;
		this.scopeString = scopeString;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagIstop() {
		return this.enumConstByFlagIstop;
	}

	public void setEnumConstByFlagIstop(EnumConst enumConstByFlagIstop) {
		this.enumConstByFlagIstop = enumConstByFlagIstop;
	}

	public PeManager getPeManager() {
		return this.peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getScopeString() {
		return this.scopeString;
	}

	public void setScopeString(String scopeString) {
		this.scopeString = scopeString;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}