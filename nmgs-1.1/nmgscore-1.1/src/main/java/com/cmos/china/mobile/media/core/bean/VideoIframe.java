package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class VideoIframe extends Entity {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String video;
	private String timepoint;
	private String path;
	private Timestamp createtime;
	
	public VideoIframe() {
		super();
	}

	public VideoIframe(String id, String video, String timepoint, String path,
			Timestamp createtime) {
		super();
		this.id = id;
		this.video = video;
		this.timepoint = timepoint;
		this.path = path;
		this.createtime = createtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}

