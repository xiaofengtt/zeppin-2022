package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



public class LuckygameGoodsIssue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8623799643918680757L;
	
	
	private String uuid;
	private String luckygameGoods;
	private String goodsId;
	private String goodsType;
	private String gameType;
	private String title;
	private String shortTitle;
	private BigDecimal dPrice;
	private BigDecimal profitRate;
	private BigDecimal betPerShare;
	
	private Integer shares;
	private Integer issueNum;
	private Integer sort;
	private Boolean promotionFlag;
	
	private Integer betShares;
	private Integer remainShares;
	
	private String status;
	
	private Timestamp createtime;
	private Timestamp lotterytime;
	private Timestamp finishedtime;
	private Integer luckyNumber;
	
	public class LuckygameGoodsIssueStatus {
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
	public String getLuckygameGoods() {
		return luckygameGoods;
	}
	public void setLuckygameGoods(String luckygameGoods) {
		this.luckygameGoods = luckygameGoods;
	}
	public Integer getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
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
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
}