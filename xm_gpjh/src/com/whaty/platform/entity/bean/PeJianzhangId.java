package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeJianzhangId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeJianzhangId extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date creatDate;
	private String flagActive;
	private String jianzhang;

	// Constructors

	/** default constructor */
	public PeJianzhangId() {
	}

	/** minimal constructor */
	public PeJianzhangId(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public PeJianzhangId(String id, String name, Date creatDate,
			String flagActive, String jianzhang) {
		this.id = id;
		this.name = name;
		this.creatDate = creatDate;
		this.flagActive = flagActive;
		this.jianzhang = jianzhang;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getFlagActive() {
		return this.flagActive;
	}

	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}

	public String getJianzhang() {
		return this.jianzhang;
	}

	public void setJianzhang(String jianzhang) {
		this.jianzhang = jianzhang;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeJianzhangId))
			return false;
		PeJianzhangId castOther = (PeJianzhangId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getCreatDate() == castOther.getCreatDate()) || (this
						.getCreatDate() != null
						&& castOther.getCreatDate() != null && this
						.getCreatDate().equals(castOther.getCreatDate())))
				&& ((this.getFlagActive() == castOther.getFlagActive()) || (this
						.getFlagActive() != null
						&& castOther.getFlagActive() != null && this
						.getFlagActive().equals(castOther.getFlagActive())))
				&& ((this.getJianzhang() == castOther.getJianzhang()) || (this
						.getJianzhang() != null
						&& castOther.getJianzhang() != null && this
						.getJianzhang().equals(castOther.getJianzhang())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getCreatDate() == null ? 0 : this.getCreatDate().hashCode());
		result = 37
				* result
				+ (getFlagActive() == null ? 0 : this.getFlagActive()
						.hashCode());
		result = 37 * result
				+ (getJianzhang() == null ? 0 : this.getJianzhang().hashCode());
		return result;
	}

}