package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

/**
 * 留言信息bean
 * @author zeppin
 *
 */
@SuppressWarnings("serial")
public class LeaveMessage extends Entity {

	private String id;
	private String video_publish;
	private String content;
	private String status;
	private String auditor;
	private String creator;
	private Timestamp createtime;
	
	public class LeaveMessageStatusType implements StatusType {
		public final static String DELETED = "deleted";
		public final static String UNCHECKED = "unchecked";
		public final static String CHECKED = "checked";
		public final static String NOPASS = "nopass";
	}
	
	public LeaveMessage() {
		super();
	}

	public LeaveMessage(String id, String video_publish, String content,
			String status, String auditor, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.video_publish = video_publish;
		this.content = content;
		this.status = status;
		this.auditor = auditor;
		this.creator = creator;
		this.createtime = createtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVideo_publish() {
		return video_publish;
	}

	public void setVideo_publish(String video_publish) {
		this.video_publish = video_publish;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
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
