package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * InvigilationTeacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "invigilation_teacher")
public class InvigilationTeacher implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4165280627103865963L;
	private Integer id;
	private String name;
	private String pinyin;
	private String idcard;
	private String mobile;
	private Short sex;
	private Ethnic ethnic;
	private Resource photo;
	private String major;
	// private InvigilationTeacherType type;
	// 考务组，研究生，教工,本科，非师大人员
	private Short type;
	private String organization;
	private String inschooltime;
	private Short isChiefExaminer;
	private Short isMixedExaminer;
	private Integer intgral;
	private String specialty;
	private Short status;
	private String reason;
	// 为0时，意为教师自己注册，否则为管理员的id
	private Integer creator;
	private Timestamp createtime;
	private Short checkStatus;
	private Timestamp checktime;
	private Integer checker;
	private String checkReason;
	// 监考校区：1温泉校区、2昆仑校区、3文光校区（多选）
	private String invigilateCampus;
	// 监考类型：1纸笔、2无纸化、3测试（多选）
	private String invigilateType;
	private Integer invigilateCount;
	private String jobDuty;
	private String studyMajor;
	private String studyGrade;
	private Integer studyLength;
	private String remark;

	private String bankCard;
	private String openID;
	private String sid;

	private String password;

	// 解禁时间
	private Timestamp releaseTime;
	// 0永久禁用，1未禁用，2禁用半年
	private Short disableType;
	// 是否被禁用过
	private Short isDisable;
	// 身份证正面照
	private Resource idCardPhoto;
	
	//20190417新增5个属性字段
	private String formation;//在编教职工、同职级教职工、长期聘用教职工、临时聘用教职工
	private String occupation;//职业
	private String bankOrg;//开户行地区
	private String bankName;//开户行名称
	private String email;//电子信箱

	// Constructors

	/** default constructor */
	public InvigilationTeacher() {
	}

	/** minimal constructor */
	public InvigilationTeacher(Timestamp createtime, Short status) {
		this.createtime = createtime;
		this.status = status;
	}

	public InvigilationTeacher(Integer id, String name, String pinyin, String idcard, String mobile, Short sex,
			Ethnic ethnic, Resource photo, String major, Short type, String organization, String inschooltime,
			Short isChiefExaminer, Short isMixedExaminer, Integer intgral, String specialty, Short status,
			String reason, Integer creator, Timestamp createtime, Short checkStatus, Timestamp checktime,
			Integer checker, String checkReason, String invigilateCampus, String invigilateType,
			Integer invigilateCount, String jobDuty, String studyMajor, String studyGrade, String remark,
			String bankCard, String openID, String sid, String password, Timestamp releaseTime, Short disableType,
			Short isDisable, Resource idCardPhoto,Integer studyLength) {
		super();
		this.id = id;
		this.name = name;
		this.pinyin = pinyin;
		this.idcard = idcard;
		this.mobile = mobile;
		this.sex = sex;
		this.ethnic = ethnic;
		this.photo = photo;
		this.major = major;
		this.type = type;
		this.organization = organization;
		this.inschooltime = inschooltime;
		this.isChiefExaminer = isChiefExaminer;
		this.isMixedExaminer = isMixedExaminer;
		this.intgral = intgral;
		this.specialty = specialty;
		this.status = status;
		this.reason = reason;
		this.creator = creator;
		this.createtime = createtime;
		this.checkStatus = checkStatus;
		this.checktime = checktime;
		this.checker = checker;
		this.checkReason = checkReason;
		this.invigilateCampus = invigilateCampus;
		this.invigilateType = invigilateType;
		this.invigilateCount = invigilateCount;
		this.jobDuty = jobDuty;
		this.studyMajor = studyMajor;
		this.studyGrade = studyGrade;
		this.remark = remark;
		this.bankCard = bankCard;
		this.openID = openID;
		this.sid = sid;
		this.password = password;
		this.releaseTime = releaseTime;
		this.disableType = disableType;
		this.isDisable = isDisable;
		this.idCardPhoto = idCardPhoto;
		this.studyLength = studyLength;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "IDCARD", length = 20)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "MOBILE", length = 12)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "SEX", length = 4)
	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PINYIN", nullable = false, length = 100)
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ETHNIC", nullable = false)
	public Ethnic getEthnic() {
		return ethnic;
	}

	public void setEthnic(Ethnic ethnic) {
		this.ethnic = ethnic;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PHOTO")
	public Resource getPhoto() {
		return photo;
	}

	public void setPhoto(Resource photo) {
		this.photo = photo;
	}

	@Column(name = "MAJOR", nullable = false, length = 20)
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "TYPE", nullable = false, length = 6)
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "ORGANIZATION", nullable = false, length = 30)
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(name = "INSHCOOL_TIME", nullable = false)
	public String getInschooltime() {
		return inschooltime;
	}

	public void setInschooltime(String inschooltime) {
		this.inschooltime = inschooltime;
	}

	@Column(name = "IS_CHIEF_EXAMINER", nullable = false, length = 4)
	public Short getIsChiefExaminer() {
		return isChiefExaminer;
	}

	public void setIsChiefExaminer(Short isChiefExaminer) {
		this.isChiefExaminer = isChiefExaminer;
	}

	@Column(name = "IS_MIXED_EXAMINER", nullable = false, length = 4)
	public Short getIsMixedExaminer() {
		return isMixedExaminer;
	}

	public void setIsMixedExaminer(Short isMixedExaminer) {
		this.isMixedExaminer = isMixedExaminer;
	}

	@Column(name = "INTEGRAL", nullable = false, length = 6)
	public Integer getIntgral() {
		return intgral;
	}

	public void setIntgral(Integer intgral) {
		this.intgral = intgral;
	}

	@Column(name = "SPECIALTY", nullable = false, length = 50)
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Column(name = "REASON")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "CREATOR", nullable = false, length = 11)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CHECK_STATUS", nullable = false, length = 4)
	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Column(name = "CHECK_TIME")
	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	@Column(name = "CHECKER")
	public Integer getChecker() {
		return checker;
	}

	public void setChecker(Integer checker) {
		this.checker = checker;
	}

	@Column(name = "CHECK_REASON")
	public String getCheckReason() {
		return checkReason;
	}

	public void setCheckReason(String checkReason) {
		this.checkReason = checkReason;
	}

	@Column(name = "INVIGILATE_CAMPUS", length = 10)
	public String getInvigilateCampus() {
		return invigilateCampus;
	}

	public void setInvigilateCampus(String invigilateCampus) {
		this.invigilateCampus = invigilateCampus;
	}

	@Column(name = "INVIGILATE_TYPE", length = 10)
	public String getInvigilateType() {
		return invigilateType;
	}

	public void setInvigilateType(String invigilateType) {
		this.invigilateType = invigilateType;
	}

	@Column(name = "INVIGILATE_COUNT", length = 4)
	public Integer getInvigilateCount() {
		return invigilateCount;
	}

	public void setInvigilateCount(Integer invigilateCount) {
		this.invigilateCount = invigilateCount;
	}

	@Column(name = "JOB_DUTY", length = 20)
	public String getJobDuty() {
		return jobDuty;
	}

	public void setJobDuty(String jobDuty) {
		this.jobDuty = jobDuty;
	}

	@Column(name = "STUDY_MAJOR", length = 20)
	public String getStudyMajor() {
		return studyMajor;
	}

	public void setStudyMajor(String studyMajor) {
		this.studyMajor = studyMajor;
	}

	@Column(name = "STUDY_GRADE", length = 20)
	public String getStudyGrade() {
		return studyGrade;
	}

	public void setStudyGrade(String studyGrade) {
		this.studyGrade = studyGrade;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "BANK_CARD", length = 20)
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	@Column(name = "OPENID", length = 64)
	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	@Column(name = "SID", length = 20)
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Column(name = "PASSWORD", length = 20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "RELEASETIME")
	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name = "DISABLETYPE")
	public Short getDisableType() {
		return disableType;
	}

	public void setDisableType(Short disableType) {
		this.disableType = disableType;
	}

	@Column(name = "ISDISABLE")
	public Short getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Short isDisable) {
		this.isDisable = isDisable;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDCARD_PHOTO")
	public Resource getIdCardPhoto() {
		return idCardPhoto;
	}

	public void setIdCardPhoto(Resource idCardPhoto) {
		this.idCardPhoto = idCardPhoto;
	}
	
	
	@Column(name = "STUDY_LENGTH")
	public Integer getStudyLength() {
		return studyLength;
	}

	public void setStudyLength(Integer studyLength) {
		this.studyLength = studyLength;
	}

	@Column(name = "FORMATION")
	public String getFormation() {
		return formation;
	}
	

	public void setFormation(String formation) {
		this.formation = formation;
	}
	
	@Column(name = "OCCUPATION")
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	@Column(name = "BANK_ORG")
	public String getBankOrg() {
		return bankOrg;
	}

	public void setBankOrg(String bankOrg) {
		this.bankOrg = bankOrg;
	}
	
	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}