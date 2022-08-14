package cn.zeppin.product.ntb.qcb.vo;

import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.base.Entity;

public class QcbVirtualAccountsVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String qcbCompany;
	private String companyAccount;
	private String companyAccountName;
	private String companyAccountNum;
	private String branchBankName;
	private String branchBankAddress;
	private String accountNum;
	
 	public QcbVirtualAccountsVO(){
		
	}
	
	public QcbVirtualAccountsVO(QcbVirtualAccounts qva){
		this.uuid = qva.getUuid();
		this.qcbCompany = qva.getQcbCompany();
		this.companyAccount = qva.getCompanyAccount();
		this.accountNum = qva.getAccountNum();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
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

	public String getBranchBankAddress() {
		return branchBankAddress;
	}

	public void setBranchBankAddress(String branchBankAddress) {
		this.branchBankAddress = branchBankAddress;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
}
