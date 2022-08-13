package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeExamNo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExamNo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private String name;
	private Long sequence;
	private Date startDatetime;
	private Date endDatetime;
	private String paperType;
	private Set prExamBookings = new HashSet(0);
	private Set peExamRooms = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeExamNo() {
	}

	/** minimal constructor */
	public PeExamNo(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeExamNo(PeSemester peSemester, String name, Long sequence,
			Date startDatetime, Date endDatetime, String paperType,
			Set prExamBookings, Set peExamRooms) {
		this.peSemester = peSemester;
		this.name = name;
		this.sequence = sequence;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.paperType = paperType;
		this.prExamBookings = prExamBookings;
		this.peExamRooms = peExamRooms;
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

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
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

	public String getPaperType() {
		return this.paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public Set getPrExamBookings() {
		return this.prExamBookings;
	}

	public void setPrExamBookings(Set prExamBookings) {
		this.prExamBookings = prExamBookings;
	}

	public Set getPeExamRooms() {
		return this.peExamRooms;
	}

	public void setPeExamRooms(Set peExamRooms) {
		this.peExamRooms = peExamRooms;
	}

}