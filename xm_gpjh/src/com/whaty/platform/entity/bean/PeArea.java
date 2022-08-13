package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeArea entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeArea extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeArea peArea;
	private String name;
	private Set peAreas = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeArea() {
	}

	/** full constructor */
	public PeArea(PeArea peArea, String name, Set peAreas) {
		this.peArea = peArea;
		this.name = name;
		this.peAreas = peAreas;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeArea getPeArea() {
		return this.peArea;
	}

	public void setPeArea(PeArea peArea) {
		this.peArea = peArea;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getPeAreas() {
		return this.peAreas;
	}

	public void setPeAreas(Set peAreas) {
		this.peAreas = peAreas;
	}

}