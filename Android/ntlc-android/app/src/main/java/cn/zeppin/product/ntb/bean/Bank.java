package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

/**
 * 描述：银行
 * 开发人: geng
 * 创建时间: 17/9/8
 */

public class Bank implements Serializable{

    /**
     * uuid : c7e69dd9-d6a2-4bc6-8700-a8ad604b9ace
     * name : 渤海银行
     * status : normal
     * singleLimit : 300000
     * dailyLimit : 500000
     * shortName : 渤海银行
     * iconColor : 3f660175-b2f0-4ab0-a35b-94bc710ce6f3
     * iconColorUrl : /upload/3f660175/b2f0/4ab0/a35b/94bc710ce6f3/94bc710ce6f3.png
     */

    private String uuid;
    private String name;
    private String status;
    private double singleLimit;
    private double dailyLimit;
    private String shortName;
    private String iconColor;
    private String iconColorUrl;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getIconColorUrl() {
        return iconColorUrl;
    }

    public void setIconColorUrl(String iconColorUrl) {
        this.iconColorUrl = iconColorUrl;
    }
}
