package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeExamRoom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExamRoom extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeExamNo peExamNo;
	private PeSite peSite;
	private String name;
	private String code;
	private String invigilatorA;
	private String invigilatorB;
	private String classroom;
	private Set prExamBookings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeExamRoom() {
	}

	/** minimal constructor */
	public PeExamRoom(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeExamRoom(PeExamNo peExamNo, PeSite peSite, String name,
			String code, String invigilatorA, String invigilatorB,
			String classroom, Set prExamBookings) {
		this.peExamNo = peExamNo;
		this.peSite = peSite;
		this.name = name;
		this.code = code;
		this.invigilatorA = invigilatorA;
		this.invigilatorB = invigilatorB;
		this.classroom = classroom;
		this.prExamBookings = prExamBookings;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeExamNo getPeExamNo() {
		return this.peExamNo;
	}

	public void setPeExamNo(PeExamNo peExamNo) {
		this.peExamNo = peExamNo;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
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

	public String getInvigilatorA() {
		return this.invigilatorA;
	}

	public void setInvigilatorA(String invigilatorA) {
		this.invigilatorA = invigilatorA;
	}

	public String getInvigilatorB() {
		return this.invigilatorB;
	}

	public void setInvigilatorB(String invigilatorB) {
		this.invigilatorB = invigilatorB;
	}

	public String getClassroom() {
		return this.classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public Set getPrExamBookings() {
		return this.prExamBookings;
	}

	public void setPrExamBookings(Set prExamBookings) {
		this.prExamBookings = prExamBookings;
	}

}