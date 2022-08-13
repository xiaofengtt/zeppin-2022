package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrPcNoteReply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcNoteReply extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrPcElective prPcElective;
	private String fkNoteId;
	private Date replyDatetime;
	private String note;

	// Constructors

	/** default constructor */
	public PrPcNoteReply() {
	}

	/** full constructor */
	public PrPcNoteReply(PrPcElective prPcElective, String fkNoteId,
			Date replyDatetime, String note) {
		this.prPcElective = prPcElective;
		this.fkNoteId = fkNoteId;
		this.replyDatetime = replyDatetime;
		this.note = note;
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

	public String getFkNoteId() {
		return this.fkNoteId;
	}

	public void setFkNoteId(String fkNoteId) {
		this.fkNoteId = fkNoteId;
	}

	public Date getReplyDatetime() {
		return this.replyDatetime;
	}

	public void setReplyDatetime(Date replyDatetime) {
		this.replyDatetime = replyDatetime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}