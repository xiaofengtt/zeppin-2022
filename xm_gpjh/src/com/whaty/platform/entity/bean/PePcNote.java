package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PePcNote entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcNote extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePcTeacher pePcTeacher;
	private PrPcOpencourse prPcOpencourse;
	private Date publishDatetime;
	private String title;
	private String note;

	// Constructors

	/** default constructor */
	public PePcNote() {
	}

	/** full constructor */
	public PePcNote(PePcTeacher pePcTeacher, PrPcOpencourse prPcOpencourse,
			Date publishDatetime, String title, String note) {
		this.pePcTeacher = pePcTeacher;
		this.prPcOpencourse = prPcOpencourse;
		this.publishDatetime = publishDatetime;
		this.title = title;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePcTeacher getPePcTeacher() {
		return this.pePcTeacher;
	}

	public void setPePcTeacher(PePcTeacher pePcTeacher) {
		this.pePcTeacher = pePcTeacher;
	}

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public Date getPublishDatetime() {
		return this.publishDatetime;
	}

	public void setPublishDatetime(Date publishDatetime) {
		this.publishDatetime = publishDatetime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}