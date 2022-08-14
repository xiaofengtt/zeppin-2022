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
 * @description 【数据对象】企财宝企业账户
 */

@Entity
@Table(name = "qcb_company_account", uniqueConstraints = {@UniqueConstraint(columnNames = "merchant_id")})
public class QcbCompanyAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1607242281354963963L;
	private String uuid;
	private String merchantId;
	private String name;
	private String bkArea;
	private String address;
	private String phone;
	private String corporation;
	private String connectionCompany;
	private String taxIdentificationNum;
	private String secretPassword;
	private String openBank;
	private String openBankCardnum;
	private String contacts;
	private String contactsMobile;
	private String contactsIdcard;
	private String contactsEmail;
	private BigDecimal accountBalance;
	private BigDecimal accountPay;
	private BigDecimal totalInvest;
	private BigDecimal totalReturn;
	private Timestamp createtime;
	private String status;
	private String virtualAccountType;
	private String qcbVirtualAccounts;
	private String financeStatus;
	private String logo;
	
	private String accredit;
	private String accreditMobile;
	
	private String taxCompany;
	private String taxAddress;
	private String taxPhone;
	
	private String authStatus;
	
	private String businessLicence;
	private String evidence;
	private String idcardFace;
	private String idcardBack;
	
	private BigDecimal feeTicket;
	
	public class QcbCompanyAccountStatus{
		public final static String NORMAL = "normal";
		public final static String UNCHECK = "uncheck";
		public final static String UNAUTH = "unauth";
		public final static String NOPASS = "nopass";
		public final static String DELETED = "deleted";
	}
	
	public class QcbCompanyAccountAuthStatus{
		public final static String UNAUTH = "unauth";
		public final static String UNCHECK = "uncheck";
		public final static String NORMAL = "normal";
		public final static String NOPASS = "nopass";
	}
	
	public class QcbCompanyAccountFinanceStatus{
		public final static String NORMAL = "normal";
		public final static String UNCHECK = "uncheck";
		public final static String UNAUTH = "unauth";
		public final static String DELETED = "deleted";
	}
	
	public class QcbCompanyAccountVirtualAccountType{
		public final static String VIRTUAL = "virtual";
		public final static String REALLY = "really";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "merchant_id", unique = true, nullable = false, length = 20)
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "bk_area", nullable = false,  length = 36)
	public String getBkArea() {
		return bkArea;
	}
	
	public void setBkArea(String bkArea) {
		this.bkArea = bkArea;
	}
	
	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "corporation", length = 30)
	public String getCorporation() {
		return corporation;
	}
	
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	@Column(name = "connection_company", length = 50)
	public String getConnectionCompany() {
		return connectionCompany;
	}
	
	public void setConnectionCompany(String connectionCompany) {
		this.connectionCompany = connectionCompany;
	}

	@Column(name = "tax_identification_num", length = 20)
	public String getTaxIdentificationNum() {
		return taxIdentificationNum;
	}
	
	public void setTaxIdentificationNum(String taxIdentificationNum) {
		this.taxIdentificationNum = taxIdentificationNum;
	}
	
	@Column(name = "secret_password", length = 50)
	public String getSecretPassword() {
		return secretPassword;
	}

	public void setSecretPassword(String secretPassword) {
		this.secretPassword = secretPassword;
	}

	@Column(name = "open_bank", length = 50)
	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	@Column(name = "open_bank_cardnum", length = 30)
	public String getOpenBankCardnum() {
		return openBankCardnum;
	}

	public void setOpenBankCardnum(String openBankCardnum) {
		this.openBankCardnum = openBankCardnum;
	}

	@Column(name = "contacts", length = 20)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "contacts_mobile", length = 20)
	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	@Column(name = "contacts_idcard", length = 20)
	public String getContactsIdcard() {
		return contactsIdcard;
	}

	public void setContactsIdcard(String contactsIdcard) {
		this.contactsIdcard = contactsIdcard;
	}

	@Column(name = "contacts_email", length = 50)
	public String getContactsEmail() {
		return contactsEmail;
	}

	public void setContactsEmail(String contactsEmail) {
		this.contactsEmail = contactsEmail;
	}
	
	@Column(name = "total_invest", nullable = false, length = 20)
	public BigDecimal getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
	}

	@Column(name = "total_return", nullable = false, length = 20)
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}
	
	@Column(name = "account_balance", nullable = false, length = 20)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Column(name = "account_pay", nullable = false, length = 20)
	public BigDecimal getAccountPay() {
		return accountPay;
	}

	public void setAccountPay(BigDecimal accountPay) {
		this.accountPay = accountPay;
	}
	
	@Column(name = "virtual_account_type", length = 20)
	public String getVirtualAccountType() {
		return virtualAccountType;
	}

	public void setVirtualAccountType(String virtualAccountType) {
		this.virtualAccountType = virtualAccountType;
	}
	
	@Column(name = "qcb_virtual_accounts", length = 36)
	public String getQcbVirtualAccounts() {
		return qcbVirtualAccounts;
	}

	public void setQcbVirtualAccounts(String qcbVirtualAccounts) {
		this.qcbVirtualAccounts = qcbVirtualAccounts;
	}

	@Column(name = "logo", length = 36)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Column(name = "finance_status", nullable = false)
	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "accredit", nullable = false)
	public String getAccredit() {
		return accredit;
	}
	
	public void setAccredit(String accredit) {
		this.accredit = accredit;
	}
	
	@Column(name = "accredit_mobile", nullable = false)
	public String getAccreditMobile() {
		return accreditMobile;
	}

	public void setAccreditMobile(String accreditMobile) {
		this.accreditMobile = accreditMobile;
	}

	@Column(name = "tax_company")
	public String getTaxCompany() {
		return taxCompany;
	}

	public void setTaxCompany(String taxCompany) {
		this.taxCompany = taxCompany;
	}

	@Column(name = "tax_address")
	public String getTaxAddress() {
		return taxAddress;
	}

	public void setTaxAddress(String taxAddress) {
		this.taxAddress = taxAddress;
	}

	@Column(name = "tax_phone")
	public String getTaxPhone() {
		return taxPhone;
	}

	public void setTaxPhone(String taxPhone) {
		this.taxPhone = taxPhone;
	}

	@Column(name = "auth_status", nullable = false, length = 20)
	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	
	@Column(name = "business_licence", length = 36)
	public String getBusinessLicence() {
		return businessLicence;
	}
	
	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	
	@Column(name = "evidence", length = 36)
	public String getEvidence() {
		return evidence;
	}
	
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	
	@Column(name = "idcard_face", length = 36)
	public String getIdcardFace() {
		return idcardFace;
	}

	public void setIdcardFace(String idcardFace) {
		this.idcardFace = idcardFace;
	}
	
	@Column(name = "idcard_back", length = 36)
	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}


	@Column(name = "fee_ticket")
	public BigDecimal getFeeTicket() {
		return feeTicket;
	}
	

	public void setFeeTicket(BigDecimal feeTicket) {
		this.feeTicket = feeTicket;
	}
}
