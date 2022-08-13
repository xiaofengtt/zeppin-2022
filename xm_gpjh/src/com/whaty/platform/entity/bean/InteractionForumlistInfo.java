package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionForumlistInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionForumlistInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String content;
	private Date createDate;
	private String num;
	private String courseId;
	private String isbrowse;
	private String isspeak;
	private String groupType;
	private String owners;

	// Constructors

	/** default constructor */
	public InteractionForumlistInfo() {
	}

	/** full constructor */
	public InteractionForumlistInfo(String name, String content,
			Date createDate, String num, String courseId, String isbrowse,
			String isspeak, String groupType, String owners) {
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.num = num;
		this.courseId = courseId;
		this.isbrowse = isbrowse;
		this.isspeak = isspeak;
		this.groupType = groupType;
		this.owners = owners;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getIsbrowse() {
		return this.isbrowse;
	}

	public void setIsbrowse(String isbrowse) {
		this.isbrowse = isbrowse;
	}

	public String getIsspeak() {
		return this.isspeak;
	}

	public void setIsspeak(String isspeak) {
		this.isspeak = isspeak;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getOwners() {
		return this.owners;
	}

	public void setOwners(String owners) {
		this.owners = owners;
	}

}