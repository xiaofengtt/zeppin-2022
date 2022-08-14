package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class NoticeTopicMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5335455424797203910L;
	
	@Id
	private String uuid;
	private String title;
	private String content;
	private String noticeTopic;
	
	private String type;
	private String status;
	private Timestamp createtime;
	private Timestamp sendtime;
	

	public class NoticeTopicMessageType {
		public final static String SYSTEM_PUBLISH = "system_publish";
	}
	
	public class NoticeTopicMessageStatus {
		public static final String NORMAL = "normal";//待发送
		public static final String FINISHED = "finished";//发送成功
		public static final String FAIL = "fail";//发送失败
		public static final String DELETE = "delete";
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoticeTopic() {
		return noticeTopic;
	}

	public void setNoticeTopic(String noticeTopic) {
		this.noticeTopic = noticeTopic;
	}

	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}