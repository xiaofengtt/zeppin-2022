package cn.zeppin.product.ntb.bean;

/**
 * 描述：消息
 * 开发人: geng
 * 创建时间: 17/12/8
 */

public class Message {
    private String uuid;
    private String infoTitle;
    private String infoText;
    private long createtime;
    private boolean flagRead;
    private boolean isEllipsize = true;//是否将多余内容省略只显示1行

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public boolean isFlagRead() {
        return flagRead;
    }

    public void setFlagRead(boolean flagRead) {
        this.flagRead = flagRead;
    }

    public boolean isEllipsize() {
        return isEllipsize;
    }

    public void setEllipsize(boolean ellipsize) {
        isEllipsize = ellipsize;
    }
}
