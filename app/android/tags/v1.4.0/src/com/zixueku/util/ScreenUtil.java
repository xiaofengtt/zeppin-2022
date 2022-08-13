package com.zixueku.util;

import android.app.Activity;
import android.view.WindowManager;

public class ScreenUtil {

	/**
	 * 
	 * @param activity
	 * @param bUseFullscreen
	 *            true:全屏 false:退出全屏
	 */
	public static void updateFullscreenStatus(Activity activity, Boolean bUseFullscreen) {
		if (bUseFullscreen) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		} else {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

	}

}
