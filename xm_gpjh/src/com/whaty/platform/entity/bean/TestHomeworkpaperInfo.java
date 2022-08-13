package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestHomeworkpaperInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestHomeworkpaperInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String creatuser;
	private Date creatdate;
	private String status;
	private String note;
	private String comments;
	private String startdate;
	private String enddate;
	private String groupId;
	private String type;

	// Constructors

	/** default constructor */
	public TestHomeworkpaperInfo() {
	}

	/** minimal constructor */
	public TestHomeworkpaperInfo(String title, String creatuser,
			Date creatdate, String status, String startdate, String enddate,
			String groupId) {
		this.title = title;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.status = status;
		this.startdate = startdate;
		this.enddate = enddate;
		this.groupId = groupId;
	}

	/** full constructor */
	public TestHomeworkpaperInfo(String title, String creatuser,
			Date creatdate, String status, String note, String comments,
			String startdate, String enddate, String groupId, String type) {
		this.title = title;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.status = status;
		this.note = note;
		this.comments = comments;
		this.startdate = startdate;
		this.enddate = enddate;
		this.groupId = groupId;
		this.type = type;
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}