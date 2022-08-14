package cn.zeppin.product.ntb.qcb.vo;

import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class QcbCompanyAccountDetailVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4474928969951847392L;
	private String uuid;
	
	private String merchantId;
	private String name;
	
	private String bkArea;
	private String bkAreaCN;
	private Map<String, Object> areaInfo;
	
	private String address;
	private String phone;
	private String corporation;
	private String connectionCompany;
	
	private String taxIdentificationNum;
	private String taxCompany;
	private String taxAddress;
	private String taxPhone;
	private String openBank;
	private String openBankCardnum;
	
	private String contacts;
	private String contactsMobile;
	private String contactsIdcard;
	private String contactsEmail;
	
	private String status;
	private String statusCN;
	
	private String logo;
	private String logoUrl;
	
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
	
	private String checkerName;
	private String checktimeCN;
	private String checkStatusCN;
	private String checkReason;
	
	
	public QcbCompanyAccountDetailVO(){
		
	}
	
	public QcbCompanyAccountDetailVO(QcbCompanyAccount company){
		this.uuid = company.getUuid();
		this.merchantId = company.getMerchantId();
		this.name = company.getName();
		this.bkArea = company.getBkArea();
		this.address = company.getAddress();
		this.phone = company.getPhone();
		this.corporation = company.getCorporation();
		this.connectionCompany = company.getConnectionCompany();
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
		
		this.logo = company.getLogo();
		this.businessLicence = company.getBusinessLicence();
		this.evidence = company.getEvidence();
		this.idcardFace = company.getIdcardFace();
		this.idcardBack = company.getIdcardBack();
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

	public String getBkArea() {
		return bkArea;
	}

	public void setBkArea(String bkArea) {
		this.bkArea = bkArea;
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

	public String getConnectionCompany() {
		return connectionCompany;
	}

	public void setConnectionCompany(String connectionCompany) {
		this.connectionCompany = connectionCompany;
	}

	public String getTaxIdentificationNum() {
		return taxIdentificationNum;
	}

	public void setTaxIdentificationNum(String taxIdentificationNum) {
		this.taxIdentificationNum = taxIdentificationNum;
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

	public String getBkAreaCN() {
		return bkAreaCN;
	}

	public void setBkAreaCN(String bkAreaCN) {
		this.bkAreaCN = bkAreaCN;
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

	public String getCheckReason() {
		return checkReason;
	}

	public void setCheckReason(String checkReason) {
		this.checkReason = checkReason;
	}
}
