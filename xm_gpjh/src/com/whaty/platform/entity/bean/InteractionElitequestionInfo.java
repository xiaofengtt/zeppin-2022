package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionElitequestionInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionElitequestionInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String body;
	private String key;
	private Date date;
	private String courseId;
	private String dirId;
	private String submituserId;
	private String submituserName;
	private String submituserType;

	// Constructors

	/** default constructor */
	public InteractionElitequestionInfo() {
	}

	/** minimal constructor */
	public InteractionElitequestionInfo(String title, String body, String key,
			Date date, String courseId, String dirId, String submituserId,
			String submituserName) {
		this.title = title;
		this.body = body;
		this.key = key;
		this.date = date;
		this.courseId = courseId;
		this.dirId = dirId;
		this.submituserId = submituserId;
		this.submituserName = submituserName;
	}

	/** full constructor */
	public InteractionElitequestionInfo(String title, String body, String key,
			Date date, String courseId, String dirId, String submituserId,
			String submituserName, String submituserType) {
		this.title = title;
		this.body = body;
		this.key = key;
		this.date = date;
		this.courseId = courseId;
		this.dirId = dirId;
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

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDirId() {
		return this.dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
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