package com.whaty.platform.entity.bean;

/**
 * PrRecExamStuCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrRecExamStuCourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrRecExamCourseTime prRecExamCourseTime;
	private PeRecStudent peRecStudent;
	private Double score;

	// Constructors

	/** default constructor */
	public PrRecExamStuCourse() {
	}

	/** full constructor */
	public PrRecExamStuCourse(PrRecExamCourseTime prRecExamCourseTime,
			PeRecStudent peRecStudent, Double score) {
		this.prRecExamCourseTime = prRecExamCourseTime;
		this.peRecStudent = peRecStudent;
		this.score = score;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrRecExamCourseTime getPrRecExamCourseTime() {
		return this.prRecExamCourseTime;
	}

	public void setPrRecExamCourseTime(PrRecExamCourseTime prRecExamCourseTime) {
		this.prRecExamCourseTime = prRecExamCourseTime;
	}

	public PeRecStudent getPeRecStudent() {
		return this.peRecStudent;
	}

	public void setPeRecStudent(PeRecStudent peRecStudent) {
		this.peRecStudent = peRecStudent;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}