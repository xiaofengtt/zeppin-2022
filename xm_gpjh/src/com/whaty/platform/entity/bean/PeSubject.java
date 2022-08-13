package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeSubject extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String note;
	private Set peTrainExperts = new HashSet(0);
	private Set peProApplies = new HashSet(0);
	private Set peTrainees = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeSubject() {
	}

	/** full constructor */
	public PeSubject(String name, String code, String note, Set peTrainExperts,
			Set peProApplies, Set peTrainees) {
		this.name = name;
		this.code = code;
		this.note = note;
		this.peTrainExperts = peTrainExperts;
		this.peProApplies = peProApplies;
		this.peTrainees = peTrainees;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeTrainExperts() {
		return this.peTrainExperts;
	}

	public void setPeTrainExperts(Set peTrainExperts) {
		this.peTrainExperts = peTrainExperts;
	}

	public Set getPeProApplies() {
		return this.peProApplies;
	}

	public void setPeProApplies(Set peProApplies) {
		this.peProApplies = peProApplies;
	}

	public Set getPeTrainees() {
		return this.peTrainees;
	}

	public void setPeTrainees(Set peTrainees) {
		this.peTrainees = peTrainees;
	}

}