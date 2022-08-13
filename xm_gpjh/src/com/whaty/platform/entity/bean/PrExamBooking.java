package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrExamBooking entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrExamBooking extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeExamRoom peExamRoom;
	private PrTchStuElective prTchStuElective;
	private PeExamNo peExamNo;
	private PeSemester peSemester;
	private Date bookingDate;
	private String seatNo;
	private Double scoreExam;
	private String flagScoreStatus;
	private Double scoreExamA;
	private Double scoreExamB;
	private String flagScoreStatusa;
	private String flagScoreStatusb;

	// Constructors

	/** default constructor */
	public PrExamBooking() {
	}

	/** full constructor */
	public PrExamBooking(PeExamRoom peExamRoom,
			PrTchStuElective prTchStuElective, PeExamNo peExamNo,
			PeSemester peSemester, Date bookingDate, String seatNo,
			Double scoreExam, String flagScoreStatus, Double scoreExamA,
			Double scoreExamB, String flagScoreStatusa, String flagScoreStatusb) {
		this.peExamRoom = peExamRoom;
		this.prTchStuElective = prTchStuElective;
		this.peExamNo = peExamNo;
		this.peSemester = peSemester;
		this.bookingDate = bookingDate;
		this.seatNo = seatNo;
		this.scoreExam = scoreExam;
		this.flagScoreStatus = flagScoreStatus;
		this.scoreExamA = scoreExamA;
		this.scoreExamB = scoreExamB;
		this.flagScoreStatusa = flagScoreStatusa;
		this.flagScoreStatusb = flagScoreStatusb;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeExamRoom getPeExamRoom() {
		return this.peExamRoom;
	}

	public void setPeExamRoom(PeExamRoom peExamRoom) {
		this.peExamRoom = peExamRoom;
	}

	public PrTchStuElective getPrTchStuElective() {
		return this.prTchStuElective;
	}

	public void setPrTchStuElective(PrTchStuElective prTchStuElective) {
		this.prTchStuElective = prTchStuElective;
	}

	public PeExamNo getPeExamNo() {
		return this.peExamNo;
	}

	public void setPeExamNo(PeExamNo peExamNo) {
		this.peExamNo = peExamNo;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getSeatNo() {
		return this.seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public Double getScoreExam() {
		return this.scoreExam;
	}

	public void setScoreExam(Double scoreExam) {
		this.scoreExam = scoreExam;
	}

	public String getFlagScoreStatus() {
		return this.flagScoreStatus;
	}

	public void setFlagScoreStatus(String flagScoreStatus) {
		this.flagScoreStatus = flagScoreStatus;
	}

	public Double getScoreExamA() {
		return this.scoreExamA;
	}

	public void setScoreExamA(Double scoreExamA) {
		this.scoreExamA = scoreExamA;
	}

	public Double getScoreExamB() {
		return this.scoreExamB;
	}

	public void setScoreExamB(Double scoreExamB) {
		this.scoreExamB = scoreExamB;
	}

	public String getFlagScoreStatusa() {
		return this.flagScoreStatusa;
	}

	public void setFlagScoreStatusa(String flagScoreStatusa) {
		this.flagScoreStatusa = flagScoreStatusa;
	}

	public String getFlagScoreStatusb() {
		return this.flagScoreStatusb;
	}

	public void setFlagScoreStatusb(String flagScoreStatusb) {
		this.flagScoreStatusb = flagScoreStatusb;
	}

}