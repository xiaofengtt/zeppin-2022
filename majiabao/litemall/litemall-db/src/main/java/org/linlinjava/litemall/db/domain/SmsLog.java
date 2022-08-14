package org.linlinjava.litemall.db.domain;


import java.util.Date;


public class SmsLog {
    private String phone;
    private String ip;
    private Date createTime;


    public SmsLog() {
    }

    public SmsLog(String phone, String ip, Date createTime) {
        this.phone = phone;
        this.ip = ip;
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
