package cn.product.treasuremall.util;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
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
	
	public static final String KEY_FRONT = "treasuremall_front";
	public static final String KEY_FRONT_MD5 = "8279a48ec7c8a6303bde3f979ce77a9d";
	
	public static final String DEVICE_APP = "01";
	public static final String DEVICE_BACKADMIN = "02";
	
	public static final String BILL_DEVICE_APP = "1";
	public static final String BILL_DEVICE_BACKADMIN = "2";
	
	public static final String BILL_PAYTYPE_WECHAT = "1";//微信
	public static final String BILL_PAYTYPE_ALIPAY_PER = "2";//支付宝_个人
	public static final String BILL_PAYTYPE_ALIPAY_COM = "3";//支付宝_企业
	public static final String BILL_PAYTYPE_BANKCARD_PER = "4";//银行卡_个人
	public static final String BILL_PAYTYPE_BANKCARD_COM = "5";//银行卡_企业
	public static final String BILL_PAYTYPE_REAPAL = "6";//融宝支付
	public static final String BILL_PAYTYPE_SCORE = "9";//未走三方，内部划账
	
	public static final String BILL_PURPOSE_WITHDRAW = "1";//提现
	public static final String BILL_PURPOSE_RECHARGE = "2";//充值
	public static final String BILL_PURPOSE_SCORE = "9";//未走三方，内部划账
	
	
	public static String SERVER_IP = "";
	
	//public static String QUIT_URL_ALI_WAP = PATH_URL+"/index.html"; 
	public static String QUIT_URL_ALI_WAP = "https://www.baidu.com";
	public static String QUIT_URL_ALI_WAP_TEST = "#/my/alipay";
	
	public static final List<String> roles = new ArrayList<String>() {

		private static final long serialVersionUID = 4379411198309099989L;
		{
			add("002170ff-082d-412f-a8a4-b021183fa365");
			add("202170ff-082d-412f-a8a4-b021183fa319");
			add("302170ff-082d-412f-a8a4-b021183fa318");
		}
	};
	
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
	
	public static String DateToDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static String timeSpanToPointString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timeSpanToPointString(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timeSpanToPointDateString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(ts);
	}
	
	public static String timeSpanToPointDateString(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
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
	
	public static String timeSpanToChinaString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaString(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaDateString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaDateString(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringLess(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringLess(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringes(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringes(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesmore(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesmore(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesLess(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesLess(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesLessYear(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesLessYear(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesLessMonth(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("M");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringesLessMonth(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("M");
		return sdf.format(ts);
	}
	
	public static String timeSpanToDateStringLess(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(ts);
	}
	
	public static String timeSpanToDateStringLess(Object ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(ts);
	}


	public static Date stringToDate(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(date);
	}
	
	public static Date stringToDatetime(String datetime) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.parse(datetime);
	}
	
	public static Date stringToDateFull(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
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
	 * 
	 * @author Clark 2014.05.29
	 * @return UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
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
	
	/**
	 * 日期格式补全
	 * @return
	 */
	public static String getFullTime(String time) {
		if(Utlity.isDateFormat(time)){
			time += " 00:00:00";
		}
		return time;
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
	 * 特殊字符转义
	 * @param str
	 * @return
	 */
	public static String strEscape (String str){
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\t", "    ");
		str = str.replaceAll("\r\n", "\n");
		str = str.replaceAll("\n", "<br/>");
		str = str.replaceAll("'", "&#39;");
		str = str.replaceAll("\\(", "&#40;");
		str = str.replaceAll("\\)", "&#41;");
		str = str.replaceAll("\\\\", "&#92;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("eval\\((.*)\\)", "");
		str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		str = str.replaceAll("script", "");
		str = str.replaceAll("\\}", "}").trim();
		
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("¥", "&yen;");
		str = str.replaceAll("§", "&sect;");
		str = str.replaceAll("‰", "&permil;");
		str = str.replaceAll("≤", "&le;");
		str = str.replaceAll("≥", "&ge;");
		str = str.replaceAll("≈", "&asymp;");
		str = str.replaceAll("——", "&mdash;");
//		str = str.replaceAll("”", "&rdquo;");
//		str = str.replaceAll("“", "&ldquo;");
//		str = str.replaceAll("’", "&rsquo;");
//		str = str.replaceAll("‘", "&lsquo;");
		return str;
	}
	
	/**
	 * 特殊字符反转义
	 * @param str
	 * @return
	 */
	public static String strDscape (String str){
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("    ", "\t");
		str = str.replaceAll("\n", "\r\n");
		str = str.replaceAll("<br/>", "\n");
		str = str.replaceAll("&#39;", "'");
		str = str.replaceAll("&#40;", "\\(");
		str = str.replaceAll("&#41;", "\\)");
		str = str.replaceAll("&#92;", "\\\\");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&times;", "×");
		str = str.replaceAll("&divide;", "÷");
		str = str.replaceAll("}", "\\}").trim();
		
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&yen;", "¥");
		str = str.replaceAll("&sect;", "§");
		str = str.replaceAll("&permil;", "‰");
		str = str.replaceAll("&le;", "≤");
		str = str.replaceAll("&ge;", "≥");
		str = str.replaceAll("&asymp;", "≈");
		str = str.replaceAll("&rdquo;", "”");
		str = str.replaceAll("&ldquo;", "“");
		str = str.replaceAll("&rsquo;", "’");
		str = str.replaceAll("&lsquo;", "‘");
		str = str.replaceAll("&mdash;", "——");
		return str;
	}
	
	/**
	 * 数字自动进位
	 * 10000==1万 100000000==1亿
	 * @param number
	 * @return
	 */
	public static String numFormat4Unit(BigDecimal number){
		String formatStr = "0";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);//去掉逗号
		
		//格式化过程
		if(number != null){
			if(number.compareTo(BigDecimal.valueOf(100000000)) == 1 || number.compareTo(BigDecimal.valueOf(100000000)) == 0){
				BigDecimal num = number.divide(BigDecimal.valueOf(100000000));
				Integer scaleNum = num.scale();//获取小数位数
				nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
				formatStr = nf.format(number.divide(BigDecimal.valueOf(100000000)))+"亿";
			} else if (number.compareTo(BigDecimal.valueOf(10000)) == 1 || number.compareTo(BigDecimal.valueOf(10000)) == 0) {
				BigDecimal num = number.divide(BigDecimal.valueOf(10000));
				Integer scaleNum = num.scale();//获取小数位数
				nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
				formatStr = nf.format(number.divide(BigDecimal.valueOf(10000)))+"万";
			} else {
				Integer scaleNum = number.scale();//获取小数位数
				nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
				formatStr = nf.format(number);
			}
		}
		
		return formatStr;
	}
	
	/**
	 * 数字自动进位
	 * 10000==1万 100000000==1亿
	 * @param number
	 * @return
	 */
	public static Map<String, String> numFormat4UnitMap(BigDecimal number){
		Map<String, String> formatMap = new HashMap<String, String>();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);//去掉逗号
		String numStr = "0";
		String unitStr = "";
		//格式化过程
		if(number != null){
			if(number.compareTo(BigDecimal.valueOf(100000000)) == 1 || number.compareTo(BigDecimal.valueOf(100000000)) == 0){
				BigDecimal num = number.divide(BigDecimal.valueOf(100000000));
				Integer scaleNum = num.scale();//获取小数位数
				nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
				numStr = nf.format(num);
				unitStr = "亿";
			} else if (number.compareTo(BigDecimal.valueOf(10000)) == 1 || number.compareTo(BigDecimal.valueOf(10000)) == 0) {
				BigDecimal num = number.divide(BigDecimal.valueOf(10000));
				Integer scaleNum = num.scale();//获取小数位数
				nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
				numStr = nf.format(num);
				unitStr = "万";
			} else {
				Integer scaleNum = number.scale();//获取小数位数
				nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
				numStr = nf.format(number);
				unitStr = "";
			}
		}
		formatMap.put("numStr", numStr);
		formatMap.put("unitStr", unitStr);
		return formatMap;
	}
	
	/**
	 * 货币格式化（逗号）保留两位小数
	 * @param number
	 * @return
	 */
	public static String numFormat4UnitDetail(BigDecimal number){
		String formatStr = "0.00";
		NumberFormat nf = NumberFormat.getInstance();
		
		//格式化过程
		if(number != null){
			Integer scaleNum = 2;//获取小数位数
			nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
			formatStr = nf.format(number);
		}
		
		if(formatStr.indexOf(".") > -1){
			if(formatStr.length() - formatStr.indexOf(".") == 2){
				formatStr+="0";
			}
		} else {
			formatStr+=".00";
		}
		return formatStr;
	}
	
	/**
	 * 货币格式化（逗号）
	 * @param number
	 * @return
	 */
	public static String numFormat4UnitDetailLess(BigDecimal number){
		String formatStr = "0.00";
		NumberFormat nf = NumberFormat.getInstance();
		
		//格式化过程
		if(number != null){
			Integer scaleNum = 2;//获取小数位数
			nf.setMaximumFractionDigits(scaleNum);//设置格式化精度
			formatStr = nf.format(number);
		}
		return formatStr;
	}
	
    /**
     * 获取*号加密的身份证号
     * @param idcard
     * @return
     */
    public static String getStarIdcard(String idcard){
    	
    	StringBuilder sb = new StringBuilder();
    	if(idcard != null && !"".equals(idcard)){
    		if(idcard.length() >= 15){//身份证
        		idcard = idcard.trim();
        		sb.append(idcard.substring(0, 3));//截取前四位身份证号码显示
            	//对于不同长度的身份证号，要有不同的处理方案(目前只针对18位和15位长度的身份证号)
            	if(idcard.length()>15){
            		sb.append("***********");
                	sb.append(idcard.substring(14, idcard.length()));//截取后四位身份证号码显示
            	}else{
            		sb.append("********");
                	sb.append(idcard.substring(11, idcard.length()));//截取后四位身份证号码显示
            	}
    		} else {//台胞证等
    			idcard = idcard.trim();
        		sb.append(idcard.substring(0, 3));//截取前四位身份证号码显示
    			sb.append("***");
            	sb.append(idcard.substring(6, idcard.length()));//截取后四位身份证号码显示
    		}

    	}else{
    		return "";
    	}
    	
    	return sb.toString();
    }
    
    /**
     * 获取*号加密的手机号
     * @param mobile
     * @return
     */
    public static String getStarMobile(String mobile){
    	
    	StringBuilder sb = new StringBuilder();
    	if(mobile != null && !"".equals(mobile)){
    		mobile = mobile.trim();
    		sb.append(mobile.substring(0, 3));//截取前四位身份证号码显示
    		sb.append("****");
        	sb.append(mobile.substring(7, mobile.length()));//截取后四位身份证号码显示
    	}else{
    		return "";
    	}
    	
    	return sb.toString();
    }
    
    /**
     * 获取*号加密的姓名
     * @param mobile
     * @return
     */
    public static String getStarName(String name){
    	
    	StringBuilder sb = new StringBuilder();
    	if(name != null && !"".equals(name)){
    		name = name.trim();
    		sb.append(name.substring(0, 1));//截取前四位身份证号码显示
    		Integer length = name.length();
    		if((length&1)!=0){//奇数
    			length = length/2 + 1;
    		} else {
    			length = length/2;
    		}
    		for(int i = 0; i<length; i++){
    			sb.append("*");
    		}
    	}else{
    		return "";
    	}
    	
    	return sb.toString();
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
	 * 生成唯一订单号
	 * @param from 订单来源
	 * @return
	 */
	public static String getOrderNumStr(String device, String type, String purpose){
		String businessCode = device+type+purpose;
		String orderNumStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String rom = getRomNumStr(3);
		long ctime = System.currentTimeMillis();
		String time = sdf.format(new Timestamp(ctime));
		orderNumStr = businessCode+time+rom;
		return orderNumStr;
	}
	
	/**
	 * 验证接口时间
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static boolean checkTime(long time, long nowTime){
		return ((nowTime - time) > 60000);
	}
	
	/**
	 * 验证接口时间
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static boolean checkLessTime(long time, long nowTime){
		return ((nowTime - time) > 30000);
	}
	
	/**
	 * 验证短信验证码时间
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static boolean checkCodeTime(long time, long nowTime){
		return ((nowTime - time) > 300000);
	}
	
	/**
	 * 获取两个日期之前的全部天数
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	/**
	 * 获取两个日期之前的全部天数
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<Date> findMonthDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	public static String getDurationStr(String starttime, String duration){
		String result = "";
		Timestamp time = Timestamp.valueOf(starttime+" 00:00:00");
		Date start = new Date(time.getTime());
		
		Calendar c = Calendar.getInstance();  
        c.setTime(start);  
        c.add(Calendar.MONTH, Integer.parseInt(duration));
		
        Date end = c.getTime();
        result+=timeSpanToChinaStringesmore(start);
		result+="-"+timeSpanToChinaStringesmore(end);
		
		return result;
	}
	
	public static String timeRomeToBeijng(String timeStr) throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
		Date time = format.parse(timeStr);
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf.format(time);
	}
	
}
