package cn.zeppin.product.ntb.ui.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geng.library.baserx.RxBus;
import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Bank;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.finance.activity.Recharge2Activity;
import cn.zeppin.product.ntb.ui.user.contract.BindBankContract;
import cn.zeppin.product.ntb.ui.user.model.BindBankModel;
import cn.zeppin.product.ntb.ui.user.presenter.BindBankPresenter;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.BankCardTextWatcher;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogBinding;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;
import rx.Observable;
import rx.functions.Func1;

import static cn.zeppin.product.ntb.app.AppConstant.INTENT_AUTO_CLOSE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_BANK;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_BANK;

/**
 * ???????????????
 */
public class AddBankActivity extends BaseActivity<BindBankPresenter, BindBankModel> implements BindBankContract.View, View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cet_cardholder)
    ClearEditText cetCardholder;
    @Bind(R.id.cet_bankcard)
    ClearEditText cetBankcard;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.iv_bankIcon)
    ImageView ivBankIcon;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.cet_phone)
    ClearEditText cetPhone;
    @Bind(R.id.ll_selectBankCard)
    LinearLayout llSelectBankCard;


    private DialogHelper sendSmsDialog;
    private CountDownTimer timer;

    private String orderNum = "";//????????????
    // ??????????????????
    private Bank currentBank;
    private TextView tvGetCode = null;
    //?????????????????????????????????
    private boolean isAutoClose = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("???????????????");
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        llSelectBankCard.setOnClickListener(this);


//        cetCardholder.setHint(new SpannedString(setHintText("????????????????????????")));
//        cetBankcard.setHint(new SpannedString(setHintText("?????????????????????")));
        cetPhone.setHint(new SpannedString(setHintText("?????????????????????????????????")));

        cetCardholder.addTextChangedListener(cardholderTextWatcher);

        SpannableString ch = new SpannableString("????????????????????????");//??????hint??????
        AbsoluteSizeSpan ach = new AbsoluteSizeSpan(16, true);//?????????????????? true???????????????sp
        ch.setSpan(ach, 0, ch.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cetCardholder.setHint(new SpannedString(ch));

        SpannableString ss = new SpannableString("?????????????????????");//??????hint??????
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//?????????????????? true???????????????sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cetBankcard.setHint(new SpannedString(ss));
        //?????????????????????
        BankCardTextWatcher bankCardTextWatcher = new BankCardTextWatcher(cetBankcard, 26);
        bankCardTextWatcher.setBankCardTextListener(new BankCardTextWatcher.BankCardTextListener() {
            @Override
            public void afterTextChanged(Editable s) {
                if (getInputBankCard().length() > 13 && !TextUtils.isEmpty(getInputBankCard())) {
                    toNext();
                }
            }
        });

        //?????????????????????????????????
        String phone = (String) SpfUtil.get(this, AppConstant.PHONE, "");
        cetPhone.setText(TextUtils.isEmpty(phone) ? "" : EncryptUtil.getFromBase64(phone));

        cetCardholder.setText(AppApplication.getInstance().getUser().getRealnameFull());

        //??????????????????
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isAutoClose = bundle.getBoolean(INTENT_AUTO_CLOSE);
        }
    }

    /**
     * ?????????
     */
    private TextWatcher cardholderTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(getInputCardholder()) && getInputBankCard().length() > 13) {
                toNext();
            }
        }
    };


    private String getInputCardholder() {
        return cetCardholder.getText().toString();
    }

    private String getInputBankCard() {
        return cetBankcard.getText().toString().replace(" ", "");
    }

    private String getInputPhone() {
        return cetPhone.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next://?????????
                if (check()) {
                    if (TextUtils.isEmpty(tvBankName.getText().toString())) {
                        showToastCenter("??????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(getInputPhone())) {
                        showToastCenter("??????????????????????????????");
                        return;
                    }
                    if (!CollectionUtils.isPhone(getInputPhone())) {
                        showToastCenter("???????????????????????????");
                        return;
                    }
                    startProgressDialog();
                    mPresenter.bindingSendCode(AppApplication.getInstance().getUuid(), getInputBankCard(), getInputPhone(), currentBank.getUuid(), getInputCardholder(),false);
//                    openSendsmsDialog("?????????" + getInputPhone() + "?????????");
                }
                break;
            case R.id.ll_selectBankCard://????????????
                if (check()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(INTENT_BANK, currentBank);
                    startActivityForResult(BankActivity.class, bundle, REQUEST_CODE_BANK);
                }
                break;
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(getInputCardholder())) {
            showToastCenter("??????????????????");
            return false;
        }
        if (TextUtils.isEmpty(getInputBankCard())) {
            showToastCenter("?????????????????????");
            return false;
        }
        if (getInputBankCard().length() < 14) {
            showToastCenter("??????????????????????????????");
            return false;
        }
        return true;
    }


    private void openSendsmsDialog(String title) {
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
                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                        mPresenter.bindingCard(AppApplication.getInstance().getUuid(), getInputBankCard(), getInputPhone(), etCode.getText().toString(), currentBank.getUuid(), getInputCardholder(), orderNum);
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
                mPresenter.bindingSendCode(AppApplication.getInstance().getUuid(), getInputBankCard(), getInputPhone(), currentBank.getUuid(), getInputCardholder(),true);
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
     * ?????????
     */
    private void toNext() {
        if (!NetWorkUtils.isNetConnected(this)) {
            showToastCenter(getResources().getString(R.string.no_net));
            return;
        }
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //???????????????????????????
                if (resultData2.success()) {
                    mRxManager.add(bindingCheckCard(ApiConstants.getToken(AppApplication.getInstance().getUuid(), resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<Bank>>(AddBankActivity.this, false) {
                                @Override
                                protected void _onNext(ResultData2<Bank> resultData2) {
                                    if (resultData2.success()) {
                                        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + resultData2.getData().getIconColor()).into(ivBankIcon);
                                        tvBankName.setText(resultData2.getData().getShortName());
                                        currentBank = resultData2.getData();
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    showToastCenter(message);
                                }
                            }));
                }
            }

            @Override
            protected void _onError(String message) {
                showToastCenter(message);
            }
        }));
    }

    private Observable<ResultData2<Bank>> bindingCheckCard(String token) {
        return Api.getDefault().bindingCheckCard(EncryptUtil.getBase64(getInputBankCard()), token)
                .map(new Func1<ResultData2<Bank>, ResultData2<Bank>>() {
                    @Override
                    public ResultData2<Bank> call(ResultData2<Bank> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<Bank>>io_main());
    }

    private SpannableString setHintText(String content) {
        SpannableString ss = new SpannableString(content);//??????hint??????
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//?????? ???????????? true???????????????sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog();
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
    public void sendCodeSuccess(String orderNum,boolean isRegain) {
        this.orderNum = orderNum;
        if(isRegain) {
            showToastCenter("?????????????????????");
        }else {
            openSendsmsDialog("?????????" + getInputPhone() + "?????????");
        }
    }

    @Override
    public void sendCodeFailed(String error) {
        showToastCenter(error);
        if (timer != null) {
            timer.cancel();
        }
        if (tvGetCode != null) {
            tvGetCode.setEnabled(true);
            tvGetCode.setText("???????????????");
        }
    }

    @Override
    public void bindSuccess() {
        sendSmsDialog.dismiss();
        if (timer != null) {
            timer.cancel();
        }
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//??????????????????
        User user = AppApplication.getInstance().getUser();
        user.setBankcardCount(user.getBankcardCount() + 1);
        setResult(RESULT_OK);

        if (isAutoClose) {
            showToastCenter("???????????????????????????");
            finish();
        } else {
            final DialogBinding dialogBinding = new DialogBinding(this, "???????????????????????????", R.drawable.bind_bankcard_success, "?????????");
            dialogBinding.setConfirmClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBinding.dismiss();
                    startActivity(Recharge2Activity.class);
                    finish();
                }
            });
            dialogBinding.setDeleteClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBinding.dismiss();
                    finish();
                }
            });
            dialogBinding.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BANK && data != null) {
            Object object = data.getSerializableExtra(INTENT_BANK);
            if (object != null && !(object instanceof String)) {
                currentBank = (Bank) data.getSerializableExtra(INTENT_BANK);
                tvBankName.setText(currentBank.getShortName());
                Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + currentBank.getIconColorUrl()).into(ivBankIcon);
            }
        }
    }
}
