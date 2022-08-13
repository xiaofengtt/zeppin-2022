package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrExamOpenMaincourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrExamOpenMaincourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeExamMaincourseNo peExamMaincourseNo;
	private String fkCourseId;
	private Set prExamStuMaincourses = new HashSet(0);
	private Set peExamMaincourseRooms = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrExamOpenMaincourse() {
	}

	/** full constructor */
	public PrExamOpenMaincourse(PeExamMaincourseNo peExamMaincourseNo,
			String fkCourseId, Set prExamStuMaincourses,
			Set peExamMaincourseRooms) {
		this.peExamMaincourseNo = peExamMaincourseNo;
		this.fkCourseId = fkCourseId;
		this.prExamStuMaincourses = prExamStuMaincourses;
		this.peExamMaincourseRooms = peExamMaincourseRooms;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeExamMaincourseNo getPeExamMaincourseNo() {
		return this.peExamMaincourseNo;
	}

	public void setPeExamMaincourseNo(PeExamMaincourseNo peExamMaincourseNo) {
		this.peExamMaincourseNo = peExamMaincourseNo;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

	public Set getPrExamStuMaincourses() {
		return this.prExamStuMaincourses;
	}

	public void setPrExamStuMaincourses(Set prExamStuMaincourses) {
		this.prExamStuMaincourses = prExamStuMaincourses;
	}

	public Set getPeExamMaincourseRooms() {
		return this.peExamMaincourseRooms;
	}

	public void setPeExamMaincourseRooms(Set peExamMaincourseRooms) {
		this.peExamMaincourseRooms = peExamMaincourseRooms;
	}

}