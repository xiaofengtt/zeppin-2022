package cn.product.treasuremall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class GoodsCoverImage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2232197667887454098L;
	
	@Id
	private String uuid;
	private String belongs;
	private String type;
	private String image;
	private Integer sort;
	
	public class GoodsCoverImageType {
		public final static String TYPE_LIST = "type_list";
		public final static String TYPE_DETAIL = "type_detail";
		public final static String TYPE_SHOW = "type_show";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getBelongs() {
		return belongs;
	}

	public void setBelongs(String belongs) {
		this.belongs = belongs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}