package cn.zeppin.shebaoxiong.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.zeppin.shebaoxiong.bean.ShbxUser;
import cn.zeppin.shebaoxiong.utils.Base64Util;
import cn.zeppin.shebaoxiong.utils.SpfUtil;

public class JsOperator {
    private Context context;

    public JsOperator(Context context) {
        this.context = context;
    }

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
            Log.i("设备屏幕", "调试一下啊");
//            JSONObject loginInfo = new JSONObject();
//            loginInfo.put("Username", "YLD");
//            loginInfo.put("Password", "111");
//            System.out.println("mobile:"+mobile);
//            System.out.println("uuid:"+uuid);
            Log.i("设备屏幕", loginInfo);
            String mobile = loginInfo.substring(0,11);
            String uuid = loginInfo.substring(11);
            Log.i("设备屏幕", mobile);
            Log.i("设备屏幕", uuid);
            ShbxUser su = new ShbxUser();
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
            Log.i("设备屏幕", "调试一下啊");
            ShbxUser su = (ShbxUser)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET);
            Log.i("设备屏幕", "调试一下啊："+su.toString());
            if(su != null){
                Log.i("设备屏幕", su.getMobile());
                Log.i("设备屏幕", su.getUuid());
                SpfUtil.remove(this.context,SpfUtil.KEY_GET);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
