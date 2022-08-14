package cn.zeppin.product.ntb.ui.main.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;


public class WebActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.web_progress)
    ProgressBar webProgress;
    @Bind(R.id.webView)
    WebView webView;

    private String mUri = "http://";
    private String mTitle = "";
    public static final int LOAD_JAVASCRIPT = 0X01;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String javaScript = "javascript: getpdf2('" + mUri + "')";
            webView.loadUrl(javaScript);
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tvTitle.setText("加载中...");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTitle = bundle.getString(AppConstant.INTENT_TITLE);
            mUri = bundle.getString(AppConstant.INTENT_URL);
        }
        initWebView();
    }

    private void initWebView() {
//        try {
//            checkCompatibility();
//            /* success, this is a newer device */
//        } catch (NoSuchMethodException e) {
//            /* failure, must be older device */
//            ToastUtil.show(this.getApplicationContext(), "系统版本过旧");
//            return;
//        }

        webView.requestFocusFromTouch();
        webView.setHorizontalFadingEdgeEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    tvTitle.setText(title);
                } else {
                    tvTitle.setText(mTitle);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if (findViewById(R.id.toolbar).getVisibility() == View.VISIBLE) {
                webProgress.setProgress(newProgress);
                if (newProgress == 100) {
                    webProgress.setVisibility(View.GONE);
                } else {
                    webProgress.setVisibility(View.VISIBLE);
                }
//                } else {
//                    webProgress.setVisibility(View.GONE);
//                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // TODO handler.cancel();这样做可以堵住安全漏洞,但会影响流量充值及一些帮助页面
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                handler.sendEmptyMessage(LOAD_JAVASCRIPT);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        WebSettings settings = this.webView.getSettings();
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        /*    版本适配主要是指API接口的适配，android版本的快速迭代，
             出现了各种兼容包v4、v7、v13。我印象最深刻的是一个关于webview的http和https的混合请求的，
            在API>=21的版本上面默认是关闭的，在21以下就是默认开启的，
           直接导致了在高版本上面http请求不能正确跳转。
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //是与86社区h5商量好的标识
//        settings.setUserAgentString(settings.getUserAgentString() + "; 10086LiveApp");
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);//解决图片加载不出来的问题
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setAppCacheEnabled(false);
        //开启DomStorage缓存
//        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);


        settings.setSavePassword(false);
        settings.setAllowFileAccess(true);//资源加载超时操作

        //安全漏洞触发成功前提条件如下： WebView中setAllowFileAccessFromFileURLs 或setAllowUniversalAccessFromFileURLsAPI配置为true；
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            webView.addJavascriptInterface(new JsObject(this, mUri), "client");
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }
        webView.loadUrl(mUri);
    }

    /**
     * 物理返回
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        webView.setVisibility(View.GONE);
        webView.destroy();
        super.onDestroy();
    }
}
