package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * SystemApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SystemApply extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String fkTraineeId;
	private String applyType;
	private String flagApplyStatus;
	private Date applyDate;
	private Date checkDate;
	private String applyNote;
	private String checkNote;
	private String applyInfo;
	private String moveSn;

	// Constructors

	/** default constructor */
	public SystemApply() {
	}

	/** full constructor */
	public SystemApply(String fkTraineeId, String applyType,
			String flagApplyStatus, Date applyDate, Date checkDate,
			String applyNote, String checkNote, String applyInfo, String moveSn) {
		this.fkTraineeId = fkTraineeId;
		this.applyType = applyType;
		this.flagApplyStatus = flagApplyStatus;
		this.applyDate = applyDate;
		this.checkDate = checkDate;
		this.applyNote = applyNote;
		this.checkNote = checkNote;
		this.applyInfo = applyInfo;
		this.moveSn = moveSn;
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

	public String getApplyType() {
		return this.applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getFlagApplyStatus() {
		return this.flagApplyStatus;
	}

	public void setFlagApplyStatus(String flagApplyStatus) {
		this.flagApplyStatus = flagApplyStatus;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getApplyNote() {
		return this.applyNote;
	}

	public void setApplyNote(String applyNote) {
		this.applyNote = applyNote;
	}

	public String getCheckNote() {
		return this.checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}

	public String getApplyInfo() {
		return this.applyInfo;
	}

	public void setApplyInfo(String applyInfo) {
		this.applyInfo = applyInfo;
	}

	public String getMoveSn() {
		return this.moveSn;
	}

	public void setMoveSn(String moveSn) {
		this.moveSn = moveSn;
	}

}