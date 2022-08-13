package com.cmos.chinamobile.media.web.vo.iface;


import java.math.BigDecimal;
import java.util.List;

public class WebCommodityVO {
	
	private String name;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private String commodityURL;
	private List<WebCommodityVO> webCommodityDisplays;
	
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
	public List<WebCommodityVO> getWebCommodityDisplays() {
		return webCommodityDisplays;
	}
	public void setWebCommodityDisplays(
			List<WebCommodityVO> webCommodityDisplays) {
		this.webCommodityDisplays = webCommodityDisplays;
	}
	
	
	
//	private String id;
//	private String title;
//	private String coverURL;
//	private String videoURL;
//	private String videoPath;
//	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getCoverURL() {
//		return coverURL;
//	}
//	public void setCoverURL(String coverURL) {
//		this.coverURL = coverURL;
//	}
//	public String getVideoURL() {
//		return videoURL;
//	}
//	public void setVideoURL(String videoURL) {
//		this.videoURL = videoURL;
//	}
//	public String getVideoPath() {
//		return videoPath;
//	}
//	public void setVideoPath(String videoPath) {
//		this.videoPath = videoPath;
//	}
//	
}
