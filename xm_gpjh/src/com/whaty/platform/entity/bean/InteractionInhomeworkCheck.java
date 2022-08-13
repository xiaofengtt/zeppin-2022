package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionInhomeworkCheck entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionInhomeworkCheck extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String inhomeworkId;
	private String body;
	private String score;
	private Date date;
	private String checkuserId;
	private String checkuserName;
	private String checkuserType;

	// Constructors

	/** default constructor */
	public InteractionInhomeworkCheck() {
	}

	/** minimal constructor */
	public InteractionInhomeworkCheck(String inhomeworkId, String body,
			Date date, String checkuserId, String checkuserName) {
		this.inhomeworkId = inhomeworkId;
		this.body = body;
		this.date = date;
		this.checkuserId = checkuserId;
		this.checkuserName = checkuserName;
	}

	/** full constructor */
	public InteractionInhomeworkCheck(String inhomeworkId, String body,
			String score, Date date, String checkuserId, String checkuserName,
			String checkuserType) {
		this.inhomeworkId = inhomeworkId;
		this.body = body;
		this.score = score;
		this.date = date;
		this.checkuserId = checkuserId;
		this.checkuserName = checkuserName;
		this.checkuserType = checkuserType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInhomeworkId() {
		return this.inhomeworkId;
	}

	public void setInhomeworkId(String inhomeworkId) {
		this.inhomeworkId = inhomeworkId;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCheckuserId() {
		return this.checkuserId;
	}

	public void setCheckuserId(String checkuserId) {
		this.checkuserId = checkuserId;
	}

	public String getCheckuserName() {
		return this.checkuserName;
	}

	public void setCheckuserName(String checkuserName) {
		this.checkuserName = checkuserName;
	}

	public String getCheckuserType() {
		return this.checkuserType;
	}

	public void setCheckuserType(String checkuserType) {
		this.checkuserType = checkuserType;
	}

}