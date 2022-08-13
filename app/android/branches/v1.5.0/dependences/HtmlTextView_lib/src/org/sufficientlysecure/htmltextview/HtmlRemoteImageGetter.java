/*
 * Copyright (C) 2013 Antarix Tandon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class HtmlRemoteImageGetter implements ImageGetter {
    Context c;
    View container;
    URI baseUri;

    /**
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     */
    public HtmlRemoteImageGetter(View t, Context c, String baseUrl) {
        this.c = c;
        this.container = t;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable which will asynchronously load the image specified in the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        UrlDrawable urlDrawable;
        String source;

        public ImageGetterAsyncTask(UrlDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result == null) {
                Log.w(HtmlTextView.TAG, "Drawable result is null! (source: " + source + ")");
                return;
            }
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 + result.getIntrinsicHeight());

            // change the reference of the current drawable to the result from the HTTP call
            urlDrawable.drawable = result;
           
            TextView textView = (TextView) HtmlRemoteImageGetter.this.container;
            textView.setText(textView.getText());
            //HtmlRemoteImageGetter.this.container.setText(HtmlRemoteImageGetter.this.container.getText());
           // final LayoutParams lp = HtmlRemoteImageGetter.this.container.getLayoutParams();
          
          //  lp.width =HtmlRemoteImageGetter.this.container.getWidth();
          //  lp.height=HtmlRemoteImageGetter.this.container.getHeight();
           //HtmlRemoteImageGetter.this.container.setLayoutParams(lp);
            
            // redraw the image by invalidating the container
            //((TextView)(HtmlRemoteImageGetter.this.container)).setMovementMethod(ScrollingMovementMethod.getInstance());
            
          HtmlRemoteImageGetter.this.container.invalidate();
        }

        /**
         * Get the Drawable from URL
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");
                
                TypedValue typedValue = new TypedValue();
                typedValue.density = TypedValue.DENSITY_DEFAULT;
              
                DisplayMetrics dm = c.getResources().getDisplayMetrics();  
        		int dwidth = dm.widthPixels;//padding left + padding right 
        		float dheight = (float)drawable.getIntrinsicHeight()*(float)dwidth/(float)drawable.getIntrinsicWidth();
        		int dh = (int)(dheight+0.5);
        		int wid = dwidth;
                int hei = dh;
                
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws IOException {
        	
        	String urls = null;
    		try {
    			urls = new String(urlString.trim().replace(" ", "%20").replace("|", "%7C").replace("[", "%5B").replace("]", "%5D"));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	
            URL url;
            if (baseUri != null) {
                url = baseUri.resolve(urls).toURL();
            } else {
                url = URI.create(urls).toURL();
            }

            return (InputStream) url.getContent();
        }
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
} 
