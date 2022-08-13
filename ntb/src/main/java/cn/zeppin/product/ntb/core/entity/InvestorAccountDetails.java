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
 * @author Clark.R 2016年9月19日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】前台投资人账户明细
 */

@Entity
@Table(name = "investor_account_details")
public class InvestorAccountDetails extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String investor;
	private Timestamp datetime;
	
	private String type;
	
	
	private BigDecimal income;
	private BigDecimal pay;
	private BigDecimal accountBalance;
	private String remark;
	private String bankcard;
	
	private String status;
	
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "investor", length = 36)
	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	@Column(name = "datetime", nullable = false, length = 36)
	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "income", length = 20)
	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	@Column(name = "pay", length = 20)
	public BigDecimal getPay() {
		return pay;
	}

	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}

	@Column(name = "account_balance", nullable = false, length = 20)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Column(name = "remark", length = 1000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "bankcard", length = 36)
	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
