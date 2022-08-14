package com.happyxmall.www.fcm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alibaba.fastjson.JSONObject;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.happyxmall.www.MainActivity;
import com.happyxmall.www.R;
import com.happyxmall.www.app.AppApplication;
import com.happyxmall.www.bean.ApiConstants;
import com.happyxmall.www.bean.FrontUser;
import com.happyxmall.www.fcm.bean.SnsFcmMessage;
import com.happyxmall.www.utils.JSONUtils;
import com.happyxmall.www.utils.PermissionsUtils;
import com.happyxmall.www.utils.ReviewUtil;
import com.happyxmall.www.utils.SpfUtil;
import com.happyxmall.www.utils.Utility;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class FcmMessagService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From Id: " + remoteMessage.getFrom());
//        if (remoteMessage.getData().size() > 0) {
////            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//        }
        try{
            if (remoteMessage.getNotification() != null) {
                logNotification(remoteMessage.getNotification());
                showNotification(remoteMessage);
                showReview(remoteMessage);
            } else {//topic推送信息 notification为null 需要从data中自行解析内容来封装
                Map<String, String> resultData = remoteMessage.getData();
                Intent intent = remoteMessage.toIntent();
                Log.i("push message data","resultData:"+resultData);
                showNotification(resultData, intent);
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: _________app first register push this id ___________________"+s);
    }


    /**
     * 调用h5实现，处理接受到的中奖消息，准备去评价
     */
    private void showReview(RemoteMessage remoteMessage){
        Map<String, String> data = remoteMessage.getData();
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        String message = "";
        if(notification != null){
            message = notification.getBody();
        }
        //解析data中的内容
        if(data != null){
            String noticeId = data.get("noticeId");
            String noticeType = data.get("noticeType");
            if(!Utility.checkStringNull(noticeType) && ApiConstants.NOTICE_TYPE_USER_WIN.equals(noticeType)){
                //用户中奖消息--弹出中奖框
                Intent intent = new Intent();
                intent.putExtra("message", message);
                intent.setAction("location.sendreview");

                Activity activity = AppApplication.getInstance().getCurrentActivity();
                if(PermissionsUtils.isNotificationEnabled(activity)){
                    //判断登录状态
                    //如果登录，则弹出提示
                    FrontUser fu = (FrontUser) SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET);
                    if(fu != null){
                        sendBroadcast(intent);
                    }
                }
            }
        }
        //mWebView.loadUrl("javascript:auth('"+su.getMobile()+"','"+su.getUuid()+"')");
    }

    /**
     * 自行实现
     * @param data
     */
    private void showReviewByself(Map<String, String> data){
        //解析data中的内容
        if(data != null){
            String noticeId = data.get("noticeId");
            String noticeType = data.get("noticeType");
            if(!Utility.checkStringNull(noticeType) && ApiConstants.NOTICE_TYPE_USER_WIN.equals(noticeType)){
                //用户中奖消息--弹出中奖框
                //出现去评价按钮
                Activity activity = AppApplication.getInstance().getCurrentActivity();
                Log.i("showReview",activity.getLocalClassName());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("Test text message", activity);
                    }
                });
            }
        }
        //mWebView.loadUrl("javascript:auth('"+su.getMobile()+"','"+su.getUuid()+"')");
    }

    private void showMessage(String message, Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle("Attention");
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //去评价
                ReviewUtil.review(activity, activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     *  显示通知
     * @param remoteMessage
     */

    /**
     * fcm要我们在前台时自己处理推送的通知，这里我们也用系统通知实现一下
     */
    private void showNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = remoteMessage.toIntent();
        if (notification == null || intent == null) {
            return;
        }

        //注册通知渠道
        final String channelID = "XShopping_02";
        String channelName ="XShopping_Android";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
        //通知标志
        final int tag = (int) System.currentTimeMillis();
        //通知点击跳转
        if (TextUtils.isEmpty(notification.getClickAction())) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            if (launchIntent == null) {
                return;
            } else {
                launchIntent.putExtras(intent);
                intent = launchIntent;
            }
        } else {
            intent.setAction(notification.getClickAction());
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, tag, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final Uri image = notification.getImageUrl();      //网络图片
        final Uri defaultSoundUri = getNotifactionSound();  //通知声音
        final int icon ;     //app图标
        final String title = notification.getTitle();//标题
        final String body = notification.getBody();//内容
        String iconName = notification.getIcon();
        if (TextUtils.isEmpty(iconName)) {
            icon = getNotificationIcon();
        }
        else {
            icon = getResource(iconName);
        }

        final  String colorString = notification.getColor();


        //加载网络图片
        if (image == null) {
            showNotification(this, channelID, tag, icon, title, body, null, defaultSoundUri,colorString, pendingIntent);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private void showNotification(Map<String, String> data, Intent intent){
        if(data != null && data.size() > 0){
            String mapString = data.get("default");
            if(!Utility.checkStringNull(mapString)){
                Map<String, Object> map = JSONUtils.json2map(mapString);
                if(map != null && map.size() > 0){
                    Log.i("push message data","map:"+map.containsKey("GCM"));
                    //"GCM":"{\"data\":{\"noticeId\":\"91e27091-9147-4bb4-b517-7ed78e38eed1\",\"noticeType\":\"system_publish\"},\"notification\":{\"body\":\"message test222\",\"click_action\":\"FCM_OPEN_MAIN_ACTIVITY\",\"sound\":\"default\",\"title\":\"XShopping\"}

                    String gcmString = map.get("GCM").toString();
                    Map<String, Object> gcmMap = JSONUtils.json2map(gcmString);

                    if(gcmMap != null && gcmMap.containsKey("notification")){
                        JSONObject object = JSONObject.parseObject(gcmMap.get("notification").toString());
                        SnsFcmMessage sfm = new SnsFcmMessage();
                        sfm.setTitle(object.getString("title"));
                        sfm.setBody(object.getString("body"));
                        sfm.setClick_action(object.getString("click_action"));
                        sfm.setSound(object.getString("sound"));
                        if(sfm != null){
                            //展示到系统通知栏
                            //注册通知渠道
                            final String channelID = "XShopping_02";
                            String channelName ="XShopping_Android";
                            //通知标志
                            final int tag = (int) System.currentTimeMillis();
                            //通知点击跳转
                            if (TextUtils.isEmpty(sfm.getClick_action())) {
                                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                                if (launchIntent == null) {
                                    return;
                                } else {
                                    launchIntent.putExtras(intent);
                                    intent = launchIntent;
                                }
                            } else {
                                intent.setAction(sfm.getClick_action());
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                            }
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            final PendingIntent pendingIntent = PendingIntent.getActivity(this, tag, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            final Uri image = null;      //网络图片
                            final Uri defaultSoundUri = getNotifactionSound();  //通知声音
                            final int icon ;     //app图标
                            final String title = sfm.getTitle();//标题
                            final String body = sfm.getBody();//内容
                            String iconName = "";
                            if (TextUtils.isEmpty(iconName)) {
                                icon = getNotificationIcon();
                            }
                            else {
                                icon = getResource(iconName);
                            }

                            final  String colorString = "";


                            //加载网络图片
                            if (image == null) {
                                showNotification(this, channelID, tag, icon, title, body, null, defaultSoundUri,colorString, pendingIntent);
                            } else {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                                    notificationChannel.enableLights(true);
                                    notificationChannel.setLightColor(Color.RED);
                                    notificationChannel.enableVibration(true);
                                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    manager.createNotificationChannel(notificationChannel);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private void showNotification(Context context, String channelID, int tag, int icon, String title, String msg, Bitmap image, Uri defaultSoundUri, String colorString, PendingIntent pendingIntent) {
        // 使用默认的通知
        // 优点：可以带有通知显示的时间，并且由系统更新显示几秒钟前，几分钟前，这个由系统在通知栏下拉时刷新
        //       高版本的android有提供小箭头可以点击展开通知，而自定义视图没办法通过代码展开通知。。。
        // 缺点：各厂商定制，还有Android系统版本不同的原因，通知样式不统一，还有兼容性问题（一言难尽）
        //       大图的缩放样式是centerCrop，导致除中间一部分外都被裁剪掉了，必须控制好图片的比例，这个比例也是一言难尽。。。。
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(icon)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true);
        if (!TextUtils.isEmpty(colorString)) {
            builder.setColor(Color.parseColor(colorString));
        }

        if (image == null) {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(msg));
        } else {
            builder.setLargeIcon(image)//设置大图标，这样通知收起时大图标就可以用来显示图片
                    .setStyle(//设置为大图样式
                            new NotificationCompat.BigPictureStyle()
                                    .setBigContentTitle(title)//这里如果设置通知标题，在华为荣耀8上会直接覆盖通知的标题设置，而三星5.0手机则在通知展开时替换为这个文本，收起时恢复通知标题，好在这里不需要有两个标题。。。
                                    .setSummaryText(msg)//三星5.0手机如果不设置，内容文本会变成空的。。。
                                    .bigPicture(image)
                                    .bigLargeIcon(null)//显示大图时，通知的大图标为空(三星5.0手机不起作用)
                    );
        }
        NotificationManagerCompat.from(this).notify(tag, builder.build());
    }

    /**
     * 设置通知app图标
     */
    public int getNotificationIcon() {
        return R.mipmap.ic_launcher;
    }

    /**
     * @return 用來設置Notifaction 的Icon
     */
    public Uri getNotifactionSound() {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return defaultSoundUri;
    }

    /**
     *  根据图片名字 获取图片的id
     * @param imageName
     * @return
     */
    public int  getResource(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName()); // 从drawable 文件夹里找
        //如果没有在"mipmap"下找到imageName,将会返回0
        if (resId==0){
            resId = getNotificationIcon();
        }
        return resId;
    }

    /**
     *   打印notification 里面的信息
     * @param notification
     */
    private void logNotification(RemoteMessage.Notification notification){
        Log.e(TAG, "logData: _________________________notification_______Title   " +notification.getTitle());
        Log.e(TAG, "logData: _________________________notification_______Body   " +notification.getBody());
        Log.e(TAG, "logData: _________________________notification_______Icon   " +notification.getIcon());
        Log.e(TAG, "logData: _________________________notification_______ClickAction  " +notification.getClickAction());
        Log.e(TAG, "logData: _________________________notification_______Color  " +notification.getColor());
        Log.e(TAG, "logData: _________________________notification_______Sound  " +notification.getSound());

    }
}
