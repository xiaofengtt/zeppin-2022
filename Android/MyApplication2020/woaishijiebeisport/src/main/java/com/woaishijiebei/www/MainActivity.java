package com.woaishijiebei.www;

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
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

import com.woaishijiebei.www.app.Api;
import com.woaishijiebei.www.app.JsOperator;
import com.woaishijiebei.www.baserx.RxSchedulers;
import com.woaishijiebei.www.bean.ApiConstants;
import com.woaishijiebei.www.bean.ResultData2;
import com.woaishijiebei.www.bean.VersionInfo;
import com.woaishijiebei.www.listener.DeviceScreenListener;
import com.woaishijiebei.www.utils.ConfigUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends Activity {

    private static final String LINK_URL = "file:///android_asset/index.html";
    private static final String LINK_URL_RETURN_ALIPAY = "file:///android_asset/index.html#/my/alipay";
    private WebView mWebView;
    private DeviceScreenListener listener = new DeviceScreenListener(this);
    public static boolean isActive; //ćšć±ćé
    private long exitTime = 0;
    private int startX = 0;
    private long downTime;
    private int startY = 0;
    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mWebView=findViewById(R.id.webView);

        //ćźç°èȘç±è·šć
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

        //èźŸçœźćŻèȘç±çŒ©æŸçœéĄ”
        //browser.getSettings().setSupportZoom(true);
        //browser.getSettings().setBuiltInZoomControls(true);
        // éŠćèźŸçœźæŻæJSèæŹ
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        //ćèźžäžæ
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
//        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
//        mWebView.getSettings().setUseWideViewPort(true);

//        mWebView.setInitialScale(70);
//        mWebView.setHorizontalScrollbarOverlay(true);

        // æŻæJsćšćœćAppæćŒćșçšïŒćœéĄ”éąè·łèœŹçæ¶ćäŸæ§ćšćœćçWebViewäčäž­
        //mWebView.setWebViewClient(new WebViewClient());
        // äŒ ć„ćœćæä»ŹéèŠæćŒççœć(ćșäșhttpçïŒäčæçæŻhttpsïŒäžèżćć°ç»çäžèŹéœæČĄæéźéą),èź°ćŸæ·»ć çœç»æé
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebChromeClient(webChromeClient);
        // ćŠæéĄ”éąäž­éŸæ„ïŒćŠæćžæçčć»éŸæ„ç»§ç»­ćšćœćbrowseräž­ććșïŒ
        // èäžæŻæ°ćŒAndroidççł»ç»browseräž­ććșèŻ„éŸæ„ïŒćżéĄ»èŠçwebviewçWebViewClientćŻčè±Ą
        mWebView.setWebViewClient(webViewClient);
        this.initializeWebView();
        mWebView.setHorizontalScrollBarEnabled(false); // æ°ŽćčłäžæŸç€șæ»ćšæĄ
        mWebView.setVerticalScrollBarEnabled(false); //

//        mWebView.loadUrl(LINK_URL);
        String channel = ConfigUtil.getMetaData(this,"CHANNEL_VALUE");
        String versionName = ConfigUtil.packageName(this);
        initData(channel,versionName);
    }

    private void initData(String channel, String version){
        getVersionDetail(channel,version).subscribe(new Observer<ResultData2<VersionInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeSubscription.add(d);
            }

            @Override
            public void onNext(ResultData2<VersionInfo> resultdata2) {
                String loadUrl = "";
                if(resultdata2.success()){
                    VersionInfo vi = resultdata2.getData();
                    if(vi.getAdverturl() != null && !"".equals(vi.getAdverturl())){
                        loadUrl = vi.getAdverturl() == null ? "" : vi.getAdverturl();
                        //getpermission();
                    }else{
                        loadUrl = LINK_URL;
                    }
                    mWebView.loadUrl(loadUrl);
                } else {
                    Toast.makeText(getApplicationContext(), "ć èœœć€±èŽ„", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "ć èœœć€±èŽ„", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Observable<ResultData2<VersionInfo>> getVersionDetail(String channel, String version){
        return Api.getDefault().getVersionDetail(channel,version, ApiConstants.BUNDLEID).map(new Function<ResultData2<VersionInfo>, ResultData2<VersionInfo>>() {
            @Override
            public ResultData2<VersionInfo> apply(ResultData2<VersionInfo> resultData2) throws Exception {
                return resultData2;
            }
        }).compose(RxSchedulers.<ResultData2<VersionInfo>>applySchedulers());
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
            //  éćæ­€æčæłèĄšæçčć»çœéĄ”ééąçéŸæ„èżæŻćšćœćçwebviewéè·łèœŹïŒäžè·łć°æ”è§ćšéŁèŸč
            final Activity context = MainActivity.this;
            if(url.startsWith("alipays:") || url.startsWith("alipay")){
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("æȘæŁæ”ć°æŻä»ćźćźąæ·ç«ŻïŒèŻ·ćźèŁćéèŻă")
                            .setPositiveButton("ç«ćłćźèŁ", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                    context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                }
                            }).setNegativeButton("ćæ¶", null).show();
                }
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
                Toast.makeText(getApplicationContext(), "ćæäžæŹĄćééźéćșçšćș", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                return true;
            }

            //èżæŻäžäžȘçćŹçšçæéźçæčæłïŒkeyCode çćŹçšæ·çćšäœïŒćŠææŻæäșèżćéźïŒćæ¶WebviewèŠèżćçèŻïŒWebViewæ§èĄćéæäœïŒć äžșmWebView.canGoBack()èżćçæŻäžäžȘBooleanç±»ćïŒæä»„æä»Źæćźèżćäžștrue
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
     * APPæŻćŠć€äșćć°ć€éç¶æ
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
            //app èżć„ćć°
            isActive = false;//èź°ćœćœćć·Čç»èżć„ćć°
            Log.i("ACTIVITY", "çšćșèżć„ćć°");
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        if( mWebView!=null) {

            // ćŠæćè°çšdestroy()æčæłïŒćäŒćœäž­if (isDestroyed()) return;èżäžèĄä»Łç ïŒéèŠćonDetachedFromWindow()ïŒć
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // éćșæ¶è°çšæ­€æčæłïŒç§»é€ç»ćźçæćĄïŒćŠćæäșçčćźçł»ç»äŒæ„é
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        /**
         * æłšéçćŹ
         */
//        listener.unregister();

        super.onDestroy();
    }
}
