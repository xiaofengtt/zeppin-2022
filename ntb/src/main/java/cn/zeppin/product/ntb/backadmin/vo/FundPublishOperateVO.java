package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundPublishOperateVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 696665572649658545L;
	
	private String uuid;
	private String fundPublish;
	private String fundPublishName;
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
	
	private String custodian;//银行
	private String custodianName;//银行名称
	
	private String scode;//产品编号
	
	private Timestamp submittime;//提交时间
	private String submittimeCN;
	
	public FundPublishOperateVO(){
		
	}
	
	public FundPublishOperateVO(FundPublishOperate bfppo){
		this.uuid = bfppo.getUuid();
		this.fundPublish = bfppo.getFundPublish();
		this.type = bfppo.getType();
		if(FundPublishOperateType.ADD.equals(bfppo.getType())){
			this.typeCN = "添加";
		}else if(FundPublishOperateType.EDIT.equals(bfppo.getType())){
			this.typeCN = "修改";
		}else if(FundPublishOperateType.DELETE.equals(bfppo.getType())){
			this.typeCN = "删除";
		}else if(FundPublishOperateType.NETVALUE.equals(bfppo.getType())){
			this.typeCN = "净值";
		}
		this.value = bfppo.getValue();
		this.reason = bfppo.getReason();
		this.status = bfppo.getStatus();
		if(FundPublishOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
			this.statusCN = "待审核";
		}else if(FundPublishOperateStatus.CHECKED.equals(bfppo.getStatus())){
			this.statusCN = "审核通过";
		}else if(FundPublishOperateStatus.UNPASSED.equals(bfppo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(FundPublishOperateStatus.DELETED.equals(bfppo.getStatus())){
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
		this.submittime = bfppo.getSubmittime();
		if(bfppo.getSubmittime() != null && !"".equals(bfppo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(bfppo.getSubmittime());
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
	
	public String getFundPublish() {
		return fundPublish;
	}

	public void setFundPublish(String fundPublish) {
		this.fundPublish = fundPublish;
	}

	public String getFundPublishName() {
		return fundPublishName;
	}

	public void setFundPublishName(String fundPublishName) {
		this.fundPublishName = fundPublishName;
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
