package com.whaty.platform.entity.bean;

/**
 * PeInfoModify entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeInfoModify extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private PeInfoModifyId id;

	// Constructors

	/** default constructor */
	public PeInfoModify() {
	}

	/** full constructor */
	public PeInfoModify(PeInfoModifyId id) {
		this.id = id;
	}

	// Property accessors

	public PeInfoModifyId getId() {
		return this.id;
	}

	public void setId(PeInfoModifyId id) {
		this.id = id;
	}

}