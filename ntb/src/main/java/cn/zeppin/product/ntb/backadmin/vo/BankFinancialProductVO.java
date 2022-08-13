package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductTypes;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345581280584932467L;
	
	private String uuid;//id
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
	private String currencyType;//面向对象
	private String currencyTypeCN;
	private String custodian;
	private String custodianName;
	private String targetAnnualizedReturnRate;//目标年化收益率
	private BigDecimal totalAmount;//产品规模
	private String collectStarttime;//认购起始日
	private String collectEndtime;//认购截止日
	private Integer term;//产品期限
	private String recordDate;//登记日
	private String valueDate;//起息日
	private String maturityDate;//到期日
	private Boolean flagPurchase;//申购状态
	private Boolean flagRedemption;//赎回状态
	private Boolean flagFlexible;//灵活期限
	private String riskLevel;//风险等级
	private String netWorth;//当前净值
	private String netWorthTime;
	private String creator;//信息录入人
	private String createtime;//录入时间
	private Boolean	isPublish;
	private String guaranteeStatus;//保本保息状态
	private String guaranteeStatusCN;
	
	private String area;//发行地区
	private BigDecimal minInvestAmount;//最小投资金额
	private BigDecimal minInvestAmountAdd;//最小投资递增金额
	private BigDecimal maxInvestAmount;//最大投资金额
	
	private String riskLevelCN;
	
	
	public BankFinancialProductVO() {
		super();
	}
	
	public BankFinancialProductVO(BankFinancialProduct bfp) {
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
		this.custodian = bfp.getCustodian();
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
			this.collectStarttime = Utlity.timeSpanToDateString(bfp.getCollectStarttime());
		}else{
			this.collectStarttime = "";
		}
		if(bfp.getCollectEndtime() != null && !"".equals(bfp.getCollectEndtime())){
			this.collectEndtime = Utlity.timeSpanToDateString(bfp.getCollectEndtime());
		}else{
			this.collectEndtime = "";
		}
		this.term = bfp.getTerm();
		if(bfp.getRecordDate() != null && !"".equals(bfp.getRecordDate())){
			this.recordDate =  Utlity.timeSpanToDateString(bfp.getRecordDate());
		}else{
			this.recordDate =  "";
		}
		if(bfp.getValueDate() != null && !"".equals(bfp.getValueDate())){
			this.valueDate =  Utlity.timeSpanToDateString(bfp.getValueDate());
		}else{
			this.valueDate =  "";
		}
		if(bfp.getMaturityDate() != null && !"".equals(bfp.getMaturityDate())){
			this.maturityDate =  Utlity.timeSpanToDateString(bfp.getMaturityDate());
		}else{
			this.maturityDate =  "";
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
		this.currencyType = bfp.getCurrencyType();
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
		this.creator = bfp.getCreator();
		this.createtime =  Utlity.timeSpanToDateString(bfp.getCreatetime());
		this.minInvestAmount = bfp.getMinInvestAmount();
		this.minInvestAmountAdd = bfp.getMinInvestAmountAdd();
		this.maxInvestAmount = bfp.getMaxInvestAmount();
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
	
	public String getTypeCN() {
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
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
	
	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	
	public String getCurrencyTypeCN() {
		return currencyTypeCN;
	}

	public void setCurrencyTypeCN(String currencyTypeCN) {
		this.currencyTypeCN = currencyTypeCN;
	}
	
	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}
	
	public String getCustodianName() {
		return custodianName;
	}

	public void setCustodianName(String custodianName) {
		this.custodianName = custodianName;
	}
	
	public String getTargetAnnualizedReturnRate() {
		return targetAnnualizedReturnRate;
	}

	public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
		this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCollectStarttime() {
		return collectStarttime;
	}

	public void setCollectStarttime(String collectStarttime) {
		this.collectStarttime = collectStarttime;
	}

	public String getCollectEndtime() {
		return collectEndtime;
	}

	public void setCollectEndtime(String collectEndtime) {
		this.collectEndtime = collectEndtime;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
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
	
	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public String getNetWorthTime() {
		return netWorthTime;
	}

	public void setNetWorthTime(String netWorthTime) {
		this.netWorthTime = netWorthTime;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(BigDecimal minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}
	
	public BigDecimal getMaxInvestAmount() {
		return maxInvestAmount;
	}

	public void setMaxInvestAmount(BigDecimal maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}
	
	public BigDecimal getMinInvestAmountAdd() {
		return minInvestAmountAdd;
	}

	public void setMinInvestAmountAdd(BigDecimal minInvestAmountAdd) {
		this.minInvestAmountAdd = minInvestAmountAdd;
	}
	
	public String getRiskLevelCN() {
		return riskLevelCN;
	}

	public void setRiskLevelCN(String riskLevelCN) {
		this.riskLevelCN = riskLevelCN;
	}
	
	public String getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(String guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}
	
	public String getGuaranteeStatusCN() {
		return guaranteeStatusCN;
	}

	public void setGuaranteeStatusCN(String guaranteeStatusCN) {
		this.guaranteeStatusCN = guaranteeStatusCN;
	}
}
