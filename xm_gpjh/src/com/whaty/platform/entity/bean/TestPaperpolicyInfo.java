package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestPaperpolicyInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestPaperpolicyInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String policycore;
	private String creatuser;
	private Date creatdate;
	private String status;
	private String note;
	private String testtime;
	private String groupId;
	private String type;

	// Constructors

	/** default constructor */
	public TestPaperpolicyInfo() {
	}

	/** minimal constructor */
	public TestPaperpolicyInfo(String title, String policycore,
			String creatuser, Date creatdate, String status) {
		this.title = title;
		this.policycore = policycore;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.status = status;
	}

	/** full constructor */
	public TestPaperpolicyInfo(String title, String policycore,
			String creatuser, Date creatdate, String status, String note,
			String testtime, String groupId, String type) {
		this.title = title;
		this.policycore = policycore;
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.status = status;
		this.note = note;
		this.testtime = testtime;
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

	public String getPolicycore() {
		return this.policycore;
	}

	public void setPolicycore(String policycore) {
		this.policycore = policycore;
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

	public String getTesttime() {
		return this.testtime;
	}

	public void setTesttime(String testtime) {
		this.testtime = testtime;
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