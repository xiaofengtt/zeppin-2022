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
 * @author Clark.R 2016年9月19日
 * @description 【数据对象】投资人理财产品购买关系
 */

@Entity
@Table(name = "investor_product_buy", uniqueConstraints = {@UniqueConstraint(columnNames = {"investor","product"})})
public class InvestorProductBuy extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6121638623124350691L;
	
	private String uuid;
	private String investor;
	private String product;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private Timestamp createtime;
	private String stage;
	
	private BigDecimal accountBalance;
	private BigDecimal totalRedeem;
	private String type;
	
	
	public class InvestorProductBuyStage{
		public final static String CONFIRMING = "confirming";//确认中
		public final static String PROFIT = "profit";//收益中（持有中）
		public final static String BALANCE = "balance";//结算还款中
		public final static String FINISHED = "finished";//已完成
	}
	
	public class InvestorProductBuyProductType{
		public final static String BANK_PRODUCT = "bank_product";//银行理财产品
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "investor", nullable = false, length = 36)
	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	@Column(name = "product", nullable = false, length = 36)
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	@Column(name = "total_amount", nullable = false)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "total_return", nullable = false)
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}
	
	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}
	
	@Column(name = "stage", nullable = false, length = 36)
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

	@Column(name = "account_balance", nullable = false)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Column(name = "total_redeem", nullable = false)
	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}
	
	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
