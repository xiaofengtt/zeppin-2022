package cn.zeppin.product.ntb.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.geng.library.baseapp.AppManager;
import com.geng.library.baseapp.BaseApplication;
import com.geng.library.commonutils.LogUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.utils.SpfUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by geng on 17/8/28.
 * class:
 */

public class AppApplication extends BaseApplication {
    //配置各大开放平台信息 DEBUG版
    {
        PlatformConfig.setWeixin("wxd91a9c85747f2f7f", "4846ceadae1f895fa9903846ec0c882b");
        PlatformConfig.setSinaWeibo("828478288", "8b38124d35cb1d1ea2ccd42d16c87345", "http://sns.whalecloud.com/sina2/callback");
//        PlatformConfig.setQQZone("1105768868", "dercsd0zL5qibMeQ");
        PlatformConfig.setQQZone("1104620628", "bFXB8eqo4icRirod");
    }

    private User user;
    private String uuid;
    private boolean isWebmarketSwitch = false;

    private static AppApplication mInstance;
    public static Realm REALM_INSTANCE;
    public static boolean isGestureLogin = false;
    public static boolean isRunningForeground = true;

    public static AppApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        isRunningForeground();
        mInstance = this;
        super.onCreate();
        LogUtils.DEBUG_ENABLE = false;
//        initTypeface();
        Fresco.initialize(this);
        uuid = (String) SpfUtil.get(this, AppConstant.UUID, "");
        initUser();

        //bugly
//        CrashReport.initCrashReport(getApplicationContext(), "8a1c06f3b1", false);

//        Intent i = new Intent(this, TimeService.class);
//        startService(i);//开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        MultiDex.install(this);//解决 友盟分析 问题
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("ntRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        REALM_INSTANCE = Realm.getInstance(config);

    }

    private void initUser() {
        Object object = SpfUtil.getSerializable(this, User.class.getName());
        if (object != null) {
            setUser((User) object);
        } else {
            setUser(null);
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        SpfUtil.putSerializable(this, User.class.getName(), user);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isWebmarketSwitch() {
        return isWebmarketSwitch;
    }

    public void setWebmarketSwitch(boolean webmarketSwitch) {
        isWebmarketSwitch = webmarketSwitch;
    }

    public void removeLocalInfo() {
        setUser(null);
        setUuid(null);
        SpfUtil.clear(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppManager.getAppManager().finishAllActivity();
        MobclickAgent.onKillProcess(this);
        System.exit(0);
        AppApplication.REALM_INSTANCE.close();
    }

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
