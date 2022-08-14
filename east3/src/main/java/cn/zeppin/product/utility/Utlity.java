package cn.zeppin.product.utility;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Utlity {

	public static Logger logger = LogManager.getLogger(Utlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";

	public final static Integer TCTEL_TOTAL_COUNT = 2000;
	public final static String TCTEL_FIR = "86480000";
	
	public final static Integer TOTEL_TOTAL_COUNT = 100;
	public final static String TOTEL_FIR = "89689500";
	
	public final static Integer TOMOBILE_TOTAL_COUNT = 100;
	public final static String TOMOBILE_FIR = "18514809";
	public final static String TOMOBILE_END = "500";
	
	public final static String[] DATA_TYPES = {"TGgGdxxb","TGgJgxxb","TGgYgxxb","TGyGydbgxb","TGyGydbhtb","TGyGydzywb","TGyGyzhxx",
		"TGyGyzjyyjyl","TGyGyzzyyhtxx","TJyQjglxxfzq","TJyQjglxxzq","TJyXtsypz","TJyXtsyqzrxx","TJyXtzjmjjfpl","TJyXtzjyyjyl","TKhDsfhzjgxx",
		"TKhJydsgr","TKhJydsjg","TKhJydsjggdxx","TKhTzgwhtb","TKhXtkhgr","TKhXtkhjg","TKjGynbkmdzb","TKjGyzcfzkmtjb","TKjGyzzkjqkmb",
		"TKjXtnbkmdzb","TKjXtxmzcfztjb","TKjXtxmzzkjqkmb","TXmFdcjsxmxx","TXmFfdcjsxmxx","TXmXtdbgxb","TXmXtdbhtb","TXmXtdzywb",
		"TXmXtxmqsxx","TXmXtxmsyqb","TXmXtxmxx","TXmXtxmyjhklypgb","TXmXtzhxx","TXmXtzjmjhtxx","TXmXtzjyyhtxx"};
	/**
	 * 
	 * @param value
	 *            要检测的String值 是否为空
	 * @return 空返回true
	 */
	public static boolean checkStringNull(String value) {
		if (value == null || value.equals("")) {
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
			return s.matches("^([+-]?\\d+)(\\.\\d+)?$");
		else
			return false;
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param data
	 * @return
	 */
	public final static boolean isInteger(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[+-]?[0-9]+$");
		else
			return false;
	}

	/**
	 * 判断是否为正整数
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isPositiveInteger(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[0-9]*[1-9][0-9]*$");
		else
			return false;
	}

	/**
	 * 货币（含有二位小数）
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isCurrency(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[+-]?\\d+(\\.\\d{1,2})+|[+-]?0$");
		else
			return false;
	}

	/**
	 * 正数货币（含有二位小数）
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isPositiveCurrency(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^(([1-9]+)|([0-9]+\\.[0-9]{1,2}))$");
		else
			return false;
	}

	/**
	 * 十倍正整数
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isTenTimesPositiveInteger(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[1-9]\\d*0$");
		else
			return false;
	}

	/**
	 * 百倍正整数
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isHundredTimesPositiveInteger(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[1-9]\\d*00$");
		else
			return false;
	}

	/**
	 * 千倍正整数
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isThousandTimesPostitveInteger(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[1-9]\\d*000$");
		else
			return false;
	}

	/**
	 * 万倍正整数
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isTenThousandTimesPositiveInteger(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^[1-9]\\d*0000$");
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
	public static boolean isDateFormat(String str) {
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

	/**
	 * 验证身份证
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean checkIdCard(String idcard) {
		String str = IDCardUtil.IDCardValidate(idcard);
		if (str.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static String parseHtmlMark(String sb) {
		return sb.replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("&ne;", "≠");
	}

	public static String getUuidPwd() throws InterruptedException {
		String pwdString = null;
		Thread.sleep(1);// 防止运算过快产生相同密码
		pwdString = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		return pwdString;
	}

	public static String timeSpanToDateSecondString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(ts);
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

	public static String timeSpanToStringLess(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(ts);
	}

	public static String timeSpanToStringLess(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(ts);
	}

	public static Date stringToDate(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(date);
	}

	public static Timestamp getCurrentTime(String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!format.equals("")) {
			defaultFormat = format;
		}
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	
	public static String getCurrentTimeStr(String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!format.equals("")) {
			defaultFormat = format;
		}
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String time = df.format(new Date());
		return time;
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
		BigDecimal bd = new BigDecimal(value);
		float f = bd.setScale(bt, BigDecimal.ROUND_HALF_UP).floatValue();
		bd = null;
		return f;
	}

	public static float getFloat2(double value, int bt) {
		BigDecimal bd = new BigDecimal(value);
		float f = bd.setScale(bt, BigDecimal.ROUND_HALF_UP).floatValue();
		bd = null;
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

	public static String numInx2Str(int inx) {
		switch (inx) {
		case 0:
			return "零";
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";
		default:
			return "零";
		}
	}

	public static Integer Boolean2Integer(Boolean bool) {
		if (bool)
			return 1;
		return 0;
	}

	public static String getIsFree(Boolean isFree) {
		if (isFree) {
			return "免费";
		} else {
			return "付费";
		}
	}

	/**
	 * 日期格式补全
	 * 
	 * @return
	 */
	public static String getFullTime(String time) {
		if (Utlity.isDateFormat(time)) {
			time += " 00:00:00";
		}
		return time;
	}

	/**
	 * 获取6位数字验证码，并组成手机短信验证码
	 * 
	 * @return
	 */
	public static String getCaptcha() {
		String captcha = "";
		Random random = new Random();// 随机类
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			captcha += rand;
		}
		// String sms = "您本次操作的验证码为："+captcha+",本次验证码5分钟后失效！";
		return captcha;
	}

	/**
	 * 对象转Map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}
	
	public static String getMimeType(String url) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();   
		String type = fileNameMap.getContentTypeFor(url);
		return type;
	}
	
	public static Map<String,Object> removeEmpty(Map<String,Object> map){
		if(map != null){
			List<String> keyList = new ArrayList<String>();
			for(String key : map.keySet()){
				if(map.get(key) == null){
					keyList.add(key);
				}
			}
			for(String key : keyList){
				map.remove(key);
			}
		}
		return map;
	}
	
	// 将字CLOB转成STRING类型   
    public static String ClobToString(Clob clob){   
    	if(clob != null){
    		String reString = "";   
        	java.io.Reader is;
			try {
				is = clob.getCharacterStream();
				BufferedReader br = new BufferedReader(is);   
	        	String s = br.readLine();   
	        	StringBuffer sb = new StringBuffer();   
	        	while (s != null) {
	        		sb.append(s);   
	        		s = br.readLine();   
	        	}   
	        	reString = sb.toString();   
	        	return reString; 
			} catch (SQLException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    } 
    
    // 将字STRING转成CLOB类型   
    public static Clob StringToClob(String string){
    	if(string != null){
    		try {
				return new SerialClob(string.toCharArray());
			} catch (SerialException e) {
				return null;
			} catch (SQLException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    public static String getStringForSql(List<String> list){
    	String str = "('-999999',";
		for(String parent : list){
			str = str + "'"+ parent+"',";
		}
		str = str.substring(0, str.length() - 1);
		str = str + ")";
    	
    	return str;
    }
    
    /**
     * 通过表名获取中文表名
     * @param key
     * @return
     */
    public static String getTableName(String key) {
    	return TableValues.tables.get(key) == null ? "" : TableValues.tables.get(key);
    }
}
