package com.whaty.platform.entity.bean;

/**
 * TestInsert entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestInsert extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;

	// Constructors

	/** default constructor */
	public TestInsert() {
	}

	/** full constructor */
	public TestInsert(String name) {
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