package cn.zeppin.product.ntb.ui.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdCheckContract;
import cn.zeppin.product.ntb.ui.user.model.ResetPwdCheckModel;
import cn.zeppin.product.ntb.ui.user.presenter.ResetPwdCheckPresenter;
import cn.zeppin.product.ntb.widget.ClearEditText;

import static cn.zeppin.product.ntb.app.ApiConstants.SendCodeType.RESETPWD;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_RESET;

/**
 * 找回密码
 */
public class ForgotPasswordActivity extends BaseActivity<ResetPwdCheckPresenter, ResetPwdCheckModel> implements ResetPwdCheckContract.View, View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.et_code)
    ClearEditText etCode;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Bind(R.id.tv_warn)
    TextView tvWarn;
    @Bind(R.id.btn_next)
    Button btnNext;

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
        return R.layout.activity_forgot_password;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("找回密码");
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String mPhone = bundle.getString(AppConstant.INTENT_PHONE);
            if (!TextUtils.isEmpty(mPhone)) {
                etPhone.setText(mPhone);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next://下一步
                if (checkNet() && checkPhone() && checkCode()) {
                    mPresenter.resetCheck(getInputPhone(), getInputCode());
                }
                break;
            case R.id.tv_getCode://获取验证码
                if (checkNet() && checkPhone()) {
                    tvGetCode.setEnabled(false);
                    timer.start();
                    mPresenter.sendCode(getInputPhone(), RESETPWD);
                }
                break;
        }
    }

    private String getInputPhone() {
        return etPhone.getText().toString();
    }

    private String getInputCode() {
        return etCode.getText().toString();
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
        tvWarn.setText(msg);
        tvWarn.setVisibility(View.VISIBLE);
    }

    /**
     * 短信验证成功
     */
    @Override
    public void resetCheckSuccess() {
        timer.cancel();
        tvGetCode.setEnabled(true);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.INTENT_PHONE, getInputPhone());
        bundle.putString(AppConstant.INTENT_CODE, getInputCode());

        startActivityForResult(ResetPwdActivity.class, bundle, REQUEST_CODE_RESET);
    }

    @Override
    public void sendCodeSuccess() {
        tvWarn.setText("短信验证码已发送至" + getInputPhone() + "，请注意查收。");
        tvWarn.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendCodeFailed(String msg) {
        stopLoading();
        tvWarn.setVisibility(View.VISIBLE);
        tvWarn.setText(msg);
        tvGetCode.setText("获取验证码");
        tvGetCode.setEnabled(true);
        timer.cancel();
    }

    private boolean checkPhone() {
        if (TextUtils.isEmpty(getInputPhone())) {
            tvWarn.setText("请输入手机号");
            tvWarn.setVisibility(View.VISIBLE);
            return false;
        }
        if (!(CollectionUtils.isPhone(getInputPhone()))) {
            tvWarn.setText("请输入正确的手机号");
            tvWarn.setVisibility(View.VISIBLE);
            return false;
        }
        tvWarn.setVisibility(View.INVISIBLE);
        return true;
    }

    private boolean checkCode() {
        if (TextUtils.isEmpty(getInputCode())) {
            tvWarn.setText("请输入验证码");
            tvWarn.setVisibility(View.VISIBLE);
            return false;
        }
        if (getInputCode().length() != 6) {
            tvWarn.setText("请输入正确的验证码");
            tvWarn.setVisibility(View.VISIBLE);
            return false;
        }
        tvWarn.setVisibility(View.INVISIBLE);
        return true;
    }

    private boolean checkNet() {
        if (!NetWorkUtils.isNetConnected(this)) {
            tvWarn.setText(getText(R.string.no_net).toString());
            tvWarn.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RESET) {
            //重置密码成功后关闭
            finish();
        }
    }
}
