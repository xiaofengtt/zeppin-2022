package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

/**
 * 描述：优惠券
 * 开发人: geng
 * 创建时间: 17/12/7
 */

public class Coupon implements Serializable{
    private boolean isChecked;
    private String uuid;
    private String coupon;
    private String couponName;
    private String couponType;
    private String couponTypeCN;
    private double couponPrice;
    private String couponPriceCN;
    private double minInvestAccount;
    private String minInvestAccountCN;
    private long expiryDate;
    private String expiryDateCN;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public double getMinInvestAccount() {
        return minInvestAccount;
    }

    public void setMinInvestAccount(double minInvestAccount) {
        this.minInvestAccount = minInvestAccount;
    }

    public String getMinInvestAccountCN() {
        return minInvestAccountCN;
    }

    public void setMinInvestAccountCN(String minInvestAccountCN) {
        this.minInvestAccountCN = minInvestAccountCN;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDateCN() {
        return expiryDateCN;
    }

    public void setExpiryDateCN(String expiryDateCN) {
        this.expiryDateCN = expiryDateCN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
