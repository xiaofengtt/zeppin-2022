package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.NoticeTopicMessage;

public class NoticeTopicMessageVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -133095154062965865L;
	private String uuid;
	private String title;
	private String content;
	private String noticeTopic;
	
	private String type;
	private String status;
	private Timestamp createtime;
	private Timestamp sendtime;
	
	private String displayName;
	
	public NoticeTopicMessageVO(NoticeTopicMessage ntm) {
		this.uuid = ntm.getUuid();
		this.title = ntm.getTitle();
		this.content = ntm.getContent();
		this.noticeTopic = ntm.getNoticeTopic();
		this.type = ntm.getType();
		this.status = ntm.getStatus();
		this.createtime = ntm.getCreatetime();
		this.sendtime = ntm.getSendtime();
		
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

	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}