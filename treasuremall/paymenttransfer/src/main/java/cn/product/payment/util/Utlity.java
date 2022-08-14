package cn.product.payment.util;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Utlity {

	public static Logger logger = LoggerFactory.getLogger(Utlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	
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
	 * @param s 要判断的字符串
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
	 * @param data
	 * @return
	 */
	public static boolean isPositiveCurrency(String data) {
		if (data != null && !"".equals(data.trim()))
			return data.matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
		else
			return false;
	}
	
	/**
	 * 十倍正整数
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
	
	public static String numberFormat(Object number, String format){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}
	
	public static String timestampFormat(Timestamp timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timestamp);
	}

	public static String timestampToString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timestampToMinuteString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(ts);
	}
	
	public static String timestampToWechatString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(ts);
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
	
	@SuppressWarnings("static-access")
	public static int getAge(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		calendar.setTime(date);
		int past = calendar.get(calendar.YEAR);
		return year - past;
	}

	/**
	 * 生成不带"-"的UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	public static Integer Boolean2Integer(Boolean bool) {
		if (bool)
			return 1;
		return 0;
	}
	
	/**
	 * 获取6位数字验证码，并组成手机短信验证码
	 * @return
	 */
	public static String getCaptcha(){
		String captcha = "";
		Random random = new Random();//随机类
		for (int i=0; i<6; i++){
			String rand = String.valueOf(random.nextInt(10));
			captcha += rand;
		}
		return captcha;
	}
	
	/**
	 * 对象转Map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {  
		  
        if(obj == null){  
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
	
    /**
     * 获取指定长度的数字随机数
     * @param length
     * @return
     */
	public static String getRomNumStr(int length){
		String romNumStr = "";
		Random random = new Random();//随机类
		for (int i=0; i<length; i++){
			String rand = String.valueOf(random.nextInt(10));
			romNumStr += rand;
		}
		return romNumStr;
	}
	
	/**
	 * 验证接口时间
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static boolean checkTime(long time, long nowTime){
		return ((nowTime - time) > 30000);
	}
}
