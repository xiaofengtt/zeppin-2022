package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeEdutype entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeEdutype extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private Set peStudents = new HashSet(0);
	private Set prPriManagerEdutypes = new HashSet(0);
	private Set prStuChangeEdutypesForFkOldEdutypeId = new HashSet(0);
	private Set prEduMajorSiteFeeLevels = new HashSet(0);
	private Set prRecPlanMajorEdutypes = new HashSet(0);
	private Set peTchPrograms = new HashSet(0);
	private Set prStuChangeEdutypesForFkNewEdutypeId = new HashSet(0);
	private Set prRecCourseMajorEdutypes = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeEdutype() {
	}

	/** minimal constructor */
	public PeEdutype(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeEdutype(String name, String code, Set peStudents,
			Set prPriManagerEdutypes, Set prStuChangeEdutypesForFkOldEdutypeId,
			Set prEduMajorSiteFeeLevels, Set prRecPlanMajorEdutypes,
			Set peTchPrograms, Set prStuChangeEdutypesForFkNewEdutypeId,
			Set prRecCourseMajorEdutypes) {
		this.name = name;
		this.code = code;
		this.peStudents = peStudents;
		this.prPriManagerEdutypes = prPriManagerEdutypes;
		this.prStuChangeEdutypesForFkOldEdutypeId = prStuChangeEdutypesForFkOldEdutypeId;
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
		this.prRecPlanMajorEdutypes = prRecPlanMajorEdutypes;
		this.peTchPrograms = peTchPrograms;
		this.prStuChangeEdutypesForFkNewEdutypeId = prStuChangeEdutypesForFkNewEdutypeId;
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
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

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

	public Set getPrPriManagerEdutypes() {
		return this.prPriManagerEdutypes;
	}

	public void setPrPriManagerEdutypes(Set prPriManagerEdutypes) {
		this.prPriManagerEdutypes = prPriManagerEdutypes;
	}

	public Set getPrStuChangeEdutypesForFkOldEdutypeId() {
		return this.prStuChangeEdutypesForFkOldEdutypeId;
	}

	public void setPrStuChangeEdutypesForFkOldEdutypeId(
			Set prStuChangeEdutypesForFkOldEdutypeId) {
		this.prStuChangeEdutypesForFkOldEdutypeId = prStuChangeEdutypesForFkOldEdutypeId;
	}

	public Set getPrEduMajorSiteFeeLevels() {
		return this.prEduMajorSiteFeeLevels;
	}

	public void setPrEduMajorSiteFeeLevels(Set prEduMajorSiteFeeLevels) {
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
	}

	public Set getPrRecPlanMajorEdutypes() {
		return this.prRecPlanMajorEdutypes;
	}

	public void setPrRecPlanMajorEdutypes(Set prRecPlanMajorEdutypes) {
		this.prRecPlanMajorEdutypes = prRecPlanMajorEdutypes;
	}

	public Set getPeTchPrograms() {
		return this.peTchPrograms;
	}

	public void setPeTchPrograms(Set peTchPrograms) {
		this.peTchPrograms = peTchPrograms;
	}

	public Set getPrStuChangeEdutypesForFkNewEdutypeId() {
		return this.prStuChangeEdutypesForFkNewEdutypeId;
	}

	public void setPrStuChangeEdutypesForFkNewEdutypeId(
			Set prStuChangeEdutypesForFkNewEdutypeId) {
		this.prStuChangeEdutypesForFkNewEdutypeId = prStuChangeEdutypesForFkNewEdutypeId;
	}

	public Set getPrRecCourseMajorEdutypes() {
		return this.prRecCourseMajorEdutypes;
	}

	public void setPrRecCourseMajorEdutypes(Set prRecCourseMajorEdutypes) {
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
	}

}