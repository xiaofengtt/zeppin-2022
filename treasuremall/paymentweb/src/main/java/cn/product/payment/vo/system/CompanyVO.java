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
    private String creatorName;
    private Timestamp createtime;
	
	public CompanyVO() {
		super();
	}
	
	public CompanyVO (Company company) {
		this.uuid = company.getUuid();
		this.name = company.getName();
		this.code = company.getCode();
		this.companyPrivate = company.getCompanyPrivate();
		this.companyPublic = company.getCompanyPublic();
		this.systemPrivate = company.getSystemPrivate();
		this.systemPublic = company.getSystemPublic();
		this.whiteUrl = company.getWhiteUrl();
		this.rechargePoundage = company.getRechargePoundage();
		this.rechargePoundageRate = company.getRechargePoundageRate();
		this.withdrawPoundage = company.getWithdrawPoundage();
		this.withdrawPoundageRate = company.getWithdrawPoundageRate();
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