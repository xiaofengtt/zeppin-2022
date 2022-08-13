package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrStuChangeEdutype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStuChangeEdutype extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private PeEdutype peEdutypeByFkOldEdutypeId;
	private PeEdutype peEdutypeByFkNewEdutypeId;
	private Date CDate;
	private String note;

	// Constructors

	/** default constructor */
	public PrStuChangeEdutype() {
	}

	/** full constructor */
	public PrStuChangeEdutype(PeStudent peStudent,
			PeEdutype peEdutypeByFkOldEdutypeId,
			PeEdutype peEdutypeByFkNewEdutypeId, Date CDate, String note) {
		this.peStudent = peStudent;
		this.peEdutypeByFkOldEdutypeId = peEdutypeByFkOldEdutypeId;
		this.peEdutypeByFkNewEdutypeId = peEdutypeByFkNewEdutypeId;
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

	public PeEdutype getPeEdutypeByFkOldEdutypeId() {
		return this.peEdutypeByFkOldEdutypeId;
	}

	public void setPeEdutypeByFkOldEdutypeId(PeEdutype peEdutypeByFkOldEdutypeId) {
		this.peEdutypeByFkOldEdutypeId = peEdutypeByFkOldEdutypeId;
	}

	public PeEdutype getPeEdutypeByFkNewEdutypeId() {
		return this.peEdutypeByFkNewEdutypeId;
	}

	public void setPeEdutypeByFkNewEdutypeId(PeEdutype peEdutypeByFkNewEdutypeId) {
		this.peEdutypeByFkNewEdutypeId = peEdutypeByFkNewEdutypeId;
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