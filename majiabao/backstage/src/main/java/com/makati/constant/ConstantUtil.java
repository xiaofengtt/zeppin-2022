package com.makati.constant;

public class ConstantUtil {
    /**
     * 用户登录密码md5加密拼接字符串
     */

    public static final String LOGIN_PWD_CODE = "xlfd";

    /**
     * 用户存放在缓存里的前缀
     */
    public static final String JEDIS_USER_PREFIX="user_";
    /**
     * 用户存放在缓存里的前缀
     */
    public static final String JEDIS_USER_PREFIX_GAME="game_user:";

    //===================================
    /**
     * 金钱转换，统一以  1000为单位   即：1元=1*1000
     */
    public static final int MONEY_TRANSFER=1000;

    /**
     * 流水表，彩票标识
     */
    public static final String CAI_PIAO_FLOW_FLAG="01";

    /**
     * 验证码，统一前缀标识
     */
    public static final String CODE_ID="codeId_";
}
