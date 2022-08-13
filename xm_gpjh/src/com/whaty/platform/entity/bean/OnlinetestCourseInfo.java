package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * OnlinetestCourseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OnlinetestCourseInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String groupId;
	private String batchId;
	private String note;
	private String startDate;
	private String endDate;
	private String isautocheck;
	private String ishiddenanswer;
	private String title;
	private String status;
	private String creatuser;
	private Date creatdate;

	// Constructors

	/** default constructor */
	public OnlinetestCourseInfo() {
	}

	/** minimal constructor */
	public OnlinetestCourseInfo(String note, String startDate, String endDate,
			String isautocheck, String ishiddenanswer) {
		this.note = note;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isautocheck = isautocheck;
		this.ishiddenanswer = ishiddenanswer;
	}

	/** full constructor */
	public OnlinetestCourseInfo(String groupId, String batchId, String note,
			String startDate, String endDate, String isautocheck,
			String ishiddenanswer, String title, String status,
			String creatuser, Date creatdate) {
		this.groupId = groupId;
		this.batchId = batchId;
		this.note = note;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isautocheck = isautocheck;
		this.ishiddenanswer = ishiddenanswer;
		this.title = title;
		this.status = status;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsautocheck() {
		return this.isautocheck;
	}

	public void setIsautocheck(String isautocheck) {
		this.isautocheck = isautocheck;
	}

	public String getIshiddenanswer() {
		return this.ishiddenanswer;
	}

	public void setIshiddenanswer(String ishiddenanswer) {
		this.ishiddenanswer = ishiddenanswer;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}