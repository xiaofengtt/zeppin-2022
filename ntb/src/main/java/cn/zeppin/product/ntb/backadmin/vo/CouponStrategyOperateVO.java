package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateStatus;
import cn.zeppin.product.ntb.core.entity.BkPaymentOperate.BkPaymentOperateType;
import cn.zeppin.product.ntb.core.entity.CouponStrategyOperate;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class CouponStrategyOperateVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String couponStrategy;
	private String strategyIdentification;
	private String couponStrategyName;
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
	
	private Timestamp submittime;//提交时间
	private String submittimeCN;
	
	public CouponStrategyOperateVO(){
		
	}
	
	public CouponStrategyOperateVO(CouponStrategyOperate cso){
		this.uuid = cso.getUuid();
		this.couponStrategy = cso.getCouponStrategy();
		this.type = cso.getType();
		if(BkPaymentOperateType.ADD.equals(cso.getType())){
			this.typeCN = "添加";
		}else if(BkPaymentOperateType.EDIT.equals(cso.getType())){
			this.typeCN = "修改";
		}else if(BkPaymentOperateType.DELETE.equals(cso.getType())){
			this.typeCN = "删除";
		}else if(BkPaymentOperateType.OPEN.equals(cso.getType())){
			this.typeCN = "开启";
		}else if(BkPaymentOperateType.CLOSED.equals(cso.getType())){
			this.typeCN = "关闭";
		}
		this.reason = cso.getReason();
		this.status = cso.getStatus();
		if(BkPaymentOperateStatus.UNCHECKED.equals(cso.getStatus())){
			this.statusCN = "待审核";
		}else if(BkPaymentOperateStatus.CHECKED.equals(cso.getStatus())){
			this.statusCN = "审核通过";
		}else if(BkPaymentOperateStatus.UNPASSED.equals(cso.getStatus())){
			this.statusCN = "审核不通过";
		}else if(BkPaymentOperateStatus.DELETED.equals(cso.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = cso.getChecker();
		this.checktime = cso.getChecktime();
		if(cso.getChecktime() != null && !"".equals(cso.getChecktime())){
			this.checktimeCN = Utlity.timeSpanToString(cso.getChecktime());
		}else{
			this.checktimeCN = "";
		}
		this.creator = cso.getCreator();
		this.createtime = cso.getCreatetime();
		if(cso.getCreatetime() != null && !"".equals(cso.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(cso.getCreatetime());
		}else{
			this.createtimeCN = "";
		}
		this.submittime = cso.getSubmittime();
		if(cso.getSubmittime() != null && !"".equals(cso.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(cso.getSubmittime());
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
	

	public String getCouponStrategy() {
		return couponStrategy;
	}
	

	public void setCouponStrategy(String couponStrategy) {
		this.couponStrategy = couponStrategy;
	}

	public String getStrategyIdentification() {
		return strategyIdentification;
	}

	public void setStrategyIdentification(String strategyIdentification) {
		this.strategyIdentification = strategyIdentification;
	}

	public String getCouponStrategyName() {
		return couponStrategyName;
	}

	public void setCouponStrategyName(String couponStrategyName) {
		this.couponStrategyName = couponStrategyName;
	}
}
