package cn.zeppin.product.ntb.ui.main.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.ui.user.activity.GestureLoginActivity;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;

/**
 * 广告页
 */
public class AdvertisementActivity extends BaseActivity {

    @Bind(R.id.iv_advertisement)
    ImageView ivAdvertisement;
    @Bind(R.id.tv_timer)
    TextView tvTimer;
    @Bind(R.id.fl_advertisement)
    FrameLayout flAdvertisement;
    private String advert_url;

    private CountDownTimer timer = new CountDownTimer(4000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvTimer.setText(millisUntilFinished / 1000 + "s 跳过");
        }

        @Override
        public void onFinish() {
            tvTimer.setText("0s 跳过");
            doJump();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_advertisement;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        timer.start();
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doJump();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            advert_url = bundle.getString(AppConstant.ADVERT_URL);
        }
        Glide.with(this).load(advert_url).error(R.drawable.head_login).into(ivAdvertisement);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    private void doJump() {
        //已登录状态
        String uuid = AppApplication.getInstance().getUuid();
        if (!TextUtils.isEmpty(uuid)) {
            UserGesture userGesture = null;
            User user = AppApplication.getInstance().getUser();
            if (user != null) {
                try {
                    userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", user.getPhone());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            //开关是否打开
            if (userGesture != null && userGesture.isOpen() && userGesture.getGesturePwd() != null) {
                startActivity(GestureLoginActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            } else {
                startActivity(MainActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

        } else {
            startActivity(MainActivity.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
