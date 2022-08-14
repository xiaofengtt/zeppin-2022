package cn.product.score.util;


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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.product.score.entity.Orderinfo.OrderinfoType;
import cn.product.score.entity.SystemParam.SystemParamKey;




public class Utlity {

	public static Logger logger = LoggerFactory.getLogger(Utlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";
	
	public static final String KEY_FRONT = "score_front";
	public static final String KEY_FRONT_MD5 = "5fa526a2bb401ffddb888d1f0445ec6a";
	
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
	public static final String BILL_PAYTYPE_ARTIFICIAL = "8";//人工提现
	public static final String BILL_PAYTYPE_SCORE = "9";//未走三方，内部划账
	
	public static final String BILL_PURPOSE_WITHDRAW = "1";//提现
	public static final String BILL_PURPOSE_RECHARGE = "2";//充值
	public static final String BILL_PURPOSE_BET = "3";//投注
	public static final String BILL_PURPOSE_SCORE = "9";//未走三方，内部划账
	
//	public static final String PATH_URL = "https://api.tianchuangyueteng.com";//生产测试
	public static final String PATH_URL = "https://api.ttscore.cn";//生产测试
	
	public static String SERVER_IP = "";
	public static String NOTIFY_URL_ALI_WAP = PATH_URL+"/notice/recharge/byAliwap";
	
	public static String QUIT_URL_ALI_WAP = "https://www.baidu.com";
	public static String QUIT_URL_ALI_WAP_TEST = "#/my/alipay";
	
	
	public static final BigDecimal REAPAL_MAX_WITHDRAW = BigDecimal.valueOf(5000000);
	
	public static final BigDecimal WITHDRAW_POUNDAGE = BigDecimal.valueOf(1.0);
	
	
	public static final String SEX_MAN = "man";//性别男
	public static final String SEX_WOMAN = "woman";//性别女
	
	public static final String DEVICE_TYPE_FREEKICK = "freekick";
	public static final String DEVICE_TYPE_JIUZHOU = "jiuzhou";
	public static final String DEVICE_TYPE_SHABA = "shaba";
	
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
	
	/**
	 * 验证系统参数
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean checkSystemParamMap(String paramKey, Map<String, Object> paramMap) {
		if(SystemParamKey.WITHDRAW_POUNDAGE.equals(paramKey)){
			for(String key : paramMap.keySet()){
				String[] keys = key.split("-");
				String value = paramMap.get(key).toString();
				if(keys == null || keys.length != 2){
					return false;
				}
				
				if(!isPositiveCurrency(keys[0]) || !isPositiveCurrency(keys[1])){
					return false;
				}
				
				if(Double.valueOf(keys[0]) > Double.valueOf(keys[1])){
					return false;
				}
				if(!isPositiveCurrency(value)){
					if(!value.contains("%")){
						return false;
					}
					value = value.replace("%", "");
					if(!isNumeric(value)){
						return false;
					}
				}
			}
		}else if(SystemParamKey.WITHDRAW_NOTICE_MOBILE.equals(paramKey)){
			for(String key : paramMap.keySet()){
				String value = paramMap.get(key).toString();
				if(!isMobileNO(key)){
					return false;
				}
				if(checkStringNull(value)){
					return false;
				}
			}
		}
		
		
		return true;
	}
	
	public static String timestampFormat(Timestamp ts, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(ts);
	}

	public static String timeSpanToString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timeSpanToStringLess(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaDateString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringLess(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		return sdf.format(ts);
	}
	
	public static String timeSpanToChinaStringes(Timestamp ts) {
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
	
	public static String timeSpanToChinaStringesLessYear(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
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
	
	public static String timeRomeToBeijng(String timeStr) throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
		Date time = format.parse(timeStr);
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf.format(time);
	}
	
	public static String getOrderTypeByPlatformType(String historyType){
		switch (historyType){
		case OrderinfoType.COMPANY_ALIPAY:
			return Utlity.BILL_PAYTYPE_ALIPAY_COM;
		case OrderinfoType.PERSONAL_ALIPAY:
			return Utlity.BILL_PAYTYPE_ALIPAY_PER;
		case OrderinfoType.COMPANY_BANKCARD:
			return Utlity.BILL_PAYTYPE_BANKCARD_COM;
		case OrderinfoType.PERSONAL_BANKCARD:
			return Utlity.BILL_PAYTYPE_BANKCARD_PER;
		case OrderinfoType.WECHAT:
			return Utlity.BILL_PAYTYPE_WECHAT;
		case OrderinfoType.REAPAL:
			return Utlity.BILL_PAYTYPE_REAPAL;
		case OrderinfoType.ARTIFICIAL:
			return Utlity.BILL_PAYTYPE_ARTIFICIAL;
		default:
			return Utlity.BILL_PAYTYPE_SCORE;
		}
	}
}
