package com.woaishijiebei.www.bean;

import java.io.Serializable;

public class FrontUser implements Serializable {

    private String uuid;
    private String mobile;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
