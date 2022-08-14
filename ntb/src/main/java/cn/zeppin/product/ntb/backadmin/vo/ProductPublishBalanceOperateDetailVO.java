package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class ProductPublishBalanceOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String bankFinancialProductPublish;
	private BankFinancialProductPublishDetailsVO bankFinancialProductPublishInfo;
	private String value;
	private String reason;
	private String status;
	private String statusCN;
	private String checker;
	private Timestamp checktime;
	private Timestamp submittime;
	private String submittimeCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public ProductPublishBalanceOperateDetailVO(){
		
	}
	
	public ProductPublishBalanceOperateDetailVO(ProductPublishBalanceOperate ppbo){
		this.uuid = ppbo.getUuid();
		this.bankFinancialProductPublish = ppbo.getBankFinancialProductPublish();
		this.value = ppbo.getValue();
		this.reason = ppbo.getReason();
		this.status = ppbo.getStatus();
		if(BankFinancialProductPublishOperateStatus.UNCHECKED.equals(ppbo.getStatus())){
			this.statusCN = "待审核";
		}else if(BankFinancialProductPublishOperateStatus.CHECKED.equals(ppbo.getStatus())){
			this.statusCN = "审核通过";
		}else if(BankFinancialProductPublishOperateStatus.UNPASSED.equals(ppbo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(BankFinancialProductPublishOperateStatus.DELETED.equals(ppbo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = ppbo.getChecker();
		this.checktime = ppbo.getChecktime();
		this.submittime = ppbo.getSubmittime();
		if(ppbo.getSubmittime() != null && !"".equals(ppbo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(ppbo.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.creator = ppbo.getCreator();
		this.createtime = ppbo.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(ppbo.getCreatetime());
		
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getBankFinancialProductPublish() {
		return bankFinancialProductPublish;
	}
	
	public void setBankFinancialProductPublish(String bankFinancialProductPublish) {
		this.bankFinancialProductPublish = bankFinancialProductPublish;
	}
	
	public BankFinancialProductPublishDetailsVO getBankFinancialProductPublishInfo() {
		return bankFinancialProductPublishInfo;
	}

	public void setBankFinancialProductPublishInfo(BankFinancialProductPublishDetailsVO bankFinancialProductPublishInfo) {
		this.bankFinancialProductPublishInfo = bankFinancialProductPublishInfo;
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
