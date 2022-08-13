package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrMeetPerson entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrMeetPerson extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private EnumConst enumConstByFkGender;
	private PeUnit peUnit;
	private PeMeeting peMeeting;
	private String name;
	private String note;
	private String folk;
	private String zhicheng;
	private Date createDate;

	// Constructors

	/** default constructor */
	public PrMeetPerson() {
	}

	/** full constructor */
	public PrMeetPerson(EnumConst enumConstByFlagBak,
			EnumConst enumConstByFkGender, PeUnit peUnit, PeMeeting peMeeting,
			String name, String note, String folk, String zhicheng,
			Date createDate) {
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFkGender = enumConstByFkGender;
		this.peUnit = peUnit;
		this.peMeeting = peMeeting;
		this.name = name;
		this.note = note;
		this.folk = folk;
		this.zhicheng = zhicheng;
		this.createDate = createDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFkGender() {
		return this.enumConstByFkGender;
	}

	public void setEnumConstByFkGender(EnumConst enumConstByFkGender) {
		this.enumConstByFkGender = enumConstByFkGender;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public PeMeeting getPeMeeting() {
		return this.peMeeting;
	}

	public void setPeMeeting(PeMeeting peMeeting) {
		this.peMeeting = peMeeting;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getZhicheng() {
		return this.zhicheng;
	}

	public void setZhicheng(String zhicheng) {
		this.zhicheng = zhicheng;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}