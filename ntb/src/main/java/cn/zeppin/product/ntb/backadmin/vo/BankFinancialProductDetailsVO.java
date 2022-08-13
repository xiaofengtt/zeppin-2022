package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductTypes;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductDetailsVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345581280584932467L;
	
	private String uuid;//id
	private String name;//名称
	private String scode;//产品编号
	private String url;//产品编号
	private String type;//产品类型
	private String typeCN;
	private String status;//产品状态
	private String stage;//产品阶段
	private String stageCN;
	private String target;//面向对象
	private String targetCN;
	private String targetAnnualizedReturnRate;//目标年化收益率
	private BigDecimal totalAmount;//产品规模
	private Timestamp collectStarttime;//认购起始日
	private String collectStarttimeCN;
	private Timestamp collectEndtime;//认购截止日
	private String collectEndtimeCN;
	private Integer term;//产品期限
	private Timestamp recordDate;//登记日
	private String recordDateCN;
	private Timestamp valueDate;//起息日
	private String valueDateCN;
	private Timestamp maturityDate;//到期日
	private String maturityDateCN;
	private Boolean flagPurchase;//申购状态
	private Boolean flagRedemption;//赎回状态
	private Boolean flagFlexible;//灵活期限
	private String riskLevel;//风险等级
	private String riskLevelCN;
	private String netWorth;//当前净值
	private String creator;//信息录入人
	private String creatorName;
	private Timestamp createtime;//录入时间
	private String createtimeCN;
	
	private String series;//系列
	private String shortname;//简称
	private String custodian;//资金托管人
	private String custodianCN;
	private String custodianLogo;
	private String currencyType;//理财币种
	private String currencyTypeCN;
	private String minAnnualizedReturnRate;//最小年化收益率
	private BigDecimal minInvestAmount;//最小投资金额
	private BigDecimal minInvestAmountAdd;//最小投资递增
	private BigDecimal maxInvestAmount;//最大投资金额
	private String subscribeFee;//认购费
	private String purchaseFee;//申购费
	private String redemingFee;//赎回费
	private String managementFee;//管理费
	private String custodyFee;//托管费
	private String networkFee;//销售渠道费
	private String investScope;//投资范围
	private String style;//投资风格
	private String styleCN;
	private String creditLevel;//信用等级
	private String creditLevelCN;
	
	private String revenueFeature;//产品收益说明
	private Boolean flagCloseend;//是否封闭产品
	private String guaranteeStatus;//保本保息状态
	private String guaranteeStatusCN;
	
	private String area;//发行地区
	private String areaCN;
	
	private String remark;//更多描述
	private String document;//说明书（文件）
	private String documentCN;//说明书名称
	private String documentType;//类型
	private String documentURL;//下载地址
	
	private String paymentType;//收益支付方式
	private String paymentTypeCN;
	
	
	public BankFinancialProductDetailsVO() {
		super();
	}
	
	public BankFinancialProductDetailsVO(BankFinancialProduct bfp) {
		this.uuid = bfp.getUuid();
		this.name = bfp.getName();
		this.scode = bfp.getScode();
		this.url = bfp.getUrl();
		this.type = bfp.getType();
		if(BankFinancialProductTypes.INCOME.equals(bfp.getType())){
			this.typeCN = "固定收益";
		} else if (BankFinancialProductTypes.FLOATINGINCOME.equals(bfp.getType())) {
			this.typeCN = "保本浮动收益";
		} else if (BankFinancialProductTypes.UNFLOATINGINCOME.equals(bfp.getType())) {
			this.typeCN = "非保本浮动收益";
		}else{
			this.typeCN = "未选择";
		}
		
		this.status = bfp.getStatus();
		this.stage = bfp.getStage();
		if(BankFinancialProductStage.UNSTART.equals(bfp.getStage())){
			this.stageCN = "未开始";
		} else if (BankFinancialProductStage.COLLECT.equals(bfp.getStage())) {
			this.stageCN = "募集中";
		} else if (BankFinancialProductStage.INCOME.equals(bfp.getStage())) {
			this.stageCN = "收益中";
		} else if (BankFinancialProductStage.FINISHED.equals(bfp.getStage())) {
			this.stageCN = "已结束";
		}
		this.target = bfp.getTarget();
		if("individual".equals(target)){
			this.targetCN="个人";
		}else if("enterprise".equals(target)){
			this.targetCN="企业";
		}else{
			this.targetCN="未选择";
		}
		if(bfp.getTargetAnnualizedReturnRate() != null){
			this.targetAnnualizedReturnRate = bfp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.targetAnnualizedReturnRate = "0.00";
		}
		this.totalAmount = bfp.getTotalAmount();
		if(bfp.getTotalAmount() != null){
			this.totalAmount = bfp.getTotalAmount().divide(BigDecimal.valueOf(100000000));
		}
		if(bfp.getCollectStarttime() != null && !"".equals(bfp.getCollectStarttime())){
			this.collectStarttime = bfp.getCollectStarttime();
			this.collectStarttimeCN = Utlity.timeSpanToString(bfp.getCollectStarttime());
		}
		if(bfp.getCollectEndtime() != null && !"".equals(bfp.getCollectEndtime())){
			this.collectEndtime = bfp.getCollectStarttime();
			this.collectEndtimeCN = Utlity.timeSpanToString(bfp.getCollectEndtime());
		}		
		this.term = bfp.getTerm() == null ? 0 : bfp.getTerm();
		if(bfp.getRecordDate() != null && !"".equals(bfp.getRecordDate())){
			this.recordDate = bfp.getRecordDate();
			this.recordDateCN = Utlity.timeSpanToDateString(bfp.getRecordDate());
		}
		if(bfp.getValueDate() != null && !"".equals(bfp.getValueDate())){
			this.valueDate = bfp.getValueDate();
			this.valueDateCN = Utlity.timeSpanToDateString(bfp.getValueDate());
		}
		if(bfp.getMaturityDate() != null && !"".equals(bfp.getMaturityDate())){
			this.maturityDate = bfp.getMaturityDate();
			this.maturityDateCN = Utlity.timeSpanToDateString(bfp.getMaturityDate());
		}
		
		this.flagPurchase = bfp.getFlagPurchase() == null ? false : bfp.getFlagPurchase();
		this.flagRedemption = bfp.getFlagRedemption() == null ? false : bfp.getFlagRedemption();
		this.flagFlexible = bfp.getFlagFlexible() == null ? false : bfp.getFlagFlexible();
		this.riskLevel = bfp.getRiskLevel();
		if(bfp.getRiskLevel() != null){
			switch (bfp.getRiskLevel()) {
			case "R1":
				this.riskLevelCN = "R1(谨慎型)";
				break;
			case "R2":
				this.riskLevelCN = "R2(稳健型)";
				break;
			case "R3":
				this.riskLevelCN = "R3(平衡型)";
				break;
			case "R4":
				this.riskLevelCN = "R4(进取型)";
				break;
			case "R5":
				this.riskLevelCN = "R5(激进型)";
				break;
			default:
				this.riskLevelCN = "未选择";
				break;
			}
		}else{
			this.riskLevelCN = "未选择";
		}
		
		
		if(bfp.getNetWorth() != null){
			this.netWorth = bfp.getNetWorth().setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.netWorth = "0.0000";
		}
		
		this.creator = bfp.getCreator();
		this.createtime = bfp.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToDateString(bfp.getCreatetime());
		
		this.series = bfp.getSeries();
		this.shortname = bfp.getShortname();
		this.custodian = bfp.getCustodian() == null ? "" : bfp.getCustodian();
		this.currencyType = bfp.getCurrencyType() == null ? "" : bfp.getCurrencyType();
		if(bfp.getCurrencyType() != null){
			if("rmb".equals(bfp.getCurrencyType())){
				this.currencyTypeCN = "人民币";
			} else if ("dollar".equals(bfp.getCurrencyType())) {
				this.currencyTypeCN = "外币";
			} else {
				this.currencyTypeCN = "未选择";
			}
		} else {
			this.currencyTypeCN = "未选择";
		}
		
		if(bfp.getMinAnnualizedReturnRate() != null){
			this.minAnnualizedReturnRate = bfp.getMinAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.minAnnualizedReturnRate = "0.00";
		}
		if(bfp.getMinInvestAmount() != null){
			this.minInvestAmount = bfp.getMinInvestAmount();
		}
		if(bfp.getMinInvestAmountAdd() != null){
			this.minInvestAmountAdd = bfp.getMinInvestAmountAdd();
		}
		if(bfp.getMaxInvestAmount() != null){
			this.maxInvestAmount = bfp.getMaxInvestAmount();
		}
		
		if(bfp.getSubscribeFee() != null){
			this.subscribeFee = bfp.getSubscribeFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.subscribeFee = "0.00";
		}
		if(bfp.getPurchaseFee() != null){
			this.purchaseFee = bfp.getPurchaseFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.purchaseFee = "0.00";
		}
		if(bfp.getRedemingFee() != null){
			this.redemingFee = bfp.getRedemingFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.redemingFee = "0.00";
		}
		if(bfp.getManagementFee() != null){
			this.managementFee = bfp.getManagementFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.managementFee = "0.00";
		}
		if(bfp.getCustodyFee() != null){
			this.custodyFee = bfp.getCustodyFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.custodyFee = "0.00";
		}
		if(bfp.getNetworkFee() != null){
			this.networkFee = bfp.getNetworkFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.networkFee = "0.00";
		}
		this.investScope = bfp.getInvestScope() == null ? "" : bfp.getInvestScope();
		this.style = bfp.getStyle() == null ? "" : bfp.getStyle();
		if(bfp.getStyle() != null){
			if("profit".equals(bfp.getStyle())){
				this.styleCN = "收益型";
			} else if ("balance".equals(bfp.getStyle())) {
				this.styleCN = "平衡型";
			} else {
				this.styleCN = "未选择";
			}
		} else {
			this.styleCN = "未选择";
		}
		
		this.creditLevel = bfp.getCreditLevel() == null ? "" : bfp.getCreditLevel();
		if(bfp.getCreditLevel() != null){
			if("one".equals(bfp.getCreditLevel())){
				this.creditLevelCN = "一级";
			} else if ("two".equals(bfp.getCreditLevel())) {
				this.creditLevelCN = "二级";
			} else {
				this.creditLevelCN = "未选择";
			}
		} else {
			this.creditLevelCN = "未选择";
		}
		this.revenueFeature = bfp.getRevenueFeature() == null ? "" : bfp.getRevenueFeature();
		this.flagCloseend = bfp.getFlagCloseend() == null ? false : bfp.getFlagCloseend();
		
		this.guaranteeStatus = bfp.getGuaranteeStatus() == null ? "" : bfp.getGuaranteeStatus();
		if(bfp.getGuaranteeStatus() != null){
			if("1".equals(bfp.getGuaranteeStatus())){
				this.guaranteeStatusCN = "不保本";
			} else if ("2".equals(bfp.getGuaranteeStatus())) {
				this.guaranteeStatusCN = "保本";
			} else if ("3".equals(bfp.getGuaranteeStatus())) {
				this.guaranteeStatusCN = "保本保息";
			} else {
				this.guaranteeStatusCN = "未选择";
			}
		} else {
			this.guaranteeStatusCN = "未选择";
		}
		this.area = bfp.getArea();
		this.remark = bfp.getRemark() == null ? "" : bfp.getRemark();
		this.document = bfp.getDocument();
		this.paymentType = bfp.getPaymentType() == null ? "" : bfp.getPaymentType();
		if(bfp.getPaymentType() != null){
			if("day".equals(bfp.getPaymentType())){
				this.paymentTypeCN = "按日支付";
			} else if ("month".equals(bfp.getPaymentType())) {
				this.paymentTypeCN = "按月支付";
			} else if ("last".equals(bfp.getPaymentType())) {
				this.paymentTypeCN = "到期一次性支付";
			} else {
				this.paymentTypeCN = "未选择";
			}
		} else {
			this.paymentTypeCN = "未选择";
		}
		
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getStageCN() {
		return stageCN;
	}

	public void setStageCN(String stageCN) {
		this.stageCN = stageCN;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getTargetCN() {
		return targetCN;
	}

	public void setTargetCN(String targetCN) {
		this.targetCN = targetCN;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getCollectStarttime() {
		return collectStarttime;
	}

	public void setCollectStarttime(Timestamp collectStarttime) {
		this.collectStarttime = collectStarttime;
	}

	public String getCollectStarttimeCN() {
		return collectStarttimeCN;
	}

	public void setCollectStarttimeCN(String collectStarttimeCN) {
		this.collectStarttimeCN = collectStarttimeCN;
	}
	
	public Timestamp getCollectEndtime() {
		return collectEndtime;
	}

	public void setCollectEndtime(Timestamp collectEndtime) {
		this.collectEndtime = collectEndtime;
	}

	public String getCollectEndtimeCN() {
		return collectEndtimeCN;
	}

	public void setCollectEndtimeCN(String collectEndtimeCN) {
		this.collectEndtimeCN = collectEndtimeCN;
	}
	
	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordDateCN() {
		return recordDateCN;
	}

	public void setRecordDateCN(String recordDateCN) {
		this.recordDateCN = recordDateCN;
	}
	
	public Timestamp getValueDate() {
		return valueDate;
	}

	public void setValueDate(Timestamp valueDate) {
		this.valueDate = valueDate;
	}

	public String getValueDateCN() {
		return valueDateCN;
	}

	public void setValueDate(String valueDateCN) {
		this.valueDateCN = valueDateCN;
	}
	
	public Timestamp getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Timestamp maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getMaturityDateCN() {
		return maturityDateCN;
	}

	public void setMaturityDateCN(String maturityDateCN) {
		this.maturityDateCN = maturityDateCN;
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

	public Boolean getFlagFlexible() {
		return flagFlexible;
	}
	
	public void setFlagFlexible(Boolean flagFlexible) {
		this.flagFlexible = flagFlexible;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
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
	
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public BigDecimal getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(BigDecimal minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public BigDecimal getMinInvestAmountAdd() {
		return minInvestAmountAdd;
	}

	public void setMinInvestAmountAdd(BigDecimal minInvestAmountAdd) {
		this.minInvestAmountAdd = minInvestAmountAdd;
	}

	public BigDecimal getMaxInvestAmount() {
		return maxInvestAmount;
	}

	public void setMaxInvestAmount(BigDecimal maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}

	public String getInvestScope() {
		return investScope;
	}

	public void setInvestScope(String investScope) {
		this.investScope = investScope;
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

	public String getRevenueFeature() {
		return revenueFeature;
	}

	public void setRevenueFeature(String revenueFeature) {
		this.revenueFeature = revenueFeature;
	}

	public Boolean getFlagCloseend() {
		return flagCloseend;
	}

	public void setFlagCloseend(Boolean flagCloseend) {
		this.flagCloseend = flagCloseend;
	}

	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTargetAnnualizedReturnRate() {
		return targetAnnualizedReturnRate;
	}

	public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
		this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
	}

	public String getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public String getMinAnnualizedReturnRate() {
		return minAnnualizedReturnRate;
	}

	public void setMinAnnualizedReturnRate(String minAnnualizedReturnRate) {
		this.minAnnualizedReturnRate = minAnnualizedReturnRate;
	}

	public String getSubscribeFee() {
		return subscribeFee;
	}

	public void setSubscribeFee(String subscribeFee) {
		this.subscribeFee = subscribeFee;
	}

	public String getPurchaseFee() {
		return purchaseFee;
	}

	public void setPurchaseFee(String purchaseFee) {
		this.purchaseFee = purchaseFee;
	}

	public String getRedemingFee() {
		return redemingFee;
	}

	public void setRedemingFee(String redemingFee) {
		this.redemingFee = redemingFee;
	}

	public String getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}

	public String getCustodyFee() {
		return custodyFee;
	}

	public void setCustodyFee(String custodyFee) {
		this.custodyFee = custodyFee;
	}
	
	public String getNetworkFee() {
		return networkFee;
	}

	public void setNetworkFee(String networkFee) {
		this.networkFee = networkFee;
	}
	
	public String getTypeCN() {
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}

	public String getRiskLevelCN() {
		return riskLevelCN;
	}

	public void setRiskLevelCN(String riskLevelCN) {
		this.riskLevelCN = riskLevelCN;
	}

	public String getCustodianCN() {
		return custodianCN;
	}

	public void setCustodianCN(String custodianCN) {
		this.custodianCN = custodianCN;
	}

	public String getCustodianLogo() {
		return custodianLogo;
	}

	public void setCustodianLogo(String custodianLogo) {
		this.custodianLogo = custodianLogo;
	}
	
	public String getCurrencyTypeCN() {
		return currencyTypeCN;
	}

	public void setCurrencyTypeCN(String currencyTypeCN) {
		this.currencyTypeCN = currencyTypeCN;
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

	public String getGuaranteeStatusCN() {
		return guaranteeStatusCN;
	}

	public void setGuaranteeStatusCN(String guaranteeStatusCN) {
		this.guaranteeStatusCN = guaranteeStatusCN;
	}

	public String getAreaCN() {
		return areaCN;
	}

	public void setAreaCN(String areaCN) {
		this.areaCN = areaCN;
	}

	public String getDocumentCN() {
		return documentCN;
	}

	public void setDocumentCN(String documentCN) {
		this.documentCN = documentCN;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getPaymentTypeCN() {
		return paymentTypeCN;
	}

	public void setPaymentTypeCN(String paymentTypeCN) {
		this.paymentTypeCN = paymentTypeCN;
	}
}
