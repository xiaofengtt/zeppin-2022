package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * Tmp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Tmp extends com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private Date test;

	// Constructors

	/** default constructor */
	public Tmp() {
	}

	// Property accessors

	public Date getTest() {
		return this.test;
	}

	public void setTest(Date test) {
		this.test = test;
	}

}