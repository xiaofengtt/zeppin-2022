package com.whaty.platform.entity.bean;

/**
 * ResourceInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ResourceInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String language;
	private String description;
	private String keywords;
	private String creatuser;
	private String typeId;
	private String dirId;
	private String content;
	private String detail;
	private String status;

	// Constructors

	/** default constructor */
	public ResourceInfo() {
	}

	/** full constructor */
	public ResourceInfo(String title, String language, String description,
			String keywords, String creatuser, String typeId, String dirId,
			String content, String detail, String status) {
		this.title = title;
		this.language = language;
		this.description = description;
		this.keywords = keywords;
		this.creatuser = creatuser;
		this.typeId = typeId;
		this.dirId = dirId;
		this.content = content;
		this.detail = detail;
		this.status = status;
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

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCreatuser() {
		return this.creatuser;
	}

	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getDirId() {
		return this.dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}