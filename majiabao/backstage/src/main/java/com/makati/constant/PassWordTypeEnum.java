package com.makati.constant;

public enum PassWordTypeEnum {
    login("login", "登录密码输错冻结"),
    withdraw("withdraw", "提现密码输错冻结");

    private String type;
    private String comment;

    PassWordTypeEnum(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    /**
     * 根据key获取value
     *
     * @param key
     *            : 键值key
     * @return String
     */
    public static String getValueByKey(String key) {
        PassWordTypeEnum[] enums = PassWordTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getType().equals(key)) {
                return enums[i].getComment();
            }
        }
        return "";
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
