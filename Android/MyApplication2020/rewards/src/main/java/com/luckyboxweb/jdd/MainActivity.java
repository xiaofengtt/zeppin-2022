package com.luckyboxweb.jdd;

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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luckyboxweb.jdd.app.JsOperator;
import com.luckyboxweb.jdd.bean.ApiConstants;
import com.luckyboxweb.jdd.bean.SwitchInfo;
import com.luckyboxweb.jdd.listener.DeviceScreenListener;
import com.luckyboxweb.jdd.utils.ConfigUtil;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.core.AVOSService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends Activity {

    private WebView mWebView;
    private DeviceScreenListener listener = new DeviceScreenListener(this);
    public static boolean isActive; //ćšć±ćé
    private static boolean isExit = false;
    private long exitTime = 0;
    private int startX = 0;
    private SwitchInfo switchInfo;
    private int scrollSize = 200;
    private long downTime;
    private int startY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //æ„ć„leancloud
        // éçœź SDK ćšć­
        AVOSCloud.setServer(AVOSService.API, "https://aliasapi.luckms.com");
        // éçœź SDK äșćŒæïŒçšäșèźżéźäșćœæ°ïŒäœżçš API èȘćźäčććïŒèéäșćŒæèȘćźäčććïŒ
        AVOSCloud.setServer(AVOSService.ENGINE, "https://luckms.com");
        // éçœź SDK æšé
        AVOSCloud.setServer(AVOSService.PUSH, "https://aliasapi.luckms.com");
        // éçœź SDK ćłæ¶éèźŻ
        AVOSCloud.setServer(AVOSService.RTM, "https://aliasapi.luckms.com");

        //æ„ćżè°èŻ
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
        // æäŸ thisăApp ID ć App Key äœäžșćæ°
        // æłšæèżéćäžäžèŠè°çš cn.leancloud.core.AVOSCloud ç initialize æčæłïŒćŠćäŒćșç° NetworkOnMainThread ç­éèŻŻă
        mWebView=(WebView)findViewById(R.id.webView);

        //è·ćçæŹćchannel
        String channel = ConfigUtil.getMetaData(this,"CHANNEL_VALUE");
        String versionName = ConfigUtil.packageName(this);
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
        // éŠćèźŸçœźæŻæJSèæŹ
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        //ćèźžäžæ
        mWebView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        this.initializeWebView();
        mWebView.setHorizontalScrollBarEnabled(false); // æ°ŽćčłäžæŸç€șæ»ćšæĄ
        mWebView.setVerticalScrollBarEnabled(false); //

        AVOSCloud.initialize(this, "TcAGYbvswRo6XrrJqcLe11Ie-gzGzoHsz", "luSmjmtpGqryRYLtr0NNanls");
        initData(channel,versionName);
//        mWebView.loadUrl(switchInfo.getLoadURL());
    }

    private void initData(final String channel, final String versionName){
        switchInfo = new SwitchInfo();
        AVQuery<AVObject> query = new AVQuery<>("mainSwitch");
        query.getInBackground(ApiConstants.ObjectId).subscribe(new Observer<AVObject>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVObject todo) {
                // è·ććçœźć±æ§
                List<Map<String,Object>> switchs = todo.get("switch") == null? null : (List<Map<String, Object>>) todo.get("switch");
                if(switchs != null && !switchs.isEmpty()){
                    for(Map<String,Object> sw : switchs) {
                        String chStr = sw.get("channel").toString();
                        String verStr = sw.get("version").toString();
                        String aliasURL = sw.get("aliasURL") == null ? "" : sw.get("aliasURL").toString();
                        switchInfo.setLoadURL(aliasURL);
                        if(chStr.toLowerCase().indexOf(channel) > -1 && versionName.equals(verStr)){
                            boolean flag = Boolean.valueOf(sw.get("isMain").toString());
                            if(flag){
                                switchInfo.setLoadURL(sw.get("mainURL") == null ? "" : sw.get("mainURL").toString());
                            } else {
                                switchInfo.setLoadURL(sw.get("aliasURL") == null ? "" : sw.get("aliasURL").toString());
                            }
                            break;
                        }
                    }
                }
                mWebView.loadUrl(switchInfo.getLoadURL());
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });
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
            String referer = ApiConstants.RefererURL;
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
            } else if (url.startsWith("weixin://wap/pay?") || url.startsWith("weixins")){
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("æȘæŁæ”ć°ćŸźäżĄćźąæ·ç«ŻïŒèŻ·ćźèŁćéèŻă")
                            .setNegativeButton("ćæ¶", null).show();
                }
                return true;
            }

            if (url.contains("https://wx.tenpay.com")) {
                Map<String, String> extraHeaders = new HashMap<>();
                extraHeaders.put("Referer", referer);
                view.loadUrl(url, extraHeaders);
                referer = url;
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
