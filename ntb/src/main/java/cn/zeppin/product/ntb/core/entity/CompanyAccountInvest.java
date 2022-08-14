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
 * @description 【数据对象】企业账户投资
 */

@Entity
@Table(name = "company_account_invest", uniqueConstraints = {@UniqueConstraint(columnNames = {"company_account","bank_financial_product"})})
public class CompanyAccountInvest extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 66418116224881904L;
	
	private String uuid;
	private String companyAccount;
	private String bankFinancialProduct;
	private BigDecimal accountBalance;
	private BigDecimal totalAmount;
	private BigDecimal totalRedeem;
	private BigDecimal totalReturn;
	private String stage;
	private String creator;
	private Timestamp createtime;
	
	public class CompanyAccountInvestStage{
		public final static String NORMAL = "normal";
		public final static String FINISHED = "finished";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "company_account", nullable = false, length = 36)
	public String getCompanyAccount() {
		return companyAccount;
	}
	
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	
	@Column(name = "bank_financial_product", nullable = false, length = 36)
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}
	
	@Column(name = "account_balance", nullable = false, length = 20)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Column(name = "total_amount", nullable = false, length = 20)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	@Column(name = "stage", nullable = false, length = 20)
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
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
}
