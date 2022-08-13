package com.whaty.platform.entity.bean;

/**
 * PeInfoNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeInfoNews extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private PeInfoNewsId id;
	private PeInfoNewsType peInfoNewsType;

	// Constructors

	/** default constructor */
	public PeInfoNews() {
	}

	/** minimal constructor */
	public PeInfoNews(PeInfoNewsId id) {
		this.id = id;
	}

	/** full constructor */
	public PeInfoNews(PeInfoNewsId id, PeInfoNewsType peInfoNewsType) {
		this.id = id;
		this.peInfoNewsType = peInfoNewsType;
	}

	// Property accessors

	public PeInfoNewsId getId() {
		return this.id;
	}

	public void setId(PeInfoNewsId id) {
		this.id = id;
	}

	public PeInfoNewsType getPeInfoNewsType() {
		return this.peInfoNewsType;
	}

	public void setPeInfoNewsType(PeInfoNewsType peInfoNewsType) {
		this.peInfoNewsType = peInfoNewsType;
	}

}