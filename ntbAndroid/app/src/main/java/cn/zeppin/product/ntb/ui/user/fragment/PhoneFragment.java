package cn.zeppin.product.ntb.ui.user.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geng.library.base.BaseFragment;
import com.geng.library.baseapp.AppManager;
import com.geng.library.baserx.RxSchedulers;
import com.geng.library.baserx.RxSubscriber;
import com.geng.library.commonutils.ACache;
import com.geng.library.commonutils.CollectionUtils;
import com.geng.library.commonutils.NetWorkUtils;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.Api;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.bean.ResultData2;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.main.activity.MainActivity;
import cn.zeppin.product.ntb.ui.main.activity.WebActivity;
import cn.zeppin.product.ntb.ui.user.contract.LoginContract;
import cn.zeppin.product.ntb.ui.user.model.LoginModel;
import cn.zeppin.product.ntb.ui.user.presenter.LoginPresenter;
import cn.zeppin.product.ntb.utils.EncryptUtil;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;
import rx.Observable;
import rx.functions.Func1;

import static cn.zeppin.product.ntb.app.ApiConstants.PDFFILE;
import static cn.zeppin.product.ntb.app.ApiConstants.SendCodeType.CODE;
import static cn.zeppin.product.ntb.app.ApiConstants.USER_AGREEMENT;
import static cn.zeppin.product.ntb.app.ApiConstants.USER_AGREEMENT_NAME;
import static cn.zeppin.product.ntb.app.AppConstant.INTENT_TITLE;

/**
 * 手机快速登录
 */
public class PhoneFragment extends BaseFragment<LoginPresenter, LoginModel> implements LoginContract.View, View.OnClickListener {
    @Bind(R.id.et_phone)
    ClearEditText etPhone;
    @Bind(R.id.et_code)
    ClearEditText etCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_getCode)
    TextView tvGetCode;
    //    @Bind(R.id.cb_purAgreement)
//    CheckBox cbPurAgreement;
    @Bind(R.id.tv_warn)
    TextView tvWarn;
    private ACache aCache;

    CountDownTimer timer = new CountDownTimer(60000, 1000) {

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
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_phone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        aCache = ACache.get(getActivity());
        etPhone.setHint(new SpannedString(setHintText("请输入手机号")));
        etCode.setHint(new SpannedString(setHintText("请输入验证码")));

        btnLogin.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
    }

    private String getInputCode() {
        return etCode.getText().toString();
    }

    private String getInputPhone() {
        return etPhone.getText().toString();
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
        //短信验证码已发送至1234568790，请注意查收。
        //登录成功
        SpfUtil.put(getActivity(), AppConstant.UUID, user.getUuid());//保存
        SpfUtil.put(getActivity(), AppConstant.PHONE, EncryptUtil.getBase64(getInputPhone()));//保存手机号用于添加银行默认手机号
        AppApplication.getInstance().setUuid(user.getUuid());
        user.setPhone(CollectionUtils.formatPhone(getInputPhone()));
        AppApplication.getInstance().setUser(user);


//        RealmOperationHelper helper = RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE);
//        //得到当前用户的手势密码
//        UserGesture userGesture = null;
//        try {
//            userGesture = (UserGesture) helper.queryByFieldFirst(UserGesture.class, "phone", CollectionUtils.formatPhone(getInputPhone()));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                toLogin();
                break;
            case R.id.tv_getCode://获取验证码
                if (!NetWorkUtils.isNetConnected(getActivity())) {
                    tvWarn.setText(getResources().getString(R.string.no_net));
                    tvWarn.setVisibility(View.VISIBLE);
                    return;
                }

                if (checkPhone(getInputPhone())) {
                    sendCode(getInputPhone(), CODE);
                    tvWarn.setVisibility(View.INVISIBLE);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private SpannableString setHintText(String content) {
        SpannableString ss = new SpannableString(content);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);//设置 字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


    private Observable<ResultData2> sendCodeModel(String phone, String codeType, String token) {
        return Api.getDefault().sendCode(phone, codeType, token).map(new Func1<ResultData2, ResultData2>() {
            @Override
            public ResultData2<User> call(ResultData2 registerResultData2) {
                return registerResultData2;
            }
        }).compose(RxSchedulers.<ResultData2>io_main());
    }

    private void sendCode(final String phone, final String codeType) {
        mRxManager.add(Api.getTime().subscribe(new RxSubscriber<ResultData2<String>>(AppApplication.getAppContext(), false) {
            @Override
            protected void _onNext(ResultData2<String> resultData2) {
                //获取服务器系统时间
                if (resultData2.success()) {
                    mRxManager.add(sendCodeModel(EncryptUtil.getBase64(phone), EncryptUtil.getBase64(codeType), ApiConstants.sendCodeToken(phone, codeType, resultData2.getData())).subscribe(
                            new RxSubscriber<ResultData2>(getActivity(), false) {
                                @Override
                                protected void _onNext(ResultData2 resultData2) {
                                    if (resultData2.success()) {
                                        //注册失败
                                        tvWarn.setVisibility(View.VISIBLE);
                                        tvWarn.setText("短信验证码已发送至" + phone + "，请注意查收。");
                                    } else {
                                        tvWarn.setVisibility(View.VISIBLE);
                                        tvWarn.setText(resultData2.getMessage());
                                        tvGetCode.setText("获取验证码");
                                        tvGetCode.setEnabled(true);
                                        timer.cancel();
                                    }
                                }

                                @Override
                                protected void _onError(String message) {
                                    sendCodeFailed(message);
                                }
                            }));
                } else {
                    sendCodeFailed(resultData2.getMessage());
                }
            }

            @Override
            protected void _onError(String message) {
                sendCodeFailed(message);
            }
        }));
    }

    private void sendCodeFailed(String message) {
        tvWarn.setText(message);
        tvWarn.setVisibility(View.VISIBLE);
        tvGetCode.setText("获取验证码");
        tvGetCode.setEnabled(true);
        timer.cancel();
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
        tvWarn.setVisibility(View.INVISIBLE);
        mPresenter.loginBycode(getInputPhone(), getInputCode());
    }

}
