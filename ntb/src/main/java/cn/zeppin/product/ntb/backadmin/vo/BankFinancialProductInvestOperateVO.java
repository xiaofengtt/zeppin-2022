package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductInvestOperateVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String bankFinancialProductInvest;
	private String bankFinancialProductInvestName;
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
	private BankFinancialProductInvest oldData;
	private BankFinancialProductInvest newData;
	
	public BankFinancialProductInvestOperateVO(){
		
	}
	
	public BankFinancialProductInvestOperateVO(BankFinancialProductInvestOperate bfppo){
		this.uuid = bfppo.getUuid();
		this.bankFinancialProductInvest = bfppo.getBankFinancialProductInvest();
		this.type = bfppo.getType();
		if(BankFinancialProductInvestOperateType.ADD.equals(bfppo.getType())){
			this.typeCN = "添加";
		}else if(BankFinancialProductInvestOperateType.EDIT.equals(bfppo.getType())){
			this.typeCN = "修改";
		}else if(BankFinancialProductInvestOperateType.DELETE.equals(bfppo.getType())){
			this.typeCN = "删除";
		}else if(BankFinancialProductInvestOperateType.CHECK.equals(bfppo.getType())){
			this.typeCN = "审核";
		}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfppo.getType())){
			this.typeCN = "赎回";
		}
		this.value = bfppo.getValue();
		
		this.reason = bfppo.getReason();
		this.status = bfppo.getStatus();
		if(BankFinancialProductInvestOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
			this.statusCN = "待审核";
		}else if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfppo.getStatus())){
			this.statusCN = "审核通过";
		}else if(BankFinancialProductInvestOperateStatus.UNPASSED.equals(bfppo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(BankFinancialProductInvestOperateStatus.DELETED.equals(bfppo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = bfppo.getChecker();
		this.checktime = bfppo.getChecktime();
		if(bfppo.getChecktime() != null && !"".equals(bfppo.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(bfppo.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.creator = bfppo.getCreator();
		this.createtime = bfppo.getCreatetime();
		if(bfppo.getCreatetime() != null && !"".equals(bfppo.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(bfppo.getCreatetime());
		}else{
			this.createtimeCN = "";
		}
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getBankFinancialProductInvest() {
		return bankFinancialProductInvest;
	}

	public void setBankFinancialProductInvest(String bankFinancialProductInvest) {
		this.bankFinancialProductInvest = bankFinancialProductInvest;
	}

	public String getBankFinancialProductInvestName() {
		return bankFinancialProductInvestName;
	}
	
	public void setBankFinancialProductInvestName(String bankFinancialProductInvestName) {
		this.bankFinancialProductInvestName = bankFinancialProductInvestName;
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
	
	public BankFinancialProductInvest getOldData() {
		return oldData;
	}
	
	public void setOldData(BankFinancialProductInvest oldData) {
		this.oldData = oldData;
	}

	public BankFinancialProductInvest getNewData() {
		return newData;
	}
	
	public void setNewData(BankFinancialProductInvest newData) {
		this.newData = newData;
	}
}
