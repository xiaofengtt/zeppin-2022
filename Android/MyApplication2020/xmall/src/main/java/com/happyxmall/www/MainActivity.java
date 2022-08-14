package com.happyxmall.www;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.happyxmall.www.app.Api;
import com.happyxmall.www.app.BaseActivity;
import com.happyxmall.www.baserx.RxSchedulers;
import com.happyxmall.www.bean.ApiConstants;
import com.happyxmall.www.bean.FrontUser;
import com.happyxmall.www.bean.FrontUserVO;
import com.happyxmall.www.bean.ResultData;
import com.happyxmall.www.bean.VersionInfo;
import com.happyxmall.www.facebook.FacebookLogin;
import com.happyxmall.www.facebook.bean.UserInfoObject;
import com.happyxmall.www.listener.DeviceScreenListener;
import com.happyxmall.www.ui.contract.LoginContract;
import com.happyxmall.www.ui.model.LoginModel;
import com.happyxmall.www.ui.presenter.LoginPresenter;
import com.happyxmall.www.utils.ConfigUtil;
import com.happyxmall.www.utils.JSONUtils;
import com.happyxmall.www.utils.PermissionsUtils;
import com.happyxmall.www.utils.ReviewUtil;
import com.happyxmall.www.utils.SpfUtil;
import com.happyxmall.www.utils.Utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import android.Manifest;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//public class MainActivity extends Activity {
public class MainActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    private WebView mWebView;
    private DeviceScreenListener listener = new DeviceScreenListener(this);
    public static boolean isActive; //全局变量
    private long exitTime = 0;
    private int startX = 0;
    private long downTime;
    private int startY = 0;

    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadCallbackBelow;
    private Uri imageUri;
    private int REQUEST_CODE = 1234;
    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    private BottomNavigationView bgview;

    private static final String LINK_URL = "file:///android_asset/index.html";
    private static final String LINK_URL_RETURN_ALIPAY = "file:///android_asset/index.html#/recharge";

    private String currency = "";
    String appid ="";
    String versionName="";
    LocationReceiver locationReceiver;
    private Boolean flagShow = false;
//    public CallbackManager callbackManager;
//    public ProfileTracker profileTracker;
//    @SuppressLint("WrongConstant")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
    private FacebookLogin faceBookLogin = null;

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        callbackManager = CallbackManager.Factory.create();
        mWebView=(WebView)findViewById(R.id.webView);
        bgview = findViewById(R.id.bottom_navigation);
        bgview.setItemIconTintList(null);
        bgview.setOnNavigationItemSelectedListener(createOnNavigationItemSelectedListener());
        bgview.setVisibility(View.GONE);
        //注册监听
        listener.register(createScreenStateListener());
        //获取版本和channel
//        String channel = ConfigUtil.getMetaData(this,"CHANNEL_VALUE");
        appid = ConfigUtil.getMetaData(this,"CHANNEL_APPID");
        versionName = ConfigUtil.packageName(this);
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


        mWebView.getSettings().setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //设置可自由缩放网页
        // 首先设置支持JS脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        //mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存
//        mWebView.getSettings().setUserAgentString("");
        mWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_3 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13E233 Safari/601.1");
//        mWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
        Log.i("params println", "user-agent：" + mWebView.getSettings().getUserAgentString());
        //允许下拉
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        this.initializeWebView();//JS监听
        mWebView.setHorizontalScrollBarEnabled(false); // 水平不显示滚动条
        mWebView.setVerticalScrollBarEnabled(false); //

        faceBookLogin = new FacebookLogin(this);
        facebookLogin();

        //();//通知登录状态
        currency = Utility.getCurrencyInfo();
        mWebView.loadUrl(LINK_URL+"#/home?channel_uuid="+appid+"&version="+versionName+"&currency="+currency);
        Log.i("params println", "appid：" + appid);
        Log.i("params println", "versionName：" + versionName);
        Log.i("params println", "currency：" + currency);
        //goSetNotification();
        //ReviewUtil.review(getApplicationContext(), MainActivity.this);
        initlocationReceiver();
        initIntent();
        //checkNotificationPermission();
        //setVisibility(View.GONE);
        //getHashkey();
    }

    /**
     * 初始化广播监听，便于接收指定action的广播内容
     */
    private void initlocationReceiver(){
        locationReceiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("location.sendreview");
        registerReceiver(locationReceiver, filter);
    }
    private Observable<ResultData<VersionInfo>> getVersionDetail(String appid, String version){
        return Api.getDefault().getVersionDetail(appid,version).map(new Function<ResultData<VersionInfo>, ResultData<VersionInfo>>() {
            @Override
            public ResultData<VersionInfo> apply(ResultData<VersionInfo> resultData2) throws Exception {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData<VersionInfo>>applySchedulers());
    }

    private void isNotificationEnabled(){
        //两个日历权限和一个数据读写权限
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}; //
//        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS}; //
        PermissionsUtils.showSystemSetting = true;//是否支持显示系统设置权限设置窗口跳转

        //创建监听权限的接口对象
        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                //权限通过执行的方法
                //权限通过验证
                //Toast.makeText(getApplicationContext(), "OKOK!!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void forbitPermissons() {
                //这是没有通过权限的时候提示的内容，自定义即可
                Toast.makeText(getApplicationContext(), "For normal use, allow permissions"
                        , Toast.LENGTH_SHORT).show();
                //finish();
//            Tool.exitApp();
            }
        };
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    /**
     * 判断是否是第一次启动
     * 是，判断是否启动了通知
     * 否，不作处理
     */
    private void checkNotificationPermission(){
        String checkNotice = "";
        checkNotice = (String)SpfUtil.getSerializable(this,SpfUtil.KEY_GET_IS_NOTICE_SHOW);
//        Log.e("checkNotice", "checkNotice--:"+checkNotice);
        if(Utility.checkStringNull(checkNotice)){
            if(!PermissionsUtils.isNotificationEnabled(this)){
//                PermissionsUtils.getInstance().goSetNotificationStart(MainActivity.this);
                mWebView.loadUrl("javascript:noticeShow()");
                SpfUtil.putSerializable(this,SpfUtil.KEY_GET_IS_NOTICE_SHOW,"no");
            }
        }
    }

    public void goSetNotificationStart(){
        if (!PermissionsUtils.isNotificationEnabled(MainActivity.this)){
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("android.provider.extra.APP_PACKAGE", MainActivity.this.getPackageName());
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", MainActivity.this.getPackageName());
                intent.putExtra("app_uid", MainActivity.this.getApplicationInfo().uid);
                startActivity(intent);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) { //4.4
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
            } else if (Build.VERSION.SDK_INT >= 15) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
            }
            startActivity(intent);
        }
    }
    private void getpermission() {
        //两个日历权限和一个数据读写权限
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}; //
//        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS}; //
        PermissionsUtils.showSystemSetting = true;//是否支持显示系统设置权限设置窗口跳转

        //创建监听权限的接口对象
        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                //权限通过执行的方法
                //权限通过验证
                //Toast.makeText(getApplicationContext(), "OKOK!!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void forbitPermissons() {
                //这是没有通过权限的时候提示的内容，自定义即可
                Toast.makeText(getApplicationContext(), "您没有允许部分权限，可能会导致部分功能不能正常使用，如需正常使用  请允许权限", Toast.LENGTH_SHORT).show();
                finish();
//            Tool.exitApp();
            }
        };
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
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
        /**
         * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.e("tt", "运行方法 openFileChooser-1");
            // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
            mUploadCallbackBelow = uploadMsg;
            takePhoto();
        }

        /**
         * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            Log.e("tt", "运行方法 openFileChooser-2 (acceptType: " + acceptType + ")");
            // 这里我们就不区分input的参数了，直接用拍照
            openFileChooser(uploadMsg);
        }

        /**
         * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.e("tt", "运行方法 openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
            // 这里我们就不区分input的参数了，直接用拍照
            openFileChooser(uploadMsg);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.e("tt", "运行方法 onShowFileChooser");
            // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
            mUploadCallbackAboveL = filePathCallback;
            takePhoto();
            return true;
        }
    };

    /**
     * 相册
     */
    private void takePhoto(){
        //启动系统相册
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * Android API < 21(Android 5.0)版本的回调处理
     * @param resultCode 选取文件或拍照的返回码
     * @param data 选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        Log.e("tt", "返回调用方法--chooseBelow");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e("tt", "系统返回URI：" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                Log.e("tt", "自定义结果：" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     * @param resultCode 选取文件或拍照的返回码
     * @param data 选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        Log.e("tt", "返回调用方法--chooseAbove");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    for (Uri uri : results) {
                        Log.e("tt", "系统返回URI：" + uri.toString());
                    }
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                Log.e("tt", "自定义结果：" + imageUri.toString());
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

    private WebViewClient webViewClient = new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            } else {
                url = request.toString();
            }

            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            final Activity context = MainActivity.this;

            if(url.startsWith("alipays:") || url.startsWith("alipay")){
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("Not detected Alipay client. Please install and try again.")
                            .setPositiveButton("install now", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                    context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                }
                            }).setNegativeButton("Cancel", null).show();
                }
                return true;
            } else if (url.startsWith("weixin://wap/pay?") || url.startsWith("weixins")){
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("Not detected WeChat client, try again after install.")
                            .setNegativeButton("Cancel", null).show();
                }
                return true;
            }

            if (url.contains("https://wx.tenpay.com")) {
                String referer = SpfUtil.getSerializable(context,SpfUtil.KEY_GET_REFERER) == null ?
                        "" : String.valueOf(SpfUtil.getSerializable(context,SpfUtil.KEY_GET_REFERER));
                if(referer == null || "".equals(referer)) {
                    referer = ApiConstants.RefererURL;
                }
                //referer = "http://test.wangqikj.com";
                Log.i("device screen testw", referer);
                Map<String, String> extraHeaders = new HashMap<>();
                extraHeaders.put("Referer", referer);
                view.loadUrl(url, extraHeaders);
                return true;
            } else if(url.contains(".goback()")){
                String link = LINK_URL_RETURN_ALIPAY+"?channel_uuid="+appid+"&version="+versionName+"&currency="+currency;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            isChecked(url);
            super.onPageStarted(view, url, favicon);
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void onPageFinished(WebView view, String url) {
            checkNotificationPermission();
            isChecked(url);
            //noticeLoginInfo(url);
            if(isFistPage(url) && flagShow){
                setVisibility(View.VISIBLE);
            } else{
                setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener createOnNavigationItemSelectedListener(){
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = getSelectItem(bgview);
                if(itemId != menuItem.getItemId()){
                    String title = menuItem.getTitle().toString();
                    switch (menuItem.getItemId()) {
                        case R.id.item_home:
                            mWebView.loadUrl(LINK_URL+"#/home");
                            break;
                        case R.id.item_categories:
                            mWebView.loadUrl(LINK_URL+"#/categories");
                            break;
                        case R.id.item_record:
                            mWebView.loadUrl(LINK_URL+"#/lottery-soon-list");
                            break;
                        case R.id.item_story:
                            mWebView.loadUrl(LINK_URL+"#/show-list");
                            break;
                        case R.id.item_account:
                            if(isLogin()){
                                mWebView.loadUrl(LINK_URL+"#/account");
                            } else {
                                bgview.setVisibility(View.GONE);
                                mWebView.loadUrl(LINK_URL+"#/login");
                            }
                            break;
                        default:
                            mWebView.loadUrl(LINK_URL+"#/home");
                            break;
                    }
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 获取选中的menu
     * @param bgview
     * @return
     */
    private int getSelectItem(BottomNavigationView bgview){
        Menu menu = bgview.getMenu();
        for (int i = 0; i < bgview.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isChecked()) {
                return menuItem.getItemId();
            }
        }
        return 0;
    }

    /**
     * 是否登录
     * @return
     */
    private boolean isLogin(){
        FrontUser su = (FrontUser)SpfUtil.getSerializable(this,SpfUtil.KEY_GET);
        if(su != null){
            return true;
        }
        return false;
    }

    private DeviceScreenListener.ScreenStateListener createScreenStateListener (){
        return new DeviceScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.i("device screen test", "屏幕点亮");
            }

            @Override
            public void onScreenOff() {
                Log.i("device screen test", "屏幕锁定");
            }

            @Override
            public void onUserPresent() {//调用JS方法登录
                Log.i("device screen test", "屏幕解锁且可以正常使用");
                //获取存储内容
                //getLoginInfo();
            }
        };
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 1500){
                Toast.makeText(getApplicationContext(), "Press the back button again to exit the program", Toast.LENGTH_SHORT).show();
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        downTime = event.getEventTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        long currentTime = event.getEventTime();
                        int endX = (int) event.getX();
                        int endY = (int) event.getY();
                        int defY = Math.abs(endY - startY);
                        int defX = Math.abs(endX - startX);
                        long time = currentTime - downTime;
                        if(endX>startX && mWebView.canGoBack() && (defX>100) && (time < 220) && defX > defY){
                            mWebView.goBack();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return super.dispatchTouchEvent(ev);
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
        super.onActionModeFinished(mode);
    }

    private void initializeWebView(){
//        mWebView.addJavascriptInterface(new JsOperator(MainActivity.this),
//                "JavascriptInterface");
        mWebView.addJavascriptInterface(new JSInterface(MainActivity.this), "JavascriptInterface");
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
        faceBookLogin.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void getHashkey(){
//        try {
//            int i = 0;
//            PackageInfo info = getPackageManager().getPackageInfo( getPackageName(),  PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                i++;
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String KeyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
//                Log.i("getHashkey","Hashkey:"+KeyHash);
//            }
//        }
//        catch (PackageManager.NameNotFoundException e) {
//
//        }
//        catch (NoSuchAlgorithmException e) {
//
//        }
//    }

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

            if(mCompositeSubscription != null){
                mCompositeSubscription.clear();
            }
        }
        /**
         * 注销监听
         */
        listener.unregister();
        /*
        注销广播监听
         */
        unregisterReceiver(locationReceiver);
//        profileTracker.stopTracking();
        super.onDestroy();
    }

    private boolean isFistPage(String url) {
        if(url.equals(LINK_URL+"#/home?channel_uuid="+appid+"&version="+versionName+"&currency="+currency)){
            return true;
        }
        if(url.indexOf("#"+ApiConstants.route_account) > -1){
            if(isLogin()){
                return true;
            } else{
                return false;
            }
        } else if(url.indexOf("#"+ApiConstants.route_categories) > -1){
            return true;
        } else if(url.indexOf("#"+ApiConstants.route_home) > -1){
            return true;
        } else if(url.indexOf("#"+ApiConstants.route_record) > -1){
            return true;
        } else if(url.indexOf("#"+ApiConstants.route_story) > -1){
            if(url.indexOf("#"+ApiConstants.route_story_user) > -1){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

//    private void noticeLoginInfo(String url) {
//        String appid = ConfigUtil.getMetaData(this,"CHANNEL_APPID");
//        String versionName = ConfigUtil.packageName(this);
//        if(url.equals(LINK_URL+"#/home?channel_uuid="+appid+"&version="+versionName+"&currency="+currency)){
//            getLoginInfo();
//        }
//        if(url.indexOf("#"+ApiConstants.route_account) > -1){
//            getLoginInfo();
//        } else if(url.indexOf("#"+ApiConstants.route_categories) > -1){
//            getLoginInfo();
//        } else if(url.indexOf("#"+ApiConstants.route_home) > -1){
//            getLoginInfo();
//        } else if(url.indexOf("#"+ApiConstants.route_record) > -1){
//            getLoginInfo();
//        } else if(url.indexOf("#"+ApiConstants.route_story) > -1){
//            if(url.indexOf("#"+ApiConstants.route_story_user) < 0){
//                getLoginInfo();
//            }
//        }
//    }

    /**
     * 动态变更选中项
     * @param url
     */
    private void isChecked(String url){
        if(url.equals(LINK_URL+"#/home?channel_uuid="+appid+"&version="+versionName+"&currency="+currency)){
            //bgview.getMenu().getItem(0).setChecked(true);
            setChecked(0);
        }
        if(url.indexOf("#"+ApiConstants.route_account) > -1){
            if(isLogin()){
                setChecked(4);
            }
        } else if(url.indexOf("#"+ApiConstants.route_categories) > -1){
//            bgview.getMenu().getItem(1).setChecked(true);
            setChecked(1);
        } else if(url.indexOf("#"+ApiConstants.route_home) > -1){
//            bgview.getMenu().getItem(0).setChecked(true);
            setChecked(0);
        } else if(url.indexOf("#"+ApiConstants.route_record) > -1){
//            bgview.getMenu().getItem(2).setChecked(true);
            setChecked(2);
        } else if(url.indexOf("#"+ApiConstants.route_story) > -1){
//            bgview.getMenu().getItem(3).setChecked(true);
            setChecked(3);
        }
    }

    private void setVisibility(int index){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bgview.setVisibility(index);
            }
        });
    }

    private void setChecked(int index){
        boolean isChecked = bgview.getMenu().getItem(index).isChecked();
        if(!isChecked){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bgview.getMenu().getItem(index).setChecked(true);
                }
            });
        }
    }

    public class LocationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals("location.sendreview")) {
                String message = intent.getStringExtra("message");
                Log.i("LocationReceiver","location.sendreview successful!!!"+message);
                //ReviewUtil.review(MainActivity.this, MainActivity.this);
                //这里调用js方法展示提示框
                mWebView.loadUrl("javascript:scoreShow()");
            }
        }
    }

    private void facebookLogin(){
        //faceBookLogin.login();
        faceBookLogin.setFacebookListener(new FacebookLogin.FacebookListener() {
            @Override
            public void facebookLoginSuccess(UserInfoObject object) throws JSONException {
                //判断是否已登录
                if(!isLogin()){//未登录状态下，再登录
                    //调用js方法 注册用户信息
                    String jsonStr = JSONUtils.obj2json(object);
//                Log.e("accessToken","accessToken4:"+object.toString());
//                //jsonStr = JSONObject.toJSONString(object);
                    Log.e("accessToken","accessToken5:"+jsonStr);
//                JSONObject o = new JSONObject();
//                o.put("userID",object.getUserID());
//                o.put("nickname",object.getNickname());
//                o.put("image",object.getImage());
//                o.put("accessToken",object.getAccessToken());
//                o.put("countryCode",object.getCountryCode());
//                jsonStr = o.toString();
//                Log.e("accessToken","accessToken6:"+o.toString());
//                Log.e("accessToken","accessToken7:"+jsonStr);
                    mWebView.loadUrl("javascript:loginFacebook('"+jsonStr+"')");
                }
                //mWebView.reload();
            }

            @Override
            public void facebookLoginFail(String message) {

                //mLoginResult.setText("facebook_account_oauth_Fail !" + message);

                Toast.makeText(MainActivity.this, "Facebook Login Error:" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void facebookLoginCancel() {

                //mLoginResult.setText("facebook_account_oauth_Cancel !");
                Toast.makeText(MainActivity.this, "Facebook Login Cancel!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        try {
            if(PermissionsUtils.isNotificationEnabled(this)){
                FrontUser fu = (FrontUser)SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET);
                if(fu != null){
                    Bundle bundle=intent.getExtras();
                    if(bundle!=null) {
                        String noticeType = bundle.getString("noticeType");
                        if(!Utility.checkStringNull(noticeType) && ApiConstants.NOTICE_TYPE_USER_WIN.equals(noticeType)){
                            Log.i("onNewIntent","FCM_OPEN_MAIN_ACTIVITY:noticeType-"+noticeType);
                            //ReviewUtil.review(MainActivity.this, MainActivity.this);
                            //这里调用js方法展示提示框
                            mWebView.loadUrl("javascript:scoreShow()");
                        }
                    }
                } else {
                    Log.i("onNewIntent","have not login info");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            super.onNewIntent(intent);
            return;
        }
        super.onNewIntent(intent);
    }

    protected void initIntent(){
        try {
            if(PermissionsUtils.isNotificationEnabled(this)){
                FrontUser fu = (FrontUser)SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET);
                if(fu != null){
                    Bundle bundle=getIntent().getExtras();
                    if(bundle!=null) {
                        String noticeType = bundle.getString("noticeType");
                        if(!Utility.checkStringNull(noticeType) && ApiConstants.NOTICE_TYPE_USER_WIN.equals(noticeType)){
                            Log.i("onNewIntent","FCM_OPEN_MAIN_ACTIVITY:noticeType-"+noticeType);
                            //这里调用js方法展示提示框
                            mWebView.loadUrl("javascript:scoreShow()");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * JS监听配置
     */
    private final class JSInterface{
        private Context context;
        public JSInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showDialog(String message) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setTitle("Attention");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

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
         * 调用JS方法获取登录状态
         */
        @JavascriptInterface
        public String getLoginInfo(){
            try {
                String su = (String)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET_USER);
                if(su != null && !"".equals(su)){
                    Log.i("local-storagemobile：", su);
                    //调用JS方法
                    return su;
                }
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
            return "";
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
                if(!Utility.checkStringNull(loginInfo)){
                    Log.i("device screen testa", "test demo first");
                    Log.i("device screen testb", loginInfo);
                    String uuid = loginInfo.substring(0,36);
                    String mobile = loginInfo.substring(36);
                    Log.i("device screen testc", mobile);
                    Log.i("device screen testd", uuid);
                    FrontUser su = new FrontUser();
                    su.setMobile(mobile);
                    su.setUuid(uuid);
                    SpfUtil.putSerializable(this.context,SpfUtil.KEY_GET,su);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 存储登录信息
         * @param loginInfo
         */
        @JavascriptInterface
        public void setLoginInfo(String loginInfo){
            try{
                Log.i("用户信息存储a", "test demo first");
                Log.i("用户信息存储b", loginInfo);
                if(loginInfo != null && !"".equals(loginInfo)){
                    SpfUtil.putSerializable(this.context,SpfUtil.KEY_GET_USER,loginInfo);
                    FrontUserVO fuvo = JSONUtils.json2obj(loginInfo,FrontUserVO.class);
                    FrontUser fu = new FrontUser();
                    fu.setMobile(fuvo.getMobile());
                    fu.setUuid(fuvo.getUuid());
                    SpfUtil.putSerializable(getApplicationContext(),SpfUtil.KEY_GET,fu);

                    new Thread(new Runnable() {//每次登录时，注册一遍设备信息
                        @Override
                        public void run() {
                            String deviceToken = (String) SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE);
                            if(deviceToken != null && !"".equals(deviceToken)){
                                //获取local-storage的用户信息，若用户信息存在，则将用户信息附到设备注册上|| 另外，需要在用户登录时，也重新调用一次checkin
                                String uuid = fu.getUuid();
                                String uniqueToken = (String) SpfUtil.getSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE_UNIQUE);//缓存存储
                                mPresenter.checkin(uniqueToken,uuid,deviceToken);
                                SpfUtil.putSerializable(getApplicationContext(),SpfUtil.KEY_GET_DEVICE,deviceToken);
                            }
                        }
                    }).start();

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public String getInfo(){
            try {
                String su = (String)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET_DATA);
                if(su != null && !"".equals(su)){
                    Log.i("local-storageData：", su);
                    //调用JS方法
                    return su;
                }
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
            return "";
        }

        @JavascriptInterface
        public void setInfo(String info){
            try{
                Log.i("信息存储a", "test demo first");
                if(info != null && !"".equals(info)){
                    Log.i("信息存储b", info);
                    SpfUtil.putSerializable(this.context,SpfUtil.KEY_GET_DATA,info);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void removeInfo(){
            try {
                Log.i("删除信息e", "test demo first");
                String su = (String)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET_DATA);
                if(su != null && !"".equals(su)){
                    Log.i("删除信息f", "test demo first："+su.toString());
                    Log.i("删除信息g", su);
                    SpfUtil.remove(this.context,SpfUtil.KEY_GET_DATA);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 删除登录信息
         */
        @JavascriptInterface
        public void removeLoginInfo(){
            try {
                Log.i("删除登录信息e", "test demo first");
                String su = (String)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET_USER);
                if(su != null && !"".equals(su)){
                    Log.i("删除登录信息f", "test demo first："+su.toString());
                    Log.i("删除登录信息g", su);
                    SpfUtil.remove(this.context,SpfUtil.KEY_GET_USER);
                }
                logout();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        /**
         * 删除登录信息
         * 这里需要读取本地文件
         * @return JSON格式的字符串
         */
        @JavascriptInterface
        public void logout(){
            try {
                Log.i("device screen test e", "test demo first");
                FrontUser su = (FrontUser)SpfUtil.getSerializable(this.context,SpfUtil.KEY_GET);
                if(su != null){
                    Log.i("device screen test f", "test demo first："+su.toString());
                    Log.i("device screen test g", su.getMobile() == null ? "":su.getMobile());
                    Log.i("device screen test h", su.getUuid());
                    SpfUtil.remove(this.context,SpfUtil.KEY_GET);
                    SpfUtil.remove(getApplicationContext(),SpfUtil.KEY_GET);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
         * 存储信息
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
        /**
         * 存储信息
         * 需要存储到本地文件中
         * @param route
         * @return
         */
        @JavascriptInterface
        public String goRoute(String route){
            try{
                Log.i("device routew", "test demo first");
                Log.i("device routem", route);
                setVisibility(View.GONE);
                if(!ApiConstants.route_other.equals(route)){
                    switch (route) {
                        case ApiConstants.route_account ://我的
                            setChecked(4);
                            setVisibility(View.VISIBLE);
                            if(!isLogin()){
                                setVisibility(View.GONE);
                            }
                            break;
                        case ApiConstants.route_categories ://全部商品
                            setChecked(1);
                            setVisibility(View.VISIBLE);
                            break;
                        case ApiConstants.route_home ://主页
                            setChecked(0);
                            setVisibility(View.VISIBLE);
                            break;
                        case ApiConstants.route_record ://即将开奖
                            setChecked(2);
                            setVisibility(View.VISIBLE);
                            break;
                        case ApiConstants.route_story ://拍拍圈
                            setChecked(3);
                            setVisibility(View.VISIBLE);
                            break;
                        default:
                            setVisibility(View.GONE);
                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("device route addw", LINK_URL+"#"+route);
                            if(route.indexOf("/recharge?result=") > -1){
                                String status = "";
                                if(route.split("=").length > 1){
                                    status = route.split("=")[1];
                                }
                                Log.i("status w", status);
                                mWebView.loadUrl(LINK_URL+"#/recharge"+"?channel_uuid="+appid+"&version="+versionName+"&currency="+currency+"&resultStatus="+status);
                            } else {
                                mWebView.loadUrl(LINK_URL+"#"+route);
                            }
//                            if("/recharge?result=".equals(route)){
//                                mWebView.loadUrl(LINK_URL+"#"+route+"?channel_uuid="+appid+"&version="+versionName+"&currency="+currency);
//                            } else {
//                                mWebView.loadUrl(LINK_URL+"#"+route);
//                            }
                        }
                    });
                }
                return "ok";
            }catch (Exception e){
                Log.i("device routew", "666");
                e.printStackTrace();
                return "error";
            }
        }

        @JavascriptInterface
        public boolean areNotificationsEnabled() {
            return PermissionsUtils.isNotificationEnabled(MainActivity.this);
        }

        @JavascriptInterface
        public void goSetNotification(){
            goSetNotificationStart();
        }

        @JavascriptInterface
        public void goReview(){
            ReviewUtil.review(MainActivity.this, MainActivity.this);
        }

        @JavascriptInterface
        public void bottomShow(boolean flag){
            Log.i("bottomShow", "bottomShow:"+flag);
            if(flag){
                setVisibility(View.VISIBLE);
                flagShow = true;
            } else {
                setVisibility(View.GONE);
                flagShow = false;
            }
        }

        @JavascriptInterface
        public void loginByFacebook(){
            faceBookLogin.login();
        }
    }
}
