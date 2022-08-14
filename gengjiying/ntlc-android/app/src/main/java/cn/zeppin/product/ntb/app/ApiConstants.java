package cn.zeppin.product.ntb.app;

import com.geng.library.commonutils.LogUtils;
import com.geng.library.commonutils.TimeUtil;

import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.MD5Util;

/**
 * Created by geng on 17/9/7.
 * class:
 */

public class ApiConstants {
                    public static final String HOST = "https://api.niutoulicai.com/";
//    public static final String HOST = "http://192.168.1.120:8080/NTB/rest/";
//    public static final String HOST = "http://192.168.1.101:8080/NTB/rest/";

                    public static final String RESOURCE_HOST = "https://backadmin.niutoulicai.com";
//    public static final String RESOURCE_HOST = "http://192.168.1.120:8080/NTB";
//                public static final String RESOURCE_HOST = "http://192.168.1.101:8080/NTB";
    public static final String PDF_HOST = RESOURCE_HOST + "/pdf/viewer.html?file=PDFFILE&name=PDFNAME";

    public static final String VERSION_INFO = HOST + "web/login/getVersionInfo";

    public static final String ADVERT = RESOURCE_HOST + "/rest/web/advert/get";

    public static final String PDFFILE = "PDFFILE";
    public static final String PDFNAME = "PDFNAME";

    //来源编码
    public static final String ANDROID = "02";

    public static final String KEY = "27739700ee0bf2930cd62d72a80def0a";

    public static final int PAGESIZE = 20;

    //公司默认的支付宝收款码
    public static final String ZEPPIN_ALIPAY_DEFAULT_CODE = "FKX08879LCON2PIIGLMUE2";

    /**
     * 产品购买协议
     */
    public static final String PRODUCT_BUY_AGREEMENT = "../resource/123.pdf";
    public static final String PRODUCT_BUY_AGREEMENT_NAME = "牛投理财定向委托投资管理协议";

    /**
     * 用户登录注册协议
     */
    public static final String USER_AGREEMENT = "../resource/456.pdf";
    public static final String USER_AGREEMENT_NAME = "牛投理财用户服务协议";

    /**
     * 持仓阶段
     */
    public class Finance {
        public final static String CONFIRMING = "confirming";//确认中（起息日）
        public final static String PROFIT = "profit";//收益中（持有中）
        public final static String BALANCE = "balance";//结算还款中（预计到账日）
        public final static String FINISHED = "finished";//已完成
        public final static String TRANSACTION = "transaction";//交易中
    }

    /**
     * 产品理财周期
     */
    public class ProductStage {
        public final static String UNSTART = "unstart";//未开始
        public final static String COLLECT = "collect";//募集中
        public final static String PROFIT = "profit";//投资中
        public final static String BALANCE = "balance";//结算中
        public final static String FINISHED = "finished";//已完成
        public final static String UNINVEST = "uninvest";//投资中(募集满)
        public final static String INVESTED = "invested";//投资完成
    }

    public class BankPayType {
        public final static String SEND = "send";
        public final static String CHECK = "check";
    }

    /**
     * 可用的支付方式
     */
    public class PayType {
        public final static String BALANCE = "balance";
        public final static String WECHART = "wechart";
        public final static String ALIPAY = "alipay";
        public final static String CHANPAY = "chanpay";
        public final static String FUQIANLA = "fuqianla";
    }

    public class TradingStatus {
        public final static String TRANSACTING = "TRANSACTING";//交易中
        public final static String FAIL = "FAIL";//交易失败
        public final static String CLOSED = "CLOSED";//交易关闭
        public final static String CANCELLED = "CANCELLED";//交易取消
        public final static String SUCCESS = "SUCCESS";//交易成功
    }

    /**
     * 实名认证审核结果
     */
    public class IdcardImgStatus {
        public final static String NOTUPLOAD = "notupload";//未上传
        public final static String UNCHECKED = "unchecked";//未审核
        public final static String CHECKED = "checked";//审核通过
        public final static String UNPASSED = "unpassed";//不通过
    }

    private class TokenType {
        public final static String TOKEN = "token";//公共
        public final static String REGISTER_TOKEN = "register_token";//注册
        public final static String LOGIN_TOKEN = "login_token";//手机号密码登录
        public final static String LOGIN_BYCODE_TOKEN = "LOGIN_BYCODE_TOKEN";//手机号验证码登录
        public final static String RESET_PWD_TOKEN = "reset_pwd_token";//重置密码
    }

    public class SendCodeType {
        public final static String RESETPWD = "resetpwd";//重置密码
        public final static String REGISTER = "register";//注册发送验证码
        public final static String CODE = "code";//登录发送验证码
    }

    /**
     * 优惠券
     */
    public class CouponStatus {
        public final static String UNUSE = "unuse";//未使用
        public final static String USED = "used";//已使用
        public final static String EXPIRED = "expired";//已过期
        public final static String HISTORY = "history";//已过期+已使用
        public final static String USE = "use";//可用券
    }

    public class CouponType {
        public final static String INTERESTS = "interests";//加息券
        public final static String CASH = "cash";//现金券

    }

    /**
     * 公共的token
     *
     * @param uuid
     * @return
     */
    public static String getToken(final String uuid, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + uuid + MD5Util.getMD5(KEY + timestamp + uuid));
    }

    /**
     * 注册token Base64(来源编码+timestamp+pwd+MD5(key+timestamp+phone+code))
     *
     * @param phone
     * @param code
     * @param pwd
     * @return
     */
    public static String getRegisterToken(String phone, String code, String pwd, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + MD5Util.getMD5(pwd) + MD5Util.getMD5(KEY + timestamp + phone + code));
    }

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     * @return
     */
    public static String getLoginToken(String phone, String pwd, String timestamp) {
        LogUtils.logd(TimeUtil.timeFormat(timestamp + ""));
        return EncryptUtil.getBase64(ANDROID + timestamp + phone + MD5Util.getMD5(KEY + timestamp + MD5Util.getMD5(pwd)));
    }

    /**
     * 短信验证码登录
     *
     * @param phone
     * @param code
     * @return
     */
    public static String getLoginBycodeToken(String phone, String code, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + phone + MD5Util.getMD5(KEY + timestamp + code));
    }

    public static String getResetCheckToken(String phone, String code, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + MD5Util.getMD5(KEY + timestamp + phone + code));
    }

    public static String getResetPwdToken(String pwd, String phone, String code, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + MD5Util.getMD5(pwd) + MD5Util.getMD5(KEY + timestamp + phone + code));
    }

    /**
     * 版本更新token
     *
     * @param version   当前版本
     * @param timestamp 时间戳
     * @return
     */
    public static String getVersionInfoToken(int version, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + MD5Util.getMD5(KEY + timestamp + version));
    }

    /**
     * 获取应用市场版本开关状态token
     *
     * @param version
     * @param webmarket
     * @param timestamp
     * @return
     */
    public static String getWebmarketSwitchToken(int version, String webmarket, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + MD5Util.getMD5(KEY + timestamp + webmarket + version));
    }

    /**
     * 设置密码：规则Base64(timestamps+md5(pwd)+MD5(key+time+md5(pwd)))
     *
     * @return
     */
    public static String setUserPwdToken(String pwd, String timestamp) {
        return EncryptUtil.getBase64(timestamp + MD5Util.getMD5(pwd) + MD5Util.getMD5(KEY + timestamp + MD5Util.getMD5(pwd)));
    }

    /**
     * 修改密码
     *
     * @param oldPwd    原密码
     * @param newPwd    新密码
     * @param timestamp
     * @return
     */
    public static String editUserPwdToken(String newPwd, String oldPwd, String timestamp) {
        return EncryptUtil.getBase64(timestamp + MD5Util.getMD5(newPwd) + MD5Util.getMD5(KEY + timestamp + MD5Util.getMD5(newPwd) + MD5Util.getMD5(oldPwd)));
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param codeType
     * @param timestamp
     * @return
     */
    public static String sendCodeToken(String phone, String codeType, String timestamp) {
        return EncryptUtil.getBase64(ANDROID + timestamp + MD5Util.getMD5(KEY + timestamp + phone + codeType));
    }
}
