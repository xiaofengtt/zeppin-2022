/**
 * Forum.java
 */
package com.whaty.platform.interaction.forum;

import com.whaty.platform.Exception.PlatformException;

public abstract class Forum implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String body;

	private String submitUserId;

	private String date;

	private String courseId;

	private String forumListId;

	private String forumElite;

	private String userLevel;

	private String userIp;

	private String relatedID;

	private String userName;

	private int click;

	private int replyNumber;

	private int readTimes;

	private int del;

	public Forum() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getForumListId() {
		return forumListId;
	}

	public void setForumListId(String forumListId) {
		this.forumListId = forumListId;
	}

	public String getForumElite() {
		return forumElite;
	}

	public void setForumElite(String forumElite) {
		this.forumElite = forumElite;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getRelatedID() {
		return relatedID;
	}

	public void setRelatedID(String relatedID) {
		this.relatedID = relatedID;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getReplyNumber() {
		return replyNumber;
	}

	public void setReplyNumber(int replyNumber) {
		this.replyNumber = replyNumber;
	}

	public int getReadTimes() {
		return readTimes;
	}

	public void setReadTimes(int readTimes) {
		this.readTimes = readTimes;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public abstract int deleteTopicForum() throws PlatformException;
}
