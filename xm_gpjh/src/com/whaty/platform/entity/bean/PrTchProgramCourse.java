package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrTchProgramCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchProgramCourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTchProgramGroup peTchProgramGroup;
	private String fkCourseId;
	private String flagIsMainCourse;
	private String flagBak;
	private Double credit;
	private Long unit;
	private Set prTchStuElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchProgramCourse() {
	}

	/** full constructor */
	public PrTchProgramCourse(PeTchProgramGroup peTchProgramGroup,
			String fkCourseId, String flagIsMainCourse, String flagBak,
			Double credit, Long unit, Set prTchStuElectives) {
		this.peTchProgramGroup = peTchProgramGroup;
		this.fkCourseId = fkCourseId;
		this.flagIsMainCourse = flagIsMainCourse;
		this.flagBak = flagBak;
		this.credit = credit;
		this.unit = unit;
		this.prTchStuElectives = prTchStuElectives;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchProgramGroup getPeTchProgramGroup() {
		return this.peTchProgramGroup;
	}

	public void setPeTchProgramGroup(PeTchProgramGroup peTchProgramGroup) {
		this.peTchProgramGroup = peTchProgramGroup;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

	public String getFlagIsMainCourse() {
		return this.flagIsMainCourse;
	}

	public void setFlagIsMainCourse(String flagIsMainCourse) {
		this.flagIsMainCourse = flagIsMainCourse;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Double getCredit() {
		return this.credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Long getUnit() {
		return this.unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	public Set getPrTchStuElectives() {
		return this.prTchStuElectives;
	}

	public void setPrTchStuElectives(Set prTchStuElectives) {
		this.prTchStuElectives = prTchStuElectives;
	}

}