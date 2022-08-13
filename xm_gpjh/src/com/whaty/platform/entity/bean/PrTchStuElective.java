package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PrTchStuElective entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchStuElective extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private PrTchProgramCourse prTchProgramCourse;
	private PrTchOpencourse prTchOpencourse;
	private String fkOperatorId;
	private String flagElectiveAdmission;
	private String flagScoreStatus;
	private String flagScorePub;
	private Date electiveDate;
	private String pri;
	private Double scoreSystem;
	private Double scoreUsual;
	private Double scoreHomework;
	private Double scoreExam;
	private Double scoreTotal;
	private String onlineTime;
	private Long enterTimes;
	private Set prExamBookings = new HashSet(0);
	private Set prTchElectiveBooks = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchStuElective() {
	}

	/** minimal constructor */
	public PrTchStuElective(Date electiveDate) {
		this.electiveDate = electiveDate;
	}

	/** full constructor */
	public PrTchStuElective(PeStudent peStudent,
			PrTchProgramCourse prTchProgramCourse,
			PrTchOpencourse prTchOpencourse, String fkOperatorId,
			String flagElectiveAdmission, String flagScoreStatus,
			String flagScorePub, Date electiveDate, String pri,
			Double scoreSystem, Double scoreUsual, Double scoreHomework,
			Double scoreExam, Double scoreTotal, String onlineTime,
			Long enterTimes, Set prExamBookings, Set prTchElectiveBooks) {
		this.peStudent = peStudent;
		this.prTchProgramCourse = prTchProgramCourse;
		this.prTchOpencourse = prTchOpencourse;
		this.fkOperatorId = fkOperatorId;
		this.flagElectiveAdmission = flagElectiveAdmission;
		this.flagScoreStatus = flagScoreStatus;
		this.flagScorePub = flagScorePub;
		this.electiveDate = electiveDate;
		this.pri = pri;
		this.scoreSystem = scoreSystem;
		this.scoreUsual = scoreUsual;
		this.scoreHomework = scoreHomework;
		this.scoreExam = scoreExam;
		this.scoreTotal = scoreTotal;
		this.onlineTime = onlineTime;
		this.enterTimes = enterTimes;
		this.prExamBookings = prExamBookings;
		this.prTchElectiveBooks = prTchElectiveBooks;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public PrTchProgramCourse getPrTchProgramCourse() {
		return this.prTchProgramCourse;
	}

	public void setPrTchProgramCourse(PrTchProgramCourse prTchProgramCourse) {
		this.prTchProgramCourse = prTchProgramCourse;
	}

	public PrTchOpencourse getPrTchOpencourse() {
		return this.prTchOpencourse;
	}

	public void setPrTchOpencourse(PrTchOpencourse prTchOpencourse) {
		this.prTchOpencourse = prTchOpencourse;
	}

	public String getFkOperatorId() {
		return this.fkOperatorId;
	}

	public void setFkOperatorId(String fkOperatorId) {
		this.fkOperatorId = fkOperatorId;
	}

	public String getFlagElectiveAdmission() {
		return this.flagElectiveAdmission;
	}

	public void setFlagElectiveAdmission(String flagElectiveAdmission) {
		this.flagElectiveAdmission = flagElectiveAdmission;
	}

	public String getFlagScoreStatus() {
		return this.flagScoreStatus;
	}

	public void setFlagScoreStatus(String flagScoreStatus) {
		this.flagScoreStatus = flagScoreStatus;
	}

	public String getFlagScorePub() {
		return this.flagScorePub;
	}

	public void setFlagScorePub(String flagScorePub) {
		this.flagScorePub = flagScorePub;
	}

	public Date getElectiveDate() {
		return this.electiveDate;
	}

	public void setElectiveDate(Date electiveDate) {
		this.electiveDate = electiveDate;
	}

	public String getPri() {
		return this.pri;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}

	public Double getScoreSystem() {
		return this.scoreSystem;
	}

	public void setScoreSystem(Double scoreSystem) {
		this.scoreSystem = scoreSystem;
	}

	public Double getScoreUsual() {
		return this.scoreUsual;
	}

	public void setScoreUsual(Double scoreUsual) {
		this.scoreUsual = scoreUsual;
	}

	public Double getScoreHomework() {
		return this.scoreHomework;
	}

	public void setScoreHomework(Double scoreHomework) {
		this.scoreHomework = scoreHomework;
	}

	public Double getScoreExam() {
		return this.scoreExam;
	}

	public void setScoreExam(Double scoreExam) {
		this.scoreExam = scoreExam;
	}

	public Double getScoreTotal() {
		return this.scoreTotal;
	}

	public void setScoreTotal(Double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public String getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Long getEnterTimes() {
		return this.enterTimes;
	}

	public void setEnterTimes(Long enterTimes) {
		this.enterTimes = enterTimes;
	}

	public Set getPrExamBookings() {
		return this.prExamBookings;
	}

	public void setPrExamBookings(Set prExamBookings) {
		this.prExamBookings = prExamBookings;
	}

	public Set getPrTchElectiveBooks() {
		return this.prTchElectiveBooks;
	}

	public void setPrTchElectiveBooks(Set prTchElectiveBooks) {
		this.prTchElectiveBooks = prTchElectiveBooks;
	}

}