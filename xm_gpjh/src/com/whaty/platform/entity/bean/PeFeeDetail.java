package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeFeeDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeFeeDetail extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String fkTraineeId;
	private String flagFeeCheck;
	private Double feeAmount;
	private Date inputDate;
	private String flagInvoiceStatus;
	private String invoiceNo;
	private String note;
	private String fkManagerId;

	// Constructors

	/** default constructor */
	public PeFeeDetail() {
	}

	/** full constructor */
	public PeFeeDetail(String fkTraineeId, String flagFeeCheck,
			Double feeAmount, Date inputDate, String flagInvoiceStatus,
			String invoiceNo, String note, String fkManagerId) {
		this.fkTraineeId = fkTraineeId;
		this.flagFeeCheck = flagFeeCheck;
		this.feeAmount = feeAmount;
		this.inputDate = inputDate;
		this.flagInvoiceStatus = flagInvoiceStatus;
		this.invoiceNo = invoiceNo;
		this.note = note;
		this.fkManagerId = fkManagerId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkTraineeId() {
		return this.fkTraineeId;
	}

	public void setFkTraineeId(String fkTraineeId) {
		this.fkTraineeId = fkTraineeId;
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

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getFlagInvoiceStatus() {
		return this.flagInvoiceStatus;
	}

	public void setFlagInvoiceStatus(String flagInvoiceStatus) {
		this.flagInvoiceStatus = flagInvoiceStatus;
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

	public String getFkManagerId() {
		return this.fkManagerId;
	}

	public void setFkManagerId(String fkManagerId) {
		this.fkManagerId = fkManagerId;
	}

}