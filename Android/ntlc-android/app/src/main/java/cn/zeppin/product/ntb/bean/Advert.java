package cn.zeppin.product.ntb.bean;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 18/1/24
 */

public class Advert {
    private String uuid;
    private String title;
    private String picture;
    private String pictureUrl;
    private String creator;
    private String creatorRealname;
    private long createtime;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorRealname() {
        return creatorRealname;
    }

    public void setCreatorRealname(String creatorRealname) {
        this.creatorRealname = creatorRealname;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
