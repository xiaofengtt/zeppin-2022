package cn.zeppin.product.ntb.ui.user.fragment;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geng.library.baseapp.AppManager;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.base.BaseFragment;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.main.activity.MainActivity;
import cn.zeppin.product.ntb.ui.main.activity.WebActivity;
import cn.zeppin.product.ntb.ui.user.activity.ForgotPasswordActivity;
import cn.zeppin.product.ntb.ui.user.contract.LoginContract;
import cn.zeppin.product.ntb.ui.user.model.LoginModel;
import cn.zeppin.product.ntb.ui.user.presenter.LoginPresenter;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;

import static cn.zeppin.product.ntb.app.ApiConstants.PDFFILE;
import static cn.zeppin.product.ntb.app.ApiConstants.USER_AGREEMENT;
import static cn.zeppin.product.ntb.app.ApiConstants.USER_AGREEMENT_NAME;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_PHONE;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_TITLE;
import static com.geng.library.commonutils.CollectionUtils.checkPwd;

/**
 * 账号密码登录
 */
public class AccountFragment extends BaseFragment<LoginPresenter, LoginModel> implements LoginContract.View, View.OnClickListener {

    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_forgetPwd)
    TextView tvForgetPwd;
    @Bind(R.id.tv_warn)
    TextView tvWarn;
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_account;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

        etPhone.setHint(new SpannedString(setHintText("请输入手机号")));
        etPassword.setHint(new SpannedString(setHintText("请输入密码")));
//        etPhone.setTypeface(AppApplication.mTypefaceLight);
//        etPassword.setTypeface(AppApplication.mTypefaceLight);

        btnLogin.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
    }

    private String getInputPhone() {
        return etPhone.getText().toString();
    }

    private String getInputPassword() {
        return etPassword.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                toLogin();
                break;
            case R.id.tv_forgetPwd://忘记密码
                Bundle bundle = new Bundle();
                bundle.putString(INTENT_PHONE, getInputPhone());
                startActivity(ForgotPasswordActivity.class, bundle);
                break;
            case R.id.tv_agreement:
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConstant.INTENT_URL, ApiConstants.PDF_HOST
                        .replace(PDFFILE, USER_AGREEMENT)
                        .replace(ApiConstants.PDFNAME, USER_AGREEMENT_NAME));
                bundle1.putString(INTENT_TITLE, USER_AGREEMENT_NAME);
                startActivity(WebActivity.class, bundle1);
                break;
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
        tvWarn.setText(msg);
        tvWarn.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginSuccess(User user) {
        tvWarn.setVisibility(View.INVISIBLE);
        //登录成功
        SpfUtil.put(getActivity(), AppConstant.UUID, user.getUuid());//保存
        SpfUtil.put(getActivity(), AppConstant.PHONE, EncryptUtil.getBase64(getInputPhone()));//保存手机号用于添加银行默认手机号
        AppApplication.getInstance().setUuid(user.getUuid());
        user.setPhone(CollectionUtils.formatPhone(getInputPhone()));
        AppApplication.getInstance().setUser(user);

        //得到当前用户的手势密码
//        UserGesture userGesture = null;
//        try {
//            userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", CollectionUtils.formatPhone(getInputPhone()));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        if (userGesture == null) {
//            Log.d("UUUUUU", "userGesture == null");
//            //未设置密码时提示,设置手势密码
//            Bundle bundle = new Bundle();
//            bundle.putBoolean(GESTURE_SKIP_HIDE,false);
//            startActivity(CreateGestureActivity.class,bundle);
//            getActivity().finish();
//        } else {
//            if (!userGesture.isOpen()) {//手势开关关闭状态
//                Log.d("UUUUUU", "手势开关关闭状态");
                if (AppManager.getAppManager().isOpenedActivity(MainActivity.class)) {
                    getActivity().finish();
                } else {
                    startActivity(MainActivity.class);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    getActivity().finish();
                }
//                return;
//            }
//            if (userGesture.getGesturePwd() != null) {
//                Log.d("UUUUUU", "userGesture.getGesturePwd() != null");
//                if (AppManager.getAppManager().isOpenedActivity(MainActivity.class)) {
//                    getActivity().finish();
//                } else {
//                    startActivity(MainActivity.class);
//                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    getActivity().finish();
//                }
//            } else {
//                Log.d("UUUUUU", "userGesture.getGesturePwd() == null");
//                Bundle bundle = new Bundle();
//                bundle.putBoolean(GESTURE_SKIP_HIDE,false);
//                startActivity(CreateGestureActivity.class,bundle);
//                getActivity().finish();
//            }
//        }
    }

    private SpannableString setHintText(String content) {
        SpannableString ss = new SpannableString(content);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置 字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void toLogin() {
        if (!NetWorkUtils.isNetConnected(getActivity())) {
            tvWarn.setText(getText(R.string.no_net).toString());
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
        if (TextUtils.isEmpty(getInputPassword())) {
            tvWarn.setText("请输入密码");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        if (!checkPwd(getInputPassword())) {
            tvWarn.setText("密码输入错误");
            tvWarn.setVisibility(View.VISIBLE);
            return;
        }
        tvWarn.setVisibility(View.INVISIBLE);
        mPresenter.login(getInputPhone(), getInputPassword());
    }
}
