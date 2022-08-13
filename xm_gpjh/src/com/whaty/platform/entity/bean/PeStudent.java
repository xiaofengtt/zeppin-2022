package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeStudent extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PrStudentInfo prStudentInfo;
	private PeMajor peMajor;
	private PeFeeLevel peFeeLevel;
	private PeRecStudent peRecStudent;
	private PeGrade peGrade;
	private PeSite peSite;
	private PeEdutype peEdutype;
	private String name;
	private String trueName;
	private String fkSsoUserId;
	private String regNo;
	private String kaoshenghao;
	private Date recruitDate;
	private Double feeBalance;
	private Double feeInactive;
	private String flagStudentStatus;
	private String flagAdvanced;
	private String flagDisobey;
	private String disobeyNote;
	private String scoreUniteEnglishA;
	private String scoreUniteEnglishB;
	private String scoreUniteComputer;
	private Double scoreDegreeEnglish;
	private String degreeEnglishType;
	private String graduationCertificateNo;
	private Date graduationDate;
	private String leaveDate;
	private String degreeCertificateNo;
	private Date degreeDate;
	private String flagMajorType;
	private String scoreUniteEnglishC;
	private String scoreUniteYuwen;
	private String scoreUniteShuxue;
	private Set prFeeDetails = new HashSet(0);
	private Set prStuChangeEdutypes = new HashSet(0);
	private Set prExamStuMaincourses = new HashSet(0);
	private Set prStuChangeSchools = new HashSet(0);
	private Set prStuChangeSites = new HashSet(0);
	private Set prStudentHistories = new HashSet(0);
	private Set prTchStuElectives = new HashSet(0);
	private Set prTchStuPapers = new HashSet(0);
	private Set prStuChangeMajors = new HashSet(0);
	private Set homeworkHistories = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeStudent() {
	}

	/** minimal constructor */
	public PeStudent(String name, String trueName) {
		this.name = name;
		this.trueName = trueName;
	}

	/** full constructor */
	public PeStudent(PrStudentInfo prStudentInfo, PeMajor peMajor,
			PeFeeLevel peFeeLevel, PeRecStudent peRecStudent, PeGrade peGrade,
			PeSite peSite, PeEdutype peEdutype, String name, String trueName,
			String fkSsoUserId, String regNo, String kaoshenghao,
			Date recruitDate, Double feeBalance, Double feeInactive,
			String flagStudentStatus, String flagAdvanced, String flagDisobey,
			String disobeyNote, String scoreUniteEnglishA,
			String scoreUniteEnglishB, String scoreUniteComputer,
			Double scoreDegreeEnglish, String degreeEnglishType,
			String graduationCertificateNo, Date graduationDate,
			String leaveDate, String degreeCertificateNo, Date degreeDate,
			String flagMajorType, String scoreUniteEnglishC,
			String scoreUniteYuwen, String scoreUniteShuxue, Set prFeeDetails,
			Set prStuChangeEdutypes, Set prExamStuMaincourses,
			Set prStuChangeSchools, Set prStuChangeSites,
			Set prStudentHistories, Set prTchStuElectives, Set prTchStuPapers,
			Set prStuChangeMajors, Set homeworkHistories) {
		this.prStudentInfo = prStudentInfo;
		this.peMajor = peMajor;
		this.peFeeLevel = peFeeLevel;
		this.peRecStudent = peRecStudent;
		this.peGrade = peGrade;
		this.peSite = peSite;
		this.peEdutype = peEdutype;
		this.name = name;
		this.trueName = trueName;
		this.fkSsoUserId = fkSsoUserId;
		this.regNo = regNo;
		this.kaoshenghao = kaoshenghao;
		this.recruitDate = recruitDate;
		this.feeBalance = feeBalance;
		this.feeInactive = feeInactive;
		this.flagStudentStatus = flagStudentStatus;
		this.flagAdvanced = flagAdvanced;
		this.flagDisobey = flagDisobey;
		this.disobeyNote = disobeyNote;
		this.scoreUniteEnglishA = scoreUniteEnglishA;
		this.scoreUniteEnglishB = scoreUniteEnglishB;
		this.scoreUniteComputer = scoreUniteComputer;
		this.scoreDegreeEnglish = scoreDegreeEnglish;
		this.degreeEnglishType = degreeEnglishType;
		this.graduationCertificateNo = graduationCertificateNo;
		this.graduationDate = graduationDate;
		this.leaveDate = leaveDate;
		this.degreeCertificateNo = degreeCertificateNo;
		this.degreeDate = degreeDate;
		this.flagMajorType = flagMajorType;
		this.scoreUniteEnglishC = scoreUniteEnglishC;
		this.scoreUniteYuwen = scoreUniteYuwen;
		this.scoreUniteShuxue = scoreUniteShuxue;
		this.prFeeDetails = prFeeDetails;
		this.prStuChangeEdutypes = prStuChangeEdutypes;
		this.prExamStuMaincourses = prExamStuMaincourses;
		this.prStuChangeSchools = prStuChangeSchools;
		this.prStuChangeSites = prStuChangeSites;
		this.prStudentHistories = prStudentHistories;
		this.prTchStuElectives = prTchStuElectives;
		this.prTchStuPapers = prTchStuPapers;
		this.prStuChangeMajors = prStuChangeMajors;
		this.homeworkHistories = homeworkHistories;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrStudentInfo getPrStudentInfo() {
		return this.prStudentInfo;
	}

	public void setPrStudentInfo(PrStudentInfo prStudentInfo) {
		this.prStudentInfo = prStudentInfo;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeFeeLevel getPeFeeLevel() {
		return this.peFeeLevel;
	}

	public void setPeFeeLevel(PeFeeLevel peFeeLevel) {
		this.peFeeLevel = peFeeLevel;
	}

	public PeRecStudent getPeRecStudent() {
		return this.peRecStudent;
	}

	public void setPeRecStudent(PeRecStudent peRecStudent) {
		this.peRecStudent = peRecStudent;
	}

	public PeGrade getPeGrade() {
		return this.peGrade;
	}

	public void setPeGrade(PeGrade peGrade) {
		this.peGrade = peGrade;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public PeEdutype getPeEdutype() {
		return this.peEdutype;
	}

	public void setPeEdutype(PeEdutype peEdutype) {
		this.peEdutype = peEdutype;
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

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getKaoshenghao() {
		return this.kaoshenghao;
	}

	public void setKaoshenghao(String kaoshenghao) {
		this.kaoshenghao = kaoshenghao;
	}

	public Date getRecruitDate() {
		return this.recruitDate;
	}

	public void setRecruitDate(Date recruitDate) {
		this.recruitDate = recruitDate;
	}

	public Double getFeeBalance() {
		return this.feeBalance;
	}

	public void setFeeBalance(Double feeBalance) {
		this.feeBalance = feeBalance;
	}

	public Double getFeeInactive() {
		return this.feeInactive;
	}

	public void setFeeInactive(Double feeInactive) {
		this.feeInactive = feeInactive;
	}

	public String getFlagStudentStatus() {
		return this.flagStudentStatus;
	}

	public void setFlagStudentStatus(String flagStudentStatus) {
		this.flagStudentStatus = flagStudentStatus;
	}

	public String getFlagAdvanced() {
		return this.flagAdvanced;
	}

	public void setFlagAdvanced(String flagAdvanced) {
		this.flagAdvanced = flagAdvanced;
	}

	public String getFlagDisobey() {
		return this.flagDisobey;
	}

	public void setFlagDisobey(String flagDisobey) {
		this.flagDisobey = flagDisobey;
	}

	public String getDisobeyNote() {
		return this.disobeyNote;
	}

	public void setDisobeyNote(String disobeyNote) {
		this.disobeyNote = disobeyNote;
	}

	public String getScoreUniteEnglishA() {
		return this.scoreUniteEnglishA;
	}

	public void setScoreUniteEnglishA(String scoreUniteEnglishA) {
		this.scoreUniteEnglishA = scoreUniteEnglishA;
	}

	public String getScoreUniteEnglishB() {
		return this.scoreUniteEnglishB;
	}

	public void setScoreUniteEnglishB(String scoreUniteEnglishB) {
		this.scoreUniteEnglishB = scoreUniteEnglishB;
	}

	public String getScoreUniteComputer() {
		return this.scoreUniteComputer;
	}

	public void setScoreUniteComputer(String scoreUniteComputer) {
		this.scoreUniteComputer = scoreUniteComputer;
	}

	public Double getScoreDegreeEnglish() {
		return this.scoreDegreeEnglish;
	}

	public void setScoreDegreeEnglish(Double scoreDegreeEnglish) {
		this.scoreDegreeEnglish = scoreDegreeEnglish;
	}

	public String getDegreeEnglishType() {
		return this.degreeEnglishType;
	}

	public void setDegreeEnglishType(String degreeEnglishType) {
		this.degreeEnglishType = degreeEnglishType;
	}

	public String getGraduationCertificateNo() {
		return this.graduationCertificateNo;
	}

	public void setGraduationCertificateNo(String graduationCertificateNo) {
		this.graduationCertificateNo = graduationCertificateNo;
	}

	public Date getGraduationDate() {
		return this.graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getDegreeCertificateNo() {
		return this.degreeCertificateNo;
	}

	public void setDegreeCertificateNo(String degreeCertificateNo) {
		this.degreeCertificateNo = degreeCertificateNo;
	}

	public Date getDegreeDate() {
		return this.degreeDate;
	}

	public void setDegreeDate(Date degreeDate) {
		this.degreeDate = degreeDate;
	}

	public String getFlagMajorType() {
		return this.flagMajorType;
	}

	public void setFlagMajorType(String flagMajorType) {
		this.flagMajorType = flagMajorType;
	}

	public String getScoreUniteEnglishC() {
		return this.scoreUniteEnglishC;
	}

	public void setScoreUniteEnglishC(String scoreUniteEnglishC) {
		this.scoreUniteEnglishC = scoreUniteEnglishC;
	}

	public String getScoreUniteYuwen() {
		return this.scoreUniteYuwen;
	}

	public void setScoreUniteYuwen(String scoreUniteYuwen) {
		this.scoreUniteYuwen = scoreUniteYuwen;
	}

	public String getScoreUniteShuxue() {
		return this.scoreUniteShuxue;
	}

	public void setScoreUniteShuxue(String scoreUniteShuxue) {
		this.scoreUniteShuxue = scoreUniteShuxue;
	}

	public Set getPrFeeDetails() {
		return this.prFeeDetails;
	}

	public void setPrFeeDetails(Set prFeeDetails) {
		this.prFeeDetails = prFeeDetails;
	}

	public Set getPrStuChangeEdutypes() {
		return this.prStuChangeEdutypes;
	}

	public void setPrStuChangeEdutypes(Set prStuChangeEdutypes) {
		this.prStuChangeEdutypes = prStuChangeEdutypes;
	}

	public Set getPrExamStuMaincourses() {
		return this.prExamStuMaincourses;
	}

	public void setPrExamStuMaincourses(Set prExamStuMaincourses) {
		this.prExamStuMaincourses = prExamStuMaincourses;
	}

	public Set getPrStuChangeSchools() {
		return this.prStuChangeSchools;
	}

	public void setPrStuChangeSchools(Set prStuChangeSchools) {
		this.prStuChangeSchools = prStuChangeSchools;
	}

	public Set getPrStuChangeSites() {
		return this.prStuChangeSites;
	}

	public void setPrStuChangeSites(Set prStuChangeSites) {
		this.prStuChangeSites = prStuChangeSites;
	}

	public Set getPrStudentHistories() {
		return this.prStudentHistories;
	}

	public void setPrStudentHistories(Set prStudentHistories) {
		this.prStudentHistories = prStudentHistories;
	}

	public Set getPrTchStuElectives() {
		return this.prTchStuElectives;
	}

	public void setPrTchStuElectives(Set prTchStuElectives) {
		this.prTchStuElectives = prTchStuElectives;
	}

	public Set getPrTchStuPapers() {
		return this.prTchStuPapers;
	}

	public void setPrTchStuPapers(Set prTchStuPapers) {
		this.prTchStuPapers = prTchStuPapers;
	}

	public Set getPrStuChangeMajors() {
		return this.prStuChangeMajors;
	}

	public void setPrStuChangeMajors(Set prStuChangeMajors) {
		this.prStuChangeMajors = prStuChangeMajors;
	}

	public Set getHomeworkHistories() {
		return this.homeworkHistories;
	}

	public void setHomeworkHistories(Set homeworkHistories) {
		this.homeworkHistories = homeworkHistories;
	}

}