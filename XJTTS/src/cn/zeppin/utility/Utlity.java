package cn.zeppin.utility;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.zeppin.entity.Teacher;

public class Utlity {

	static Logger logger = LogManager.getLogger(Utlity.class);
//	public static final String REAL_UPLOAD_PATH = "Z:\\xjjspxgl\\xjtts_real\\xjtts";
	public static final String REAL_UPLOAD_PATH = "E:\\zeppin\\xjtts";

	// 页面回传
	public static void ResponseWrite(String msg, String type, HttpServletResponse response) {

		try {
			if (type != null && type.length() > 0) {
				response.setContentType(type);
			}
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(msg);

		} catch (Exception e) {
			logger.error(e);
		}

	}

	// 发送信息
	public static void sendResponse(String status, String value, HttpServletResponse response) {
		StringBuilder checkSB = new StringBuilder();
		checkSB.append("{");
		checkSB.append("\"Result\":\"" + status + "\"");
		checkSB.append(",");
		checkSB.append("\"Message\":\"" + value + "\"");
		checkSB.append("}");
		ResponseWrite(checkSB.toString(), "application/json", response);
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
		if (mobiles != null) {
			Pattern p = Pattern.compile("^\\d{11}$");
			Matcher m = p.matcher(mobiles);
			return m.matches();
		} else {
			return false;
		}
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
	 * 验证身份证
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean checkIdCard(String idcard) {
		String str = IdCardUtil.IDCardValidate(idcard);
		if (str.equals("")) {
			return true;
		} else {
			return false;
		}
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
	 * 生成不带"-"的UUID
	 * 
	 * @author Clark 2014.05.29
	 * @return UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	/**
	 * 获取6位数字验证码，并组成手机短信验证码
	 * @return
	 */
	public static String getCaptcha(){
		String captcha = "";
		Random random = new Random();//随机类
		for (int i=0;i<6;i++){
			String rand=String.valueOf(random.nextInt(10));
			captcha+=rand;
		}
//		String sms = "您本次操作的验证码为："+captcha+",本次验证码5分钟后失效！";
		return captcha;
	}
	
	 /** 
     * 格式化日期 
     * @param date 日期对象 
     * @return String 日期字符串 
     */  
    public static String formatDate(Date date){  
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");  
        String sDate = f.format(date);  
        return sDate;  
    }  
      
    /** 
     * 获取当年的第一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearFirst(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);  
    }  
      
    /** 
     * 获取当年的最后一天 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }  
      
    /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }  
    
    /**
     * 获取*号加密的身份证号
     * @param idcard
     * @return
     */
    public static String getStarIdcard(String idcard){
    	
    	StringBuilder sb = new StringBuilder();
    	if(idcard != null && !"".equals(idcard)){
    		idcard = idcard.trim();
    		sb.append(idcard.substring(0, 6));//截取前四位身份证号码显示
        	//对于不同长度的身份证号，要有不同的处理方案(目前只针对18位和15位长度的身份证号)
        	if(idcard.length()>15){
        		sb.append("**********");
            	sb.append(idcard.substring(14, idcard.length()));//截取后四位身份证号码显示
        	}else{
        		sb.append("********");
            	sb.append(idcard.substring(12, idcard.length()));//截取后四位身份证号码显示
        	}
    	}else{
    		return "";
    	}
    	
    	return sb.toString();
    }
    
    /**
     * 获取*号加密的身份证号
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
    
	/** */
	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 * @throws IOException 
	 */
	public static String renameFile(String path, String oldname, String newname) throws IOException {
		File oldfile = new File(path + "/" + oldname);
		File newfile = new File(path + "/" + newname);
		if (!oldfile.exists()) {
//			System.out.println("文件不存在！");
			return "文件不存在！";// 重命名文件不存在
		}
		
		if (oldfile.renameTo(newfile)) {
			return "ok";
		} else {
			FileUtils.copyFile(oldfile, newfile);
			if(!oldfile.delete()) {
				FileUtils.deleteQuietly(oldfile);
			}
		}
//		System.out.println("文件创建失败");
		return "文件创建失败！";
	}
	
	/**
	 * 根据系统类型获取文件系统绝对路径
	 * @param context
	 * @return
	 */
	public static String getRealPath(ServletContext context) {
		String path = context.getRealPath("/").replace("\\", "/");
		String osName = System.getProperty("os.name");
		if (Pattern.matches("Linux.*", osName)) {
			return path;
		} else if (Pattern.matches("Windows.*", osName)) {
			return REAL_UPLOAD_PATH;
		}
		return null;
	}
    
	public static void main(String[] args) {
//		System.out.println(isNumeric("-1"));
		Teacher t  = new Teacher();
		t.setId(1);
		Teacher t1  = new Teacher();
		t1.setId(2);
		Teacher t2  = new Teacher();
		t2.setId(3);
		Teacher t3  = new Teacher();
		t3.setId(4);
		List<Teacher> list = new ArrayList<Teacher>();
		list.add(t);
		list.add(t1);
		list.add(t2);
		list.add(t3);
		for(Teacher t0 : list) {
			t0.setCreator(t0.getId());
			System.out.println(t0.getCreator());
		}
		for(Teacher t0: list) {
			System.out.println(t0.getCreator());
		}
			
	}
}
