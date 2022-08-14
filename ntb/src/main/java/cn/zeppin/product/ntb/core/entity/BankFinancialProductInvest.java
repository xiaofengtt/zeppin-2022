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
 * 
 * @description 【数据对象】银行理财产品投资信息
 */

@Entity
@Table(name = "bank_financial_product_invest", uniqueConstraints = {@UniqueConstraint(columnNames = {"company_account","bank_financial_product","bank_financial_product_publish"})})
public class BankFinancialProductInvest extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1247487393527835513L;
	
	private String uuid;
	private String bankFinancialProduct;
	private String bankFinancialProductPublish;
	private String companyAccount;
	private BigDecimal totalRedeem;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private String stage;
	private String creator;
	private Timestamp createtime;
	
	private BigDecimal accountBalance;
	
	public class BankFinancialProductInvestStage{
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
	
	@Column(name = "bank_financial_product", nullable = false, length = 36)
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}
	
	@Column(name = "bank_financial_product_publish", nullable = false, length = 36)
	public String getBankFinancialProductPublish() {
		return bankFinancialProductPublish;
	}

	public void setBankFinancialProductPublish(String bankFinancialProductPublish) {
		this.bankFinancialProductPublish = bankFinancialProductPublish;
	}
	
	@Column(name = "company_account", nullable = false, length = 36)
	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	@Column(name = "total_redeem")
	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}
	
	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}
	
	@Column(name = "total_amount", nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "total_return")
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
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "account_balance", nullable = false)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
}
