package com.zixueku.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zixueku.R;
/**
 * 
 * @author Crist
 *
 */
public class ToastUtil {
	/**
	 * 屏幕的宽度
	 */
	private static float width;
	/**
	 * 屏幕的高度
	 */
	private static float height;
	/**
	 * 显示对勾的吐司的宽度（单位dip）
	 */
	private static float successDip = 100;
	/**
	 * 显示对勾的吐司宽度转换为像素的宽度值
	 */
	private static float xSuccessWidth;
	/**
	 * 文字吐司的宽度（单位dip）
	 */
	private static float textToastDip = 180;
	/**
	 * 文字吐司宽度转换为像素后的值
	 */
	private static float xTextToastWidth;
	
	private static final int TEXT_SIZE = 16;
	
	/**
	 * 显示吐司
	 * 如果想在子线程中显示吐司，请在调用该方法之前调用Looper.prepare();
	 * 之后调用Looper.loop();
	 * @param viewID 吐司的布局id
	 * @param context 上下文
	 * @param duration 显示的时间
	 */
	public static void showSuccess(Context context,String content,int duration){
		getResolution(context);
		Toast toast = new Toast(context);
		View toast1 = View.inflate(context,R.layout.toast_ok, null);
		TextView tv_info = (TextView) toast1.findViewById(R.id.tv_info);
		if(content!=null){
			tv_info.setText(content);
		}
		toast.setGravity(Gravity.TOP+Gravity.LEFT, (int) ((width-xSuccessWidth)/2), (int) (2*height/5));
		if(duration!=0&&duration!=1)
			duration = 1;
		toast.setDuration(duration); 
		toast.setView(toast1);
		toast.show();
	}
	
	/**
	 * 显示文本吐司
	 * @param context 上下文
	 * @param content 显示的文字信息
	 * @param duration 显示时间
	 */
	@SuppressWarnings("deprecation")
	public static void showTextInTop(Context context,String content,int duration){
		getResolution(context);
		Toast toast = new Toast(context);
		TextView tv = new TextView(context);
		tv.setWidth((int) xTextToastWidth);
		tv.setHeight(dip2px(context,30));
		tv.setGravity(Gravity.CENTER);
		Drawable drawable = context.getResources().getDrawable(R.drawable.shape_background_corner_black);
		tv.setBackgroundDrawable(drawable);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
		tv.setText(content);
		toast.setGravity(Gravity.TOP+Gravity.LEFT, (int) ((width-xTextToastWidth)/2), (int) (1*height/5));
		if(duration!=0&&duration!=1)
			duration = 1;
		toast.setDuration(duration); 
		toast.setView(tv);
		toast.show();
	}
	
	/**
	 * 显示文本吐司
	 * @param context 上下文
	 * @param content 显示的文字信息
	 * @param duration 显示时间
	 */
	@SuppressWarnings("deprecation")
	public static void showTextInMiddle(Context context,String content,int duration){
		getResolution(context);
		Toast toast = new Toast(context);
		TextView tv = new TextView(context);
		tv.setHeight(dip2px(context,30));
		tv.setGravity(Gravity.CENTER);
		Drawable drawable = context.getResources().getDrawable(R.drawable.shape_background_corner_black);
		tv.setBackgroundDrawable(drawable);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
		tv.setText(content);
		toast.setGravity(Gravity.CENTER, 0, 0);
		tv.setPadding(CommonUtil.dip2px(context, 5), 0, CommonUtil.dip2px(context, 5), 0);
		if(duration!=0&&duration!=1)
			duration = 1;
		toast.setDuration(duration);  
		toast.setView(tv);
		toast.show();
	}
	
	@SuppressWarnings("deprecation")
	public static void showTextInMiddle(Context context, int resId, int duration){
		getResolution(context);
		Toast toast = new Toast(context);
		TextView tv = new TextView(context);
		String content = context.getResources().getString(resId);
		tv.setHeight(dip2px(context,30));
		tv.setGravity(Gravity.CENTER);
		Drawable drawable = context.getResources().getDrawable(R.drawable.shape_background_corner_black);
		tv.setBackgroundDrawable(drawable);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
		tv.setText(content);
		toast.setGravity(Gravity.CENTER, 0, 0);
		tv.setPadding(CommonUtil.dip2px(context, 5), 0, CommonUtil.dip2px(context, 5), 0);
		if(duration!=0&&duration!=1)
			duration = 1;
		toast.setDuration(duration);  
		toast.setView(tv);
		toast.show();
	}
	
	/**
	 * 显示文本吐司
	 * @param context 上下文
	 * @param content 显示的文字信息
	 * @param duration 显示时间
	 */
	@SuppressWarnings("deprecation")
	public static void showTextInBottom(Context context,String content,int duration){
		getResolution(context);
		Toast toast = new Toast(context);
		TextView tv = new TextView(context);
		tv.setWidth((int) xTextToastWidth);
		tv.setHeight(dip2px(context,30));
		tv.setGravity(Gravity.CENTER);
		Drawable drawable = context.getResources().getDrawable(R.drawable.shape_background_corner_black);
		tv.setBackgroundDrawable(drawable);
		tv.setTextColor(Color.parseColor("#ffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
		tv.setText(content);
		toast.setGravity(Gravity.TOP+Gravity.LEFT, (int) ((width-xTextToastWidth)/2), (int) (3*height/5));
		if(duration!=0&&duration!=1)
			duration = 1;
		toast.setDuration(duration); 
		toast.setView(tv);
		toast.show();
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 初始化屏幕的宽高信息
	 * @param context
	 */
	private static void getResolution(Context context) {
		xSuccessWidth = dip2px(context, successDip);
		xTextToastWidth = dip2px(context, textToastDip);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
	}
}
