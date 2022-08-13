/**
 * 
 */
package com.whaty.platform.entity.fee.deal;

import java.util.Date;

import com.whaty.platform.User;

/**
 * @author chenjian
 *
 */
public  class UserFeeRecord {
	
	private String id;
	
	private User user;
	
	private Date dateTime;
	
	private double feeValue;
	
	private String feeType;
	
	private String payoutType;
	
	private boolean  checked;
	
	private String note;
	
	private String orderNo;
	
	private String invoiceStatus; //发票打印状态
	
	private String invoiceDate;  //发票打印时间

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public double getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(double feeValue) {
		this.feeValue = feeValue;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPayoutType() {
		return payoutType;
	}

	public void setPayoutType(String payoutType) {
		this.payoutType = payoutType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	

}
