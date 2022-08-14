package cn.zeppin.product.ntb.ui.main.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.geng.library.baseapp.AppManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.ApiConstants;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.Advert;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.permission.PermissionResultCallBack;
import cn.zeppin.product.ntb.permission.PermissionUtil;
import cn.zeppin.product.ntb.ui.user.activity.GestureLoginActivity;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.zeppin.product.ntb.app.ApiConstants.RESOURCE_HOST;
import static cn.zeppin.product.ntb.permission.PermissionUtil.PERMISSION_REQUEST_CODE;


public class SplashActivity extends BaseActivity {

    @Bind(R.id.ll_splash)
    LinearLayout llSplash;
    private UserGesture userGesture = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决 覆盖安装后点Home键切出应用后再点击桌面图标返回导致应用重启问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            AppManager.getAppManager().finishAllActivity();
            return;
        }
    }

    @Override
    public void initView() {
        checkPermission();
        try {
            User user = AppApplication.getInstance().getUser();
            if (user != null) {
                userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", user.getPhone());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 渐变展示启动屏
     */
    private void startAnimation() {
        Animation aa = new Animation() {
        };
        aa.setDuration(1000);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onAnimationEnd(Animation arg0) {
                getAdvertisement();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });

        llSplash.startAnimation(aa);
    }

    private void checkPermission() {
//        final String[] permissionsArr = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE
//        };
        final String[] permissionsArr = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtil.getInstance().request(this, permissionsArr, new PermissionResultCallBack() {
            @Override
            public void onPermissionGranted() {
                startAnimation();
            }

            @Override
            public void onPermissionGranted(String... permissions) {
                Log.i("permissions", permissions.toString());
            }

            @Override
            public void onPermissionDenied(String... permissions) {
                Log.i("permissions", permissions.toString());
            }

            @Override
            public void onRationalShow(String... permissions) {
                Log.i("permissions", permissions.toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllGranted = true;
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                startAnimation();
            } else {
                // Permission Denied
                showToastCenter("请授权后才可正常启动");
                AppManager.getAppManager().finishAllActivity();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getAdvertisement() {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(ApiConstants.ADVERT)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    doJump();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String r = response.body().string();
                        JSONObject respJson = new JSONObject(r);
                        JSONObject dataJson = respJson.getJSONObject("data");
                        if (dataJson != null) {
                            Advert advert = JSON.parseObject(dataJson.toString(), Advert.class);
                            if (advert != null && !TextUtils.isEmpty(advert.getPictureUrl())) {
                                Bundle bundle = new Bundle();
                                bundle.putString(AppConstant.ADVERT_URL, RESOURCE_HOST + advert.getPictureUrl());
                                startActivity(AdvertisementActivity.class, bundle);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                return;
                            }
                        }
                        doJump();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        doJump();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            doJump();
        }
    }


    private void doJump() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //已登录状态
                String uuid = AppApplication.getInstance().getUuid();
                if (!TextUtils.isEmpty(uuid)) {
                    //开关是否打开
                    if (userGesture != null && userGesture.isOpen() && userGesture.getGesturePwd() != null) {
                        startActivity(GestureLoginActivity.class);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }

                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }
        });
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
