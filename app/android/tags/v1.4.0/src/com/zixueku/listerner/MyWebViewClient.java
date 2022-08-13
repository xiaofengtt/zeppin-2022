package com.zixueku.listerner;

import android.webkit.WebView;

public class MyWebViewClient extends android.webkit.WebViewClient{

	@Override
	public void onPageFinished(WebView view, String url) {
		view.getSettings().setBlockNetworkImage(false);
		super.onPageFinished(view, url);
		// html加载完成之后，添加监听图片的点击js函数
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		view.loadUrl("javascript:(function(){" +
				"var objs = document.getElementsByTagName(\"img\"); " + 
						"for(var i=0;i<objs.length;i++)  " + 
				"{"
						+ "    objs[i].onclick=function()  " + 
				"    {  " 
						+ "        window.imagelistner.openImage(this.src);  " + 
				"    }  " + 
				"}" + 
				"})()");
	}
	
}
