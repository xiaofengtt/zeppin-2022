package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePriCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePriCategory extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PePriCategory pePriCategory;
	private String name;
	private String code;
	private String path;
	private String flagLeftMenu;
	private Set pePriCategories = new HashSet(0);
	private Set pePriorities = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePriCategory() {
	}

	/** minimal constructor */
	public PePriCategory(String name, String code) {
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public PePriCategory(PePriCategory pePriCategory, String name, String code,
			String path, String flagLeftMenu, Set pePriCategories,
			Set pePriorities) {
		this.pePriCategory = pePriCategory;
		this.name = name;
		this.code = code;
		this.path = path;
		this.flagLeftMenu = flagLeftMenu;
		this.pePriCategories = pePriCategories;
		this.pePriorities = pePriorities;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFlagLeftMenu() {
		return this.flagLeftMenu;
	}

	public void setFlagLeftMenu(String flagLeftMenu) {
		this.flagLeftMenu = flagLeftMenu;
	}

	public Set getPePriCategories() {
		return this.pePriCategories;
	}

	public void setPePriCategories(Set pePriCategories) {
		this.pePriCategories = pePriCategories;
	}

	public Set getPePriorities() {
		return this.pePriorities;
	}

	public void setPePriorities(Set pePriorities) {
		this.pePriorities = pePriorities;
	}

}