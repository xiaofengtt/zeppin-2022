package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class BankVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String name;
	private String logo;
	private String logoUrl;
	private String status;
	private BigDecimal singleLimit;
	private BigDecimal dailyLimit;
	
	private String color;
	private String icon;
	private String iconUrl;
	
	private String shortName;
	private Boolean flagBinding;
	
	private String iconColor;
	private String iconColorUrl;
	
	private String code;
	
	private String codeNum;
	private Boolean flagBank;
	
	private String creditInquiryPhone;
	private String creditInquiryCommand;
	
	public BankVO(){
		
	}
	
	public BankVO(Bank bank){
		this.uuid = bank.getUuid();
		this.name = bank.getName();
		this.logo = bank.getLogo();
		this.status = bank.getStatus();
		this.singleLimit = bank.getSingleLimit();
		this.dailyLimit = bank.getDailyLimit();
		this.color = bank.getColor();
		this.icon = bank.getIcon();
		this.shortName = bank.getShortName();
		this.flagBinding = bank.getFlagBinding();
		this.iconColor = bank.getIconColor();
		this.code = bank.getCode();
		this.codeNum = bank.getCodeNum();
		this.flagBank = bank.getFlagBank();
		this.creditInquiryPhone = bank.getCreditInquiryPhone();
		this.creditInquiryCommand = bank.getCreditInquiryCommand();
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public Boolean getFlagBinding() {
		return flagBinding;
	}

	public void setFlagBinding(Boolean flagBinding) {
		this.flagBinding = flagBinding;
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

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCodeNum() {
		return codeNum;
	}
	
	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}
	
	public Boolean getFlagBank() {
		return flagBank;
	}
	
	public void setFlagBank(Boolean flagBank) {
		this.flagBank = flagBank;
	}
	
	public String getCreditInquiryPhone() {
		return creditInquiryPhone;
	}

	public void setCreditInquiryPhone(String creditInquiryPhone) {
		this.creditInquiryPhone = creditInquiryPhone;
	}

	public String getCreditInquiryCommand() {
		return creditInquiryCommand;
	}

	public void setCreditInquiryCommand(String creditInquiryCommand) {
		this.creditInquiryCommand = creditInquiryCommand;
	}
}
