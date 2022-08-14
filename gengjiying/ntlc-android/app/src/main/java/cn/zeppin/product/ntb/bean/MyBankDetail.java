package cn.zeppin.product.ntb.bean;

/**
 * 描述：我的银行卡详情
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class MyBankDetail {


    /**
     * color : #FF5D6E
     * singleLimit : 300000
     * dailyLimit : 500000
     * name : 渤海银行
     * icon : /upload/de11f226/44a7/4e57/9b77/c33806a7241d/c33806a7241d.png
     * bankcard : 8888
     * shortName : 渤海银行
     * uuid : 5b769336-1ccf-4e2f-9fe4-25cfe2cb4cb4
     */

    private String color;
    private String singleLimit;
    private String dailyLimit;
    private String name;
    private String icon;
    private String bankcard;
    private String shortName;
    private String uuid;
    private String phone;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(String singleLimit) {
        this.singleLimit = singleLimit;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
