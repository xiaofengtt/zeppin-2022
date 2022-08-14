package cn.zeppin.product.ntb.ui.user.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.bumptech.glide.Glide;
import com.geng.library.baserx.RxBus;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.pay.AuthResult;
import cn.zeppin.product.ntb.ui.user.contract.AlipayAuthContract;
import cn.zeppin.product.ntb.ui.user.model.AlipayAuthModel;
import cn.zeppin.product.ntb.ui.user.presenter.AlipayAuthPresenter;
import cn.zeppin.product.ntb.utils.AlipayUtil;
import cn.zeppin.product.ntb.widget.SelectableRoundedImageView;

public class AlipayBindActivity extends BaseActivity<AlipayAuthPresenter, AlipayAuthModel> implements AlipayAuthContract.View {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_bind_bg)
    ImageView ivBindBg;
    @Bind(R.id.tv_bind_des)
    TextView tvBindDes;
    @Bind(R.id.tv_alipay_nickName)
    TextView tvAlipayNickName;
    @Bind(R.id.iv_alipay_head)
    SelectableRoundedImageView ivAlipayHead;
    @Bind(R.id.fl_alipay_head_bg)
    FrameLayout flAlipayHeadBg;
    @Bind(R.id.ll_top_bg)
    LinearLayout llTopBg;
    @Bind(R.id.btn_bind)
    Button btnBind;

    private static final int SDK_AUTH_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(Recharge2Activity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                        mPresenter.bindingUserInfoByAli(AppApplication.getInstance().getUuid(), authResult.getAuthCode());
                    } else {
                        // 其他状态值则为授权失败
                        showToastCenter("授权失败");

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_alipay_bind;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("绑定支付宝");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        User user = AppApplication.getInstance().getUser();
        if (user.isBindingAliFlag()) {
            llTopBg.setBackground(getResources().getDrawable(R.drawable.bg_alipay_binding));
            ivBindBg.setBackground(getResources().getDrawable(R.drawable.alipay_binding));
            tvBindDes.setText("当前已绑定！");
            tvBindDes.setTextColor(getResources().getColor(R.color.white));
            tvAlipayNickName.setText(user.getAliNickname());

            Glide.with(mContext).load(user.getAliPhoto()).error(R.drawable.head_login).into(ivAlipayHead);
//            ivAlipayHead.setImageDrawable(getResources().getDrawable(R.drawable.head_login));
            ivAlipayHead.setVisibility(View.VISIBLE);

            flAlipayHeadBg.setBackground(getResources().getDrawable(R.drawable.aipay_binding_headbg));
            btnBind.setText("重新绑定");
        } else {
            llTopBg.setBackground(getResources().getDrawable(R.drawable.bg_alipay_unbinding));
            ivBindBg.setBackground(getResources().getDrawable(R.drawable.alipay_unbind));
            tvBindDes.setText("用户未绑定！");
            tvBindDes.setTextColor(getResources().getColor(R.color.color_565656));
            flAlipayHeadBg.setBackground(getResources().getDrawable(R.drawable.alipay_unbind_head));
            ivAlipayHead.setVisibility(View.GONE);
            btnBind.setText("立即绑定");
        }

        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlipayUtil.hasInstalledAlipayClient(AlipayBindActivity.this)) {
                    mPresenter.getAuthInfo4Ali(AppApplication.getInstance().getUuid());
                } else {
                    startProgressDialog("系统检测到您尚未安装支付宝客户端，请下载安装后重试。");
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
                    timer.schedule(task, 2000);//2秒后执行
                }
            }
        });
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
    public void returnAuthInfo(final String authInfo) {
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(AlipayBindActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public void successBindingAli(User user) {
        finish();
//        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
        RxBus.getInstance().post(AppConstant.USER_ALIAUTH_SUCCESS, user);//授权绑定成功
    }
}
