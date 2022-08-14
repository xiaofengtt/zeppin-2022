package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 18/1/17
 */

public class UserGesture extends RealmObject implements Serializable {
    @PrimaryKey
    private String phone;
    private byte[] gesturePwd;
    private int errorTime;
    private boolean isOpen = false;

    public UserGesture() {
    }

    public UserGesture(String phone, byte[] gesturePwd, int errorTime, boolean isOpen) {
        this.phone = phone;
        this.gesturePwd = gesturePwd;
        this.errorTime = errorTime;
        this.isOpen = isOpen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getGesturePwd() {
        return gesturePwd;
    }

    public void setGesturePwd(byte[] gesturePwd) {
        this.gesturePwd = gesturePwd;
    }

    public int getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(int errorTime) {
        this.errorTime = errorTime;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
