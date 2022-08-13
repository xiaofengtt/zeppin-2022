package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionQuestionInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionQuestionInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String body;
	private String key;
	private Date publishDate;
	private String courseId;
	private String submituserId;
	private String submituserName;
	private String submituserType;
	private String reStatus;

	// Constructors

	/** default constructor */
	public InteractionQuestionInfo() {
	}

	/** minimal constructor */
	public InteractionQuestionInfo(String title, String body, String key,
			Date publishDate, String courseId, String submituserId,
			String submituserName, String reStatus) {
		this.title = title;
		this.body = body;
		this.key = key;
		this.publishDate = publishDate;
		this.courseId = courseId;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
		this.reStatus = reStatus;
	}

	/** full constructor */
	public InteractionQuestionInfo(String title, String body, String key,
			Date publishDate, String courseId, String submituserId,
			String submituserName, String submituserType, String reStatus) {
		this.title = title;
		this.body = body;
		this.key = key;
		this.publishDate = publishDate;
		this.courseId = courseId;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
		this.submituserType = submituserType;
		this.reStatus = reStatus;
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

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
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

	public String getReStatus() {
		return this.reStatus;
	}

	public void setReStatus(String reStatus) {
		this.reStatus = reStatus;
	}

}