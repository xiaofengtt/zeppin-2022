package cn.product.treasuremall.entity.activity;

import java.io.Serializable;

public class ConfigFirstchargeDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530520869469147227L;
	
	private String uuid;
	private String activityInfo;
	private String type;
	private String amount;
	private String capitalAccount;
	private String prizeType;
	private String prize;
	private Integer sort;
	
	public class ConfigFirstchargeDetailType{
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCapitalAccount() {
		return capitalAccount;
	}
	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}
	public String getPrize() {
		return prize;
	}
	public void setPrize(String prize) {
		this.prize = prize;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}