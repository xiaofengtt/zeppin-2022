package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzBatch extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date startTime;
	private Date endTime;
	private String bz;
	private Date evalStartDate;
	private Date evalEndDate;
	private Date askEndDate;
	private Date askStartDate;
	private Date courseDate;
	private String standards;
	private Long batchCode;
	private Set prBzzTchOpencourses = new HashSet(0);
	private Set peBzzStudents = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeBzzBatch() {
	}

	/** full constructor */
	public PeBzzBatch(String name, Date startTime, Date endTime, String bz,
			Date evalStartDate, Date evalEndDate, Date askEndDate,
			Date askStartDate, Date courseDate, String standards,
			Long batchCode, Set prBzzTchOpencourses, Set peBzzStudents) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bz = bz;
		this.evalStartDate = evalStartDate;
		this.evalEndDate = evalEndDate;
		this.askEndDate = askEndDate;
		this.askStartDate = askStartDate;
		this.courseDate = courseDate;
		this.standards = standards;
		this.batchCode = batchCode;
		this.prBzzTchOpencourses = prBzzTchOpencourses;
		this.peBzzStudents = peBzzStudents;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getEvalStartDate() {
		return this.evalStartDate;
	}

	public void setEvalStartDate(Date evalStartDate) {
		this.evalStartDate = evalStartDate;
	}

	public Date getEvalEndDate() {
		return this.evalEndDate;
	}

	public void setEvalEndDate(Date evalEndDate) {
		this.evalEndDate = evalEndDate;
	}

	public Date getAskEndDate() {
		return this.askEndDate;
	}

	public void setAskEndDate(Date askEndDate) {
		this.askEndDate = askEndDate;
	}

	public Date getAskStartDate() {
		return this.askStartDate;
	}

	public void setAskStartDate(Date askStartDate) {
		this.askStartDate = askStartDate;
	}

	public Date getCourseDate() {
		return this.courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public String getStandards() {
		return this.standards;
	}

	public void setStandards(String standards) {
		this.standards = standards;
	}

	public Long getBatchCode() {
		return this.batchCode;
	}

	public void setBatchCode(Long batchCode) {
		this.batchCode = batchCode;
	}

	public Set getPrBzzTchOpencourses() {
		return this.prBzzTchOpencourses;
	}

	public void setPrBzzTchOpencourses(Set prBzzTchOpencourses) {
		this.prBzzTchOpencourses = prBzzTchOpencourses;
	}

	public Set getPeBzzStudents() {
		return this.peBzzStudents;
	}

	public void setPeBzzStudents(Set peBzzStudents) {
		this.peBzzStudents = peBzzStudents;
	}

}