package cn.zeppin.product.ntb.bean;

import java.util.List;

/**
 * 描述：我的持仓详情
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class FinanceDetail {

    /**
     * uuid : 2c206332-f3f6-4a17-8714-9c4fc5b4d4ff
     * investor : d84216b7-b856-4223-9a5b-7fb3dee80743
     * product : ad1522f3-a7b9-4443-b6aa-8e961a4534ec
     * productName : 永乐2号BC1032
     * bankName : 浙商银行
     * productScode : BC1032
     * incomeDate : 2017-09-28
     * valueDate : 2017-07-20
     * maturityDate : 2018-01-16
     * targetAnnualizedReturnRate : 5.21
     * term : 180
     * flagBuy : false
     * price : 50000
     * priceCN : 50,000
     * totalReturn : 0.00
     * paytime : 1506481706000
     * paytimeCN : 2017-09-27 11:08:26
     * bill : 7646bb76-fa1b-46e6-ad94-d8709fb47935
     * orderNum : 142170927110826280
     * orderType : balance
     * orderTypeCN : 余额支付
     * status : SUCCESS
     * stage : confirming
     * stageCN : 确认中
     * agreementName : 《定向委托投资管理协议142170927110826280142170927110826280》
     * agreementUrl : /agreement/b9c070df/b57f/4fb8/a701/59f90583001c/定向委托投资管理协议142170927110826280.pdf
     */

    private String uuid;
    private String investor;
    private String product;
    private String productName;
    private String bankName;
    private String productScode;
    private String incomeDate;
    private String valueDate;
    private String maturityDate;
    private String targetAnnualizedReturnRate;
    private String term;
    private boolean flagBuy;
    private double price;
    private String priceCN;
    private String totalReturn;//实际收益
    private long paytime;
    private String paytimeCN;
    private String bill;
    private String orderNum;
    private String orderType;
    private String orderTypeCN;
    private String status;
    private String stage;
    private String stageCN;
    private String agreementName;
    private String agreementUrl;
    private double realReturnRate;//实际收益率
    private List<FinanceHistory> accountHistory;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getProductScode() {
        return productScode;
    }

    public void setProductScode(String productScode) {
        this.productScode = productScode;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
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

    public String getTargetAnnualizedReturnRate() {
        return targetAnnualizedReturnRate;
    }

    public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
        this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public boolean isFlagBuy() {
        return flagBuy;
    }

    public void setFlagBuy(boolean flagBuy) {
        this.flagBuy = flagBuy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceCN() {
        return priceCN;
    }

    public void setPriceCN(String priceCN) {
        this.priceCN = priceCN;
    }

    public String getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(String totalReturn) {
        this.totalReturn = totalReturn;
    }

    public long getPaytime() {
        return paytime;
    }

    public void setPaytime(long paytime) {
        this.paytime = paytime;
    }

    public String getPaytimeCN() {
        return paytimeCN;
    }

    public void setPaytimeCN(String paytimeCN) {
        this.paytimeCN = paytimeCN;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeCN() {
        return orderTypeCN;
    }

    public void setOrderTypeCN(String orderTypeCN) {
        this.orderTypeCN = orderTypeCN;
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

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    public double getRealReturnRate() {
        return realReturnRate;
    }

    public void setRealReturnRate(double realReturnRate) {
        this.realReturnRate = realReturnRate;
    }

    public List<FinanceHistory> getAccountHistory() {
        return accountHistory;
    }

    public void setAccountHistory(List<FinanceHistory> accountHistory) {
        this.accountHistory = accountHistory;
    }
}
