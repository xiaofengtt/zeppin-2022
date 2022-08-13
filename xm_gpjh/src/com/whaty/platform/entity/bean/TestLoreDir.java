package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TestLoreDir entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestLoreDir extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String note;
	private String fatherdir;
	private Date creatdate;
	private String groupId;
	private Set testLoreInfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public TestLoreDir() {
	}

	/** minimal constructor */
	public TestLoreDir(String fatherdir) {
		this.fatherdir = fatherdir;
	}

	/** full constructor */
	public TestLoreDir(String name, String note, String fatherdir,
			Date creatdate, String groupId, Set testLoreInfos) {
		this.name = name;
		this.note = note;
		this.fatherdir = fatherdir;
		this.creatdate = creatdate;
		this.groupId = groupId;
		this.testLoreInfos = testLoreInfos;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFatherdir() {
		return this.fatherdir;
	}

	public void setFatherdir(String fatherdir) {
		this.fatherdir = fatherdir;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Set getTestLoreInfos() {
		return this.testLoreInfos;
	}

	public void setTestLoreInfos(Set testLoreInfos) {
		this.testLoreInfos = testLoreInfos;
	}

}