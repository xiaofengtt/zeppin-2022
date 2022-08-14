package cn.zeppin.product.ntb.bean;

/**
 * Created by geng on 17/9/6.
 * class: 银行理财列表数据
 */

public class Product {

    /**
     * uuid : 3d8290eb-feaa-4780-8b8f-e4ec718c55ff
     * bankFinancialProduct : 2e5436d7-b7a7-4948-85cd-9d7cfdb52f2a
     * name : 产品名称a
     * scode : 产品编号a
     * url :
     * type : income
     * typeCN : 固定收益
     * status : checked
     * stage : finished
     * stageCN : 已完成
     * target : individual
     * targetCN : 个人
     * custodian : 5d48b883-ef5f-4021-9dcc-bea5db21a42a
     * custodianName : 浙商银行
     * currencyType : rmb
     * currencyTypeCN : 人民币
     * targetAnnualizedReturnRate : 0.00
     * totalAmount : 0
     * collectStarttime : 2017-08-01
     * collectEndtime : 2017-08-22
     * collectEndtimeCN : 2017.08.22
     * term : 16
     * recordDate : 2017-08-02
     * valueDate : 2017-08-08
     * maturityDate : 2017-08-24
     * flagPurchase : false
     * flagRedemption : false
     * flagFlexible : false
     * riskLevel : R1
     * netWorth : null
     * netWorthTime : null
     * guaranteeStatus : 1
     * guaranteeStatusCN : 不保本
     * area : 全国
     * minInvestAmount : 0
     * minInvestAmountCN : 0
     * minInvestAmountAdd : 0
     * maxInvestAmount : 0
     * riskLevelCN : R1(谨慎型)
     * flagBuy : false
     * shortname :
     */

    private String uuid;
    private String bankFinancialProduct;
    private String name;//产品名称
    private String scode;//产品编码
    private String url;//产品路径
    private String type;//收益类型
    private String typeCN;
    private String status;//状态
    private String stage;//投资阶段
    private String stageCN;
    private String target;
    private String targetCN;
    private String custodian;//管理银行
    private String custodianName;
    private String currencyType;
    private String currencyTypeCN;
    private String targetAnnualizedReturnRate;//目标收益率
    private String totalAmount;//募集金额 单位 亿
    private String collectStarttime;//募集起始时间
    private String collectEndtime;//募集结束时间
    private String collectEndtimeCN;
    private String term;//产品期限
    private String recordDate;//登记日
    private String valueDate;//起息日
    private String maturityDate;//到期日
    private boolean flagPurchase;//申购状态
    private boolean flagRedemption;//赎回状态
    private boolean flagFlexible;//灵活期限
    private String riskLevel;//风险等级
    private Object netWorth;
    private Object netWorthTime;
    private String guaranteeStatus;//保本保息状态
    private String guaranteeStatusCN;
    private String area;//地区
    private String minInvestAmount;//最小投资金额
    private String minInvestAmountCN;//最小投资金额
    private String minInvestAmountAdd;//最小投资递增
    private String maxInvestAmount;
    private String riskLevelCN;
    private boolean flagBuy;//是否可购买
    private String shortname;//产品简称
    //带逗号
    private String minInvestAmountLess;//最小投资金额
    private String minInvestAmountAddLess;//最小投资递增
    private String maxInvestAmountLess;//最大投资金额
    private String iconColorUrl;//银行icon

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTargetAnnualizedReturnRate() {
        return targetAnnualizedReturnRate;
    }

    public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
        this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
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

    public String getCollectEndtimeCN() {
        return collectEndtimeCN;
    }

    public void setCollectEndtimeCN(String collectEndtimeCN) {
        this.collectEndtimeCN = collectEndtimeCN;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
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

    public boolean isFlagPurchase() {
        return flagPurchase;
    }

    public void setFlagPurchase(boolean flagPurchase) {
        this.flagPurchase = flagPurchase;
    }

    public boolean isFlagRedemption() {
        return flagRedemption;
    }

    public void setFlagRedemption(boolean flagRedemption) {
        this.flagRedemption = flagRedemption;
    }

    public boolean isFlagFlexible() {
        return flagFlexible;
    }

    public void setFlagFlexible(boolean flagFlexible) {
        this.flagFlexible = flagFlexible;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Object getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(Object netWorth) {
        this.netWorth = netWorth;
    }

    public Object getNetWorthTime() {
        return netWorthTime;
    }

    public void setNetWorthTime(Object netWorthTime) {
        this.netWorthTime = netWorthTime;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(String minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public String getMinInvestAmountCN() {
        return minInvestAmountCN;
    }

    public void setMinInvestAmountCN(String minInvestAmountCN) {
        this.minInvestAmountCN = minInvestAmountCN;
    }

    public String getMinInvestAmountAdd() {
        return minInvestAmountAdd;
    }

    public void setMinInvestAmountAdd(String minInvestAmountAdd) {
        this.minInvestAmountAdd = minInvestAmountAdd;
    }

    public String getMaxInvestAmount() {
        return maxInvestAmount;
    }

    public void setMaxInvestAmount(String maxInvestAmount) {
        this.maxInvestAmount = maxInvestAmount;
    }

    public String getRiskLevelCN() {
        return riskLevelCN;
    }

    public void setRiskLevelCN(String riskLevelCN) {
        this.riskLevelCN = riskLevelCN;
    }

    public boolean isFlagBuy() {
        return flagBuy;
    }

    public void setFlagBuy(boolean flagBuy) {
        this.flagBuy = flagBuy;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
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

    public String getIconColorUrl() {
        return iconColorUrl;
    }

    public void setIconColorUrl(String iconColorUrl) {
        this.iconColorUrl = iconColorUrl;
    }
}
