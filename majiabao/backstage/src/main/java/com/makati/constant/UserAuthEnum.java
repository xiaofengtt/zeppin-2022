package com.makati.constant;

public enum UserAuthEnum {
    ALLOW_LOGIN(1, "允许登录"),
    ALLOW_CHARGE(2, "允许充值"),
    ALLOW_WITHDRAW(3, "允许取款"),
    NOT_IN_BLACKLIST(4, "不在黑名单");

    private Integer code;
    private String comment;

    UserAuthEnum(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
