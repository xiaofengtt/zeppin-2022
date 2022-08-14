//package com.kingkong.common.util;
//
//import cn.jiguang.common.ClientConfig;
//import cn.jiguang.common.resp.APIConnectionException;
//import cn.jiguang.common.resp.APIRequestException;
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.push.PushResult;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.notification.AndroidNotification;
//import cn.jpush.api.push.model.notification.IosAlert;
//import cn.jpush.api.push.model.notification.IosNotification;
//import cn.jpush.api.push.model.notification.Notification;
//import com.kingkong.common.config.JPushProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  推送平台的工具类
// */
//@Slf4j
//@Component
//public class JPushClientUtil {
//
//    private static boolean isInit=false;
//    private static List<JPushClient> list=new ArrayList<JPushClient>();
//
//    @Autowired
//    private JPushProperties jPushPropertiesAutowired;
//    private static JPushProperties jPushProperties;
//
//    @PostConstruct
//    public void init() {
//        jPushProperties = this.jPushPropertiesAutowired;
//    }
//
//
//        // 初始化推送对象
//    public static void  getJpushClient(){
//        if(!isInit){
//            ClientConfig config=ClientConfig.getInstance();
//            // config.setApnsProduction(true);  苹果的设置为生产环境
//            int count= Integer.parseInt(jPushProperties.getTotal());
//            String appKey[]=jPushProperties.getAppkey().split("#");
//            String masterKey[]=jPushProperties.getSecret().split("#");
//            for (int i = 0; i <count ; i++) {
//                JPushClient jpushClient = new JPushClient(masterKey[i], appKey[i], null, config);
//                list.add(jpushClient);
//            }
//            isInit=true;
//        }
//
//    }
//
//
//    /**
//     *
//     * @param flag:1为推送全部，0：为一个或者部分
//     * @param alias：flag=0时，用户的userId数组
//     * @param alert：通知内容
//     * @param status: 1：展示窗口，0：不展示窗口
//     * @param title:标题
//     */
//    public static void sendPush(String flag,String alias[],String alert,String status,String title){
//        getJpushClient();
//
//        PushPayload payload =buildPushObject_all_alias_alert_info(flag,alias,alert,status,title);
//        for (int i = 0; i <list.size() ; i++) {
//            try {
//
//                JPushClient tempJpushClient=list.get(i);
//                PushResult result =tempJpushClient.sendPush(payload);
//                log.info("Got result - " + result);
//
//            } catch (APIConnectionException e) {
//                // Connection error, should retry later
//                log.error("Connection error, should retry later", e);
//
//            } catch (APIRequestException e) {
//                // Should review the error, and fix the request
//                //log.error("Should review the error, and fix the request", e);
//                log.info("HTTP Status: " + e.getStatus());
//                log.info("Error Code: " + e.getErrorCode());
//                log.info("Error Message: " + e.getErrorMessage());
//            } catch (Exception e) {
//                log.error("sendPush Error ,errorInfo: {},alert  :{}", e,alert);
//            }
//        }
//
//
//    }
//
//    /** 2018-05-18号新增
//     * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
//     * @param flag:1为全部推送，0：别名推送
//     * @param alias：当flag=0时，推送别名
//     * @param alert:推送内容
//     * @param status: 1：展示窗口，0：不展示窗口
//     * @param title:标题
//     * @return
//     */
//    public static PushPayload buildPushObject_all_alias_alert_info(String flag,String [] alias,String alert,String status,String title ) {
//        PushPayload.Builder builder=PushPayload.newBuilder().setPlatform(Platform.all());
//        if("1".equals(flag)){
//            builder.setAudience(Audience.all());
//        }else{
//            builder.setAudience(Audience.alias(alias));
//        }
//        AndroidNotification androidNotification=AndroidNotification.newBuilder().setTitle(title).setAlert(alert).addExtra("status",status).build();
//        IosAlert iosAlert=IosAlert.newBuilder().setTitleAndBody(title,null,alert).build();
//        IosNotification iosNotification=IosNotification.newBuilder().setAlert(iosAlert).addExtra("status",status).build();
//        builder.setNotification(Notification.newBuilder().addPlatformNotification(androidNotification)
//                .addPlatformNotification(iosNotification)
//                .build() );
//
//        return builder.build();
//    }
//
//}
