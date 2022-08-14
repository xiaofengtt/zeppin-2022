package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.FundOperate;
import cn.zeppin.product.ntb.core.entity.FundOperate.FundOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundOperate.FundOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String fund;
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
	private Timestamp submittime;
	private String submittimeCN;
	private FundDetailsVO oldData;
	private FundDetailsVO newData;
	
	public FundOperateDetailVO(){
		
	}
	
	public FundOperateDetailVO(FundOperate fo){
		this.uuid = fo.getUuid();
		this.fund = fo.getFund();
		this.type = fo.getType();
		if(FundOperateType.ADD.equals(fo.getType())){
			this.typeCN = "添加";
		}else if(FundOperateType.EDIT.equals(fo.getType())){
			this.typeCN = "修改";
		}else if(FundOperateType.DELETE.equals(fo.getType())){
			this.typeCN = "删除";
		}else if(FundOperateType.NETVALUE.equals(fo.getType())){
			this.typeCN = "录入净值";
		}

		this.value = fo.getValue();
		this.reason = fo.getReason();
		this.status = fo.getStatus();
		if(FundOperateStatus.UNCHECKED.equals(fo.getStatus())){
			this.statusCN = "待审核";
		}else if(FundOperateStatus.CHECKED.equals(fo.getStatus())){
			this.statusCN = "审核通过";
		}else if(FundOperateStatus.UNPASSED.equals(fo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(FundOperateStatus.DELETED.equals(fo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = fo.getChecker();
		this.checktime = fo.getChecktime();
		this.submittime = fo.getSubmittime();
		if(fo.getSubmittime() != null && !"".equals(fo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(fo.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.creator = fo.getCreator();
		this.createtime = fo.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(fo.getCreatetime());
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getFund() {
		return fund;
	}
	
	public void setFund(String fund) {
		this.fund = fund;
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

	public FundDetailsVO getOldData() {
		return oldData;
	}
	
	public void setOldData(FundDetailsVO oldData) {
		this.oldData = oldData;
	}

	public FundDetailsVO getNewData() {
		return newData;
	}
	
	public void setNewData(FundDetailsVO newData) {
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
