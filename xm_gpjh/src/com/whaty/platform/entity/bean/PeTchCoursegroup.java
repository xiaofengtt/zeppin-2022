package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchCoursegroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchCoursegroup extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Set peTchProgramGroups = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchCoursegroup() {
	}

	/** minimal constructor */
	public PeTchCoursegroup(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeTchCoursegroup(String name, Set peTchProgramGroups) {
		this.name = name;
		this.peTchProgramGroups = peTchProgramGroups;
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

	public Set getPeTchProgramGroups() {
		return this.peTchProgramGroups;
	}

	public void setPeTchProgramGroups(Set peTchProgramGroups) {
		this.peTchProgramGroups = peTchProgramGroups;
	}

}