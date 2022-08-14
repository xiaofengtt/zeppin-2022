package cn.zeppin.product.utility;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.zeppin.product.utility.reapal.data.PoundageFee4Pay;



public class Utlity {

	public static Logger logger = LogManager.getLogger(Utlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	
//	public static final String PATH_URL = "https://api.niutoulicai.com"; 
//	public static final String PATH_URL = "https://7d8c6c61.ngrok.io/NTB/rest";//120测试
	public static final String PATH_URL = "https://account.qicaibao.vip/rest";//生产测试
//	public static final String PATH_URL = "https://10ecec26.ngrok.io/NTB/rest";//本地测试
	
	public static String SERVER_IP = "";
	public static String NOTIFY_URL = PATH_URL+"/web/notice/notice";
	public static String NOTIFY_URL_BUY = PATH_URL+"/web/notice/buyNotice";
	
	public static String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
	public static String WX_APPID = "wxd017cbdd8dc924cc"; 
	public static String WX_APPSECRET = "6850d88551b414d66cc047b3ef392a4d";
	public static String WX_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String WX_MCH_ID = "1487029092";
	public static String WX_KEY = "855786196b27ef0b4a066ffafdab11ed";//5bb314fa1c9f2f5e81fdc05d69a84d89
	
	public static String WX_PAYCHECK_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	public static String WX_PAYCLOSE_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	/**商户 App ID*/
	public static final String ALI_APPID = "2016071901635869";
	/**蚂蚁开放平台网关地址*/
	public static final String ALI_URL = "https://openapi.alipay.com/gateway.do";
	/**商户私钥*/
	public static final String APP_PRIVATE_KEY = "";
	/**蚂蚁公钥*/
	public static final String ALIPAY_PUBLIC_KEY = "$ALIPAY_PUBLIC_KEY";
	
	public static final String DEVICE_NUMBER_WECHAT = "01";
	public static final String DEVICE_NUMBER_ANDROID = "02";
	public static final String DEVICE_NUMBER_IOS = "03";
	public static final String DEVICE_NUMBER_WEB = "04";
	public static final String DEVICE_NUMBER_QCB_WECHAT = "05";
	public static final String DEVICE_NUMBER_SHBX_WEB = "06";

	public static final String KEY = "27739700ee0bf2930cd62d72a80def0a";
	public static final String KEY_QCB = "acd50ce3f96114043e138b04074eeb40";
	public static final String KEY_QCB_WECHAT_AUTH = "099290dd1e24448db81bec66570fbaed";
	
	public static final String WECHAT_AUTH_CODE_BIND = "01";
	public static final String WECHAT_AUTH_CODE_PAYROLL = "02";
	/**
	 * type 支付方式 balance-余额 wechart-微信 alipay-支付宝 bankcard-银行卡
	 */
	public static final String PAY_TYPE_BALANCE = "balance";
	public static final String PAY_TYPE_WECHART = "wechart";
	public static final String PAY_TYPE_ALIPAY = "alipay";
	public static final String PAY_TYPE_BANKCARD = "bankcard";
	
	/**
	 * 订单类型编码
	 */
	public static final String BILL_DEVICE_WECHAT = "1";//微信
	public static final String BILL_DEVICE_ANDROID = "2";//安卓
	public static final String BILL_DEVICE_IOS = "3";//IOS
	public static final String BILL_DEVICE_WEB = "4";//网页
	public static final String BILL_DEVICE_OHER = "5";//其他
	public static final String BILL_DEVICE_COMPANY = "6";//企业
	public static final String BILL_DEVICE_ALIPAY = "7";//支付宝
	public static final String BILL_DEVICE_REDPACKET = "8";//红包
	public static final String BILL_DEVICE_QCB = "9";//企财宝
	public static final String BILL_DEVICE_SHBX = "A";//社保熊
	
	public static final String BILL_PAYTYPE_BALANCE = "1";//余额
	public static final String BILL_PAYTYPE_WECHART = "2";//微信
	public static final String BILL_PAYTYPE_ALIPAY = "3";//支付宝
	public static final String BILL_PAYTYPE_BANKCARD = "4";//银行卡
	public static final String BILL_PAYTYPE_OHER = "0";//其他
	public static final String BILL_PAYTYPE_CHANPAY = "5";//畅捷支付
	public static final String BILL_PAYTYPE_REAPAL = "6";//融宝支付
	public static final String BILL_PAYTYPE_NTLC = "9";//未走三方，内部划账
	
	public static final String BILL_PURPOSE_WITHDRAW = "1";//提现
	public static final String BILL_PURPOSE_INCOME = "2";//充值
	public static final String BILL_PURPOSE_BUY = "3";//购买
	public static final String BILL_PURPOSE_DIVIDEND = "4";//结息
	public static final String BILL_PURPOSE_RETURN = "5";//返还本金
	public static final String BILL_PURPOSE_OTHER = "0";//其他
	public static final String BILL_PURPOSE_CHANPAY_BINDING = "6";//畅捷支付绑卡
	public static final String BILL_PURPOSE_REDPACKET = "7";//现金红包
	public static final String BILL_PURPOSE_NTLC = "9";//未走三方，内部划账
	public static final String BILL_PURPOSE_REPAYMENT = "8";//还款
	
	public static final String SEX_MAN = "man";//性别男
	public static final String SEX_WOMAN = "woman";//性别女
	
	public static final BigDecimal POUNDAGE_FEE_WECHAT = BigDecimal.valueOf(Double.parseDouble("0.006"));
	
	public static final BigDecimal MAX_RED_PACKET = BigDecimal.valueOf(18.88);
	
	public static final BigDecimal REAPAL_MAX_WITHDRAW = BigDecimal.valueOf(5000000);
	
	public static final BigDecimal WITHDRAW_POUNDAGE = BigDecimal.valueOf(1.0);
	
	public static final BigDecimal POUNDAGE_FEE_QCB_COMPANY = BigDecimal.valueOf(Double.parseDouble("0.0025"));
	
	public static final BigDecimal WITHDRAW_POUNDAGE_QCB = BigDecimal.TEN;
	
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
			return data.matches("^(([1-9]+)|([0-9]+\\.[0-9]{1,2}))$");
		else
			return false;
	}
	
	/**
	 * 正数货币（含有二位小数）
	 * @param data
	 * @return
	 */
	public static boolean isPositiveCurrency4Web(String data) {
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

	public static String parseHtmlMark(String sb) {
		return sb.replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("&ne;", "≠");
	}

	public static String getUuidPwd() throws InterruptedException {
		String pwdString = null;
		Thread.sleep(1);// 防止运算过快产生相同密码
		pwdString = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		return pwdString;
	}

	public static String timeSpanToDateString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(ts);
	}
	
	public static String timeSpanToPointString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(ts);
	}

	public static String timeSpanToPointDateString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		if (bool)
			return 1;
		return 0;
	}
	
	public static String getIsFree(Boolean isFree) {
		if(isFree){
			return "免费";
		}else{
			return "付费";
		}
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
//		String sms = "您本次操作的验证码为："+captcha+",本次验证码5分钟后失效！";
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
     * 获取*号加密的企业账户
     * @param accountNum
     * @return
     */
    public static String getStarAccountNum(String accountNum){
    	
    	StringBuilder sb = new StringBuilder();
    	if(accountNum != null && !"".equals(accountNum)){
    		accountNum = accountNum.trim();
    		sb.append(accountNum.substring(0, 4));//截取前四位身份证号码显示
    		sb.append(" **** **** ");
        	sb.append(accountNum.substring(accountNum.length() - 3, accountNum.length()));//截取后四位身份证号码显示
    	}else{
    		return "";
    	}
    	
    	return sb.toString();
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
	
	public static String getDeviceStr(String token) {
		String deviceNumber = token.substring(0,2);
		String device = Utlity.BILL_DEVICE_OHER;
		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){
			device = Utlity.BILL_DEVICE_WECHAT;
		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber)) {
			device = Utlity.BILL_DEVICE_ANDROID;
		} else if (Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
			device = Utlity.BILL_DEVICE_IOS;
		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
			device = Utlity.BILL_DEVICE_WEB;
		}
		return device;
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
//		return ((System.currentTimeMillis() - time) > 10000);
		return ((nowTime - time) > 30000);
	}
	
	/**
	 * 验证短信验证码时间
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static boolean checkCodeTime(long time, long nowTime){
//		return ((System.currentTimeMillis() - time) > 10000);
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
	
	public static String getMerchantId(Integer count) {
		String merchantId = "";
		Integer date = 350000;
		String romStr = getRomNumStr(2);
		merchantId = String.valueOf(date)+romStr;
		
		date = Integer.parseInt(merchantId);
		date+=count;
		return String.valueOf(date);
	}
	
	/**
	 * 企财宝企业提现 获取提现手续费
	 * 千分之二点五 手续费  最低10元
	 * @param price
	 * @return
	 */
	public static BigDecimal getPoundage4QcbWithdraw(BigDecimal price) {
		
		BigDecimal poundage = price.multiply(POUNDAGE_FEE_QCB_COMPANY);
		if(WITHDRAW_POUNDAGE_QCB.compareTo(poundage) > -1){
			return WITHDRAW_POUNDAGE_QCB;
		} else {
			return poundage.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
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
	
	/**
	 * 根据不同银行获取快捷支付手续费
	 * @param price
	 * @param bank
	 * @return
	 */
	public static BigDecimal getPoundage4Pay(BigDecimal price, String bank){
		BigDecimal poundage = BigDecimal.valueOf(Double.parseDouble("0.003"));//默认千分之三
		if(PoundageFee4Pay.bigdecimal2p.containsKey(bank)){
			poundage = PoundageFee4Pay.bigdecimal2p.get(bank);
		}
		
		BigDecimal result = price.multiply(poundage).setScale(2,BigDecimal.ROUND_HALF_UP);
		return result;
	}
}
