package com.whaty.platform.campus.base;

import com.whaty.platform.Items;
public abstract class AssociationMember implements Items {

	public static String STUDENT_MEMBER = "STUDENT";
	
	public static String TEACHER_MEMBER = "TEACHER";
	
	public static String MANAGER_MEMBER = "MANAGER";
	
	public static String SITEMANAGER_MEMBER = "SITEMANAGER";
	
	public static String SITETEACHER_MEMBER = "SITETEACHER";
	
	public static String OTHER_MEMBER = "OTHER";
	
	private String id = "";

	private String name = "";
	
	private String status = ""; //成员加入审核状态 0：未审核  1：已通过  2：未通过

	private Association association = null;//社团
	
	private String loginId = "";//登录ID
	
	private String password = "";//密码
	
	private String memberType = "";//成员类型:学生、教师、管理员、分站管理员、分站教师
	
	private String phone = "";//固定电话
	
	private String mobilephone = "";//移动电话
	
	private String email = "";//邮箱
	
	private String applyDate = "";// 申请加入时间
	
	private String checkDate = "";//审核日期
	
	private String checker = "";//审核人
	
	private String linkId = "";//与学生、教师、管理员、分站管理员、分站教师关联ID
	
	private String role = "0";//成员角色：0普通成员  1：管理员

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
