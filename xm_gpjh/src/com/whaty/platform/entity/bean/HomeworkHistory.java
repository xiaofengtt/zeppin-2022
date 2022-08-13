package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * HomeworkHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class HomeworkHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private HomeworkInfo homeworkInfo;
	private PeStudent peStudent;
	private Date testdate;
	private String note;
	private String score;
	private String flagIscheck;
	private String flagBak;

	// Constructors

	/** default constructor */
	public HomeworkHistory() {
	}

	/** full constructor */
	public HomeworkHistory(HomeworkInfo homeworkInfo, PeStudent peStudent,
			Date testdate, String note, String score, String flagIscheck,
			String flagBak) {
		this.homeworkInfo = homeworkInfo;
		this.peStudent = peStudent;
		this.testdate = testdate;
		this.note = note;
		this.score = score;
		this.flagIscheck = flagIscheck;
		this.flagBak = flagBak;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HomeworkInfo getHomeworkInfo() {
		return this.homeworkInfo;
	}

	public void setHomeworkInfo(HomeworkInfo homeworkInfo) {
		this.homeworkInfo = homeworkInfo;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public Date getTestdate() {
		return this.testdate;
	}

	public void setTestdate(Date testdate) {
		this.testdate = testdate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFlagIscheck() {
		return this.flagIscheck;
	}

	public void setFlagIscheck(String flagIscheck) {
		this.flagIscheck = flagIscheck;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}