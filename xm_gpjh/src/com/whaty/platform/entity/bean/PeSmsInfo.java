package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeSmsInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeSmsInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSite peSite;
	private String flagSmsType;
	private String flagSmsStatus;
	private Date creatDate;
	private Date bookingDate;
	private Date sendDate;
	private String returnReason;
	private String senderName;
	private String senderLoginInId;
	private String note;
	private Set prSmsSendStatuses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeSmsInfo() {
	}

	/** full constructor */
	public PeSmsInfo(PeSite peSite, String flagSmsType, String flagSmsStatus,
			Date creatDate, Date bookingDate, Date sendDate,
			String returnReason, String senderName, String senderLoginInId,
			String note, Set prSmsSendStatuses) {
		this.peSite = peSite;
		this.flagSmsType = flagSmsType;
		this.flagSmsStatus = flagSmsStatus;
		this.creatDate = creatDate;
		this.bookingDate = bookingDate;
		this.sendDate = sendDate;
		this.returnReason = returnReason;
		this.senderName = senderName;
		this.senderLoginInId = senderLoginInId;
		this.note = note;
		this.prSmsSendStatuses = prSmsSendStatuses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public String getFlagSmsType() {
		return this.flagSmsType;
	}

	public void setFlagSmsType(String flagSmsType) {
		this.flagSmsType = flagSmsType;
	}

	public String getFlagSmsStatus() {
		return this.flagSmsStatus;
	}

	public void setFlagSmsStatus(String flagSmsStatus) {
		this.flagSmsStatus = flagSmsStatus;
	}

	public Date getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getReturnReason() {
		return this.returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderLoginInId() {
		return this.senderLoginInId;
	}

	public void setSenderLoginInId(String senderLoginInId) {
		this.senderLoginInId = senderLoginInId;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrSmsSendStatuses() {
		return this.prSmsSendStatuses;
	}

	public void setPrSmsSendStatuses(Set prSmsSendStatuses) {
		this.prSmsSendStatuses = prSmsSendStatuses;
	}

}