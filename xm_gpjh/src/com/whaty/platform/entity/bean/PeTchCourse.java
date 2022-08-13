package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchCourse implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFlagBak;
	private EnumConst enumConstByFlagIsvalid;
	private String name;
	private String code;
	private Double credit;
	private String note;
	private Set peQuestionses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchCourse() {
	}

	/** minimal constructor */
	public PeTchCourse(String id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public PeTchCourse(String id, EnumConst enumConstByFlagBak,
			EnumConst enumConstByFlagIsvalid, String name, String code,
			Double credit, String note, Set peQuestionses) {
		this.id = id;
		this.enumConstByFlagBak = enumConstByFlagBak;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.name = name;
		this.code = code;
		this.credit = credit;
		this.note = note;
		this.peQuestionses = peQuestionses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFlagBak() {
		return this.enumConstByFlagBak;
	}

	public void setEnumConstByFlagBak(EnumConst enumConstByFlagBak) {
		this.enumConstByFlagBak = enumConstByFlagBak;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
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

	public Double getCredit() {
		return this.credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeQuestionses() {
		return this.peQuestionses;
	}

	public void setPeQuestionses(Set peQuestionses) {
		this.peQuestionses = peQuestionses;
	}

}