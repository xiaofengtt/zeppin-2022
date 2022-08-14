package cn.zeppin.product.ntb.ui.finance.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.BaseDialog;

import static cn.zeppin.product.ntb.app.AppConstant.SEND_PHONE;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/12/4
 */

public class SendCodeDialogActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_code)
    ClearEditText etCode;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private Dialog sendSmsDialog;
    //是否自动发送
    public static boolean isAutoSend = false;
    //要发送的验证码
    private String phone = "xxx";
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

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

    @Override
    public int getLayoutId() {
        return R.layout.dialog_sendsms;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phone = bundle.getString(SEND_PHONE);
        }
        if (isAutoSend) {
            tvTitle.setText("验证码已发送至" + phone + "的手机");
        } else {
            tvTitle.setText("发送验证码至" + phone + "的手机");
        }
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_confirm:
                break;
            case R.id.tv_getCode:
                break;
        }
    }

    private void openSendCodeDialog() {
        sendSmsDialog = new BaseDialog(this, R.style.BaseDialog, R.layout.dialog_sendsms);
        // 设置标题
        ((TextView) sendSmsDialog.findViewById(R.id.tv_title)).setText("");
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
//                    if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm) || mOrderNum == null) {
//                        mPresenter.echargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.CHECK, "", etCode.getText().toString(), mOrderNum);
//                    }
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
                timer.cancel();
                finish();
            }
        });
        //获取短信验证码
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGetCode.setEnabled(false);
                timer.start();
//                mPresenter.echargeByChanpay(uuid, price + "", currentMyBank.getUuid(), ApiConstants.BankPayType.SEND, "", "", "");
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


    public void rechargeByChanpay(final String uuid, final String price, final String bankcard, final String type, final String body, final String code, final String orderNum) {
//        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
//            @Override
//            protected void _onNext(ResultData2<String> resultData2) {
//                //获取服务器系统时间
//                if (resultData2.success()) {
//                    mRxManager.add(new RechargeModel().rechargeByChanpay(uuid, EncryptUtil.getBase64(price), bankcard, type, body
//                            , EncryptUtil.getBase64(code), orderNum, ApiConstants.getToken(uuid, resultData2.getData()))
//                            .subscribe(new RxSubscriber<ResultData2<String>>(mContext, false) {
//                                @Override
//                                protected void _onNext(ResultData2<String> resultData2) {
//                                    if (resultData2.success()) {
//                                        mView.sendCodeSuccess(resultData2.getData());
//                                    } else {
//                                        mView.sendCodeFailed(resultData2.getMessage());
//                                    }
//                                }
//
//                                @Override
//                                protected void _onError(String message) {
//                                    if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
//                                        mView.stopLoading();
//                                        mView.showErrorTip(message);
//                                    } else {
//                                        mView.sendCodeFailed(message);
//                                    }
//                                }
//                            }));
//                } else {
//                    if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
//                        mView.stopLoading();
//                        mView.showErrorTip(resultData2.getMessage());
//                    } else {//发送验证码
//                        mView.sendCodeFailed(resultData2.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            protected void _onError(String message) {
//                if (ApiConstants.BankPayType.CHECK.equals(type)) {//充值
//                    mView.stopLoading();
//                    mView.showErrorTip(message);
//                } else {
//                    mView.sendCodeFailed(message);
//                }
//            }
//        }));
    }

    @Override
    protected void onDestroy() {
        dismissAllDialog();
        super.onDestroy();
    }

    private void dismissAllDialog() {
        if (sendSmsDialog != null && sendSmsDialog.isShowing())
            sendSmsDialog.dismiss();
    }

}
