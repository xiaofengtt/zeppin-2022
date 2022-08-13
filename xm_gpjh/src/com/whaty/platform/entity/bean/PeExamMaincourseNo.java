package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeExamMaincourseNo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExamMaincourseNo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private String name;
	private Date startDatetime;
	private Date endDatetime;
	private Set prExamOpenMaincourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeExamMaincourseNo() {
	}

	/** minimal constructor */
	public PeExamMaincourseNo(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeExamMaincourseNo(PeSemester peSemester, String name,
			Date startDatetime, Date endDatetime, Set prExamOpenMaincourses) {
		this.peSemester = peSemester;
		this.name = name;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.prExamOpenMaincourses = prExamOpenMaincourses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set getPrExamOpenMaincourses() {
		return this.prExamOpenMaincourses;
	}

	public void setPrExamOpenMaincourses(Set prExamOpenMaincourses) {
		this.prExamOpenMaincourses = prExamOpenMaincourses;
	}

}