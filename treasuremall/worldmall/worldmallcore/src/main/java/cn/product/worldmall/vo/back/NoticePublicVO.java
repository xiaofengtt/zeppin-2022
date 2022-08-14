package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.NoticePublic;

public class NoticePublicVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1094455258682702095L;
	private String uuid;
	private String type;
	private String title;
	private String details;
	
	private Timestamp onlinetime;
	private String status;
	
	private Timestamp createtime;
	private String creator;
	private String creatorCN;
	
	public NoticePublicVO(NoticePublic np) {
		this.uuid = np.getUuid();
		this.type = np.getType();
		this.title = np.getTitle();
		this.details = np.getDetails();
		this.onlinetime = np.getOnlinetime();
		this.status = np.getStatus();
		this.createtime = np.getCreatetime();
		this.creator = np.getCreator();
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

	public String getCreatorCN() {
		return creatorCN;
	}

	public void setCreatorCN(String creatorCN) {
		this.creatorCN = creatorCN;
	}
}