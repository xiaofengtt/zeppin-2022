package com.zixueku.widget;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-19 上午11:08:41
 */
public class URLDrawable extends BitmapDrawable {
	// the drawable that you need to set, you could set the initial drawing
	// with the loading image if you need to
	private Drawable drawable;

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	@Override
	public void draw(Canvas canvas) {
		// override the draw to facilitate refresh function later
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}
}
