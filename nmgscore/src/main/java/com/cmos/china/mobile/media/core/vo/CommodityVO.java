package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.Utlity;

public class CommodityVO implements Serializable{
	
	private static final long serialVersionUID = 1763447739574823908L;
	private String id;
	private String name;
	private String cover;
	private String coverURL;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private String creator;
	private Timestamp createtime;
	private String createtimeCN;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoverURL() {
		return coverURL;
	}
	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	

	
}
