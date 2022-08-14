package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize;

public class ActivityInfoScorelotteryPrizeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1897426488659956627L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	private Integer sort;
	
	private String prizeDetail;
	private String prizeCoverUrl;
	private Boolean isScorelottery;//是否已参与

	public ActivityInfoScorelotteryPrizeVO(ActivityInfoScorelotteryPrize aisp) {
		this.uuid = aisp.getUuid();
		this.activityInfo = aisp.getActivityInfo();
		this.status = aisp.getActivityInfo();
		this.createtime = aisp.getCreatetime();
		this.creator = aisp.getCreator();
		this.sort = aisp.getSort();
		this.prizeTitle = aisp.getPrizeTitle();
		this.prizeType = aisp.getPrizeType();
		this.prizeCover = aisp.getPrizeCover();
		this.prize = aisp.getPrize();
		this.isScorelottery = false;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPrizeTitle() {
		return prizeTitle;
	}

	public void setPrizeTitle(String prizeTitle) {
		this.prizeTitle = prizeTitle;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizeCover() {
		return prizeCover;
	}

	public void setPrizeCover(String prizeCover) {
		this.prizeCover = prizeCover;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getPrizeDetail() {
		return prizeDetail;
	}

	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}

	public String getPrizeCoverUrl() {
		return prizeCoverUrl;
	}

	public void setPrizeCoverUrl(String prizeCoverUrl) {
		this.prizeCoverUrl = prizeCoverUrl;
	}

	public Boolean getIsScorelottery() {
		return isScorelottery;
	}

	public void setIsScorelottery(Boolean isScorelottery) {
		this.isScorelottery = isScorelottery;
	}
}