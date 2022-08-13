package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionEliteanswerInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionEliteanswerInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String elitequestionId;
	private String body;
	private Date date;
	private String reuserId;
	private String reuserName;
	private String reuserType;

	// Constructors

	/** default constructor */
	public InteractionEliteanswerInfo() {
	}

	/** minimal constructor */
	public InteractionEliteanswerInfo(String elitequestionId, String body,
			Date date, String reuserId, String reuserName) {
		this.elitequestionId = elitequestionId;
		this.body = body;
		this.date = date;
		this.reuserId = reuserId;
		this.reuserName = reuserName;
	}

	/** full constructor */
	public InteractionEliteanswerInfo(String elitequestionId, String body,
			Date date, String reuserId, String reuserName, String reuserType) {
		this.elitequestionId = elitequestionId;
		this.body = body;
		this.date = date;
		this.reuserId = reuserId;
		this.reuserName = reuserName;
		this.reuserType = reuserType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getElitequestionId() {
		return this.elitequestionId;
	}

	public void setElitequestionId(String elitequestionId) {
		this.elitequestionId = elitequestionId;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReuserId() {
		return this.reuserId;
	}

	public void setReuserId(String reuserId) {
		this.reuserId = reuserId;
	}

	public String getReuserName() {
		return this.reuserName;
	}

	public void setReuserName(String reuserName) {
		this.reuserName = reuserName;
	}

	public String getReuserType() {
		return this.reuserType;
	}

	public void setReuserType(String reuserType) {
		this.reuserType = reuserType;
	}

}