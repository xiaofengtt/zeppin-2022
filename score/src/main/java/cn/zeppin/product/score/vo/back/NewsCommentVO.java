package cn.zeppin.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.zeppin.product.score.entity.NewsComment;

public class NewsCommentVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2721718695964466556L;
	
	private String uuid;
	private String newspublish;
	private String newsTitle;
	private NewsCommentVO parent;
	private String content;
	private String status;
	private String creator;
	private String creatorName;
	private String creatorMobile;
	private Timestamp createtime;
	
	public NewsCommentVO(NewsComment nc){
		this.uuid = nc.getUuid();
		this.newspublish = nc.getNewspublish();
		this.content = nc.getContent();
		this.status = nc.getStatus();
		this.creator = nc.getCreator();
		this.createtime = nc.getCreatetime();
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

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public NewsCommentVO getParent() {
		return parent;
	}

	public void setParent(NewsCommentVO parent) {
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreatorMobile() {
		return creatorMobile;
	}

	public void setCreatorMobile(String creatorMobile) {
		this.creatorMobile = creatorMobile;
	}
}