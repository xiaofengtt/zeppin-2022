package com.whaty.platform.entity.bean;

/**
 * PrProgramUnit entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrProgramUnit extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeUnit peUnit;
	private PeProApplyno peProApplyno;

	// Constructors

	/** default constructor */
	public PrProgramUnit() {
	}

	/** full constructor */
	public PrProgramUnit(PeUnit peUnit, PeProApplyno peProApplyno) {
		this.peUnit = peUnit;
		this.peProApplyno = peProApplyno;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

}