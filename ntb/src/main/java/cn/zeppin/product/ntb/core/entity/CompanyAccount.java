/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】企业账户
 */

@Entity
@Table(name = "company_account" , uniqueConstraints = {@UniqueConstraint(columnNames = "account_num")})
public class CompanyAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String accountName;
	private String accountNum;
	private String companyName;
	private String type;
	private String bank;
	private String branchBank;
	private BigDecimal investment;
	private BigDecimal accountBalance;
	private BigDecimal totalRedeem;
	private BigDecimal totalReturn;
	private String creator;
	private Timestamp createtime;
	private String status;
	
	public class CompanyAccountStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	public class CompanyAccountType{
		public final static String THIRD = "third";
		public final static String INVEST = "invest";
		public final static String COLLECT = "collect";
		public final static String REDEEM = "redeem";
	}
	
	public class CompanyAccountUuid{
		public final static String WECHART = "083e49ef-cbc2-4ed9-9464-b09a99b4d9a8";
		public final static String ALIPAY = "083e49ef-cbc2-4fd9-9e64-b09a99b4d9a8";
		public final static String CHANPAY = "613e31ef-ca52-4fd9-a523-af9192b3d9e1";
		public final static String REAPAL = "1ffddd49-a46a-40f0-b72f-54c1e91ee126";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "account_name", nullable = false, length = 50)
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Column(name = "account_num", nullable = false, length = 36)
	public String getAccountNum() {
		return accountNum;
	}
	
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	@Column(name = "company_name", nullable = false, length = 50)
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "bank", length = 36)
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@Column(name = "branch_bank", length = 36)
	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	@Column(name = "investment", nullable = false, length = 20)
	public BigDecimal getInvestment() {
		return investment;
	}
	
	public void setInvestment(BigDecimal investment) {
		this.investment = investment;
	}
	
	@Column(name = "account_balance", nullable = false, length = 20)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	@Column(name = "total_redeem", nullable = false, length = 20)
	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}

	@Column(name = "total_return", nullable = false, length = 20)
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}

	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
