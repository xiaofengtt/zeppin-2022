package cn.zeppin.score;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.zeppin.score.app.JsOperator;
import cn.zeppin.score.bean.FrontUser;
import cn.zeppin.score.listener.DeviceScreenListener;
import cn.zeppin.score.utils.SpfUtil;

public class MainActivity extends Activity {

    //    private static final String LINK_URL = "http://192.168.1.233:8080/NTB/shbxApp/index.html";
//    private static final String LINK_URL = "https://api.score.com/shbxApp/index.html";
    //    private static final String LINK_URL = "https://account.qicaibao.vip/shbxApp/index.html";
    private static final String LINK_URL = "file:///android_asset/index.html";
    private static final String LINK_URL_RETURN_ALIPAY = "file:///android_asset/index.html#/my/alipay";
    private WebView mWebView;
    private DeviceScreenListener listener = new DeviceScreenListener(this);
    public static boolean isActive; //全局变量
    private static boolean isExit = false;
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mWebView=(WebView)findViewById(R.id.webView);

        //实现自由跨域
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = mWebView.getSettings().getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(mWebView.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //设置可自由缩放网页
        //browser.getSettings().setSupportZoom(true);
        //browser.getSettings().setBuiltInZoomControls(true);
        // 首先设置支持JS脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        //允许下拉
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
//        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
//        mWebView.getSettings().setUseWideViewPort(true);

//        mWebView.setInitialScale(70);
//        mWebView.setHorizontalScrollbarOverlay(true);

        // 支持Js在当前App打开应用，当页面跳转的时候依旧在当前的WebView之中
        //mWebView.setWebViewClient(new WebViewClient());
        // 传入当前我们需要打开的网址(基于http的，也有的是https，不过后台给的一般都没有问题),记得添加网络权限
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebChromeClient(webChromeClient);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        mWebView.setWebViewClient(webViewClient);
        this.initializeWebView();
        mWebView.setHorizontalScrollBarEnabled(false); // 水平不显示滚动条
        mWebView.setVerticalScrollBarEnabled(false); //

        mWebView.loadUrl(LINK_URL);

//        //注册监听
//        listener.register(new DeviceScreenListener.ScreenStateListener() {
//            @Override
//            public void onScreenOn() {
//                Log.i("设备屏幕", "屏幕点亮");
//            }
//
//            @Override
//            public void onScreenOff() {
//                Log.i("设备屏幕", "屏幕锁定");
//            }
//
//            @Override
//            public void onUserPresent() {//调用JS方法登录
//                Log.i("设备屏幕", "屏幕解锁且可以正常使用");
//                //获取存储内容
//                getLoginInfo();
//            }
//        });
    }

    @Override
    protected void onResume() {
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
//            final Activity context = MainActivity.this;
//            if(url.startsWith("alipays:") || url.startsWith("alipay")){
//                try {
//                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
//                } catch (Exception e) {
//                    new AlertDialog.Builder(context)
//                            .setMessage("未检测到支付宝客户端，请安装后重试。")
//                            .setPositiveButton("立即安装", new DialogInterface.OnClickListener(){
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
//                                    context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
//                                }
//                            }).setNegativeButton("取消", null).show();
//                }
//                return true;
//            }

            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void onPageFinished(WebView view, String url) {
//            if(url.indexOf("#/my/alipay") > -1){
//                view.loadUrl(LINK_URL_RETURN_ALIPAY);
//            }
            super.onPageFinished(view, url);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 1500){
                Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                return true;
            }

            //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
            if(keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
                mWebView.goBack();
                return true;
            }else{
                onBackPressed();
            }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
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
//        listener.unregister();

        super.onDestroy();
    }
}
