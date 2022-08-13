package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PePcExercise entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcExercise extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrPcOpencourse prPcOpencourse;
	private Date publishDatetime;
	private Date uploadEndDatetime;
	private String note;
	private Set prPcStuExercises = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePcExercise() {
	}

	/** full constructor */
	public PePcExercise(PrPcOpencourse prPcOpencourse, Date publishDatetime,
			Date uploadEndDatetime, String note, Set prPcStuExercises) {
		this.prPcOpencourse = prPcOpencourse;
		this.publishDatetime = publishDatetime;
		this.uploadEndDatetime = uploadEndDatetime;
		this.note = note;
		this.prPcStuExercises = prPcStuExercises;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getUploadEndDatetime() {
		return this.uploadEndDatetime;
	}

	public void setUploadEndDatetime(Date uploadEndDatetime) {
		this.uploadEndDatetime = uploadEndDatetime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrPcStuExercises() {
		return this.prPcStuExercises;
	}

	public void setPrPcStuExercises(Set prPcStuExercises) {
		this.prPcStuExercises = prPcStuExercises;
	}

}