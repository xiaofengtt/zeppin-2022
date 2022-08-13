package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionInhomeworkInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionInhomeworkInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String file;
	private String body;
	private Date date;
	private String submituserId;
	private String submituserName;
	private String submituserType;
	private String homeworkId;
	private String checkStatus;

	// Constructors

	/** default constructor */
	public InteractionInhomeworkInfo() {
	}

	/** minimal constructor */
	public InteractionInhomeworkInfo(String title, String body, Date date,
			String submituserId, String submituserName, String homeworkId,
			String checkStatus) {
		this.title = title;
		this.body = body;
		this.date = date;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
		this.homeworkId = homeworkId;
		this.checkStatus = checkStatus;
	}

	/** full constructor */
	public InteractionInhomeworkInfo(String title, String file, String body,
			Date date, String submituserId, String submituserName,
			String submituserType, String homeworkId, String checkStatus) {
		this.title = title;
		this.file = file;
		this.body = body;
		this.date = date;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
		this.submituserType = submituserType;
		this.homeworkId = homeworkId;
		this.checkStatus = checkStatus;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
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

	public String getSubmituserId() {
		return this.submituserId;
	}

	public void setSubmituserId(String submituserId) {
		this.submituserId = submituserId;
	}

	public String getSubmituserName() {
		return this.submituserName;
	}

	public void setSubmituserName(String submituserName) {
		this.submituserName = submituserName;
	}

	public String getSubmituserType() {
		return this.submituserType;
	}

	public void setSubmituserType(String submituserType) {
		this.submituserType = submituserType;
	}

	public String getHomeworkId() {
		return this.homeworkId;
	}

	public void setHomeworkId(String homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

}