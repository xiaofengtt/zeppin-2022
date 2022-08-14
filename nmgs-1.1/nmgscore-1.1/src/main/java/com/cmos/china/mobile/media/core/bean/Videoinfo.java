package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class Videoinfo extends Entity {

	private static final long serialVersionUID = -2690805028405263533L;
	private String id;
	private String province;
	private String title;
	private String context;
	private String tag;
	private String status;
	private String thumbnail;
	private String video;
	private String timeLength;
	private Boolean transcodingFlag;
	private String originalVideo;
	private String source;
	private String copyright;
	private String author;
	private String creator;
	private Timestamp createtime;
	private Integer sequence;
	
	public class VideoStatusType implements StatusType {
		public final static String DELETED = "deleted";
		public final static String UPLOADED = "uploaded";
		public final static String TRANSCODING = "transcoding";
		public final static String UNCHECKED = "unchecked";
		public final static String FAILED = "failed";
		public final static String CHECKED = "checked";
		public final static String DESTROY = "destroy";
	}

	public Videoinfo() {
		super();
	}

	public Videoinfo(String id, String province, String title, String context, String tag,
			String status, String thumbnail, String video, String timeLength,
			Boolean transcodingFlag, String originalVideo, String source,
			String copyright, String author, String creator,
			Timestamp createtime,Integer sequence) {
		super();
		this.id = id;
		this.province = province;
		this.title = title;
		this.context = context;
		this.tag = tag;
		this.status = status;
		this.thumbnail = thumbnail;
		this.video = video;
		this.timeLength = timeLength;
		this.transcodingFlag = transcodingFlag;
		this.originalVideo = originalVideo;
		this.source = source;
		this.copyright = copyright;
		this.author = author;
		this.creator = creator;
		this.createtime = createtime;
		this.sequence = sequence;
	}

	public String getId() {
		return id;
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
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}

	public Boolean getTranscodingFlag() {
		return transcodingFlag;
	}

	public void setTranscodingFlag(Boolean transcodingFlag) {
		this.transcodingFlag = transcodingFlag;
	}

	public String getOriginalVideo() {
		return originalVideo;
	}

	public void setOriginalVideo(String originalVideo) {
		this.originalVideo = originalVideo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}

