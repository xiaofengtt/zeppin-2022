package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductPublishOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String bankFinancialProductPublish;
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
	private Timestamp createtime;
	private String createtimeCN;
	private BankFinancialProductPublishDetailsVO oldData;
	private BankFinancialProductPublishDetailsVO newData;
	
	public BankFinancialProductPublishOperateDetailVO(){
		
	}
	
	public BankFinancialProductPublishOperateDetailVO(BankFinancialProductPublishOperate bfppo){
		this.uuid = bfppo.getUuid();
		this.bankFinancialProductPublish = bfppo.getBankFinancialProductPublish();
		this.type = bfppo.getType();
		if(BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
			this.typeCN = "添加";
		}else if(BankFinancialProductPublishOperateType.EDIT.equals(bfppo.getType())){
			this.typeCN = "修改";
		}else if(BankFinancialProductPublishOperateType.DELETE.equals(bfppo.getType())){
			this.typeCN = "删除";
		}else if(BankFinancialProductPublishOperateType.EXCEPTION.equals(bfppo.getType())){
			this.typeCN = "异常下线";
		}

		this.value = bfppo.getValue();
		this.reason = bfppo.getReason();
		this.status = bfppo.getStatus();
		if(BankFinancialProductPublishOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
			this.statusCN = "待审核";
		}else if(BankFinancialProductPublishOperateStatus.CHECKED.equals(bfppo.getStatus())){
			this.statusCN = "审核通过";
		}else if(BankFinancialProductPublishOperateStatus.UNPASSED.equals(bfppo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(BankFinancialProductPublishOperateStatus.DELETED.equals(bfppo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = bfppo.getChecker();
		this.checktime = bfppo.getChecktime();
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
	
	public String getBankFinancialProductPublish() {
		return bankFinancialProductPublish;
	}
	
	public void setBankFinancialProductPublish(String bankFinancialProductPublish) {
		this.bankFinancialProductPublish = bankFinancialProductPublish;
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

	public BankFinancialProductPublishDetailsVO getOldData() {
		return oldData;
	}
	
	public void setOldData(BankFinancialProductPublishDetailsVO oldData) {
		this.oldData = oldData;
	}

	public BankFinancialProductPublishDetailsVO getNewData() {
		return newData;
	}
	
	public void setNewData(BankFinancialProductPublishDetailsVO newData) {
		this.newData = newData;
	}
}
