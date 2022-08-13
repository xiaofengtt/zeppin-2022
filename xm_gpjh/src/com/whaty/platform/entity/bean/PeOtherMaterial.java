package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeOtherMaterial entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeOtherMaterial extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeProApplyno peProApplyno;
	private PeUnit peUnit;
	private String materialName;
	private String materialFile;
	private Date uploadDate;

	// Constructors

	/** default constructor */
	public PeOtherMaterial() {
	}

	/** full constructor */
	public PeOtherMaterial(PeProApplyno peProApplyno, PeUnit peUnit,
			String materialName, String materialFile, Date uploadDate) {
		this.peProApplyno = peProApplyno;
		this.peUnit = peUnit;
		this.materialName = materialName;
		this.materialFile = materialFile;
		this.uploadDate = uploadDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialFile() {
		return this.materialFile;
	}

	public void setMaterialFile(String materialFile) {
		this.materialFile = materialFile;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}