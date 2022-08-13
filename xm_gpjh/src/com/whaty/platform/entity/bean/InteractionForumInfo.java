package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionForumInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionForumInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String body;
	private String submituserId;
	private Date publishDate;
	private String courseId;
	private String click;
	private String del;
	private String replyNumber;
	private String forumListId;
	private String userName;
	private String forumElite;
	private String readTimes;
	private String userLevel;
	private String userIp;
	private String relatedId;

	// Constructors

	/** default constructor */
	public InteractionForumInfo() {
	}

	/** minimal constructor */
	public InteractionForumInfo(String title, String body) {
		this.title = title;
		this.body = body;
	}

	/** full constructor */
	public InteractionForumInfo(String title, String body, String submituserId,
			Date publishDate, String courseId, String click, String del,
			String replyNumber, String forumListId, String userName,
			String forumElite, String readTimes, String userLevel,
			String userIp, String relatedId) {
		this.title = title;
		this.body = body;
		this.submituserId = submituserId;
		this.publishDate = publishDate;
		this.courseId = courseId;
		this.click = click;
		this.del = del;
		this.replyNumber = replyNumber;
		this.forumListId = forumListId;
		this.userName = userName;
		this.forumElite = forumElite;
		this.readTimes = readTimes;
		this.userLevel = userLevel;
		this.userIp = userIp;
		this.relatedId = relatedId;
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

	public String getSubmituserId() {
		return this.submituserId;
	}

	public void setSubmituserId(String submituserId) {
		this.submituserId = submituserId;
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

	public String getClick() {
		return this.click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getReplyNumber() {
		return this.replyNumber;
	}

	public void setReplyNumber(String replyNumber) {
		this.replyNumber = replyNumber;
	}

	public String getForumListId() {
		return this.forumListId;
	}

	public void setForumListId(String forumListId) {
		this.forumListId = forumListId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getForumElite() {
		return this.forumElite;
	}

	public void setForumElite(String forumElite) {
		this.forumElite = forumElite;
	}

	public String getReadTimes() {
		return this.readTimes;
	}

	public void setReadTimes(String readTimes) {
		this.readTimes = readTimes;
	}

	public String getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserIp() {
		return this.userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getRelatedId() {
		return this.relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

}