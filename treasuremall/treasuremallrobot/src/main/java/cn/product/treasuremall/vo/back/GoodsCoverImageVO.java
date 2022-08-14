package cn.product.treasuremall.vo.back;

import java.io.Serializable;

import cn.product.treasuremall.entity.GoodsCoverImage;

public class GoodsCoverImageVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028997314692384013L;
	
	private String uuid;
	private String belongs;
	private String type;
	private String image;
	private String imageUrl;
	private Integer sort;
	
	public GoodsCoverImageVO(GoodsCoverImage gci){
		this.uuid = gci.getUuid();
		this.belongs = gci.getBelongs();
		this.type = gci.getType();
		this.image = gci.getImage();
		this.sort = gci.getSort();
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}