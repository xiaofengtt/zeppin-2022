package cn.zeppin.product.ntb.ui.user.activity;

import android.os.Bundle;
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
import cn.zeppin.product.ntb.ui.user.contract.ResetPwdContract;
import cn.zeppin.product.ntb.ui.user.model.ResetPwdModel;
import cn.zeppin.product.ntb.ui.user.presenter.ResetPwdPresenter;
import cn.zeppin.product.ntb.widget.ClearEditText;

/**
 * 找回密码
 */
public class ResetPwdActivity extends BaseActivity<ResetPwdPresenter, ResetPwdModel> implements ResetPwdContract.View, View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_new_pwd)
    ClearEditText etNewPwd;
    @Bind(R.id.et_confirm_pwd)
    ClearEditText etConfirmPwd;
    @Bind(R.id.tv_warn)
    TextView tvWarn;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private String phone;
    private String code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phone = bundle.getString(AppConstant.INTENT_PHONE);
            code = bundle.getString(AppConstant.INTENT_CODE);
        }

        tvTitle.setText("找回密码");
        ivBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
//        tvGetCode.setOnClickListener(this);

//        etPhone.setTypeface(AppApplication.mTypefaceLight);
//        etCode.setTypeface(AppApplication.mTypefaceLight);
//        etNewPwd.setTypeface(AppApplication.mTypefaceLight);
//        etConfirmPwd.setTypeface(AppApplication.mTypefaceLight);
    }

//
//    private String getInputPhone() {
//        return etPhone.getText().toString();
//    }
//
//    private String getInputCode() {
//        return etCode.getText().toString();
//    }

    private String getInputNewPwd() {
        return etNewPwd.getText().toString();
    }

    private String getInputConfirmPwd() {
        return etConfirmPwd.getText().toString();
    }

//    private boolean checkPhone(String phone) {
//        if (TextUtils.isEmpty(phone)) {
//            tvWarn.setText("请输入手机号");
//            tvWarn.setVisibility(View.VISIBLE);
//            return false;
//        }
//        if (!(CollectionUtils.isPhone(phone))) {
//            tvWarn.setText("请输入正确的手机号");
//            tvWarn.setVisibility(View.VISIBLE);
//            return false;
//        }
//        tvWarn.setVisibility(View.INVISIBLE);
//        return true;
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confirm://确认
                toReset();
                break;
//            case R.id.tv_getCode://获取验证码
//                if (!NetWorkUtils.isNetConnected(this)) {
//                    tvWarn.setVisibility(View.INVISIBLE);
//                    showNetErrorTip();
//                    return;
//                }
//                if (checkPhone(getInputPhone())) {
//                    tvGetCode.setEnabled(false);
//                    timer.start();
//                    mPresenter.sendCode(EncryptUtil.getBase64(getInputPhone()), EncryptUtil.getBase64("resetpwd"));
//                }
//                break;
            default:
                break;
        }
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
    public void resetSuccess() {
//        timer.cancel();
        showToastCenter("重置成功");
        setResult(RESULT_OK);
        finish();
    }


    private void toReset() {
        if (!NetWorkUtils.isNetConnected(this)) {
            tvWarn.setText(getText(R.string.no_net).toString());
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
//        if (TextUtils.isEmpty(getInputPhone())) {
//            tvWarn.setText("请输入手机号");
//            tvWarn.setVisibility(View.VISIBLE);
//            return;
//        }
//        if (!(CollectionUtils.isPhone(getInputPhone()))) {
//            tvWarn.setText("请输入正确的手机号");
//            tvWarn.setVisibility(View.VISIBLE);
//            return;
//        }
        if (TextUtils.isEmpty(getInputNewPwd())) {
            tvWarn.setText("请输入密码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (!CollectionUtils.checkPwd(getInputNewPwd())) {
            tvWarn.setText("请输入8-20位字母数字组合密码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(getInputConfirmPwd())) {
            tvWarn.setText("请再次输入密码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (!getInputNewPwd().equals(getInputConfirmPwd())) {
            tvWarn.setText("两次密码输入不一致");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
//        if (TextUtils.isEmpty(getInputCode())) {
//            tvWarn.setText("请输入验证码");
//            tvWarn.setVisibility(View.VISIBLE);
//            return;
//        }
//        if (getInputCode().length() != 6) {
//            tvWarn.setText("请输入正确的验证码");
//            tvWarn.setVisibility(View.VISIBLE);
//            return;
//        }
        tvWarn.setVisibility(View.INVISIBLE);
        mPresenter.resetPwd(getInputNewPwd(), phone, code);
    }
}
