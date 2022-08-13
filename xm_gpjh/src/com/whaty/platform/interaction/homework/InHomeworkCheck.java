/*
 * InHomeworkCheck.java
 *
 * Created on 2005年1月6日, 下午6:12
 */

package com.whaty.platform.interaction.homework;

/**
 * 
 * @author Administrator
 */
public abstract class InHomeworkCheck implements com.whaty.platform.Items {

	private String id;

	private InHomework inHomework;

	private String body;

	private String score = "0";

	private String date;

	private String checkuserId;

	private String checkuserName;

	private String checkuserType;

	/** Creates a new instance of InHomeworkCheck */
	public InHomeworkCheck() {
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCheckuserId() {
		return checkuserId;
	}

	public void setCheckuserId(String checkuserId) {
		this.checkuserId = checkuserId;
	}

	public String getCheckuserName() {
		return checkuserName;
	}

	public void setCheckuserName(String checkuserName) {
		this.checkuserName = checkuserName;
	}

	public String getCheckuserType() {
		return checkuserType;
	}

	public void setCheckuserType(String checkuserType) {
		this.checkuserType = checkuserType;
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

	public InHomework getInHomework() {
		return inHomework;
	}

	public void setInHomework(InHomework inHomework) {
		this.inHomework = inHomework;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
