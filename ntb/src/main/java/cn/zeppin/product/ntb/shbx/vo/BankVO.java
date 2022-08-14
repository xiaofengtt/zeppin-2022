package cn.zeppin.product.ntb.shbx.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class BankVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6695683720711199306L;
	
	private String uuid;
	private String name;
	private String status;
	private BigDecimal singleLimit;
	private BigDecimal dailyLimit;
	private String shortName;
	
	private String iconColor;
	private String iconColorUrl;
	
	
	public BankVO(){
		
	}
	
	public BankVO(Bank bank){
		this.uuid = bank.getUuid();
		this.name = bank.getName();
		this.status = bank.getStatus();
		this.singleLimit = bank.getSingleLimit();
		this.dailyLimit = bank.getDailyLimit();
		this.shortName = bank.getShortName();
		this.iconColor = bank.getIconColor();
	}

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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(BigDecimal singleLimit) {
		this.singleLimit = singleLimit;
	}

	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(BigDecimal dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	public String getIconColor() {
		return iconColor;
	}
	

	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}
	

	public String getIconColorUrl() {
		return iconColorUrl;
	}
	

	public void setIconColorUrl(String iconColorUrl) {
		this.iconColorUrl = iconColorUrl;
	}

}
