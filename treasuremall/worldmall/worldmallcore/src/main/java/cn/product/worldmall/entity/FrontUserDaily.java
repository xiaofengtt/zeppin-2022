package cn.product.worldmall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class FrontUserDaily implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3557879401409284369L;
	
	@Id
	private String uuid;
	private String frontUser;
	private String dailyDate;
	
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
	
	public String getDailyDate() {
		return dailyDate;
	}
	
	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}
}