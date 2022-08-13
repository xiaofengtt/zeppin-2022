package cn.zeppin.project.chinamobile.media.web.vo;

import java.sql.Timestamp;

import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;
import cn.zeppin.project.chinamobile.media.utility.Utlity;

public class VideoIframeVO extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
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

