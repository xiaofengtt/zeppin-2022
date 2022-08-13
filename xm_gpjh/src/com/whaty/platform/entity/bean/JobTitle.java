package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeProvince entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class JobTitle extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;

	private Set peTrainees = new HashSet(0);

	

	// Constructors

	/** default constructor */
	public JobTitle() {
	}

	/** full constructor */
	public JobTitle(String id, String name, Set peTrainees) {
		super();
		this.id = id;
		this.name = name;
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

	public Set getPeTrainees() {
		return this.peTrainees;
	}

	public void setPeTrainees(Set peTrainees) {
		this.peTrainees = peTrainees;
	}

	
	
}