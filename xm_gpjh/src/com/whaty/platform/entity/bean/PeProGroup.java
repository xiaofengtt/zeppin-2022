package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeProGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeProGroup extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String note;
	private Set peProApplies = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeProGroup() {
	}

	/** full constructor */
	public PeProGroup(String name, String note, Set peProApplies) {
		this.name = name;
		this.note = note;
		this.peProApplies = peProApplies;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeProApplies() {
		return this.peProApplies;
	}

	public void setPeProApplies(Set peProApplies) {
		this.peProApplies = peProApplies;
	}

}