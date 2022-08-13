package com.whaty.platform.entity.bean;

/**
 * PrRecCourseMajorEdutype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrRecCourseMajorEdutype extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeRecExamcourse peRecExamcourse;
	private PeMajor peMajor;
	private PeEdutype peEdutype;

	// Constructors

	/** default constructor */
	public PrRecCourseMajorEdutype() {
	}

	/** full constructor */
	public PrRecCourseMajorEdutype(PeRecExamcourse peRecExamcourse,
			PeMajor peMajor, PeEdutype peEdutype) {
		this.peRecExamcourse = peRecExamcourse;
		this.peMajor = peMajor;
		this.peEdutype = peEdutype;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeRecExamcourse getPeRecExamcourse() {
		return this.peRecExamcourse;
	}

	public void setPeRecExamcourse(PeRecExamcourse peRecExamcourse) {
		this.peRecExamcourse = peRecExamcourse;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

}