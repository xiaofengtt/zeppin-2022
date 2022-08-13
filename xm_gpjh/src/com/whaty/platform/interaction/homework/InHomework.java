/*
 * Inhomework.java
 *
 * Created on 2005年1月6日, 下午6:03
 */

package com.whaty.platform.interaction.homework;

/**
 * 
 * @author Administrator
 */
public abstract class InHomework implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String file;

	private String body;

	private String date;

	private boolean checkStatus = false;

	private String submituserId;

	private String submituserName;

	private String submituserType;

	private Homework homework;

	/** Creates a new instance of Inhomework */
	public InHomework() {
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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

	public boolean isCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
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

}
