package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TrainingCourseStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TrainingCourseStudent extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTchCourseware peTchCourseware;
	private String studentId;
	private String status;
	private Date getDate;
	private Double percent;
	private String learnStatus;
	private Double score;
	private Set prBzzTchStuElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public TrainingCourseStudent() {
	}

	/** full constructor */
	public TrainingCourseStudent(PeTchCourseware peTchCourseware,
			String studentId, String status, Date getDate, Double percent,
			String learnStatus, Double score, Set prBzzTchStuElectives) {
		this.peTchCourseware = peTchCourseware;
		this.studentId = studentId;
		this.status = status;
		this.getDate = getDate;
		this.percent = percent;
		this.learnStatus = learnStatus;
		this.score = score;
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchCourseware getPeTchCourseware() {
		return this.peTchCourseware;
	}

	public void setPeTchCourseware(PeTchCourseware peTchCourseware) {
		this.peTchCourseware = peTchCourseware;
	}

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getGetDate() {
		return this.getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}

	public Double getPercent() {
		return this.percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getLearnStatus() {
		return this.learnStatus;
	}

	public void setLearnStatus(String learnStatus) {
		this.learnStatus = learnStatus;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Set getPrBzzTchStuElectives() {
		return this.prBzzTchStuElectives;
	}

	public void setPrBzzTchStuElectives(Set prBzzTchStuElectives) {
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

}