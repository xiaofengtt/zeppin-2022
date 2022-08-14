package com.happyxmall.www.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


import com.happyxmall.www.R;
import com.happyxmall.www.bean.FrontUser;
import com.happyxmall.www.utils.SpfUtil;


public class JsOperator {
    private Context context;
    public JsOperator(Context context) {
        this.context = context;
    }

    private static final String openQQ = "mqqwpa://im/chat?chat_type=wpa&uin=";

    /**
     * 弹出消息对话框
     */
    @JavascriptInterface
    public void showDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 存储登录信息
     * 需要存储到本地文件中
     * @param loginInfo
     * @return
     */
    @JavascriptInterface
    public void auth(String loginInfo){
        try{
            Log.i("device screen test", "test demo first");
//            JSONObject loginInfo = new JSONObject();
//            loginInfo.put("Username", "YLD");
//            loginInfo.put("Password", "111");
//            System.out.println("mobile:"+mobile);
//            System.out.println("uuid:"+uuid);
            Log.i("device screen test", loginInfo);
            String uuid = loginInfo.substring(0,36);
            String mobile = loginInfo.substring(36);
            Log.i("device screen test", mobile);
            Log.i("device screen test", uuid);
            FrontUser su = new FrontUser();
            su.setMobile(mobile);
            su.setUuid(uuid);
            SpfUtil.putSerializable(this.context,SpfUtil.KEY_GET,su);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    /**
//     * 获取登录的用户名和密码
//     * 这里需要读取本地文件
//     * @return JSON格式的字符串
//     */
//    @JavascriptInterface
//    public String getLoginInfo(){
//        try{
//            JSONObject login = new JSONObject();
//            login.put("Username", "YLD");
//            login.put("Password", "111");
//
//            return login.toString();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    /**
     * 删除登录信息
     * 这里需要读取本地文件
     * @return JSON格式的字符串
     */
    @JavascriptInterface
    public void logout(){
        try {
            Log.i("device screen test", "test demo first");
            FrontUser su = (FrontUser)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET);
            Log.i("device screen test", "test demo first："+su.toString());
            if(su != null){
                Log.i("device screen test", su.getMobile());
                Log.i("device screen test", su.getUuid());
                SpfUtil.remove(this.context,SpfUtil.KEY_GET);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 调起QQ
     * @param qqid
     */
    @JavascriptInterface
    public void openQQ(String qqid){
        String url = openQQ+qqid;
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(in);
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    @JavascriptInterface
    public void openTel(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 存储登录信息
     * 需要存储到本地文件中
     * @param refererURL
     * @return
     */
    @JavascriptInterface
    public String wechat(String refererURL){
        try{
            Log.i("device screen testw", "test demo first");
            Log.i("device screen testm", refererURL);
            SpfUtil.putSerializable(this.context,SpfUtil.KEY_GET_REFERER,refererURL);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
