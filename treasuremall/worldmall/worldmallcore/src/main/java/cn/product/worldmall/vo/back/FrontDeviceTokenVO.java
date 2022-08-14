package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontDeviceToken;

public class FrontDeviceTokenVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2436290483449773632L;
	
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
	private String countryName;
	private String countryNameEn;
	private String countryCode;
	
	private String topic;
	
	private String nickname;
	private Integer showId;
	
	private List<Map<String, Object>> topicList;
	
	public FrontDeviceTokenVO(){
		
	}
	
	public FrontDeviceTokenVO(FrontDeviceToken fdt){
		this.uuid = fdt.getUuid();
		this.uniqueToken = fdt.getUniqueToken();
		this.deviceToken = fdt.getDeviceToken();
		this.deviceType = fdt.getDeviceType();
		this.endpointArn = fdt.getEndpointArn();
		this.frontUser = fdt.getFrontUser();
		this.frontUserGroup = fdt.getFrontUserGroup();
		this.createtime = fdt.getCreatetime();
		this.updatetime = fdt.getUpdatetime();
		this.ip = fdt.getIp();
		this.country = fdt.getCountry();
		this.countryCode = fdt.getCountryCode();
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryNameEn() {
		return countryNameEn;
	}

	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public List<Map<String, Object>> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Map<String, Object>> topicList) {
		this.topicList = topicList;
	}

}
