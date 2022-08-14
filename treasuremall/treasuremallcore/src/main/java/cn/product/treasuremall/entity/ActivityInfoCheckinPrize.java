package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class ActivityInfoCheckinPrize implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4720760497606172816L;
	
	@Id
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
	
    public class ActivityInfoCheckinPrizeStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }
	
    public class ActivityInfoCheckinPrizeType{
    	public final static String GOLD = "gold";
    	public final static String ENTITY = "entity";
    	public final static String VOUCHER = "voucher";
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
}