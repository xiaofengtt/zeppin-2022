package com.happyxmall.www.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        initGlobeActivity();
    }

    public static Context getAppContext() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initGlobeActivity(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // 此处记录最后的activity
                mActivity = activity;
//                Log.e(TAG,activity+"onActivityCreated");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                Log.e(TAG,activity+"onActivityDestroyed");
            }

            /** Unused implementation **/
            @Override
            public void onActivityStarted(Activity activity) {
//                Log.e(TAG,"onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
//                Log.e(TAG,"onActivityResumed");
            }
            @Override
            public void onActivityPaused(Activity activity) {
//                Log.e(TAG,"onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
//                Log.e(TAG,"onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
        });
    }

    public static BaseApplication getInstance(){
        return baseApplication;
    }

    /**
     *公开方法，外部可通过 MyApplication.getInstance().getCurrentActivity() 获取到当前最上层的activity
     */
    public Activity getCurrentActivity() {
        return mActivity;
    }

}
