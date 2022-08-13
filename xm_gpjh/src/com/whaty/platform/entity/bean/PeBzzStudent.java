package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzStudent extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeEnterprise peEnterprise;
	private PeBzzBatch peBzzBatch;
	private String name;
	private String regNo;
	private String fkSsoUserId;
	private String gender;
	private String folk;
	private String education;
	private String age;
	private String position;
	private String title;
	private String department;
	private String address;
	private String zipcode;
	private String phone;
	private String mobilePhone;
	private String email;
	private String trueName;
	private Date birthday;
	private Set prBzzTchStuElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeBzzStudent() {
	}

	/** minimal constructor */
	public PeBzzStudent(String regNo) {
		this.regNo = regNo;
	}

	/** full constructor */
	public PeBzzStudent(PeEnterprise peEnterprise, PeBzzBatch peBzzBatch,
			String name, String regNo, String fkSsoUserId, String gender,
			String folk, String education, String age, String position,
			String title, String department, String address, String zipcode,
			String phone, String mobilePhone, String email, String trueName,
			Date birthday, Set prBzzTchStuElectives) {
		this.peEnterprise = peEnterprise;
		this.peBzzBatch = peBzzBatch;
		this.name = name;
		this.regNo = regNo;
		this.fkSsoUserId = fkSsoUserId;
		this.gender = gender;
		this.folk = folk;
		this.education = education;
		this.age = age;
		this.position = position;
		this.title = title;
		this.department = department;
		this.address = address;
		this.zipcode = zipcode;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.trueName = trueName;
		this.birthday = birthday;
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeEnterprise getPeEnterprise() {
		return this.peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public PeBzzBatch getPeBzzBatch() {
		return this.peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Set getPrBzzTchStuElectives() {
		return this.prBzzTchStuElectives;
	}

	public void setPrBzzTchStuElectives(Set prBzzTchStuElectives) {
		this.prBzzTchStuElectives = prBzzTchStuElectives;
	}

}