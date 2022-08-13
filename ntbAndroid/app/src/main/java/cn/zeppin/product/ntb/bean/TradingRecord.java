package cn.zeppin.product.ntb.bean;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class TradingRecord {

    /**
     * uuid : fcf5e3c0-9fb4-45d1-a94d-9efe70f58fbb
     * investor : d84216b7-b856-4223-9a5b-7fb3dee80743
     * order : 0ca6ef05-eb5b-481a-86ed-4196ad425959
     * orderType : wechart
     * type : income
     * typeCN : 充值
     * accountBalance : 950000.71
     * accountBalanceCN : 950000.71
     * priceflag : true
     * price : 1000000.00
     * status : CLOSED
     * statusCN : 交易关闭
     * createtime : 1507861057000
     * createtimeCN : 2017年10月13日 10:17
     * createtimeLessCN : 10月13日 10:17
     * time : 2017年10月
     * remark : 账户充值
     */

    private String uuid;
    private String investor;
    private String order;
    private String orderType;
    private String type;
    private String typeCN;
    private double accountBalance;
    private String accountBalanceCN;
    private boolean priceflag;
    private String price;
    private String status;
    private String statusCN;
    private long createtime;
    private String createtimeCN;
    private String createtimeLessCN;
    private String time;
    private String remark;

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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountBalanceCN() {
        return accountBalanceCN;
    }

    public void setAccountBalanceCN(String accountBalanceCN) {
        this.accountBalanceCN = accountBalanceCN;
    }

    public boolean isPriceflag() {
        return priceflag;
    }

    public void setPriceflag(boolean priceflag) {
        this.priceflag = priceflag;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCN() {
        return statusCN;
    }

    public void setStatusCN(String statusCN) {
        this.statusCN = statusCN;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getCreatetimeCN() {
        return createtimeCN;
    }

    public void setCreatetimeCN(String createtimeCN) {
        this.createtimeCN = createtimeCN;
    }

    public String getCreatetimeLessCN() {
        return createtimeLessCN;
    }

    public void setCreatetimeLessCN(String createtimeLessCN) {
        this.createtimeLessCN = createtimeLessCN;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
