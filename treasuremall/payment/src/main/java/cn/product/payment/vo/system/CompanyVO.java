package cn.product.payment.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.payment.entity.Company;

public class CompanyVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6841330662558009410L;
	
	private String uuid;
	private String name;
	private String code;
	private BigDecimal balance;
	private BigDecimal balanceLock;
    private String whiteUrl;
    private String status;
    private String creator;
    private String creatorName;
    private Timestamp createtime;
	
	public CompanyVO() {
		super();
	}
	
	public CompanyVO (Company company) {
		this.uuid = company.getUuid();
		this.name = company.getName();
		this.code = company.getCode();
		this.whiteUrl = company.getWhiteUrl();
		this.status = company.getStatus();
		this.creator = company.getCreator();
		this.createtime = company.getCreatetime();
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceLock() {
		return balanceLock;
	}

	public void setBalanceLock(BigDecimal balanceLock) {
		this.balanceLock = balanceLock;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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
}