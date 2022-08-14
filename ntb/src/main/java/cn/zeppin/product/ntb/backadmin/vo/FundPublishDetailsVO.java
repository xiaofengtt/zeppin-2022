package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishTypes;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundPublishDetailsVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7957766676785529221L;
	
	private String uuid;
	private String name;
	private String scode;
	private String riskLevel;
	private String type;
	private String typeCN;
	private String status;
	
	private String shortname;
	private String gp;
	private String custodian;
	private Boolean flagStructured;
	private String flagStructuredCN;
	private String structuredType;
	private String structuredTypeCN;
	private String structuredRemark;
	private String style;
	private String styleCN;
	private String creditLevel;
	private String creditLevelCN;
	private String performanceLevel;
	private Boolean flagPurchase;
	private String flagPurchaseCN;
	private Boolean flagRedemption;
	private String flagRedemptionCN;
	private BigDecimal planingScale;
	private BigDecimal actualScale;
	private BigDecimal gpPurchaseScale;
	private BigDecimal lastestScale;
	private Timestamp setuptime;
	private String setuptimeCN;
	private Timestamp collectStarttime;
	private String collectStarttimeCN;
	private Timestamp collectEndtime;
	private String collectEndtimeCN;
	private Timestamp purchaseStarttime;
	private String purchaseStarttimeCN;
	private Timestamp purchaseEndtime;
	private String purchaseEndtimeCN;
	private String goal;
	private String investIdea;
	private String investScope;
	private String investStaregy;
	private String investStandard;
	private String revenueFeature;
	private String riskManagement;
	private BigDecimal netWorth;
	
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public FundPublishDetailsVO(FundPublish fp){
		this.uuid = fp.getUuid();
		this.name = fp.getName();
		this.scode = fp.getScode();
		this.riskLevel = fp.getRiskLevel();
		this.type = fp.getType();
		
		switch (fp.getType()) {
		case FundPublishTypes.CURRENCY:
			this.typeCN = "货币型";
			break;
		case FundPublishTypes.BOND:
			this.typeCN = "债券型";
			break;
		case FundPublishTypes.STOCK:
			this.typeCN = "股票型";
			break;
		case FundPublishTypes.MIX:
			this.typeCN = "混合型";
			break;
		default:
			this.typeCN = "未选择";
			break;
		}
		this.status = fp.getStatus();
		this.shortname = fp.getShortname();
		this.gp = fp.getGp();
		this.custodian = fp.getCustodian();
		
		this.flagStructured = fp.getFlagStructured();
		if(fp.getFlagStructured() !=null){
			if(fp.getFlagStructured()){
				this.flagStructuredCN = "分级型";
			}else{
				this.flagStructuredCN = "普通型";
			}
		}else{
			this.flagStructuredCN = "未选择";
		}
		
		this.structuredType = fp.getStructuredType();
		if("priority".equals(fp.getStructuredType())){
			this.structuredTypeCN = "优先型";
		} else if ("lag".equals(fp.getStructuredType())) {
			this.structuredTypeCN = "劣后型";
		} else{
			this.structuredTypeCN = "未选择";
		}
		this.structuredRemark = fp.getStructuredRemark() == null ? "未选择" : fp.getStructuredRemark();
		this.style = fp.getStyle();
		if("profit".equals(fp.getStyle())){
			this.styleCN = "收益型";
		} else if ("balance".equals(fp.getStyle())) {
			this.styleCN = "平衡型";
		} else {
			this.styleCN = "未选择";
		}
		
		this.creditLevel = fp.getCreditLevel();
		if("high".equals(fp.getCreditLevel())){
			this.creditLevelCN = "高";
		} else if ("middle".equals(fp.getCreditLevel())) {
			this.creditLevelCN = "中";
		} else if ("low".equals(fp.getCreditLevel())) {
			this.creditLevelCN = "低";
		} else {
			this.creditLevelCN = "未选择";
		}
		this.performanceLevel = fp.getPerformanceLevel() == null ? "" : fp.getPerformanceLevel();
		this.flagPurchase = fp.getFlagPurchase() == null ? false : fp.getFlagPurchase();
		if(this.flagPurchase){
			this.flagPurchaseCN = "开放";
		}else{
			this.flagPurchaseCN = "关闭";
		}
		
		this.flagRedemption = fp.getFlagRedemption() == null ? false : fp.getFlagRedemption();
		if(this.flagRedemption){
			this.flagRedemptionCN = "开放";
		}else{
			this.flagRedemptionCN = "关闭";
		}
		
		this.planingScale = fp.getPlaningScale();
		this.actualScale = fp.getActualScale();
		this.gpPurchaseScale = fp.getGpPurchaseScale();
		this.lastestScale = fp.getLastestScale();
		this.setuptime = fp.getSetuptime();
		if(fp.getSetuptime() != null && !"".equals(fp.getSetuptime())){
			this.setuptimeCN =  Utlity.timeSpanToDateString(fp.getSetuptime());
		}else{
			this.setuptimeCN =  "";
		}
		this.collectStarttime = fp.getCollectStarttime();
		if(fp.getCollectStarttime() != null && !"".equals(fp.getCollectStarttime())){
			this.collectStarttimeCN =  Utlity.timeSpanToDateString(fp.getCollectStarttime());
		}else{
			this.collectStarttimeCN =  "";
		}
		this.collectEndtime = fp.getCollectEndtime();
		if(fp.getCollectEndtime() != null && !"".equals(fp.getCollectEndtime())){
			this.collectEndtimeCN =  Utlity.timeSpanToDateString(fp.getCollectEndtime());
		}else{
			this.collectEndtimeCN =  "";
		}
		this.purchaseStarttime = fp.getPurchaseStarttime();
		if(fp.getPurchaseStarttime() != null && !"".equals(fp.getPurchaseStarttime())){
			this.purchaseStarttimeCN =  Utlity.timeSpanToDateString(fp.getPurchaseStarttime());
		}else{
			this.purchaseStarttimeCN =  "";
		}
		this.purchaseEndtime = fp.getPurchaseEndtime();
		if(fp.getPurchaseEndtime() != null && !"".equals(fp.getPurchaseEndtime())){
			this.purchaseEndtimeCN =  Utlity.timeSpanToDateString(fp.getPurchaseEndtime());
		}else{
			this.purchaseEndtimeCN =  "";
		}
		this.goal = fp.getGoal() == null ? "" : fp.getGoal();
		this.investIdea = fp.getInvestIdea() == null ? "":fp.getInvestIdea();
		this.investScope = fp.getInvestScope() == null ? "" : fp.getInvestScope();
		this.investStaregy = fp.getInvestStaregy() == null ? "" : fp.getInvestStaregy();
		this.investStandard = fp.getInvestStandard() == null ? "" : fp.getInvestStandard();
		this.revenueFeature = fp.getRevenueFeature() == null ? "" : fp.getRevenueFeature();
		this.riskManagement = fp.getRiskManagement() == null ? "" : fp.getRiskManagement();
		this.netWorth = fp.getNetWorth();
		this.creator = fp.getCreator();
		this.createtime = fp.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToDateString(fp.getCreatetime());
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getScode() {
		return scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getGp() {
		return gp;
	}

	public void setGp(String gp) {
		this.gp = gp;
	}

	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public Boolean getFlagStructured() {
		return flagStructured;
	}

	public void setFlagStructured(Boolean flagStructured) {
		this.flagStructured = flagStructured;
	}

	public String getStructuredType() {
		return structuredType;
	}

	public void setStructuredType(String structuredType) {
		this.structuredType = structuredType;
	}

	public String getStructuredRemark() {
		return structuredRemark;
	}

	public void setStructuredRemark(String structuredRemark) {
		this.structuredRemark = structuredRemark;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getPerformanceLevel() {
		return performanceLevel;
	}

	public void setPerformanceLevel(String performanceLevel) {
		this.performanceLevel = performanceLevel;
	}

	public Boolean getFlagPurchase() {
		return flagPurchase;
	}

	public void setFlagPurchase(Boolean flagPurchase) {
		this.flagPurchase = flagPurchase;
	}

	public Boolean getFlagRedemption() {
		return flagRedemption;
	}

	public void setFlagRedemption(Boolean flagRedemption) {
		this.flagRedemption = flagRedemption;
	}

	public BigDecimal getPlaningScale() {
		return planingScale;
	}

	public void setPlaningScale(BigDecimal planingScale) {
		this.planingScale = planingScale;
	}

	public BigDecimal getActualScale() {
		return actualScale;
	}

	public void setActualScale(BigDecimal actualScale) {
		this.actualScale = actualScale;
	}

	public BigDecimal getGpPurchaseScale() {
		return gpPurchaseScale;
	}

	public void setGpPurchaseScale(BigDecimal gpPurchaseScale) {
		this.gpPurchaseScale = gpPurchaseScale;
	}

	public BigDecimal getLastestScale() {
		return lastestScale;
	}

	public void setLastestScale(BigDecimal lastestScale) {
		this.lastestScale = lastestScale;
	}

	public Timestamp getSetuptime() {
		return setuptime;
	}

	public void setSetuptime(Timestamp setuptime) {
		this.setuptime = setuptime;
	}

	public Timestamp getCollectStarttime() {
		return collectStarttime;
	}

	public void setCollectStarttime(Timestamp collectStarttime) {
		this.collectStarttime = collectStarttime;
	}

	public Timestamp getCollectEndtime() {
		return collectEndtime;
	}

	public void setCollectEndtime(Timestamp collectEndtime) {
		this.collectEndtime = collectEndtime;
	}

	public Timestamp getPurchaseStarttime() {
		return purchaseStarttime;
	}

	public void setPurchaseStarttime(Timestamp purchaseStarttime) {
		this.purchaseStarttime = purchaseStarttime;
	}

	public Timestamp getPurchaseEndtime() {
		return purchaseEndtime;
	}

	public void setPurchaseEndtime(Timestamp purchaseEndtime) {
		this.purchaseEndtime = purchaseEndtime;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getInvestIdea() {
		return investIdea;
	}

	public void setInvestIdea(String investIdea) {
		this.investIdea = investIdea;
	}

	public String getInvestScope() {
		return investScope;
	}

	public void setInvestScope(String investScope) {
		this.investScope = investScope;
	}

	public String getInvestStaregy() {
		return investStaregy;
	}

	public void setInvestStaregy(String investStaregy) {
		this.investStaregy = investStaregy;
	}

	public String getInvestStandard() {
		return investStandard;
	}

	public void setInvestStandard(String investStandard) {
		this.investStandard = investStandard;
	}

	public String getRevenueFeature() {
		return revenueFeature;
	}

	public void setRevenueFeature(String revenueFeature) {
		this.revenueFeature = revenueFeature;
	}

	public String getRiskManagement() {
		return riskManagement;
	}

	public void setRiskManagement(String riskManagement) {
		this.riskManagement = riskManagement;
	}

	public BigDecimal getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(BigDecimal netWorth) {
		this.netWorth = netWorth;
	}

	public String getTypeCN() {
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}

	public String getFlagStructuredCN() {
		return flagStructuredCN;
	}

	public void setFlagStructuredCN(String flagStructuredCN) {
		this.flagStructuredCN = flagStructuredCN;
	}

	public String getStructuredTypeCN() {
		return structuredTypeCN;
	}

	public void setStructuredTypeCN(String structuredTypeCN) {
		this.structuredTypeCN = structuredTypeCN;
	}

	public String getStyleCN() {
		return styleCN;
	}

	public void setStyleCN(String styleCN) {
		this.styleCN = styleCN;
	}

	public String getCreditLevelCN() {
		return creditLevelCN;
	}

	public void setCreditLevelCN(String creditLevelCN) {
		this.creditLevelCN = creditLevelCN;
	}

	public String getFlagPurchaseCN() {
		return flagPurchaseCN;
	}

	public void setFlagPurchaseCN(String flagPurchaseCN) {
		this.flagPurchaseCN = flagPurchaseCN;
	}

	public String getFlagRedemptionCN() {
		return flagRedemptionCN;
	}

	public void setFlagRedemptionCN(String flagRedemptionCN) {
		this.flagRedemptionCN = flagRedemptionCN;
	}

	public String getSetuptimeCN() {
		return setuptimeCN;
	}

	public void setSetuptimeCN(String setuptimeCN) {
		this.setuptimeCN = setuptimeCN;
	}

	public String getCollectStarttimeCN() {
		return collectStarttimeCN;
	}

	public void setCollectStarttimeCN(String collectStarttimeCN) {
		this.collectStarttimeCN = collectStarttimeCN;
	}

	public String getCollectEndtimeCN() {
		return collectEndtimeCN;
	}

	public void setCollectEndtimeCN(String collectEndtimeCN) {
		this.collectEndtimeCN = collectEndtimeCN;
	}

	public String getPurchaseStarttimeCN() {
		return purchaseStarttimeCN;
	}

	public void setPurchaseStarttimeCN(String purchaseStarttimeCN) {
		this.purchaseStarttimeCN = purchaseStarttimeCN;
	}

	public String getPurchaseEndtimeCN() {
		return purchaseEndtimeCN;
	}

	public void setPurchaseEndtimeCN(String purchaseEndtimeCN) {
		this.purchaseEndtimeCN = purchaseEndtimeCN;
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
	
}
