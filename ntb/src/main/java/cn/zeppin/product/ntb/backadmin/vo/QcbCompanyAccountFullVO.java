package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyAccountFullVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4474928969951847392L;
	private String uuid;
	private String merchantId;
	private String name;
	private String phone;
	private String corporation;
	
	private String bkArea;
	private String bkAreaCN;
	private Map<String, Object> areaInfo;
	
	private String address;
	
	private String taxIdentificationNum;
	private String taxCompany;
	private String taxAddress;
	private String taxPhone;
	private String openBank;
	private String openBankCardnum;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal accountPay;
	private String accountPayCN;
	private BigDecimal totalInvest;
	private String totalInvestCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;
	private BigDecimal totalAmount;
	private String totalAmountCN;
	
	private String virtualAccountType;
	private String qcbVirtualAccounts;
	private String companyAccount;
	private String companyAccountName;
	private String companyAccountNum;
	private String branchBankName;
	private String accountNum;
	
	private String contacts;
	private String contactsMobile;
	private String contactsIdcard;
	private String contactsEmail;
	
	private String logo;
	private String logoUrl;
	
	private String checkerName;
	private String checktimeCN;
	private String checkStatusCN;
	
	private String status;
	private String statusCN;
	private Timestamp createtime;
	private String createtimeCN;
	
	private String authStatus;
	private String authStatusCN;
	
	private String businessLicence;
	private String businessLicenceURL;
	private String evidence;
	private String evidenceURL;
	private String idcardFace;
	private String idcardFaceURL;
	private String idcardBack;
	private String idcardBackURL;
	
	private BigDecimal feeTicket;
	private String feeTicketCN;
	
	
	public QcbCompanyAccountFullVO(){
		
	}
	
	public QcbCompanyAccountFullVO(QcbCompanyAccount company){
		this.uuid = company.getUuid();
		this.merchantId = company.getMerchantId();
		this.name = company.getName();
		this.phone = company.getPhone() == null ? "" : company.getPhone();
		this.corporation = company.getCorporation() == null ? "" : company.getCorporation();
		
		this.bkArea = company.getBkArea();
		this.address = company.getAddress();
		
		this.taxIdentificationNum = company.getTaxIdentificationNum();
		this.taxCompany = company.getTaxCompany();
		this.taxAddress = company.getTaxAddress();
		this.taxPhone = company.getTaxPhone();
		this.openBank = company.getOpenBank();
		this.openBankCardnum = company.getOpenBankCardnum();
		
		this.contacts = company.getContacts();
		this.contactsMobile = company.getContactsMobile();
		this.contactsIdcard = company.getContactsIdcard();
		this.contactsEmail = company.getContactsEmail();
		
		this.accountPay = company.getAccountPay();
		this.accountPayCN = Utlity.numFormat4UnitDetail(company.getAccountPay());
		this.accountBalance = company.getAccountBalance();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(company.getAccountBalance());
		this.totalInvest = company.getTotalInvest();
		this.totalInvestCN = Utlity.numFormat4UnitDetail(company.getTotalInvest());
		this.totalReturn = company.getTotalReturn();
		this.totalReturnCN = Utlity.numFormat4UnitDetail(company.getTotalReturn());
		this.totalAmount = this.accountBalance.add(this.totalInvest);
		this.totalAmountCN = Utlity.numFormat4UnitDetail(this.totalAmount);
		
		this.qcbVirtualAccounts = company.getQcbVirtualAccounts();
		this.virtualAccountType = company.getVirtualAccountType();
		this.logo = company.getLogo();
		
		this.status = company.getStatus();
		if(QcbCompanyAccountStatus.UNAUTH.equals(company.getStatus())){
			this.statusCN = "未认证";
		} else if (QcbCompanyAccountStatus.NORMAL.equals(company.getStatus())) {
			this.statusCN = "已认证";
		} else if (QcbCompanyAccountStatus.NOPASS.equals(company.getStatus())) {
			this.statusCN = "认证未通过";
		} else if (QcbCompanyAccountStatus.DELETED.equals(company.getStatus())) {
			this.statusCN = "已删除";
		} else if (QcbCompanyAccountStatus.UNCHECK.equals(company.getStatus())) {
			this.statusCN = "审核中";
		} else {
			this.statusCN = "--";
		}
		
		this.authStatus = company.getAuthStatus();
		if(QcbCompanyAccountAuthStatus.UNAUTH.equals(company.getAuthStatus())){
			this.authStatusCN = "未认证";
		} else if (QcbCompanyAccountAuthStatus.NORMAL.equals(company.getAuthStatus())) {
			this.authStatusCN = "已认证";
		} else if (QcbCompanyAccountAuthStatus.NOPASS.equals(company.getAuthStatus())) {
			this.authStatusCN = "认证未通过";
		} else if (QcbCompanyAccountAuthStatus.UNCHECK.equals(company.getAuthStatus())) {
			this.authStatusCN = "审核中";
		} else {
			this.authStatusCN = "--";
		}
		
		this.createtime = company.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaString(company.getCreatetime());
		
		this.businessLicence = company.getBusinessLicence();
		this.evidence = company.getEvidence();
		this.idcardFace = company.getIdcardFace();
		this.idcardBack = company.getIdcardBack();
		
		this.feeTicket = company.getFeeTicket();
		if(company.getFeeTicket() != null){
			this.feeTicketCN = Utlity.numFormat4UnitDetail(company.getFeeTicket().multiply(BigDecimal.valueOf(100)));
		} else {
			this.feeTicketCN = "--";
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getTaxIdentificationNum() {
		return taxIdentificationNum;
	}

	public void setTaxIdentificationNum(String taxIdentificationNum) {
		this.taxIdentificationNum = taxIdentificationNum;
	}

	public String getTaxCompany() {
		return taxCompany;
	}

	public void setTaxCompany(String taxCompany) {
		this.taxCompany = taxCompany;
	}

	public String getTaxAddress() {
		return taxAddress;
	}

	public void setTaxAddress(String taxAddress) {
		this.taxAddress = taxAddress;
	}

	public String getTaxPhone() {
		return taxPhone;
	}

	public void setTaxPhone(String taxPhone) {
		this.taxPhone = taxPhone;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getOpenBankCardnum() {
		return openBankCardnum;
	}

	public void setOpenBankCardnum(String openBankCardnum) {
		this.openBankCardnum = openBankCardnum;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public String getContactsIdcard() {
		return contactsIdcard;
	}

	public void setContactsIdcard(String contactsIdcard) {
		this.contactsIdcard = contactsIdcard;
	}

	public String getContactsEmail() {
		return contactsEmail;
	}

	public void setContactsEmail(String contactsEmail) {
		this.contactsEmail = contactsEmail;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}

	public BigDecimal getAccountPay() {
		return accountPay;
	}

	public void setAccountPay(BigDecimal accountPay) {
		this.accountPay = accountPay;
	}

	public String getAccountPayCN() {
		return accountPayCN;
	}

	public void setAccountPayCN(String accountPayCN) {
		this.accountPayCN = accountPayCN;
	}

	public BigDecimal getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
	}

	public String getTotalInvestCN() {
		return totalInvestCN;
	}

	public void setTotalInvestCN(String totalInvestCN) {
		this.totalInvestCN = totalInvestCN;
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getVirtualAccountType() {
		return virtualAccountType;
	}

	public void setVirtualAccountType(String virtualAccountType) {
		this.virtualAccountType = virtualAccountType;
	}

	public String getQcbVirtualAccounts() {
		return qcbVirtualAccounts;
	}

	public void setQcbVirtualAccounts(String qcbVirtualAccounts) {
		this.qcbVirtualAccounts = qcbVirtualAccounts;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getCompanyAccountName() {
		return companyAccountName;
	}

	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
	}

	public String getCompanyAccountNum() {
		return companyAccountNum;
	}

	public void setCompanyAccountNum(String companyAccountNum) {
		this.companyAccountNum = companyAccountNum;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public String getChecktimeCN() {
		return checktimeCN;
	}

	public void setChecktimeCN(String checktimeCN) {
		this.checktimeCN = checktimeCN;
	}

	public String getCheckStatusCN() {
		return checkStatusCN;
	}

	public void setCheckStatusCN(String checkStatusCN) {
		this.checkStatusCN = checkStatusCN;
	}

	
	public String getAuthStatus() {
		return authStatus;
	}
	

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	

	public String getAuthStatusCN() {
		return authStatusCN;
	}
	

	public void setAuthStatusCN(String authStatusCN) {
		this.authStatusCN = authStatusCN;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getBusinessLicenceURL() {
		return businessLicenceURL;
	}

	public void setBusinessLicenceURL(String businessLicenceURL) {
		this.businessLicenceURL = businessLicenceURL;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getEvidenceURL() {
		return evidenceURL;
	}

	public void setEvidenceURL(String evidenceURL) {
		this.evidenceURL = evidenceURL;
	}

	public String getIdcardFace() {
		return idcardFace;
	}

	public void setIdcardFace(String idcardFace) {
		this.idcardFace = idcardFace;
	}

	public String getIdcardFaceURL() {
		return idcardFaceURL;
	}

	public void setIdcardFaceURL(String idcardFaceURL) {
		this.idcardFaceURL = idcardFaceURL;
	}

	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	public String getIdcardBackURL() {
		return idcardBackURL;
	}

	public void setIdcardBackURL(String idcardBackURL) {
		this.idcardBackURL = idcardBackURL;
	}
	
	public String getBkArea() {
		return bkArea;
	}
	
	public void setBkArea(String bkArea) {
		this.bkArea = bkArea;
	}

	public String getBkAreaCN() {
		return bkAreaCN;
	}

	public void setBkAreaCN(String bkAreaCN) {
		this.bkAreaCN = bkAreaCN;
	}

	public Map<String, Object> getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(Map<String, Object> areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public BigDecimal getFeeTicket() {
		return feeTicket;
	}
	

	public void setFeeTicket(BigDecimal feeTicket) {
		this.feeTicket = feeTicket;
	}
	

	public String getFeeTicketCN() {
		return feeTicketCN;
	}
	

	public void setFeeTicketCN(String feeTicketCN) {
		this.feeTicketCN = feeTicketCN;
	}
}
