package com.cmos.china.mobile.media.core.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Commodity extends Entity {

	private static final long serialVersionUID = 377829351023598212L;
	private String id;
	private String province;
	private String url;
	private String name;
	private String cover;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private String status;
	private String creator;
	private Timestamp createtime;

	public Commodity() {
		super();
	}

	public Commodity(String id, String province, String url, String name, String cover,
			BigDecimal price, BigDecimal originalPrice, String status,
			String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.province = province;
		this.url = url;
		this.name = name;
		this.cover = cover;
		this.price = price;
		this.originalPrice = originalPrice;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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
