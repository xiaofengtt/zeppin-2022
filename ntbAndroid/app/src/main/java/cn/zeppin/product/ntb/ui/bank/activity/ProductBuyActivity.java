package cn.zeppin.product.ntb.ui.bank.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Coupon;
import cn.zeppin.product.ntb.bean.ProductDetail;
import cn.zeppin.product.ntb.bean.ResultData;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.bank.contract.ProductPayContract;
import cn.zeppin.product.ntb.ui.bank.model.ProductPayModel;
import cn.zeppin.product.ntb.ui.bank.presenter.ProductPayPresenter;
import cn.zeppin.product.ntb.ui.finance.activity.Recharge2Activity;
import cn.zeppin.product.ntb.ui.main.activity.WebActivity;
import cn.zeppin.product.ntb.ui.user.activity.CouponActivity;
import cn.zeppin.product.ntb.ui.user.model.CouponModel;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.PriceInputFilter;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.TextViewBorder;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;
import rx.functions.Action1;

import static cn.zeppin.product.ntb.app.ApiConstants.PDFFILE;
import static cn.zeppin.product.ntb.app.ApiConstants.PRODUCT_BUY_AGREEMENT;
import static cn.zeppin.product.ntb.app.ApiConstants.PRODUCT_BUY_AGREEMENT_NAME;
import static cn.zeppin.product.ntb.app.ApiConstants.RESOURCE_HOST;
import static cn.zeppin.product.ntb.app.AppConstant.COUPON_USE_COUNT;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BUY_PRICE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_PRODUCT_DETAIL;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_TITLE;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_COUPON;
import static cn.zeppin.product.ntb.app.AppConstant.SELECTED_COUPON;

/**
 * 购买理财
 */
public class ProductBuyActivity extends BaseActivity<ProductPayPresenter, ProductPayModel> implements ProductPayContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_bankIcon)
    ImageView ivBankIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.tv_scode)
    TextView tvScode;
    @Bind(R.id.tv_term)
    TextViewBorder tvTerm;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.tv_recharge)
    TextView tvRecharge;
    @Bind(R.id.tv_money_des)
    TextView tvMoneyDes;
    @Bind(R.id.cet_money)
    ClearEditText cetMoney;
    @Bind(R.id.tv_product_rate)
    TextView tvProductRate;
    @Bind(R.id.tv_termCN)
    TextView tvTermCN;
    @Bind(R.id.tv_expected_return)
    TextView tvExpectedReturn;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    //    @Bind(R.id.cb_purAgreement)
//    CheckBox cbPurAgreement;
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.rl_coupon)
    RelativeLayout rlCoupon;
    @Bind(R.id.tv_coupon_content)
    TextView tvCouponContent;
    private ProductDetail mData;
    //最低买入金额
    private double minInvestAmount;
    //投资递增金额
    private double minInvestAmountAdd;
    //预期收益
    private String expectedReturn = "0.00";
    //账户余额
    private double accountBalance = 0.00;
    //剩余募集额
    private double totalRaise = 0.00;
    private DialogHelper sendSmsDialog;
    private CountDownTimer timer;
    private TextView tvGetCode;
    private Coupon currentCoupon;

    private String uuid = AppApplication.getInstance().getUuid();

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_buy;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void initView() {
        tvTitle.setText("购买理财");
        ivBack.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);//充值
        rlCoupon.setOnClickListener(this);//优惠券

        startProgressDialog();
        if (!NetWorkUtils.isNetConnected(this)) {
            showToastCenter(getResources().getString(R.string.no_net));
        } else {
            User user = AppApplication.getInstance().getUser();
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mData = (ProductDetail) bundle.getSerializable(INTENT_PRODUCT_DETAIL);
                Glide.with(mContext).load(RESOURCE_HOST + mData.getIconColorUrl()).into(ivBankIcon);

                tvName.setText(mData.getName());
                tvBankName.setText("【" + mData.getCustodianCN() + "】");
                tvScode.setText("编码" + mData.getScode());
                tvTerm.setText("期限/" + mData.getTerm() + "天");
//            tvCurrencyType.setText(mData.getCurrencyTypeCN());//理财币种
                minInvestAmountAdd = mData.getMinInvestAmountAdd();
//            String minInvestAmountAddStr = "投资递增金额" + CollectionUtils.numFormat4UnitString(mData.getMinInvestAmountAdd()) + "元";
                String minInvestAmountAddStr = "递增" + mData.getMinInvestAmountAddNum() + mData.getMinInvestAmountAddCN() + "元";
//            tvMinInvestAmountAdd.setText(setTxtStyle(minInvestAmountAddStr, 7, minInvestAmountAddStr.length()));//递增金额

                getUserBalance(user);

                minInvestAmount = mData.getMinInvestAmount();
                SpannableString ss = new SpannableString(CollectionUtils.numFormat4UnitString(minInvestAmount) + "元起，" + minInvestAmountAddStr);//定义hint的值
                AbsoluteSizeSpan ass = new AbsoluteSizeSpan(18, true);//设置 字体大小 true表示单位是sp
                ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                cetMoney.setHint(new SpannedString(ss));
                cetMoney.addTextChangedListener(moneyTextWatcher);

                //预期年化利率
                tvProductRate.setText(mData.getTargetAnnualizedReturnRate() + "%");

                tvTermCN.setText(mData.getTerm() + "天预期收益");

                totalRaise = Double.parseDouble(mData.getTotalRaise().replace(",", ""));

                llContent.setVisibility(View.VISIBLE);
                stopProgressDialog();
            }

            //金额过滤
            InputFilter[] filters = {new PriceInputFilter()};
            cetMoney.setFilters(filters);

            //监听用户信息变化
            mRxManager.on(AppConstant.REFRESH_USER, new Action1<Boolean>() {
                @Override
                public void call(Boolean isRefresh) {
                    String uuid = AppApplication.getInstance().getUuid();
                    mPresenter.getUserInfo(uuid);
                }
            });
            //购买成功
            mRxManager.on(AppConstant.PRODUCT_BUY_SUCCESS, new Action1<Boolean>() {
                @Override
                public void call(Boolean isRefresh) {
                    String uuid = AppApplication.getInstance().getUuid();
                    mPresenter.getUserInfo(uuid);
                }
            });

            //优惠券
            if (user.getCouponCount() > 0) {
                tvCouponContent.setText("未使用");
                tvCouponContent.setEnabled(true);
            } else {
                tvCouponContent.setText("无可用");
                tvCouponContent.setEnabled(false);
            }
        }
    }

    /**
     * 获取设置用户当前余额
     *
     * @param user
     */
    private void getUserBalance(User user) {
        String balance = "当前余额0.00元";
        if (user != null) {
            balance = "当前余额" + user.getAccountBalance() + "元";
            accountBalance = Double.parseDouble(user.getAccountBalance());
        }
        tvBalance.setText(setTxtStyle(balance, 4, balance.length() - 1));
    }


    private TextWatcher moneyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ("".equals(s.toString())) {
                expectedReturn = "0.00";
                tvExpectedReturn.setText(expectedReturn + "元");
                btnBuy.setText("确认购买");
                currentCoupon = null;
                //优惠券
                if (AppApplication.getInstance().getUser().getCouponCount() > 0) {
                    tvCouponContent.setText("未使用");
                    tvCouponContent.setEnabled(true);
                }
                return;
            } else {
                double current = Double.parseDouble(getInputPrice());
                btnBuy.setText("确认购买" + CollectionUtils.numFormat4UnitDetailLess(current) + "元");
                //优惠券
                checkAvailableCoupon(current);
            }

        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_buy://确认购买
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                    toPay();
//                    startActivity(PaySuccessActivity.class);
//                    finish();
                }
                break;
            case R.id.tv_agreement://购买协议
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.INTENT_URL, ApiConstants.PDF_HOST
                        .replace(PDFFILE, PRODUCT_BUY_AGREEMENT)
                        .replace(ApiConstants.PDFNAME, PRODUCT_BUY_AGREEMENT_NAME));
                bundle.putString(INTENT_TITLE, PRODUCT_BUY_AGREEMENT_NAME);
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.tv_recharge://去充值
                startActivity(Recharge2Activity.class);
                break;
            case R.id.rl_coupon://优惠券
                if (tvCouponContent.isEnabled()) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(INTENT_BUY_PRICE, getInputPrice());
                    bundle1.putSerializable(SELECTED_COUPON, currentCoupon);
                    startActivityForResult(CouponActivity.class, bundle1, REQUEST_CODE_COUPON);
                }
                break;
        }
    }

    /**
     * 确认购买
     */

    private void toPay() {
        if (TextUtils.isEmpty(getInputPrice())) {
            showToastCenter("请输入购买金额");
            return;
        }
        double current = Double.parseDouble(getInputPrice());
        if (current < minInvestAmount) {
            showToastCenter("买入金额不能小于最小起购金额" + mData.getMinInvestAmountLess() + "元");
            return;
        }
        if ((current - minInvestAmount) % minInvestAmountAdd != 0) {
            showToastCenter("买入金额不符合投资递增金额要求");
            return;
        }
        if (current > mData.getMaxInvestAmount()) {
            showToastCenter("买入金额不能大于最大投资金额" + mData.getMaxInvestAmountLess() + "元");
            return;
        }
        if (totalRaise < current) {
            showToastCenter("买入金额不能超过剩余募集额" + mData.getTotalRaise() + "元");
            return;
        }
        if (accountBalance < current) {//当前余额小于买入Ò金额，则进入充值页面
            startProgressDialog(getResources().getString(R.string.to_recharge_toast));
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    stopProgressDialog();
                    /**
                     *要执行的操作
                     */
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_BUY_PRICE, getInputPrice());
                    startActivity(Recharge2Activity.class, bundle);
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);//2秒后执行
            return;
        }
        //否则支付
        final String price = getPayPrice();
        startProgressDialog();
        mPresenter.sendCodeById(uuid, price, mData.getUuid(), false);
    }

    /**
     * 获取支付金额
     *
     * @return
     */
    @NonNull
    private String getPayPrice() {
        if (TextUtils.isEmpty(getInputPrice())) {
            return "";
        }
        double payAmount = Double.parseDouble(getInputPrice());
        return (BigDecimal.valueOf(payAmount)).multiply(BigDecimal.valueOf(100)) + "";
    }

    private String getInputPrice() {
        return cetMoney.getText().toString();
    }

    /**
     * 设置字符串start到end之间字符的样式
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    private SpannableString setTxtStyle(String content, int start, int end) {
        SpannableString rate = new SpannableString(content);
        rate.setSpan(new TextAppearanceSpan(this, R.style.BuyMoneyTxtStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return rate;
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showToastCenter(msg);
    }

    @Override
    public void paySuccess() {
        stopLoading();
        sendSmsDialog.dismiss();
        if (timer != null) {
            timer.cancel();
        }
        startActivity(PaySuccessActivity.class);
        finish();
    }

    @Override
    public void sendCodeSuccess(boolean isRegain) {
        if (isRegain) {
            showToastCenter("验证码发送成功");
        } else {
            double payAmount = Double.parseDouble(cetMoney.getText().toString());
            final String price = (BigDecimal.valueOf(payAmount)).multiply(BigDecimal.valueOf(100)) + "";
            User user = AppApplication.getInstance().getUser();
            openSendsmsDialog("发送至" + user.getPhone() + "的手机", price);
        }
    }

    @Override
    public void sendCodeFailed(String message) {
        showToastCenter(message);
        if (timer != null) {
            timer.cancel();
            tvGetCode.setText("获取验证码");
            tvGetCode.setEnabled(true);
        }
    }


    @Override
    public void getUserInfo(User user) {
        AppApplication.getInstance().setUser(user);
        getUserBalance(user);
    }

    private void openSendsmsDialog(String title, final String price) {
        final String uuid = AppApplication.getInstance().getUuid();
//        double payAmount = Double.parseDouble(cetMoney.getText().toString());
//        final String price = (BigDecimal.valueOf(payAmount)).multiply(BigDecimal.valueOf(100)) + "";
        ;//已分为单位

        sendSmsDialog = new DialogHelper(this, R.style.dialog_prompt);
        sendSmsDialog.setContentView(R.layout.dialog_sendsms);
        // 设置标题
        ((TextView) sendSmsDialog.findViewById(R.id.tv_title)).setText(title);
        final ClearEditText etCode = (ClearEditText) sendSmsDialog.findViewById(R.id.et_code);
        tvGetCode = (TextView) sendSmsDialog.findViewById(R.id.tv_getCode);

        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText("重新获取");
            }
        };
        // 设置确定按钮
        Button confirm = (Button) sendSmsDialog.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etCode.getText()) && etCode.getText().length() == 6) {
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                        String couponUuid = "";
                        if (currentCoupon != null) {
                            couponUuid = currentCoupon.getUuid();
                        }
                        mPresenter.productPay(uuid, price, mData.getUuid(), ApiConstants.PayType.BALANCE, etCode.getText().toString(), couponUuid);
                    }
                } else {
                    showToastCenter("请输入正确的验证码");
                }
            }
        });
        //设置取消按钮
        Button cancel = (Button) sendSmsDialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsDialog.dismiss();
                timer.cancel();
            }
        });
        //获取短信验证码
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGetCode.setEnabled(false);
                timer.start();
                mPresenter.sendCodeById(uuid, price, mData.getUuid(), true);
            }
        });
        sendSmsDialog.setCancelable(false);

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = sendSmsDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        sendSmsDialog.show();
        //自动获取验证码
        tvGetCode.setEnabled(false);
        timer.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == AppConstant.RequestCode.REQUEST_CODE_COUPON) {
            currentCoupon = (Coupon) data.getSerializableExtra(SELECTED_COUPON);
            int count = data.getIntExtra(COUPON_USE_COUNT, 0);
            setCouponViewByCoupon(currentCoupon, count);
        }
    }

    private void setCouponViewByCoupon(Coupon coupon, int count) {
        if (TextUtils.isEmpty(getInputPrice())) {
            //未输入购买金额
            if (coupon != null) {
                if (ApiConstants.CouponType.INTERESTS.equals(coupon.getCouponType())) {//加息券
                    changeCouponView("使用" + coupon.getCouponPriceCN() + "%" + coupon.getCouponTypeCN(), true);
                } else {//现金券
                    changeCouponView("使用" + coupon.getCouponPriceCN() + "元" + coupon.getCouponTypeCN(), true);
                }
            } else {
                if (AppApplication.getInstance().getUser().getCouponCount() > 0) {
                    changeCouponView("未使用", true);
                } else {
                    changeCouponView("无可用", false);
                }
            }
        } else {
            double rate = Double.parseDouble(mData.getTargetAnnualizedReturnRate());
            int term = Integer.parseInt(mData.getTerm());
            double current = Double.parseDouble(getInputPrice());
            if (coupon != null) {
                if (ApiConstants.CouponType.INTERESTS.equals(coupon.getCouponType())) {//加息券
                    rate += Double.parseDouble(coupon.getCouponPriceCN());
                    changeCouponView("使用" + coupon.getCouponPriceCN() + "%" + coupon.getCouponTypeCN(), true);
                } else {//现金券
                    current += Double.parseDouble(coupon.getCouponPriceCN());
                    changeCouponView("使用" + coupon.getCouponPriceCN() + "元" + coupon.getCouponTypeCN(), true);
                }
            } else {
                if (count > 0) {
                    changeCouponView("未使用", true);
                } else {
                    changeCouponView("无可用", false);
                }
            }

            double er = current * rate * 0.01 / 365 * term;
            expectedReturn = CollectionUtils.calculateProfit(er);
            tvExpectedReturn.setText(expectedReturn + "元");
        }
    }


    private void changeCouponView(String name, boolean isEnable) {
        tvCouponContent.setText(name);
        tvCouponContent.setEnabled(isEnable);
    }

    /**
     * 获取可用的优惠券
     *
     * @param current
     */
    private void checkAvailableCoupon(final double current) {
        final int count = AppApplication.getInstance().getUser().getCouponCount();
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    String price = (BigDecimal.valueOf(current).multiply(BigDecimal.valueOf(100))) + "";//以分为单位
                    mRxManager.add(new CouponModel().getCouponList(uuid, ApiConstants.CouponStatus.USE, EncryptUtil.getBase64(price), ApiConstants.getToken(uuid, resultData2.getData())).subscribe(new RxSubscriber<ResultData<Coupon>>(mContext, false) {
                        @Override
                        protected void _onNext(ResultData<Coupon> couponResultData) {
                            if (couponResultData.success()) {
                                if (couponResultData.getData().size() > 0) {
                                    currentCoupon = couponResultData.getData().get(0);
                                    setCouponViewByCoupon(currentCoupon, couponResultData.getData().size());
                                } else {
                                    currentCoupon = null;
                                    setCouponViewByCoupon(null, 0);
                                }
                            }
                        }

                        @Override
                        protected void _onError(String message) {
                            currentCoupon = null;
                            setCouponViewByCoupon(currentCoupon, count);
                        }
                    }));
                } else {
                    currentCoupon = null;
                    setCouponViewByCoupon(currentCoupon, count);
                }
            }

            @Override
            protected void _onError(String message) {
                currentCoupon = null;
                setCouponViewByCoupon(currentCoupon, count);
            }
        }));
    }
}
