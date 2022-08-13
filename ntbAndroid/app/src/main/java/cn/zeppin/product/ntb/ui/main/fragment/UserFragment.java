package cn.zeppin.product.ntb.ui.main.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geng.library.base.BaseFragment;
import com.geng.library.baserx.RxBus;
import com.geng.library.commonutils.AppUtil;
import com.geng.library.commonutils.ToastUitl;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.ui.main.activity.MainActivity;
import cn.zeppin.product.ntb.ui.user.activity.AboutActivity;
import cn.zeppin.product.ntb.ui.user.activity.AlipayBindActivity;
import cn.zeppin.product.ntb.ui.user.activity.CertificationActivity;
import cn.zeppin.product.ntb.ui.user.activity.CertificationYesActivity;
import cn.zeppin.product.ntb.ui.user.activity.CouponActivity;
import cn.zeppin.product.ntb.ui.user.activity.EditPwdActivity;
import cn.zeppin.product.ntb.ui.user.activity.LoginActivity;
import cn.zeppin.product.ntb.ui.user.activity.MessageActivity;
import cn.zeppin.product.ntb.ui.user.activity.MyBankListActivity;
import cn.zeppin.product.ntb.ui.user.activity.SafeActivity;
import cn.zeppin.product.ntb.ui.user.activity.SetPwdActivity;
import cn.zeppin.product.ntb.ui.user.activity.TradingRecordActivity;
import cn.zeppin.product.ntb.utils.SpfUtil;
import cn.zeppin.product.ntb.widget.SelectableRoundedImageView;
import cn.zeppin.product.ntb.widget.dialog.DialogHelper;
import rx.functions.Action1;

import static android.text.TextUtils.isEmpty;
import static cn.zeppin.product.ntb.R.id.rl_setPwd;

/**
 * des:关注主页
 * Created by xsf
 * on 2016.09.17:07
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.iv_head)
    SelectableRoundedImageView ivHead;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.ll_unLogin)
    LinearLayout llUnLogin;
    @Bind(R.id.ll_idCard)
    LinearLayout llIdCard;
    @Bind(R.id.ll_bankCard)
    LinearLayout llBankCard;
    @Bind(R.id.rl_tradingRecord)
    RelativeLayout rlTradingRecord;
    @Bind(R.id.rl_contactUs)
    RelativeLayout rlContactUs;
    @Bind(R.id.btn_logout)
    Button btnLogout;
    @Bind(R.id.tv_authStatus)
    TextView tvAuthStatus;
    @Bind(R.id.tv_bindStatus)
    TextView tvBindStatus;
    @Bind(R.id.rl_login)
    RelativeLayout rlLogin;
    @Bind(R.id.iv_idCard)
    ImageView ivIdCard;
    @Bind(R.id.iv_bankCard)
    ImageView ivBankCard;
    @Bind(R.id.id_toolbar)
    View idToolbar;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_alipay_isBind)
    TextView tvAlipayIsBind;
    @Bind(R.id.rl_bindingAlipay)
    RelativeLayout rlBindingAlipay;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.rl_version_update)
    RelativeLayout rlVersionUpdate;
    @Bind(R.id.rl_coupon)
    RelativeLayout rlCoupon;
    @Bind(R.id.iv_new_version)
    ImageView ivNewVersion;
    @Bind(R.id.iv_new_notice)
    ImageView ivNewNotice;
    @Bind(R.id.fl_notice)
    FrameLayout flNotice;
    @Bind(R.id.tv_toLogin)
    TextView tvToLogin;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.tv_coupon_count)
    TextView tvCouponCount;
    @Bind(R.id.rl_safe)
    RelativeLayout rlSafe;

    private User user = null;
    private String uuid = AppApplication.getInstance().getUuid();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("个人中心");
        loadUserInfo();

        //监听信息变化
        mRxManager.on(AppConstant.REFRESH_USERFRAGMENT, new Action1<Boolean>() {
            @Override
            public void call(Boolean isRefresh) {
                loadUserInfo();
            }
        });

//        btnLogin.setOnClickListener(this);
//        btnRegister.setOnClickListener(this);
        llIdCard.setOnClickListener(this);
        llBankCard.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        rlTradingRecord.setOnClickListener(this);
        rlContactUs.setOnClickListener(this);
        rlBindingAlipay.setOnClickListener(this);
        rlVersionUpdate.setOnClickListener(this);
        rlCoupon.setOnClickListener(this);

        llUnLogin.setOnClickListener(this);
        flNotice.setOnClickListener(this);

        //当前应用版本名
        tvVersion.setText("v" + AppUtil.getVersionName(getActivity()));
    }

    @OnClick(R.id.rl_safe)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_idCard://实名认证
                if (isLogin() && user != null) {
                    if (user.isRealnameAuthFlag()) {//已实名认证
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(AppConstant.INTENT_USER, user);
                        startActivity(CertificationYesActivity.class, bundle);
                    } else {
                        startActivity(CertificationActivity.class);
                    }
                }
                break;
            case R.id.ll_bankCard://我的银行卡
                if (isLogin()) {
                    User user = AppApplication.getInstance().getUser();
                    //实名认证判断
                    if (user != null && !user.isRealnameAuthFlag()) {
                        startProgressDialog(getResources().getString(R.string.to_certification_toast));
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                stopProgressDialog();
                                /**
                                 *要执行的操作
                                 */
                                startActivity(CertificationActivity.class);
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 2000);//2秒后执行
                        return;
                    }
                    startActivity(MyBankListActivity.class);
                }
                break;
            case R.id.btn_logout://退出登录
                DialogHelper.EnsureAndCancelDialog(getActivity(), "温馨提示", "您确定要退出登录吗？", "确认", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //确定
                        uuid = null;
                        user = null;
                        AppApplication.getInstance().removeLocalInfo();
                        loadUserInfo();
                        RxBus.getInstance().post(AppConstant.LOGIN_OUT, true);//退出登录
                        startActivity(LoginActivity.class);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                break;
            case R.id.rl_tradingRecord://交易记录
                if (isLogin()) {
                    startActivity(TradingRecordActivity.class);
                }
                break;
            case R.id.rl_contactUs://联系我们
//                ToastUitl.showToastCenter("联系我们");
                startActivity(AboutActivity.class);

//                new ShareUtil(getActivity()).shareMenu("https://work.bugtags.com/apps/1568423609901094","理财产品",null);
                break;
            case R.id.ll_unLogin://未登录
                startActivity(LoginActivity.class);
                break;
            case R.id.rl_bindingAlipay://绑定支付宝
                //实名认证判断
                if (user != null && !user.isRealnameAuthFlag()) {
                    startProgressDialog(getResources().getString(R.string.to_certification_toast));
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            stopProgressDialog();
                            /**
                             *要执行的操作
                             */
                            startActivity(CertificationActivity.class);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 2000);//2秒后执行
                    return;
                }
                startActivity(AlipayBindActivity.class);
                break;
            case R.id.rl_editPwd://修改密码
                startActivity(EditPwdActivity.class);
                break;
            case rl_setPwd://设置密码
                startActivity(SetPwdActivity.class);
                break;
            case R.id.rl_version_update://版本更新
                ((MainActivity) getActivity()).getVersionInfo(true);
                break;
            case R.id.rl_coupon://优惠券
                startActivity(CouponActivity.class);
                break;
            case R.id.fl_notice://我的消息
                startActivity(MessageActivity.class);
                break;
            case R.id.rl_safe://安全设置
                startActivity(SafeActivity.class);
                break;
            default:
                break;
        }
    }

    public void showLoading(String title) {
        startProgressDialog(title);
    }

    public void stopLoading() {
        stopProgressDialog();
    }

    public void showErrorTip(String msg) {
        ToastUitl.showToastCenter(msg);
    }

    public void getUserInfo(User user) {
        if (user != null) {
            AppApplication.getInstance().setUser(user);
            SpfUtil.put(getActivity(), AppConstant.UUID, uuid);//保存
            this.user = user;
            llUnLogin.setVisibility(View.GONE);
            tvUserName.setVisibility(View.VISIBLE);
            if (user.isRealnameAuthFlag()) {
                tvUserName.setText(user.getPublicName());
            } else {
                tvUserName.setText(user.getNickname());
            }
            btnLogout.setVisibility(View.VISIBLE);

            //是否认证
            if (user.isRealnameAuthFlag()) {
                tvAuthStatus.setText("已认证");
                ivIdCard.setImageDrawable(getResources().getDrawable(R.drawable.user_idcard_yes));
                tvAuthStatus.setTextColor(getResources().getColor(R.color.color_5BA241));
            } else {
                tvAuthStatus.setText("未认证");
                ivIdCard.setImageDrawable(getResources().getDrawable(R.drawable.user_idcard));
                tvAuthStatus.setTextColor(getResources().getColor(R.color.color_4990E2));
            }

            //是否绑卡
            if (user.getBankcardCount() > 0) {
                ivBankCard.setImageDrawable(getResources().getDrawable(R.drawable.user_bankcard_yes));
                tvBindStatus.setText(user.getBankcardCount() + "张");
                tvBindStatus.setTextColor(getResources().getColor(R.color.color_5BA241));
            } else {
                tvBindStatus.setText("未绑定");
                tvBindStatus.setTextColor(getResources().getColor(R.color.color_4990E2));
                ivBankCard.setImageDrawable(getResources().getDrawable(R.drawable.user_bankcard));
            }

            //是否绑定支付宝
            if (user.isBindingAliFlag()) {
                tvAlipayIsBind.setText("已绑定");
                tvAlipayIsBind.setEnabled(true);
            } else {
                tvAlipayIsBind.setText("未绑定");
                tvAlipayIsBind.setEnabled(false);
            }
        }
    }

    /**
     * 获取用户信息
     */
    private void loadUserInfo() {
//        uuid = (String) SpfUtil.get(getActivity(), AppConstant.UUID, null);
        uuid = AppApplication.getInstance().getUuid();
        user = AppApplication.getInstance().getUser();
        if (!isEmpty(uuid)) {//已登录
            if (user != null) {
                getUserInfo(user);
            }
            rlLogin.setVisibility(View.VISIBLE);
            llUnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            idToolbar.setVisibility(View.GONE);
            rlBindingAlipay.setVisibility(View.VISIBLE);
//            //是否设置过秘密
//            if (user.isPwdFlag()) {
//                rlEditPwd.setVisibility(View.VISIBLE);
//                rlSetPwd.setVisibility(View.GONE);
//            } else {
//                rlEditPwd.setVisibility(View.GONE);
//                rlSetPwd.setVisibility(View.VISIBLE);
//            }
            rlCoupon.setVisibility(View.VISIBLE);
            if (user.getCouponCount() > 0) {
                tvCouponCount.setText(user.getCouponCount() + "张");
            } else {
                tvCouponCount.setText("无可用");
            }

            Glide.with(this).load(user.getAliPhoto()).error(R.drawable.head_login).into(ivHead);

            //是否有未读消息
            if (user.isMessageFlag()) {
                //有未读消息则红点提示
                ivNewNotice.setVisibility(View.VISIBLE);
            } else {
                ivNewNotice.setVisibility(View.GONE);
            }
            rlSafe.setVisibility(View.VISIBLE);
//            ivAlipayHead.setImageDrawable(getResources().getDrawable(R.drawable.head_login));
        } else {
            rlLogin.setVisibility(View.GONE);
            llUnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
            idToolbar.setVisibility(View.VISIBLE);
            tvAuthStatus.setText("未认证");
            ivIdCard.setImageDrawable(getResources().getDrawable(R.drawable.user_idcard));
            tvAuthStatus.setTextColor(getResources().getColor(R.color.color_4990E2));
            tvBindStatus.setText("未绑定");
            tvBindStatus.setTextColor(getResources().getColor(R.color.color_4990E2));
            ivBankCard.setImageDrawable(getResources().getDrawable(R.drawable.user_bankcard));
            rlBindingAlipay.setVisibility(View.GONE);
//            rlEditPwd.setVisibility(View.GONE);
//            rlSetPwd.setVisibility(View.GONE);
            rlCoupon.setVisibility(View.GONE);
            rlSafe.setVisibility(View.GONE);
        }
    }

    /**
     * 登录判断
     *
     * @return
     */
    private boolean isLogin() {
        if (TextUtils.isEmpty(uuid)) {
            startActivity(LoginActivity.class);
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((MainActivity) getActivity()).isNewVersion()) {
            ivNewVersion.setVisibility(View.VISIBLE);
        } else {
            ivNewVersion.setVisibility(View.GONE);
        }
    }
}
