package com.whaty.platform.entity.apply;

import java.util.List;

public class Apply {
	private String id;

	private String title;

	private String note;

	private String applyUser;

	private String applyDate;

	private ApplyType applyType;

	private List confirmStatus;

	private List confirmRecord;

	private String executeCode;

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public ApplyType getApplyType() {
		return applyType;
	}

	public void setApplyType(ApplyType applyType) {
		this.applyType = applyType;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public List getConfirmRecord() {
		return confirmRecord;
	}

	public void setConfirmRecord(List confirmRecord) {
		this.confirmRecord = confirmRecord;
	}

	public List getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(List confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getExecuteCode() {
		return executeCode;
	}

	public void setExecuteCode(String executeCode) {
		this.executeCode = executeCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isPass(String step_id){
		return true;
	}
	
	public int doConfirm(){
		return 0;
	}
	
	public int doRefuse(){
		return 0;
	}
	
	public int doCancel(){
		return 0;
	}
	
	public void doExecute(){
		
	}
}
