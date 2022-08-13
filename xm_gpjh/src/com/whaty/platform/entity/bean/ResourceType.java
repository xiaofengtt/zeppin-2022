package com.whaty.platform.entity.bean;

/**
 * ResourceType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ResourceType extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String note;
	private String status;
	private String xml;

	// Constructors

	/** default constructor */
	public ResourceType() {
	}

	/** full constructor */
	public ResourceType(String name, String note, String status, String xml) {
		this.name = name;
		this.note = note;
		this.status = status;
		this.xml = xml;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}