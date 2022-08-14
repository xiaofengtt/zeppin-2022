package cn.product.score.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import cn.product.score.entity.CapitalAccount;
import cn.product.score.util.JSONUtils;

public class CapitalAccountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529678434857703117L;
	
	private String uuid;
	private String capitalPlatform;
	private String capitalPlatformName;
	private String capitalPlatformType;
	private String name;
	private String accountNum;
	private BigDecimal balance;
	private BigDecimal poundageRate;
	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal dailyMax;
	private BigDecimal dailySum;
	private Map<String, Object> data;
	private String remark;
	private String type;
	private String transType;
	private String status;
	private String sort;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	
	public CapitalAccountVO(CapitalAccount ca){
		this.uuid = ca.getUuid();
		this.capitalPlatform = ca.getCapitalPlatform();
		this.name = ca.getName();
		this.accountNum = ca.getAccountNum();
		this.balance = ca.getBalance().abs();
		this.poundageRate = ca.getPoundageRate();
		this.min = ca.getMin();
		this.max = ca.getMax();
		this.dailyMax = ca.getDailyMax();
		this.dailySum = ca.getDailySum();
		this.data = JSONUtils.json2map(ca.getData());
		this.remark = ca.getRemark();
		this.type = ca.getType();
		this.transType = ca.getTransType();
		this.status = ca.getStatus();
		this.sort = ca.getSort();
		this.creator = ca.getCreator();
		this.createtime = ca.getCreatetime();
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

	public String getCapitalPlatformName() {
		return capitalPlatformName;
	}

	public void setCapitalPlatformName(String capitalPlatformName) {
		this.capitalPlatformName = capitalPlatformName;
	}

	public String getCapitalPlatformType() {
		return capitalPlatformType;
	}

	public void setCapitalPlatformType(String capitalPlatformType) {
		this.capitalPlatformType = capitalPlatformType;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public BigDecimal getDailySum() {
		return dailySum;
	}

	public void setDailySum(BigDecimal dailySum) {
		this.dailySum = dailySum;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}