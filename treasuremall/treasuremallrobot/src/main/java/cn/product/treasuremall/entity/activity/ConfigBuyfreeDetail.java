package cn.product.treasuremall.entity.activity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConfigBuyfreeDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1137902578456943825L;
	
	private String uuid;
	private String activityInfo;
	private String type;
	private String goodsId;
	private String goodsCover;
	private BigDecimal goodsPrice;
	private String goodsTitle;
	private String goodsShortTitle;
	private Integer shares;
	private Integer sort;
	
	public class ConfigBuyFreeDetailType{
    	public final static String ADD = "add";
    	public final static String EDIT = "edit";
    	public final static String DELETE = "delete";
    }
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsCover() {
		return goodsCover;
	}
	public void setGoodsCover(String goodsCover) {
		this.goodsCover = goodsCover;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getGoodsShortTitle() {
		return goodsShortTitle;
	}
	public void setGoodsShortTitle(String goodsShortTitle) {
		this.goodsShortTitle = goodsShortTitle;
	}
	public Integer getShares() {
		return shares;
	}
	public void setShares(Integer shares) {
		this.shares = shares;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}