package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class CapitalAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3476043938750734407L;
	
	@Id
	private String uuid;
	private String capitalPlatform;
	private String name;
	private String accountNum;
	private String frontUserGroup;
	private String frontUserStatus;
	private BigDecimal poundageRate;
	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal dailyMax;
	private BigDecimal totalMax;
	private String data;
	private String remark;
	private String type;
	private String transType;
	private String status;
	private Integer sort;
	private String creator;
	private Timestamp createtime;
	private Integer userPreReceive;//账户每日单人次可收账次数，null或者空字符串，代表无限制
	
	private String logo;
    
    public class CapitalAccountStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCapitalPlatform() {
		return capitalPlatform;
	}

	public void setCapitalPlatform(String capitalPlatform) {
		this.capitalPlatform = capitalPlatform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public BigDecimal getPoundageRate() {
		return poundageRate;
	}

	public void setPoundageRate(BigDecimal poundageRate) {
		this.poundageRate = poundageRate;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getDailyMax() {
		return dailyMax;
	}

	public void setDailyMax(BigDecimal dailyMax) {
		this.dailyMax = dailyMax;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public BigDecimal getTotalMax() {
		return totalMax;
	}

	public void setTotalMax(BigDecimal totalMax) {
		this.totalMax = totalMax;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getFrontUserGroup() {
		return frontUserGroup;
	}

	public void setFrontUserGroup(String frontUserGroup) {
		this.frontUserGroup = frontUserGroup;
	}

	public String getFrontUserStatus() {
		return frontUserStatus;
	}

	public void setFrontUserStatus(String frontUserStatus) {
		this.frontUserStatus = frontUserStatus;
	}

	public Integer getUserPreReceive() {
		return userPreReceive;
	}

	public void setUserPreReceive(Integer userPreReceive) {
		this.userPreReceive = userPreReceive;
	}
}