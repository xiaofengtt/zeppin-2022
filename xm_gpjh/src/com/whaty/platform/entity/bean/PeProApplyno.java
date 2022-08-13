package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeProApplyno entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeProApplyno extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFkProgramStatus;
	private EnumConst enumConstByFkProgramType;
	private EnumConst enumConstByFkProvinceCheck;
	private String code;
	private String name;
	private String year;
	private String replyBook;
	private Date deadline;
	private String note;
	private Double feeStandard;
	private Long limit;
	private Long classHour;
	private Set peOtherMaterials = new HashSet(0);
	private Set peTrainees = new HashSet(0);
	private Set prProgramUnits = new HashSet(0);
	private Set peFeeBudgets = new HashSet(0);
	private Set peProApplies = new HashSet(0);
	private Set prProSummaries = new HashSet(0);
	private Set peVotePapers = new HashSet(0);
	private Set peFeeActualBudgets = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeProApplyno() {
	}

	/** full constructor */
	public PeProApplyno(EnumConst enumConstByFkProgramStatus,
			EnumConst enumConstByFkProgramType,
			EnumConst enumConstByFkProvinceCheck, String code, String name,
			String year, String replyBook, Date deadline, String note,
			Double feeStandard, Long limit, Long classHour,
			Set peOtherMaterials, Set peTrainees, Set prProgramUnits,
			Set peFeeBudgets, Set peProApplies, Set prProSummaries,
			Set peVotePapers, Set peFeeActualBudgets) {
		this.enumConstByFkProgramStatus = enumConstByFkProgramStatus;
		this.enumConstByFkProgramType = enumConstByFkProgramType;
		this.enumConstByFkProvinceCheck = enumConstByFkProvinceCheck;
		this.code = code;
		this.name = name;
		this.year = year;
		this.replyBook = replyBook;
		this.deadline = deadline;
		this.note = note;
		this.feeStandard = feeStandard;
		this.limit = limit;
		this.classHour = classHour;
		this.peOtherMaterials = peOtherMaterials;
		this.peTrainees = peTrainees;
		this.prProgramUnits = prProgramUnits;
		this.peFeeBudgets = peFeeBudgets;
		this.peProApplies = peProApplies;
		this.prProSummaries = prProSummaries;
		this.peVotePapers = peVotePapers;
		this.peFeeActualBudgets = peFeeActualBudgets;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFkProgramStatus() {
		return this.enumConstByFkProgramStatus;
	}

	public void setEnumConstByFkProgramStatus(
			EnumConst enumConstByFkProgramStatus) {
		this.enumConstByFkProgramStatus = enumConstByFkProgramStatus;
	}

	public EnumConst getEnumConstByFkProgramType() {
		return this.enumConstByFkProgramType;
	}

	public void setEnumConstByFkProgramType(EnumConst enumConstByFkProgramType) {
		this.enumConstByFkProgramType = enumConstByFkProgramType;
	}

	public EnumConst getEnumConstByFkProvinceCheck() {
		return this.enumConstByFkProvinceCheck;
	}

	public void setEnumConstByFkProvinceCheck(
			EnumConst enumConstByFkProvinceCheck) {
		this.enumConstByFkProvinceCheck = enumConstByFkProvinceCheck;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getReplyBook() {
		return this.replyBook;
	}

	public void setReplyBook(String replyBook) {
		this.replyBook = replyBook;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getFeeStandard() {
		return this.feeStandard;
	}

	public void setFeeStandard(Double feeStandard) {
		this.feeStandard = feeStandard;
	}

	public Long getLimit() {
		return this.limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getClassHour() {
		return this.classHour;
	}

	public void setClassHour(Long classHour) {
		this.classHour = classHour;
	}

	public Set getPeOtherMaterials() {
		return this.peOtherMaterials;
	}

	public void setPeOtherMaterials(Set peOtherMaterials) {
		this.peOtherMaterials = peOtherMaterials;
	}

	public Set getPeTrainees() {
		return this.peTrainees;
	}

	public void setPeTrainees(Set peTrainees) {
		this.peTrainees = peTrainees;
	}

	public Set getPrProgramUnits() {
		return this.prProgramUnits;
	}

	public void setPrProgramUnits(Set prProgramUnits) {
		this.prProgramUnits = prProgramUnits;
	}

	public Set getPeFeeBudgets() {
		return this.peFeeBudgets;
	}

	public void setPeFeeBudgets(Set peFeeBudgets) {
		this.peFeeBudgets = peFeeBudgets;
	}

	public Set getPeProApplies() {
		return this.peProApplies;
	}

	public void setPeProApplies(Set peProApplies) {
		this.peProApplies = peProApplies;
	}

	public Set getPrProSummaries() {
		return this.prProSummaries;
	}

	public void setPrProSummaries(Set prProSummaries) {
		this.prProSummaries = prProSummaries;
	}

	public Set getPeVotePapers() {
		return this.peVotePapers;
	}

	public void setPeVotePapers(Set peVotePapers) {
		this.peVotePapers = peVotePapers;
	}

	public Set getPeFeeActualBudgets() {
		return this.peFeeActualBudgets;
	}

	public void setPeFeeActualBudgets(Set peFeeActualBudgets) {
		this.peFeeActualBudgets = peFeeActualBudgets;
	}

}