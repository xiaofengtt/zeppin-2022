/*
 * HomeworkCheck.java
 *
 * Created on 2005年1月6日, 下午6:02
 */

package com.whaty.platform.interaction.homework;

/**
 * 
 * @author Administrator
 */
public abstract class HomeworkCheck implements com.whaty.platform.Items {
	private String id;

	private Homework homework;

	private String body;

	private String date;

	private String checkuserId;

	private String checkuserName;

	private String checkuserType;

	/** Creates a new instance of HomeworkCheck */
	public HomeworkCheck() {
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

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
