package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePriority entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePriority extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePriCategory pePriCategory;
	private String name;
	private String namespace;
	private String action;
	private String method;
	private Set prPriRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePriority() {
	}

	/** minimal constructor */
	public PePriority(String name, String namespace, String action,
			String method) {
		this.name = name;
		this.namespace = namespace;
		this.action = action;
		this.method = method;
	}

	/** full constructor */
	public PePriority(PePriCategory pePriCategory, String name,
			String namespace, String action, String method, Set prPriRoles) {
		this.pePriCategory = pePriCategory;
		this.name = name;
		this.namespace = namespace;
		this.action = action;
		this.method = method;
		this.prPriRoles = prPriRoles;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PePriCategory getPePriCategory() {
		return this.pePriCategory;
	}

	public void setPePriCategory(PePriCategory pePriCategory) {
		this.pePriCategory = pePriCategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Set getPrPriRoles() {
		return this.prPriRoles;
	}

	public void setPrPriRoles(Set prPriRoles) {
		this.prPriRoles = prPriRoles;
	}

}