/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】企财宝员工银行卡
 */

@Entity
@Table(name = "qcb_employee_bankcard")
public class QcbEmployeeBankcard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3042211663271924244L;
	private String uuid;
	private String qcbEmployee;
	private String bank;
	private String branchBank;
	private String type;
	
	private String bankAccountName;
	private String bindingBankCard;
	private Timestamp createtime;
	private String status;
	
	private String bindingCardPhone;
	private String bindingCardCardholder;
	private String bindingId;
	
	private Boolean flagRemind;
	private Integer remindTime;
	
	public class QcbEmployeeBankcardType{
		public final static String DEBIT = "debit";
		public final static String CREDIT = "credit";
	}
	
	public class QcbEmployeeBankcardStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "qcb_employee", nullable = false, length = 36)
	public String getQcbEmployee() {
		return qcbEmployee;
	}

	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	@Column(name = "bank", nullable = false, length = 36)
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
	
	@Column(name = "type", nullable = false, length = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "bank_account_name", length = 50)
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Column(name = "binding_bank_card", nullable = false, length = 64)
	public String getBindingBankCard() {
		return bindingBankCard;
	}

	public void setBindingBankCard(String bindingBankCard) {
		this.bindingBankCard = bindingBankCard;
	}

	@Column(name = "binding_id")
	public String getBindingId() {
		return bindingId;
	}

	public void setBindingId(String bindingId) {
		this.bindingId = bindingId;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "binding_card_phone", nullable = false, length = 11)
	public String getBindingCardPhone() {
		return bindingCardPhone;
	}

	public void setBindingCardPhone(String bindingCardPhone) {
		this.bindingCardPhone = bindingCardPhone;
	}
	
	@Column(name = "binding_card_cardholder", nullable = false, length = 50)
	public String getBindingCardCardholder() {
		return bindingCardCardholder;
	}

	public void setBindingCardCardholder(String bindingCardCardholder) {
		this.bindingCardCardholder = bindingCardCardholder;
	}

	@Column(name = "flag_remind")
	public Boolean getFlagRemind() {
		return flagRemind;
	}

	public void setFlagRemind(Boolean flagRemind) {
		this.flagRemind = flagRemind;
	}

	@Column(name = "remind_time")
	public Integer getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Integer remindTime) {
		this.remindTime = remindTime;
	}
	
	
}
