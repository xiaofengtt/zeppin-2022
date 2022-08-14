package cn.zeppin.product.score.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.zeppin.product.score.util.JSONUtils;

public class CapitalAccountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529678434857703117L;
	
	private String uuid;
	private String capitalPlatform;
	private String name;
	private String accountNum;
	private BigDecimal poundageRate;
	private BigDecimal min;
	private BigDecimal max;
	private Map<String, Object> data;
	private String remark;
	private String type;
	private String status;
	private String sort;
	
	public CapitalAccountVO(CapitalAccount ca){
		this.uuid = ca.getUuid();
		this.capitalPlatform = ca.getCapitalPlatform();
		this.name = ca.getName();
		this.accountNum = ca.getAccountNum();
		this.poundageRate = ca.getPoundageRate();
		this.min = ca.getMin();
		this.max = ca.getMax();
		if(CapitalPlatformType.COMPANY_BANKCARD.equals(ca.getType()) || CapitalPlatformType.PERSONAL_BANKCARD.equals(ca.getType())){
			this.data = JSONUtils.json2map(ca.getData());
		}
		this.remark = ca.getRemark();
		this.type = ca.getType();
		this.status = ca.getStatus();
		this.sort = ca.getSort();
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
}