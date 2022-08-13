package cn.zeppin.utility;

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
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.zeppin.entity.Area;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.TextbookCapter;

import com.alibaba.fastjson.JSON;

public class Utlity {

	public static Logger logger = LogManager.getLogger(Utlity.class);
	public static String JSON_DATA = "json";
	public static String XML_DATA = "xml";
	public static String TEXT_DATA = "text";

	// 页面回传
	public static void ResponseWrite(Object obj, String type, HttpServletResponse response) {

		try {
			if (type == null) {
				type = "json";
			}
			response.setContentType("application/" + type);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			if (type != null && type.equals(Utlity.JSON_DATA)) {
				String json = JSON.toJSONString(obj, true);
				
				
//				System.out.println("-------------------");
//				System.out.println(json);
//				System.out.println("-------------------");
				
				
				response.getWriter().write(json);
			} else if (type != null && type.equals(Utlity.XML_DATA)) {
				String xml = JSONUtils.JSON2XML(JSON.toJSONString(obj));
				response.getWriter().write(xml);
			} else {
				String text = obj.toString();
				response.getWriter().write(text);
			}

		} catch (Exception e) {
			logger.error(e);
		}

	}

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

	public static String getSourceType(short sourcetype) {
		switch (sourcetype) {
		case 0:
			return "真题";
		case 1:
			return "模拟试题";
		case 2:
			return "课后练习题";
		case 3:
			return "其他";
		default:
			return "其他";
		}
	}

	public static String getDiffcultyLevelType(short diffcultyLevel) {
		switch (diffcultyLevel) {
		case 1:
			return "非常简单";
		case 2:
			return "简单";
		case 3:
			return "一般";
		case 4:
			return "困难";
		case 5:
			return "非常困难";
		default:
			return "非常简单";
		}
	}

	public static String getStatusName(short status) {
		switch (status) {
		case 0:
			return "已删除";
		case 1:
			return "已审核";
		case 2:
			return "草稿";
		case 3:
			return "已发布";
		default:
			return "草稿";
		}
	}

	public static String getGradeNaviName(Grade grade) {
		if (grade.getGrade() == null) {
			return grade.getName();
		} else {
			return getGradeNaviName(grade.getGrade()) + " > " + grade.getName();
		}
	}

	public static String getCategoryNaviName(Category category) {
		if (category.getCategory() == null) {
			return category.getName();
		} else {
			return getCategoryNaviName(category.getCategory()) + " > " + category.getName();
		}
	}

	public static String getKnowledgeNaviName(Knowledge knowledge) {
		if (knowledge.getKnowledge() == null) {
			return knowledge.getName();
		} else {
			return getKnowledgeNaviName(knowledge.getKnowledge()) + " > " + knowledge.getName();
		}
	}

	public static String getTextbookCapterNaviName(TextbookCapter capter) {
		if (capter.getTextbookCapter() == null) {
			return capter.getTextbook().getName() + " > " + capter.getName();
		} else {
			return getTextbookCapterNaviName(capter.getTextbookCapter()) + " > " + capter.getName();
		}
	}
	
	/**
	 * 获取地区
	 * 
	 * @author Administrator
	 * @date: 2014年9月9日 下午2:47:26 <br/>
	 * @param area
	 * @return
	 */
	public static String getAreaName(Area area) {
		if (area.getArea() == null) {
			return area.getName();
		} else {
			return getAreaName(area.getArea()) + area.getName();
		}
	}
}
