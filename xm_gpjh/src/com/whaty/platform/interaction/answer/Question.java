/*
 * Question.java
 *
 * Created on 2005��1��6��, ����2:37
 */

package com.whaty.platform.interaction.answer;


/**
 * ���ɶ�Ӧ����
 * 
 * @author chenjian
 */
public abstract class Question implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String body;

	private String[] key;

	private String date;

	private String courseId;

	private String submituserId;

	private String submituserName;

	private String submituserType;

	private boolean reStatus = false;

	// private String type;

	/** Creates a new instance of Question */
	public Question() {
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public String getKeys() {
		String keys = "";
		if (this.getKey() != null && this.getKey().length > 0) {
			for (int i = 0; i < this.getKey().length; i++) {
				keys = keys + this.getKey()[i] + ",";
			}
			if (keys.length() > 1)
				return keys.substring(0, keys.length() - 1);
			else
				return "";
		} else {
			return "";
		}
	}

	public boolean isReStatus() {
		return reStatus;
	}

	public void setReStatus(boolean reStatus) {
		this.reStatus = reStatus;
	}

	public String getSubmituserId() {
		return submituserId;
	}

	public void setSubmituserId(String submituserId) {
		this.submituserId = submituserId;
	}

	public String getSubmituserName() {
		return submituserName;
	}

	public void setSubmituserName(String submituserName) {
		this.submituserName = submituserName;
	}

	public String getSubmituserType() {
		return submituserType;
	}

	public void setSubmituserType(String submituserType) {
		this.submituserType = submituserType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract int addToElite(QuestionEliteDir dir);

}
