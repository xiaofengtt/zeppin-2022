package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrFinacDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrFinacDetail extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeFinancial peFinancial;
	private PrFinacDetail prFinacDetail;
	private String name;
	private String value;
	private String note;
	private Set prFinacDetails = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrFinacDetail() {
	}

	/** full constructor */
	public PrFinacDetail(PeFinancial peFinancial, PrFinacDetail prFinacDetail,
			String name, String value, String note, Set prFinacDetails) {
		this.peFinancial = peFinancial;
		this.prFinacDetail = prFinacDetail;
		this.name = name;
		this.value = value;
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

	public PeFinancial getPeFinancial() {
		return this.peFinancial;
	}

	public void setPeFinancial(PeFinancial peFinancial) {
		this.peFinancial = peFinancial;
	}

	public PrFinacDetail getPrFinacDetail() {
		return this.prFinacDetail;
	}

	public void setPrFinacDetail(PrFinacDetail prFinacDetail) {
		this.prFinacDetail = prFinacDetail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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