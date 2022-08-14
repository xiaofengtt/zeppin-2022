package cn.zeppin.product.ntb.bean;

/**
 * 描述：图片资源
 * 开发人: geng
 * 创建时间: 17/10/10
 */

public class ImgResource {

    /**
     * uuid : 23ac28fe-3800-410d-a81e-25b198047dbc
     * type : 2
     * filename : 25b198047dbc
     * url : /upload/23ac28fe/3800/410d/a81e/25b198047dbc/25b198047dbc.jpg
     * filetype : jpg
     * size : 92551
     * status : normal
     */

    private String uuid;
    private String type;
    private String filename;
    private String url;
    private String filetype;
    private int size;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
