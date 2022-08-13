package com.whaty.platform.test.paper;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class Paper {
	private String id = "";

	private String title = "";

	private String note = "";

	private String creatUser = "";

	private String creatDate = "";

	private String status = "";

	private String type = "";

	private String groupId = "";

	/**
	 * 属性 creatDate 的获取方法。
	 * 
	 * @return 属性 creatDate 的值。
	 */
	public String getCreatDate() {
		return creatDate;
	}

	/**
	 * 属性 creatDate 的设置方法。
	 * 
	 * @param creatDate
	 *            属性 creatDate 的新值。
	 */
	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	/**
	 * 属性 creatUser 的获取方法。
	 * 
	 * @return 属性 creatUser 的值。
	 */
	public String getCreatUser() {
		return creatUser;
	}

	/**
	 * 属性 creatUser 的设置方法。
	 * 
	 * @param creatUser
	 *            属性 creatUser 的新值。
	 */
	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	/**
	 * 属性 id 的获取方法。
	 * 
	 * @return 属性 id 的值。
	 */
	public String getId() {
		return id;
	}

	/**
	 * 属性 id 的设置方法。
	 * 
	 * @param id
	 *            属性 id 的新值。
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 属性 note 的获取方法。
	 * 
	 * @return 属性 note 的值。
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 属性 note 的设置方法。
	 * 
	 * @param note
	 *            属性 note 的新值。
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 属性 status 的获取方法。
	 * 
	 * @return 属性 status 的值。
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 属性 status 的设置方法。
	 * 
	 * @param status
	 *            属性 status 的新值。
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性 title 的获取方法。
	 * 
	 * @return 属性 title 的值。
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 属性 title 的设置方法。
	 * 
	 * @param title
	 *            属性 title 的新值。
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public abstract List getPaperQuestion() throws PlatformException;

	public abstract void addPaperQuestion(List PaperQuestion)
			throws PlatformException;

	public abstract void removePaperQuestion(List PaperQuestion)
			throws PlatformException;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract int removePaperQuestions() throws PlatformException;

	public abstract int removePaperQuestions(String questionIds)
			throws PlatformException;

	public abstract int setActive();

	public abstract int cancelActive();

	public abstract int reverseActive();
}
