package com.whaty.platform.entity.bean;

/**
 * PrExamStuMaincourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrExamStuMaincourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrExamOpenMaincourse prExamOpenMaincourse;
	private PeStudent peStudent;
	private PeExamMaincourseRoom peExamMaincourseRoom;
	private String seatNo;
	private Double score;
	private String flagScoreStatus;
	private String flagScorePub;

	// Constructors

	/** default constructor */
	public PrExamStuMaincourse() {
	}

	/** full constructor */
	public PrExamStuMaincourse(PrExamOpenMaincourse prExamOpenMaincourse,
			PeStudent peStudent, PeExamMaincourseRoom peExamMaincourseRoom,
			String seatNo, Double score, String flagScoreStatus,
			String flagScorePub) {
		this.prExamOpenMaincourse = prExamOpenMaincourse;
		this.peStudent = peStudent;
		this.peExamMaincourseRoom = peExamMaincourseRoom;
		this.seatNo = seatNo;
		this.score = score;
		this.flagScoreStatus = flagScoreStatus;
		this.flagScorePub = flagScorePub;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrExamOpenMaincourse getPrExamOpenMaincourse() {
		return this.prExamOpenMaincourse;
	}

	public void setPrExamOpenMaincourse(
			PrExamOpenMaincourse prExamOpenMaincourse) {
		this.prExamOpenMaincourse = prExamOpenMaincourse;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public PeExamMaincourseRoom getPeExamMaincourseRoom() {
		return this.peExamMaincourseRoom;
	}

	public void setPeExamMaincourseRoom(
			PeExamMaincourseRoom peExamMaincourseRoom) {
		this.peExamMaincourseRoom = peExamMaincourseRoom;
	}

	public String getSeatNo() {
		return this.seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
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

}