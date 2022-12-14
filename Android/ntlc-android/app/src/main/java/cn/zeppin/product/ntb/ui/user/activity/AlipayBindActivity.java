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

                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
//                        Toast.makeText(Recharge2Activity.this,
//                                "????????????\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                        mPresenter.bindingUserInfoByAli(AppApplication.getInstance().getUuid(), authResult.getAuthCode());
                    } else {
                        // ?????????????????????????????????
                        showToastCenter("????????????");

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
        tvTitle.setText("???????????????");
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
            tvBindDes.setText("??????????????????");
            tvBindDes.setTextColor(getResources().getColor(R.color.white));
            tvAlipayNickName.setText(user.getAliNickname());

            Glide.with(mContext).load(user.getAliPhoto()).error(R.drawable.head_login).into(ivAlipayHead);
//            ivAlipayHead.setImageDrawable(getResources().getDrawable(R.drawable.head_login));
            ivAlipayHead.setVisibility(View.VISIBLE);

            flAlipayHeadBg.setBackground(getResources().getDrawable(R.drawable.aipay_binding_headbg));
            btnBind.setText("????????????");
        } else {
            llTopBg.setBackground(getResources().getDrawable(R.drawable.bg_alipay_unbinding));
            ivBindBg.setBackground(getResources().getDrawable(R.drawable.alipay_unbind));
            tvBindDes.setText("??????????????????");
            tvBindDes.setTextColor(getResources().getColor(R.color.color_565656));
            flAlipayHeadBg.setBackground(getResources().getDrawable(R.drawable.alipay_unbind_head));
            ivAlipayHead.setVisibility(View.GONE);
            btnBind.setText("????????????");
        }

        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlipayUtil.hasInstalledAlipayClient(AlipayBindActivity.this)) {
                    mPresenter.getAuthInfo4Ali(AppApplication.getInstance().getUuid());
                } else {
                    startProgressDialog("??????????????????????????????????????????????????????????????????????????????");
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
                    timer.schedule(task, 2000);//2????????????
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
                // ??????AuthTask ??????
                AuthTask authTask = new AuthTask(AlipayBindActivity.this);
                // ???????????????????????????????????????
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // ??????????????????
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public void successBindingAli(User user) {
        finish();
//        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//??????????????????
        RxBus.getInstance().post(AppConstant.USER_ALIAUTH_SUCCESS, user);//??????????????????
    }
}
