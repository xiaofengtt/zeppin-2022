package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.ActivityInfoCheckinPrize;

public class ActivityInfoCheckinPrizeVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2696650017278321024L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeTitle;//奖品名称
	private String prizeType;//奖品类型：gold(金币)/entity(实物奖品)/voucher(优惠券)
	private String prizeCover;//奖品封面，可空
	private String prize;//奖品信息，如果是金币奖品，值是金币数/否则是个UUID
	private Integer dayNum;//第几天
	private Integer sort;//排序
	
	private String prizeDetail;//奖品名称
	private String prizeCoverUrl;//封面图地址
	private Boolean isCheckin;//是否已签到

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
		this.isCheckin = false;
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

	public Boolean getIsCheckin() {
		return isCheckin;
	}

	public void setIsCheckin(Boolean isCheckin) {
		this.isCheckin = isCheckin;
	}
}