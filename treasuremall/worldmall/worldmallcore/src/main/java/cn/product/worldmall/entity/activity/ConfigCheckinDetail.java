package cn.product.worldmall.entity.activity;

import java.io.Serializable;

public class ConfigCheckinDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1807206951643480760L;
	
	private String uuid;
	private String activityInfo;
	private String type;
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	private Integer dayNum;
	private Integer sort;
	
	public class ConfigCheckinDetailType{
    	public final static String ADD = "add";
    	public final static String EDIT = "edit";
    	public final static String DELETE = "delete";
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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