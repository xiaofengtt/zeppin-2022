package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestExampaperInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestExampaperInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String creatuser;
	private Date creatdate;
	private String status;
	private String note;
	private String time;
	private String type;
	private String groupId;

	// Constructors

	/** default constructor */
	public TestExampaperInfo() {
	}

	/** full constructor */
	public TestExampaperInfo(String title, String creatuser, Date creatdate,
			String status, String note, String time, String type, String groupId) {
		this.title = title;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.status = status;
		this.note = note;
		this.time = time;
		this.type = type;
		this.groupId = groupId;
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

	public String getCreatuser() {
		return this.creatuser;
	}

	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}