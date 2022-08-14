package cn.zeppin.product.ntb.ui.user.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.baseapp.AppManager;
import com.star.lockpattern.util.LockPatternUtil;
import com.star.lockpattern.widget.LockPatternIndicator;
import com.star.lockpattern.widget.LockPatternView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.ui.main.activity.MainActivity;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;
import io.realm.Realm;

public class CreateGestureActivity extends BaseActivity {
    @Bind(R.id.lockPatterIndicator)
    LockPatternIndicator lockPatternIndicator;
    @Bind(R.id.messageTv)
    TextView messageTv;
    @Bind(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @Bind(R.id.resetBtn)
    Button resetBtn;
    @Bind(R.id.skipBtn)
    Button skipBtn;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private List<LockPatternView.Cell> mChosenPattern = null;
    //    private ACache aCache;
    private static final long DELAYTIME = 600L;
    private static final String TAG = "CreateGestureActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_gesture;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("设置手势密码");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skipBtn.getVisibility() == View.GONE) {
                    if (AppManager.getAppManager().isOpenedActivity(MainActivity.class)) {
                        finish();
                    } else {
                        startActivity(MainActivity.class);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                } else {
                    toSkip();
                }
            }
        });
//        aCache = ACache.get(CreateGestureActivity.this);
        lockPatternView.setOnPatternListener(patternListener);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Boolean hideSkip = bundle.getBoolean(AppConstant.GESTURE_SKIP_HIDE, true);
            skipBtn.setVisibility(hideSkip ? View.GONE : View.VISIBLE);
            viewLine.setVisibility(hideSkip ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 手势监听
     */
    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
            //updateStatus(Status.DEFAULT, null);
            lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            //Log.e(TAG, "--onPatternDetected--");
            if (mChosenPattern == null && pattern.size() >= 4) {
                mChosenPattern = new ArrayList<LockPatternView.Cell>(pattern);
                updateStatus(Status.CORRECT, pattern);
            } else if (mChosenPattern == null && pattern.size() < 4) {
                updateStatus(Status.LESSERROR, pattern);
            } else if (mChosenPattern != null) {
                if (mChosenPattern.equals(pattern)) {
                    updateStatus(Status.CONFIRMCORRECT, pattern);
                } else {
                    updateStatus(Status.CONFIRMERROR, pattern);
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     * @param pattern
     */
    private void updateStatus(Status status, List<LockPatternView.Cell> pattern) {
        messageTv.setTextColor(getResources().getColor(status.colorId));
        messageTv.setText(status.strId);
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CORRECT:
                updateLockPatternIndicator();
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case LESSERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CONFIRMERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CONFIRMCORRECT:
                saveChosenPattern(pattern);
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                setLockPatternSuccess();
                break;
        }
    }

    /**
     * 更新 Indicator
     */
    private void updateLockPatternIndicator() {
        if (mChosenPattern == null)
            return;
        lockPatternIndicator.setIndicator(mChosenPattern);
    }

    /**
     * 成功设置了手势密码(跳到首页)
     */
    private void setLockPatternSuccess() {
        setResult(RESULT_OK);
//        Toast.makeText(this, "create gesture success", Toast.LENGTH_SHORT).show();
        if (AppManager.getAppManager().isOpenedActivity(MainActivity.class)) {
            finish();
        } else {
            startActivity(MainActivity.class);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    /**
     * 保存手势密码
     */
    private void saveChosenPattern(List<LockPatternView.Cell> cells) {
        final byte[] bytes = LockPatternUtil.patternToHash(cells);
//        List gpList = new ArrayList();

//        JSONArray jsonArray = aCache.getAsJSONArray(AppConstant.ARRAY_GESTURE_PASSWORD);
//        if (jsonArray == null) {
//            jsonArray = new JSONArray();
//        }
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(AppApplication.getInstance().getUuid(), EncryptUtil.getBase64(new String(bytes)));
//            jsonArray.put(jsonObject);
//            aCache.put(AppConstant.ARRAY_GESTURE_PASSWORD, jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        try {
            UserGesture userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", AppApplication.getInstance().getUser().getPhone());
            if (userGesture != null) {
                AppApplication.REALM_INSTANCE.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //先查找后得到UserGesture对象
                        UserGesture userGesture = AppApplication.REALM_INSTANCE.where(UserGesture.class).equalTo("phone", AppApplication.getInstance().getUser().getPhone()).findFirst();
                        userGesture.setErrorTime(0);
                        userGesture.setGesturePwd(bytes);
                        userGesture.setOpen(true);
                    }
                });
            } else {
                userGesture = new UserGesture(AppApplication.getInstance().getUser().getPhone(), bytes, 0, true);
                RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).add(userGesture);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    private enum Status {
        //默认的状态，刚开始的时候（初始化状态）
        DEFAULT(R.string.create_gesture_default, R.color.alpha_70_black),
        //第一次记录成功
        CORRECT(R.string.create_gesture_correct, R.color.alpha_70_black),
        //连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
        LESSERROR(R.string.create_gesture_less_error, R.color.color_f53529),
        //二次确认错误
        CONFIRMERROR(R.string.create_gesture_confirm_error, R.color.color_f53529),
        //二次确认正确
        CONFIRMCORRECT(R.string.create_gesture_confirm_correct, R.color.alpha_70_black);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }

    /**
     * 重新设置手势
     */
    public void resetLockPattern(View view) {
        mChosenPattern = null;
        lockPatternIndicator.setDefaultIndicator();
        updateStatus(Status.DEFAULT, null);
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
    }

    public void skip(View view) {
        toSkip();
    }

    private void toSkip() {
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

        if (AppManager.getAppManager().isOpenedActivity(MainActivity.class)) {
            finish();
        } else {
            startActivity(MainActivity.class);
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppApplication.REALM_INSTANCE.close();
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
