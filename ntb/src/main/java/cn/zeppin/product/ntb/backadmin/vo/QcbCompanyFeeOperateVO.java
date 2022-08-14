package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate.QcbCompanyFeeOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate.QcbCompanyFeeOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyFeeOperateVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String qcbCompany;
	private String qcbCompanyName;
	private String fee;
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
	private Timestamp submittime;//提交时间
	private String submittimeCN;
	
	private QcbCompanyAccountFeeVO oldData;
	private QcbCompanyAccountFeeVO newData;
	
	public QcbCompanyFeeOperateVO(){
		
	}
	
	public QcbCompanyFeeOperateVO(QcbCompanyFeeOperate cato){
		this.uuid = cato.getUuid();
		this.type = cato.getType();
		if(QcbCompanyFeeOperateType.FEE_TICKET.equals(cato.getType())){
			this.typeCN = "平台手续费率";
		}
		this.value = cato.getValue();
		this.reason = cato.getReason();
		this.status = cato.getStatus();
		if(QcbCompanyFeeOperateStatus.UNCHECKED.equals(cato.getStatus())){
			this.statusCN = "待审核";
		}else if(QcbCompanyFeeOperateStatus.CHECKED.equals(cato.getStatus())){
			this.statusCN = "审核通过";
		}else if(QcbCompanyFeeOperateStatus.UNPASSED.equals(cato.getStatus())){
			this.statusCN = "审核不通过";
		}else if(QcbCompanyFeeOperateStatus.DELETED.equals(cato.getStatus())){
			this.statusCN = "已删除";
		}else if(QcbCompanyFeeOperateStatus.DRAFT.equals(cato.getStatus())){
			this.statusCN = "草稿";
		}
		this.checker = cato.getChecker();
		this.checktime = cato.getChecktime();
		if(cato.getChecktime() != null && !"".equals(cato.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(cato.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.creator = cato.getCreator();
		this.createtime = cato.getCreatetime();
		if(cato.getCreatetime() != null && !"".equals(cato.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(cato.getCreatetime());
		}else{
			this.createtimeCN = "";
		}
		this.submittime = cato.getSubmittime();
		if(cato.getSubmittime() != null && !"".equals(cato.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(cato.getSubmittime());
		}else{
			this.submittimeCN = "";
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

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
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
	
	public String getChecker() {
		return checker;
	}
	
	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	public String getCheckerName() {
		return checkerName;
	}
	
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
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

	
	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	
	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
	}

	public QcbCompanyAccountFeeVO getOldData() {
		return oldData;
	}

	public void setOldData(QcbCompanyAccountFeeVO oldData) {
		this.oldData = oldData;
	}

	public QcbCompanyAccountFeeVO getNewData() {
		return newData;
	}

	public void setNewData(QcbCompanyAccountFeeVO newData) {
		this.newData = newData;
	}
	
}
