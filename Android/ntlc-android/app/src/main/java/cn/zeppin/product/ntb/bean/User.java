package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

/**
 * 描述：用户
 * 开发人: geng
 * 创建时间: 17/9/25
 */

public class User implements Serializable {
    /**
     * nickname : 游客：13716640748
     * realname : 耿**
     * idcard : 1****************4
     * phone : 137****0748
     * realnameAuthFlag : true
     * idcardImgStatus : unpassed
     * idcardImgStatusCN : 未通过
     * bankcardCount : 1
     * accountBalance : 0.00
     * publicName : 耿女士
     */

    private String nickname;
    private String realname;
    private String realnameFull;
    private String idcard;
    private String phone;
    private boolean realnameAuthFlag;
    private String idcardImgStatus;
    private String idcardImgStatusCN;
    private int bankcardCount;
    private String accountBalance;
    private String publicName;
    private String uuid;//用户id
    private String aliPhoto;//支付宝用户头像
    private String aliUserid;//阿里用户id
    private String aliNickname;//阿里用户昵称
    private boolean bindingAliFlag;//是否已绑定支付宝
    private boolean pwdFlag;//是否设置过密码
    private int couponCount;//优惠券总数
    private boolean messageFlag;//是否有新消息

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealnameFull() {
        return realnameFull;
    }

    public void setRealnameFull(String realnameFull) {
        this.realnameFull = realnameFull;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRealnameAuthFlag() {
        return realnameAuthFlag;
    }

    public void setRealnameAuthFlag(boolean realnameAuthFlag) {
        this.realnameAuthFlag = realnameAuthFlag;
    }

    public String getIdcardImgStatus() {
        return idcardImgStatus;
    }

    public void setIdcardImgStatus(String idcardImgStatus) {
        this.idcardImgStatus = idcardImgStatus;
    }

    public String getIdcardImgStatusCN() {
        return idcardImgStatusCN;
    }

    public void setIdcardImgStatusCN(String idcardImgStatusCN) {
        this.idcardImgStatusCN = idcardImgStatusCN;
    }

    public int getBankcardCount() {
        return bankcardCount;
    }

    public void setBankcardCount(int bankcardCount) {
        this.bankcardCount = bankcardCount;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getAliUserid() {
        return aliUserid;
    }

    public void setAliUserid(String aliUserid) {
        this.aliUserid = aliUserid;
    }

    public String getAliNickname() {
        return aliNickname;
    }

    public void setAliNickname(String aliNickname) {
        this.aliNickname = aliNickname;
    }

    public boolean isBindingAliFlag() {
        return bindingAliFlag;
    }

    public void setBindingAliFlag(boolean bindingAliFlag) {
        this.bindingAliFlag = bindingAliFlag;
    }

    public String getAliPhoto() {
        return aliPhoto;
    }

    public void setAliPhoto(String aliPhoto) {
        this.aliPhoto = aliPhoto;
    }

    public boolean isPwdFlag() {
        return pwdFlag;
    }

    public void setPwdFlag(boolean pwdFlag) {
        this.pwdFlag = pwdFlag;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public boolean isMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(boolean messageFlag) {
        this.messageFlag = messageFlag;
    }
}
