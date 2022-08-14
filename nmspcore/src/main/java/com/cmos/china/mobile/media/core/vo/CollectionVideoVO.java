package com.cmos.china.mobile.media.core.vo;

import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.Utlity;


public class CollectionVideoVO {
	private String cid;
	private String vid;
	private Timestamp createtime;
	private String createtimeCN;
	private String title;
	private String cover;
	private String video;
	private String videoPath;
	private String categoryid;
	private String timeLength;
	private String clickCount;
	private Integer sequence;

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}
	public String getClickCount() {
		return clickCount;
	}
	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
	}
	
}
