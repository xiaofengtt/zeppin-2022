package com.cmos.chinamobile.media.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Utlity {

	public static final String JSON_DATA = "json";
	public static final String XML_DATA = "xml";
	public static final String TEXT_DATA = "text";
	public static final String basePath = "/home/zyzxfs/server";

	/**
	 * 
	 * @param value
	 *            要检测的String值 是否为空
	 * @return 空返回true
	 */
	public static boolean checkStringNull(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}
		return false;
	}

	/**
	 * 检测排序参数是否合法
	 * 
	 * @author Administrator
	 * @date: 2014年7月25日 下午5:52:10 <br/>
	 * @param param
	 * @return
	 */
	public static boolean checkSortParam(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("\\w*?(-|(-(de|a)sc))?");
		else
			return false;
	}

	/**
	 * @category 判断是否全是数字
	 * @param s
	 *            要判断的字符串
	 * @return true|false
	 */
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}

	/**
	 * @category 验证是否是手机
	 * @param mobiles
	 *            手机号码
	 * @return true|false
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^\\d{11}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取ip
	 * 
	 * @author Administrator
	 * @date: 2014年10月20日 下午3:43:39 <br/>
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String parseHtmlMark(String sb) {
		return sb.replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("&ne;", "≠");
	}

	public static String getUuidPwd() throws InterruptedException {
		String pwdString;
		Thread.sleep(1);// 防止运算过快产生相同密码
		pwdString = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		return pwdString;
	}

	public static String timeSpanToDateString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(ts);
	}

	public static String timeSpanToDateString(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(ts);
	}

	public static String timeSpanToString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}

	public static String timeSpanToString(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}

	public static Date stringToDate(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(date);
	}

	public static Timestamp getCurrentTime(String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!"".equals(format)) {
			defaultFormat = format;
		}
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	
	@SuppressWarnings("static-access")
	public static int getAge(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		calendar.setTime(date);
		int past = calendar.get(calendar.YEAR);
		return year - past;
	}

	public static String getAgeToDate(int age) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -age);
		Date dt = calendar.getTime();
		return timeSpanToDateString(dt);
	}

	/**
	 * 
	 * @author Administrator
	 * @date: 2014年10月29日 下午4:29:35 <br/>
	 * @return
	 */
	public static float getFloat2(float value, int bt) {
		BigDecimal bd = BigDecimal.valueOf(value);
		float f = bd.setScale(bt, BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}

	public static float getFloat2(double value, int bt) {
		BigDecimal bd =  BigDecimal.valueOf(value);
		float f = bd.setScale(bt, BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}

	public static String getFloat2Str(float f) {
		DecimalFormat df = new DecimalFormat();
		String style = "#0%";
		df.applyPattern(style);
		return df.format(f);
	}

	public static String getFloat2Str(double f) {
		DecimalFormat df = new DecimalFormat();
		String style = "#0%";
		df.applyPattern(style);
		return df.format(f);
	}

	public static String getFloat2StrNormal(float f) {
		DecimalFormat dfStr = new DecimalFormat();
		String styleStr = "#0";
		dfStr.applyPattern(styleStr);
		return dfStr.format(f);
	}

	public static String getFloat2StrNormal(double f) {
		DecimalFormat dfStr = new DecimalFormat();
		String styleStr = "#0";
		dfStr.applyPattern(styleStr);
		return dfStr.format(f);
	}

	/**
	 * 生成不带"-"的UUID
	 * 
	 * @author Clark 2014.05.29
	 * @return UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	public static int[] StrArray2IntArray(String[] strs) {
		if (strs == null) {
			return null;
		}
		int[] values = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			values[i] = Integer.valueOf(strs[i]);
		}
		return values;
	}


	public static Integer Boolean2Integer(Boolean bool) {
		if (bool)
			return 1;
		return 0;
	}
}
