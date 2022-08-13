package com.zixueku.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
public class URLImageParser2 implements ImageGetter {
	TextView textView;

	Context context;

	/***
	 * Construct the URLImageParser which will execute AsyncTask and refresh the
	 * container
	 * 
	 * @param t
	 */
	public URLImageParser2(TextView t, Context context) {
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

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		URLDrawable urlDrawable;

		public ImageGetterAsyncTask(URLDrawable d) {
			this.urlDrawable = d;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// String source = params[0];
			String imageUrl = CommonTools.convertURL(params[0]);
			return fetchDrawable(imageUrl);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			// set the correct bound according to the result from HTTP call
			if (result == null) {
				return;
			}
			
			Drawable dra = result;
			urlDrawable.setBounds(0, 0, 0 + dra.getIntrinsicWidth(), 0 + dra.getIntrinsicHeight());
			urlDrawable.setDrawable(dra);
			
		//	URLImageParser2.this.contactsLayout.invalidate();
		}

		/***
		 * Get the Drawable from URL
		 * 
		 * @param urlString
		 * @return
		 */
		public Drawable fetchDrawable(String urlString) {
			
			
			
			try {
				InputStream is = fetch(urlString);
				Drawable drawable = Drawable.createFromStream(is, "src");
				DisplayMetrics dm = context.getResources().getDisplayMetrics();
				
				TypedValue typedValue = new TypedValue();
	            typedValue.density = TypedValue.DENSITY_DEFAULT;
	           
	            
	    		int dwidth = dm.widthPixels-10;//padding left + padding right 
	    		float dheight = (float)drawable.getIntrinsicHeight()*(float)dwidth/(float)drawable.getIntrinsicWidth();
	    		int dh = (int)(dheight+0.5);
	    		int wid = dwidth;
	            int hei = dh;
	            /*int wid = drawable.getIntrinsicWidth() > dwidth? dwidth: drawable.getIntrinsicWidth();
	            int hei = drawable.getIntrinsicHeight() > dh ? dh: drawable.getIntrinsicHeight();*/
	            drawable.setBounds(0, 0, wid, hei);
				
				return drawable;
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