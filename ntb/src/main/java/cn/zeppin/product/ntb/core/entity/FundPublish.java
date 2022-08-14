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
 * @description 【数据对象】活期理财信息
 */

@Entity
@Table(name = "fund_publish")
public class FundPublish extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945212316881961605L;
	
	private String uuid;
	private String name;
	private String scode;
	private String shortname;
	private String type;
	private String status;
	private String gp;
	private String custodian;
	private Boolean flagStructured;
	private String structuredType;
	private String structuredRemark;
	private String style;
	private String riskLevel;
	private String creditLevel;
	private String performanceLevel;
	private Boolean flagPurchase;
	private Boolean flagRedemption;
	private BigDecimal planingScale;
	private BigDecimal actualScale;
	private BigDecimal gpPurchaseScale;
	private BigDecimal lastestScale;
	private Timestamp setuptime;
	private Timestamp collectStarttime;
	private Timestamp collectEndtime;
	private Timestamp purchaseStarttime;
	private Timestamp purchaseEndtime;
	private String goal;
	private String investIdea;
	private String investScope;
	private String investStaregy;
	private String investStandard;
	private String revenueFeature;
	private String riskManagement;
	private BigDecimal netWorth;
	private String creator;
	private Timestamp createtime;
	
	public class FundPublishStatus{
		public final static String CHECKED = "checked";
		public final static String DELETED = "deleted";
	}
	
	public class FundPublishTypes{
		public final static String CURRENCY = "currency";//货币
		public final static String BOND = "bond";//债券
		public final static String STOCK = "stock";//股票
		public final static String MIX = "mix";//混合
	}
	
	public class FundPublishUuid{
		public final static String CURRENT = "eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee";
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
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "gp", length = 50)
	public String getGp() {
		return gp;
	}
	
	public void setGp(String gp) {
		this.gp = gp;
	}

	@Column(name = "custodian", length = 50)
	public String getCustodian() {
		return custodian;
	}
	
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	@Column(name = "flag_structured")
	public Boolean getFlagStructured() {
		return flagStructured;
	}
	
	public void setFlagStructured(Boolean flagStructured) {
		this.flagStructured = flagStructured;
	}
	
	@Column(name = "structured_type", length = 20)
	public String getStructuredType() {
		return structuredType;
	}
	
	public void setStructuredType(String structuredType) {
		this.structuredType = structuredType;
	}
	
	@Column(name = "structured_remark", length = 1000)
	public String getStructuredRemark() {
		return structuredRemark;
	}
	
	public void setStructuredRemark(String structuredRemark) {
		this.structuredRemark = structuredRemark;
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

	@Column(name = "performance_level", length = 30)
	public String getPerformanceLevel() {
		return performanceLevel;
	}
	
	public void setPerformanceLevel(String performanceLevel) {
		this.performanceLevel = performanceLevel;
	}
	
	@Column(name = "flag_purchase")
	public Boolean getFlagPurchase() {
		return flagPurchase;
	}
	
	public void setFlagPurchase(Boolean flagPurchase) {
		this.flagPurchase = flagPurchase;
	}

	@Column(name = "flag_redemption")
	public Boolean getFlagRedemption() {
		return flagRedemption;
	}
	
	public void setFlagRedemption(Boolean flagRedemption) {
		this.flagRedemption = flagRedemption;
	}
	
	@Column(name = "planing_scale", length = 20)
	public BigDecimal getPlaningScale() {
		return planingScale;
	}
	
	public void setPlaningScale(BigDecimal planingScale) {
		this.planingScale = planingScale;
	}

	@Column(name = "actual_scale", length = 20)
	public BigDecimal getActualScale() {
		return actualScale;
	}
	
	public void setActualScale(BigDecimal actualScale) {
		this.actualScale = actualScale;
	}
	
	@Column(name = "gp_purchase_scale", length = 20)
	public BigDecimal getGpPurchaseScale() {
		return gpPurchaseScale;
	}
	
	public void setGpPurchaseScale(BigDecimal gpPurchaseScale) {
		this.gpPurchaseScale = gpPurchaseScale;
	}
	
	@Column(name = "lastest_scale", length = 20)
	public BigDecimal getLastestScale() {
		return lastestScale;
	}
	
	public void setLastestScale(BigDecimal lastestScale) {
		this.lastestScale = lastestScale;
	}
	
	@Column(name = "setuptime")
	public Timestamp getSetuptime() {
		return setuptime;
	}
	
	public void setSetuptime(Timestamp setuptime) {
		this.setuptime = setuptime;
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

	@Column(name = "purchase_starttime")
	public Timestamp getPurchaseStarttime() {
		return purchaseStarttime;
	}
	
	public void setPurchaseStarttime(Timestamp purchaseStarttime) {
		this.purchaseStarttime = purchaseStarttime;
	}
	
	@Column(name = "purchase_endtime")
	public Timestamp getPurchaseEndtime() {
		return purchaseEndtime;
	}
	
	public void setPurchaseEndtime(Timestamp purchaseEndtime) {
		this.purchaseEndtime = purchaseEndtime;
	}
	
	@Column(name = "goal", length = 1000)
	public String getGoal() {
		return goal;
	}
	
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	@Column(name = "invest_idea", length = 1000)
	public String getInvestIdea() {
		return investIdea;
	}
	
	public void setInvestIdea(String investIdea) {
		this.investIdea = investIdea;
	}
	
	@Column(name = "invest_scope", length = 1000)
	public String getInvestScope() {
		return investScope;
	}
	
	public void setInvestScope(String investScope) {
		this.investScope = investScope;
	}

	@Column(name = "invest_staregy", length = 1000)
	public String getInvestStaregy() {
		return investStaregy;
	}
	
	public void setInvestStaregy(String investStaregy) {
		this.investStaregy = investStaregy;
	}

	@Column(name = "invest_standard", length = 1000)
	public String getInvestStandard() {
		return investStandard;
	}
	
	public void setInvestStandard(String investStandard) {
		this.investStandard = investStandard;
	}
	
	@Column(name = "revenue_feature", length = 1000)
	public String getRevenueFeature() {
		return revenueFeature;
	}
	
	public void setRevenueFeature(String revenueFeature) {
		this.revenueFeature = revenueFeature;
	}
	
	@Column(name = "risk_management", length = 1000)
	public String getRiskManagement() {
		return riskManagement;
	}
	
	public void setRiskManagement(String riskManagement) {
		this.riskManagement = riskManagement;
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
}
