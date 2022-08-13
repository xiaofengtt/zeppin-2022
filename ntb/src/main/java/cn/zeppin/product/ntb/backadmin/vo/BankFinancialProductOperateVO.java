package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductOperateVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String bankFinancialProduct;
	private String bankFinancialProductName;
	private String type;
	private String typeCN;
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
	
	private String custodian;//银行
	private String custodianName;//银行名称
	
	private String scode;//产品编号
	
	private Timestamp submittime;//提交时间
	private String submittimeCN;
	
	public BankFinancialProductOperateVO(){
		
	}
	
	public BankFinancialProductOperateVO(BankFinancialProductOperate bfpo){
		this.uuid = bfpo.getUuid();
		this.bankFinancialProduct = bfpo.getBankFinancialProduct();
		this.type = bfpo.getType();
		if(BankFinancialProductOperateType.ADD.equals(bfpo.getType())){
			this.typeCN = "添加";
		}else if(BankFinancialProductOperateType.EDIT.equals(bfpo.getType())){
			this.typeCN = "修改";
		}else if(BankFinancialProductOperateType.DELETE.equals(bfpo.getType())){
			this.typeCN = "删除";
		}else if(BankFinancialProductOperateType.NETVALUE.equals(bfpo.getType())){
			this.typeCN = "净值";
		}
		this.reason = bfpo.getReason();
		this.status = bfpo.getStatus();
		if(BankFinancialProductOperateStatus.UNCHECKED.equals(bfpo.getStatus())){
			this.statusCN = "待审核";
		}else if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus())){
			this.statusCN = "审核通过";
		}else if(BankFinancialProductOperateStatus.UNPASSED.equals(bfpo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(BankFinancialProductOperateStatus.DELETED.equals(bfpo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = bfpo.getChecker();
		this.checktime = bfpo.getChecktime();
		if(bfpo.getChecktime() != null && !"".equals(bfpo.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(bfpo.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.creator = bfpo.getCreator();
		this.createtime = bfpo.getCreatetime();
		if(bfpo.getCreatetime() != null && !"".equals(bfpo.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(bfpo.getCreatetime());
		}else{
			this.createtimeCN = "";
		}
		this.submittime = bfpo.getSubmittime();
		if(bfpo.getSubmittime() != null && !"".equals(bfpo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(bfpo.getSubmittime());
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
	
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}
	
	public void setBankFinancialProductName(String bankFinancialProductName) {
		this.bankFinancialProductName = bankFinancialProductName;
	}
	
	public String getBankFinancialProductName() {
		return bankFinancialProductName;
	}
	
	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
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

	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public String getCustodianName() {
		return custodianName;
	}

	public void setCustodianName(String custodianName) {
		this.custodianName = custodianName;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
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
