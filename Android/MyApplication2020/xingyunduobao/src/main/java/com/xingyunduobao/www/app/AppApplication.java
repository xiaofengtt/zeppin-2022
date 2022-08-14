package com.xingyunduobao.www.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;


/**
 */

public class AppApplication extends BaseApplication {
    public static boolean isRunningForeground = true;

    public int count = 0;
    public void isRunningForeground() {
        if (Build.VERSION.SDK_INT >= 14) {
            registerActivityLifecycleCallbacks(
                    new Application.ActivityLifecycleCallbacks() {
                        @Override
                        public void onActivityCreated(
                                Activity activity, Bundle bundle) {

                        }

                        @Override
                        public void onActivityStarted(Activity activity) {
                            if (count == 0) {
                                isRunningForeground = true;
                            }
                            count++;
                        }

                        @Override
                        public void onActivityResumed(Activity activity) {
                        }

                        @Override
                        public void onActivityPaused(Activity activity) {

                        }

                        @Override
                        public void onActivityStopped(Activity activity) {
                            count--;
                            if (count == 0) {
                                isRunningForeground = false;
                            }

                        }

                        @Override
                        public void onActivitySaveInstanceState(
                                Activity activity, Bundle bundle) {

                        }

                        @Override
                        public void onActivityDestroyed(Activity activity) {

                        }
                    });

        }
    }
}
