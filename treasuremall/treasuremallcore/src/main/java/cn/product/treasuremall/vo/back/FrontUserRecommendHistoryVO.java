package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserHistory;

public class FrontUserRecommendHistoryVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4100708083927284426L;
	private String uuid;
	
	private String frontUser;
	private Integer showId;
    private String nickname;
	private String imageURL;
    
    private String recommendFrontUser;
	private Integer recommendShowId;
    private String recommendNickname;
    private String recommendImageURL;
    
	private Timestamp createtime;
	private BigDecimal rechargeAmount;
	private BigDecimal awardAmount;
	
    
    public FrontUserRecommendHistoryVO(FrontUserHistory fuh) {
    	this.uuid = fuh.getUuid();
    	this.frontUser = fuh.getFrontUser();
    	this.createtime = fuh.getCreatetime();
    }

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getRecommendFrontUser() {
		return recommendFrontUser;
	}

	public void setRecommendFrontUser(String recommendFrontUser) {
		this.recommendFrontUser = recommendFrontUser;
	}

	public Integer getRecommendShowId() {
		return recommendShowId;
	}

	public void setRecommendShowId(Integer recommendShowId) {
		this.recommendShowId = recommendShowId;
	}

	public String getRecommendNickname() {
		return recommendNickname;
	}

	public void setRecommendNickname(String recommendNickname) {
		this.recommendNickname = recommendNickname;
	}

	public String getRecommendImageURL() {
		return recommendImageURL;
	}

	public void setRecommendImageURL(String recommendImageURL) {
		this.recommendImageURL = recommendImageURL;
	}
}