package cn.jiuzhousport.www.bean;

public class VersionInfo {
    private String type;
    private String bundleid;
    private String displayname;
    private String channel;
    private String name;
    private Integer code;
    private String adverturl;
    private String mainurl;
    private Boolean flagupdate;
    private Boolean flagcompel;
    private String downloadurl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBundleid() {
        return bundleid;
    }

    public void setBundleid(String bundleid) {
        this.bundleid = bundleid;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAdverturl() {
        return adverturl;
    }

    public void setAdverturl(String adverturl) {
        this.adverturl = adverturl;
    }

    public String getMainurl() {
        return mainurl;
    }

    public void setMainurl(String mainurl) {
        this.mainurl = mainurl;
    }

    public Boolean getFlagupdate() {
        return flagupdate;
    }

    public void setFlagupdate(Boolean flagupdate) {
        this.flagupdate = flagupdate;
    }

    public Boolean getFlagcompel() {
        return flagcompel;
    }

    public void setFlagcompel(Boolean flagcompel) {
        this.flagcompel = flagcompel;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "type='" + type + '\'' +
                ", bundleid='" + bundleid + '\'' +
                ", displayname='" + displayname + '\'' +
                ", channel='" + channel + '\'' +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", adverturl='" + adverturl + '\'' +
                ", mainurl='" + mainurl + '\'' +
                ", flagupdate=" + flagupdate +
                ", flagcompel=" + flagcompel +
                ", downloadurl='" + downloadurl + '\'' +
                '}';
    }
}
