package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ActivityInfoBuyfree implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9220540621819373235L;
	
	private String uuid;
	private String activityInfo;
	private String activityInfoBuyfreeGoods;
	private String status;
	private Timestamp createtime;
	private String goodsId;
	private String goodsCover;
	private BigDecimal goodsPrice;
	private String goodsTitle;
	private String goodsShortTitle;
	private Integer issueNum;
	private Integer shares;
	private Integer betShares;
	private Integer remainShares;
	private Integer sort;
	private Timestamp lotterytime;
	private Timestamp finishedtime;
	private Integer luckyNumber;
	
    public class ActivityInfoBuyfreeStatus{
    	public final static String BETTING = "betting";
		public final static String FINISHED = "finished";
		public final static String LOTTERY = "lottery";
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

	public String getActivityInfoBuyfreeGoods() {
		return activityInfoBuyfreeGoods;
	}

	public void setActivityInfoBuyfreeGoods(String activityInfoBuyfreeGoods) {
		this.activityInfoBuyfreeGoods = activityInfoBuyfreeGoods;
	}

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}

	public Integer getBetShares() {
		return betShares;
	}

	public void setBetShares(Integer betShares) {
		this.betShares = betShares;
	}

	public Integer getRemainShares() {
		return remainShares;
	}

	public void setRemainShares(Integer remainShares) {
		this.remainShares = remainShares;
	}

	public Timestamp getLotterytime() {
		return lotterytime;
	}

	public void setLotterytime(Timestamp lotterytime) {
		this.lotterytime = lotterytime;
	}

	public Timestamp getFinishedtime() {
		return finishedtime;
	}

	public void setFinishedtime(Timestamp finishedtime) {
		this.finishedtime = finishedtime;
	}

	public Integer getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}
}