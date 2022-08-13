package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeEmailHistory entity. @author MyEclipse Persistence Tools
 */

public class PeEmailHistory extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String senderId;
	private String receiverId;
	private String receiverEmail;
	private Date sendTime;
	private String title;
	private String content;
	private String attachments;

	// Constructors

	/** default constructor */
	public PeEmailHistory() {
	}

	/** full constructor */
	public PeEmailHistory(String senderId, String receiverId,
			String receiverEmail, Date sendTime, String title, String content,
			String attachments) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.receiverEmail = receiverEmail;
		this.sendTime = sendTime;
		this.title = title;
		this.content = content;
		this.attachments = attachments;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverEmail() {
		return this.receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

}