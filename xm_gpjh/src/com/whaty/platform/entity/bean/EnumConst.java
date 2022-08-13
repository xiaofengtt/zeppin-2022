package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EnumConst entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EnumConst extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String namespace;
	private String isDefault;
	private Date createDate;
	private String note;
	private Set peVotePapersForFlagIsvalid = new HashSet(0);
	private Set peTraineesForFkModifyChecked = new HashSet(0);
	private Set peProAppliesForFkCheckFirst = new HashSet(0);
	private Set peVotePapersForFlagType = new HashSet(0);
	private Set peVotePapersForFlagCanSuggest = new HashSet(0);
	private Set peUnitsForFkUnitType = new HashSet(0);
	private Set peProApplynosForFkProgramStatus = new HashSet(0);
	private Set peVotePapersForFlagLimitDiffip = new HashSet(0);
	private Set peManagersForFkGender = new HashSet(0);
	private Set peTraineesForFkGraduted = new HashSet(0);
	private Set peValuaExpertsForFkGender = new HashSet(0);
	private Set prJobUnitsForFkJobStatus = new HashSet(0);
	private Set peProApplynosForFkProvinceCheck = new HashSet(0);
	private Set peUnitsForFlagIsvalid = new HashSet(0);
	private Set prJobUnitsForFkJobCheck = new HashSet(0);
	private Set peManagersForFkStatus = new HashSet(0);
	private Set prProExpertsForFkCheckFinal = new HashSet(0);
	private Set peProApplynosForFkProgramType = new HashSet(0);
	private Set peTrainExpertsForFkGender = new HashSet(0);
	private Set peTraineesForFkGender = new HashSet(0);
	private Set prProExpertsForFkCheckFirst = new HashSet(0);
	private Set peTrainExpertsForFkStatus = new HashSet(0);
	private Set ssoUsersForFlagBak = new HashSet(0);
	private Set peTraineesForFkStatusTraining = new HashSet(0);
	private Set ssoUsersForFlagIsvalid = new HashSet(0);
	private Set peVotePapersForFlagLimitDiffsession = new HashSet(0);
	private Set peProAppliesForFkCheckFinal = new HashSet(0);
	private Set peFinancials = new HashSet(0);
	private Set peTraineesForFkChecked = new HashSet(0);
	private Set peProAppliesForFkCheckResultProvince = new HashSet(0);
	private Set pePriRolesForFlagRoleType = new HashSet(0);
	private Set peTraineesForFkSsoUserId = new HashSet(0);
	private Set peManagersForFlagIsvalid = new HashSet(0);
	private Set peProAppliesForFkCheckNational = new HashSet(0);
	private Set pePriRolesForFlagBak = new HashSet(0);
	private Set peValuaExpertsForFkStatus = new HashSet(0);
	private Set peVotePapersForFlagViewSuggest = new HashSet(0);

	// Constructors

	/** default constructor */
	public EnumConst() {
	}

	/** minimal constructor */
	public EnumConst(String name) {
		this.name = name;
	}

	/** full constructor */
	public EnumConst(String name, String code, String namespace,
			String isDefault, Date createDate, String note,
			Set peVotePapersForFlagIsvalid, Set peTraineesForFkModifyChecked,
			Set peProAppliesForFkCheckFirst, Set peVotePapersForFlagType,
			Set peVotePapersForFlagCanSuggest, Set peUnitsForFkUnitType,
			Set peProApplynosForFkProgramStatus,
			Set peVotePapersForFlagLimitDiffip, Set peManagersForFkGender,
			Set peTraineesForFkGraduted, Set peValuaExpertsForFkGender,
			Set prJobUnitsForFkJobStatus, Set peProApplynosForFkProvinceCheck,
			Set peUnitsForFlagIsvalid, Set prJobUnitsForFkJobCheck,
			Set peManagersForFkStatus, Set prProExpertsForFkCheckFinal,
			Set peProApplynosForFkProgramType, Set peTrainExpertsForFkGender,
			Set peTraineesForFkGender, Set prProExpertsForFkCheckFirst,
			Set peTrainExpertsForFkStatus, Set ssoUsersForFlagBak,
			Set peTraineesForFkStatusTraining, Set ssoUsersForFlagIsvalid,
			Set peVotePapersForFlagLimitDiffsession,
			Set peProAppliesForFkCheckFinal, Set peFinancials,
			Set peTraineesForFkChecked,
			Set peProAppliesForFkCheckResultProvince,
			Set pePriRolesForFlagRoleType, Set peTraineesForFkSsoUserId,
			Set peManagersForFlagIsvalid, Set peProAppliesForFkCheckNational,
			Set pePriRolesForFlagBak, Set peValuaExpertsForFkStatus,
			Set peVotePapersForFlagViewSuggest) {
		this.name = name;
		this.code = code;
		this.namespace = namespace;
		this.isDefault = isDefault;
		this.createDate = createDate;
		this.note = note;
		this.peVotePapersForFlagIsvalid = peVotePapersForFlagIsvalid;
		this.peTraineesForFkModifyChecked = peTraineesForFkModifyChecked;
		this.peProAppliesForFkCheckFirst = peProAppliesForFkCheckFirst;
		this.peVotePapersForFlagType = peVotePapersForFlagType;
		this.peVotePapersForFlagCanSuggest = peVotePapersForFlagCanSuggest;
		this.peUnitsForFkUnitType = peUnitsForFkUnitType;
		this.peProApplynosForFkProgramStatus = peProApplynosForFkProgramStatus;
		this.peVotePapersForFlagLimitDiffip = peVotePapersForFlagLimitDiffip;
		this.peManagersForFkGender = peManagersForFkGender;
		this.peTraineesForFkGraduted = peTraineesForFkGraduted;
		this.peValuaExpertsForFkGender = peValuaExpertsForFkGender;
		this.prJobUnitsForFkJobStatus = prJobUnitsForFkJobStatus;
		this.peProApplynosForFkProvinceCheck = peProApplynosForFkProvinceCheck;
		this.peUnitsForFlagIsvalid = peUnitsForFlagIsvalid;
		this.prJobUnitsForFkJobCheck = prJobUnitsForFkJobCheck;
		this.peManagersForFkStatus = peManagersForFkStatus;
		this.prProExpertsForFkCheckFinal = prProExpertsForFkCheckFinal;
		this.peProApplynosForFkProgramType = peProApplynosForFkProgramType;
		this.peTrainExpertsForFkGender = peTrainExpertsForFkGender;
		this.peTraineesForFkGender = peTraineesForFkGender;
		this.prProExpertsForFkCheckFirst = prProExpertsForFkCheckFirst;
		this.peTrainExpertsForFkStatus = peTrainExpertsForFkStatus;
		this.ssoUsersForFlagBak = ssoUsersForFlagBak;
		this.peTraineesForFkStatusTraining = peTraineesForFkStatusTraining;
		this.ssoUsersForFlagIsvalid = ssoUsersForFlagIsvalid;
		this.peVotePapersForFlagLimitDiffsession = peVotePapersForFlagLimitDiffsession;
		this.peProAppliesForFkCheckFinal = peProAppliesForFkCheckFinal;
		this.peFinancials = peFinancials;
		this.peTraineesForFkChecked = peTraineesForFkChecked;
		this.peProAppliesForFkCheckResultProvince = peProAppliesForFkCheckResultProvince;
		this.pePriRolesForFlagRoleType = pePriRolesForFlagRoleType;
		this.peTraineesForFkSsoUserId = peTraineesForFkSsoUserId;
		this.peManagersForFlagIsvalid = peManagersForFlagIsvalid;
		this.peProAppliesForFkCheckNational = peProAppliesForFkCheckNational;
		this.pePriRolesForFlagBak = pePriRolesForFlagBak;
		this.peValuaExpertsForFkStatus = peValuaExpertsForFkStatus;
		this.peVotePapersForFlagViewSuggest = peVotePapersForFlagViewSuggest;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeVotePapersForFlagIsvalid() {
		return this.peVotePapersForFlagIsvalid;
	}

	public void setPeVotePapersForFlagIsvalid(Set peVotePapersForFlagIsvalid) {
		this.peVotePapersForFlagIsvalid = peVotePapersForFlagIsvalid;
	}

	public Set getPeTraineesForFkModifyChecked() {
		return this.peTraineesForFkModifyChecked;
	}

	public void setPeTraineesForFkModifyChecked(Set peTraineesForFkModifyChecked) {
		this.peTraineesForFkModifyChecked = peTraineesForFkModifyChecked;
	}

	public Set getPeProAppliesForFkCheckFirst() {
		return this.peProAppliesForFkCheckFirst;
	}

	public void setPeProAppliesForFkCheckFirst(Set peProAppliesForFkCheckFirst) {
		this.peProAppliesForFkCheckFirst = peProAppliesForFkCheckFirst;
	}

	public Set getPeVotePapersForFlagType() {
		return this.peVotePapersForFlagType;
	}

	public void setPeVotePapersForFlagType(Set peVotePapersForFlagType) {
		this.peVotePapersForFlagType = peVotePapersForFlagType;
	}

	public Set getPeVotePapersForFlagCanSuggest() {
		return this.peVotePapersForFlagCanSuggest;
	}

	public void setPeVotePapersForFlagCanSuggest(
			Set peVotePapersForFlagCanSuggest) {
		this.peVotePapersForFlagCanSuggest = peVotePapersForFlagCanSuggest;
	}

	public Set getPeUnitsForFkUnitType() {
		return this.peUnitsForFkUnitType;
	}

	public void setPeUnitsForFkUnitType(Set peUnitsForFkUnitType) {
		this.peUnitsForFkUnitType = peUnitsForFkUnitType;
	}

	public Set getPeProApplynosForFkProgramStatus() {
		return this.peProApplynosForFkProgramStatus;
	}

	public void setPeProApplynosForFkProgramStatus(
			Set peProApplynosForFkProgramStatus) {
		this.peProApplynosForFkProgramStatus = peProApplynosForFkProgramStatus;
	}

	public Set getPeVotePapersForFlagLimitDiffip() {
		return this.peVotePapersForFlagLimitDiffip;
	}

	public void setPeVotePapersForFlagLimitDiffip(
			Set peVotePapersForFlagLimitDiffip) {
		this.peVotePapersForFlagLimitDiffip = peVotePapersForFlagLimitDiffip;
	}

	public Set getPeManagersForFkGender() {
		return this.peManagersForFkGender;
	}

	public void setPeManagersForFkGender(Set peManagersForFkGender) {
		this.peManagersForFkGender = peManagersForFkGender;
	}

	public Set getPeTraineesForFkGraduted() {
		return this.peTraineesForFkGraduted;
	}

	public void setPeTraineesForFkGraduted(Set peTraineesForFkGraduted) {
		this.peTraineesForFkGraduted = peTraineesForFkGraduted;
	}

	public Set getPeValuaExpertsForFkGender() {
		return this.peValuaExpertsForFkGender;
	}

	public void setPeValuaExpertsForFkGender(Set peValuaExpertsForFkGender) {
		this.peValuaExpertsForFkGender = peValuaExpertsForFkGender;
	}

	public Set getPrJobUnitsForFkJobStatus() {
		return this.prJobUnitsForFkJobStatus;
	}

	public void setPrJobUnitsForFkJobStatus(Set prJobUnitsForFkJobStatus) {
		this.prJobUnitsForFkJobStatus = prJobUnitsForFkJobStatus;
	}

	public Set getPeProApplynosForFkProvinceCheck() {
		return this.peProApplynosForFkProvinceCheck;
	}

	public void setPeProApplynosForFkProvinceCheck(
			Set peProApplynosForFkProvinceCheck) {
		this.peProApplynosForFkProvinceCheck = peProApplynosForFkProvinceCheck;
	}

	public Set getPeUnitsForFlagIsvalid() {
		return this.peUnitsForFlagIsvalid;
	}

	public void setPeUnitsForFlagIsvalid(Set peUnitsForFlagIsvalid) {
		this.peUnitsForFlagIsvalid = peUnitsForFlagIsvalid;
	}

	public Set getPrJobUnitsForFkJobCheck() {
		return this.prJobUnitsForFkJobCheck;
	}

	public void setPrJobUnitsForFkJobCheck(Set prJobUnitsForFkJobCheck) {
		this.prJobUnitsForFkJobCheck = prJobUnitsForFkJobCheck;
	}

	public Set getPeManagersForFkStatus() {
		return this.peManagersForFkStatus;
	}

	public void setPeManagersForFkStatus(Set peManagersForFkStatus) {
		this.peManagersForFkStatus = peManagersForFkStatus;
	}

	public Set getPrProExpertsForFkCheckFinal() {
		return this.prProExpertsForFkCheckFinal;
	}

	public void setPrProExpertsForFkCheckFinal(Set prProExpertsForFkCheckFinal) {
		this.prProExpertsForFkCheckFinal = prProExpertsForFkCheckFinal;
	}

	public Set getPeProApplynosForFkProgramType() {
		return this.peProApplynosForFkProgramType;
	}

	public void setPeProApplynosForFkProgramType(
			Set peProApplynosForFkProgramType) {
		this.peProApplynosForFkProgramType = peProApplynosForFkProgramType;
	}

	public Set getPeTrainExpertsForFkGender() {
		return this.peTrainExpertsForFkGender;
	}

	public void setPeTrainExpertsForFkGender(Set peTrainExpertsForFkGender) {
		this.peTrainExpertsForFkGender = peTrainExpertsForFkGender;
	}

	public Set getPeTraineesForFkGender() {
		return this.peTraineesForFkGender;
	}

	public void setPeTraineesForFkGender(Set peTraineesForFkGender) {
		this.peTraineesForFkGender = peTraineesForFkGender;
	}

	public Set getPrProExpertsForFkCheckFirst() {
		return this.prProExpertsForFkCheckFirst;
	}

	public void setPrProExpertsForFkCheckFirst(Set prProExpertsForFkCheckFirst) {
		this.prProExpertsForFkCheckFirst = prProExpertsForFkCheckFirst;
	}

	public Set getPeTrainExpertsForFkStatus() {
		return this.peTrainExpertsForFkStatus;
	}

	public void setPeTrainExpertsForFkStatus(Set peTrainExpertsForFkStatus) {
		this.peTrainExpertsForFkStatus = peTrainExpertsForFkStatus;
	}

	public Set getSsoUsersForFlagBak() {
		return this.ssoUsersForFlagBak;
	}

	public void setSsoUsersForFlagBak(Set ssoUsersForFlagBak) {
		this.ssoUsersForFlagBak = ssoUsersForFlagBak;
	}

	public Set getPeTraineesForFkStatusTraining() {
		return this.peTraineesForFkStatusTraining;
	}

	public void setPeTraineesForFkStatusTraining(
			Set peTraineesForFkStatusTraining) {
		this.peTraineesForFkStatusTraining = peTraineesForFkStatusTraining;
	}

	public Set getSsoUsersForFlagIsvalid() {
		return this.ssoUsersForFlagIsvalid;
	}

	public void setSsoUsersForFlagIsvalid(Set ssoUsersForFlagIsvalid) {
		this.ssoUsersForFlagIsvalid = ssoUsersForFlagIsvalid;
	}

	public Set getPeVotePapersForFlagLimitDiffsession() {
		return this.peVotePapersForFlagLimitDiffsession;
	}

	public void setPeVotePapersForFlagLimitDiffsession(
			Set peVotePapersForFlagLimitDiffsession) {
		this.peVotePapersForFlagLimitDiffsession = peVotePapersForFlagLimitDiffsession;
	}

	public Set getPeProAppliesForFkCheckFinal() {
		return this.peProAppliesForFkCheckFinal;
	}

	public void setPeProAppliesForFkCheckFinal(Set peProAppliesForFkCheckFinal) {
		this.peProAppliesForFkCheckFinal = peProAppliesForFkCheckFinal;
	}

	public Set getPeFinancials() {
		return this.peFinancials;
	}

	public void setPeFinancials(Set peFinancials) {
		this.peFinancials = peFinancials;
	}

	public Set getPeTraineesForFkChecked() {
		return this.peTraineesForFkChecked;
	}

	public void setPeTraineesForFkChecked(Set peTraineesForFkChecked) {
		this.peTraineesForFkChecked = peTraineesForFkChecked;
	}

	public Set getPeProAppliesForFkCheckResultProvince() {
		return this.peProAppliesForFkCheckResultProvince;
	}

	public void setPeProAppliesForFkCheckResultProvince(
			Set peProAppliesForFkCheckResultProvince) {
		this.peProAppliesForFkCheckResultProvince = peProAppliesForFkCheckResultProvince;
	}

	public Set getPePriRolesForFlagRoleType() {
		return this.pePriRolesForFlagRoleType;
	}

	public void setPePriRolesForFlagRoleType(Set pePriRolesForFlagRoleType) {
		this.pePriRolesForFlagRoleType = pePriRolesForFlagRoleType;
	}

	public Set getPeTraineesForFkSsoUserId() {
		return this.peTraineesForFkSsoUserId;
	}

	public void setPeTraineesForFkSsoUserId(Set peTraineesForFkSsoUserId) {
		this.peTraineesForFkSsoUserId = peTraineesForFkSsoUserId;
	}

	public Set getPeManagersForFlagIsvalid() {
		return this.peManagersForFlagIsvalid;
	}

	public void setPeManagersForFlagIsvalid(Set peManagersForFlagIsvalid) {
		this.peManagersForFlagIsvalid = peManagersForFlagIsvalid;
	}

	public Set getPeProAppliesForFkCheckNational() {
		return this.peProAppliesForFkCheckNational;
	}

	public void setPeProAppliesForFkCheckNational(
			Set peProAppliesForFkCheckNational) {
		this.peProAppliesForFkCheckNational = peProAppliesForFkCheckNational;
	}

	public Set getPePriRolesForFlagBak() {
		return this.pePriRolesForFlagBak;
	}

	public void setPePriRolesForFlagBak(Set pePriRolesForFlagBak) {
		this.pePriRolesForFlagBak = pePriRolesForFlagBak;
	}

	public Set getPeValuaExpertsForFkStatus() {
		return this.peValuaExpertsForFkStatus;
	}

	public void setPeValuaExpertsForFkStatus(Set peValuaExpertsForFkStatus) {
		this.peValuaExpertsForFkStatus = peValuaExpertsForFkStatus;
	}

	public Set getPeVotePapersForFlagViewSuggest() {
		return this.peVotePapersForFlagViewSuggest;
	}

	public void setPeVotePapersForFlagViewSuggest(
			Set peVotePapersForFlagViewSuggest) {
		this.peVotePapersForFlagViewSuggest = peVotePapersForFlagViewSuggest;
	}

}