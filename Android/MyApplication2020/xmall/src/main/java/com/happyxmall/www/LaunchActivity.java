package com.happyxmall.www;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.happyxmall.www.utils.DeviceUtil;
import com.happyxmall.www.utils.SpfUtil;
import com.happyxmall.www.utils.Utility;

public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //加载启动界面
        setContentView(R.layout.activity_launch);
        checkUniqueToken();
        Integer time = 500;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(LaunchActivity.this, Launch2Activity.class));
                //startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                Intent intentIndex = new Intent(LaunchActivity.this, Launch2Activity.class);
                Intent intentAdvertisement = new Intent(LaunchActivity.this, MainActivity.class);
                Intent[] intents = new Intent[2];
                intents[1] = intentIndex;
                intents[0] = intentAdvertisement;
                startActivities(intents);

                LaunchActivity.this.finish();
            }
        }, time);
    }

    private void checkUniqueToken(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueToken = DeviceUtil.readDeviceID(LaunchActivity.this);//local-storage
                String spToken = (String) SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE_UNIQUE);//缓存存储
                if(!Utility.checkStringNull(spToken)){
                    if(!Utility.checkStringNull(uniqueToken) || !spToken.equals(uniqueToken)){//以缓存中的数据为主
                        DeviceUtil.saveDeviceID(spToken,LaunchActivity.this);
                    }
                } else {//缓存中无数据-则以local-storage为主，更新缓存数据
                    if(!Utility.checkStringNull(uniqueToken)){
                        SpfUtil.putSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE_UNIQUE,uniqueToken);
                    } else {//无缓存-也无local-storage，生成新的token存储
                        String token = DeviceUtil.getUUID();
                        SpfUtil.putSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE_UNIQUE,token);
                        DeviceUtil.saveDeviceID(token,LaunchActivity.this);
                    }
                }
                Log.i("params println", "uniqueToken："+spToken);
            }
        }).start();
    }
}
