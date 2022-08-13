package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * SmsInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmsInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String targets;
	private String content;
	private String smsStatus;
	private Date sendDate;
	private String scope;
	private String sender;
	private String checker;
	private String site;
	private String note;
	private String teaId;
	private Long sendobjnum;
	private Long splititem;
	private String settime;
	private String type;
	private String sendstatus;

	// Constructors

	/** default constructor */
	public SmsInfo() {
	}

	/** full constructor */
	public SmsInfo(String targets, String content, String smsStatus,
			Date sendDate, String scope, String sender, String checker,
			String site, String note, String teaId, Long sendobjnum,
			Long splititem, String settime, String type, String sendstatus) {
		this.targets = targets;
		this.content = content;
		this.smsStatus = smsStatus;
		this.sendDate = sendDate;
		this.scope = scope;
		this.sender = sender;
		this.checker = checker;
		this.site = site;
		this.note = note;
		this.teaId = teaId;
		this.sendobjnum = sendobjnum;
		this.splititem = splititem;
		this.settime = settime;
		this.type = type;
		this.sendstatus = sendstatus;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargets() {
		return this.targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSmsStatus() {
		return this.smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTeaId() {
		return this.teaId;
	}

	public void setTeaId(String teaId) {
		this.teaId = teaId;
	}

	public Long getSendobjnum() {
		return this.sendobjnum;
	}

	public void setSendobjnum(Long sendobjnum) {
		this.sendobjnum = sendobjnum;
	}

	public Long getSplititem() {
		return this.splititem;
	}

	public void setSplititem(Long splititem) {
		this.splititem = splititem;
	}

	public String getSettime() {
		return this.settime;
	}

	public void setSettime(String settime) {
		this.settime = settime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSendstatus() {
		return this.sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}

}