package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class ProvinceModule extends Entity {
	
	private static final long serialVersionUID = 5703096467321125198L;
	private String id;
	private String province;
	private String module;
	private String title;
	private String content;
	private String url;
	private String image;
	private Integer priority;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public ProvinceModule() {
		super();
	}

	public ProvinceModule(String id, String province, String module, String title, String url, String content,
			String image, Integer priority, String status, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.province = province;
		this.module = module;
		this.title = title;
		this.url = url;
		this.content = content;
		this.image = image;
		this.priority = priority;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
