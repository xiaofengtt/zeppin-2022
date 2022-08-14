package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class ActivityInfoRechargePrize implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6316795324537083117L;
	
	@Id
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeType;
	private String prize;
	private String capitalAccount;
	private Integer sort;
	
    public class ActivityInfoRechargePrizeStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }
	
    public class ActivityInfoRechargePrizeType{
    	public final static String GOLD = "gold";
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

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}