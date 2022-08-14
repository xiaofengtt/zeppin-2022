package com.foshanyigoushangcheng.www;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //加载启动界面
        setContentView(R.layout.activity_launch);
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
}
