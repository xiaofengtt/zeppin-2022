package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PrRecExamCourseTime entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrRecExamCourseTime extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeRecExamcourse peRecExamcourse;
	private PeRecruitplan peRecruitplan;
	private Date startDatetime;
	private Date endDatetime;
	private Long scoreLine;
	private Set prRecExamStuCourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrRecExamCourseTime() {
	}

	/** full constructor */
	public PrRecExamCourseTime(PeRecExamcourse peRecExamcourse,
			PeRecruitplan peRecruitplan, Date startDatetime, Date endDatetime,
			Long scoreLine, Set prRecExamStuCourses) {
		this.peRecExamcourse = peRecExamcourse;
		this.peRecruitplan = peRecruitplan;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.scoreLine = scoreLine;
		this.prRecExamStuCourses = prRecExamStuCourses;
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

	public PeRecruitplan getPeRecruitplan() {
		return this.peRecruitplan;
	}

	public void setPeRecruitplan(PeRecruitplan peRecruitplan) {
		this.peRecruitplan = peRecruitplan;
	}

	public Date getStartDatetime() {
		return this.startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public Date getEndDatetime() {
		return this.endDatetime;
	}

	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}

	public Long getScoreLine() {
		return this.scoreLine;
	}

	public void setScoreLine(Long scoreLine) {
		this.scoreLine = scoreLine;
	}

	public Set getPrRecExamStuCourses() {
		return this.prRecExamStuCourses;
	}

	public void setPrRecExamStuCourses(Set prRecExamStuCourses) {
		this.prRecExamStuCourses = prRecExamStuCourses;
	}

}