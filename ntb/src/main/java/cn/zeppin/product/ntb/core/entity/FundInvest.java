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
 * @description 【数据对象】活期理财产品投资信息
 */

@Entity
@Table(name = "fund_invest")
public class FundInvest extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1247487393527835513L;
	
	private String uuid;
	private String fund;
	private String fundPublish;
	private String companyAccount;
	private BigDecimal accountBalance;
	private BigDecimal totalAmount;
	private String type;
	private String creator;
	private Timestamp createtime;
	
	public class FundInvestType{
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
	
	@Column(name = "fund", nullable = false, length = 36)
	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}
	
	@Column(name = "fund_publish", nullable = false, length = 36)
	public String getFundPublish() {
		return fundPublish;
	}

	public void setFundPublish(String fundPublish) {
		this.fundPublish = fundPublish;
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
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
