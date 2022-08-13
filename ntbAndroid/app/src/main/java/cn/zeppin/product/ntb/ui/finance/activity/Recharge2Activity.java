package cn.zeppin.product.ntb.ui.finance.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geng.library.baserx.RxBus;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.AlipayCode;
import cn.zeppin.product.ntb.bean.MyBank;
import cn.zeppin.product.ntb.bean.PaymentList;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.pay.AuthResult;
import cn.zeppin.product.ntb.ui.bank.adapter.SelectBankAdapter;
import cn.zeppin.product.ntb.ui.user.activity.AddBankActivity;
import cn.zeppin.product.ntb.ui.user.activity.AlipayBindActivity;
import cn.zeppin.product.ntb.ui.user.contract.PayContract;
import cn.zeppin.product.ntb.ui.user.model.PayModel;
import cn.zeppin.product.ntb.ui.user.presenter.PayPresenter;
import cn.zeppin.product.ntb.utils.AlipayUtil;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.utils.PriceInputFilter;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogBinding;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;
import rx.functions.Action1;

import static cn.zeppin.product.ntb.app.ApiConstants.ZEPPIN_ALIPAY_DEFAULT_CODE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_AUTO_CLOSE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_RECHARGE_PRICE;
import static cn.zeppin.product.ntb.app.AppConstant.SpfKey.RECHARGE_BANK;
import static cn.zeppin.product.ntb.utils.MyUtil.setBackgroundAlpha;
import static com.geng.library.commonutils.CollectionUtils.closeKeyboard;
import static com.geng.library.commonutils.CollectionUtils.isHideInput;
import static com.geng.library.commonutils.CollectionUtils.numFormat4UnitString;

public class Recharge2Activity extends BaseActivity<PayPresenter, PayModel> implements PayContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_payBankIcon)
    ImageView ivPayBankIcon;
    @Bind(R.id.tv_payBankName)
    TextView tvPayBankName;
    @Bind(R.id.tv_singleLimit)
    TextView tvSingleLimit;
    @Bind(R.id.tv_dailyLimit)
    TextView tvDailyLimit;
    @Bind(R.id.ll_selectBankCard)
    LinearLayout llSelectBankCard;
    @Bind(R.id.ll_addBank)
    LinearLayout llAddBank;
    @Bind(R.id.ll_bankCardPay)
    LinearLayout llBankCardPay;
    @Bind(R.id.tv_alipay_nickName)
    TextView tvAlipayNickName;
    @Bind(R.id.tv_alipay_isBind)
    TextView tvAlipayIsBind;
    @Bind(R.id.ll_alipay)
    LinearLayout llAlipay;
    @Bind(R.id.et_price)
    ClearEditText etPrice;
    @Bind(R.id.tv_error)
    TextView tvError;
    @Bind(R.id.btn_pay)
    Button btnPay;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_accountBalance)
    TextView tvAccountBalance;
    @Bind(R.id.ll_alipayWarn)
    LinearLayout llAlipayWarn;

    private String uuid;
    //当前选的银行
    private MyBank currentMyBank;
    private List<MyBank> myBankList = new ArrayList<>();
    private List<MyBank> bankList = new ArrayList<>();
    private String mOrderNum;//订单编号
    private CountDownTimer timer;
    private TextView tvGetCode;
    private DialogHelper sendSmsDialog;

    //当前支付方式
    private String currentPayment = null;
    private static final int SDK_AUTH_FLAG = 1;
    //支付宝收款码
    private List<AlipayCode> mAlipayCodeList;
    //是否打开了支付宝
    private boolean isOpenAlipay = false;

    private DialogHelper.Builder payWarnDialogBuilder;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(Recharge2Activity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                        mPresenter.bindingUserInfoByAli(uuid, authResult.getAuthCode());
                    } else {
                        // 其他状态值则为授权失败
                        showToastCenter("授权失败");

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge2;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        ivBack.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        llSelectBankCard.setOnClickListener(this);
        llAddBank.setOnClickListener(this);
        llAlipay.setOnClickListener(this);

        setEtPriceHint(new SpannableString("请输入充值金额"));
        etPrice.addTextChangedListener(priceTextWatcher);

        //充值金额过滤
        InputFilter[] filters = {new PriceInputFilter()};
        etPrice.setFilters(filters);
        //账户余额
        setUserBalance(AppApplication.getInstance().getUser());

        uuid = AppApplication.getInstance().getUuid();
        //获取可用的支付方式
        mPresenter.getPaymentList(uuid, 0, 100, "");

        //重新授权
        mRxManager.on(AppConstant.USER_ALIAUTH_SUCCESS, new Action1<User>() {
            @Override
            public void call(User user) {
                tvAlipayNickName.setText("（" + user.getAliNickname() + "）");
                tvAlipayIsBind.setText("已绑定");
                tvAlipayIsBind.setEnabled(true);
                User aUser = AppApplication.getInstance().getUser();
                aUser.setAliNickname(user.getAliNickname());
                aUser.setAliUserid(user.getAliUserid());
                aUser.setBindingAliFlag(true);
                AppApplication.getInstance().setUser(aUser);
            }
        });
    }

    private void setEtPriceHint(SpannableString ss) {
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etPrice.setHint(new SpannedString(ss));
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

    /**
     * 获取可用的支付方式
     *
     * @param list
     */
    @Override
    public void returnPaymentList(List<PaymentList> list) {
        llContent.setVisibility(View.VISIBLE);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isFlagSwitch()) {//当前可用
                currentPayment = list.get(i).getPayment();
                if (ApiConstants.PayType.ALIPAY.equals(currentPayment)) {//支付宝
                    setEtPriceHint(new SpannableString("请输入1000的整数倍，最高5万元"));
                    llAlipay.setVisibility(View.VISIBLE);
                    llAlipayWarn.setVisibility(View.VISIBLE);
                    User user = AppApplication.getInstance().getUser();
                    //获取用户授权
                    if (user.isBindingAliFlag()) {//判断是否已授权
                        tvAlipayNickName.setText("(" + user.getAliNickname() + ")");
                        tvAlipayIsBind.setText("已绑定");
                        tvAlipayIsBind.setEnabled(true);
                    } else {
                        tvAlipayNickName.setText("");
                        tvAlipayIsBind.setText("未绑定");
                        tvAlipayIsBind.setEnabled(false);
                    }
                    //获取支付宝收款码
                    mPresenter.getAliQrCode(uuid);
                } else if (ApiConstants.PayType.CHANPAY.equals(currentPayment)) {//畅捷
                    llBankCardPay.setVisibility(View.VISIBLE);
                    llAlipay.setVisibility(View.GONE);
                    llAlipayWarn.setVisibility(View.GONE);
                    //获取用户绑卡列表
                    mPresenter.getBindingCard(uuid);
                }
            }
        }
    }

    /**
     * 后台获取鉴权需要的数据
     *
     * @param authInfo
     */
    @Override
    public void returnAuthInfo(final String authInfo) {
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(Recharge2Activity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public void returnAliQrCode(List<AlipayCode> list) {
        mAlipayCodeList = list;
    }

    /**
     * 后台绑定支付宝账号成功后，返回支付宝账户信息
     *
     * @param user
     */
    @Override
    public void successBindingAli(User user) {
        tvAlipayNickName.setText("(" + user.getAliNickname() + ")");
        tvAlipayIsBind.setText("已绑定");
        tvAlipayIsBind.setEnabled(true);
        User aUser = AppApplication.getInstance().getUser();
        aUser.setAliNickname(user.getAliNickname());
        aUser.setAliUserid(user.getAliUserid());
        aUser.setBindingAliFlag(true);
        AppApplication.getInstance().setUser(aUser);
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
    }

    /**
     * 发送验证码成功
     *
     * @param orderNum
     */
    @Override
    public void sendCodeSuccess(String orderNum, boolean isRegain) {
        //充值验证码发送成功
        mOrderNum = orderNum;
        if (isRegain) {
            showToastCenter("验证码发送成功");
        } else {
            final String rechargePrice = (BigDecimal.valueOf(Double.parseDouble(getInputPrice())).multiply(BigDecimal.valueOf(100))) + "";//已分为单位
            openSendsmsDialog("发送至" + currentMyBank.getPhone() + "的手机", rechargePrice);
        }
    }

    /**
     * 发送验证码失败
     *
     * @param error
     */
    @Override
    public void sendCodeFailed(String error) {
        showToastCenter(error);
        if (timer != null) {
            timer.cancel();
            tvGetCode.setText("获取验证码");
            tvGetCode.setEnabled(true);
        }
    }

    /**
     * 畅捷支付成功
     */
    @Override
    public void SuccessByChanpay() {
        sendSmsDialog.dismiss();
        if (timer != null) {
            timer.cancel();
        }
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_RECHARGE_PRICE, getInputPrice());
        startActivity(RechargeSuccessActivity.class, bundle);
        finish();
    }

    @Override
    public void returnMyBankList(List<MyBank> list) {
        myBankList.clear();
        if (list != null && list.size() > 0) {
            myBankList.addAll(list);
            isAddBank(false);
//            //获取之前选择的银行卡
//            Object bankObject = SpfUtil.getSerializable(this, RECHARGE_BANK);
//            if (bankObject != null) {
//                currentMyBank = (MyBank) bankObject;
//            } else {
//                currentMyBank = myBankList.get(0);
//            }
            currentMyBank = initCurrentMyBank(myBankList);
            setCurrentBankView(currentMyBank);
        } else {
            SpfUtil.remove(this, RECHARGE_BANK);
            isAddBank(true);
        }
        llContent.setVisibility(View.VISIBLE);
        stopProgressDialog();
    }

    /**
     * 默认选中一张充值银行卡
     *
     * @param list
     * @return
     */
    private MyBank initCurrentMyBank(List<MyBank> list) {
        //获取之前选择的银行卡,
        MyBank saveBank = (MyBank) SpfUtil.getSerializable(this, RECHARGE_BANK);
        if (saveBank != null) {
            //检测下当前绑卡列表里是否有该卡
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUuid().equals(saveBank.getUuid())) {
                    return saveBank;
                }
            }
        }
        //走到这里表示列表没有该卡，已在别的设备解绑，这时清除本地保存的数据
        SpfUtil.remove(this, RECHARGE_BANK);
        return list.get(0);
    }


    @Override
    public void returnUserInfo(User user) {
        setUserBalance(user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_pay://支付
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                    toPay();
                }
                break;
            case R.id.ll_selectBankCard://选择银行
                User user = AppApplication.getInstance().getUser();
                if (user != null && user.getBankcardCount() > 0) {
                    //已绑定
                    openBankListDialog();
                } else {
                    //还未绑定银行卡
                    final DialogBinding dialogBinding = new DialogBinding(this, "您还未绑定银行卡！", R.drawable.unbind_bankcard, "马上绑卡");
                    dialogBinding.setConfirmClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBinding.dismiss();
                            openAddBankActivity();
                        }
                    });
                    dialogBinding.setDeleteClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBinding.dismiss();
                        }
                    });
                    dialogBinding.show();
                }
                break;
            case R.id.ll_addBank://添加银行
                openAddBankActivity();
                break;
            case R.id.ll_alipay://授权支付宝
                if (!tvAlipayIsBind.isEnabled()) {//未绑定
                    if (AlipayUtil.hasInstalledAlipayClient(this)) {
                        mPresenter.getAuthInfo4Ali(uuid);
                    } else {
//                        ToastUitl.showToastCenter("没有检测到支付宝客户端");
                        uninstalledAlipay();
                    }
                } else {
                    startActivity(AlipayBindActivity.class);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 去支付
     */
    private void toPay() {
        if (!NetWorkUtils.isNetConnected(this)) {
//            showToastCenter(getResources().getString(R.string.no_net));
            showErrorView(getResources().getString(R.string.no_net));
            return;
        }
        if (TextUtils.isEmpty(getInputPrice())) {
//            showToastCenter("请输入充值金额");
            showErrorView("请输入充值金额");
            return;
        }
        double price = Double.parseDouble(getInputPrice());
        if (price <= 0) {
            showErrorView("请输入有效的充值金额");
            return;
        }
        if (ApiConstants.PayType.ALIPAY.equals(currentPayment)) {//支付宝支付----------------
            if (!tvAlipayIsBind.isEnabled()) {//未绑定
//                showToastCenter("请先绑定支付宝账号");
//                showErrorView("请先绑定支付宝账号");
                startProgressDialog("请先绑定支付宝账号");
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        stopProgressDialog();
                        /**
                         *要执行的操作
                         */
                        mPresenter.getAuthInfo4Ali(uuid);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);//2秒后执行
                return;
            }
//            Double price = Double.parseDouble(getInputPrice());
            if (price % 1000 != 0) {
//                showToastCenter("请输入1000的整数倍");
                showErrorView("请输入1000的整数倍");
                return;
            }
            if (price > 50000) {
//                showToastCenter("充值金额最高5万元");
                showErrorView("单笔充值金额最高5万元，请重新输入");
                return;
            }
            tvError.setVisibility(View.GONE);
            openAlipayTransfer(getAlipayCode(getInputPrice()));

        } else if (ApiConstants.PayType.CHANPAY.equals(currentPayment)) {//畅捷支付-----------------
            if (currentMyBank == null) {
//                showToastCenter("请先选择充值的银行卡");
                showErrorView("请先选择充值的银行卡");
                return;
            }
            if (getSingleLimit() != 0 && price > getSingleLimit()) {//大于银行单笔限额
//                showToastCenter("当前充值金额超出限额，请分笔充值");
                showToastCenter("当前充值金额超出限额，请分笔充值");
                return;
            }
            if (price < 4) {
//                showToastCenter("充值金额最低4元");
                showErrorView("充值金额最低4元");
            } else {
                tvError.setVisibility(View.GONE);
                //银行预留手机号
                final String rechargePrice = (BigDecimal.valueOf(Double.parseDouble(getInputPrice())).multiply(BigDecimal.valueOf(100))) + "";//已分为单位
                mPresenter.rechargeByChanpay(uuid, rechargePrice + "", currentMyBank.getUuid(), ApiConstants.BankPayType.SEND, "", "", "", false);
            }
        }
    }

    private void showErrorView(String error) {
        showToastCenter(error);
        tvError.setText(error);
        tvError.setVisibility(View.VISIBLE);
    }

    /**
     * 金额输入监听
     */
    private TextWatcher priceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString())) {
                double price = Double.parseDouble(s.toString());
                btnPay.setText("充值" + CollectionUtils.numFormat4UnitDetailLess(price) + "元");
            } else {
                btnPay.setText("充值");
            }
//                double price = Double.parseDouble(s.toString());
//                if (ApiConstants.PayType.ALIPAY.equals(currentPayment)) {//支付宝支付----------------
//                    if (price % 1000 != 0) {
//                        tvError.setVisibility(View.VISIBLE);
////                        btnPay.setEnabled(false);
//                        tvError.setText("请输入1000的整数倍");
//                        return;
//                    } else if (price > 50000) {
//                        tvError.setVisibility(View.VISIBLE);
////                        btnPay.setEnabled(false);
//                        tvError.setText("充值金额最高5万元");
//                    } else {
//                        tvError.setVisibility(View.GONE);
////                        btnPay.setEnabled(true);
//                    }
//                } else if (ApiConstants.PayType.CHANPAY.equals(currentPayment)) {//畅捷支付-----------------
//                    if (getSingleLimit() != 0 && price > getSingleLimit()) {//大于银行单笔限额
//                        tvError.setVisibility(View.VISIBLE);
////                        btnPay.setEnabled(false);
//                        tvError.setText("当前充值金额超出限额，请分笔充值");
//                    } else {
//                        tvError.setVisibility(View.GONE);
////                        btnPay.setEnabled(true);
//                    }
//                }
//            } else {
//                tvError.setVisibility(View.GONE);
//                btnPay.setEnabled(false);
//            }
        }
//
    };

    private String getInputPrice() {
        return etPrice.getText().toString();
    }

    /**
     * 获取当前选择的银行，单笔限额
     *
     * @return
     */
    private double getSingleLimit() {
        if (currentMyBank != null) {
            return currentMyBank.getSingleLimit();
        }
        return 0;
    }

    /**
     * 是否需要添加银行
     */
    private void isAddBank(boolean isAdd) {
        if (isAdd) {
            llAddBank.setVisibility(View.VISIBLE);
            llSelectBankCard.setVisibility(View.GONE);
        } else {
            llAddBank.setVisibility(View.GONE);
            llSelectBankCard.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置当前选中的银行View
     *
     * @param myBank
     */
    private void setCurrentBankView(MyBank myBank) {
        tvPayBankName.setText(myBank.getShortName() + "储蓄卡（" + myBank.getBankcard() + "）");
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + myBank.getIconColor()).into(ivPayBankIcon);

        tvSingleLimit.setText(numFormat4UnitString(myBank.getSingleLimit()));
        tvDailyLimit.setText(numFormat4UnitString(myBank.getDailyLimit()));
    }


    /**
     * 选择银行弹框
     */
    private void openBankListDialog() {
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_banklist, null);

        final PopupWindow pop = new PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        tvTitle.setText("选择充值银行");
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

        bankList = new ArrayList<>();
        bankList.addAll(myBankList);

        String currentMyBankUUid = null;
        if (currentMyBank != null) {
            currentMyBankUUid = currentMyBank.getUuid();
        }
        SelectBankAdapter mAdapter = new SelectBankAdapter(bankList, currentMyBankUUid, true);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footView = inflater.inflate(R.layout.dialog_banklist_item_foot, null);
        mAdapter.setFooterView(footView);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentMyBank = myBankList.get(position);
                setCurrentBankView(currentMyBank);
                checkSingleLimit();

                //记录当前选择的银行卡
                SpfUtil.putSerializable(Recharge2Activity.this, RECHARGE_BANK, currentMyBank);
                popDismiss(pop);
            }
        });

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加银行卡
                popDismiss(pop);
                openAddBankActivity();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//关闭
                popDismiss(pop);
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popDismiss(pop);
            }
        });
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popDismiss(pop);
                }
                return false;
            }
        });


        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.DialogBankList);
        pop.showAtLocation(contentView, Gravity.BOTTOM, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        setBackgroundAlpha(this, 0.6f);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBackgroundAlpha(Recharge2Activity.this, 1f);
            }
        });
    }

    private void popDismiss(PopupWindow pop) {
        if (pop.isShowing()) {
            pop.dismiss();
        }
    }

    /**
     * 判断是否超过银行单笔限额
     */
    private void checkSingleLimit() {
        if (!TextUtils.isEmpty(getInputPrice())) {
            double price = Double.parseDouble(getInputPrice());
            if (getSingleLimit() != 0 && price > getSingleLimit()) {//大于银行单笔限额
//                tvError.setVisibility(View.VISIBLE);
//                btnPay.setEnabled(false);
//                tvError.setText("当前充值金额超出限额，请分笔充值");
                showErrorView("当前充值金额超出限额，请分笔充值");
            }
        }
    }

    /**
     * 打开添加银行页面
     */
    private void openAddBankActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(INTENT_AUTO_CLOSE, true);
        startActivityForResult(AddBankActivity.class, bundle, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
    }

    /**
     * 打开发送验证码对话框
     *
     * @param title
     */
    private void openSendsmsDialog(String title, final String price) {
        final String uuid = AppApplication.getInstance().getUuid();
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
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm) || mOrderNum == null) {
                        mPresenter.rechargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.CHECK, "", etCode.getText().toString(), mOrderNum, false);
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
                mPresenter.rechargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.SEND, "", "", "", true);
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

    /**
     * 点击其他区域，关闭软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                closeKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 打开支付宝转账页面
     */
    public void openAlipayTransfer(final String code) {
        if (AlipayUtil.hasInstalledAlipayClient(this)) {
            AlipayUtil.startAlipayClient(Recharge2Activity.this, code);  //第二个参数代表要给被支付的二维码code  可以在用草料二维码在线生成
            isOpenAlipay = true;
        } else {
            uninstalledAlipay();
        }
    }

    private void uninstalledAlipay() {
        startProgressDialog("系统检测到您尚未安装支付宝客户端，请下载安装后重试。");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                stopProgressDialog();
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://ds.alipay.com/?from=mobilec");
                intent.setData(content_url);
                startActivity(intent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);//2秒后执行
    }

    /**
     * 获取收款码
     *
     * @param code
     * @return
     */
    public String getAlipayCode(String code) {
        if (mAlipayCodeList == null || TextUtils.isEmpty(code)) {
            return ZEPPIN_ALIPAY_DEFAULT_CODE;
        } else {
            String priceCN;
            for (AlipayCode aCode : mAlipayCodeList) {
                priceCN = aCode.getPriceCN().substring(0, aCode.getPriceCN().length() - 1);
                if (priceCN.equals(code)) {
                    return aCode.getPriceUrlcodeCN();
                }
            }
            return ZEPPIN_ALIPAY_DEFAULT_CODE;
        }
    }

    /**
     * 获取设置用户当前余额
     *
     * @param user
     */
    private void setUserBalance(User user) {
        String balance = "当前余额0.00元";
        if (user != null) {
            balance = "当前余额" + user.getAccountBalance() + "元";
        }
        tvAccountBalance.setText(setTxtStyle(balance, 4, balance.length() - 1));
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.RequestCode.REQUEST_CODE_BANKLIST) {
            String uuid = AppApplication.getInstance().getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                mPresenter.getBindingCard(uuid);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOpenAlipay && payWarnDialogBuilder == null) {
            payWarnDialogBuilder = DialogHelper.EnsureAndCancelDialog(Recharge2Activity.this, "温馨提示", getResources().getString(R.string.recharge_dialog_warn), "已完成付款", "付款遇到问题", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    payWarnDialogBuilder = null;
                    //确定
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    payWarnDialogBuilder = null;
                    mPresenter.getUserInfo(uuid);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
    }
}
