package com.whaty.platform.campus.base;

import java.util.ArrayList;
import java.util.List;
import com.whaty.platform.Items;
public abstract class Association implements Items {

	private String id = "";

	private String title = "";
	
	private String status = ""; //�������״̬ 0��δ���  1����ͨ��  2��δͨ��

	private String applyDate = "";// ��������ʱ��
	
	private String checkDate = "";//�������

	private String note = "";
	
	private String forumId = "";
	
	private String managerId = "";//���Ź���ԱID
	
	private String managerName = "";//���Ź���Ա����
	
	private String createrType = "";//���Ŵ���������

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
