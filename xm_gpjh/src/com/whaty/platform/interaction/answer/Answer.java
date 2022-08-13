/*
 * Answer.java
 *
 * Created on 2005年1月6日, 下午2:37
 */

package com.whaty.platform.interaction.answer;

/**
 * 答疑对应的类
 * 
 * @author chenjian
 */
public abstract class Answer implements com.whaty.platform.Items {

	private String id; 

	private Question question;

	private String body;

	private String date;

	private String reuserId;

	private String reuserName;

	private String reuserType;

	/** Creates a new instance of Question */
	public Answer() {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getReuserId() {
		return reuserId;
	}

	public void setReuserId(String reuserId) {
		this.reuserId = reuserId;
	}

	public String getReuserName() {
		return reuserName;
	}

	public void setReuserName(String reuserName) {
		this.reuserName = reuserName;
	}

	public String getReuserType() {
		return reuserType;
	}

	public void setReuserType(String reuserType) {
		this.reuserType = reuserType;
	}

}
