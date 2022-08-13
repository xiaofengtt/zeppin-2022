package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBulletinId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBulletinId implements java.io.Serializable {

	// Fields

	private String id;
	private PeManager peManager;
	private EnumConst enumConst;
	private EnumConst enumConst_1;
	private String title;
	private Date publishDate;
	private Date updateDate;
	private String scopeString;
	private String note;
	private PeSitemanager peSitemanager;
	private PeEnterpriseManager peEnterpriseManager;

	// Constructors

	/** default constructor */
	public PeBulletinId() {
	}

	/** minimal constructor */
	public PeBulletinId(String id, String title) {
		this.id = id;
		this.title = title;
	}

	/** full constructor */
	public PeBulletinId(String id, PeManager peManager, EnumConst enumConst,
			EnumConst enumConst_1, String title, Date publishDate,
			Date updateDate, String scopeString, String note,
			PeSitemanager peSitemanager, PeEnterpriseManager peEnterpriseManager) {
		this.id = id;
		this.peManager = peManager;
		this.enumConst = enumConst;
		this.enumConst_1 = enumConst_1;
		this.title = title;
		this.publishDate = publishDate;
		this.updateDate = updateDate;
		this.scopeString = scopeString;
		this.note = note;
		this.peSitemanager = peSitemanager;
		this.peEnterpriseManager = peEnterpriseManager;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeManager getPeManager() {
		return this.peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public EnumConst getEnumConst() {
		return this.enumConst;
	}

	public void setEnumConst(EnumConst enumConst) {
		this.enumConst = enumConst;
	}

	public EnumConst getEnumConst_1() {
		return this.enumConst_1;
	}

	public void setEnumConst_1(EnumConst enumConst_1) {
		this.enumConst_1 = enumConst_1;
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

	public PeSitemanager getPeSitemanager() {
		return this.peSitemanager;
	}

	public void setPeSitemanager(PeSitemanager peSitemanager) {
		this.peSitemanager = peSitemanager;
	}

	public PeEnterpriseManager getPeEnterpriseManager() {
		return this.peEnterpriseManager;
	}

	public void setPeEnterpriseManager(PeEnterpriseManager peEnterpriseManager) {
		this.peEnterpriseManager = peEnterpriseManager;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeBulletinId))
			return false;
		PeBulletinId castOther = (PeBulletinId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getPeManager() == castOther.getPeManager()) || (this
						.getPeManager() != null
						&& castOther.getPeManager() != null && this
						.getPeManager().equals(castOther.getPeManager())))
				&& ((this.getEnumConst() == castOther.getEnumConst()) || (this
						.getEnumConst() != null
						&& castOther.getEnumConst() != null && this
						.getEnumConst().equals(castOther.getEnumConst())))
				&& ((this.getEnumConst_1() == castOther.getEnumConst_1()) || (this
						.getEnumConst_1() != null
						&& castOther.getEnumConst_1() != null && this
						.getEnumConst_1().equals(castOther.getEnumConst_1())))
				&& ((this.getTitle() == castOther.getTitle()) || (this
						.getTitle() != null
						&& castOther.getTitle() != null && this.getTitle()
						.equals(castOther.getTitle())))
				&& ((this.getPublishDate() == castOther.getPublishDate()) || (this
						.getPublishDate() != null
						&& castOther.getPublishDate() != null && this
						.getPublishDate().equals(castOther.getPublishDate())))
				&& ((this.getUpdateDate() == castOther.getUpdateDate()) || (this
						.getUpdateDate() != null
						&& castOther.getUpdateDate() != null && this
						.getUpdateDate().equals(castOther.getUpdateDate())))
				&& ((this.getScopeString() == castOther.getScopeString()) || (this
						.getScopeString() != null
						&& castOther.getScopeString() != null && this
						.getScopeString().equals(castOther.getScopeString())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getPeSitemanager() == castOther.getPeSitemanager()) || (this
						.getPeSitemanager() != null
						&& castOther.getPeSitemanager() != null && this
						.getPeSitemanager()
						.equals(castOther.getPeSitemanager())))
				&& ((this.getPeEnterpriseManager() == castOther
						.getPeEnterpriseManager()) || (this
						.getPeEnterpriseManager() != null
						&& castOther.getPeEnterpriseManager() != null && this
						.getPeEnterpriseManager().equals(
								castOther.getPeEnterpriseManager())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getPeManager() == null ? 0 : this.getPeManager().hashCode());
		result = 37 * result
				+ (getEnumConst() == null ? 0 : this.getEnumConst().hashCode());
		result = 37
				* result
				+ (getEnumConst_1() == null ? 0 : this.getEnumConst_1()
						.hashCode());
		result = 37 * result
				+ (getTitle() == null ? 0 : this.getTitle().hashCode());
		result = 37
				* result
				+ (getPublishDate() == null ? 0 : this.getPublishDate()
						.hashCode());
		result = 37
				* result
				+ (getUpdateDate() == null ? 0 : this.getUpdateDate()
						.hashCode());
		result = 37
				* result
				+ (getScopeString() == null ? 0 : this.getScopeString()
						.hashCode());
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37
				* result
				+ (getPeSitemanager() == null ? 0 : this.getPeSitemanager()
						.hashCode());
		result = 37
				* result
				+ (getPeEnterpriseManager() == null ? 0 : this
						.getPeEnterpriseManager().hashCode());
		return result;
	}

}