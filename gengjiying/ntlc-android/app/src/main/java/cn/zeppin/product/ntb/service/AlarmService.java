package cn.zeppin.product.ntb.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;

import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.bean.UserGesture;
import cn.zeppin.product.ntb.receiver.AlarmReceiver;
import cn.zeppin.product.ntb.utils.RealmOperationHelper;

/**
 * 后台服务每10s秒检测一次，如果应用处于后台时间超过2分钟，则需要开始手势密码登录
 */
public class AlarmService extends Service {
    private static long entryTime = 0l;
    private int anHour = 10 * 1000;  // 每10s执行一次
    private UserGesture userGesture = null;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!AppApplication.isRunningForeground) {//处于后台
//            Log.d("GGGG", "后台！！");
            if (System.currentTimeMillis() - entryTime > 120000) {//两分钟
//                Log.d("AlarmService", "间隔：" + (System.currentTimeMillis() - entryTime));
                if (userGesture == null) {
                    try {
                        User user = AppApplication.getInstance().getUser();
                        if(user != null) {
                            String phone = user.getPhone();
                            if(!TextUtils.isEmpty(phone)){
                                userGesture = (UserGesture) RealmOperationHelper.getInstance(AppApplication.REALM_INSTANCE).queryByFieldFirst(UserGesture.class, "phone", phone);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (userGesture != null) {
                    if (userGesture.isOpen() && userGesture.getGesturePwd() != null) {
                        AppApplication.isGestureLogin = true;
                    }
                }
            }
        } else {
//            Log.d("GGGG", "前台！！");
            entryTime = System.currentTimeMillis();
            AppApplication.isGestureLogin = false;
        }

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

}
