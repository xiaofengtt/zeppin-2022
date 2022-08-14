package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import cn.product.treasuremall.entity.CapitalAccount;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;

public class CapitalAccountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529678434857703117L;
	
	private String uuid;
	private String capitalPlatform;
	private String capitalPlatformName;
	private String name;
	private String accountNum;
	private BigDecimal poundageRate;
	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal dailyMax;
	private BigDecimal totalMax;
	private String data;
	private Map<String, Object> dataMap;
	private String remark;
	private String type;
	private String transType;
	private String status;
	private Integer sort;
	private String creator;
	private Timestamp createtime;
	private String logo;
	private String logoUrl;
	
	public CapitalAccountVO(CapitalAccount ca){
		this.uuid = ca.getUuid();
		this.capitalPlatform = ca.getCapitalPlatform();
		this.name = ca.getName();
		this.accountNum = ca.getAccountNum();
		this.poundageRate = ca.getPoundageRate();
		this.min = ca.getMin();
		this.max = ca.getMax();
		this.dailyMax = ca.getDailyMax();
		this.totalMax = ca.getTotalMax();
		this.remark = ca.getRemark();
		this.type = ca.getType();
		this.transType = ca.getTransType();
		this.status = ca.getStatus();
		this.sort = ca.getSort();
		this.creator = ca.getCreator();
		this.createtime = ca.getCreatetime();
		this.logo = ca.getLogo();
		this.data = ca.getData();
		this.dataMap = Utlity.checkStringNull(ca.getData()) ? new HashMap<String, Object>() : JSONUtils.json2map(ca.getData());
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

	public BigDecimal getTotalMax() {
		return totalMax;
	}

	public void setTotalMax(BigDecimal totalMax) {
		this.totalMax = totalMax;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
}