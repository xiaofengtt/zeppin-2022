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
 * 
 * @description 【数据对象】银行理财产品信息
 */

@Entity
@Table(name = "bank_financial_product")
public class BankFinancialProduct extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String name;
	private String series;
	private String url;
	private String scode;
	private String shortname;
	private String type;
	private String stage;
	private String status;
	private String target;
	private String custodian;
	private String currencyType;
	private BigDecimal targetAnnualizedReturnRate;
	private BigDecimal minAnnualizedReturnRate;
	private BigDecimal minInvestAmount;
	private BigDecimal minInvestAmountAdd;
	private BigDecimal maxInvestAmount;
	private BigDecimal subscribeFee;
	private BigDecimal purchaseFee;
	private BigDecimal redemingFee;
	private BigDecimal managementFee;
	private BigDecimal custodyFee;
	private BigDecimal networkFee;
	private BigDecimal totalAmount;
	private Timestamp collectStarttime;
	private Timestamp collectEndtime;
	private Integer term;
	private Timestamp recordDate;
	private Timestamp valueDate;
	private Timestamp maturityDate;
	private Boolean flagPurchase;
	private Boolean flagRedemption;
	private Boolean flagFlexible;
	private String investScope;
	private String style;
	private String riskLevel;
	private String creditLevel;
	private String revenueFeature;
	private Boolean flagCloseend;
	private String guaranteeStatus;
	private String area;
	private String remark;
	private BigDecimal netWorth;
	private String creator;
	private Timestamp createtime;
	private String document;
	private String paymentType;
	
	public class BankFinancialProductStatus{
		public final static String CHECKED = "checked";
		public final static String DELETED = "deleted";
	}
	
	public class BankFinancialProductStage{
		public final static String UNSTART = "unstart";
		public final static String COLLECT = "collect";
		public final static String INCOME = "income";
		public final static String FINISHED = "finished";
	}
	
	public class BankFinancialProductTypes{
		public final static String INCOME = "income";//固定收益
		public final static String FLOATINGINCOME = "floatingIncome";//保本浮动收益
		public final static String UNFLOATINGINCOME = "unfloatingIncome";//非保本浮动
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "series", nullable = false, length = 20)
	public String getSeries() {
		return series;
	}
	
	public void setSeries(String series) {
		this.series = series;
	}
	
	@Column(name = "url", length = 100)
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "scode", nullable = false, length = 100)
	public String getScode() {
		return scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
	}
	
	@Column(name = "shortname", nullable = false, length = 50)
	public String getShortname() {
		return shortname;
	}
	
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "stage", nullable = false, length = 20)
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "target", length = 10)
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "custodian", length = 50)
	public String getCustodian() {
		return custodian;
	}
	
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}
	
	@Column(name = "currency_type", length = 20)
	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	
	@Column(name = "target_annualized_return_rate", nullable = false, length = 10)
	public BigDecimal getTargetAnnualizedReturnRate() {
		return targetAnnualizedReturnRate;
	}
	
	public void setTargetAnnualizedReturnRate(BigDecimal targetAnnualizedReturnRate) {
		this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
	}
	
	@Column(name = "min_annualized_return_rate", length = 10)
	public BigDecimal getMinAnnualizedReturnRate() {
		return minAnnualizedReturnRate;
	}
	
	public void setMinAnnualizedReturnRate(BigDecimal minAnnualizedReturnRate) {
		this.minAnnualizedReturnRate = minAnnualizedReturnRate;
	}
	
	@Column(name = "min_invest_amount", nullable = false, length = 10)
	public BigDecimal getMinInvestAmount() {
		return minInvestAmount;
	}
	
	public void setMinInvestAmount(BigDecimal minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}
	
	@Column(name = "min_invest_amount_add", nullable = false, length = 10)
	public BigDecimal getMinInvestAmountAdd() {
		return minInvestAmountAdd;
	}
	
	public void setMinInvestAmountAdd(BigDecimal minInvestAmountAdd) {
		this.minInvestAmountAdd = minInvestAmountAdd;
	}
	
	@Column(name = "max_invest_amount", nullable = false, length = 20)
	public BigDecimal getMaxInvestAmount() {
		return maxInvestAmount;
	}
	
	public void setMaxInvestAmount(BigDecimal maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}
	
	@Column(name = "subscribe_fee", length = 10)
	public BigDecimal getSubscribeFee() {
		return subscribeFee;
	}
	
	public void setSubscribeFee(BigDecimal subscribeFee) {
		this.subscribeFee = subscribeFee;
	}
	
	@Column(name = "purchase_fee", length = 10)
	public BigDecimal getPurchaseFee() {
		return purchaseFee;
	}
	
	public void setPurchaseFee(BigDecimal purchaseFee) {
		this.purchaseFee = purchaseFee;
	}
	
	@Column(name = "redeming_fee", length = 10)
	public BigDecimal getRedemingFee() {
		return redemingFee;
	}
	
	public void setRedemingFee(BigDecimal redemingFee) {
		this.redemingFee = redemingFee;
	}
	
	@Column(name = "management_fee", length = 10)
	public BigDecimal getManagementFee() {
		return managementFee;
	}
	
	public void setManagementFee(BigDecimal managementFee) {
		this.managementFee = managementFee;
	}
	
	@Column(name = "custody_fee", length = 10)
	public BigDecimal getCustodyFee() {
		return custodyFee;
	}
	
	public void setCustodyFee(BigDecimal custodyFee) {
		this.custodyFee = custodyFee;
	}
	
	@Column(name = "network_fee", length = 10)
	public BigDecimal getNetworkFee() {
		return networkFee;
	}
	
	public void setNetworkFee(BigDecimal networkFee) {
		this.networkFee = networkFee;
	}
	
	@Column(name = "total_amount", length = 20)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "collect_starttime")
	public Timestamp getCollectStarttime() {
		return collectStarttime;
	}
	
	public void setCollectStarttime(Timestamp collectStarttime) {
		this.collectStarttime = collectStarttime;
	}
	
	@Column(name = "collect_endtime")
	public Timestamp getCollectEndtime() {
		return collectEndtime;
	}
	
	public void setCollectEndtime(Timestamp collectEndtime) {
		this.collectEndtime = collectEndtime;
	}

	@Column(name = "term")
	public Integer getTerm() {
		return term;
	}
	
	public void setTerm(Integer term) {
		this.term = term;
	}
	
	@Column(name = "record_date")
	public Timestamp getRecordDate() {
		return recordDate;
	}
	
	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}
	
	@Column(name = "value_date")
	public Timestamp getValueDate() {
		return valueDate;
	}
	
	public void setValueDate(Timestamp valueDate) {
		this.valueDate = valueDate;
	}
	
	@Column(name = "maturity_date")
	public Timestamp getMaturityDate() {
		return maturityDate;
	}
	
	public void setMaturityDate(Timestamp maturityDate) {
		this.maturityDate = maturityDate;
	}
	
	@Column(name = "flag_purchase")
	public Boolean getFlagPurchase() {
		return flagPurchase;
	}
	
	public void setFlagPurchase(Boolean flagPurchase) {
		this.flagPurchase = flagPurchase;
	}

	@Column(name = "flag_flexible")
	public Boolean getFlagFlexible() {
		return flagFlexible;
	}
	
	public void setFlagFlexible(Boolean flagFlexible) {
		this.flagFlexible = flagFlexible;
	}
	
	@Column(name = "flag_redemption")
	public Boolean getFlagRedemption() {
		return flagRedemption;
	}
	
	public void setFlagRedemption(Boolean flagRedemption) {
		this.flagRedemption = flagRedemption;
	}
	
	@Column(name = "invest_scope", length = 1000)
	public String getInvestScope() {
		return investScope;
	}
	
	public void setInvestScope(String investScope) {
		this.investScope = investScope;
	}

	@Column(name = "style", length = 20)
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	@Column(name = "risk_level", length = 20)
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	@Column(name = "credit_level", length = 20)
	public String getCreditLevel() {
		return creditLevel;
	}
	
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	
	@Column(name = "revenue_feature", length = 2000)
	public String getRevenueFeature() {
		return revenueFeature;
	}
	
	public void setRevenueFeature(String revenueFeature) {
		this.revenueFeature = revenueFeature;
	}
	
	@Column(name = "flag_closeend")
	public Boolean getFlagCloseend() {
		return flagCloseend;
	}
	
	public void setFlagCloseend(Boolean flagCloseend) {
		this.flagCloseend = flagCloseend;
	}

	@Column(name = "guarantee_status", length = 20)
	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}
	
	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	@Column(name = "area", length = 100)
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "remark", length = 5000)
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "net_worth", length = 20)
	public BigDecimal getNetWorth() {
		return netWorth;
	}
	
	public void setNetWorth(BigDecimal netWorth) {
		this.netWorth = netWorth;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "document", length = 36)
	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	
	@Column(name = "payment_type")
	public String getPaymentType() {
		return paymentType;
	}
	

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
