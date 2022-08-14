package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class NoticeTopic implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3197899227206531686L;
	
	@Id
	private String uuid;
	private String type;
	private String displayName;
	private String topicArn;
	
	private String status;
	private Timestamp createtime;
	
	public class NoticeTopicType {
		public static final String TOPIC_DEFAULT = "topic_default";//标准类型
		public static final String TOPIC_FIFO = "topic_fifo";//先进先出类型--sqs专用
	}
	
	public class NoticeTopicStatus {
		public static final String NORMAL = "normal";
		public static final String DISABLE = "disable";
		public static final String DELETE = "delete";
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTopicArn() {
		return topicArn;
	}

	public void setTopicArn(String topicArn) {
		this.topicArn = topicArn;
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
}