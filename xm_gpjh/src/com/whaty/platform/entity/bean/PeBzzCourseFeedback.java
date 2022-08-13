package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeBzzCourseFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzCourseFeedback extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String px;
	private Set peBzzAssesses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeBzzCourseFeedback() {
	}

	/** full constructor */
	public PeBzzCourseFeedback(String name, String px, Set peBzzAssesses) {
		this.name = name;
		this.px = px;
		this.peBzzAssesses = peBzzAssesses;
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

	public String getPx() {
		return this.px;
	}

	public void setPx(String px) {
		this.px = px;
	}

	public Set getPeBzzAssesses() {
		return this.peBzzAssesses;
	}

	public void setPeBzzAssesses(Set peBzzAssesses) {
		this.peBzzAssesses = peBzzAssesses;
	}

}