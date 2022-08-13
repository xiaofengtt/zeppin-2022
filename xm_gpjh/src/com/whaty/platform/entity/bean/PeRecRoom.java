package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeRecRoom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeRecRoom extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSite peSite;
	private PeRecruitplan peRecruitplan;
	private String name;
	private String code;
	private String invigilatorA;
	private String invigilatorB;
	private String classroom;
	private Set peRecStudents = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeRecRoom() {
	}

	/** minimal constructor */
	public PeRecRoom(String name, String code) {
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public PeRecRoom(PeSite peSite, PeRecruitplan peRecruitplan, String name,
			String code, String invigilatorA, String invigilatorB,
			String classroom, Set peRecStudents) {
		this.peSite = peSite;
		this.peRecruitplan = peRecruitplan;
		this.name = name;
		this.code = code;
		this.invigilatorA = invigilatorA;
		this.invigilatorB = invigilatorB;
		this.classroom = classroom;
		this.peRecStudents = peRecStudents;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public PeRecruitplan getPeRecruitplan() {
		return this.peRecruitplan;
	}

	public void setPeRecruitplan(PeRecruitplan peRecruitplan) {
		this.peRecruitplan = peRecruitplan;
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

	public Set getPeRecStudents() {
		return this.peRecStudents;
	}

	public void setPeRecStudents(Set peRecStudents) {
		this.peRecStudents = peRecStudents;
	}

}