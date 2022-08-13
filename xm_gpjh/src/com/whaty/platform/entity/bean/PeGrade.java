package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeGrade entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeGrade extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private Long serialNumber;
	private Date beginDate;
	private Set prPriManagerGrades = new HashSet(0);
	private Set peStudents = new HashSet(0);
	private Set peRecruitplans = new HashSet(0);
	private Set peTchPrograms = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeGrade() {
	}

	/** minimal constructor */
	public PeGrade(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeGrade(String name, String code, Long serialNumber, Date beginDate,
			Set prPriManagerGrades, Set peStudents, Set peRecruitplans,
			Set peTchPrograms) {
		this.name = name;
		this.code = code;
		this.serialNumber = serialNumber;
		this.beginDate = beginDate;
		this.prPriManagerGrades = prPriManagerGrades;
		this.peStudents = peStudents;
		this.peRecruitplans = peRecruitplans;
		this.peTchPrograms = peTchPrograms;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Set getPrPriManagerGrades() {
		return this.prPriManagerGrades;
	}

	public void setPrPriManagerGrades(Set prPriManagerGrades) {
		this.prPriManagerGrades = prPriManagerGrades;
	}

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

	public Set getPeRecruitplans() {
		return this.peRecruitplans;
	}

	public void setPeRecruitplans(Set peRecruitplans) {
		this.peRecruitplans = peRecruitplans;
	}

	public Set getPeTchPrograms() {
		return this.peTchPrograms;
	}

	public void setPeTchPrograms(Set peTchPrograms) {
		this.peTchPrograms = peTchPrograms;
	}

}