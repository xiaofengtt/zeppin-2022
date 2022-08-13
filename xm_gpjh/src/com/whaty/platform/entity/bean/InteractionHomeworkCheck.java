package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionHomeworkCheck entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionHomeworkCheck extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String homeworkId;
	private String body;
	private Date date;
	private String checkuserId;
	private String checkuserName;
	private String checkuserType;

	// Constructors

	/** default constructor */
	public InteractionHomeworkCheck() {
	}

	/** minimal constructor */
	public InteractionHomeworkCheck(String homeworkId, String body, Date date,
			String checkuserId, String checkuserName) {
		this.homeworkId = homeworkId;
		this.body = body;
		this.date = date;
		this.checkuserId = checkuserId;
		this.checkuserName = checkuserName;
	}

	/** full constructor */
	public InteractionHomeworkCheck(String homeworkId, String body, Date date,
			String checkuserId, String checkuserName, String checkuserType) {
		this.homeworkId = homeworkId;
		this.body = body;
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

	public String getHomeworkId() {
		return this.homeworkId;
	}

	public void setHomeworkId(String homeworkId) {
		this.homeworkId = homeworkId;
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