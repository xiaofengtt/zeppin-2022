package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts.QcbVirtualAccountsStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbVirtualAccountsDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String qcbCompany;
	private String qcbCompanyId;
	private String qcbCompanyName;
	private String companyAccount;
	private String companyAccountName;
	private String companyAccountNum;
	private String branchBankName;
	private String branchBankAddress;
	private String accountNum;
	private String status;
	private String statusCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String creator;
	private String creatorName;
	
 	public QcbVirtualAccountsDetailVO(){
		
	}
	
	public QcbVirtualAccountsDetailVO(QcbVirtualAccounts qva){
		this.uuid = qva.getUuid();
		this.qcbCompany = qva.getQcbCompany();
		this.companyAccount = qva.getCompanyAccount();
		this.accountNum = qva.getAccountNum();
		this.status = qva.getStatus();
		if(QcbVirtualAccountsStatus.NORMAL.equals(qva.getStatus())){
			this.statusCN = "未绑定";
		}else if(QcbVirtualAccountsStatus.USED.equals(qva.getStatus())){
			this.statusCN = "已绑定";
		}
		this.createtime = qva.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qva.getCreatetime());
		this.creator = qva.getCreator();
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

	public String getQcbCompanyId() {
		return qcbCompanyId;
	}

	public void setQcbCompanyId(String qcbCompanyId) {
		this.qcbCompanyId = qcbCompanyId;
	}

	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}
