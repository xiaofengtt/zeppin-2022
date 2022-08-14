package cn.product.worldmall.vo.front;

import java.io.Serializable;

import cn.product.worldmall.entity.Bank;

public class BankVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String name;
	private String shortName;
	private String logo;
	private String logoUrl;
	private String status;
	
	public BankVO(){
		
	}
	
	public BankVO(Bank bank){
		this.uuid = bank.getUuid();
		this.name = bank.getName();
		this.shortName = bank.getShortName();
		this.logo = bank.getLogo();
		this.status = bank.getStatus();
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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
}
