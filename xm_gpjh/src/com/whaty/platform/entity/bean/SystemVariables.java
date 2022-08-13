package com.whaty.platform.entity.bean;

/**
 * SystemVariables entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SystemVariables extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String value;
	private String pattern;
	private String flagPlatformSection;
	private String flagBak;

	// Constructors

	/** default constructor */
	public SystemVariables() {
	}

	/** minimal constructor */
	public SystemVariables(String name, String value, String pattern) {
		this.name = name;
		this.value = value;
		this.pattern = pattern;
	}

	/** full constructor */
	public SystemVariables(String name, String value, String pattern,
			String flagPlatformSection, String flagBak) {
		this.name = name;
		this.value = value;
		this.pattern = pattern;
		this.flagPlatformSection = flagPlatformSection;
		this.flagBak = flagBak;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getFlagPlatformSection() {
		return this.flagPlatformSection;
	}

	public void setFlagPlatformSection(String flagPlatformSection) {
		this.flagPlatformSection = flagPlatformSection;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}