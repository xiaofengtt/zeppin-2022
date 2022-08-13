package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrJobUnit entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrJobUnit extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeUnit peUnit;
	private EnumConst enumConstByFkJobStatus;
	private PeJob peJob;
	private String reply;
	private String uploadFile;
	private String checkNote;
	private String name;
	private Date createDate;

	// Constructors

	/** default constructor */
	public PrJobUnit() {
	}

	/** full constructor */
	public PrJobUnit(PeUnit peUnit, EnumConst enumConst, PeJob peJob,
			String reply, String uploadFile, String checkNote, String name,
			Date createDate) {
		this.peUnit = peUnit;
		this.enumConstByFkJobStatus = enumConst;
		this.peJob = peJob;
		this.reply = reply;
		this.uploadFile = uploadFile;
		this.checkNote = checkNote;
		this.name = name;
		this.createDate = createDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public EnumConst getEnumConstByFkJobStatus() {
		return this.enumConstByFkJobStatus;
	}

	public void setEnumConstByFkJobStatus(EnumConst enumConst) {
		this.enumConstByFkJobStatus = enumConst;
	}

	public PeJob getPeJob() {
		return this.peJob;
	}

	public void setPeJob(PeJob peJob) {
		this.peJob = peJob;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getUploadFile() {
		return this.uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getCheckNote() {
		return this.checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}