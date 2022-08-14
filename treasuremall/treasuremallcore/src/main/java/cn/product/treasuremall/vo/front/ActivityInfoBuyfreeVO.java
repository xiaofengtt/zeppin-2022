package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.ActivityInfoBuyfree;

public class ActivityInfoBuyfreeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3831791308484675849L;
	
	private String uuid;
	private String activityInfo;
	private String activityInfoBuyfreeGoods;
	private String status;//抽奖状态betting/lottery/finished跟抽奖玩法一样的
	private Timestamp createtime;
	private String goodsId;//商品ID
	private String goodsCover;//商品封面
	private BigDecimal goodsPrice;//商品价格（元）
	private String goodsTitle;//商品名称
	private String goodsShortTitle;//商品简称
	private Integer issueNum;//当前期号
	private Integer shares;//总参与次数
	private Integer betShares;//已参与次数
	private Integer remainShares;//剩余参与次数
	private Integer sort;//排序
	private Timestamp lotterytime;
	private Timestamp finishedtime;
	private Integer luckyNumber;
	
	private String goodsCoverUrl;//奖品封面链接
	private String activityInfoBuyfreeGoodsStatus;
	
	private Integer currentIssueNum;//进行的当前期数
	private String currentIssueUuid;//当前进行中的抽奖期数ID
	private Long timeLine;//抽奖倒计时--无用
	
	private Boolean isFirstBuy;//当日是否已参与过此商品抽奖
	
	public ActivityInfoBuyfreeVO(ActivityInfoBuyfree aib) {
		this.uuid = aib.getUuid();
		this.activityInfo = aib.getActivityInfo();
		this.activityInfoBuyfreeGoods = aib.getActivityInfoBuyfreeGoods();
		this.status = aib.getStatus();
		this.createtime = aib.getCreatetime();
		this.goodsId = aib.getGoodsId();
		this.goodsCover = aib.getGoodsCover();
		this.goodsPrice = aib.getGoodsPrice();
		this.goodsTitle = aib.getGoodsTitle();
		this.goodsShortTitle = aib.getGoodsShortTitle();
		this.shares = aib.getShares();
		this.issueNum = aib.getIssueNum();
		this.sort = aib.getSort();
		this.betShares = aib.getBetShares();
		this.remainShares = aib.getRemainShares();
		this.lotterytime = aib.getLotterytime();
		this.finishedtime = aib.getFinishedtime();
		this.luckyNumber = aib.getLuckyNumber();
		this.isFirstBuy = true;
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

	public String getActivityInfoBuyfreeGoods() {
		return activityInfoBuyfreeGoods;
	}

	public void setActivityInfoBuyfreeGoods(String activityInfoBuyfreeGoods) {
		this.activityInfoBuyfreeGoods = activityInfoBuyfreeGoods;
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

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getGoodsCoverUrl() {
		return goodsCoverUrl;
	}

	public void setGoodsCoverUrl(String goodsCoverUrl) {
		this.goodsCoverUrl = goodsCoverUrl;
	}

	public Integer getCurrentIssueNum() {
		return currentIssueNum;
	}

	public void setCurrentIssueNum(Integer currentIssueNum) {
		this.currentIssueNum = currentIssueNum;
	}

	public String getCurrentIssueUuid() {
		return currentIssueUuid;
	}

	public void setCurrentIssueUuid(String currentIssueUuid) {
		this.currentIssueUuid = currentIssueUuid;
	}

	public Boolean getIsFirstBuy() {
		return isFirstBuy;
	}

	public void setIsFirstBuy(Boolean isFirstBuy) {
		this.isFirstBuy = isFirstBuy;
	}

	public String getActivityInfoBuyfreeGoodsStatus() {
		return activityInfoBuyfreeGoodsStatus;
	}

	public void setActivityInfoBuyfreeGoodsStatus(String activityInfoBuyfreeGoodsStatus) {
		this.activityInfoBuyfreeGoodsStatus = activityInfoBuyfreeGoodsStatus;
	}

	public Long getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(Long timeLine) {
		this.timeLine = timeLine;
	}
}