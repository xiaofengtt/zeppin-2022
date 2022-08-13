package com.gpjh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "PE_TRAINEE")
@Repository("trainEe")
public class TrainEe implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "LOGIN_ID")
	private String loginId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FK_UNIT_FROM")
	private String unitFrom;

	@Column(name = "TELEPHONE")
	private String telephone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "OFFICE_PHONE")
	private String officePhone;

	@Column(name = "FK_GENDER")
	private String gender;

	@Column(name = "AGE")
	private Integer age;

	@Column(name = "FK_GRADUTED")
	private String graduted;

	@Column(name = "FK_MODIFY_CHECKED")
	private String modifyChecked;

	@Column(name = "FK_CHECKED_TRAINEE")
	private String checkedTrainee;

	@Column(name = "FK_SSO_USER_ID")
	private String ssoUserId;

	@Column(name = "FK_SUBJECT")
	private String fkSubject;

	@Column(name = "FK_PROVINCE")
	private String fkProvince;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "FK_STATUS_TRAINING")
	private String statusTraining;

	@Column(name = "FOLK")
	private String folk;

	@Column(name = "WORK_PLACE")
	private String workPlace;

	@Column(name = "EDUCATION")
	private String education;

	@Column(name = "ZHIWU")
	private String zhiWu;

	@Column(name = "WORKYEAR")
	private String workYear;

	//@ManyToOne
	@Column(name = "FK_TRAINING_UNIT")
	private String trainingUnit;

	@Column(name = "FK_PRO_APPLYNO")
	private String proApplyNo;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "NOTESECOND")
	private String noteCond;

	@Column(name = "CERTIFICATE_NUMBER")
	private String certificateNumber;

	@Column(name = "ZHICHENG")
	private String zhiCheng;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "YAOQIUDEFEN")
	private Integer yaoQiuDefen;

	@Column(name = "GUOCHENGDEFEN")
	private Integer guoChengDefen;

	@Column(name = "CHENGXIAODEFEN")
	private Integer chengXiaoDefen;

	@Column(name = "YIJIAN")
	private String yiJian;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitFrom() {
		return unitFrom;
	}

	public void setUnitFrom(String unitFrom) {
		this.unitFrom = unitFrom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGraduted() {
		return graduted;
	}

	public void setGraduted(String graduted) {
		this.graduted = graduted;
	}

	public String getModifyChecked() {
		return modifyChecked;
	}

	public void setModifyChecked(String modifyChecked) {
		this.modifyChecked = modifyChecked;
	}

	public String getCheckedTrainee() {
		return checkedTrainee;
	}

	public void setCheckedTrainee(String checkedTrainee) {
		this.checkedTrainee = checkedTrainee;
	}

	public String getSsoUserId() {
		return ssoUserId;
	}

	public void setSsoUserId(String ssoUserId) {
		this.ssoUserId = ssoUserId;
	}

	public String getFkSubject() {
		return fkSubject;
	}

	public void setFkSubject(String fkSubject) {
		this.fkSubject = fkSubject;
	}

	public String getFkProvince() {
		return fkProvince;
	}

	public void setFkProvince(String fkProvince) {
		this.fkProvince = fkProvince;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatusTraining() {
		return statusTraining;
	}

	public void setStatusTraining(String statusTraining) {
		this.statusTraining = statusTraining;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getZhiWu() {
		return zhiWu;
	}

	public void setZhiWu(String zhiWu) {
		this.zhiWu = zhiWu;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getTrainingUnit() {
		return trainingUnit;
	}

	public void setTrainingUnit(String trainingUnit) {
		this.trainingUnit = trainingUnit;
	}

	public String getProApplyNo() {
		return proApplyNo;
	}

	public void setProApplyNo(String proApplyNo) {
		this.proApplyNo = proApplyNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getNoteCond() {
		return noteCond;
	}

	public void setNoteCond(String noteCond) {
		this.noteCond = noteCond;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getZhiCheng() {
		return zhiCheng;
	}

	public void setZhiCheng(String zhiCheng) {
		this.zhiCheng = zhiCheng;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getYaoQiuDefen() {
		return yaoQiuDefen;
	}

	public void setYaoQiuDefen(Integer yaoQiuDefen) {
		this.yaoQiuDefen = yaoQiuDefen;
	}

	public int getGuoChengDefen() {
		return guoChengDefen;
	}

	public void setGuoChengDefen(Integer guoChengDefen) {
		this.guoChengDefen = guoChengDefen;
	}

	public int getChengXiaoDefen() {
		return chengXiaoDefen;
	}

	public void setChengXiaoDefen(Integer chengXiaoDefen) {
		this.chengXiaoDefen = chengXiaoDefen;
	}

	public String getYiJian() {
		return yiJian;
	}

	public void setYiJian(String yiJian) {
		this.yiJian = yiJian;
	}

}
