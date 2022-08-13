package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrCourseArrange entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrCourseArrange extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrTchOpencourse prTchOpencourse;
	private Date arrangeDate;
	private String flagCourseSection;
	private String classroom;
	private String flagBak;

	// Constructors

	/** default constructor */
	public PrCourseArrange() {
	}

	/** full constructor */
	public PrCourseArrange(PrTchOpencourse prTchOpencourse, Date arrangeDate,
			String flagCourseSection, String classroom, String flagBak) {
		this.prTchOpencourse = prTchOpencourse;
		this.arrangeDate = arrangeDate;
		this.flagCourseSection = flagCourseSection;
		this.classroom = classroom;
		this.flagBak = flagBak;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrTchOpencourse getPrTchOpencourse() {
		return this.prTchOpencourse;
	}

	public void setPrTchOpencourse(PrTchOpencourse prTchOpencourse) {
		this.prTchOpencourse = prTchOpencourse;
	}

	public Date getArrangeDate() {
		return this.arrangeDate;
	}

	public void setArrangeDate(Date arrangeDate) {
		this.arrangeDate = arrangeDate;
	}

	public String getFlagCourseSection() {
		return this.flagCourseSection;
	}

	public void setFlagCourseSection(String flagCourseSection) {
		this.flagCourseSection = flagCourseSection;
	}

	public String getClassroom() {
		return this.classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}