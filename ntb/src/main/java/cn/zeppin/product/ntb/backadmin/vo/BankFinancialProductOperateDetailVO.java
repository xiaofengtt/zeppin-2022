package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductOperateDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261557034566491111L;
	
	private String uuid;
	private String bankFinancialProduct;
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
	private BankFinancialProductDetailsVO oldData;
	private BankFinancialProductDetailsVO newData;
	
	public BankFinancialProductOperateDetailVO(){
		
	}
	
	public BankFinancialProductOperateDetailVO(BankFinancialProductOperate bfpo){
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
			this.typeCN = "录入净值";
		}

		this.value = bfpo.getValue();
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
		this.creator = bfpo.getCreator();
		this.createtime = bfpo.getCreatetime();
		if(bfpo.getCreatetime() != null && !"".equals(bfpo.getCreatetime())){
			this.createtimeCN = Utlity.timeSpanToString(bfpo.getCreatetime());
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
	
	public String getBankFinancialProduct() {
		return bankFinancialProduct;
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

	public BankFinancialProductDetailsVO getOldData() {
		return oldData;
	}
	
	public void setOldData(BankFinancialProductDetailsVO oldData) {
		this.oldData = oldData;
	}

	public BankFinancialProductDetailsVO getNewData() {
		return newData;
	}
	
	public void setNewData(BankFinancialProductDetailsVO newData) {
		this.newData = newData;
	}
}
