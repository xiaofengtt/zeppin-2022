package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeProgramTime entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeProgramTime extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String fkUnit;
	private String name;
	private Date begin;
	private Date end;
	private String note;

	// Constructors

	/** default constructor */
	public PeProgramTime() {
	}

	/** full constructor */
	public PeProgramTime(String fkUnit, String name, Date begin, Date end,
			String note) {
		this.fkUnit = fkUnit;
		this.name = name;
		this.begin = begin;
		this.end = end;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkUnit() {
		return this.fkUnit;
	}

	public void setFkUnit(String fkUnit) {
		this.fkUnit = fkUnit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBegin() {
		return this.begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}