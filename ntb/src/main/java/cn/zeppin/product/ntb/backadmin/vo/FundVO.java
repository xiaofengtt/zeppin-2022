package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.FundRate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6820706644086128755L;
	
	private String uuid;
	private String name;
	private String scode;
	private String riskLevel;
	private String type;
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private String status;
	private List<FundRate> fundRateList = new ArrayList<FundRate>();

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getScode() {
		return scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(accountBalance);
	}

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<FundRate> getFundRateList() {
		return fundRateList;
	}
	
	public void setFundRateList(List<FundRate> fundRateList) {
		this.fundRateList = fundRateList;
	}
}
