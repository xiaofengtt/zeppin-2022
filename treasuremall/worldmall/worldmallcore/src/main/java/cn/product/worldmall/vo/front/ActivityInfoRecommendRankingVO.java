package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.product.worldmall.entity.ActivityInfoRecommendRanking;
import cn.product.worldmall.util.Utlity;

public class ActivityInfoRecommendRankingVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7106541494347504291L;
	
	private String uuid;
	private String frontUser;
	private String frontUserName;
	private Integer frontUserShowId;
	private Integer recommend;
	private BigDecimal award;
	private String imageURL;

	public ActivityInfoRecommendRankingVO(ActivityInfoRecommendRanking airr) {
		this.uuid = airr.getUuid();
		this.frontUser = airr.getFrontUser();
		this.recommend = airr.getRecommend();
		this.award = airr.getAward();
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

	public String getFrontUserName() {
		return frontUserName;
	}

	public void setFrontUserName(String frontUserName) {
		this.frontUserName = frontUserName;
	}

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public BigDecimal getAward() {
		return award;
	}

	public void setAward(BigDecimal award) {
		this.award = award;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}