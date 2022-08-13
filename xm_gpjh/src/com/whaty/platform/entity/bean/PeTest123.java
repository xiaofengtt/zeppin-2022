package com.whaty.platform.entity.bean;

/**
 * PeTest123 entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTest123 extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;

	// Constructors

	/** default constructor */
	public PeTest123() {
	}

	/** full constructor */
	public PeTest123(String name) {
		this.name = name;
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

}