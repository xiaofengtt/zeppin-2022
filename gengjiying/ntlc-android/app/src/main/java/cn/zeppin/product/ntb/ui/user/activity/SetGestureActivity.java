package cn.zeppin.product.ntb.ui.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;
import cn.zeppin.product.ntb.widget.GestureSwitchButton;
import io.realm.Realm;

import static cn.zeppin.product.ntb.app.AppConstant.GESTURE_CHECK;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_GESTURE_LOGIN;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_GESTURE_RESET;
import static cn.zeppin.product.ntb.app.AppConstant.RequestCode.REQUEST_CODE_GESTURE_SET;

public class SetGestureActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.gestureSwitchBtn)
    GestureSwitchButton gestureSwitchBtn;
    @Bind(R.id.rl_reset)
    RelativeLayout rlReset;

    private boolean isOpen = false;
    private UserGesture userGesture = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_gesture;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("手势密码");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(GESTURE_CHECK, true);
                //选中
                startActivityForResult(GestureLoginActivity.class, bundle, REQUEST_CODE_GESTURE_RESET);
            }
        });

        setSwitch();

        gestureSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gestureSwitchBtn.isChecked()) {
                    startActivityForResult(CreateGestureActivity.class, REQUEST_CODE_GESTURE_SET);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(GESTURE_CHECK, true);
                    //选中
                    startActivityForResult(GestureLoginActivity.class, bundle, REQUEST_CODE_GESTURE_LOGIN);
                }
                gestureSwitchBtn.setChecked(!gestureSwitchBtn.isChecked());
            }
        });
        //签到开关按钮监听
        gestureSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    private void setSwitch() {
        User user = AppApplication.getInstance().getUser();
        if (user != null) {
            try {

                userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", user.getPhone());

                if (userGesture != null && userGesture.getGesturePwd() != null) {
                    //签到开关打开状态
                    gestureSwitchBtn.setChecked(userGesture.isOpen());
                    rlReset.setVisibility(userGesture.isOpen() ? View.VISIBLE : View.GONE);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstant.RequestCode.REQUEST_CODE_GESTURE_LOGIN) {//开关
                if (gestureSwitchBtn.isChecked()) {
                    gestureSwitchBtn.setChecked(false);
                    rlReset.setVisibility(gestureSwitchBtn.isChecked() ? View.VISIBLE : View.GONE);
                    closeGesture();
                }
            } else if (requestCode == REQUEST_CODE_GESTURE_SET) {//设置
                setSwitch();
            } else if (requestCode == REQUEST_CODE_GESTURE_RESET) {//重置
                startActivityForResult(CreateGestureActivity.class, REQUEST_CODE_GESTURE_SET);
            }
        }
    }

    private void closeGesture(){
        try {
            UserGesture userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", AppApplication.getInstance().getUser().getPhone());
            if (userGesture != null) {
                AppApplication.REALM_INSTANCE.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //先查找后得到UserGesture对象
                        UserGesture userGesture = AppApplication.REALM_INSTANCE.where(UserGesture.class).equalTo("phone", AppApplication.getInstance().getUser().getPhone()).findFirst();
                        userGesture.setErrorTime(0);
                        userGesture.setGesturePwd(null);
                        userGesture.setOpen(false);
                    }
                });
            } else {
                userGesture = new UserGesture(AppApplication.getInstance().getUser().getPhone(), null, 0, false);
                RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).add(userGesture);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
