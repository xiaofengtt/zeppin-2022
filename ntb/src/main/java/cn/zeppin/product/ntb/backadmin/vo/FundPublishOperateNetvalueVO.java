package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class FundPublishOperateNetvalueVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String fundPublish;
	private String fundPublishName;
	private String scode;
	private String custodian;
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
	private List<FundPublishOperateDailyVO> dataList;
	
	public FundPublishOperateNetvalueVO(){
		
	}
	
	public FundPublishOperateNetvalueVO(FundPublishOperate bfpo){
		this.uuid = bfpo.getUuid();
		this.fundPublish = bfpo.getFundPublish();
		this.type = bfpo.getType();
		if(FundPublishOperateType.ADD.equals(bfpo.getType())){
			this.typeCN = "添加";
		}else if(FundPublishOperateType.EDIT.equals(bfpo.getType())){
			this.typeCN = "修改";
		}else if(FundPublishOperateType.DELETE.equals(bfpo.getType())){
			this.typeCN = "删除";
		}else if(FundPublishOperateType.NETVALUE.equals(bfpo.getType())){
			this.typeCN = "录入净值";
		}

		this.value = bfpo.getValue();
		this.dataList = JSONUtils.json2list(bfpo.getValue(), FundPublishOperateDailyVO.class);
		this.reason = bfpo.getReason();
		this.status = bfpo.getStatus();
		if(FundPublishOperateStatus.UNCHECKED.equals(bfpo.getStatus())){
			this.statusCN = "待审核";
		}else if(FundPublishOperateStatus.CHECKED.equals(bfpo.getStatus())){
			this.statusCN = "审核通过";
		}else if(FundPublishOperateStatus.UNPASSED.equals(bfpo.getStatus())){
			this.statusCN = "审核不通过";
		}else if(FundPublishOperateStatus.DELETED.equals(bfpo.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = bfpo.getChecker();
		this.checktime = bfpo.getChecktime();
		this.submittime = bfpo.getSubmittime();
		if(bfpo.getSubmittime() != null && !"".equals(bfpo.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(bfpo.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.creator = bfpo.getCreator();
		this.createtime = bfpo.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(bfpo.getCreatetime());
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

	public String getScode() {
		return scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
	}
	
	public String getCustodian() {
		return custodian;
	}
	
	public void setCustodian(String custodian) {
		this.custodian = custodian;
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

	public List<FundPublishOperateDailyVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<FundPublishOperateDailyVO> dataList) {
		this.dataList = dataList;
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
