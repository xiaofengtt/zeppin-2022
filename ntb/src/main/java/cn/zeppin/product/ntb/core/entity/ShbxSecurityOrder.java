/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】社保熊用户参保订单信息
 */

@Entity
@Table(name = "shbx_security_order")
public class ShbxSecurityOrder extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3880369620924089077L;
	private String uuid;
	private String orderNumber;
	private String shbxInsured;
	private String starttime;
	private String duration;
	
	private BigDecimal housingFund;
	private BigDecimal cardinalNumber;
	private BigDecimal benefits;
	private BigDecimal serviceFee;
	
	private String shbxUserHistory;
	
	private String creator;
	private Timestamp createtime;
	private String status;
	
	private String company;
	private String sourceCompany;
	private String receipt;
	
	private String insuredType;
	
	private String processCreator;
	private Timestamp processCreatetime;
	
	
	public class ShbxSecurityOrderStatus{
		public final static String NORMAL = "normal";//已处理
		public final static String UNPROCESS = "unprocess";//未处理
		public final static String PAYED = "payed";//已支付
		public final static String DELETED = "deleted";
		public final static String UNPAY = "unpay";//待支付
		public final static String FAIL = "fail";//支付失败
	}
	
	public class ShbxSecurityInsuredType{
		public final static String NEW = "new";
		public final static String CHANGEIN = "changein";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "order_number", nullable = false, length = 36)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "shbx_insured", nullable = false, length = 36)
	public String getShbxInsured() {
		return shbxInsured;
	}

	public void setShbxInsured(String shbxInsured) {
		this.shbxInsured = shbxInsured;
	}

	@Column(name = "starttime", nullable = false)
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	@Column(name = "duration", nullable = false)
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "cardinal_number", nullable = false)
	public BigDecimal getCardinalNumber() {
		return cardinalNumber;
	}

	public void setCardinalNumber(BigDecimal cardinalNumber) {
		this.cardinalNumber = cardinalNumber;
	}

	@Column(name = "benefits", nullable = false)
	public BigDecimal getBenefits() {
		return benefits;
	}

	public void setBenefits(BigDecimal benefits) {
		this.benefits = benefits;
	}

	@Column(name = "service_fee", nullable = false)
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	@Column(name = "shbx_user_history", nullable = false, length = 36)
	public String getShbxUserHistory() {
		return shbxUserHistory;
	}

	public void setShbxUserHistory(String shbxUserHistory) {
		this.shbxUserHistory = shbxUserHistory;
	}

	@Column(name = "company")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "receipt")
	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	@Column(name = "housing_fund")
	public BigDecimal getHousingFund() {
		return housingFund;
	}

	public void setHousingFund(BigDecimal housingFund) {
		this.housingFund = housingFund;
	}

	@Column(name = "insured_type", nullable = false, length = 20)
	public String getInsuredType() {
		return insuredType;
	}

	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}

	@Column(name = "source_company")
	public String getSourceCompany() {
		return sourceCompany;
	}

	public void setSourceCompany(String sourceCompany) {
		this.sourceCompany = sourceCompany;
	}


	@Column(name = "process_creator")
	public String getProcessCreator() {
		return processCreator;
	}
	

	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}
	
	@Column(name = "process_createtime")
	public Timestamp getProcessCreatetime() {
		return processCreatetime;
	}
	

	public void setProcessCreatetime(Timestamp processCreatetime) {
		this.processCreatetime = processCreatetime;
	}
}
