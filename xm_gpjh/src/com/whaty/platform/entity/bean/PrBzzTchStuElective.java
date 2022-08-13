package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrBzzTchStuElective entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrBzzTchStuElective extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeBzzStudent peBzzStudent;
	private TrainingCourseStudent trainingCourseStudent;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private String fkOperatorId;
	private String flagScoreStatus;
	private String flagScorePub;
	private Date electiveDate;
	private Double scoreExam;
	private String onlineTime;

	// Constructors

	/** default constructor */
	public PrBzzTchStuElective() {
	}

	/** minimal constructor */
	public PrBzzTchStuElective(Date electiveDate) {
		this.electiveDate = electiveDate;
	}

	/** full constructor */
	public PrBzzTchStuElective(PeBzzStudent peBzzStudent,
			TrainingCourseStudent trainingCourseStudent,
			PrBzzTchOpencourse prBzzTchOpencourse, String fkOperatorId,
			String flagScoreStatus, String flagScorePub, Date electiveDate,
			Double scoreExam, String onlineTime) {
		this.peBzzStudent = peBzzStudent;
		this.trainingCourseStudent = trainingCourseStudent;
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.fkOperatorId = fkOperatorId;
		this.flagScoreStatus = flagScoreStatus;
		this.flagScorePub = flagScorePub;
		this.electiveDate = electiveDate;
		this.scoreExam = scoreExam;
		this.onlineTime = onlineTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeBzzStudent getPeBzzStudent() {
		return this.peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public TrainingCourseStudent getTrainingCourseStudent() {
		return this.trainingCourseStudent;
	}

	public void setTrainingCourseStudent(
			TrainingCourseStudent trainingCourseStudent) {
		this.trainingCourseStudent = trainingCourseStudent;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return this.prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public String getFkOperatorId() {
		return this.fkOperatorId;
	}

	public void setFkOperatorId(String fkOperatorId) {
		this.fkOperatorId = fkOperatorId;
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

	public Double getScoreExam() {
		return this.scoreExam;
	}

	public void setScoreExam(Double scoreExam) {
		this.scoreExam = scoreExam;
	}

	public String getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

}