package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate.ProductPublishBalanceOperateStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class ProductPublishBalanceOperateVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String bankFinancialProductPublish;
	private String bankFinancialProductPublishName;
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
	
	public ProductPublishBalanceOperateVO(){
		
	}
	
	public ProductPublishBalanceOperateVO(ProductPublishBalanceOperate ppbo){
		this.uuid = ppbo.getUuid();
		this.bankFinancialProductPublish = ppbo.getBankFinancialProductPublish();
		this.value = ppbo.getValue();
		this.reason = ppbo.getReason();
		this.status = ppbo.getStatus();
		if(ProductPublishBalanceOperateStatus.UNCHECKED.equals(ppbo.getStatus())){
			this.statusCN = "待审核";
		}else if(ProductPublishBalanceOperateStatus.CHECKED.equals(ppbo.getStatus())){
			this.statusCN = "审核通过";
		}else if(ProductPublishBalanceOperateStatus.UNPASSED.equals(ppbo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(ProductPublishBalanceOperateStatus.DELETED.equals(ppbo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = ppbo.getChecker();
		this.checktime = ppbo.getChecktime();
		if(ppbo.getChecktime() != null && !"".equals(ppbo.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(ppbo.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.creator = ppbo.getCreator();
		this.createtime = ppbo.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(ppbo.getCreatetime());
		this.submittime = ppbo.getSubmittime();
		if(ppbo.getSubmittime() != null && !"".equals(ppbo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(ppbo.getSubmittime());
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
	
	public String getBankFinancialProductPublish() {
		return bankFinancialProductPublish;
	}

	public void setBankFinancialProductPublish(String bankFinancialProductPublish) {
		this.bankFinancialProductPublish = bankFinancialProductPublish;
	}

	public String getBankFinancialProductPublishName() {
		return bankFinancialProductPublishName;
	}
	
	public void setBankFinancialProductPublishName(String bankFinancialProductPublishName) {
		this.bankFinancialProductPublishName = bankFinancialProductPublishName;
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
	
	
}
