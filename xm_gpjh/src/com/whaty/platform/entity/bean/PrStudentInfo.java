package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PrStudentInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrStudentInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private Date birthday;
	private String gender;
	private String cardType;
	private String cardNo;
	private String fork;
	private String province;
	private String city;
	private String zzmm;
	private String xueli;
	private String marriage;
	private String email;
	private String occupation;
	private String workplace;
	private String zip;
	private String address;
	private String phone;
	private String mobilephone;
	private String graduateSchool;
	private String graduateSchoolCode;
	private String graduateYear;
	private String graduateCode;
	private String xuezhi;
	private String photoLink;
	private Date photoDate;
	private Date lastInfoUpdateDate;
	private Set peStudents = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrStudentInfo() {
	}

	/** full constructor */
	public PrStudentInfo(Date birthday, String gender, String cardType,
			String cardNo, String fork, String province, String city,
			String zzmm, String xueli, String marriage, String email,
			String occupation, String workplace, String zip, String address,
			String phone, String mobilephone, String graduateSchool,
			String graduateSchoolCode, String graduateYear,
			String graduateCode, String xuezhi, String photoLink,
			Date photoDate, Date lastInfoUpdateDate, Set peStudents) {
		this.birthday = birthday;
		this.gender = gender;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.fork = fork;
		this.province = province;
		this.city = city;
		this.zzmm = zzmm;
		this.xueli = xueli;
		this.marriage = marriage;
		this.email = email;
		this.occupation = occupation;
		this.workplace = workplace;
		this.zip = zip;
		this.address = address;
		this.phone = phone;
		this.mobilephone = mobilephone;
		this.graduateSchool = graduateSchool;
		this.graduateSchoolCode = graduateSchoolCode;
		this.graduateYear = graduateYear;
		this.graduateCode = graduateCode;
		this.xuezhi = xuezhi;
		this.photoLink = photoLink;
		this.photoDate = photoDate;
		this.lastInfoUpdateDate = lastInfoUpdateDate;
		this.peStudents = peStudents;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getFork() {
		return this.fork;
	}

	public void setFork(String fork) {
		this.fork = fork;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZzmm() {
		return this.zzmm;
	}

	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}

	public String getXueli() {
		return this.xueli;
	}

	public void setXueli(String xueli) {
		this.xueli = xueli;
	}

	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getGraduateSchool() {
		return this.graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getGraduateSchoolCode() {
		return this.graduateSchoolCode;
	}

	public void setGraduateSchoolCode(String graduateSchoolCode) {
		this.graduateSchoolCode = graduateSchoolCode;
	}

	public String getGraduateYear() {
		return this.graduateYear;
	}

	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}

	public String getGraduateCode() {
		return this.graduateCode;
	}

	public void setGraduateCode(String graduateCode) {
		this.graduateCode = graduateCode;
	}

	public String getXuezhi() {
		return this.xuezhi;
	}

	public void setXuezhi(String xuezhi) {
		this.xuezhi = xuezhi;
	}

	public String getPhotoLink() {
		return this.photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public Date getPhotoDate() {
		return this.photoDate;
	}

	public void setPhotoDate(Date photoDate) {
		this.photoDate = photoDate;
	}

	public Date getLastInfoUpdateDate() {
		return this.lastInfoUpdateDate;
	}

	public void setLastInfoUpdateDate(Date lastInfoUpdateDate) {
		this.lastInfoUpdateDate = lastInfoUpdateDate;
	}

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

}