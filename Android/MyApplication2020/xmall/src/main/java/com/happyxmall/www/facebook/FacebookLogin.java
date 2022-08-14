package com.happyxmall.www.facebook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.happyxmall.www.facebook.bean.UserInfoObject;
import com.happyxmall.www.utils.JSONUtils;
import com.happyxmall.www.utils.Utility;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

public class FacebookLogin {
    private Activity activity;
    private CallbackManager callbackManager;
    private FacebookListener facebookListener;
    private List<String> permissions;
    private LoginManager loginManager;

    public FacebookLogin(Activity activity) {
        this.activity = activity;
        //初始化facebook登录服务
        callbackManager = CallbackManager.Factory.create();
        getLoginManager().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // login success
                AccessToken accessToken = loginResult.getAccessToken();
                getLoginInfo(accessToken);
            }

            @Override
            public void onCancel() {
                //取消登录
                if (facebookListener != null) {
                    facebookListener.facebookLoginCancel();
                }

            }

            @Override
            public void onError(FacebookException error) {
                //登录出错
                if (facebookListener != null) {
                    facebookListener.facebookLoginFail(error.getMessage());
                }
            }
        });
        permissions = Arrays
                .asList("email", "public_profile");
    }

    public void sendUserInfoToJS(String userInfo, Activity activity){
        //用户中奖消息--弹出中奖框
        Intent intent = new Intent();
        intent.putExtra("userInfo", userInfo);
        intent.setAction("location.sendUserInfo");
    }

    /**
     * 登录
     */
    public void login() {
        //NATIVE_WITH_FALLBACK--默认值；使用fb-app登录 失败则打开默认浏览器登录
        //WEB_VIEW_ONLY -- 仅使用应用内置webview登录框登录
        getLoginManager().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY).logInWithReadPermissions(
                activity, permissions);
    }


    /**
     * 获取loginMananger
     *
     * @return
     */
    private LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    /**
     * 设置登录简体器
     *
     * @param facebookListener
     */
    public void setFacebookListener(FacebookListener facebookListener) {

        this.facebookListener = facebookListener;
    }

    public interface FacebookListener {

        /**
         * 登录成功
         *
         * @param object 用户信息 JSONObject
         */
        void facebookLoginSuccess(UserInfoObject object) throws JSONException;

        /**
         * 登录失败
         *
         * @param message 失败信息
         */
        void facebookLoginFail(String message);

        /**
         * 取消登录
         */
        void facebookLoginCancel();
    }

    /**
     * 获取登录信息
     *
     * @param accessToken
     */
    public void getLoginInfo(AccessToken accessToken) {
        UserInfoObject obj = new UserInfoObject();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            if (object != null) {
                //比如:1565455221565
                String id = object.optString("id");
                //比如：Zhang San
                String name = object.optString("name");
                //性别：比如 male （男）  female （女）
                String gender = object.optString("gender");
                //邮箱：比如：56236545@qq.com
                String emali = object.optString("email");

                //获取用户头像
//                JSONObject object_pic = object.optJSONObject("picture");
//                JSONObject object_data = object_pic.optJSONObject("data");
//                String photo = object_data.optString("url");
                String image = "";
                Profile profile = Profile.getCurrentProfile();
                if(profile != null){
                    Uri u = profile.getProfilePictureUri(200,200);
                    if(u != null){
                        image = u.toString();
                    }
                }

                obj.setImage(image);
                obj.setAccessToken(accessToken.getToken());
                obj.setUserID(accessToken.getUserId());
                obj.setNickname(name);
                obj.setCountryCode(Utility.getCountryCode());
                if (facebookListener != null) {
                    // 此处参数根需要自己修改
                    try {
                        facebookListener.facebookLoginSuccess(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
