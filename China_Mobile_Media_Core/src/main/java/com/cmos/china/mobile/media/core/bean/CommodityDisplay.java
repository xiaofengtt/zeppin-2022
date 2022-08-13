package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class CommodityDisplay extends Entity {

	private static final long serialVersionUID = 1L;
	private String id;
	private String commodity;
	private Integer displayIndex;
	private String displayImage;
	private String creator;
	private Timestamp createtime;
	
	public CommodityDisplay() {
		super();
	}
	
	public CommodityDisplay(String id, String commodity, Integer displayIndex,
			String displayImage, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.commodity = commodity;
		this.displayIndex = displayIndex;
		this.displayImage = displayImage;
		this.creator = creator;
		this.createtime = createtime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	public Integer getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	
	public String getDisplayImage() {
		return displayImage;
	}
	public void setDisplayImage(String displayImage) {
		this.displayImage = displayImage;
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
