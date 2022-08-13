package com.zixueku.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.zixueku.widget.URLDrawable;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-19 上午11:01:16
 */
public class URLImageParser implements ImageGetter {
	TextView textView;

	Context context;

	/***
	 * Construct the URLImageParser which will execute AsyncTask and refresh the
	 * container
	 * 
	 * @param t
	 */
	public URLImageParser(TextView t, Context context) {
		this.textView = t;
		this.context = context;
	}

	public Drawable getDrawable(String source) {
		URLDrawable urlDrawable = new URLDrawable();

		// get the actual source
		ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);

		asyncTask.execute(source);

		// return reference to URLDrawable where I will change with actual image
		// from
		// the src tag
		return urlDrawable;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, HashMap<String, Object>> {
		URLDrawable urlDrawable;

		public ImageGetterAsyncTask(URLDrawable d) {
			this.urlDrawable = d;
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			// String source = params[0];
			String imageUrl = CommonTools.convertURL(params[0]);
			return fetchDrawable(imageUrl);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			// set the correct bound according to the result from HTTP call
			if (result == null) {
				return;
			}
			Drawable dra = (Drawable) result.get("drawable");
			urlDrawable.setBounds(0, 0, 0 + dra.getIntrinsicWidth(), 0 + dra.getIntrinsicHeight());
			urlDrawable.setDrawable(dra);
			textView.setText(textView.getText());
			textView.invalidate();

			LayoutParams lp = textView.getLayoutParams();
			lp.width = LayoutParams.MATCH_PARENT;
			// lp.height = textView.getMeasuredHeight() + (int)
			// result.get("diffValue");
			lp.height = textView.getHeight() + (int) result.get("showHeight") - CommonTools.dip2px(context, 15);
			// Log.e("height", textView.getHeight() + "");
			textView.setLayoutParams(lp);
		}

		/***
		 * Get the Drawable from URL
		 * 
		 * @param urlString
		 * @return
		 */
		public HashMap<String, Object> fetchDrawable(String urlString) {
			try {
				InputStream is = fetch(urlString);
				Drawable drawable = Drawable.createFromStream(is, "src");
				DisplayMetrics dm = context.getResources().getDisplayMetrics();
				// 获取手机屏幕分辨率
				// 屏幕实际大小
				// int screenWidth = dm.widthPixels;
				// int screenHeight = dm.heightPixels;
				float density = dm.density;
				// 图片实际大小

				int screenWidth = dm.widthPixels - CommonTools.dip2px(context, 25);

				float p = 1.8f;
				int imgWidth = (int) (drawable.getIntrinsicWidth() * density * p);
				int imgHeight = (int) (drawable.getIntrinsicHeight() * density * p);

				int showWidth = imgWidth;
				int showHeight = imgHeight;

				// 进行等比例缩放程序
				if (imgWidth > screenWidth) {
					showWidth = screenWidth;
					showHeight = (int) (imgHeight * dm.widthPixels / imgWidth);
				}
				drawable.setBounds(0, 0, showWidth, showHeight);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("drawable", drawable);
				map.put("diffValue", showHeight - drawable.getIntrinsicHeight());
				map.put("showHeight", showHeight);
				return map;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		private InputStream fetch(String urlString) throws MalformedURLException, IOException {
			Log.e("imagePath", urlString);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(urlString);
			HttpResponse response = httpClient.execute(request);
			return response.getEntity().getContent();
		}
	}
}