package com.product.worldpay.vo.store;

import java.io.Serializable;
import java.math.BigDecimal;

import com.product.worldpay.entity.Company;

public class CompanyVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -481758044470060426L;
	
	private String uuid;
	private String name;
	private String code;
    private String companyPublic;
    private String systemPublic;
    private String whiteUrl;
    private BigDecimal withdrawPoundage;
    private BigDecimal withdrawPoundageRate;
    private BigDecimal rechargePoundage;
    private BigDecimal rechargePoundageRate;
    private String status;
	
	public CompanyVO() {
		super();
	}
	
	public CompanyVO (Company company) {
		this.uuid = company.getUuid();
		this.name = company.getName();
		this.code = company.getCode();
		this.companyPublic = company.getCompanyPublic();
		this.systemPublic = company.getSystemPublic();
		this.whiteUrl = company.getWhiteUrl();
		this.rechargePoundage = company.getRechargePoundage();
		this.rechargePoundageRate = company.getRechargePoundageRate();
		this.withdrawPoundage = company.getWithdrawPoundage();
		this.withdrawPoundageRate = company.getWithdrawPoundageRate();
		this.status = company.getStatus();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyPublic() {
		return companyPublic;
	}

	public void setCompanyPublic(String companyPublic) {
		this.companyPublic = companyPublic;
	}

	public String getSystemPublic() {
		return systemPublic;
	}

	public void setSystemPublic(String systemPublic) {
		this.systemPublic = systemPublic;
	}

	public String getWhiteUrl() {
		return whiteUrl;
	}

	public void setWhiteUrl(String whiteUrl) {
		this.whiteUrl = whiteUrl;
	}

	public BigDecimal getWithdrawPoundage() {
		return withdrawPoundage;
	}

	public void setWithdrawPoundage(BigDecimal withdrawPoundage) {
		this.withdrawPoundage = withdrawPoundage;
	}

	public BigDecimal getWithdrawPoundageRate() {
		return withdrawPoundageRate;
	}

	public void setWithdrawPoundageRate(BigDecimal withdrawPoundageRate) {
		this.withdrawPoundageRate = withdrawPoundageRate;
	}

	public BigDecimal getRechargePoundage() {
		return rechargePoundage;
	}

	public void setRechargePoundage(BigDecimal rechargePoundage) {
		this.rechargePoundage = rechargePoundage;
	}

	public BigDecimal getRechargePoundageRate() {
		return rechargePoundageRate;
	}

	public void setRechargePoundageRate(BigDecimal rechargePoundageRate) {
		this.rechargePoundageRate = rechargePoundageRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}