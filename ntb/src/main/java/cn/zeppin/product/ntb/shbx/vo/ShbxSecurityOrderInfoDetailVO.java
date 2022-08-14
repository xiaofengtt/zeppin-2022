/**
 * 
 */
package cn.zeppin.product.ntb.shbx.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityInsuredType;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityOrderStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.Utlity;

public class ShbxSecurityOrderInfoDetailVO implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 472242002961844242L;
	private String uuid;
	private String orderNumber;
	private String shbxInsured;

	private String starttime;

	private String duration;
	private String durationCN;

	private BigDecimal housingFund;
	private String housingFundCN;
	private BigDecimal cardinalNumber;
	private String cardinalNumberCN;
	private BigDecimal benefits;
	private String benefitsCN;
	private BigDecimal serviceFee;
	private String serviceFeeCN;

	private String shbxUserHistory;

	private Timestamp createtime;
	private String createtimeCN;
	private String creator;
	private String creatorName;
	private String status;
	private String statusCN;

	private String company;
	private String sourceCompany;

	private String receipt;
	private List<Object> receipts;// 参保凭证列表

	private String insuredType;
	private String insuredTypeCN;

	private String processCreator;
	private String processCreatorName;
	private Timestamp processCreatetime;
	private String processCreatetimeCN;

	private BigDecimal totalAmount;
	private String totalAmountCN;

	private String shbxInsuredName;
	private String shbxInsuredMobile;
	private String shbxInsuredIdcard;
	private String shbxInsuredHouseholdarea;
	private String shbxInsuredHouseholdareaCN;
	private String shbxInsuredHouseholdtype;
	private String shbxInsuredEducation;
	private String shbxInsuredEmail;
	private String shbxInsuredNationality;
	
	private Timestamp worktime;
	private String worktimeCN;

	public ShbxSecurityOrderInfoDetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShbxSecurityOrderInfoDetailVO(ShbxSecurityOrder sso) {
		this.uuid = sso.getUuid();
		this.benefits = sso.getBenefits();
		this.benefitsCN = Utlity.numFormat4UnitDetail(sso.getBenefits());
		this.cardinalNumber = sso.getCardinalNumber();
		this.cardinalNumberCN = Utlity.numFormat4UnitDetail(sso
				.getCardinalNumber());
		this.company = sso.getCompany();
		this.createtime = sso.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(sso.getCreatetime());
		this.creator = sso.getCreator();
		this.creatorName = "";
		this.duration = sso.getDuration();
		this.durationCN = Utlity.getDurationStr(sso.getStarttime(),
				sso.getDuration());
		this.housingFund = sso.getHousingFund();
		if (sso.getHousingFund() != null) {
			this.housingFundCN = Utlity.numFormat4UnitDetail(sso
					.getHousingFund());
		} else {
			this.housingFundCN = "0.00";
		}
		this.insuredType = sso.getInsuredType();
		if (ShbxSecurityInsuredType.NEW.equals(sso.getInsuredType())) {
			this.insuredTypeCN = "新参保";
		} else if (ShbxSecurityInsuredType.CHANGEIN
				.equals(sso.getInsuredType())) {
			this.insuredTypeCN = "普通转入";
		}
		this.orderNumber = sso.getOrderNumber();
		this.processCreatetime = sso.getProcessCreatetime();
		if (sso.getProcessCreatetime() != null) {
			this.processCreatetimeCN = Utlity.timeSpanToString(sso
					.getProcessCreatetime());
		} else {
			this.processCreatetimeCN = "";
		}
		this.processCreator = sso.getProcessCreator();
		this.processCreatorName = "";
		this.receipt = sso.getReceipt();
		this.serviceFee = sso.getServiceFee();
		this.serviceFeeCN = Utlity.numFormat4UnitDetail(sso.getServiceFee());
		this.shbxInsured = sso.getShbxInsured();
		this.shbxUserHistory = sso.getShbxUserHistory();
		this.starttime = sso.getStarttime();
		this.status = sso.getStatus();
		if (ShbxSecurityOrderStatus.NORMAL.equals(sso.getStatus())) {
			this.statusCN = "已处理";
		} else if (ShbxSecurityOrderStatus.UNPROCESS.equals(sso.getStatus())) {
			this.statusCN = "待处理";
		} else if(ShbxSecurityOrderStatus.UNPAY.equals(sso.getStatus())){
			this.statusCN = "待支付";
		} else if(ShbxSecurityOrderStatus.PAYED.equals(sso.getStatus())){
			this.statusCN = "待处理";
		} else if(ShbxSecurityOrderStatus.FAIL.equals(sso.getStatus())){
			this.statusCN = "支付失败";
		} else {
			this.statusCN = "--";
		}
		this.sourceCompany = sso.getSourceCompany() == null ? "" : sso
				.getSourceCompany();
		this.receipts = new ArrayList<Object>();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getShbxInsured() {
		return shbxInsured;
	}

	public void setShbxInsured(String shbxInsured) {
		this.shbxInsured = shbxInsured;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDurationCN() {
		return durationCN;
	}

	public void setDurationCN(String durationCN) {
		this.durationCN = durationCN;
	}

	public BigDecimal getHousingFund() {
		return housingFund;
	}

	public void setHousingFund(BigDecimal housingFund) {
		this.housingFund = housingFund;
	}

	public BigDecimal getCardinalNumber() {
		return cardinalNumber;
	}

	public void setCardinalNumber(BigDecimal cardinalNumber) {
		this.cardinalNumber = cardinalNumber;
	}

	public BigDecimal getBenefits() {
		return benefits;
	}

	public void setBenefits(BigDecimal benefits) {
		this.benefits = benefits;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getShbxUserHistory() {
		return shbxUserHistory;
	}

	public void setShbxUserHistory(String shbxUserHistory) {
		this.shbxUserHistory = shbxUserHistory;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSourceCompany() {
		return sourceCompany;
	}

	public void setSourceCompany(String sourceCompany) {
		this.sourceCompany = sourceCompany;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	public String getProcessCreator() {
		return processCreator;
	}

	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}

	public Timestamp getProcessCreatetime() {
		return processCreatetime;
	}

	public void setProcessCreatetime(Timestamp processCreatetime) {
		this.processCreatetime = processCreatetime;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getShbxInsuredName() {
		return shbxInsuredName;
	}

	public void setShbxInsuredName(String shbxInsuredName) {
		this.shbxInsuredName = shbxInsuredName;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getProcessCreatorName() {
		return processCreatorName;
	}

	public void setProcessCreatorName(String processCreatorName) {
		this.processCreatorName = processCreatorName;
	}

	public String getProcessCreatetimeCN() {
		return processCreatetimeCN;
	}

	public void setProcessCreatetimeCN(String processCreatetimeCN) {
		this.processCreatetimeCN = processCreatetimeCN;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getHousingFundCN() {
		return housingFundCN;
	}

	public void setHousingFundCN(String housingFundCN) {
		this.housingFundCN = housingFundCN;
	}

	public String getCardinalNumberCN() {
		return cardinalNumberCN;
	}

	public void setCardinalNumberCN(String cardinalNumberCN) {
		this.cardinalNumberCN = cardinalNumberCN;
	}

	public String getBenefitsCN() {
		return benefitsCN;
	}

	public void setBenefitsCN(String benefitsCN) {
		this.benefitsCN = benefitsCN;
	}

	public String getServiceFeeCN() {
		return serviceFeeCN;
	}

	public void setServiceFeeCN(String serviceFeeCN) {
		this.serviceFeeCN = serviceFeeCN;
	}

	public String getInsuredTypeCN() {
		return insuredTypeCN;
	}

	public void setInsuredTypeCN(String insuredTypeCN) {
		this.insuredTypeCN = insuredTypeCN;
	}

	public List<Object> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Object> receipts) {
		this.receipts = receipts;
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

	public String getShbxInsuredMobile() {
		return shbxInsuredMobile;
	}

	public void setShbxInsuredMobile(String shbxInsuredMobile) {
		this.shbxInsuredMobile = shbxInsuredMobile;
	}

	public String getShbxInsuredIdcard() {
		return shbxInsuredIdcard;
	}

	public void setShbxInsuredIdcard(String shbxInsuredIdcard) {
		this.shbxInsuredIdcard = shbxInsuredIdcard;
	}

	public String getShbxInsuredHouseholdarea() {
		return shbxInsuredHouseholdarea;
	}

	public void setShbxInsuredHouseholdarea(String shbxInsuredHouseholdarea) {
		this.shbxInsuredHouseholdarea = shbxInsuredHouseholdarea;
	}

	public String getShbxInsuredHouseholdareaCN() {
		return shbxInsuredHouseholdareaCN;
	}

	public void setShbxInsuredHouseholdareaCN(String shbxInsuredHouseholdareaCN) {
		this.shbxInsuredHouseholdareaCN = shbxInsuredHouseholdareaCN;
	}

	public String getShbxInsuredHouseholdtype() {
		return shbxInsuredHouseholdtype;
	}

	public void setShbxInsuredHouseholdtype(String shbxInsuredHouseholdtype) {
		this.shbxInsuredHouseholdtype = shbxInsuredHouseholdtype;
	}

	public String getShbxInsuredEducation() {
		return shbxInsuredEducation;
	}

	public void setShbxInsuredEducation(String shbxInsuredEducation) {
		this.shbxInsuredEducation = shbxInsuredEducation;
	}

	public String getShbxInsuredEmail() {
		return shbxInsuredEmail;
	}

	public void setShbxInsuredEmail(String shbxInsuredEmail) {
		this.shbxInsuredEmail = shbxInsuredEmail;
	}

	public String getShbxInsuredNationality() {
		return shbxInsuredNationality;
	}

	public void setShbxInsuredNationality(String shbxInsuredNationality) {
		this.shbxInsuredNationality = shbxInsuredNationality;
	}


	public Timestamp getWorktime() {
		return worktime;
	}
	

	public void setWorktime(Timestamp worktime) {
		this.worktime = worktime;
		this.worktimeCN = DataTimeConvert.timespanToString(worktime, "yyyy年MM月dd日");
	}
	

	public String getWorktimeCN() {
		return worktimeCN;
	}
	

	public void setWorktimeCN(String worktimeCN) {
		this.worktimeCN = worktimeCN;
	}
}
