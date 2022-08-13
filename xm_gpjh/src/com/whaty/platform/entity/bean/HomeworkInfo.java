package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HomeworkInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class HomeworkInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String creater;
	private Date creatdate;
	private String flagIsvalid;
	private String note;
	private String groupId;
	private Date startdate;
	private Date enddate;
	private String flagBak;
	private Set homeworkHistories = new HashSet(0);

	// Constructors

	/** default constructor */
	public HomeworkInfo() {
	}

	/** full constructor */
	public HomeworkInfo(String title, String creater, Date creatdate,
			String flagIsvalid, String note, String groupId, Date startdate,
			Date enddate, String flagBak, Set homeworkHistories) {
		this.title = title;
		this.creater = creater;
		this.creatdate = creatdate;
		this.flagIsvalid = flagIsvalid;
		this.note = note;
		this.groupId = groupId;
		this.startdate = startdate;
		this.enddate = enddate;
		this.flagBak = flagBak;
		this.homeworkHistories = homeworkHistories;
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

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Set getHomeworkHistories() {
		return this.homeworkHistories;
	}

	public void setHomeworkHistories(Set homeworkHistories) {
		this.homeworkHistories = homeworkHistories;
	}

}