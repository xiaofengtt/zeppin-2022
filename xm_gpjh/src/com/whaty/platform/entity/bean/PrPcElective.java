package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrPcElective entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcElective extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePcStudent pePcStudent;
	private PrPcOpencourse prPcOpencourse;
	private String flagEnrol;
	private String flagBak;
	private String roomNum;
	private String seatNum;
	private Double score;
	private Set prPcStuExercises = new HashSet(0);
	private Set prPcNoteReplies = new HashSet(0);
	private Set prPcStuBookings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrPcElective() {
	}

	/** full constructor */
	public PrPcElective(PePcStudent pePcStudent, PrPcOpencourse prPcOpencourse,
			String flagEnrol, String flagBak, String roomNum, String seatNum,
			Double score, Set prPcStuExercises, Set prPcNoteReplies,
			Set prPcStuBookings) {
		this.pePcStudent = pePcStudent;
		this.prPcOpencourse = prPcOpencourse;
		this.flagEnrol = flagEnrol;
		this.flagBak = flagBak;
		this.roomNum = roomNum;
		this.seatNum = seatNum;
		this.score = score;
		this.prPcStuExercises = prPcStuExercises;
		this.prPcNoteReplies = prPcNoteReplies;
		this.prPcStuBookings = prPcStuBookings;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePcStudent getPePcStudent() {
		return this.pePcStudent;
	}

	public void setPePcStudent(PePcStudent pePcStudent) {
		this.pePcStudent = pePcStudent;
	}

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public String getFlagEnrol() {
		return this.flagEnrol;
	}

	public void setFlagEnrol(String flagEnrol) {
		this.flagEnrol = flagEnrol;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public String getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getSeatNum() {
		return this.seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Set getPrPcStuExercises() {
		return this.prPcStuExercises;
	}

	public void setPrPcStuExercises(Set prPcStuExercises) {
		this.prPcStuExercises = prPcStuExercises;
	}

	public Set getPrPcNoteReplies() {
		return this.prPcNoteReplies;
	}

	public void setPrPcNoteReplies(Set prPcNoteReplies) {
		this.prPcNoteReplies = prPcNoteReplies;
	}

	public Set getPrPcStuBookings() {
		return this.prPcStuBookings;
	}

	public void setPrPcStuBookings(Set prPcStuBookings) {
		this.prPcStuBookings = prPcStuBookings;
	}

}