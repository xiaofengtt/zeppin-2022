package com.whaty.platform.entity.bean;

/**
 * PeExamScoreInputUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExamScoreInputUser extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String password;

	// Constructors

	/** default constructor */
	public PeExamScoreInputUser() {
	}

	/** full constructor */
	public PeExamScoreInputUser(String name, String password) {
		this.name = name;
		this.password = password;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}