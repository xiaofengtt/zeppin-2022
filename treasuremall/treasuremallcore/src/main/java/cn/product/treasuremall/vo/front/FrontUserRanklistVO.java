package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.product.treasuremall.entity.FrontUserRanklist;
import cn.product.treasuremall.util.Utlity;

public class FrontUserRanklistVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9033373647455291214L;
	
	private String frontUser;
	private BigDecimal totalPayment;
	private BigDecimal totalWinning;
	
	private Integer paymentTimes;
	private Integer winningTimes;
	
	private Integer buyCount;
	private Integer rankNum;
	
	private Integer showId;
	private String nickname;
	private String imageUrl;
	
	public FrontUserRanklistVO(FrontUserRanklist fur) {
		this.frontUser = fur.getFrontUser();
		this.totalPayment = fur.getTotalPayment();
		this.totalWinning = fur.getTotalWinning();
		this.paymentTimes = fur.getPaymentTimes();
		this.winningTimes = fur.getWinningTimes();
		this.buyCount = fur.getBuyCount();
		this.rankNum = fur.getRankNum();
		this.imageUrl = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";//默认头像
	}

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

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}