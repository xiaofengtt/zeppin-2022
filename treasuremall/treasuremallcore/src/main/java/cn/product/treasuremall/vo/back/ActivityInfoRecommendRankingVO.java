package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.ActivityInfoRecommendRanking;

public class ActivityInfoRecommendRankingVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7106541494347504291L;
	
	private String uuid;
	private String frontUser;
	private String frontUserName;
	private Integer frontUserShowId;
	private String type;
	private Integer recommend;
	private BigDecimal award;
	private String status;
	private Timestamp createtime;

	public ActivityInfoRecommendRankingVO(ActivityInfoRecommendRanking airr) {
		this.uuid = airr.getUuid();
		this.frontUser = airr.getFrontUser();
		this.type = airr.getType();
		this.recommend = airr.getRecommend();
		this.award = airr.getAward();
		this.status = airr.getStatus();
		this.createtime = airr.getCreatetime();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}