package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrStuChangeSchool entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStuChangeSchool extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private String flagAbortschoolType;
	private String flagBak;
	private Date opDate;
	private String note;

	// Constructors

	/** default constructor */
	public PrStuChangeSchool() {
	}

	/** full constructor */
	public PrStuChangeSchool(PeStudent peStudent, String flagAbortschoolType,
			String flagBak, Date opDate, String note) {
		this.peStudent = peStudent;
		this.flagAbortschoolType = flagAbortschoolType;
		this.flagBak = flagBak;
		this.opDate = opDate;
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

	public String getFlagAbortschoolType() {
		return this.flagAbortschoolType;
	}

	public void setFlagAbortschoolType(String flagAbortschoolType) {
		this.flagAbortschoolType = flagAbortschoolType;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Date getOpDate() {
		return this.opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}