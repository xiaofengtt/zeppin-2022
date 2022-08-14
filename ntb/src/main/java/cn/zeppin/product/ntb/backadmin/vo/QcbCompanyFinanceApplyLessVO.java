package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountFinanceStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyFinanceApplyLessVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2824567655547876403L;
	
	private String uuid;
	private String contacts;
	private String contactsMobile;
	private String status;
	private String statusCN;
	private Timestamp applytime;
	private String applytimeCN;
	
	private String qcbCompany;
	private String qcbCompanyName;
	private String qcbCompanyMerchantId;
	private String qcbCompanyArea;
	private String qcbCompanyAreaStr;
	
	public QcbCompanyFinanceApplyLessVO(){
	}
	
	public QcbCompanyFinanceApplyLessVO(QcbCompanyFinanceApply qcfa){
		this.uuid = qcfa.getUuid();
		this.contacts = qcfa.getContacts();
		this.contactsMobile = qcfa.getContactsMobile();
		this.applytime = qcfa.getCreatetime();
		if(qcfa.getCreatetime() != null){
			this.applytimeCN = Utlity.timeSpanToChinaString(qcfa.getCreatetime());
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		if(QcbCompanyAccountFinanceStatus.NORMAL.equals(status)){
			this.statusCN = "已通过";
		} else if (QcbCompanyAccountFinanceStatus.DELETED.equals(status)){
			this.statusCN = "已删除";
		} else if (QcbCompanyAccountFinanceStatus.UNCHECK.equals(status)){
			this.statusCN = "申请中";
		} else if (QcbCompanyAccountFinanceStatus.UNAUTH.equals(status)){
			this.statusCN = "未申请";
		} else {
			this.statusCN = "--";
		}
	}

	public String getStatusCN() {
		return statusCN;
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public Timestamp getApplytime() {
		return applytime;
	}

	public void setApplytime(Timestamp applytime) {
		this.applytime = applytime;
		if(applytime != null){
			this.applytimeCN = Utlity.timeSpanToChinaString(applytime);
		}
	}

	public String getApplytimeCN() {
		return applytimeCN;
	}

	public void setApplytimeCN(String applytimeCN) {
		this.applytimeCN = applytimeCN;
	}

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	public String getQcbCompanyMerchantId() {
		return qcbCompanyMerchantId;
	}
	
	public void setQcbCompanyMerchantId(String qcbCompanyMerchantId) {
		this.qcbCompanyMerchantId = qcbCompanyMerchantId;
	}

	public String getQcbCompanyArea() {
		return qcbCompanyArea;
	}

	public void setQcbCompanyArea(String qcbCompanyArea) {
		this.qcbCompanyArea = qcbCompanyArea;
	}

	public String getQcbCompanyAreaStr() {
		return qcbCompanyAreaStr;
	}

	public void setQcbCompanyAreaStr(String qcbCompanyAreaStr) {
		this.qcbCompanyAreaStr = qcbCompanyAreaStr;
	}

	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
	}
}
