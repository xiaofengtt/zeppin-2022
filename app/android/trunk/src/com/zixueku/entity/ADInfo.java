package com.zixueku.entity;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * 类说明 活动
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年9月8日 下午3:54:21
 */
public class ADInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	@SerializedName("image.url")
	private String imageUrl;
	private String title;
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", imageUrl=" + imageUrl + ", title=" + title + ", url=" + url + "]";
	}
}
