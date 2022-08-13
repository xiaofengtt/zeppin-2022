package cn.zeppin.product.ntb.bean;

/**
 * 描述：交易详情
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class TradingRecordDetail {

    /**
     * uuid : 6919c755-0fc8-497c-87ac-c8b8dd312d1c
     * investor : db1dbafa-fe92-4965-8101-50c2b99b0ceb
     * order : db1dbafa-fe92-4965-8101-50c2b99b0ceb
     * orderNum : 212171104142852616
     * orderType : balance
     * orderTypeCN : 平台余额支付
     * type : buy
     * typeCN : 购买
     * accountBalance : 38
     * accountBalanceCN : 38.00
     * priceflag : false
     * price : 1.00
     * status : SUCCESS
     * statusCN : 交易成功
     * createtime : 1509776932000
     * createtimeCN : 2017年11月04日 14:28
     * createtimeLessCN : 11月04日 14:28
     * time : 2017年11月
     * remark : 购买【中信银行】慧赢成长
     * product : fa537daa-3e0c-41d3-9a6c-01b7b03e61f9
     * productType : bank_product
     */

    private String uuid;
    private String investor;
    private String order;
    private String orderNum;
    private String orderType;
    private String orderTypeCN;
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
    private String product;
    private String productType;

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
