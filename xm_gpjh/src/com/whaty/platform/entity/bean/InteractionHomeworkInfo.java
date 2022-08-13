package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionHomeworkInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionHomeworkInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String body;
	private Date date;
	private Date handinDate;
	private String courseId;
	private String checkStatus;
	private String submituserId;
	private String submituserName;
	private String submituserType;

	// Constructors

	/** default constructor */
	public InteractionHomeworkInfo() {
	}

	/** minimal constructor */
	public InteractionHomeworkInfo(String title, String body, Date date,
			Date handinDate, String courseId, String checkStatus,
			String submituserId, String submituserName) {
		this.title = title;
		this.body = body;
		this.date = date;
		this.handinDate = handinDate;
		this.courseId = courseId;
		this.checkStatus = checkStatus;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
	}

	/** full constructor */
	public InteractionHomeworkInfo(String title, String body, Date date,
			Date handinDate, String courseId, String checkStatus,
			String submituserId, String submituserName, String submituserType) {
		this.title = title;
		this.body = body;
		this.date = date;
		this.handinDate = handinDate;
		this.courseId = courseId;
		this.checkStatus = checkStatus;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
		this.submituserType = submituserType;
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

	public Date getHandinDate() {
		return this.handinDate;
	}

	public void setHandinDate(Date handinDate) {
		this.handinDate = handinDate;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
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

}