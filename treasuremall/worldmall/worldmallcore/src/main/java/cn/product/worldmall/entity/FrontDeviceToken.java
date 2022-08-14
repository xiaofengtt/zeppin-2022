package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontDeviceToken implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4742445792110049776L;
	
	@Id
	private String uuid;
	private String uniqueToken;
	private String deviceType;
	private String deviceToken;
	private String endpointArn;
	
	private String frontUser;
	private String frontUserGroup;
	
	private Timestamp createtime;
	private Timestamp updatetime;
	
	private String ip;
	private String country;
	private String countryCode;
	
	private String topic;
	
	public class FrontDeviceTokenDeviceType{
    	public final static String IOS = "01";
    	public final static String ANDROID = "02";
    }
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUniqueToken() {
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getEndpointArn() {
		return endpointArn;
	}

	public void setEndpointArn(String endpointArn) {
		this.endpointArn = endpointArn;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getFrontUserGroup() {
		return frontUserGroup;
	}

	public void setFrontUserGroup(String frontUserGroup) {
		this.frontUserGroup = frontUserGroup;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}