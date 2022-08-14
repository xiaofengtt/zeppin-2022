package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Advert;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class AdvertVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uuid;
	private String title;
	private String picture;
	private String pictureUrl;
	private String creator;
	private String creatorRealname;
	private Timestamp createtime;
	private String status;
	private String createDate;

	public AdvertVO() {
		super();
	}

	public AdvertVO(Advert advert) {
		super();
		this.uuid = advert.getUuid();
		this.title = advert.getTitle();
		this.picture = advert.getPicture();
		this.creator = advert.getCreator();
		this.createtime = advert.getCreatetime();
		this.status = advert.getStatus();
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorRealname() {
		return creatorRealname;
	}

	public void setCreatorRealname(String creatorRealname) {
		this.creatorRealname = creatorRealname;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createDate = Utlity.timeSpanToString(createtime);
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
