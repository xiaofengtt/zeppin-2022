package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionAnswerInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionAnswerInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String questionId;
	private String body;
	private Date publishDate;
	private String reuserId;
	private String reuserName;
	private String reuserType;

	// Constructors

	/** default constructor */
	public InteractionAnswerInfo() {
	}

	/** full constructor */
	public InteractionAnswerInfo(String questionId, String body,
			Date publishDate, String reuserId, String reuserName,
			String reuserType) {
		this.questionId = questionId;
		this.body = body;
		this.publishDate = publishDate;
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

	public String getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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