package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

/**
 * 描述：我的银行卡
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class MyBank implements Serializable {

    /**
     * color : #37C39D
     * name : 浙商银行
     * icon : /upload/de11f226/44a7/4e57/9b77/c33806a7241d/c33806a7241d.png
     * iconColor : /upload/3f660175/b2f0/4ab0/a35b/94bc710ce6f3/94bc710ce6f3.png
     * bankcard : 8787
     * shortName : 浙商银行
     * uuid : db5c0704-9970-482d-8895-788d049ceae3
     */

    private String color;
    private String name;
    private String icon;
    private String iconColor;
    private String bankcard;
    private String shortName;
    private String uuid;
    private double singleLimit;
    private double dailyLimit;
    private String phone;
    //是否可用
    private boolean isAvailable = true;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(double singleLimit) {
        this.singleLimit = singleLimit;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
