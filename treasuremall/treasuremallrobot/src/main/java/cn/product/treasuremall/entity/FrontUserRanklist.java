package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class FrontUserRanklist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5105280458373897810L;
	@Id
	private String frontUser;
	private BigDecimal totalPayment;
	private BigDecimal totalWinning;
	
	private Integer paymentTimes;
	private Integer winningTimes;
	
	private Integer buyCount;
	private Integer rankNum;
	

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public BigDecimal getTotalWinning() {
		return totalWinning;
	}

	public void setTotalWinning(BigDecimal totalWinning) {
		this.totalWinning = totalWinning;
	}

	public Integer getPaymentTimes() {
		return paymentTimes;
	}

	public void setPaymentTimes(Integer paymentTimes) {
		this.paymentTimes = paymentTimes;
	}

	public Integer getWinningTimes() {
		return winningTimes;
	}

	public void setWinningTimes(Integer winningTimes) {
		this.winningTimes = winningTimes;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Integer getRankNum() {
		return rankNum;
	}

	public void setRankNum(Integer rankNum) {
		this.rankNum = rankNum;
	}
}