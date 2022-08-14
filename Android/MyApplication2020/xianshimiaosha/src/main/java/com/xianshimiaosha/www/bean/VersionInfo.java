package com.xianshimiaosha.www.bean;

public class VersionInfo {
    private String appid;
    private String version;
    private int onoff;
    private String downurl;
    private String mjurl;
    private String zburl;
    private String url;
    private String qd;
    private int qzonoff;
    private int state;
    private String text;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getMjurl() {
        return mjurl;
    }

    public void setMjurl(String mjurl) {
        this.mjurl = mjurl;
    }

    public String getZburl() {
        return zburl;
    }

    public void setZburl(String zburl) {
        this.zburl = zburl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQd() {
        return qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public int getQzonoff() {
        return qzonoff;
    }

    public void setQzonoff(int qzonoff) {
        this.qzonoff = qzonoff;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "appid='" + appid + '\'' +
                ", version='" + version + '\'' +
                ", onoff=" + onoff +
                ", downurl='" + downurl + '\'' +
                ", mjurl='" + mjurl + '\'' +
                ", zburl='" + zburl + '\'' +
                ", url='" + url + '\'' +
                ", qd='" + qd + '\'' +
                ", qzonoff=" + qzonoff +
                ", state=" + state +
                ", text='" + text + '\'' +
                '}';
    }
}
