package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeEmailHistoryId entity. @author MyEclipse Persistence Tools
 */

public class PeEmailHistoryId extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

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
	public PeEmailHistoryId() {
	}

	/** minimal constructor */
	public PeEmailHistoryId(String id) {
		this.id = id;
	}

	/** full constructor */
	public PeEmailHistoryId(String id, String senderId, String receiverId,
			String receiverEmail, Date sendTime, String title, String content,
			String attachments) {
		this.id = id;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeEmailHistoryId))
			return false;
		PeEmailHistoryId castOther = (PeEmailHistoryId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getSenderId() == castOther.getSenderId()) || (this
						.getSenderId() != null
						&& castOther.getSenderId() != null && this
						.getSenderId().equals(castOther.getSenderId())))
				&& ((this.getReceiverId() == castOther.getReceiverId()) || (this
						.getReceiverId() != null
						&& castOther.getReceiverId() != null && this
						.getReceiverId().equals(castOther.getReceiverId())))
				&& ((this.getReceiverEmail() == castOther.getReceiverEmail()) || (this
						.getReceiverEmail() != null
						&& castOther.getReceiverEmail() != null && this
						.getReceiverEmail()
						.equals(castOther.getReceiverEmail())))
				&& ((this.getSendTime() == castOther.getSendTime()) || (this
						.getSendTime() != null
						&& castOther.getSendTime() != null && this
						.getSendTime().equals(castOther.getSendTime())))
				&& ((this.getTitle() == castOther.getTitle()) || (this
						.getTitle() != null
						&& castOther.getTitle() != null && this.getTitle()
						.equals(castOther.getTitle())))
				&& ((this.getContent() == castOther.getContent()) || (this
						.getContent() != null
						&& castOther.getContent() != null && this.getContent()
						.equals(castOther.getContent())))
				&& ((this.getAttachments() == castOther.getAttachments()) || (this
						.getAttachments() != null
						&& castOther.getAttachments() != null && this
						.getAttachments().equals(castOther.getAttachments())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getSenderId() == null ? 0 : this.getSenderId().hashCode());
		result = 37
				* result
				+ (getReceiverId() == null ? 0 : this.getReceiverId()
						.hashCode());
		result = 37
				* result
				+ (getReceiverEmail() == null ? 0 : this.getReceiverEmail()
						.hashCode());
		result = 37 * result
				+ (getSendTime() == null ? 0 : this.getSendTime().hashCode());
		result = 37 * result
				+ (getTitle() == null ? 0 : this.getTitle().hashCode());
		result = 37 * result
				+ (getContent() == null ? 0 : this.getContent().hashCode());
		result = 37
				* result
				+ (getAttachments() == null ? 0 : this.getAttachments()
						.hashCode());
		return result;
	}

}