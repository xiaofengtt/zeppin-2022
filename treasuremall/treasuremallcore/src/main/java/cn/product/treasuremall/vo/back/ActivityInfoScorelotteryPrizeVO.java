package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.ActivityInfoScorelotteryPrize;

public class ActivityInfoScorelotteryPrizeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5899132979586273352L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	private BigDecimal winningRate;
	private Integer sort;
	
	private String prizeDetail;
	private String prizeCoverUrl;

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
		this.winningRate = aisp.getWinningRate();
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

	public BigDecimal getWinningRate() {
		return winningRate;
	}

	public void setWinningRate(BigDecimal winningRate) {
		this.winningRate = winningRate;
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