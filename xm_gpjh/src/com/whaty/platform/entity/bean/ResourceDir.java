package com.whaty.platform.entity.bean;

/**
 * ResourceDir entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ResourceDir extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String parent;
	private String note;
	private String status;
	private String isinherit;
	private String keyid;

	// Constructors

	/** default constructor */
	public ResourceDir() {
	}

	/** full constructor */
	public ResourceDir(String name, String parent, String note, String status,
			String isinherit, String keyid) {
		this.name = name;
		this.parent = parent;
		this.note = note;
		this.status = status;
		this.isinherit = isinherit;
		this.keyid = keyid;
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

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsinherit() {
		return this.isinherit;
	}

	public void setIsinherit(String isinherit) {
		this.isinherit = isinherit;
	}

	public String getKeyid() {
		return this.keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

}