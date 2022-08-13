package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionAnnounceInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionAnnounceInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String body;
	private String publisherId;
	private String publisherName;
	private String publisherType;
	private Date publishDate;
	private String courseId;

	// Constructors

	/** default constructor */
	public InteractionAnnounceInfo() {
	}

	/** minimal constructor */
	public InteractionAnnounceInfo(String title, String body,
			String publisherId, String publisherType, Date publishDate,
			String courseId) {
		this.title = title;
		this.body = body;
		this.publisherId = publisherId;
		this.publisherType = publisherType;
		this.publishDate = publishDate;
		this.courseId = courseId;
	}

	/** full constructor */
	public InteractionAnnounceInfo(String title, String body,
			String publisherId, String publisherName, String publisherType,
			Date publishDate, String courseId) {
		this.title = title;
		this.body = body;
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publisherType = publisherType;
		this.publishDate = publishDate;
		this.courseId = courseId;
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

	public String getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return this.publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherType() {
		return this.publisherType;
	}

	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
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

}