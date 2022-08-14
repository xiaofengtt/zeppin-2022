package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class WinningInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4720500549313719481L;
	
	@Id
	private String uuid;
	private String goodsIssue;
	private String goodsId;
	private String frontUser;
	private Integer showId;
	private String orderId;
	private String gameType;
	
	private Integer luckynum;
	
	private String type;
	
	private BigDecimal paymentDAmount;
	
	private Integer buyCount;
	private BigDecimal dealPrice;
	
	private Timestamp winningTime;
	
	public class WinningInfoType {
		public static final String NORMAL = "normal";//未领奖
		public static final String GOLD = "gold";//金币
		public static final String ENTITY = "entity";//实物
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

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

}