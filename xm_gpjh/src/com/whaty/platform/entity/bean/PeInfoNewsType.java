package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeInfoNewsType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeInfoNewsType extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String note;
	private Set peInfoNewses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeInfoNewsType() {
	}

	/** minimal constructor */
	public PeInfoNewsType(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeInfoNewsType(String name, String note, Set peInfoNewses) {
		this.name = name;
		this.note = note;
		this.peInfoNewses = peInfoNewses;
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

	public Set getPeInfoNewses() {
		return this.peInfoNewses;
	}

	public void setPeInfoNewses(Set peInfoNewses) {
		this.peInfoNewses = peInfoNewses;
	}

}