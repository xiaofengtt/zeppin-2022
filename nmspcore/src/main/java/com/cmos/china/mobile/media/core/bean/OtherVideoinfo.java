package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class OtherVideoinfo extends Entity {
	
	private static final long serialVersionUID = 8951712361898553951L;
	private String id;
	private String title;
	private String context;
	private String tag;
	private String status;
	private String thumbnail;
	private String url;
	private String length;
	private Boolean transcodingFlag;
	private String resource;
	private String source;
	private String copyright;
	private String author;
	private String owner;
	private Timestamp createtime;
	private Integer sequence;


	
	public class VideoStatusType implements StatusType {
		public final static String DELETED = "deleted";
		public final static String UPLOADED = "uploaded";
		public final static String TRANSCODING = "transcoding";
		public final static String FAILED = "failed";
		public final static String NORMAL = "normal";
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Boolean getTranscodingFlag() {
		return transcodingFlag;
	}

	public void setTranscodingFlag(Boolean transcodingFlag) {
		this.transcodingFlag = transcodingFlag;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

