package com.makati.constant;

/**
 * 流水表状态码
 * @author eden
 * @create 2018-01-20 19:38
 **/
public enum MoneyFlowTypeEnum {
    TYPE_FLOW_01("01", "入款"),
    TYPE_FLOW_02("02", "出款"),
    TYPE_FLOW_04("04", "彩票"),
    TYPE_FLOW_05("05", "活动"),
    TYPE_FLOW_07("07", "返水");



    private String changeType;
    private String comment;

    MoneyFlowTypeEnum(String changeType, String comment) {
        this.changeType = changeType;
        this.comment = comment;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
