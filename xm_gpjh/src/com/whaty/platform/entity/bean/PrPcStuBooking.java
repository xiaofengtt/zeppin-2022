package com.whaty.platform.entity.bean;

/**
 * PrPcStuBooking entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcStuBooking extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrPcElective prPcElective;
	private PrPcBookingSeat prPcBookingSeat;

	// Constructors

	/** default constructor */
	public PrPcStuBooking() {
	}

	/** full constructor */
	public PrPcStuBooking(PrPcElective prPcElective,
			PrPcBookingSeat prPcBookingSeat) {
		this.prPcElective = prPcElective;
		this.prPcBookingSeat = prPcBookingSeat;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrPcElective getPrPcElective() {
		return this.prPcElective;
	}

	public void setPrPcElective(PrPcElective prPcElective) {
		this.prPcElective = prPcElective;
	}

	public PrPcBookingSeat getPrPcBookingSeat() {
		return this.prPcBookingSeat;
	}

	public void setPrPcBookingSeat(PrPcBookingSeat prPcBookingSeat) {
		this.prPcBookingSeat = prPcBookingSeat;
	}

}