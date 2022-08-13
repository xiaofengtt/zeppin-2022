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
	
	private String status = ""; //��Ա�������״̬ 0��δ���  1����ͨ��  2��δͨ��

	private Association association = null;//����
	
	private String loginId = "";//��¼ID
	
	private String password = "";//����
	
	private String memberType = "";//��Ա����:ѧ������ʦ������Ա����վ����Ա����վ��ʦ
	
	private String phone = "";//�̶��绰
	
	private String mobilephone = "";//�ƶ��绰
	
	private String email = "";//����
	
	private String applyDate = "";// �������ʱ��
	
	private String checkDate = "";//�������
	
	private String checker = "";//�����
	
	private String linkId = "";//��ѧ������ʦ������Ա����վ����Ա����վ��ʦ����ID
	
	private String role = "0";//��Ա��ɫ��0��ͨ��Ա  1������Ա

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
