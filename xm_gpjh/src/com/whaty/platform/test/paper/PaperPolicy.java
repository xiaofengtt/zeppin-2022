package com.whaty.platform.test.paper;

public abstract class PaperPolicy {

	private String id;

	private String title;

	private String policyCore;

	private String note;

	private String creatUser;

	private String creatDate;

	private String status;

	private String type;

	private String groupId;

	private String testTime;

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public String getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getPolicyCore() {
		return policyCore;
	}

	public void setPolicyCore(String policyCore) {
		this.policyCore = policyCore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
