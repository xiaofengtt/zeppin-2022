package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserHistory;
import cn.product.worldmall.util.Utlity;

public class FrontUserRecommendHistoryVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1902093731491621743L;
	
	private String uuid;
	private String frontUser;
	private String recommendFrontUser;
	private Integer recommendShowId;
    private String nickname;
	private String imageURL;
	private Timestamp createtime;
	private BigDecimal rechargeAmount;
	private BigDecimal awardAmount;
	
    
    public FrontUserRecommendHistoryVO(FrontUserHistory fuh) {
    	this.uuid = fuh.getUuid();
    	this.frontUser = fuh.getFrontUser();
    	this.createtime = fuh.getCreatetime();
		this.imageURL = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";
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
}