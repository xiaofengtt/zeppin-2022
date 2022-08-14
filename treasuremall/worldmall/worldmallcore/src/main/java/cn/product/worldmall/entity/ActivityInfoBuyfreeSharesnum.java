package cn.product.worldmall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class ActivityInfoBuyfreeSharesnum implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2892082068853609431L;
	
	@Id
	private String activityInfoBuyfree;
	private String sharenums;
	
	public String getActivityInfoBuyfree() {
		return activityInfoBuyfree;
	}
	public void setActivityInfoBuyfree(String activityInfoBuyfree) {
		this.activityInfoBuyfree = activityInfoBuyfree;
	}
	public String getSharenums() {
		return sharenums;
	}
	public void setSharenums(String sharenums) {
		this.sharenums = sharenums;
	}

}