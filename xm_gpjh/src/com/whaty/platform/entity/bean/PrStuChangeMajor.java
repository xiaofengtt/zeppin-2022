package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrStuChangeMajor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStuChangeMajor extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private PeMajor peMajorByFkNewMajorId;
	private PeMajor peMajorByFkOldMajorId;
	private Date CDate;
	private String note;

	// Constructors

	/** default constructor */
	public PrStuChangeMajor() {
	}

	/** full constructor */
	public PrStuChangeMajor(PeStudent peStudent, PeMajor peMajorByFkNewMajorId,
			PeMajor peMajorByFkOldMajorId, Date CDate, String note) {
		this.peStudent = peStudent;
		this.peMajorByFkNewMajorId = peMajorByFkNewMajorId;
		this.peMajorByFkOldMajorId = peMajorByFkOldMajorId;
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

	public PeMajor getPeMajorByFkNewMajorId() {
		return this.peMajorByFkNewMajorId;
	}

	public void setPeMajorByFkNewMajorId(PeMajor peMajorByFkNewMajorId) {
		this.peMajorByFkNewMajorId = peMajorByFkNewMajorId;
	}

	public PeMajor getPeMajorByFkOldMajorId() {
		return this.peMajorByFkOldMajorId;
	}

	public void setPeMajorByFkOldMajorId(PeMajor peMajorByFkOldMajorId) {
		this.peMajorByFkOldMajorId = peMajorByFkOldMajorId;
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