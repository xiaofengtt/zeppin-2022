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
 * @description 【数据对象】企财宝企业银行卡
 */

@Entity
@Table(name = "qcb_company_bankcard")
public class QcbCompanyBankcard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3042211663271924244L;
	private String uuid;
	private String qcbCompany;
	private String bank;
	private String branchBank;
	private String bkArea;
	
	private String bindingBankCard;
	private String bindingCardPhone;
	private String bindingCardType;
	private String bindingCardCardholder;
	
	private String status;
	private Timestamp createtime;
	private String creator;
	
	public class QcbCompanyBankcardStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	public class QcbCompanyBankcardType{
		public final static String COMPANY = "company";
		public final static String PERSONAL = "personal";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "qcb_company", nullable = false, length = 36)
	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
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

	@Column(name = "bk_area", length = 36)
	public String getBkArea() {
		return bkArea;
	}

	public void setBkArea(String bkArea) {
		this.bkArea = bkArea;
	}
	
	@Column(name = "binding_card_cardholder", nullable = false, length = 50)
	public String getBindingCardCardholder() {
		return bindingCardCardholder;
	}

	public void setBindingCardCardholder(String bindingCardCardholder) {
		this.bindingCardCardholder = bindingCardCardholder;
	}

	@Column(name = "binding_bank_card", nullable = false, length = 64)
	public String getBindingBankCard() {
		return bindingBankCard;
	}

	public void setBindingBankCard(String bindingBankCard) {
		this.bindingBankCard = bindingBankCard;
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
	
	@Column(name = "binding_card_type", nullable = false, length = 11)
	public String getBindingCardType() {
		return bindingCardType;
	}

	public void setBindingCardType(String bindingCardType) {
		this.bindingCardType = bindingCardType;
	}

	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
