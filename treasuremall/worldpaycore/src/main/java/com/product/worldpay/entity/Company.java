package com.product.worldpay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class Company implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7836531123048618307L;
	
	@Id
	private String uuid;
	private String name;
	private String code;
	private String companyPrivate;
    private String companyPublic;
    private String systemPrivate;
    private String systemPublic;
    private String whiteUrl;
    private BigDecimal withdrawPoundage;
    private BigDecimal withdrawPoundageRate;
    private BigDecimal rechargePoundage;
    private BigDecimal rechargePoundageRate;
    private String status;
    private String creator;
    private Timestamp createtime;
    
	public class CompanyStatus{
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

	public String getCompanyPrivate() {
		return companyPrivate;
	}

	public void setCompanyPrivate(String companyPrivate) {
		this.companyPrivate = companyPrivate;
	}

	public String getCompanyPublic() {
		return companyPublic;
	}

	public void setCompanyPublic(String companyPublic) {
		this.companyPublic = companyPublic;
	}

	public String getSystemPrivate() {
		return systemPrivate;
	}

	public void setSystemPrivate(String systemPrivate) {
		this.systemPrivate = systemPrivate;
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