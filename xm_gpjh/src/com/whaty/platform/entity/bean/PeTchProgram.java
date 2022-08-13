package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchProgram entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchProgram extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeMajor peMajor;
	private PeGrade peGrade;
	private PeEdutype peEdutype;
	private String name;
	private String flagDegreeCandisobey;
	private String flagMajorType;
	private Long graduateMinCredit;
	private Double degreeAvgScore;
	private Double degreePaperScore;
	private Long paperMinCreditHour;
	private Long paperMinSemeser;
	private Long maxElective;
	private Double minSemester;
	private Double maxSemester;
	private String note;
	private String flagUniteA;
	private String flagUniteB;
	private Set peTchProgramGroups = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchProgram() {
	}

	/** minimal constructor */
	public PeTchProgram(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeTchProgram(PeMajor peMajor, PeGrade peGrade, PeEdutype peEdutype,
			String name, String flagDegreeCandisobey, String flagMajorType,
			Long graduateMinCredit, Double degreeAvgScore,
			Double degreePaperScore, Long paperMinCreditHour,
			Long paperMinSemeser, Long maxElective, Double minSemester,
			Double maxSemester, String note, String flagUniteA,
			String flagUniteB, Set peTchProgramGroups) {
		this.peMajor = peMajor;
		this.peGrade = peGrade;
		this.peEdutype = peEdutype;
		this.name = name;
		this.flagDegreeCandisobey = flagDegreeCandisobey;
		this.flagMajorType = flagMajorType;
		this.graduateMinCredit = graduateMinCredit;
		this.degreeAvgScore = degreeAvgScore;
		this.degreePaperScore = degreePaperScore;
		this.paperMinCreditHour = paperMinCreditHour;
		this.paperMinSemeser = paperMinSemeser;
		this.maxElective = maxElective;
		this.minSemester = minSemester;
		this.maxSemester = maxSemester;
		this.note = note;
		this.flagUniteA = flagUniteA;
		this.flagUniteB = flagUniteB;
		this.peTchProgramGroups = peTchProgramGroups;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public PeGrade getPeGrade() {
		return this.peGrade;
	}

	public void setPeGrade(PeGrade peGrade) {
		this.peGrade = peGrade;
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

	public String getFlagDegreeCandisobey() {
		return this.flagDegreeCandisobey;
	}

	public void setFlagDegreeCandisobey(String flagDegreeCandisobey) {
		this.flagDegreeCandisobey = flagDegreeCandisobey;
	}

	public String getFlagMajorType() {
		return this.flagMajorType;
	}

	public void setFlagMajorType(String flagMajorType) {
		this.flagMajorType = flagMajorType;
	}

	public Long getGraduateMinCredit() {
		return this.graduateMinCredit;
	}

	public void setGraduateMinCredit(Long graduateMinCredit) {
		this.graduateMinCredit = graduateMinCredit;
	}

	public Double getDegreeAvgScore() {
		return this.degreeAvgScore;
	}

	public void setDegreeAvgScore(Double degreeAvgScore) {
		this.degreeAvgScore = degreeAvgScore;
	}

	public Double getDegreePaperScore() {
		return this.degreePaperScore;
	}

	public void setDegreePaperScore(Double degreePaperScore) {
		this.degreePaperScore = degreePaperScore;
	}

	public Long getPaperMinCreditHour() {
		return this.paperMinCreditHour;
	}

	public void setPaperMinCreditHour(Long paperMinCreditHour) {
		this.paperMinCreditHour = paperMinCreditHour;
	}

	public Long getPaperMinSemeser() {
		return this.paperMinSemeser;
	}

	public void setPaperMinSemeser(Long paperMinSemeser) {
		this.paperMinSemeser = paperMinSemeser;
	}

	public Long getMaxElective() {
		return this.maxElective;
	}

	public void setMaxElective(Long maxElective) {
		this.maxElective = maxElective;
	}

	public Double getMinSemester() {
		return this.minSemester;
	}

	public void setMinSemester(Double minSemester) {
		this.minSemester = minSemester;
	}

	public Double getMaxSemester() {
		return this.maxSemester;
	}

	public void setMaxSemester(Double maxSemester) {
		this.maxSemester = maxSemester;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFlagUniteA() {
		return this.flagUniteA;
	}

	public void setFlagUniteA(String flagUniteA) {
		this.flagUniteA = flagUniteA;
	}

	public String getFlagUniteB() {
		return this.flagUniteB;
	}

	public void setFlagUniteB(String flagUniteB) {
		this.flagUniteB = flagUniteB;
	}

	public Set getPeTchProgramGroups() {
		return this.peTchProgramGroups;
	}

	public void setPeTchProgramGroups(Set peTchProgramGroups) {
		this.peTchProgramGroups = peTchProgramGroups;
	}

}