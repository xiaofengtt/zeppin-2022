package cn.zeppin.product.ntb.ui.user.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geng.library.baseapp.AppManager;
import com.star.lockpattern.util.LockPatternUtil;
import com.star.lockpattern.widget.LockPatternView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.ui.main.activity.MainActivity;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;
import cn.zeppin.product.ntb.widget.SelectableRoundedImageView;
import io.realm.Realm;


public class GestureLoginActivity extends BaseActivity {
    @Bind(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @Bind(R.id.tvMessage)
    TextView messageTv;
    @Bind(R.id.btnForgetGesture)
    Button forgetGestureBtn;

    //    private ACache aCache;
    private static final long DELAYTIME = 600l;
    //    private byte[] gesturePassword;
    private static int errorTime = 0;
    private static final int MAX_ERROR_TIME = 5;
    @Bind(R.id.iv_head)
    SelectableRoundedImageView ivHead;
    @Bind(R.id.tvWarn)
    TextView tvWarn;
    @Bind(R.id.btnOtherAccount)
    Button btnOtherAccount;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;

    private UserGesture userGesture;
    private boolean isCheck = false;

    private long exitTime = 0;
    private String headUrl = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isCheck = bundle.getBoolean(AppConstant.GESTURE_CHECK, false);
        }
        llBottom.setVisibility(isCheck ? View.GONE : View.VISIBLE);
        User user = AppApplication.getInstance().getUser();
        if (user != null) {
            headUrl = user.getAliPhoto();
            //得到当前用户的手势密码
            try {
                userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", user.getPhone());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                userGesture = new UserGesture("", null, 0, true);
            }
        }
        Glide.with(this).load(headUrl).error(R.drawable.head_login).into(ivHead);

        lockPatternView.setOnPatternListener(patternListener);
        if (isCheck) {
            updateStatus(Status.ORIGINAL_DEFAULT);
        } else {
            updateStatus(Status.DEFAULT);
        }
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                if (LockPatternUtil.checkPattern(pattern, userGesture.getGesturePwd())) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        messageTv.setText(status.strId);
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                tvWarn.setVisibility(View.GONE);
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                tvWarn.setVisibility(View.VISIBLE);
                errorTime++;
                updateErrorTimeFirstByField();
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                messageTv.setText(messageTv.getText() + ", 您还可以再输入" + (MAX_ERROR_TIME - errorTime) + "次");
                if (errorTime >= MAX_ERROR_TIME) {
                    setEmptyPwdFirstByField();
                    startActivity(LoginActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    AppManager.getAppManager().finishOtherActivity(LoginActivity.class);
                }
                break;
            case CORRECT:
                tvWarn.setVisibility(View.GONE);
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                errorTime = 0;
                break;
        }
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        if (isCheck) {
            setResult(RESULT_OK);
            finish();
        } else {
            if (AppManager.getAppManager().isOpenedActivity(MainActivity.class)) {
                finish();
            } else {
                startActivity(MainActivity.class);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }
    }

    /**
     * 忘记手势密码（去账号登录界面）
     */
    @OnClick(R.id.btnForgetGesture)
    void forgetGesturePasswrod() {
        setEmptyPwdFirstByField();
        startActivity(LoginActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        AppManager.getAppManager().finishOtherActivity(LoginActivity.class);
    }

    @OnClick(R.id.btnOtherAccount)
    void otherAccount() {
        startActivity(LoginActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        AppManager.getAppManager().finishOtherActivity(LoginActivity.class);
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.alpha_70_black),
        ORIGINAL_DEFAULT(R.string.gesture_check, R.color.alpha_70_black),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.color_f53529),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.alpha_70_black);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        errorTime = 0;
//        AppApplication.REALM_INSTANCE.close();
    }

    private void updateErrorTimeFirstByField() {
        AppApplication.REALM_INSTANCE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //先查找后得到User对象
                UserGesture userGesture = AppApplication.REALM_INSTANCE.where(UserGesture.class).equalTo("phone", AppApplication.getInstance().getUser().getPhone()).findFirst();
                if (userGesture != null) {
                    userGesture.setErrorTime(errorTime);
                }
            }
        });
    }

    private void setEmptyPwdFirstByField() {
        AppApplication.REALM_INSTANCE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //先查找后得到User对象
                UserGesture userGesture = AppApplication.REALM_INSTANCE.where(UserGesture.class).equalTo("phone", AppApplication.getInstance().getUser().getPhone()).findFirst();
                if (userGesture != null) {
                    userGesture.setGesturePwd(null);
                    userGesture.setOpen(true);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isCheck) {
                return super.onKeyDown(keyCode, event);
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) { // System.currentTimeMillis()无论何时调用，肯定大于2000
                    showToastCenter("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    AppManager.getAppManager().finishAllActivity();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
