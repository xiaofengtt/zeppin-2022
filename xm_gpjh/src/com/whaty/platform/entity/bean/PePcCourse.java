package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePcCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcCourse extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String note;
	private Set prPcOpencourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePcCourse() {
	}

	/** full constructor */
	public PePcCourse(String name, String code, String note, Set prPcOpencourses) {
		this.name = name;
		this.code = code;
		this.note = note;
		this.prPcOpencourses = prPcOpencourses;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrPcOpencourses() {
		return this.prPcOpencourses;
	}

	public void setPrPcOpencourses(Set prPcOpencourses) {
		this.prPcOpencourses = prPcOpencourses;
	}

}