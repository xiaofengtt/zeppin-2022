package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PePcOpencourseResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcOpencourseResource extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrPcOpencourse prPcOpencourse;
	private String name;
	private String fkUploadTeacherId;
	private String url;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public PePcOpencourseResource() {
	}

	/** full constructor */
	public PePcOpencourseResource(PrPcOpencourse prPcOpencourse, String name,
			String fkUploadTeacherId, String url, Date uploadDate) {
		this.prPcOpencourse = prPcOpencourse;
		this.name = name;
		this.fkUploadTeacherId = fkUploadTeacherId;
		this.url = url;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFkUploadTeacherId() {
		return this.fkUploadTeacherId;
	}

	public void setFkUploadTeacherId(String fkUploadTeacherId) {
		this.fkUploadTeacherId = fkUploadTeacherId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}