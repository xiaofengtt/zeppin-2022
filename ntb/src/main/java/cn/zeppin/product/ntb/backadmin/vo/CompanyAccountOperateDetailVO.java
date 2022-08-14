package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class CompanyAccountOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String companyAccount;
	private String type;
	private String typeCN;
	private String value;
	private String reason;
	private String status;
	private String statusCN;
	private String checker;
	private Timestamp checktime;
	private String checktimeCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	private Timestamp submittime;
	private String submittimeCN;
	private CompanyAccountDetailVO oldData;
	private CompanyAccountDetailVO newData;
	
	public CompanyAccountOperateDetailVO(){
		
	}
	
	public CompanyAccountOperateDetailVO(CompanyAccountOperate cao){
		this.uuid = cao.getUuid();
		this.companyAccount = cao.getCompanyAccount();
		this.type = cao.getType();
		if(CompanyAccountOperateType.ADD.equals(cao.getType())){
			this.typeCN = "添加";
		}else if(CompanyAccountOperateType.EDIT.equals(cao.getType())){
			this.typeCN = "修改";
		}else if(CompanyAccountOperateType.DELETE.equals(cao.getType())){
			this.typeCN = "删除";
		}

		this.value = cao.getValue();
		this.reason = cao.getReason();
		this.status = cao.getStatus();
		if(CompanyAccountOperateStatus.UNCHECKED.equals(cao.getStatus())){
			this.statusCN = "待审核";
		}else if(CompanyAccountOperateStatus.CHECKED.equals(cao.getStatus())){
			this.statusCN = "审核通过";
		}else if(CompanyAccountOperateStatus.UNPASSED.equals(cao.getStatus())){
			this.statusCN = "审核不通过";
		}else if(CompanyAccountOperateStatus.DELETED.equals(cao.getStatus())){
			this.statusCN = "已删除";
		}else if(CompanyAccountOperateStatus.DRAFT.equals(cao.getStatus())){
			this.statusCN = "草稿";
		}
		this.checker = cao.getChecker();
		this.checktime = cao.getChecktime();
		if(cao.getChecktime() != null && !"".equals(cao.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(cao.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.submittime = cao.getSubmittime();
		if(cao.getSubmittime() != null && !"".equals(cao.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(cao.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.creator = cao.getCreator();
		this.createtime = cao.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(cao.getCreatetime());
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCompanyAccount() {
		return companyAccount;
	}
	
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
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

	public CompanyAccountDetailVO getOldData() {
		return oldData;
	}
	
	public void setOldData(CompanyAccountDetailVO oldData) {
		this.oldData = oldData;
	}

	public CompanyAccountDetailVO getNewData() {
		return newData;
	}
	
	public void setNewData(CompanyAccountDetailVO newData) {
		this.newData = newData;
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
	
}
