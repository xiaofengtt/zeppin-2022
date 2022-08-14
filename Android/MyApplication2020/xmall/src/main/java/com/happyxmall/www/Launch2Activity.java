package com.happyxmall.www;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.happyxmall.www.app.BaseActivity;
import com.happyxmall.www.bean.FrontUser;
import com.happyxmall.www.ui.contract.LoginContract;
import com.happyxmall.www.ui.model.LoginModel;
import com.happyxmall.www.ui.presenter.LoginPresenter;
import com.happyxmall.www.utils.SpfUtil;
import com.happyxmall.www.utils.TUtil;


public class Launch2Activity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        checkin();
    }

    @Override
    public void initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //加载启动界面
        setContentView(R.layout.activity_launch);
        Integer time = 3500;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Launch2Activity.this.finish();
            }
        }, time);
    }

    private void checkin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //檢查是否安裝了GooglePlay,安裝了則設置token,未安裝則不設置,默認為空
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("TAG", "getInstanceId failed", task.getException());
                                        return;
                                    }
                                    // Get new Instance ID token
                                    InstanceIdResult result = task.getResult();
                                    if (result != null) {
                                        String token = result.getToken();
                                        //保存FCM token
                                        //ABLESharedPreferencesUtils.setFcmToken(xxx.this, token);

                                        Log.i("params println", "deviceToken：" + token);
                                        //先判断是否与local-storage的deviceToken一致，若一致，则不需要再次注册该设备
                                        boolean flag = true;
                                        String deviceToken = (String) SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE);
                                        if(deviceToken != null && !"".equals(deviceToken)){
                                            if(token.equals(deviceToken)){
                                                flag = false;
                                            }
                                        }
                                        //否则调用注册接口，将设备重新注册
                                        if(flag){
                                            //获取local-storage的用户信息，若用户信息存在，则将用户信息附到设备注册上|| 另外，需要在用户登录时，也重新调用一次checkin
                                            String uuid = "";
                                            FrontUser fu = (FrontUser)SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET);
                                            if(fu != null){
                                                Log.i("params println", "FrontUser："+fu.toString());
                                                uuid = fu.getUuid();
                                            }
                                            String uniqueToken = (String) SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE_UNIQUE);//缓存存储
                                            mPresenter.checkin(uniqueToken,uuid,token);
                                            SpfUtil.putSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE,token);
                                        }
                                    }
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
