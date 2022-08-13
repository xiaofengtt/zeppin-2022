package com.whaty.platform.entity.bean;

/**
 * PePcOpencourseCourseware entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcOpencourseCourseware extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrPcOpencourse prPcOpencourse;
	private String name;
	private String note;

	// Constructors

	/** default constructor */
	public PePcOpencourseCourseware() {
	}

	/** full constructor */
	public PePcOpencourseCourseware(PrPcOpencourse prPcOpencourse, String name,
			String note) {
		this.prPcOpencourse = prPcOpencourse;
		this.name = name;
		this.note = note;
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

}