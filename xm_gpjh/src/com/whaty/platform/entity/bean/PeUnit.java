package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeUnit entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeUnit extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFkUnitType;
	private EnumConst enumConstByFlagIsvalid;
	private PeProvince peProvince;
	private String code;
	private String name;
	private String note;
	private Set peManagers = new HashSet(0);
	private Set peOtherMaterials = new HashSet(0);
	private Set peFeeActualBudgets = new HashSet(0);
	private Set peProApplies = new HashSet(0);
	private Set peMeetingResources = new HashSet(0);
	private Set prMeetPersons = new HashSet(0);
	private Set peTraineesForFkTrainingUnit = new HashSet(0);
	private Set prJobUnits = new HashSet(0);
	private Set peTrainExperts = new HashSet(0);
	private Set peFeeBudgets = new HashSet(0);
	private Set prProgramUnits = new HashSet(0);
	private Set peTraineesForFkUnitFrom = new HashSet(0);
	private Set peMeetings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeUnit() {
	}

	/** full constructor */
	public PeUnit(EnumConst enumConstByFkUnitType,
			EnumConst enumConstByFlagIsvalid, PeProvince peProvince,
			String code, String name, String note, Set peManagers,
			Set peOtherMaterials, Set peFeeActualBudgets, Set peProApplies,
			Set peMeetingResources, Set prMeetPersons,
			Set peTraineesForFkTrainingUnit, Set prJobUnits,
			Set peTrainExperts, Set peFeeBudgets, Set prProgramUnits,
			Set peTraineesForFkUnitFrom, Set peMeetings) {
		this.enumConstByFkUnitType = enumConstByFkUnitType;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.peProvince = peProvince;
		this.code = code;
		this.name = name;
		this.note = note;
		this.peManagers = peManagers;
		this.peOtherMaterials = peOtherMaterials;
		this.peFeeActualBudgets = peFeeActualBudgets;
		this.peProApplies = peProApplies;
		this.peMeetingResources = peMeetingResources;
		this.prMeetPersons = prMeetPersons;
		this.peTraineesForFkTrainingUnit = peTraineesForFkTrainingUnit;
		this.prJobUnits = prJobUnits;
		this.peTrainExperts = peTrainExperts;
		this.peFeeBudgets = peFeeBudgets;
		this.prProgramUnits = prProgramUnits;
		this.peTraineesForFkUnitFrom = peTraineesForFkUnitFrom;
		this.peMeetings = peMeetings;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFkUnitType() {
		return this.enumConstByFkUnitType;
	}

	public void setEnumConstByFkUnitType(EnumConst enumConstByFkUnitType) {
		this.enumConstByFkUnitType = enumConstByFkUnitType;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public PeProvince getPeProvince() {
		return this.peProvince;
	}

	public void setPeProvince(PeProvince peProvince) {
		this.peProvince = peProvince;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeManagers() {
		return this.peManagers;
	}

	public void setPeManagers(Set peManagers) {
		this.peManagers = peManagers;
	}

	public Set getPeOtherMaterials() {
		return this.peOtherMaterials;
	}

	public void setPeOtherMaterials(Set peOtherMaterials) {
		this.peOtherMaterials = peOtherMaterials;
	}

	public Set getPeFeeActualBudgets() {
		return this.peFeeActualBudgets;
	}

	public void setPeFeeActualBudgets(Set peFeeActualBudgets) {
		this.peFeeActualBudgets = peFeeActualBudgets;
	}

	public Set getPeProApplies() {
		return this.peProApplies;
	}

	public void setPeProApplies(Set peProApplies) {
		this.peProApplies = peProApplies;
	}

	public Set getPeMeetingResources() {
		return this.peMeetingResources;
	}

	public void setPeMeetingResources(Set peMeetingResources) {
		this.peMeetingResources = peMeetingResources;
	}

	public Set getPrMeetPersons() {
		return this.prMeetPersons;
	}

	public void setPrMeetPersons(Set prMeetPersons) {
		this.prMeetPersons = prMeetPersons;
	}

	public Set getPeTraineesForFkTrainingUnit() {
		return this.peTraineesForFkTrainingUnit;
	}

	public void setPeTraineesForFkTrainingUnit(Set peTraineesForFkTrainingUnit) {
		this.peTraineesForFkTrainingUnit = peTraineesForFkTrainingUnit;
	}

	public Set getPrJobUnits() {
		return this.prJobUnits;
	}

	public void setPrJobUnits(Set prJobUnits) {
		this.prJobUnits = prJobUnits;
	}

	public Set getPeTrainExperts() {
		return this.peTrainExperts;
	}

	public void setPeTrainExperts(Set peTrainExperts) {
		this.peTrainExperts = peTrainExperts;
	}

	public Set getPeFeeBudgets() {
		return this.peFeeBudgets;
	}

	public void setPeFeeBudgets(Set peFeeBudgets) {
		this.peFeeBudgets = peFeeBudgets;
	}

	public Set getPrProgramUnits() {
		return this.prProgramUnits;
	}

	public void setPrProgramUnits(Set prProgramUnits) {
		this.prProgramUnits = prProgramUnits;
	}

	public Set getPeTraineesForFkUnitFrom() {
		return this.peTraineesForFkUnitFrom;
	}

	public void setPeTraineesForFkUnitFrom(Set peTraineesForFkUnitFrom) {
		this.peTraineesForFkUnitFrom = peTraineesForFkUnitFrom;
	}

	public Set getPeMeetings() {
		return this.peMeetings;
	}

	public void setPeMeetings(Set peMeetings) {
		this.peMeetings = peMeetings;
	}

}