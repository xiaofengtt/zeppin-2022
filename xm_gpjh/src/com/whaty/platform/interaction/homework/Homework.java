/*
 * Homework.java
 *
 * Created on 2005年1月6日, 下午5:56
 */

package com.whaty.platform.interaction.homework;

import com.whaty.platform.entity.basic.Course;

/**
 * 作业类
 * 
 * @author chenjian
 */
public abstract class Homework implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String body;

	private String date;

	private String handinDate;

	private Course course;

	private String submituserId;

	private String submituserName;

	private String submituserType;

	private boolean checkStatus = false;

	/** Creates a new instance of Homework */
	public Homework() {
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public String getHandinDate() {
		return handinDate;
	}

	public void setHandinDate(String handinDate) {
		this.handinDate = handinDate;
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
