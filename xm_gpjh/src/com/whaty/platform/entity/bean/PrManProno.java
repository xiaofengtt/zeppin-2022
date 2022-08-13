package com.whaty.platform.entity.bean;

/**
 * PrManProno entity. @author MyEclipse Persistence Tools
 */

public class PrManProno extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeManager peManager;
	private PeProApplyno peProApplyno;

	// Constructors

	/** default constructor */
	public PrManProno() {
	}

	/** full constructor */
	public PrManProno(PeManager peManager, PeProApplyno peProApplyno) {
		this.peManager = peManager;
		this.peProApplyno = peProApplyno;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeManager getPeManager() {
		return this.peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

}