package cn.zeppin.product.ntb.qcb.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFinanceApply.QcbCompanyFinanceApplyStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyFinanceApplyVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4740659141418123237L;
	
	private Boolean flagApply;
	private String uuid;
	private String contacts;
	private String contactsMobile;
	private String status;
	private String statusCN;
	private String checkreason;
	private Timestamp checktime;
	private String checktimeCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public QcbCompanyFinanceApplyVO(){
		this.flagApply = false;
	}
	
	public QcbCompanyFinanceApplyVO(QcbCompanyFinanceApply qcfa){
		this.flagApply = true;
		this.uuid = qcfa.getUuid();
		this.contacts = qcfa.getContacts();
		this.contactsMobile = qcfa.getContactsMobile();
		this.status = qcfa.getStatus();
		if(QcbCompanyFinanceApplyStatus.CHECKED.equals(qcfa.getStatus())){
			this.statusCN = "已通过";
		} else if (QcbCompanyFinanceApplyStatus.DELETED.equals(qcfa.getStatus())){
			this.statusCN = "已删除";
		} else if (QcbCompanyFinanceApplyStatus.UNCHECKED.equals(qcfa.getStatus())){
			this.statusCN = "未审核";
		} else if (QcbCompanyFinanceApplyStatus.UNPASSED.equals(qcfa.getStatus())){
			this.statusCN = "未通过";
		} else {
			this.statusCN = "--";
		}
		this.checkreason = qcfa.getCheckreason();
		this.checktime = qcfa.getChecktime();
		if(qcfa.getChecktime() != null){
			this.checktimeCN = Utlity.timeSpanToChinaString(qcfa.getChecktime());
		} else {
			this.checktimeCN = "未审核";
		}
		this.creator = qcfa.getCreator();
		
		this.createtime = qcfa.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaString(qcfa.getCreatetime());
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
	}
	

	public String getStatusCN() {
		return statusCN;
	}
	

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	

	public String getCheckreason() {
		return checkreason;
	}
	

	public void setCheckreason(String checkreason) {
		this.checkreason = checkreason;
	}
	

	public Timestamp getChecktime() {
		return checktime;
	}
	

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}
	

	public String getChecktimeCN() {
		return checktimeCN;
	}
	

	public void setChecktimeCN(String checktimeCN) {
		this.checktimeCN = checktimeCN;
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

	public Boolean getFlagApply() {
		return flagApply;
	}

	public void setFlagApply(Boolean flagApply) {
		this.flagApply = flagApply;
	}
}
