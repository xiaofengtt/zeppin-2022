package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 18/1/23
 */

public class UserControl extends RealmObject implements Serializable {
    @PrimaryKey
    private String user;
    private boolean show;

    public UserControl() {
    }

    public UserControl(String user, boolean show) {
        this.user = user;
        this.show = show;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
