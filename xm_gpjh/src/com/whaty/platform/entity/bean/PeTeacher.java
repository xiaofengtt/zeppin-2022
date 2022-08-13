package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeTeacher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTeacher extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String loginId;
	private String name;
	private String trueName;
	private String fkSsoUserId;
	private String flagIsvalid;
	private String cardNo;
	private String gender;
	private Date birthday;
	private String phoneOffice;
	private String phoneHome;
	private String mobilephone;
	private String email;
	private String workplace;
	private String note;
	private Set prTchCourseTeachers = new HashSet(0);
	private Set prTraingCourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTeacher() {
	}

	/** minimal constructor */
	public PeTeacher(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeTeacher(String loginId, String name, String trueName,
			String fkSsoUserId, String flagIsvalid, String cardNo,
			String gender, Date birthday, String phoneOffice, String phoneHome,
			String mobilephone, String email, String workplace, String note,
			Set prTchCourseTeachers, Set prTraingCourses) {
		this.loginId = loginId;
		this.name = name;
		this.trueName = trueName;
		this.fkSsoUserId = fkSsoUserId;
		this.flagIsvalid = flagIsvalid;
		this.cardNo = cardNo;
		this.gender = gender;
		this.birthday = birthday;
		this.phoneOffice = phoneOffice;
		this.phoneHome = phoneHome;
		this.mobilephone = mobilephone;
		this.email = email;
		this.workplace = workplace;
		this.note = note;
		this.prTchCourseTeachers = prTchCourseTeachers;
		this.prTraingCourses = prTraingCourses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoneOffice() {
		return this.phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getPhoneHome() {
		return this.phoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrTchCourseTeachers() {
		return this.prTchCourseTeachers;
	}

	public void setPrTchCourseTeachers(Set prTchCourseTeachers) {
		this.prTchCourseTeachers = prTchCourseTeachers;
	}

	public Set getPrTraingCourses() {
		return this.prTraingCourses;
	}

	public void setPrTraingCourses(Set prTraingCourses) {
		this.prTraingCourses = prTraingCourses;
	}

}