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

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * 
 * @description 【数据对象】银行理财产品投资详细记录
 */

@Entity
@Table(name = "bank_financial_product_invest_records")
public class BankFinancialProductInvestRecords extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1247487393527835513L;
	
	private String uuid;
	private String bankFinancialProduct;
	private String bankFinancialProductPublish;
	private String companyAccount;
	private BigDecimal totalAmount;
	private BigDecimal accountBalace;
	private String remark;
	private String creator;
	private Timestamp createtime;
	private String type;
	
	public class BankFinancialProductInvestRecordsType{
		public final static String INVEST = "invest";
		public final static String REDEEM = "redeem";
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
	
	@Column(name = "total_amount", nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "account_balance")
	public BigDecimal getAccountBalace() {
		return accountBalace;
	}

	public void setAccountBalace(BigDecimal accountBalace) {
		this.accountBalace = accountBalace;
	}

	@Column(name = "remark", length = 500)
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
