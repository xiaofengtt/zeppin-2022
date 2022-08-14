package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.ActivityInfoCheckinPrize;

public class ActivityInfoCheckinPrizeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5769358920794849685L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	private Integer dayNum;
	private Integer sort;
	
	private String prizeDetail;
	private String prizeCoverUrl;

	public ActivityInfoCheckinPrizeVO(ActivityInfoCheckinPrize aicp) {
		this.uuid = aicp.getUuid();
		this.activityInfo = aicp.getActivityInfo();
		this.status = aicp.getActivityInfo();
		this.createtime = aicp.getCreatetime();
		this.creator = aicp.getCreator();
		this.sort = aicp.getSort();
		this.prizeTitle = aicp.getPrizeTitle();
		this.prizeType = aicp.getPrizeType();
		this.prizeCover = aicp.getPrizeCover();
		this.prize = aicp.getPrize();
		this.dayNum = aicp.getDayNum();
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

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
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
}