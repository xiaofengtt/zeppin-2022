package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrFeeDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrFeeDetail extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeStudent peStudent;
	private PeFeeBatch peFeeBatch;
	private String flagFeeType;
	private String flagFeeCheck;
	private Double feeAmount;
	private Double creditAmount;
	private Date inputDate;
	private String invoiceNo;
	private String note;

	// Constructors

	/** default constructor */
	public PrFeeDetail() {
	}

	/** full constructor */
	public PrFeeDetail(PeStudent peStudent, PeFeeBatch peFeeBatch,
			String flagFeeType, String flagFeeCheck, Double feeAmount,
			Double creditAmount, Date inputDate, String invoiceNo, String note) {
		this.peStudent = peStudent;
		this.peFeeBatch = peFeeBatch;
		this.flagFeeType = flagFeeType;
		this.flagFeeCheck = flagFeeCheck;
		this.feeAmount = feeAmount;
		this.creditAmount = creditAmount;
		this.inputDate = inputDate;
		this.invoiceNo = invoiceNo;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeStudent getPeStudent() {
		return this.peStudent;
	}

	public void setPeStudent(PeStudent peStudent) {
		this.peStudent = peStudent;
	}

	public PeFeeBatch getPeFeeBatch() {
		return this.peFeeBatch;
	}

	public void setPeFeeBatch(PeFeeBatch peFeeBatch) {
		this.peFeeBatch = peFeeBatch;
	}

	public String getFlagFeeType() {
		return this.flagFeeType;
	}

	public void setFlagFeeType(String flagFeeType) {
		this.flagFeeType = flagFeeType;
	}

	public String getFlagFeeCheck() {
		return this.flagFeeCheck;
	}

	public void setFlagFeeCheck(String flagFeeCheck) {
		this.flagFeeCheck = flagFeeCheck;
	}

	public Double getFeeAmount() {
		return this.feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}