package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeMajor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeMajor extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String alias;
	private String flagIsEducation;
	private String flagBak;
	private Set prStuMultiMajors = new HashSet(0);
	private Set prEduMajorSiteFeeLevels = new HashSet(0);
	private Set prStuChangeMajorsForFkOldMajorId = new HashSet(0);
	private Set peStudents = new HashSet(0);
	private Set prStuChangeableMajorsForFkOldMajorId = new HashSet(0);
	private Set prRecPlanMajorEdutypes = new HashSet(0);
	private Set prPriManagerMajors = new HashSet(0);
	private Set prRecCourseMajorEdutypes = new HashSet(0);
	private Set prStuChangeMajorsForFkNewMajorId = new HashSet(0);
	private Set peTchPrograms = new HashSet(0);
	private Set prStuChangeableMajorsForFkNewMajorId = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeMajor() {
	}

	/** minimal constructor */
	public PeMajor(String name, String code, String alias) {
		this.name = name;
		this.code = code;
		this.alias = alias;
	}

	/** full constructor */
	public PeMajor(String name, String code, String alias,
			String flagIsEducation, String flagBak, Set prStuMultiMajors,
			Set prEduMajorSiteFeeLevels, Set prStuChangeMajorsForFkOldMajorId,
			Set peStudents, Set prStuChangeableMajorsForFkOldMajorId,
			Set prRecPlanMajorEdutypes, Set prPriManagerMajors,
			Set prRecCourseMajorEdutypes, Set prStuChangeMajorsForFkNewMajorId,
			Set peTchPrograms, Set prStuChangeableMajorsForFkNewMajorId) {
		this.name = name;
		this.code = code;
		this.alias = alias;
		this.flagIsEducation = flagIsEducation;
		this.flagBak = flagBak;
		this.prStuMultiMajors = prStuMultiMajors;
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
		this.prStuChangeMajorsForFkOldMajorId = prStuChangeMajorsForFkOldMajorId;
		this.peStudents = peStudents;
		this.prStuChangeableMajorsForFkOldMajorId = prStuChangeableMajorsForFkOldMajorId;
		this.prRecPlanMajorEdutypes = prRecPlanMajorEdutypes;
		this.prPriManagerMajors = prPriManagerMajors;
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
		this.prStuChangeMajorsForFkNewMajorId = prStuChangeMajorsForFkNewMajorId;
		this.peTchPrograms = peTchPrograms;
		this.prStuChangeableMajorsForFkNewMajorId = prStuChangeableMajorsForFkNewMajorId;
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

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getFlagIsEducation() {
		return this.flagIsEducation;
	}

	public void setFlagIsEducation(String flagIsEducation) {
		this.flagIsEducation = flagIsEducation;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Set getPrStuMultiMajors() {
		return this.prStuMultiMajors;
	}

	public void setPrStuMultiMajors(Set prStuMultiMajors) {
		this.prStuMultiMajors = prStuMultiMajors;
	}

	public Set getPrEduMajorSiteFeeLevels() {
		return this.prEduMajorSiteFeeLevels;
	}

	public void setPrEduMajorSiteFeeLevels(Set prEduMajorSiteFeeLevels) {
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
	}

	public Set getPrStuChangeMajorsForFkOldMajorId() {
		return this.prStuChangeMajorsForFkOldMajorId;
	}

	public void setPrStuChangeMajorsForFkOldMajorId(
			Set prStuChangeMajorsForFkOldMajorId) {
		this.prStuChangeMajorsForFkOldMajorId = prStuChangeMajorsForFkOldMajorId;
	}

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

	public Set getPrStuChangeableMajorsForFkOldMajorId() {
		return this.prStuChangeableMajorsForFkOldMajorId;
	}

	public void setPrStuChangeableMajorsForFkOldMajorId(
			Set prStuChangeableMajorsForFkOldMajorId) {
		this.prStuChangeableMajorsForFkOldMajorId = prStuChangeableMajorsForFkOldMajorId;
	}

	public Set getPrRecPlanMajorEdutypes() {
		return this.prRecPlanMajorEdutypes;
	}

	public void setPrRecPlanMajorEdutypes(Set prRecPlanMajorEdutypes) {
		this.prRecPlanMajorEdutypes = prRecPlanMajorEdutypes;
	}

	public Set getPrPriManagerMajors() {
		return this.prPriManagerMajors;
	}

	public void setPrPriManagerMajors(Set prPriManagerMajors) {
		this.prPriManagerMajors = prPriManagerMajors;
	}

	public Set getPrRecCourseMajorEdutypes() {
		return this.prRecCourseMajorEdutypes;
	}

	public void setPrRecCourseMajorEdutypes(Set prRecCourseMajorEdutypes) {
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
	}

	public Set getPrStuChangeMajorsForFkNewMajorId() {
		return this.prStuChangeMajorsForFkNewMajorId;
	}

	public void setPrStuChangeMajorsForFkNewMajorId(
			Set prStuChangeMajorsForFkNewMajorId) {
		this.prStuChangeMajorsForFkNewMajorId = prStuChangeMajorsForFkNewMajorId;
	}

	public Set getPeTchPrograms() {
		return this.peTchPrograms;
	}

	public void setPeTchPrograms(Set peTchPrograms) {
		this.peTchPrograms = peTchPrograms;
	}

	public Set getPrStuChangeableMajorsForFkNewMajorId() {
		return this.prStuChangeableMajorsForFkNewMajorId;
	}

	public void setPrStuChangeableMajorsForFkNewMajorId(
			Set prStuChangeableMajorsForFkNewMajorId) {
		this.prStuChangeableMajorsForFkNewMajorId = prStuChangeableMajorsForFkNewMajorId;
	}

}