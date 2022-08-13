package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class VideoPublish extends Entity {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String video;
	private String category;
	private String status;
	private String title;
	private String shortTitle;
	private String cover;
	private String creator;
	private Timestamp createtime;

	public VideoPublish() {
		super();
	}

	public VideoPublish(String id, String video, String category,
			String status, String title, String shortTitle, String cover,
			String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.video = video;
		this.category = category;
		this.status = status;
		this.title = title;
		this.shortTitle = shortTitle;
		this.cover = cover;
		this.creator = creator;
		this.createtime = createtime;
	}

	public class VideoPublishStatusType implements StatusType {
		public final static String DELETED = "deleted";
		public final static String UNCHECKED = "unchecked";
		public final static String CHECKED = "checked";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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

