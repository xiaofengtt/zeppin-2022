/**
 * 
 */
package cn.zeppin.product.ntb.shbx.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityInsuredType;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityOrderStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class ShbxSecurityOrderInfoVO implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4221506978172807286L;
	private String uuid;
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
	private String status;
	private String statusCN;

	private BigDecimal totalAmount;
	private String totalAmountCN;

	private String shbxInsuredName;
	
	private String orderNumber;
	
	private String insuredType;
	private String insuredTypeCN;
	
	private String time;
	
	private String shbxOrgCN;

	public ShbxSecurityOrderInfoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShbxSecurityOrderInfoVO(ShbxSecurityOrder sso) {
		this.uuid = sso.getUuid();
		this.orderNumber = sso.getOrderNumber();
		this.benefits = sso.getBenefits();
		this.benefitsCN = Utlity.numFormat4UnitDetail(sso.getBenefits());
		this.cardinalNumber = sso.getCardinalNumber();
		this.cardinalNumberCN = Utlity.numFormat4UnitDetail(sso
				.getCardinalNumber());
		this.createtime = sso.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(sso.getCreatetime());
		this.time = Utlity.timeSpanToChinaStringesLess(sso.getCreatetime());
		this.duration = sso.getDuration();
		this.durationCN = Utlity.getDurationStr(sso.getStarttime(), sso.getDuration());
		this.housingFund = sso.getHousingFund();
		if (sso.getHousingFund() != null) {
			this.housingFundCN = Utlity.numFormat4UnitDetail(sso
					.getHousingFund());
		} else {
			this.housingFundCN = "0.00";
		}
		
		this.serviceFee = sso.getServiceFee();
		this.serviceFeeCN = Utlity.numFormat4UnitDetail(sso.getServiceFee());
		this.shbxInsured = sso.getShbxInsured();
		this.shbxUserHistory = sso.getShbxUserHistory();
		this.starttime = sso.getStarttime();
		this.status = sso.getStatus();
		if(ShbxSecurityOrderStatus.NORMAL.equals(sso.getStatus())){
			this.statusCN = "在缴";
		} else if(ShbxSecurityOrderStatus.UNPROCESS.equals(sso.getStatus())){
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
		this.insuredType = sso.getInsuredType();
		if(ShbxSecurityInsuredType.NEW.equals(sso.getInsuredType())){
			this.insuredTypeCN = "新参保";
		} else if (ShbxSecurityInsuredType.CHANGEIN.equals(sso.getInsuredType())) {
			this.insuredTypeCN = "普通转入";
		}
		this.shbxOrgCN = "北京市";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	public String getInsuredTypeCN() {
		return insuredTypeCN;
	}

	public void setInsuredTypeCN(String insuredTypeCN) {
		this.insuredTypeCN = insuredTypeCN;
	}


	public String getTime() {
		return time;
	}
	

	public void setTime(String time) {
		this.time = time;
	}

	public String getShbxOrgCN() {
		return shbxOrgCN;
	}

	public void setShbxOrgCN(String shbxOrgCN) {
		this.shbxOrgCN = shbxOrgCN;
	}
}
