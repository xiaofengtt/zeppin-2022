package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundPublishOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String fundPublish;
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
	private FundPublishDetailsVO oldData;
	private FundPublishDetailsVO newData;
	
	public FundPublishOperateDetailVO(){
		
	}
	
	public FundPublishOperateDetailVO(FundPublishOperate fpo){
		this.uuid = fpo.getUuid();
		this.fundPublish = fpo.getFundPublish();
		this.type = fpo.getType();
		if(FundPublishOperateType.ADD.equals(fpo.getType())){
			this.typeCN = "添加";
		}else if(FundPublishOperateType.EDIT.equals(fpo.getType())){
			this.typeCN = "修改";
		}else if(FundPublishOperateType.DELETE.equals(fpo.getType())){
			this.typeCN = "删除";
		}else if(FundPublishOperateType.NETVALUE.equals(fpo.getType())){
			this.typeCN = "净值";
		}

		this.value = fpo.getValue();
		this.reason = fpo.getReason();
		this.status = fpo.getStatus();
		if(FundPublishOperateStatus.UNCHECKED.equals(fpo.getStatus())){
			this.statusCN = "待审核";
		}else if(FundPublishOperateStatus.CHECKED.equals(fpo.getStatus())){
			this.statusCN = "审核通过";
		}else if(FundPublishOperateStatus.UNPASSED.equals(fpo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(FundPublishOperateStatus.DELETED.equals(fpo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = fpo.getChecker();
		this.checktime = fpo.getChecktime();
		this.submittime = fpo.getSubmittime();
		if(fpo.getSubmittime() != null && !"".equals(fpo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(fpo.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.creator = fpo.getCreator();
		this.createtime = fpo.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(fpo.getCreatetime());
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getFundPublish() {
		return fundPublish;
	}

	public void setFundPublish(String fundPublish) {
		this.fundPublish = fundPublish;
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

	public FundPublishDetailsVO getOldData() {
		return oldData;
	}
	
	public void setOldData(FundPublishDetailsVO oldData) {
		this.oldData = oldData;
	}

	public FundPublishDetailsVO getNewData() {
		return newData;
	}
	
	public void setNewData(FundPublishDetailsVO newData) {
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
