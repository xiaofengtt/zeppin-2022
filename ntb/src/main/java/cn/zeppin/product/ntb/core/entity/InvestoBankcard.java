/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
 * @description 【数据对象】前台投资人银行卡信息
 */

@Entity
@Table(name = "investor_bankcard", uniqueConstraints = {@UniqueConstraint(columnNames = "binding_bank_card")})
public class InvestoBankcard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String investor;
	private String bank;
	private String branchBank;
	
	private String bankAccountName;
	private String bindingBankCard;
	private Timestamp bandingtime;
	private String status;
	
	
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

	@Column(name = "bank", nullable = false, length = 36)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "branch_bank", nullable = false, length = 36)
	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	@Column(name = "bank_account_name", nullable = false, length = 50)
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Column(name = "binding_bank_card", nullable = false, length = 30)
	public String getBindingBankCard() {
		return bindingBankCard;
	}

	public void setBindingBankCard(String bindingBankCard) {
		this.bindingBankCard = bindingBankCard;
	}

	@Column(name = "bandingtime", nullable = false)
	public Timestamp getBandingtime() {
		return bandingtime;
	}

	public void setBandingtime(Timestamp bandingtime) {
		this.bandingtime = bandingtime;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
