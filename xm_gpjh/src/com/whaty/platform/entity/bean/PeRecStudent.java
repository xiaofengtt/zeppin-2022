package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeRecStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeRecStudent extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrRecPlanMajorSite prRecPlanMajorSite;
	private PeRecRoom peRecRoom;
	private String name;
	private String flagRecChannel;
	private String flagRecStatus;
	private String flagNoexam;
	private String flagNoexamStatus;
	private String flagMatriculate;
	private String flagPublish;
	private String flagTeacher;
	private String flagTeacherStatus;
	private String flagXueliCheck;
	private String recProvince;
	private String gender;
	private String matriculateNum;
	private Date birthday;
	private String cardType;
	private String cardNo;
	private String folk;
	private String province;
	private String city;
	private String section;
	private String zzmm;
	private String xueli;
	private String marriage;
	private String email;
	private String occupation;
	private String workplace;
	private String workplaceZip;
	private String workplacePhone;
	private String address;
	private String phone;
	private String zip;
	private String register;
	private String photoLink;
	private String graduateSchool;
	private String mobilephone;
	private String graduateDate;
	private String graduateCode;
	private String graduateMajor;
	private String workBegindate;
	private String seatNum;
	private String examCardNum;
	private String password;
	private Double recExamFee;
	private Date recExamFeeDate;
	private String recExamFeeInvoice;
	private String note;
	private String flagMajorType;
	private Set peStudents = new HashSet(0);
	private Set prRecExamStuCourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeRecStudent() {
	}

	/** minimal constructor */
	public PeRecStudent(PrRecPlanMajorSite prRecPlanMajorSite, String name,
			String flagNoexam, String flagTeacher) {
		this.prRecPlanMajorSite = prRecPlanMajorSite;
		this.name = name;
		this.flagNoexam = flagNoexam;
		this.flagTeacher = flagTeacher;
	}

	/** full constructor */
	public PeRecStudent(PrRecPlanMajorSite prRecPlanMajorSite,
			PeRecRoom peRecRoom, String name, String flagRecChannel,
			String flagRecStatus, String flagNoexam, String flagNoexamStatus,
			String flagMatriculate, String flagPublish, String flagTeacher,
			String flagTeacherStatus, String flagXueliCheck,
			String recProvince, String gender, String matriculateNum,
			Date birthday, String cardType, String cardNo, String folk,
			String province, String city, String section, String zzmm,
			String xueli, String marriage, String email, String occupation,
			String workplace, String workplaceZip, String workplacePhone,
			String address, String phone, String zip, String register,
			String photoLink, String graduateSchool, String mobilephone,
			String graduateDate, String graduateCode, String graduateMajor,
			String workBegindate, String seatNum, String examCardNum,
			String password, Double recExamFee, Date recExamFeeDate,
			String recExamFeeInvoice, String note, String flagMajorType,
			Set peStudents, Set prRecExamStuCourses) {
		this.prRecPlanMajorSite = prRecPlanMajorSite;
		this.peRecRoom = peRecRoom;
		this.name = name;
		this.flagRecChannel = flagRecChannel;
		this.flagRecStatus = flagRecStatus;
		this.flagNoexam = flagNoexam;
		this.flagNoexamStatus = flagNoexamStatus;
		this.flagMatriculate = flagMatriculate;
		this.flagPublish = flagPublish;
		this.flagTeacher = flagTeacher;
		this.flagTeacherStatus = flagTeacherStatus;
		this.flagXueliCheck = flagXueliCheck;
		this.recProvince = recProvince;
		this.gender = gender;
		this.matriculateNum = matriculateNum;
		this.birthday = birthday;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.folk = folk;
		this.province = province;
		this.city = city;
		this.section = section;
		this.zzmm = zzmm;
		this.xueli = xueli;
		this.marriage = marriage;
		this.email = email;
		this.occupation = occupation;
		this.workplace = workplace;
		this.workplaceZip = workplaceZip;
		this.workplacePhone = workplacePhone;
		this.address = address;
		this.phone = phone;
		this.zip = zip;
		this.register = register;
		this.photoLink = photoLink;
		this.graduateSchool = graduateSchool;
		this.mobilephone = mobilephone;
		this.graduateDate = graduateDate;
		this.graduateCode = graduateCode;
		this.graduateMajor = graduateMajor;
		this.workBegindate = workBegindate;
		this.seatNum = seatNum;
		this.examCardNum = examCardNum;
		this.password = password;
		this.recExamFee = recExamFee;
		this.recExamFeeDate = recExamFeeDate;
		this.recExamFeeInvoice = recExamFeeInvoice;
		this.note = note;
		this.flagMajorType = flagMajorType;
		this.peStudents = peStudents;
		this.prRecExamStuCourses = prRecExamStuCourses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrRecPlanMajorSite getPrRecPlanMajorSite() {
		return this.prRecPlanMajorSite;
	}

	public void setPrRecPlanMajorSite(PrRecPlanMajorSite prRecPlanMajorSite) {
		this.prRecPlanMajorSite = prRecPlanMajorSite;
	}

	public PeRecRoom getPeRecRoom() {
		return this.peRecRoom;
	}

	public void setPeRecRoom(PeRecRoom peRecRoom) {
		this.peRecRoom = peRecRoom;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlagRecChannel() {
		return this.flagRecChannel;
	}

	public void setFlagRecChannel(String flagRecChannel) {
		this.flagRecChannel = flagRecChannel;
	}

	public String getFlagRecStatus() {
		return this.flagRecStatus;
	}

	public void setFlagRecStatus(String flagRecStatus) {
		this.flagRecStatus = flagRecStatus;
	}

	public String getFlagNoexam() {
		return this.flagNoexam;
	}

	public void setFlagNoexam(String flagNoexam) {
		this.flagNoexam = flagNoexam;
	}

	public String getFlagNoexamStatus() {
		return this.flagNoexamStatus;
	}

	public void setFlagNoexamStatus(String flagNoexamStatus) {
		this.flagNoexamStatus = flagNoexamStatus;
	}

	public String getFlagMatriculate() {
		return this.flagMatriculate;
	}

	public void setFlagMatriculate(String flagMatriculate) {
		this.flagMatriculate = flagMatriculate;
	}

	public String getFlagPublish() {
		return this.flagPublish;
	}

	public void setFlagPublish(String flagPublish) {
		this.flagPublish = flagPublish;
	}

	public String getFlagTeacher() {
		return this.flagTeacher;
	}

	public void setFlagTeacher(String flagTeacher) {
		this.flagTeacher = flagTeacher;
	}

	public String getFlagTeacherStatus() {
		return this.flagTeacherStatus;
	}

	public void setFlagTeacherStatus(String flagTeacherStatus) {
		this.flagTeacherStatus = flagTeacherStatus;
	}

	public String getFlagXueliCheck() {
		return this.flagXueliCheck;
	}

	public void setFlagXueliCheck(String flagXueliCheck) {
		this.flagXueliCheck = flagXueliCheck;
	}

	public String getRecProvince() {
		return this.recProvince;
	}

	public void setRecProvince(String recProvince) {
		this.recProvince = recProvince;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMatriculateNum() {
		return this.matriculateNum;
	}

	public void setMatriculateNum(String matriculateNum) {
		this.matriculateNum = matriculateNum;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
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

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
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

	public String getWorkplaceZip() {
		return this.workplaceZip;
	}

	public void setWorkplaceZip(String workplaceZip) {
		this.workplaceZip = workplaceZip;
	}

	public String getWorkplacePhone() {
		return this.workplacePhone;
	}

	public void setWorkplacePhone(String workplacePhone) {
		this.workplacePhone = workplacePhone;
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

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRegister() {
		return this.register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getPhotoLink() {
		return this.photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	public String getGraduateSchool() {
		return this.graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getGraduateDate() {
		return this.graduateDate;
	}

	public void setGraduateDate(String graduateDate) {
		this.graduateDate = graduateDate;
	}

	public String getGraduateCode() {
		return this.graduateCode;
	}

	public void setGraduateCode(String graduateCode) {
		this.graduateCode = graduateCode;
	}

	public String getGraduateMajor() {
		return this.graduateMajor;
	}

	public void setGraduateMajor(String graduateMajor) {
		this.graduateMajor = graduateMajor;
	}

	public String getWorkBegindate() {
		return this.workBegindate;
	}

	public void setWorkBegindate(String workBegindate) {
		this.workBegindate = workBegindate;
	}

	public String getSeatNum() {
		return this.seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public String getExamCardNum() {
		return this.examCardNum;
	}

	public void setExamCardNum(String examCardNum) {
		this.examCardNum = examCardNum;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getRecExamFee() {
		return this.recExamFee;
	}

	public void setRecExamFee(Double recExamFee) {
		this.recExamFee = recExamFee;
	}

	public Date getRecExamFeeDate() {
		return this.recExamFeeDate;
	}

	public void setRecExamFeeDate(Date recExamFeeDate) {
		this.recExamFeeDate = recExamFeeDate;
	}

	public String getRecExamFeeInvoice() {
		return this.recExamFeeInvoice;
	}

	public void setRecExamFeeInvoice(String recExamFeeInvoice) {
		this.recExamFeeInvoice = recExamFeeInvoice;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFlagMajorType() {
		return this.flagMajorType;
	}

	public void setFlagMajorType(String flagMajorType) {
		this.flagMajorType = flagMajorType;
	}

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

	public Set getPrRecExamStuCourses() {
		return this.prRecExamStuCourses;
	}

	public void setPrRecExamStuCourses(Set prRecExamStuCourses) {
		this.prRecExamStuCourses = prRecExamStuCourses;
	}

}