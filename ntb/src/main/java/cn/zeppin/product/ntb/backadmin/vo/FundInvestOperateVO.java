package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.FundInvestOperate;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate.FundInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate.FundInvestOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class FundInvestOperateVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String fund;
	private String fundName;
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
	private Timestamp submittime;//提交时间
	private String submittimeCN;
	private String remark;
	private BigDecimal totalAmount;
	private String totalAmountCN;
	
	public FundInvestOperateVO(){
		
	}
	
	public FundInvestOperateVO(FundInvestOperate bfpio){
		this.uuid = bfpio.getUuid();
		this.fund = bfpio.getFund();
		this.type = bfpio.getType();
		if(FundInvestOperateType.INVEST.equals(bfpio.getType())){
			this.typeCN = "投资";
		}else if(FundInvestOperateType.REDEEM.equals(bfpio.getType())){
			this.typeCN = "赎回";
		}
		this.value = bfpio.getValue();
		Map<String, Object> valueMap = JSONUtils.json2map(value);
		this.remark = valueMap.get("remark") == null ? "" : valueMap.get("remark").toString();
		this.reason = bfpio.getReason();
		this.status = bfpio.getStatus();
		if(FundInvestOperateStatus.UNCHECKED.equals(bfpio.getStatus())){
			this.statusCN = "待审核";
		}else if(FundInvestOperateStatus.CHECKED.equals(bfpio.getStatus())){
			this.statusCN = "审核通过";
		}else if(FundInvestOperateStatus.UNPASSED.equals(bfpio.getStatus())){
			this.statusCN = "审核不通过";
		}else if(FundInvestOperateStatus.DELETED.equals(bfpio.getStatus())){
			this.statusCN = "已删除";
		}else if(FundInvestOperateStatus.DRAFT.equals(bfpio.getStatus())){
			this.statusCN = "草稿";
		}
		this.checker = bfpio.getChecker();
		this.checktime = bfpio.getChecktime();
		if(bfpio.getChecktime() != null && !"".equals(bfpio.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(bfpio.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.creator = bfpio.getCreator();
		this.createtime = bfpio.getCreatetime();
		if(bfpio.getCreatetime() != null && !"".equals(bfpio.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(bfpio.getCreatetime());
		}else{
			this.createtimeCN = "";
		}
		this.submittime = bfpio.getSubmittime();
		if(bfpio.getSubmittime() != null && !"".equals(bfpio.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(bfpio.getSubmittime());
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

	
	public String getRemark() {
		return remark;
	}
	

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
		
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
		this.totalAmountCN = Utlity.numFormat4UnitDetailLess(totalAmount);
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}


	public String getFund() {
		return fund;
	}
	

	public void setFund(String fund) {
		this.fund = fund;
	}
	

	public String getFundName() {
		return fundName;
	}
	

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

}
