package com.whaty.platform.entity.bean;

/**
 * PrPriManagerEdutype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPriManagerEdutype extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeEdutype peEdutype;
	private String fkSsoUserId;

	// Constructors

	/** default constructor */
	public PrPriManagerEdutype() {
	}

	/** full constructor */
	public PrPriManagerEdutype(PeEdutype peEdutype, String fkSsoUserId) {
		this.peEdutype = peEdutype;
		this.fkSsoUserId = fkSsoUserId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

}