/*
 * @(#)PeNoteHistory.java 11:11:58 AM
 * All Rights Reserved,Copyright(C) 2009,北京网梯科技发展有限公司
 * Aug 31, 2010 应用项目部 zhangyihui
 */
package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * @author <a href="mailto: zhangyihui@whaty.com">zhangyihui</a>
 * @date Aug 31, 2010
 */
public class PeNoteHistory extends AbstractBean {
	private String id;
	private String senderId;
	private String receiverId;
	private Date sendTime;
	private String content;

	public PeNoteHistory(String id, String senderId, String receiverId,
			Date sendTime, String content) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.sendTime = sendTime;
		this.content = content;
	}
	
	public PeNoteHistory(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
}
