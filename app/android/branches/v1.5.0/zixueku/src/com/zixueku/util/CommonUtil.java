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
		showInfoDialog(context, message, "??????", "??????", null);
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
	 * ?????????????????????????????????????????????????????? 0???????????? 1???WIFI 2???CMWAP 3???CMNET
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
	 * ????????????????????????
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
	 * ??????????????????
	 * 
	 * @return ??????????????????????????????yyyy-MM-dd HH:mm:ss
	 */

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * ??????????????????yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * ??????????????????????????? dp ????????? ????????? px(??????)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * ??????????????????????????? px(??????) ????????? ????????? dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * ???sp????????????px??????????????????????????????
	 * 
	 * @param spValue
	 * @param fontScale
	 *            ???DisplayMetrics????????????scaledDensity???
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * ??????????????????
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenPic(Activity context) {
		// ??????????????????
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int[] pics = new int[2];
		// ???????????????
		pics[0] = dm.widthPixels;
		// ????????????
		pics[1] = dm.heightPixels;
		return pics;
	}

	public static int[] getScreenDp(Activity context) {
		// ??????????????????
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int[] dips = new int[2];
		// ???????????????
		dips[0] = px2dip(context, dm.widthPixels);
		// ????????????
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
		canvas.drawBitmap(b, 0, 0, p); // ???????????????????????????Paint????????????

		return new BitmapDrawable(bitmap);
	}

	/** ??????Selector??? ???????????????????????????????????????????????????????????????????????? */
	public static StateListDrawable createSLD(Context context, Drawable drawable) {
		StateListDrawable bg = new StateListDrawable();
		int brightness = 50 - 127;
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
				brightness,// ????????????
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
			when.append(difference_months + "???");
		}

		difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
		if (difference_days > 0) {
			when.append(difference_days + "???");
		}

		difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
		if (difference_hours > 0) {
			when.append(difference_hours + "??????");
		}

		difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
		if (difference_minutes > 0) {
			when.append(difference_minutes + "??????");
		}

		difference_seconds = (int) ((curTime % 60) - (created % 60));
		if (difference_seconds > 0) {
			when.append(difference_seconds + "???");
		}

		return when.append("???").toString();
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// ??????ListView?????????Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()????????????????????????
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // ????????????View ?????????
			totalHeight += listItem.getMeasuredHeight(); // ??????????????????????????????
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()???????????????????????????????????????
		// params.height??????????????????ListView???????????????????????????
		listView.setLayoutParams(params);
	}

	public static String formatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date());
	}

	public static String formatMsgDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy???MM???dd??? HH:mm");
		return sdf.format(new Date(date));
	}

	public static boolean isEmail(String email) {
		if (email == null)
			email = "";
		String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		return email.matches(regex);
	}

	// ???????????????????????????
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][0-9]\\d{9}";// "[1]"?????????1????????????1???"[358]"????????????????????????3???5???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
		return mobiles == null ? false : mobiles.matches(telRegex);
	}

	// ?????????????????????
	public static boolean isNumber(String keys) {
		String telRegex = "[0-9]+";// "[1]"?????????1????????????1???"[358]"????????????????????????3???5???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
		return keys.matches(telRegex);
	}

	// ?????????????????????
	public static boolean isLetter(String keys) {
		String telRegex = "[a-zA-Z]+";// "[1]"?????????1????????????1???"[358]"????????????????????????3???5???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
		return keys.matches(telRegex);
	}

	public static boolean isASII(String keys) {
		String regex = "[\\x00-\\xff]+";
		return keys.matches(regex);
	}

	public static int isMobileOrId(String num) {
		String telRegex = "[1]\\d{10}";// "[1]"?????????1????????????1???"[358]"????????????????????????3???5???8???????????????"\\d{9}"????????????????????????0???9???????????????9??????
		if (num.matches(telRegex)) {
			return 1; // ?????????
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
	 * ????????????????????????????????????
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
	 * ?????????????????????????????????????????????
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
		long d_ms_time = now_ms - ms;// ??? ?????????
		int d_s_time = (int) (d_ms_time / 1000);// ??? ??????
		int min = d_s_time / 60;// ????????????60?????? ?????????
		int hour = min / 60;// ?????????
		int day = hour / 24;// ??????
		if (min <= 0) {
			return "1?????????";
		} else if (min < 60) {
			return min + "?????????";
		} else if (min < 2 * 60) {
			return "1?????????";
		} else if (min < 24 * 60) {
			return hour + "?????????";
		} else if (min < 7 * 24 * 60) {
			return day + "??????";
		} else {
			return formatDateYYYYMMDD(ms);
		}

	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param timeInterval
	 *            ???????????????
	 * @return
	 */
	public static String timeConversion(long timeInterval) {
		long time = System.currentTimeMillis();
		long d_ms_time = time - timeInterval;// ??? ?????????
		int d_s_time = (int) (d_ms_time / 1000);// ??? ??????
		int min = d_s_time / 60;// ????????????60?????? ?????????
		int hour = min / 60;// ?????????
		int day = hour / 24;// ??????
		int yue = day / 30;// ??????
		int nian = day / 365;// ??????
		if (min <= 0) {
			return "1?????????";
		} else if (min < 60) {
			return min + "?????????";
		} else if (min < 2 * 60) {
			return "1?????????";
		} else if (min < 24 * 60) {
			return hour + "?????????";
		} else if (min < 30 * 24 * 60) {
			return day + "??????";
		} else if (min < 12 * 24 * 60 * 30) {
			return yue + "?????????";
		} else if (min < 7 * 24 * 60 * 30 * 12) {
			return nian + "??????";
		} else {
			return formatDateYYYYMMDD(timeInterval);
		}
	}

	public static String getDTimeMMDD(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		long s_time = Long.parseLong(time.trim()) * 1000; // ????????????

		SimpleDateFormat sdf = new SimpleDateFormat("MM???dd???");
		String formatTime = sdf.format(new Date(s_time));

		return formatTime;

	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param time
	 *            ???????????????
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
			return "??????" + formatHHmm(time);
		} else if (time < TODAY_MS) {
			return "??????" + formatHHmm(time);
		} else if (time < TOMORROW_MS) {
			return "??????" + formatHHmm(time);
		} else {
			return "";
		}
	}

	/**
	 * ?????????????????? 19:01 ??????
	 * 
	 * @param ms
	 * @return
	 */
	public static String formatHHmm(long ms) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(ms);
	}

	/**
	 * ??????????????????????????????
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
	 * ????????????????????????????????????
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
	 * ????????????????????????????????????
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
	 * Environment.getExternalStorageState()??????????????????SDCard??????????????????????????????SDCard???
	 * ????????????????????????????????????????????????????????????Environment.MEDIA_MOUNTED???
	 * Environment.getExternalStorageDirectory
	 * ()??????????????????SDCard???????????????????????????SDCard???????????????????????????????????? File sdCardDir = new
	 * File("/sdcard"); //??????SDCard?????? File saveFile = new File(sdCardDir,
	 * "abc.txt"); //??????????????????????????????????????? File saveFile = new File("/sdcard/abc.txt");
	 * FileOutputStream outStream = new FileOutputStream(saveFile);
	 * outStream.write("??????test".getBytes()); outStream.close();
	 */
	public static boolean getSDCardStatus() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// ?????????????????????
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

	// ?????????????????????
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
	 *  ??????values ???String ??????
	 * @param context
	 * @param resId
	 * @return
	 */
	static String getStrng(Context context, int resId) {
		return context.getResources().getString(resId);
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	public static String getTime2() {
		Time t = new Time();
		t.setToNow(); // ?????????????????????
		int year = t.year;
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		int second = t.second;

		return year + "???" + (month + 1) + "???" + date + "???" + "" + hour + ":"
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
	 * ??????Sdcard????????????
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
		// ???????????????????????????//
				.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true)// ????????????????????????????????????SD??????
				.imageScaleType(ImageScaleType.EXACTLY)
				// ????????????????????????
				.cacheInMemory(true).considerExifParams(true).build();

		return options;
	}

}
