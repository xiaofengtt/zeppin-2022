package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchProgramGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchProgramGroup extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTchProgram peTchProgram;
	private PeTchCoursegroup peTchCoursegroup;
	private Long minCredit;
	private Long maxCredit;
	private Set prTchProgramCourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchProgramGroup() {
	}

	/** full constructor */
	public PeTchProgramGroup(PeTchProgram peTchProgram,
			PeTchCoursegroup peTchCoursegroup, Long minCredit, Long maxCredit,
			Set prTchProgramCourses) {
		this.peTchProgram = peTchProgram;
		this.peTchCoursegroup = peTchCoursegroup;
		this.minCredit = minCredit;
		this.maxCredit = maxCredit;
		this.prTchProgramCourses = prTchProgramCourses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchProgram getPeTchProgram() {
		return this.peTchProgram;
	}

	public void setPeTchProgram(PeTchProgram peTchProgram) {
		this.peTchProgram = peTchProgram;
	}

	public PeTchCoursegroup getPeTchCoursegroup() {
		return this.peTchCoursegroup;
	}

	public void setPeTchCoursegroup(PeTchCoursegroup peTchCoursegroup) {
		this.peTchCoursegroup = peTchCoursegroup;
	}

	public Long getMinCredit() {
		return this.minCredit;
	}

	public void setMinCredit(Long minCredit) {
		this.minCredit = minCredit;
	}

	public Long getMaxCredit() {
		return this.maxCredit;
	}

	public void setMaxCredit(Long maxCredit) {
		this.maxCredit = maxCredit;
	}

	public Set getPrTchProgramCourses() {
		return this.prTchProgramCourses;
	}

	public void setPrTchProgramCourses(Set prTchProgramCourses) {
		this.prTchProgramCourses = prTchProgramCourses;
	}

}