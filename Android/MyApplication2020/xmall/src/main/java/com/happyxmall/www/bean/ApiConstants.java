package com.happyxmall.www.bean;

import android.util.Log;

import com.happyxmall.www.utils.DESUtil;
import com.happyxmall.www.utils.EncryptUtil;
import com.happyxmall.www.utils.MD5Util;
import com.happyxmall.www.utils.TimeUtil;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * class:
 */

public class ApiConstants {
//    public static final String LoadUrl = "http://www.baidu.com";
//    public static final String ObjectId = "5d809409d5de2b008a3f4de4";
//    public static final String HOSTURL = "http://192.168.1.120:28080/";
    public static final String HOSTURL = "https://api.happyxmall.com/";
    public static final String RefererURL = "https://www.happyxmall.com";
    public static final String route_home = "/home";
    public static final String route_categories = "/categories";
    public static final String route_record = "/lottery-soon-list";
    public static final String route_story = "/show-list";
    public static final String route_account = "/account";
    public static final String route_other = "other";
    public static final String route_story_user = "/show-list-user";
    public static final String ANDROID = "02";
    public static final String KEY_FRONT_MD5 = "8279a48ec7c8a6303bde3f979ce77a9d";

    public static final String NOTICE_TYPE_USER_WIN = "user_win";

    /**
     * register device token
     *
     * @param uuid
     * @param timestamp
     * @return
     */
    public static String getCheckinToken(String uuid, String timestamp) {
        Log.i("timestamp", "getCheckinToken：" + TimeUtil.timeFormat(timestamp + ""));
//        return EncryptUtil.getBase64(ANDROID + timestamp + EncryptUtil.getDes(KEY_FRONT_MD5+timestamp+uuid));
        return EncryptUtil.getBase64(ANDROID + timestamp + DESUtil.encrypt("",KEY_FRONT_MD5+timestamp+uuid));
    }
}
