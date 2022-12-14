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
    //??????????????????
    private MyBank currentMyBank;
    private List<MyBank> myBankList = new ArrayList<>();
    private List<MyBank> bankList = new ArrayList<>();
    private String mOrderNum;//????????????
    private CountDownTimer timer;
    private TextView tvGetCode;
    private DialogHelper sendSmsDialog;

    //??????????????????
    private String currentPayment = null;
    private static final int SDK_AUTH_FLAG = 1;
    //??????????????????
    private List<AlipayCode> mAlipayCodeList;
    //????????????????????????
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

                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
//                        Toast.makeText(Recharge2Activity.this,
//                                "????????????\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                        mPresenter.bindingUserInfoByAli(uuid, authResult.getAuthCode());
                    } else {
                        // ?????????????????????????????????
                        showToastCenter("????????????");

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

        setEtPriceHint(new SpannableString("?????????????????????"));
        etPrice.addTextChangedListener(priceTextWatcher);

        //??????????????????
        InputFilter[] filters = {new PriceInputFilter()};
        etPrice.setFilters(filters);
        //????????????
        setUserBalance(AppApplication.getInstance().getUser());

        uuid = AppApplication.getInstance().getUuid();
        //???????????????????????????
        mPresenter.getPaymentList(uuid, 0, 100, "");

        //????????????
        mRxManager.on(AppConstant.USER_ALIAUTH_SUCCESS, new Action1<User>() {
            @Override
            public void call(User user) {
                tvAlipayNickName.setText("???" + user.getAliNickname() + "???");
                tvAlipayIsBind.setText("?????????");
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
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//?????????????????? true???????????????sp
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
     * ???????????????????????????
     *
     * @param list
     */
    @Override
    public void returnPaymentList(List<PaymentList> list) {
        llContent.setVisibility(View.VISIBLE);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isFlagSwitch()) {//????????????
                currentPayment = list.get(i).getPayment();
                if (ApiConstants.PayType.ALIPAY.equals(currentPayment)) {//?????????
                    setEtPriceHint(new SpannableString("?????????1000?????????????????????5??????"));
                    llAlipay.setVisibility(View.VISIBLE);
                    llAlipayWarn.setVisibility(View.VISIBLE);
                    User user = AppApplication.getInstance().getUser();
                    //??????????????????
                    if (user.isBindingAliFlag()) {//?????????????????????
                        tvAlipayNickName.setText("(" + user.getAliNickname() + ")");
                        tvAlipayIsBind.setText("?????????");
                        tvAlipayIsBind.setEnabled(true);
                    } else {
                        tvAlipayNickName.setText("");
                        tvAlipayIsBind.setText("?????????");
                        tvAlipayIsBind.setEnabled(false);
                    }
                    //????????????????????????
                    mPresenter.getAliQrCode(uuid);
                } else if (ApiConstants.PayType.CHANPAY.equals(currentPayment)) {//??????
                    llBankCardPay.setVisibility(View.VISIBLE);
                    llAlipay.setVisibility(View.GONE);
                    llAlipayWarn.setVisibility(View.GONE);
                    //????????????????????????
                    mPresenter.getBindingCard(uuid);
                }
            }
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param authInfo
     */
    @Override
    public void returnAuthInfo(final String authInfo) {
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // ??????AuthTask ??????
                AuthTask authTask = new AuthTask(Recharge2Activity.this);
                // ???????????????????????????????????????
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // ??????????????????
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public void returnAliQrCode(List<AlipayCode> list) {
        mAlipayCodeList = list;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @param user
     */
    @Override
    public void successBindingAli(User user) {
        tvAlipayNickName.setText("(" + user.getAliNickname() + ")");
        tvAlipayIsBind.setText("?????????");
        tvAlipayIsBind.setEnabled(true);
        User aUser = AppApplication.getInstance().getUser();
        aUser.setAliNickname(user.getAliNickname());
        aUser.setAliUserid(user.getAliUserid());
        aUser.setBindingAliFlag(true);
        AppApplication.getInstance().setUser(aUser);
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//??????????????????
    }

    /**
     * ?????????????????????
     *
     * @param orderNum
     */
    @Override
    public void sendCodeSuccess(String orderNum, boolean isRegain) {
        //???????????????????????????
        mOrderNum = orderNum;
        if (isRegain) {
            showToastCenter("?????????????????????");
        } else {
            final String rechargePrice = (BigDecimal.valueOf(Double.parseDouble(getInputPrice())).multiply(BigDecimal.valueOf(100))) + "";//???????????????
            openSendsmsDialog("?????????" + currentMyBank.getPhone() + "?????????", rechargePrice);
        }
    }

    /**
     * ?????????????????????
     *
     * @param error
     */
    @Override
    public void sendCodeFailed(String error) {
        showToastCenter(error);
        if (timer != null) {
            timer.cancel();
            tvGetCode.setText("???????????????");
            tvGetCode.setEnabled(true);
        }
    }

    /**
     * ??????????????????
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
//            //??????????????????????????????
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
     * ?????????????????????????????????
     *
     * @param list
     * @return
     */
    private MyBank initCurrentMyBank(List<MyBank> list) {
        //??????????????????????????????,
        MyBank saveBank = (MyBank) SpfUtil.getSerializable(this, RECHARGE_BANK);
        if (saveBank != null) {
            //?????????????????????????????????????????????
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUuid().equals(saveBank.getUuid())) {
                    return saveBank;
                }
            }
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????
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
            case R.id.btn_pay://??????
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                    toPay();
                }
                break;
            case R.id.ll_selectBankCard://????????????
                User user = AppApplication.getInstance().getUser();
                if (user != null && user.getBankcardCount() > 0) {
                    //?????????
                    openBankListDialog();
                } else {
                    //?????????????????????
                    final DialogBinding dialogBinding = new DialogBinding(this, "???????????????????????????", R.drawable.unbind_bankcard, "????????????");
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
            case R.id.ll_addBank://????????????
                openAddBankActivity();
                break;
            case R.id.ll_alipay://???????????????
                if (!tvAlipayIsBind.isEnabled()) {//?????????
                    if (AlipayUtil.hasInstalledAlipayClient(this)) {
                        mPresenter.getAuthInfo4Ali(uuid);
                    } else {
//                        ToastUitl.showToastCenter("?????????????????????????????????");
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
     * ?????????
     */
    private void toPay() {
        if (!NetWorkUtils.isNetConnected(this)) {
//            showToastCenter(getResources().getString(R.string.no_net));
            showErrorView(getResources().getString(R.string.no_net));
            return;
        }
        if (TextUtils.isEmpty(getInputPrice())) {
//            showToastCenter("?????????????????????");
            showErrorView("?????????????????????");
            return;
        }
        double price = Double.parseDouble(getInputPrice());
        if (price <= 0) {
            showErrorView("??????????????????????????????");
            return;
        }
        if (ApiConstants.PayType.ALIPAY.equals(currentPayment)) {//???????????????----------------
            if (!tvAlipayIsBind.isEnabled()) {//?????????
//                showToastCenter("???????????????????????????");
//                showErrorView("???????????????????????????");
                startProgressDialog("???????????????????????????");
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        stopProgressDialog();
                        /**
                         *??????????????????
                         */
                        mPresenter.getAuthInfo4Ali(uuid);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);//2????????????
                return;
            }
//            Double price = Double.parseDouble(getInputPrice());
            if (price % 1000 != 0) {
//                showToastCenter("?????????1000????????????");
                showErrorView("?????????1000????????????");
                return;
            }
            if (price > 50000) {
//                showToastCenter("??????????????????5??????");
                showErrorView("????????????????????????5????????????????????????");
                return;
            }
            tvError.setVisibility(View.GONE);
            openAlipayTransfer(getAlipayCode(getInputPrice()));

        } else if (ApiConstants.PayType.CHANPAY.equals(currentPayment)) {//????????????-----------------
            if (currentMyBank == null) {
//                showToastCenter("??????????????????????????????");
                showErrorView("??????????????????????????????");
                return;
            }
            if (getSingleLimit() != 0 && price > getSingleLimit()) {//????????????????????????
//                showToastCenter("????????????????????????????????????????????????");
                showToastCenter("????????????????????????????????????????????????");
                return;
            }
            if (price < 4) {
//                showToastCenter("??????????????????4???");
                showErrorView("??????????????????4???");
            } else {
                tvError.setVisibility(View.GONE);
                //?????????????????????
                final String rechargePrice = (BigDecimal.valueOf(Double.parseDouble(getInputPrice())).multiply(BigDecimal.valueOf(100))) + "";//???????????????
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
     * ??????????????????
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
                btnPay.setText("??????" + CollectionUtils.numFormat4UnitDetailLess(price) + "???");
            } else {
                btnPay.setText("??????");
            }
//                double price = Double.parseDouble(s.toString());
//                if (ApiConstants.PayType.ALIPAY.equals(currentPayment)) {//???????????????----------------
//                    if (price % 1000 != 0) {
//                        tvError.setVisibility(View.VISIBLE);
////                        btnPay.setEnabled(false);
//                        tvError.setText("?????????1000????????????");
//                        return;
//                    } else if (price > 50000) {
//                        tvError.setVisibility(View.VISIBLE);
////                        btnPay.setEnabled(false);
//                        tvError.setText("??????????????????5??????");
//                    } else {
//                        tvError.setVisibility(View.GONE);
////                        btnPay.setEnabled(true);
//                    }
//                } else if (ApiConstants.PayType.CHANPAY.equals(currentPayment)) {//????????????-----------------
//                    if (getSingleLimit() != 0 && price > getSingleLimit()) {//????????????????????????
//                        tvError.setVisibility(View.VISIBLE);
////                        btnPay.setEnabled(false);
//                        tvError.setText("????????????????????????????????????????????????");
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
     * ??????????????????????????????????????????
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
     * ????????????????????????
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
     * ???????????????????????????View
     *
     * @param myBank
     */
    private void setCurrentBankView(MyBank myBank) {
        tvPayBankName.setText(myBank.getShortName() + "????????????" + myBank.getBankcard() + "???");
        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + myBank.getIconColor()).into(ivPayBankIcon);

        tvSingleLimit.setText(numFormat4UnitString(myBank.getSingleLimit()));
        tvDailyLimit.setText(numFormat4UnitString(myBank.getDailyLimit()));
    }


    /**
     * ??????????????????
     */
    private void openBankListDialog() {
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_banklist, null);

        final PopupWindow pop = new PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        tvTitle.setText("??????????????????");
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

                //??????????????????????????????
                SpfUtil.putSerializable(Recharge2Activity.this, RECHARGE_BANK, currentMyBank);
                popDismiss(pop);
            }
        });

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//???????????????
                popDismiss(pop);
                openAddBankActivity();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//??????
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
     * ????????????????????????????????????
     */
    private void checkSingleLimit() {
        if (!TextUtils.isEmpty(getInputPrice())) {
            double price = Double.parseDouble(getInputPrice());
            if (getSingleLimit() != 0 && price > getSingleLimit()) {//????????????????????????
//                tvError.setVisibility(View.VISIBLE);
//                btnPay.setEnabled(false);
//                tvError.setText("????????????????????????????????????????????????");
                showErrorView("????????????????????????????????????????????????");
            }
        }
    }

    /**
     * ????????????????????????
     */
    private void openAddBankActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(INTENT_AUTO_CLOSE, true);
        startActivityForResult(AddBankActivity.class, bundle, AppConstant.RequestCode.REQUEST_CODE_BANKLIST);
    }

    /**
     * ??????????????????????????????
     *
     * @param title
     */
    private void openSendsmsDialog(String title, final String price) {
        final String uuid = AppApplication.getInstance().getUuid();
        sendSmsDialog = new DialogHelper(this, R.style.dialog_prompt);
        sendSmsDialog.setContentView(R.layout.dialog_sendsms);
        // ????????????
        ((TextView) sendSmsDialog.findViewById(R.id.tv_title)).setText(title);
        final ClearEditText etCode = (ClearEditText) sendSmsDialog.findViewById(R.id.et_code);
        tvGetCode = (TextView) sendSmsDialog.findViewById(R.id.tv_getCode);

        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText("????????????(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText("????????????");
            }
        };
        // ??????????????????
        Button confirm = (Button) sendSmsDialog.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etCode.getText()) && etCode.getText().length() == 6) {
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm) || mOrderNum == null) {
                        mPresenter.rechargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.CHECK, "", etCode.getText().toString(), mOrderNum, false);
                    }
                } else {
                    showToastCenter("???????????????????????????");
                }
            }
        });
        //??????????????????
        Button cancel = (Button) sendSmsDialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsDialog.dismiss();
                timer.cancel();
            }
        });
        //?????????????????????
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGetCode.setEnabled(false);
                timer.start();
                mPresenter.rechargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.SEND, "", "", "", true);
            }
        });
        sendSmsDialog.setCancelable(false);

        // ?????????????????????, ?????????????????????
        Window window = sendSmsDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // ????????????
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        sendSmsDialog.show();
        //?????????????????????
        tvGetCode.setEnabled(false);
        timer.start();
    }

    /**
     * ????????????????????????????????????
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
     * ???????????????????????????
     */
    public void openAlipayTransfer(final String code) {
        if (AlipayUtil.hasInstalledAlipayClient(this)) {
            AlipayUtil.startAlipayClient(Recharge2Activity.this, code);  //????????????????????????????????????????????????code  ???????????????????????????????????????
            isOpenAlipay = true;
        } else {
            uninstalledAlipay();
        }
    }

    private void uninstalledAlipay() {
        startProgressDialog("??????????????????????????????????????????????????????????????????????????????");
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
        timer.schedule(task, 2000);//2????????????
    }

    /**
     * ???????????????
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
     * ??????????????????????????????
     *
     * @param user
     */
    private void setUserBalance(User user) {
        String balance = "????????????0.00???";
        if (user != null) {
            balance = "????????????" + user.getAccountBalance() + "???";
        }
        tvAccountBalance.setText(setTxtStyle(balance, 4, balance.length() - 1));
    }

    /**
     * ???????????????start???end?????????????????????
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
            payWarnDialogBuilder = DialogHelper.EnsureAndCancelDialog(Recharge2Activity.this, "????????????", getResources().getString(R.string.recharge_dialog_warn), "???????????????", "??????????????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    payWarnDialogBuilder = null;
                    //??????
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
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//??????????????????
    }
}
