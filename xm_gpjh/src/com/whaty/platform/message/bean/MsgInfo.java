package com.whaty.platform.message.bean;

import java.util.Date;

/**
 * MsgInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MsgInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String fromUserId;
	private String fromUserName;
	private String content;
	private Date sendTime;
	private Date expireTime;
	private Date readTime;
	private String canReply;
	private String status;

	// Constructors

	/** default constructor */
	public MsgInfo() {
	}

	/** full constructor */
	public MsgInfo(String userId, String fromUserId, String fromUserName,
			String content, Date sendTime, Date expireTime, Date readTime,
			String canReply, String status) {
		this.userId = userId;
		this.fromUserId = fromUserId;
		this.fromUserName = fromUserName;
		this.content = content;
		this.sendTime = sendTime;
		this.expireTime = expireTime;
		this.readTime = readTime;
		this.canReply = canReply;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFromUserId() {
		return this.fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return this.fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getCanReply() {
		return this.canReply;
	}

	public void setCanReply(String canReply) {
		this.canReply = canReply;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}