/*
 * Manager.java
 *
 * Created on 2004��9��30��, ����11:08
 */

package com.whaty.platform.entity.user;

import java.util.List;

import com.whaty.platform.util.RedundanceData;

/**
 * ����Ա������
 * 
 * @author Ligang
 */
public abstract class Manager extends EntityUser implements
		com.whaty.platform.Items {

	/** Creates a new instance of Manager */
	public Manager() {
	}

	private String site_id;

	private String group_id;

	private String group_name;

	private int login_num;

	private String status;

	private String dep_id;

	private RedundanceData redundace = null;

	private HumanPlatformInfo platformInfo = null;

	private String site;

	private String major;
	

	private String grade;

	private String eduType;
	
	private String mobilePhone;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * ���� site_id �Ļ�ȡ������
	 * 
	 * @return ���� site_id ��ֵ��
	 */
	public java.lang.String getSite_id() {
		return site_id;
	}

	/**
	 * ���� site_id �����÷�����
	 * 
	 * @param site_id
	 *            ���� site_id ����ֵ��
	 */
	public void setSite_id(java.lang.String site_id) {
		this.site_id = site_id;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public int getLogin_num() {
		return login_num;
	}

	public void setLogin_num(int login_num) {
		this.login_num = login_num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return ���� dep_id��
	 */
	public String getDep_id() {
		return dep_id;
	}

	/**
	 * @param dep_id
	 *            Ҫ���õ� dep_id��
	 */
	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}

	/**
	 * �жϹ���ԱId�Ƿ��Ѿ�����
	 * 
	 * @return 0Ϊ�����ڣ�����0Ϊ����
	 */
	public abstract int isIdExist();

	public RedundanceData getRedundace() {
		return redundace;
	}

	public void setRedundace(RedundanceData redundace) {
		this.redundace = redundace;
	}

	public HumanPlatformInfo getPlatformInfo() {
		return platformInfo;
	}

	public void setPlatformInfo(HumanPlatformInfo platformInfo) {
		this.platformInfo = platformInfo;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getEduType() {
		return eduType;
	}

	public void setEduType(String eduType) {
		this.eduType = eduType;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public abstract int updateRangeAdminRights(String id, String site,
			String grade, String major, String edutype);

	public abstract int changeGroup();

	public abstract int updateRight(List right);

}
