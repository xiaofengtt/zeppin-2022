/*
 * Announce.java
 *
 * Created on 2005��1��6��, ����8:52
 */

package com.whaty.platform.interaction.announce;


/**
 * ������
 * 
 * @author chenjian
 */
public abstract class Announce implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String body;

	private String date;

	private String publisherId;

	private String publisherName;

	private String publisherType;

	// �˴�Ϊ open_course_id
	private String courseId;

	/*
	 * private String type;
	 */

	/** Creates a new instance of Announce */
	public Announce() {
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

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ����ʦ����֪ͨ����Ϣͨ��Email���͸�ѧ��
	 */
	public abstract void sendEmail();

	/**
	 * ����ʦ����֪ͨ����Ϣͨ�����Ϣ���͸�ѧ��
	 */
	public abstract void sendSMS();

	/**
	 * ���Ҫ������֪ͨ
	 */
	public abstract void check();

	/**
	 * ȡ�����֪ͨ
	 */
	public abstract void uncheck();

	/**
	 * �ö�
	 */
	public abstract void putTop();

	/**
	 * ȡ���ö�
	 */
	public abstract void unPutTop();

}
