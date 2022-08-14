package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.WinningInfo;
import cn.product.worldmall.entity.WinningInfoReceive;
import cn.product.worldmall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;

public class WinningInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5794907459772447571L;
	
	private String uuid;
	private String goodsIssue;
	private String frontUser;
	private String gameType;
	
	private Integer luckynum;
	
	private BigDecimal paymentDAmount;
	
	private Integer buyCount;
	private BigDecimal dealPrice;
	
	private Timestamp winningTime;
	
	private String title;
	private String shortTitle;
	private String cover;//商品封面图
	private Integer shares;//总份额
	private String code;//商品编码
	private BigDecimal price;//商品价值
	private String source;
	private String sourceUrl;
	private Integer issueNum;
	
	//领奖信息
	private String nickname;
	private Integer showId;
	private String imageUrl;
	private Boolean isRobot;
	private Long orderNum;
	private String orderId;
	private String status;
	private Timestamp operattime;
	
	private String type;
	private String ip;
	
	private String provideInfo;
	private ProvideInfoVO detailInfo;
	
	private Timestamp createtime;
	
	private Timestamp starttime;
	private Timestamp finishedtime;
	
	public WinningInfoVO() {
		super();
	}
	
	public WinningInfoVO (WinningInfo wi) {
		this.uuid = wi.getUuid();
		this.goodsIssue = wi.getGoodsIssue();
		this.frontUser = wi.getFrontUser();
		this.gameType = wi.getGameType();
		this.luckynum = wi.getLuckynum();
		this.paymentDAmount = wi.getPaymentDAmount();
		this.buyCount = wi.getBuyCount();
		this.dealPrice = wi.getDealPrice();
		this.winningTime = wi.getWinningTime();
		this.type = wi.getType();
		this.status = WinningInfoReceiveStatus.NORMAL;
	}
	
	public WinningInfoVO (WinningInfo wi, WinningInfoReceive wir) {
		this.uuid = wi.getUuid();
		this.goodsIssue = wi.getGoodsIssue();
		this.frontUser = wi.getFrontUser();
		this.gameType = wi.getGameType();
		this.luckynum = wi.getLuckynum();
		this.paymentDAmount = wi.getPaymentDAmount();
		this.buyCount = wi.getBuyCount();
		this.dealPrice = wi.getDealPrice();
		this.winningTime = wi.getWinningTime();
		this.orderId = wir.getOrderId();
		this.status = wir.getStatus();
		this.type = wir.getType();
		this.ip = wir.getIp();
		this.provideInfo = wir.getProvideInfo();
		this.createtime = wir.getCreatetime();
		if(!Utlity.checkStringNull(wir.getProvideInfo())) {
			this.detailInfo = JSONUtils.json2obj(wir.getProvideInfo(), ProvideInfoVO.class);
		}
		this.operattime = wir.getOperattime();
	}
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGoodsIssue() {
		return goodsIssue;
	}

	public void setGoodsIssue(String goodsIssue) {
		this.goodsIssue = goodsIssue;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public Integer getLuckynum() {
		return luckynum;
	}

	public void setLuckynum(Integer luckynum) {
		this.luckynum = luckynum;
	}

	public BigDecimal getPaymentDAmount() {
		return paymentDAmount;
	}

	public void setPaymentDAmount(BigDecimal paymentDAmount) {
		this.paymentDAmount = paymentDAmount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(BigDecimal dealPrice) {
		this.dealPrice = dealPrice;
	}

	public Timestamp getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Timestamp winningTime) {
		this.winningTime = winningTime;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProvideInfo() {
		return provideInfo;
	}

	public void setProvideInfo(String provideInfo) {
		this.provideInfo = provideInfo;
	}

	public ProvideInfoVO getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(ProvideInfoVO detailInfo) {
		this.detailInfo = detailInfo;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public Boolean getIsRobot() {
		return isRobot;
	}

	public void setIsRobot(Boolean isRobot) {
		this.isRobot = isRobot;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getFinishedtime() {
		return finishedtime;
	}

	public void setFinishedtime(Timestamp finishedtime) {
		this.finishedtime = finishedtime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
}