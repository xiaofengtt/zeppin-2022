package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionTeachclassInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionTeachclassInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String teachclassId;
	private String title;
	private String content;
	private String status;
	private String type;
	private Date publishDate;

	// Constructors

	/** default constructor */
	public InteractionTeachclassInfo() {
	}

	/** minimal constructor */
	public InteractionTeachclassInfo(String teachclassId) {
		this.teachclassId = teachclassId;
	}

	/** full constructor */
	public InteractionTeachclassInfo(String teachclassId, String title,
			String content, String status, String type, Date publishDate) {
		this.teachclassId = teachclassId;
		this.title = title;
		this.content = content;
		this.status = status;
		this.type = type;
		this.publishDate = publishDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeachclassId() {
		return this.teachclassId;
	}

	public void setTeachclassId(String teachclassId) {
		this.teachclassId = teachclassId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}