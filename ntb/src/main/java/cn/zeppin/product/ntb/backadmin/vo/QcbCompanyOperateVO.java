package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyOperateVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2386484700517613480L;
	private String uuid;
	private String qcbCompany;
	private String type;
	private String typeCN;
	private String value;
	private String reason;
	private String status;
	private String statusCN;
	private String checker;
	private String checkerName;
	private Timestamp checktime;
	private String checktimeCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	private Timestamp submittime;
	private String submittimeCN;
	private QcbCompanyAccountFullVO oldData;
	private QcbCompanyAccountFullVO newData;
	
 	public QcbCompanyOperateVO(){
		
	}
	
	public QcbCompanyOperateVO(QcbCompanyOperate qco){
		this.uuid = qco.getUuid();
		this.qcbCompany = qco.getQcbCompany();
		this.status = qco.getStatus();
		if(QcbCompanyOperateStatus.UNCHECKED.equals(qco.getStatus())){
			statusCN = "审核中";
		}else if(QcbCompanyOperateStatus.CHECKED.equals(qco.getStatus())){
			statusCN = "审核通过";
		}else if(QcbCompanyOperateStatus.UNPASSED.equals(qco.getStatus())){
			statusCN = "审核未通过";
		}else if(QcbCompanyOperateStatus.DELETED.equals(qco.getStatus())){
			statusCN = "已删除";
		}
		this.type = qco.getType();
		if(QcbCompanyOperateType.EDIT.equals(qco.getType())){
			this.typeCN = "修改";
		}else if(QcbCompanyOperateType.DELETE.equals(qco.getType())){
			this.typeCN = "删除";
		}
		this.createtime = qco.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qco.getCreatetime());
		this.creator = qco.getCreator();
		
		this.value = qco.getValue();
		this.reason = qco.getReason();
		this.checker = qco.getChecker();
		this.checktime = qco.getChecktime();
		if(qco.getChecktime() != null && !"".equals(qco.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(qco.getChecktime());
		}else{
			this.checktimeCN = "";
		}
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

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCN() {
		return typeCN;
	}
	
	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
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

	public Timestamp getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}

	public String getSubmittimeCN() {
		return submittimeCN;
	}

	public void setSubmittimeCN(String submittimeCN) {
		this.submittimeCN = submittimeCN;
	}

	public QcbCompanyAccountFullVO getOldData() {
		return oldData;
	}

	public void setOldData(QcbCompanyAccountFullVO oldData) {
		this.oldData = oldData;
	}
	
	public QcbCompanyAccountFullVO getNewData() {
		return newData;
	}

	public void setNewData(QcbCompanyAccountFullVO newData) {
		this.newData = newData;
	}


	public String getCheckerName() {
		return checkerName;
	}
	

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
}
