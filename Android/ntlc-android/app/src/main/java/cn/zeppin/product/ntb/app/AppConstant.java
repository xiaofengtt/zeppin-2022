package cn.zeppin.product.ntb.app;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/9/7
 */

public class AppConstant {
    public static final String NT_USER = "nt_user";

    //银行理财
    //年化收益
    public static final String BANK_ANNUALIZED_TYPE = "target_annualized_return_rate";
    //产品期限
    public static final String BANK_TERM_TYPE = "term";
    //起购金额
    public static final String BANK_AMOUNT_TYPE = "min_invest_amount";

    public static final String PRODUCT_UUID = "product_uuid";

    public static final String UUID = "uuid";
    public static final String PHONE = "phone";

    //产品详情
    public static final String INTENT_PRODUCT_DETAIL = "product_detail";
    //支付金额
    public static final String INTENT_PAY_AMOUNT = "pay_amount";

    public static final String INTENT_PHONE = "intent_phone";
    public static final String INTENT_CODE = "intent_code";
    public static final String INTENT_USER = "intent_user";
    //银行
    public static final String INTENT_BANK = "intent_bank";
    //持卡人
    public static final String INTENT_CARDHOLDER = "intent_cardholder";
    //银行卡号
    public static final String INTENT_BANKCARD = "intent_bankcard";
    //持仓id
    public static final String INTENT_FINANCE_UUID = "intent_finance_uuid";
    public static final String INTENT_URL = "intent_url";
    public static final String INTENT_TITLE = "intent_title";
    public static final String INTENT_PDF_URL = "intent_pdf_url";
    public static final String INTENT_PDF_TITLE = "intent_pdf_title";
    //充值金额
    public static final String INTENT_RECHARGE_PRICE = "intent_recharge_price";
    //提现
    public static final String INTENT_WITHDRAWAL_PRICE = "intent_withdrawal_price";
    //交易记录id
    public static final String INTENT_BILL_ID = "intent_bill_id";
    //绑卡界面自动关闭
    public static final String INTENT_AUTO_CLOSE = "intent_auto_close";
    //产品买入金额
    public static final String INTENT_BUY_PRICE = "intent_buy_price";
    //是否设置过密码
    public static final String INTENT_SETPWD = "intent_setpwd";
    //大礼包
    public static final String INTENT_GIFT = "intent_gift";

    //用户信息刷新
    public static final String REFRESH_USER = "refresh_user";
    public static final String REFRESH_USERFRAGMENT = "refresh_userfragment";
    public static final String REFRESH_FINANCEFRAGMENT = "refresh_financefragment";
    public static final String USER_ALIAUTH_SUCCESS = "user_aliauth_success";
    //退出登录
    public static final String LOGIN_OUT = "login_out";

    public static final String TAB_INDEX = "tab_index";
    //跳转到"银行理财"
    public static final String TO_BANK = "to_bank";
    public static final String TO_MAIN = "to_main";
    //跳转到"我的持仓"
    public static final String TO_FINANCE = "to_finance";
    //刷新"我的持仓"--"持有中"
    public static final String REFRESH_FINANCE_HOLD = "refresh_finance_hold";
    //刷新"我的持仓"--"交易中"
    public static final String REFRESH_FINANCE_TRANSACTION = "refresh_finance_transaction";
    //刷新"我的持仓"--"已完成"
    public static final String REFRESH_FINANCE_FINISH = "refresh_finance_finish";
    //跳转到"我的持仓"--"交易中"
    public static final String TO_FINANCE_TRANSACTION = "to_finance_transaction";
    public static final String REFRESH_BANK = "refresh_bank";
    //刷新银行卡列表
    public static final String REFRESH_MY_BANK = "refresh_my_bank";
    //实名认证成功
    public static final String CERTIFICATION_PAY_SUCCESS = "certification_pay_success";
    //产品购买成功
    public static final String PRODUCT_BUY_SUCCESS = "product_buy_success";

    //更新传值
    public static final String UPDATE_VERSION_INFO = "update_version_info";
    //发送短信dialog  value
    public static final String SEND_PHONE = "send_phone";
    //当前选择的优惠券
    public static final String SELECTED_COUPON = "selected_coupon";
    //优惠券可用数
    public static final String COUPON_USE_COUNT = "coupon_use_count";

    public static class RequestCode {
        public static final int REQUEST_CODE_RESET = 1000;
        public static final int REQUEST_CODE_BANK = 1001;
        public static final int REQUEST_CODE_UNBINDBANK = 1002;
        public static final int REQUEST_CODE_LOGIN = 1003;
        public static final int REQUEST_CODE_PHONE = 1004;
        public static final int REQUEST_CODE_BANKLIST = 1005;
        //优惠券
        public static final int REQUEST_CODE_COUPON = 1006;
        public static final int REQUEST_CODE_GESTURE_LOGIN = 1007;
        public static final int REQUEST_CODE_GESTURE_RESET = 1008;
        public static final int REQUEST_CODE_GESTURE_SET = 1009;
    }

    public static class FragmentIndex {
        public static final int BANK = 0;
        public static final int FINANCE = 1;
        public static final int USER = 2;
    }

    /**
     * 本地存储的key的名称
     */
    public static class SpfKey {
        //购买选择银行
        public static final String PRODUCT_BUY_BANK = "product_buy_bank";
        //充值选择银行
        public static final String RECHARGE_BANK = "RECHARGE_BANK";
        //提现选择银行
        public static final String WITHDRAWAL_BANK = "withdrawal_bank";
    }

    public static final String GESTURE_SKIP_HIDE = "gesture_skip";
    public static final String GESTURE_CHECK = "gesture_check";

    public static final String ADVERT_URL = "advert_url";

}
