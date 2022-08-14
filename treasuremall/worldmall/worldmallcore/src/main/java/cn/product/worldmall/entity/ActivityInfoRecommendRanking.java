package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class ActivityInfoRecommendRanking implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7163959999688191757L;
	
	@Id
	private String uuid;
	private String frontUser;
	private String type;
	private Integer recommend;
	private BigDecimal award;
	private String status;
	private Timestamp createtime;
	
	public class ActivityInfoRecommendRankingType{
		public final static String NORMAL = "normal";
    	public final static String ROBOT = "robot";
    }
	
    public class ActivityInfoRecommendRankingStatus{
    	public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
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