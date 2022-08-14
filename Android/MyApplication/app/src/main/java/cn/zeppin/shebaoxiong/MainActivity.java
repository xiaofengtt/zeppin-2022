package cn.zeppin.shebaoxiong;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import cn.zeppin.shebaoxiong.app.JsOperator;
import cn.zeppin.shebaoxiong.bean.ShbxUser;
import cn.zeppin.shebaoxiong.listener.DeviceScreenListener;
import cn.zeppin.shebaoxiong.utils.Base64Util;
import cn.zeppin.shebaoxiong.utils.SpfUtil;
import cn.zeppin.shebaoxiong.utils.Utility;

public class MainActivity extends Activity {

    private static final String LINK_URL = "https://api.shebaoxiong.com/shbxApp/index.html";
//    private static final String LINK_URL = "https://account.qicaibao.vip/shbxApp/index.html";
    private WebView mWebView;
    //private ActionBar actionBar;
    private TopBar topBar;
    private DeviceScreenListener listener = new DeviceScreenListener(this);
    public static boolean isActive; //全局变量
    int screenWidth;// 窗口的宽度
    int screenHeight;// 窗口高度


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();  //隐藏ActionBar
            //actionBar.show();  //显示ActionBar

            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            //logo显示与隐藏
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
        }*/

        mWebView=(WebView)findViewById(R.id.webView);
        mWebView.loadUrl(LINK_URL);
        //设置可自由缩放网页
        //browser.getSettings().setSupportZoom(true);
        //browser.getSettings().setBuiltInZoomControls(true);
        // 首先设置支持JS脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        // 支持Js在当前App打开应用，当页面跳转的时候依旧在当前的WebView之中
        //mWebView.setWebViewClient(new WebViewClient());
        // 传入当前我们需要打开的网址(基于http的，也有的是https，不过后台给的一般都没有问题),记得添加网络权限
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebChromeClient(webChromeClient);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        mWebView.setWebViewClient(webViewClient);
        this.initializeWebView();

        topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnLeftButtonClickListener(new TopBarListener() {
            @Override
            public void leftButtonClick() {
                mWebView.goBack();
            }
        });

        //注册监听
        listener.register(new DeviceScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.i("设备屏幕", "屏幕点亮");
            }

            @Override
            public void onScreenOff() {
                Log.i("设备屏幕", "屏幕锁定");
            }

            @Override
            public void onUserPresent() {//调用JS方法登录
                Log.i("设备屏幕", "屏幕解锁且可以正常使用");
                //获取存储内容
                getLoginInfo();
            }
        });

        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);// 取得窗口属性
        this.screenWidth = dm.widthPixels;// 窗口的宽度
        this.screenHeight = dm.heightPixels;// 窗口高度
        //设置title字号
        topBar.setLeftButtonHide(true);
        topBar.setTitleSize(Utility.adjustFontSize(screenWidth,screenHeight));
    }

    @Override
    protected void onResume() {
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Log.i("ACTIVITY", "程序从后台唤醒");
            getLoginInfo();
        }
        super.onResume();
    }

    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onReceivedTitle(WebView view, String title) {

            super.onReceivedTitle(view, title);
        }
    };

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //actionBar.setTitle(view.getTitle());
            //设置title内容
            topBar.setTitle(view.getTitle());

            if("社保熊".equals(view.getTitle())){
                topBar.setLeftButtonHide(true);
            }else{
                topBar.setLeftButtonHide(false);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }else{
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mWebView.goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        //actionBar.setTitle();
        super.onActionModeFinished(mode);
    }

    private void initializeWebView(){
        mWebView.addJavascriptInterface(new JsOperator(MainActivity.this),
                "JavascriptInterface");
    }

    /**
     * 调用JS方法获取登录状态
     */
    private void getLoginInfo(){
        try {
            ShbxUser su = (ShbxUser)SpfUtil.getSerializable(MainActivity.this,SpfUtil.KEY_GET);
            if(su != null){
                Log.i("mobile", su.getMobile());
                Log.i("uuid", su.getUuid());
                //调用JS方法
                mWebView.loadUrl("javascript:auth('"+su.getMobile()+"','"+su.getUuid()+"')");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * APP是否处于前台唤醒状态
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
    @Override
    protected void onStop() {
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
            Log.i("ACTIVITY", "程序进入后台");
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if( mWebView!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        /**
         * 注销监听
         */
        listener.unregister();

        super.onDestroy();
    }
}
