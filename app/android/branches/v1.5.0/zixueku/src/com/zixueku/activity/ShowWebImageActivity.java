package com.zixueku.activity;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zixueku.R;

public class ShowWebImageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webimage);
		String imagePath = getIntent().getStringExtra("image");
		PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);
		ImageLoader.getInstance().displayImage(imagePath, photoView);
		photoView.setOnViewTapListener(new OnViewTapListener() {
			@Override
			public void onViewTap(View arg0, float arg1, float arg2) {
				ShowWebImageActivity.this.finish();
			}
		});
	}
}
