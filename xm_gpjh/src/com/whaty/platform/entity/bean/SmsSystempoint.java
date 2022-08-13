package com.whaty.platform.entity.bean;

/**
 * SmsSystempoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SmsSystempoint extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String content;
	private String flagIsvalid;
	private String flagBak;

	// Constructors

	/** default constructor */
	public SmsSystempoint() {
	}

	/** minimal constructor */
	public SmsSystempoint(String name, String content, String flagIsvalid) {
		this.name = name;
		this.content = content;
		this.flagIsvalid = flagIsvalid;
	}

	/** full constructor */
	public SmsSystempoint(String name, String content, String flagIsvalid,
			String flagBak) {
		this.name = name;
		this.content = content;
		this.flagIsvalid = flagIsvalid;
		this.flagBak = flagBak;
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

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}