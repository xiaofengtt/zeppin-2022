package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class NoticePublic implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 779628728347452257L;
	
	@Id
	private String uuid;
	private String type;
	private String title;
	private String details;
	
	private Timestamp onlinetime;
	private String status;
	
	private Timestamp createtime;
	private String creator;
	
	public class NoticePublicType {
		public static final String ACTIVITY = "activity";//活动
		public static final String PLATFORM = "platform";//平台
	}
	
	public class NoticePublicStatus {
		public static final String ONLINE = "online";//上线
		public static final String OFFLINE = "offline";//下线
		public static final String DELETE = "delete";//删除
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Timestamp getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(Timestamp onlinetime) {
		this.onlinetime = onlinetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}