package cn.zeppin.product.ntb.web.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishTypes;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.HtmlHelper;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductPublishDetailsVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345581280584932467L;
	
	private String uuid;//id
	private String bankFinancialProduct;//所继承的银行理财产品ID
	private String name;//名称
	private String scode;//产品编号
	private String url;//url
	private String type;//产品类型
	private String typeCN;
	private String status;//产品状态
	private String stage;//产品阶段
	private String stageCN;
	private String target;//面向对象
	private String targetCN;
	private String targetAnnualizedReturnRate;//目标年化收益率
	private BigDecimal totalAmount;//产品规模
	private BigDecimal collectAmount;//募集规模
	private String collectAmountCN;//募集规模
	private Timestamp collectStarttime;//认购起始日
	private String collectStarttimeCN;
	private String collectStarttimeWeb;
	private Timestamp collectEndtime;//认购截止日
	private String collectEndtimeCN;
	private String collectEndtimeWeb;
	private Integer term;//产品期限
	private Timestamp recordDate;//登记日
	private String recordDateCN;
	private Timestamp valueDate;//起息日
	private String valueDateCN;
	private String valueDateWeb;
	private Timestamp maturityDate;//到期日
	private String maturityDateCN;
	private String maturityDateWeb;
	private Boolean flagPurchase;//申购状态
	private Boolean flagRedemption;//赎回状态
	private Boolean flagFlexible;//灵活期限
	private String riskLevel;//风险等级
	private String riskLevelCN;
	private String netWorth;//当前净值
	
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
	private String investScopeWeb;//投资范围(去除HTML标签)
	private String style;//投资风格
	private String styleCN;
	private String creditLevel;//信用等级
	private String creditLevelCN;
	
	private String revenueFeature;//产品收益说明
	private String revenueFeatureWeb;//产品收益说明(去除HTML标签)
	private Boolean flagCloseend;//是否封闭产品
	private String guaranteeStatus;//保本保息状态
	private String guaranteeStatusCN;
	
	private String area;//发行地区
	private String areaCN;
	
	private String remark;//更多描述
	private String remarkWeb;//更多描述(去除HTML标签)
	private String document;//说明书（文件）
	private String documentCN;//说明书名称
	private String documentType;//类型
	private String documentURL;//下载地址
	
	private String paymentType;//收益支付方式
	private String paymentTypeCN;
	
	private String minInvestAmountNum;//最小投资金额
	private String minInvestAmountCN;
	
	private String minInvestAmountAddNum;//最小投资金额递增
	private String minInvestAmountAddCN;

	private String maxInvestAmountNum;//最大投资金额
	private String maxInvestAmountCN;
	
	//带逗号 
	private String minInvestAmountLess;//最小投资金额
	private String minInvestAmountAddLess;//最小投资递增
	private String maxInvestAmountLess;//最大投资金额
	
	
	private Boolean flagBuy;
	private String iconColorUrl;//银行icon
	private String totalRaise;//剩余募集金额
	private String realReturnRate;//实际收益
	private String realReturnRateCN;//实际收益
	
	public BankFinancialProductPublishDetailsVO() {
		super();
	}
	
	public BankFinancialProductPublishDetailsVO(BankFinancialProductPublish bfp) {
		this.uuid = bfp.getUuid();
		this.name = Utlity.strDscape(bfp.getName());
		if(bfp.getUrl() != null){
			this.url = Utlity.strDscape(bfp.getUrl());
		} else {
			this.url = "";
		}
		this.scode = Utlity.strDscape(bfp.getScode());
		this.type = bfp.getType();
		if(BankFinancialProductPublishTypes.INCOME.equals(bfp.getType())){
			this.typeCN = "固定收益";
		} else if (BankFinancialProductPublishTypes.FLOATINGINCOME.equals(bfp.getType())) {
			this.typeCN = "保本浮动收益";
		} else if (BankFinancialProductPublishTypes.UNFLOATINGINCOME.equals(bfp.getType())) {
			this.typeCN = "非保本浮动收益";
		}else{
			this.typeCN = "未选择";
		}
		
		this.status = bfp.getStatus();
		this.stage = bfp.getStage();
		if(BankFinancialProductPublishStage.UNSTART.equals(bfp.getStage())){
			this.stageCN = "未开始";
		} else if (BankFinancialProductPublishStage.COLLECT.equals(bfp.getStage())) {
			this.stageCN = "募集中";
		} else if (BankFinancialProductPublishStage.UNINVEST.equals(bfp.getStage())) {
			this.stageCN = "投资中";
		} else if (BankFinancialProductPublishStage.INVESTED.equals(bfp.getStage())) {
			this.stageCN = "投资完成";
		} else if (BankFinancialProductPublishStage.PROFIT.equals(bfp.getStage())) {
			this.stageCN = "收益中";
		} else if (BankFinancialProductPublishStage.BALANCE.equals(bfp.getStage())) {
			this.stageCN = "结算中";
		} else if (BankFinancialProductPublishStage.FINISHED.equals(bfp.getStage())) {
			this.stageCN = "已完成";
		} else if (BankFinancialProductPublishStage.EXCEPTION.equals(bfp.getStage())) {
			this.stageCN = "异常下线";
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
		this.collectAmount = bfp.getCollectAmount();
		if(bfp.getCollectAmount() != null){
			if(collectAmount.divide(BigDecimal.valueOf(100000000)).compareTo(BigDecimal.ONE) < 0){
				this.collectAmountCN = bfp.getCollectAmount().divide(BigDecimal.valueOf(10000)) + "万";
			}else{
				this.collectAmountCN = bfp.getCollectAmount().divide(BigDecimal.valueOf(100000000)) + "亿";
			}
		}
		if(bfp.getCollectStarttime() != null && !"".equals(bfp.getCollectStarttime())){
			this.collectStarttime = bfp.getCollectStarttime();
			this.collectStarttimeCN = Utlity.timeSpanToString(bfp.getCollectStarttime());
//			this.collectStarttimeWeb = Utlity.timeSpanToDateString(bfp.getCollectStarttime());
			this.collectStarttimeWeb = Utlity.timeSpanToDateStringLess(bfp.getCollectStarttime());
		}
		if(bfp.getCollectEndtime() != null && !"".equals(bfp.getCollectEndtime())){
			this.collectEndtime = bfp.getCollectStarttime();
			this.collectEndtimeCN = Utlity.timeSpanToString(bfp.getCollectEndtime());
//			this.collectEndtimeWeb = Utlity.timeSpanToDateString(bfp.getCollectEndtime());
			this.collectEndtimeWeb = Utlity.timeSpanToDateStringLess(bfp.getCollectEndtime());
		}		
		this.term = bfp.getTerm() == null ? 0 : bfp.getTerm();
		if(bfp.getRecordDate() != null && !"".equals(bfp.getRecordDate())){
			this.recordDate = bfp.getRecordDate();
			this.recordDateCN = Utlity.timeSpanToDateString(bfp.getRecordDate());
		}
		if(bfp.getValueDate() != null && !"".equals(bfp.getValueDate())){
			this.valueDate = bfp.getValueDate();
			this.valueDateCN = Utlity.timeSpanToDateString(bfp.getValueDate());
			this.valueDateWeb = Utlity.timeSpanToDateStringLess(bfp.getValueDate());
		}
		if(bfp.getMaturityDate() != null && !"".equals(bfp.getMaturityDate())){
			this.maturityDate = bfp.getMaturityDate();
			this.maturityDateCN = Utlity.timeSpanToDateString(bfp.getMaturityDate());
			this.maturityDateWeb = Utlity.timeSpanToDateStringLess(bfp.getMaturityDate());
		}
		
		this.flagPurchase = bfp.getFlagPurchase() == null ? false : bfp.getFlagPurchase();		
		this.flagRedemption = bfp.getFlagRedemption() == null ? false : bfp.getFlagRedemption();
		this.flagFlexible = bfp.getFlagFlexible() == null ? false : bfp.getFlagFlexible();
		this.riskLevel = bfp.getRiskLevel();
		if(bfp.getRiskLevel() != null){
			switch (bfp.getRiskLevel()) {
			case "R1":
				this.riskLevelCN = "R1(低风险)";
				break;
			case "R2":
				this.riskLevelCN = "R2(较低风险)";
				break;
			case "R3":
				this.riskLevelCN = "R3(中风险)";
				break;
			case "R4":
				this.riskLevelCN = "R4(较高风险)";
				break;
			case "R5":
				this.riskLevelCN = "R5(高风险)";
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
		
		this.series = Utlity.strDscape(bfp.getSeries());
		if(bfp.getShortname() != null){
			this.shortname = Utlity.strDscape(bfp.getShortname());
		} else {
			this.shortname = "";
		}
		
		this.custodian = bfp.getCustodian() == null ? "" : bfp.getCustodian();
		this.custodianCN = "";
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
		try {
			this.investScopeWeb = bfp.getInvestScope() == null ? "" : Utlity.strDscape(HtmlHelper.parseString2Html(bfp.getInvestScope()));
		} catch (Exception e) {
			e.printStackTrace();
			this.investScopeWeb = "";
		}
		
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
		
		try {
			this.revenueFeatureWeb = bfp.getRevenueFeature() == null ? "" : Utlity.strDscape(HtmlHelper.parseString2Html(bfp.getRevenueFeature()));
		} catch (Exception e) {
			e.printStackTrace();
			this.revenueFeatureWeb = "";
		}
		
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
		this.area = bfp.getArea() == null ? "" : bfp.getArea();
		this.areaCN = "";
		this.remark = bfp.getRemark() == null ? "" : bfp.getRemark();
		
		try {
			this.remarkWeb = bfp.getRemark() == null ? "" : Utlity.strDscape(HtmlHelper.parseString2Html(bfp.getRemark()));
		} catch (Exception e) {
			e.printStackTrace();
			this.remarkWeb = "";
		}
		
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
		
		Map<String, String> minInvestAmountMap = Utlity.numFormat4UnitMap(bfp.getMinInvestAmount());
		this.minInvestAmountNum = minInvestAmountMap.get("numStr");
		this.minInvestAmountCN = minInvestAmountMap.get("unitStr");
		this.minInvestAmountLess = Utlity.numFormat4UnitDetailLess(bfp.getMinInvestAmount());
		
		Map<String, String> minInvestAmountAddMap = Utlity.numFormat4UnitMap(bfp.getMinInvestAmountAdd());
		this.minInvestAmountAddNum = minInvestAmountAddMap.get("numStr");
		this.minInvestAmountAddCN = minInvestAmountAddMap.get("unitStr");
		this.minInvestAmountAddLess = Utlity.numFormat4UnitDetailLess(bfp.getMinInvestAmountAdd());
		
		Map<String, String> maxInvestAmountMap = Utlity.numFormat4UnitMap(bfp.getMaxInvestAmount());
		this.maxInvestAmountNum = maxInvestAmountMap.get("numStr");
		this.maxInvestAmountCN = maxInvestAmountMap.get("unitStr");
		this.maxInvestAmountLess = Utlity.numFormat4UnitDetailLess(bfp.getMaxInvestAmount());
		this.flagBuy = bfp.getFlagBuy();
		
		BigDecimal totalRaiseNum = bfp.getCollectAmount().subtract(bfp.getRealCollect());
		this.totalRaise = Utlity.numFormat4UnitDetailLess(totalRaiseNum);
		this.realReturnRate = bfp.getRealReturnRate() == null ? "" : bfp.getRealReturnRate().toString();
		this.realReturnRateCN = bfp.getRealReturnRate() == null ? "" : bfp.getRealReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
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
	
	public String getCollectStarttimeWeb() {
		return collectStarttimeWeb;
	}

	public void setCollectStarttimeWeb(String collectStarttimeWeb) {
		this.collectStarttimeWeb = collectStarttimeWeb;
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

	public String getCollectEndtimeWeb() {
		return collectEndtimeWeb;
	}

	public void setCollectEndtimeWeb(String collectEndtimeWeb) {
		this.collectEndtimeWeb = collectEndtimeWeb;
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

	public void setValueDateCN(String valueDateCN) {
		this.valueDateCN = valueDateCN;
	}

	public String getValueDateWeb() {
		return valueDateWeb;
	}

	public void setValueDateWeb(String valueDateWeb) {
		this.valueDateWeb = valueDateWeb;
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
	
	public String getMaturityDateWeb() {
		return maturityDateWeb;
	}

	public void setMaturityDateWeb(String maturityDateWeb) {
		this.maturityDateWeb = maturityDateWeb;
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

	public String getMinInvestAmountNum() {
		return minInvestAmountNum;
	}

	public void setMinInvestAmountNum(String minInvestAmountNum) {
		this.minInvestAmountNum = minInvestAmountNum;
	}

	public String getMinInvestAmountCN() {
		return minInvestAmountCN;
	}

	public void setMinInvestAmountCN(String minInvestAmountCN) {
		this.minInvestAmountCN = minInvestAmountCN;
	}

	public String getMinInvestAmountAddNum() {
		return minInvestAmountAddNum;
	}

	public void setMinInvestAmountAddNum(String minInvestAmountAddNum) {
		this.minInvestAmountAddNum = minInvestAmountAddNum;
	}

	public String getMinInvestAmountAddCN() {
		return minInvestAmountAddCN;
	}

	public void setMinInvestAmountAddCN(String minInvestAmountAddCN) {
		this.minInvestAmountAddCN = minInvestAmountAddCN;
	}
	
	public String getMaxInvestAmountNum() {
		return maxInvestAmountNum;
	}

	public void setMaxInvestAmountNum(String maxInvestAmountNum) {
		this.maxInvestAmountNum = maxInvestAmountNum;
	}

	public String getMaxInvestAmountCN() {
		return maxInvestAmountCN;
	}

	public void setMaxInvestAmountCN(String maxInvestAmountCN) {
		this.maxInvestAmountCN = maxInvestAmountCN;
	}
	
	public Boolean getFlagBuy() {
		return flagBuy;
	}
	
	public void setFlagBuy(Boolean flagBuy) {
		this.flagBuy = flagBuy;
	}
	
	public String getInvestScopeWeb() {
		return investScopeWeb;
	}

	public void setInvestScopeWeb(String investScopeWeb) {
		this.investScopeWeb = investScopeWeb;
	}

	public String getRevenueFeatureWeb() {
		return revenueFeatureWeb;
	}

	public void setRevenueFeatureWeb(String revenueFeatureWeb) {
		this.revenueFeatureWeb = revenueFeatureWeb;
	}

	public String getRemarkWeb() {
		return remarkWeb;
	}

	public void setRemarkWeb(String remarkWeb) {
		this.remarkWeb = remarkWeb;
	}


	public String getIconColorUrl() {
		return iconColorUrl;
	}
	

	public void setIconColorUrl(String iconColorUrl) {
		this.iconColorUrl = iconColorUrl;
	}
	

	public String getTotalRaise() {
		return totalRaise;
	}
	

	public void setTotalRaise(String totalRaise) {
		this.totalRaise = totalRaise;
	}

	public String getMinInvestAmountLess() {
		return minInvestAmountLess;
	}

	public void setMinInvestAmountLess(String minInvestAmountLess) {
		this.minInvestAmountLess = minInvestAmountLess;
	}

	public String getMinInvestAmountAddLess() {
		return minInvestAmountAddLess;
	}

	public void setMinInvestAmountAddLess(String minInvestAmountAddLess) {
		this.minInvestAmountAddLess = minInvestAmountAddLess;
	}

	public String getMaxInvestAmountLess() {
		return maxInvestAmountLess;
	}

	public void setMaxInvestAmountLess(String maxInvestAmountLess) {
		this.maxInvestAmountLess = maxInvestAmountLess;
	}

	public String getRealReturnRate() {
		return realReturnRate;
	}

	public void setRealReturnRate(String realReturnRate) {
		this.realReturnRate = realReturnRate;
	}

	public BigDecimal getCollectAmount() {
		return collectAmount;
	}

	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}

	public String getCollectAmountCN() {
		return collectAmountCN;
	}

	public void setCollectAmountCN(String collectAmountCN) {
		this.collectAmountCN = collectAmountCN;
	}

	public String getRealReturnRateCN() {
		return realReturnRateCN;
	}

	public void setRealReturnRateCN(String realReturnRateCN) {
		this.realReturnRateCN = realReturnRateCN;
	}
	
}
