package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.Banner;

public class BannerVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4317771565403722873L;
	private String uuid;
	private String type;
	private String title;
	private String code;
	private String image;
	private String imageUrl;
	private String url;
	private String status;
	
	private Timestamp starttime;
	private Timestamp endtime;
	private Integer sort;
	private String frontUserLevel;
	private Timestamp refreshtime;
	
	public BannerVO(){
		
	}
	
	public BannerVO(Banner banner){
		this.uuid = banner.getUuid();
		this.type = banner.getType();
		this.status = banner.getStatus();
		this.title = banner.getTitle();
		this.code = banner.getCode();
		this.image = banner.getImage();
		this.url = banner.getUrl();
		this.status = banner.getStatus();
		this.starttime = banner.getStarttime();
		this.endtime = banner.getEndtime();
		this.sort = banner.getSort();
		this.frontUserLevel = banner.getFrontUserLevel();
		this.refreshtime = banner.getRefreshtime();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFrontUserLevel() {
		return frontUserLevel;
	}

	public void setFrontUserLevel(String frontUserLevel) {
		this.frontUserLevel = frontUserLevel;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getRefreshtime() {
		return refreshtime;
	}

	public void setRefreshtime(Timestamp refreshtime) {
		this.refreshtime = refreshtime;
	}
}
