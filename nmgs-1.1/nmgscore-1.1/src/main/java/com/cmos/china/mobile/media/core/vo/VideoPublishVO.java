package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.Utlity;

public class VideoPublishVO implements Serializable{
	
	private static final long serialVersionUID = -1247936478228611371L;
	private String id;
	private String title;
	private String shortTitle;
	private String cover;
	private String coverURL;
	private String category;
	private String categoryName;
	private String creator;
	private Timestamp createtime;
	private String createtimeCN;
	private String status;
	private String statusCN;
	private Integer sequence;
	
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
	public String getShortTitle() {
		return shortTitle;
	}
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	public String getCoverURL() {
		return coverURL;
	}
	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
		this.createtimeCN = Utlity.timeSpanToString(createtime);
	}
	public String getCreatetimeCN() {
		return createtimeCN;
	}
	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		this.statusCN = this.getStatusCN(status);
	}
	public String getStatusCN(String status) {
		if("unchecked".equals(status)){
			return "?????????";
		}else if("uploaded".equals(status)){
			return "?????????";
		}else if("transcoding".equals(status)){
			return "?????????";
		}else if("failed".equals(status)){
			return "????????????";
		}else if("checked".equals(status)){
			return "?????????";
		}else if("deleted".equals(status)){
			return "?????????";
		}else{
			return "";
		}
	}
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	
	public String getStatusCN() {
		return statusCN;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
}
