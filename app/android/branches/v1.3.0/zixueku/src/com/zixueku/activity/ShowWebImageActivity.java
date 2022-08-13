package com.zixueku.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zixueku.R;
import com.zixueku.listerner.OnImageTouchedListener;
import com.zixueku.widget.ZoomableImageView;

public class ShowWebImageActivity extends Activity {
	private TextView imageTextView = null;
	private String imagePath = null;
	private ZoomableImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webimage);
		this.imagePath = getIntent().getStringExtra("image");
		this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
		imageTextView.setText(this.imagePath);
		imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);

		ImageLoader.getInstance().loadImage(this.imagePath, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				imageView.setImageBitmap(loadedImage);
			}
		});
		
		imageView.setOnImageTouchedListener(new OnImageTouchedListener() {
			
			@Override
			public void onImageTouched() {
				ShowWebImageActivity.this.finish();
			}
		});
	}
}
