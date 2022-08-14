package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.Utlity;

public class VideoIframeVO implements Serializable{
	
	private static final long serialVersionUID = -8849899703894873974L;
	private String id;
	private String video;
	private String timepoint;
	private String path;
	private Timestamp createtime;
	private String createtimeCN;
	
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
		this.createtimeCN = Utlity.timeSpanToString(createtime);
	}
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

}

