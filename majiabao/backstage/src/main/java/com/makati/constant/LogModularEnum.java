package com.makati.constant;

public enum LogModularEnum {
    HUODONG("领取活动奖励"),
    CHARGE("充值申请"),
    WITHDRAW("申请取款"),
    REGISTER("用户注册"),
    DONGJIE("用户冻结"),
    DSB_GUANZHU("大神榜关注"),
    GENDAN("跟单"),
    CP_CHEDAN("彩票撤单"),
    HD_PHONE("手机验证码"),
    DATA_EDIT("会员信息修改");
    String name;

    LogModularEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
