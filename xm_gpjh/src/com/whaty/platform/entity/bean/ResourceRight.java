package com.whaty.platform.entity.bean;

/**
 * ResourceRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ResourceRight extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String dirId;

	// Constructors

	/** default constructor */
	public ResourceRight() {
	}

	/** full constructor */
	public ResourceRight(String userId, String dirId) {
		this.userId = userId;
		this.dirId = dirId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDirId() {
		return this.dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

}