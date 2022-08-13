package com.whaty.platform.campus.base;

import java.util.ArrayList;
import java.util.List;
import com.whaty.platform.Items;
public abstract class Association implements Items {

	private String id = "";

	private String title = "";
	
	private String status = ""; //社团审核状态 0：未审核  1：已通过  2：未通过

	private String applyDate = "";// 申请社团时间
	
	private String checkDate = "";//审核日期

	private String note = "";
	
	private String forumId = "";
	
	private String managerId = "";//社团管理员ID
	
	private String managerName = "";//社团管理员姓名
	
	private String createrType = "";//社团创建者类型

	private List members = new ArrayList();
	
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getForumId() {
		return forumId;
	}

	public void setForumId(String forumId) {
		this.forumId = forumId;
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


	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public List getMembers() {
		return members;
	}

	public void setMembers(List members) {
		this.members = members;
	}

	public String getCreaterType() {
		return createrType;
	}

	public void setCreaterType(String createrType) {
		this.createrType = createrType;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
}
