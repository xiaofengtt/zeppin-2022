package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeTrainingClass entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTrainingClass extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date startDate;
	private String note;

	// Constructors

	/** default constructor */
	public PeTrainingClass() {
	}

	/** full constructor */
	public PeTrainingClass(String name, Date startDate, String note) {
		this.name = name;
		this.startDate = startDate;
		this.note = note;
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

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}