package com.whaty.platform.entity.bean;

/**
 * PeJianzhang entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeJianzhang extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private PeJianzhangId id;

	// Constructors

	/** default constructor */
	public PeJianzhang() {
	}

	/** full constructor */
	public PeJianzhang(PeJianzhangId id) {
		this.id = id;
	}

	// Property accessors

	public PeJianzhangId getId() {
		return this.id;
	}

	public void setId(PeJianzhangId id) {
		this.id = id;
	}

}