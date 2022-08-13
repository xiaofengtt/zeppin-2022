package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeFinancial entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeFinancial extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConst;
	private PeProImplemt peProImplemt;
	private String fkProgramImplement;
	private String name;
	private Date createDate;
	private String note;
	private Set prFinacDetails = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeFinancial() {
	}

	/** minimal constructor */
	public PeFinancial(PeProImplemt peProImplemt) {
		this.peProImplemt = peProImplemt;
	}

	/** full constructor */
	public PeFinancial(EnumConst enumConst, PeProImplemt peProImplemt,
			String fkProgramImplement, String name, Date createDate,
			String note, Set prFinacDetails) {
		this.enumConst = enumConst;
		this.peProImplemt = peProImplemt;
		this.fkProgramImplement = fkProgramImplement;
		this.name = name;
		this.createDate = createDate;
		this.note = note;
		this.prFinacDetails = prFinacDetails;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConst() {
		return this.enumConst;
	}

	public void setEnumConst(EnumConst enumConst) {
		this.enumConst = enumConst;
	}

	public PeProImplemt getPeProImplemt() {
		return this.peProImplemt;
	}

	public void setPeProImplemt(PeProImplemt peProImplemt) {
		this.peProImplemt = peProImplemt;
	}

	public String getFkProgramImplement() {
		return this.fkProgramImplement;
	}

	public void setFkProgramImplement(String fkProgramImplement) {
		this.fkProgramImplement = fkProgramImplement;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrFinacDetails() {
		return this.prFinacDetails;
	}

	public void setPrFinacDetails(Set prFinacDetails) {
		this.prFinacDetails = prFinacDetails;
	}

}