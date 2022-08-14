package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateType;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class CompanyAccountTransferOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String companyAccountHistory;
	private String type;
	private String typeCN;
	private String value;
	private String reason;
	private String status;
	private String statusCN;
	private String checker;
	private Timestamp checktime;
	private String creator;
	private String creatorName;
	private Timestamp submittime;
	private String submittimeCN;
	private Timestamp createtime;
	private String createtimeCN;
	private CompanyAccountHistoryDetailVO newData;
	
	private String receipt;
	private List<Resource> listReceipt;
	
 	public CompanyAccountTransferOperateDetailVO(){
		
	}
	
	public CompanyAccountTransferOperateDetailVO(CompanyAccountTransferOperate cato){
		this.uuid = cato.getUuid();
		this.companyAccountHistory = cato.getCompanyAccountHistory();
		this.type = cato.getType();
		if(CompanyAccountTransferOperateType.RECHARGE.equals(cato.getType())){
			this.typeCN = "充值";
		}else if(CompanyAccountTransferOperateType.EXPEND.equals(cato.getType())){
			this.typeCN = "费用录入";
		}else if(CompanyAccountTransferOperateType.TRANSFER.equals(cato.getType())){
			this.typeCN = "转账";
		}

		this.value = cato.getValue();
		this.reason = cato.getReason();
		this.status = cato.getStatus();
		if(CompanyAccountTransferOperateStatus.UNCHECKED.equals(cato.getStatus())){
			this.statusCN = "待审核";
		}else if(CompanyAccountTransferOperateStatus.CHECKED.equals(cato.getStatus())){
			this.statusCN = "审核通过";
		}else if(CompanyAccountTransferOperateStatus.UNPASSED.equals(cato.getStatus())){
			this.statusCN = "审核不通过";
		}else if(CompanyAccountTransferOperateStatus.DELETED.equals(cato.getStatus())){
			this.statusCN = "已删除";
		}else if(CompanyAccountTransferOperateStatus.DRAFT.equals(cato.getStatus())){
			this.statusCN = "草稿";
		}
		this.checker = cato.getChecker();
		this.checktime = cato.getChecktime();
		this.submittime = cato.getSubmittime();
		if(cato.getSubmittime() != null && !"".equals(cato.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(cato.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.creator = cato.getCreator();
		this.createtime = cato.getCreatetime();
		if(cato.getCreatetime() != null && !"".equals(cato.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(cato.getCreatetime());
		}else{
			this.createtimeCN = "";
		}
		this.receipt = cato.getReceipt() == null ? "" : cato.getReceipt();
		this.listReceipt = new ArrayList<Resource>();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCompanyAccountHistory() {
		return companyAccountHistory;
	}
	
	public void setCompanyAccountHistory(String companyAccountHistory) {
		this.companyAccountHistory = companyAccountHistory;
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

	public CompanyAccountHistoryDetailVO getNewData() {
		return newData;
	}
	
	public void setNewData(CompanyAccountHistoryDetailVO newData) {
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

	
	public List<Resource> getListReceipt() {
		return listReceipt;
	}
	

	public void setListReceipt(List<Resource> listReceipt) {
		this.listReceipt = listReceipt;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
}
