package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductInvestOperateDetailVO implements Entity {

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
	private Timestamp submittime;
	private String submittimeCN;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private BigDecimal poundage;
	private String totalAmountCN;
	private String totalReturnCN;
	private String poundageCN;
	private List<BankFinancialProductInvestRecordsVO> dataList;
	
	private String receipt;
	private List<Resource> listReceipt;
	
	public BankFinancialProductInvestOperateDetailVO(){
		
	}
	
	public BankFinancialProductInvestOperateDetailVO(BankFinancialProductInvestOperate bfpio){
		this.uuid = bfpio.getUuid();
		this.bankFinancialProduct = bfpio.getBankFinancialProduct();
		this.type = bfpio.getType();
		if(BankFinancialProductInvestOperateType.INVEST.equals(bfpio.getType())){
			this.typeCN = "投资";
		}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){
			this.typeCN = "赎回";
		}

		this.value = bfpio.getValue();
		this.reason = bfpio.getReason();
		this.status = bfpio.getStatus();
		if(BankFinancialProductInvestOperateStatus.UNCHECKED.equals(bfpio.getStatus())){
			this.statusCN = "待审核";
		}else if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfpio.getStatus())){
			this.statusCN = "审核通过";
		}else if(BankFinancialProductInvestOperateStatus.UNPASSED.equals(bfpio.getStatus())){
			this.statusCN = "审核不通过";
		}else if(BankFinancialProductInvestOperateStatus.DELETED.equals(bfpio.getStatus())){
			this.statusCN = "已删除";
		}
		this.checker = bfpio.getChecker();
		this.checktime = bfpio.getChecktime();
		this.creator = bfpio.getCreator();
		this.submittime = bfpio.getSubmittime();
		if(bfpio.getSubmittime() != null && !"".equals(bfpio.getSubmittime())){
			this.submittimeCN = Utlity.timeSpanToString(bfpio.getSubmittime());
		}else{
			this.submittimeCN = "";
		}
		this.createtime = bfpio.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(bfpio.getCreatetime());
		
		this.receipt = bfpio.getReceipt() == null ? "" : bfpio.getReceipt();
		this.listReceipt = new ArrayList<Resource>();
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

	public List<BankFinancialProductInvestRecordsVO> getDataList() {
		return dataList;
	}

	public void setDataList(List<BankFinancialProductInvestRecordsVO> dataList) {
		this.dataList = dataList;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
		this.totalAmountCN = Utlity.numFormat4UnitDetailLess(totalAmount);
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
		this.totalReturnCN = Utlity.numFormat4UnitDetail(totalReturn);
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
		this.poundageCN = Utlity.numFormat4UnitDetail(poundage);
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public String getPoundageCN() {
		return poundageCN;
	}

	public void setPoundageCN(String poundageCN) {
		this.poundageCN = poundageCN;
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

	
	public String getReceipt() {
		return receipt;
	}
	

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	

	public List<Resource> getListReceipt() {
		return listReceipt;
	}
	

	public void setListReceipt(List<Resource> listReceipt) {
		this.listReceipt = listReceipt;
	}
	
}
