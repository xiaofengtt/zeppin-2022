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
 * @description 【数据对象】银行理财产品投资信息
 */

@Entity
@Table(name = "bank_financial_product_invest")
public class BankFinancialProductInvest extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1247487393527835513L;
	
	private String uuid;
	private String bankFinancialProduct;
	private String bankFinancialProductPublish;
	private BigDecimal amount;
	private BigDecimal redeemAmount;
	private BigDecimal investIncome;
	private BigDecimal returnCapital;
	private BigDecimal returnInterest;
	private BigDecimal platfomIncome;
	private String stage;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class BankFinancialProductInvestStatus{
		public final static String UNCHECKED = "unchecked";
		public final static String CHECKED = "checked";
		public final static String DELETED = "deleted";
	}
	
	public class BankFinancialProductInvestStage{
		public final static String UNSTART = "unstart";
		public final static String INCOME = "income";
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
	
	@Column(name = "stage", nullable = false, length = 20)
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "amount", nullable = false)
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name = "redeem_amount")
	public BigDecimal getRedeemAmount() {
		return redeemAmount;
	}
	
	public void setRedeemAmount(BigDecimal redeemAmount) {
		this.redeemAmount = redeemAmount;
	}
	
	@Column(name = "invest_income")
	public BigDecimal getInvestIncome() {
		return investIncome;
	}
	
	public void setInvestIncome(BigDecimal investIncome) {
		this.investIncome = investIncome;
	}
	
	@Column(name = "return_capital")
	public BigDecimal getReturnCapital() {
		return returnCapital;
	}
	
	public void setReturnCapital(BigDecimal returnCapital) {
		this.returnCapital = returnCapital;
	}
	
	@Column(name = "return_interest")
	public BigDecimal getReturnInterest() {
		return returnInterest;
	}
	
	public void setReturnInterest(BigDecimal returnInterest) {
		this.returnInterest = returnInterest;
	}
	
	@Column(name = "platfom_income")
	public BigDecimal getPlatfomIncome() {
		return platfomIncome;
	}
	
	public void setPlatfomIncome(BigDecimal platfomIncome) {
		this.platfomIncome = platfomIncome;
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
}
