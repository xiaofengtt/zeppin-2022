package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.ActivityInfoBuyfreeGoods;

public class ActivityInfoBuyfreeGoodsVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8378895631300827318L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String goodsId;
	private String goodsCover;
	private String goodsCoverUrl;
	private BigDecimal goodsPrice;
	private String goodsTitle;
	private String goodsShortTitle;
	private Integer shares;
	private Integer currentIssueNum;
	private Integer sort;
	

	public ActivityInfoBuyfreeGoodsVO(ActivityInfoBuyfreeGoods aibg) {
		this.uuid = aibg.getUuid();
		this.activityInfo = aibg.getActivityInfo();
		this.status = aibg.getActivityInfo();
		this.createtime = aibg.getCreatetime();
		this.creator = aibg.getCreator();
		this.goodsId = aibg.getGoodsId();
		this.goodsCover = aibg.getGoodsCover();
		this.goodsPrice = aibg.getGoodsPrice();
		this.goodsTitle = aibg.getGoodsTitle();
		this.goodsShortTitle = aibg.getGoodsShortTitle();
		this.shares = aibg.getShares();
		this.currentIssueNum = aibg.getCurrentIssueNum();
		this.sort = aibg.getSort();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public Integer getCurrentIssueNum() {
		return currentIssueNum;
	}

	public void setCurrentIssueNum(Integer currentIssueNum) {
		this.currentIssueNum = currentIssueNum;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getGoodsCoverUrl() {
		return goodsCoverUrl;
	}

	public void setGoodsCoverUrl(String goodsCoverUrl) {
		this.goodsCoverUrl = goodsCoverUrl;
	}
}