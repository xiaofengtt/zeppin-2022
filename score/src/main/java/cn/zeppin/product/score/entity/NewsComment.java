package cn.zeppin.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class NewsComment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2086135017931566361L;
	
	@Id
	private String uuid;
	private String newspublish;
	private String parent;
	private String content;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class NewsCommentStatus{
		public final static String CONFIRM = "confirm";
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNewspublish() {
		return newspublish;
	}

	public void setNewspublish(String newspublish) {
		this.newspublish = newspublish;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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