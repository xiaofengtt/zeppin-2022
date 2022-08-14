package cn.zeppin.product.ntb.ui.user.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geng.library.baserx.RxBus;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.ui.main.activity.WebActivity;
import cn.zeppin.product.ntb.ui.user.contract.RegisterContract;
import cn.zeppin.product.ntb.ui.user.model.RegisterModel;
import cn.zeppin.product.ntb.ui.user.presenter.RegisterPresenter;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;
import cn.zeppin.product.ntb.widget.dialog.DialogBinding;

import static cn.zeppin.product.ntb.app.ApiConstants.PDFFILE;
import static cn.zeppin.product.ntb.app.ApiConstants.SendCodeType.REGISTER;
import static cn.zeppin.product.ntb.app.ApiConstants.USER_AGREEMENT;
import static cn.zeppin.product.ntb.app.ApiConstants.USER_AGREEMENT_NAME;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_TITLE;
import static com.geng.library.commonutils.CollectionUtils.checkPwd;


/**
 * 注册（成功 直接登录）
 */
// TODO: 17/9/26 键盘遮挡问题 用户协议
public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View, View.OnClickListener {

    //    @Bind(R.id.iv_back)
//    ImageView ivBack;
//    @Bind(R.id.tv_title)
//    TextView tvTitle;
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.et_code)
    ClearEditText etCode;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.tv_warn)
    TextView tvWarn;
    @Bind(R.id.cb_purAgreement)
    CheckBox cbPurAgreement;
    @Bind(R.id.ll_registerView)
    LinearLayout llRegisterView;


    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetCode.setText(millisUntilFinished / 1000 + "s后重新获取");
        }

        @Override
        public void onFinish() {
            tvGetCode.setEnabled(true);
            tvGetCode.setText("获取验证码");
        }
    };
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        etPhone.setHint(new SpannedString(setHintText("请输入手机号")));
        etCode.setHint(new SpannedString(setHintText("请输入验证码")));
        etPassword.setHint(new SpannedString(setHintText("请输入8-20位字母数字组合密码")));

        btnRegister.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);

        autoScrollView(llRegisterView, btnRegister);//弹出软键盘时滚动视图
    }

    private boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            tvWarn.setVisibility(View.VISIBLE);
            tvWarn.setText("请输入手机号");
            return false;
        }
        if (!(CollectionUtils.isPhone(phone))) {
            tvWarn.setVisibility(View.VISIBLE);
            tvWarn.setText("请输入正确的手机号");
            return false;
        }
        return true;
    }

    private String getInputPhone() {
        return etPhone.getText().toString();
    }

    private String getInputCode() {
        return etCode.getText().toString();
    }

    private String getInputPassword() {
        return etPassword.getText().toString();
    }

    public void toLoginActivity(View view) {
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                toRegister();
                break;
            case R.id.tv_getCode://获取验证码
                if (!NetWorkUtils.isNetConnected(this)) {
                    tvWarn.setText(getResources().getString(R.string.no_net));
                    tvWarn.setVisibility(View.VISIBLE);
                    return;
                }
                if (checkPhone(getInputPhone())) {
                    tvWarn.setVisibility(View.INVISIBLE);
                    mPresenter.sendCode(getInputPhone(), REGISTER);
                    tvGetCode.setEnabled(false);
                    timer.start();
                }
                break;
            case R.id.tv_agreement:
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.INTENT_URL, ApiConstants.PDF_HOST
                        .replace(PDFFILE, USER_AGREEMENT)
                        .replace(ApiConstants.PDFNAME, USER_AGREEMENT_NAME));
                bundle.putString(INTENT_TITLE, USER_AGREEMENT_NAME);
                startActivity(WebActivity.class, bundle);
                break;
            default:
                break;
        }

    }

    private void toRegister() {
        if (!NetWorkUtils.isNetConnected(this)) {
            tvWarn.setText(getResources().getString(R.string.no_net));
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(getInputPhone())) {
            tvWarn.setText("请输入手机号码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (!CollectionUtils.isPhone(getInputPhone())) {
            tvWarn.setText("请输入正确的手机号码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(getInputCode())) {
            tvWarn.setText("请输入验证码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (getInputCode().length() != 6) {
            tvWarn.setText("验证输入错误");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(getInputPassword())) {
            tvWarn.setText("请输入密码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (!checkPwd(getInputPassword())) {
            tvWarn.setText("请输入8-20位字母数字组合密码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (!cbPurAgreement.isChecked()) {
            tvWarn.setText("请先阅读并同意《牛投理财用户服务协议》");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        tvWarn.setVisibility(View.INVISIBLE);
        mPresenter.register(getInputPhone(), getInputCode(), getInputPassword());
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

    @Override
    public void registerSuccess(String uuid) {
        tvWarn.setVisibility(View.INVISIBLE);
        //注册成功
        SpfUtil.put(this, AppConstant.UUID, uuid);//保存
        SpfUtil.put(this, AppConstant.PHONE, EncryptUtil.getBase64(getInputPhone()));//保存手机号用于添加银行默认手机号
        AppApplication.getInstance().setUuid(uuid);
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息

        final DialogBinding dialogBinding = new DialogBinding(this, "恭喜你！注册成功！", R.drawable.register_success, "实名认证");
        dialogBinding.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实名认证
                dialogBinding.dismiss();
                startActivity(CertificationActivity.class);
                finish();
            }
        });
        dialogBinding.setDeleteClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBinding.dismiss();
                finish();//关闭
            }
        });
        dialogBinding.show();
    }

    @Override
    public void sendCodeSuccess(String phone) {
        //发送验证成功
        tvWarn.setVisibility(View.VISIBLE);
        tvWarn.setText("短信验证码已发送至" + phone + "，请注意查收。");
    }

    @Override
    public void sendCodeFailed(String msg) {
        tvWarn.setVisibility(View.VISIBLE);
        tvWarn.setText(msg);
        tvGetCode.setText("获取验证码");
        tvGetCode.setEnabled(true);
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
    }

    private SpannableString setHintText(String content) {
        SpannableString ss = new SpannableString(content);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置 字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition = 0;

    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }
}
