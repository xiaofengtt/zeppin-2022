package com.xianshimiaosha.www;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xianshimiaosha.www.app.Api;
import com.xianshimiaosha.www.app.JsOperator;
import com.xianshimiaosha.www.baserx.RxManager;
import com.xianshimiaosha.www.baserx.RxSchedulers;
import com.xianshimiaosha.www.bean.ApiConstants;
import com.xianshimiaosha.www.bean.ResultData;
import com.xianshimiaosha.www.bean.ResultData2;
import com.xianshimiaosha.www.bean.SwitchInfo;
import com.xianshimiaosha.www.bean.VersionInfo;
import com.xianshimiaosha.www.listener.DeviceScreenListener;
import com.xianshimiaosha.www.utils.ConfigUtil;
import com.xianshimiaosha.www.utils.PermissionsUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import android.Manifest;

public class MainActivity extends Activity {

    private WebView mWebView;
    private DeviceScreenListener listener = new DeviceScreenListener(this);
    public static boolean isActive; //????????????
    private static boolean isExit = false;
    private long exitTime = 0;
    private int startX = 0;
    private SwitchInfo switchInfo;
    private int scrollSize = 200;
    private long downTime;
    private int startY = 0;

    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadCallbackBelow;
    private Uri imageUri;
    private int REQUEST_CODE = 1234;
    private ValueCallback<Uri[]> filePathCallback1;
    private RxManager rxManager;
    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        rxManager = new RxManager();
        mWebView=(WebView)findViewById(R.id.webView);
        //???????????????channel
//        String channel = ConfigUtil.getMetaData(this,"CHANNEL_VALUE");
        String appid = ConfigUtil.getMetaData(this,"CHANNEL_APPID");
        String versionName = ConfigUtil.packageName(this);
        //??????????????????
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

        //???????????????????????????
        // ??????????????????JS??????
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        //????????????
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        this.initializeWebView();
        mWebView.setHorizontalScrollBarEnabled(false); // ????????????????????????
        mWebView.setVerticalScrollBarEnabled(false); //


//        mWebView.loadUrl(switchInfo.getLoadURL());
        initData(appid,versionName);
    }

    private void initData(String appid, String version){
        getVersionDetail(appid,version).subscribe(new Observer<ResultData2<VersionInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeSubscription.add(d);
            }

            @Override
            public void onNext(ResultData2<VersionInfo> resultdata2) {
                String loadUrl = "";
                if(resultdata2.success()){
                    VersionInfo vi = resultdata2.getData();
                    if(vi.getState() == 1){
                        loadUrl = vi.getZburl() == null ? "" : vi.getZburl();
                        getpermission();
                    }else if(vi.getState() == 2){
                        loadUrl = vi.getMjurl() == null ? "" : vi.getMjurl();
                        getpermission();
                    }else{
                        getpermission();
                        loadUrl = vi.getMjurl() == null ? "" : vi.getMjurl();
                    }
                    mWebView.loadUrl(loadUrl);
                } else {
                    Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
//        getVersionDetail(appid,version).subscribe(new DefaultObserver<ResultData2<VersionInfo>>() {
//            @Override
//            public void onNext(ResultData2<VersionInfo> resultdata2) {
//                String loadUrl = "";
//                if(resultdata2.success()){
//                    VersionInfo vi = resultdata2.getData();
//                    if(vi.getState() == 1){
//                        loadUrl = vi.getZburl() == null ? "" : vi.getZburl();
//                    }else if(vi.getState() == 2){
//                        loadUrl = vi.getMjurl() == null ? "" : vi.getMjurl();
//                    }else{
//                        loadUrl = vi.getMjurl() == null ? "" : vi.getMjurl();
//                    }
//                    mWebView.loadUrl(loadUrl);
//                } else {
//                    Toast.makeText(getApplicationContext(), "????????????1", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(getApplicationContext(), "????????????2", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
    }

    private Observable<ResultData2<VersionInfo>> getVersionDetail(String appid, String version){
        return Api.getDefault().getVersionDetail(appid,version).map(new Function<ResultData2<VersionInfo>, ResultData2<VersionInfo>>() {
            @Override
            public ResultData2<VersionInfo> apply(ResultData2<VersionInfo> resultData2) throws Exception {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<VersionInfo>>applySchedulers());
    }

    private void getpermission() {
        //?????????????????????????????????????????????
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}; //
//        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS}; //
        PermissionsUtils.showSystemSetting = true;//??????????????????????????????????????????????????????

        //?????????????????????????????????
        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                //???????????????????????????
                //??????????????????
                //Toast.makeText(getApplicationContext(), "OKOK!!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void forbitPermissons() {
                //??????????????????????????????????????????????????????????????????
                Toast.makeText(getApplicationContext(), "????????????????????????????????????????????????????????????????????????????????????????????????  ???????????????", Toast.LENGTH_SHORT).show();
                //finish();
//            Tool.exitApp();
            }
        };
        //?????????this?????????????????????Activity?????????
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //??????????????????this
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
         * 8(Android 2.2) <= API <= 10(Android 2.3)???????????????
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.e("tt", "???????????? openFileChooser-1");
            // (2)??????????????????????????????API < 21??????????????????????????? mUploadCallbackBelow????????? != null
            mUploadCallbackBelow = uploadMsg;
            takePhoto();
        }

        /**
         * 11(Android 3.0) <= API <= 15(Android 4.0.3)???????????????
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            Log.e("tt", "???????????? openFileChooser-2 (acceptType: " + acceptType + ")");
            // ????????????????????????input??????????????????????????????
            openFileChooser(uploadMsg);
        }

        /**
         * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)???????????????
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.e("tt", "???????????? openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
            // ????????????????????????input??????????????????????????????
            openFileChooser(uploadMsg);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.e("tt", "???????????? onShowFileChooser");
            // (1)??????????????????????????????API >= 21??????????????????????????? mUploadCallbackAboveL????????? != null
            mUploadCallbackAboveL = filePathCallback;
            takePhoto();
            return true;
        }
    };

    /**
     * ??????
     */
    private void takePhoto(){
        //??????????????????
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * Android API < 21(Android 5.0)?????????????????????
     * @param resultCode ?????????????????????????????????
     * @param data ????????????????????????????????????
     */
    private void chooseBelow(int resultCode, Intent data) {
        Log.e("tt", "??????????????????--chooseBelow");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // ?????????????????????????????????
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e("tt", "????????????URI???" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // ??????????????????????????????????????????????????????????????????data??????
                Log.e("tt", "??????????????????" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) ?????????????????????
     * @param resultCode ?????????????????????????????????
     * @param data ????????????????????????????????????
     */
    private void chooseAbove(int resultCode, Intent data) {
        Log.e("tt", "??????????????????--chooseAbove");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // ?????????????????????????????????????????????
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    for (Uri uri : results) {
                        Log.e("tt", "????????????URI???" + uri.toString());
                    }
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                Log.e("tt", "??????????????????" + imageUri.toString());
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

    //???????????????
    public void showStatusBar() {
        if (Build.VERSION.SDK_INT < 30) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    //???????????????
    public void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 30) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //  ??????????????????????????????????????????????????????????????????webview????????????????????????????????????
            final Activity context = MainActivity.this;
            if(url.startsWith("alipays:") || url.startsWith("alipay")){
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("??????????????????????????????????????????????????????")
                            .setPositiveButton("????????????", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                    context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                }
                            }).setNegativeButton("??????", null).show();
                }
                return true;
            } else if (url.startsWith("weixin://wap/pay?") || url.startsWith("weixins")){
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("???????????????????????????????????????????????????")
                            .setNegativeButton("??????", null).show();
                }
                return true;
            }

            if (url.contains("https://wx.tenpay.com")) {
                String referer = ApiConstants.RefererURL;

                Log.i("????????????w", referer);
                Map<String, String> extraHeaders = new HashMap<>();
                extraHeaders.put("Referer", referer);
                view.loadUrl(url, extraHeaders);
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 1500){
                Toast.makeText(getApplicationContext(), "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                return true;
            }

            //??????????????????????????????????????????keyCode ?????????????????????????????????????????????????????????Webview??????????????????WebView???????????????????????????mWebView.canGoBack()??????????????????Boolean????????????????????????????????????true
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
        //actionBar.setTitle();
        super.onActionModeFinished(mode);
    }

    private void initializeWebView(){
        mWebView.addJavascriptInterface(new JsOperator(MainActivity.this),
                "JavascriptInterface");
    }

    /**
     * APP??????????????????????????????
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
            //app ????????????
            isActive = false;//??????????????????????????????
            Log.i("ACTIVITY", "??????????????????");
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        if( mWebView!=null) {

            // ???????????????destroy()?????????????????????if (isDestroyed()) return;???????????????????????????onDetachedFromWindow()??????
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // ????????????????????????????????????????????????????????????????????????????????????
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
         * ????????????
         */
//        listener.unregister();

        super.onDestroy();
    }
}
