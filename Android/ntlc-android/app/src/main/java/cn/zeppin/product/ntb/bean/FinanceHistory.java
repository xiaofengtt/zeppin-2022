package cn.zeppin.product.ntb.bean;

/**
 * 描述：我的持仓购买记录
 * 开发人: geng
 * 创建时间: 17/10/13
 */

public class FinanceHistory {


    /**
     * uuid : fe5c5602-d618-4452-8a56-aeb71e6d2325
     * investor : ec411fd6-9984-422e-975c-f37572e17cdd
     * price : 10000
     * priceCN : 10,000.00
     * createtime : 1513761797000
     * createtimeCN : 2017年12月20日 17:23
     * createtimeLessCN : 12月20日 17:23
     * coupon : c4af63f7-10dd-4368-aa4d-4f81295e2ce1
     * couponName : 500元现金券
     * couponType : cash
     * couponTypeCN : 现金券
     * couponPrice : 500
     * couponPriceCN : 500
     */

    private String uuid;
    private String investor;
    private double price;
    private String priceCN;
    private long createtime;
    private String createtimeCN;
    private String createtimeLessCN;
    private String coupon;
    private String couponName;
    private String couponType;
    private String couponTypeCN;
    private double couponPrice;
    private String couponPriceCN;

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

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponTypeCN() {
        return couponTypeCN;
    }

    public void setCouponTypeCN(String couponTypeCN) {
        this.couponTypeCN = couponTypeCN;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponPriceCN() {
        return couponPriceCN;
    }

    public void setCouponPriceCN(String couponPriceCN) {
        this.couponPriceCN = couponPriceCN;
    }
}
