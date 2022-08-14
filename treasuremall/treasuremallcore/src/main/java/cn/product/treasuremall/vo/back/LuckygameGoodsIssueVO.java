package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.LuckygameGoodsIssue;

public class LuckygameGoodsIssueVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3246228708284386377L;
	
	private String uuid;
	private String luckygameGoods;
	private String goodsId;
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
	
	private String luckyGroup;//中奖组信息
	private String tabs;
	
	//中奖人信息
	private String showIdStr;
	private String nickname;
	private BigDecimal dAmount;
	private BigDecimal actualDAmount;
	private Timestamp paymenttime;
	
	private String coverImg;
	private String code;
	
	public LuckygameGoodsIssueVO(LuckygameGoodsIssue lgi) {
		this.uuid = lgi.getUuid();
		this.luckygameGoods = lgi.getLuckygameGoods();
		this.goodsId = lgi.getGoodsId();
		this.gameType = lgi.getGameType();
		this.title = lgi.getTitle();
		this.shortTitle = lgi.getShortTitle();
		this.dPrice = lgi.getdPrice();
		this.profitRate = lgi.getProfitRate();
		this.betPerShare = lgi.getBetPerShare();
		this.shares = lgi.getShares();
		this.issueNum = lgi.getIssueNum();
		this.sort = lgi.getSort();
		this.promotionFlag = lgi.getPromotionFlag();
		this.betShares = lgi.getBetShares();
		this.remainShares = lgi.getRemainShares();
		this.status = lgi.getStatus();
		this.createtime = lgi.getCreatetime();
		this.lotterytime = lgi.getLotterytime();
		this.finishedtime = lgi.getFinishedtime();
		this.luckyNumber = lgi.getLuckyNumber();
		this.showIdStr = "无";
		this.nickname = "无";
		this.dAmount = BigDecimal.ZERO;
		this.actualDAmount = BigDecimal.ZERO;
		this.luckyGroup = lgi.getLuckyGroup();
		this.tabs = "";
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


	public String getShowIdStr() {
		return showIdStr;
	}


	public void setShowIdStr(String showIdStr) {
		this.showIdStr = showIdStr;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public BigDecimal getdAmount() {
		return dAmount;
	}


	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}


	public BigDecimal getActualDAmount() {
		return actualDAmount;
	}


	public void setActualDAmount(BigDecimal actualDAmount) {
		this.actualDAmount = actualDAmount;
	}


	public String getCoverImg() {
		return coverImg;
	}


	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Timestamp getPaymenttime() {
		return paymenttime;
	}


	public void setPaymenttime(Timestamp paymenttime) {
		this.paymenttime = paymenttime;
	}


	public String getLuckyGroup() {
		return luckyGroup;
	}


	public void setLuckyGroup(String luckyGroup) {
		this.luckyGroup = luckyGroup;
	}


	public String getTabs() {
		return tabs;
	}


	public void setTabs(String tabs) {
		this.tabs = tabs;
	}
}