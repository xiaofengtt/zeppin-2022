package cn.zeppin.product.ntb.ui.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import cn.zeppin.product.ntb.bean.MyBankDetail;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.user.contract.UnBindBankContract;
import cn.zeppin.product.ntb.ui.user.model.UnbindBankModel;
import cn.zeppin.product.ntb.ui.user.presenter.UnbindBankPresenter;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;
import rx.Observable;
import rx.functions.Func1;

public class MyBankDetailActivity extends BaseActivity<UnbindBankPresenter, UnbindBankModel> implements UnBindBankContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_bankIcon)
    ImageView ivBankIcon;
    @Bind(R.id.tv_bankName)
    TextView tvBankName;
    @Bind(R.id.iv_bankIconBig)
    ImageView ivBankIconBig;
    @Bind(R.id.tv_bankcard)
    TextView tvBankcard;
    @Bind(R.id.tv_singleLimit)
    TextView tvSingleLimit;
    @Bind(R.id.tv_dailyLimit)
    TextView tvDailyLimit;
    @Bind(R.id.btn_unbind)
    Button btnUnbind;
    @Bind(R.id.cv_bank)
    CardView cvBank;
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    private String bankUuid;
    private DialogHelper sendSmsDialog;
    private CountDownTimer timer;
    private MyBankDetail myBankDetail;
    private User user;
    private TextView tvGetCode = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bank_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    private void initData() {
        String uuid = AppApplication.getInstance().getUuid();
        Bundle bundle = getIntent().getExtras();
        bankUuid = bundle.getString(AppConstant.INTENT_BANK, "");

        if (NetWorkUtils.isNetConnected(this)) {
            if (!TextUtils.isEmpty(uuid)) {
                startProgressDialog();
                getBindingCardInfo(uuid);
            }
        } else {
            showToastCenter(getResources().getString(R.string.net_error));
        }
    }

    private void getBindingCardInfo(final String uuid) {
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManager.add(bindingCardInfo(uuid, bankUuid, ApiConstants.getToken(uuid, resultData2.getData()))
                            .subscribe(new RxSubscriber<ResultData2<MyBankDetail>>(mContext, false) {
                                @Override
                                protected void _onNext(ResultData2<MyBankDetail> resultData2) {
                                    if (resultData2.success()) {
                                        myBankDetail = resultData2.getData();
                                        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + myBankDetail.getIcon()).into(ivBankIcon);
                                        Glide.with(mContext).load(ApiConstants.RESOURCE_HOST + myBankDetail.getIcon()).into(ivBankIconBig);
                                        tvBankName.setText(myBankDetail.getShortName());
                                        tvBankcard.setText("****  ****  ****  " + myBankDetail.getBankcard());
                                        tvSingleLimit.setText("￥" + myBankDetail.getSingleLimit());//单笔限额
                                        tvDailyLimit.setText("￥" + myBankDetail.getDailyLimit());//每日限额
                                        cvBank.setCardBackgroundColor(Color.parseColor(myBankDetail.getColor()));
                                        llContent.setVisibility(View.VISIBLE);
                                    } else {
                                        showToastCenter(resultData2.getMessage());
                                    }
                                    stopProgressDialog();
                                }

                                @Override
                                protected void _onError(String message) {
                                    showToastCenter(message);
                                    stopProgressDialog();
                                }
                            }));
                } else {
                    stopProgressDialog();
                    showToastCenter(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                showToastCenter(message);
                stopProgressDialog();
            }
        }));
    }

    @Override
    public void initView() {
        tvTitle.setText("交易限额");
        ivBack.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);

        user = AppApplication.getInstance().getUser();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_unbind:
                if (user != null) {
                    openSendsmsDialog("发送至" + CollectionUtils.formatPhone(user.getPhone()) + "的手机");
                }
                break;
        }
    }

    private Observable<ResultData2<MyBankDetail>> bindingCardInfo(String uuid, String bankcard, String token) {
        return Api.getDefault().bindingCardInfo(uuid, bankcard, token)
                .map(new Func1<ResultData2<MyBankDetail>, ResultData2<MyBankDetail>>() {
                    @Override
                    public ResultData2 call(ResultData2<MyBankDetail> resultData2) {
                        return resultData2;
                    }
                }).compose(RxSchedulers.<ResultData2<MyBankDetail>>io_main());
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
    public void sendCodeSuccess() {
        showToastCenter("验证码发送成功");
    }

    @Override
    public void sendCodeFailed(String error) {
        showToastCenter(error);
        if (timer != null) {
            timer.cancel();
        }
        if (tvGetCode != null) {
            tvGetCode.setEnabled(true);
            tvGetCode.setText("获取验证码");
        }
    }

    @Override
    public void unbindSuccess() {
        if (timer != null) {
            timer.cancel();
        }
        sendSmsDialog.dismiss();
        //解绑成功
        showToastCenter("解绑成功！");
        setResult(RESULT_OK);
        User user = AppApplication.getInstance().getUser();
        user.setBankcardCount(user.getBankcardCount() - 1);

        finish();
    }


    private void openSendsmsDialog(String title) {
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
                        mPresenter.unbindBankcard(AppApplication.getInstance().getUuid(), user.getPhone(), etCode.getText().toString(), bankUuid);
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
                mPresenter.unbindSendCode(AppApplication.getInstance().getUuid(), bankUuid, user.getPhone());
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
    }
}
