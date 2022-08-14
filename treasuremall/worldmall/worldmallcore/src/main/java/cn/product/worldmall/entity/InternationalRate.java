package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class InternationalRate implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 468303351881609932L;
	
	@Id
	private String uuid;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String internationalInfo;
	private String currencyCode;
	private String currencyName;
	private String baseCurrency;
	private BigDecimal realRate;
	private BigDecimal checkRate;
	

	public class InternationalRateStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
		public final static String PUBLISH = "publish";
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getInternationalInfo() {
		return internationalInfo;
	}
	public void setInternationalInfo(String internationalInfo) {
		this.internationalInfo = internationalInfo;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public BigDecimal getRealRate() {
		return realRate;
	}
	public void setRealRate(BigDecimal realRate) {
		this.realRate = realRate;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getCheckRate() {
		return checkRate;
	}
	public void setCheckRate(BigDecimal checkRate) {
		this.checkRate = checkRate;
	}
}