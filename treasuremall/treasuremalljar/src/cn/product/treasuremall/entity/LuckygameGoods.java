package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



public class LuckygameGoods implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5048749763128286459L;
	
	
	private String uuid;
	private String goodsId;
	private String goodsType;
	private String gameType;
	private String title;
	private String shortTitle;
	private BigDecimal dPrice;
	private BigDecimal profitRate;
	private BigDecimal betPerShare;
	
	private Integer shares;
	private Integer totalIssueNum;
	private Integer currentIssueNum;
	
	private String status;
	private Integer sort;
	
	private Boolean promotionFlag;
	private Timestamp createtime;
	private String creator;
	
	private String tabs;
	
	public class LuckygameGoodsStatus {
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
		
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortTitle() {
		return shortTitle;
	}
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	public BigDecimal getdPrice() {
		return dPrice;
	}
	public void setdPrice(BigDecimal dPrice) {
		this.dPrice = dPrice;
	}
	public BigDecimal getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}
	public BigDecimal getBetPerShare() {
		return betPerShare;
	}
	public void setBetPerShare(BigDecimal betPerShare) {
		this.betPerShare = betPerShare;
	}
	public Integer getShares() {
		return shares;
	}
	public void setShares(Integer shares) {
		this.shares = shares;
	}
	public Integer getTotalIssueNum() {
		return totalIssueNum;
	}
	public void setTotalIssueNum(Integer totalIssueNum) {
		this.totalIssueNum = totalIssueNum;
	}
	public Integer getCurrentIssueNum() {
		return currentIssueNum;
	}
	public void setCurrentIssueNum(Integer currentIssueNum) {
		this.currentIssueNum = currentIssueNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Boolean getPromotionFlag() {
		return promotionFlag;
	}
	public void setPromotionFlag(Boolean promotionFlag) {
		this.promotionFlag = promotionFlag;
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
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getTabs() {
		return tabs;
	}
	public void setTabs(String tabs) {
		this.tabs = tabs;
	} 
}