package com.cmos.china.mobile.media.core.vo.iface;


import java.math.BigDecimal;
import java.util.List;

public class WebCommodityVO {
	
	private String name;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private String commodityURL;
	private List<WebCommodityDisplayVO> webCommodityDisplays;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCommodityURL() {
		return commodityURL;
	}
	public void setCommodityURL(String commodityURL) {
		this.commodityURL = commodityURL;
	}
	public List<WebCommodityDisplayVO> getWebCommodityDisplays() {
		return webCommodityDisplays;
	}
	public void setWebCommodityDisplays(
			List<WebCommodityDisplayVO> webCommodityDisplays) {
		this.webCommodityDisplays = webCommodityDisplays;
	}
}
