package com.zixueku.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class CommonUtil {

	private static AlertDialog dialog;
	private static AlertDialog update_alert;

	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "确定", null);
	}

	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}

	public static String getMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
	 * 
	 * @param context
	 * @return
	 */
	public static int isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return 0;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						NetworkInfo netWorkInfo = info[i];
						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
							return 1;
						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							String extraInfo = netWorkInfo.getExtraInfo();
							if ("cmwap".equalsIgnoreCase(extraInfo)
									|| "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
								return 2;
							}
							return 3;
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
	 */

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 返回的格式为yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取屏幕宽高
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenPic(Activity context) {
		// 取得窗口属性
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int[] pics = new int[2];
		// 窗口的宽度
		pics[0] = dm.widthPixels;
		// 窗口高度
		pics[1] = dm.heightPixels;
		return pics;
	}

	public static int[] getScreenDp(Activity context) {
		// 取得窗口属性
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int[] dips = new int[2];
		// 窗口的宽度
		dips[0] = px2dip(context, dm.widthPixels);
		// 窗口高度
		dips[1] = px2dip(context, dm.heightPixels);
		return dips;
	}

	/**
	 * 
	 * @param context
	 * @param button
	 * @param nornalImageFileName
	 * @param pressedImageFileName
	 * @throws Exception
	 */
	public static void bindViewSelector(Context context, final View view,
			String nornalImageurl, final String pressedImageUrl) {
		final StateListDrawable stateListDrawable = new StateListDrawable();
		final BitmapUtils utils = new BitmapUtils(context);
		utils.display(view, nornalImageurl, new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View container, String uri,
					Bitmap bitmap, BitmapDisplayConfig config,
					BitmapLoadFrom from) {
				Drawable normalDrawable = new BitmapDrawable(bitmap);
				stateListDrawable.addState(
						new int[] { android.R.attr.state_active },
						normalDrawable);
				stateListDrawable.addState(new int[] {
						android.R.attr.state_focused,
						android.R.attr.state_enabled }, normalDrawable);
				stateListDrawable.addState(
						new int[] { android.R.attr.state_enabled },
						normalDrawable);
				utils.display(container, pressedImageUrl,
						new BitmapLoadCallBack<View>() {

							@Override
							public void onLoadCompleted(View container,
									String uri, Bitmap bitmap,
									BitmapDisplayConfig config,
									BitmapLoadFrom from) {
								stateListDrawable.addState(new int[] {
										android.R.attr.state_pressed,
										android.R.attr.state_enabled },
										new BitmapDrawable(bitmap));

								view.setBackgroundDrawable(stateListDrawable);

							}

							@Override
							public void onLoadFailed(View container,
									String uri, Drawable drawable) {
								// TODO Auto-generated method stub

							}
						});
			}

			@Override
			public void onLoadFailed(View container, String uri,
					Drawable drawable) {

			}
		});

	}

	private static Drawable createDrawable(Drawable d, Paint p) {

		BitmapDrawable bd = (BitmapDrawable) d;
		Bitmap b = bd.getBitmap();
		Bitmap bitmap = Bitmap.createBitmap(bd.getIntrinsicWidth(),
				bd.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(b, 0, 0, p); // 关键代码，使用新的Paint画原图，

		return new BitmapDrawable(bitmap);
	}

	/** 设置Selector。 本次只增加点击变暗的效果，注释的代码为更多的效果 */
	public static StateListDrawable createSLD(Context context, Drawable drawable) {
		StateListDrawable bg = new StateListDrawable();
		int brightness = 50 - 127;
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
				brightness,// 改变亮度
				0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });

		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

		Drawable normal = drawable;
		Drawable pressed = createDrawable(drawable, paint);
		bg.addState(new int[] { android.R.attr.state_pressed, }, pressed);
		bg.addState(new int[] { android.R.attr.state_focused, }, pressed);
		bg.addState(new int[] { android.R.attr.state_selected }, pressed);
		bg.addState(new int[] {}, normal);
		return bg;
	}

	public static Bitmap getImageFromAssetsFile(Context ct, String fileName) {
		Bitmap image = null;
		AssetManager am = ct.getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;

	}

	public static String getUploadtime(long created) {
		StringBuffer when = new StringBuffer();
		int difference_seconds;
		int difference_minutes;
		int difference_hours;
		int difference_days;
		int difference_months;
		long curTime = System.currentTimeMillis();
		difference_months = (int) (((curTime / 2592000) % 12) - ((created / 2592000) % 12));
		if (difference_months > 0) {
			when.append(difference_months + "月");
		}

		difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
		if (difference_days > 0) {
			when.append(difference_days + "天");
		}

		difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
		if (difference_hours > 0) {
			when.append(difference_hours + "小时");
		}

		difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
		if (difference_minutes > 0) {
			when.append(difference_minutes + "分钟");
		}

		difference_seconds = (int) ((curTime % 60) - (created % 60));
		if (difference_seconds > 0) {
			when.append(difference_seconds + "秒");
		}

		return when.append("前").toString();
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	public static String formatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date());
	}

	public static String formatMsgDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return sdf.format(new Date(date));
	}

	public static boolean isEmail(String email) {
		if (email == null)
			email = "";
		String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		return email.matches(regex);
	}

	// 判断是否为手机号码
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][0-9]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		return mobiles == null ? false : mobiles.matches(telRegex);
	}

	// 判断是否为数字
	public static boolean isNumber(String keys) {
		String telRegex = "[0-9]+";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		return keys.matches(telRegex);
	}

	// 判断是否为字母
	public static boolean isLetter(String keys) {
		String telRegex = "[a-zA-Z]+";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		return keys.matches(telRegex);
	}

	public static boolean isASII(String keys) {
		String regex = "[\\x00-\\xff]+";
		return keys.matches(regex);
	}

	public static int isMobileOrId(String num) {
		String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (num.matches(telRegex)) {
			return 1; // 手机号
		} else if (num.matches("\\d{8}")) {
			return 2; // id
		}

		return 0;
	}

	public static String formatDateYYYYMMDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	public static String formatDateYYYYMMDD(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(date));
	}

	public static String formatDateYYYYMMDDHHMM(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date(date));
	}

	/**
	 * 判断某个服务是否正在运行
	 * 
	 * @param context
	 * @param serviceName
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String serviceName) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = manager.getRunningServices(100);
		for (RunningServiceInfo info : infos) {
			String name = info.service.getClassName();
			if (serviceName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据时间戳返回与当前时间的时差
	 * 
	 * @param time
	 * @return
	 */
	public static String getDTime(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		long s_time = Long.parseLong(time.trim());
		long ms = s_time;
		long now_ms = System.currentTimeMillis();
		long d_ms_time = now_ms - ms;// 差 毫秒值
		int d_s_time = (int) (d_ms_time / 1000);// 差 秒值
		int min = d_s_time / 60;// 秒值除以60取整 分钟值
		int hour = min / 60;// 小时值
		int day = hour / 24;// 天数
		if (min <= 0) {
			return "1分钟前";
		} else if (min < 60) {
			return min + "分钟前";
		} else if (min < 2 * 60) {
			return "1小时前";
		} else if (min < 24 * 60) {
			return hour + "小时前";
		} else if (min < 7 * 24 * 60) {
			return day + "天前";
		} else {
			return formatDateYYYYMMDD(ms);
		}

	}

	/**
	 * 根据秒数获取时间间隔
	 * 
	 * @param timeInterval
	 *            获取的时间
	 * @return
	 */
	public static String timeConversion(long timeInterval) {
		long time = System.currentTimeMillis();
		long d_ms_time = time - timeInterval;// 差 毫秒值
		int d_s_time = (int) (d_ms_time / 1000);// 差 秒值
		int min = d_s_time / 60;// 秒值除以60取整 分钟值
		int hour = min / 60;// 小时值
		int day = hour / 24;// 天数
		int yue = day / 30;// 月数
		int nian = day / 365;// 月数
		if (min <= 0) {
			return "1分钟前";
		} else if (min < 60) {
			return min + "分钟前";
		} else if (min < 2 * 60) {
			return "1小时前";
		} else if (min < 24 * 60) {
			return hour + "小时前";
		} else if (min < 30 * 24 * 60) {
			return day + "天前";
		} else if (min < 12 * 24 * 60 * 30) {
			return yue + "个月前";
		} else if (min < 7 * 24 * 60 * 30 * 12) {
			return nian + "年前";
		} else {
			return formatDateYYYYMMDD(timeInterval);
		}
	}

	public static String getDTimeMMDD(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		long s_time = Long.parseLong(time.trim()) * 1000; // 转为毫秒

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		String formatTime = sdf.format(new Date(s_time));

		return formatTime;

	}

	/**
	 * 根据时间戳返回时间的通俗表达
	 * 
	 * @param time
	 *            秒值字符串
	 * @return
	 */
	public static String getTimeStr2(String timeStr) {
		if (TextUtils.isEmpty(timeStr)) {
			return "";
		}
		long time = Long.parseLong(timeStr);
		long ONE_DAY_MS = 1 * 24 * 60 * 60 * 1000;
		long TODAY_MS = getTodayMs();
		long BEFOR_YESTODAY_MS = TODAY_MS - (2 * ONE_DAY_MS);
		long YESTODAY_MS = TODAY_MS - ONE_DAY_MS;
		long TOMORROW_MS = TODAY_MS + ONE_DAY_MS;

		if (time < BEFOR_YESTODAY_MS) {
			return formatDateYYYYMMDD(time);
		} else if (time < YESTODAY_MS) {
			return "前天" + formatHHmm(time);
		} else if (time < TODAY_MS) {
			return "昨天" + formatHHmm(time);
		} else if (time < TOMORROW_MS) {
			return "今天" + formatHHmm(time);
		} else {
			return "";
		}
	}

	/**
	 * 格式化时间为 19:01 格式
	 * 
	 * @param ms
	 * @return
	 */
	public static String formatHHmm(long ms) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(ms);
	}

	/**
	 * 获取今日零时的毫秒值
	 * 
	 * @return
	 */
	public static long getTodayMs() {
		long ms = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(ms);
		try {
			Date date = sdf.parse(today);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取当前应用程序的版本号
	 * 
	 * @return
	 */
	public static int getVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return Integer.valueOf(info.versionCode + "");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取当前应用程序的版本名
	 * 
	 * @return
	 */
	public static String getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName + "";
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * Environment.getExternalStorageState()方法用于获取SDCard的状态，如果手机装有SDCard，
	 * 并且可以进行读写，那么方法返回的状态等于Environment.MEDIA_MOUNTED。
	 * Environment.getExternalStorageDirectory
	 * ()方法用于获取SDCard的目录，当然要获取SDCard的目录，你也可以这样写： File sdCardDir = new
	 * File("/sdcard"); //获取SDCard目录 File saveFile = new File(sdCardDir,
	 * "abc.txt"); //上面两句代码可以合成一句： File saveFile = new File("/sdcard/abc.txt");
	 * FileOutputStream outStream = new FileOutputStream(saveFile);
	 * outStream.write("你好test".getBytes()); outStream.close();
	 */
	public static boolean getSDCardStatus() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取屏幕的宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();

		// DisplayMetrics dm = new DisplayMetrics();
		// ((Activity) context).getWindowManager().getDefaultDisplay()
		// .getMetrics(dm);
		// int screenWidth = dm.widthPixels;
	}

	// 获取屏幕的高度
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();

		// DisplayMetrics dm = new DisplayMetrics();
		// ((Activity) context).getWindowManager().getDefaultDisplay()
		// .getMetrics(dm);
		// int screenHeight = dm.heightPixels;
	}

	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {

			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	/**
	 *  获取values 里String 字段
	 * @param context
	 * @param resId
	 * @return
	 */
	static String getStrng(Context context, int resId) {
		return context.getResources().getString(resId);
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static String getTime2() {
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;

		return year + "年" + (month + 1) + "月" + date + "日" + "" + hour + ":"
				+ minute + ":" + second;
	}

	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	public static DisplayImageOptions getOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// 设置图片的解码类型//
				.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.EXACTLY)
				// 是否緩存都內存中
				.cacheInMemory(true).considerExifParams(true).build();

		return options;
	}

}
