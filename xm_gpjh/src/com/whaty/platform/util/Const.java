package com.whaty.platform.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Const {
	
	//调查问卷百分比精度
	public static int percentScale = 2;
	
	// //靳希坤增加///
	public static int mustscore = 60;// 获得学分的成绩最低标准
	public static int mustmainscore = 60;// 主干课程通过分数
	public static int paperscore = 60;// 毕业的论文成绩最低标准
	public static int mustEnglishDegreeScore = 60;// 学位英语标准

	// TODO 靳希坤：课程分组name改为id
	public static String majorrequired_id ="_2";// "专业必修课";
	public static String majoralternation_id = "_3";// "专业选修课";
	public static String publicrequired_id = "_1";// "公共必修课";
	public static String publicalternation_id = "_4";// "公共选修课";
	public static String FIRST_PASSWORD = "111111";// 初始化密码;
	// /////////////
	public static String credit = "^\\d{1,2}(\\.\\d)?$";
	public static String creditMessage = "输入格式：1至2到两位整数 0至1位小数";
	public static String credit_for_extjs = "regex:new RegExp(/" + credit + "/),regexText:'" + creditMessage + "',";
	
	public static String score = "^(([1-9]?\\d)(\\.\\d)?)$|^100$";
	public static String scoreMessage = "输入格式：0至100的整数，0至1位小数";
	public static String score_for_extjs = "regex:new RegExp(/"+score+"/),regexText:'"+scoreMessage+"',";
	
	public static String email = "^\\w+([-+.]\\w*)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$";
	public static String emailMessage = "邮箱格式输入不正确";
	public static String email_for_extjs = "regex:new RegExp(/"+email+"/),regexText:'"+emailMessage+"',";
	
	public static String phone = "^(\\d{11})$";
	public static String phoneMessage = "输入格式：11位数字";
	public static String phone_for_extjs = "regex:new RegExp(/"+phone+"/),regexText:'"+phoneMessage+"',";
	
	public static String fee = "^\\d{1,6}(\\.\\d{1,2})?$";
	public static String feeMessage = "金额输入格式：1到6位整数 0到2位小数";
	public static String fee_for_extjs = "regex:new RegExp(/"+fee+"/),regexText:'"+feeMessage+"',";
	public static String _fee = "^(-)?\\d{1,8}(\\.\\d{1,2})?$"; // 金额输入格式：1到8位整数
																// 0到2位小数 可以是负数

	public static String AccountingInvoiceID = "^[A-Za-z]{2}\\d{8}$";
	public static String AccountingInvoiceMessage = "发票号格式：2位字母加8位数字";
	public static String AccountingInvoice_for_extjs = "regex:new RegExp(/"+AccountingInvoiceID+"/),regexText:'"+AccountingInvoiceMessage+"',";
	
	
	public static String scoreLine = "^((0|[1-9]\\d{0,2}))$";
	public static String scoreLineMessage = "输入格式：正整数(最多3位)或0";
	public static String scoreLine_for_extjs = "regex:new RegExp(/"+scoreLine+"/),regexText:'"+scoreLineMessage+"',";
	
	public static String sex = "^[\u7537|\u5973]{1,}$";
	public static String sexMessage = "输入格式：性别只能为男,女";
	public static String sex_for_extjs = "regex:new RegExp(/"+sex+"/),regexText:'"+sexMessage+"',";
	
	public static String edu = "^[\u521D\u4E2D|\u9AD8\u4E2D|\u804C\u9AD8|\u4E2D\u4E13|\u6280\u6821|\u5927\u4E13|\u5927\u672C|\u7855\u58EB|\u535A\u58EB]{1,}$";
	public static String eduMessage = "输入格式：学历只能填写：初中、高中、职高、中专、技校、大专、大本、硕士、博士";
	public static String edu_for_extjs = "regex:new RegExp(/"+edu+"/),regexText:'"+eduMessage+"',";
	
	public static String chinese = "^[\u0391-\uFFE5]+$";
	public static String chineseMessage = "输入格式：中文";
	public static String chinese_for_extjs = "regex:new RegExp(/"+chinese+"/),regexText:'"+chineseMessage+"',";
	
	public static String phone_number_for_extjs = "regex:new RegExp(/^(((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?)|(((\\(\\d{2,3}\\))|(\\d{3}\\-))?1[35]\\d{9})$/),regexText:'输入格式：区号-7位或8位数字（区号可以没有）',";
	
	public static String mobile = "^(\\+86)?0?1[3|5|8]\\d{9}$";
	public static String mobileMessage = "请输入正确的移动电话";
	public static String mobile_for_extjs = "regex:new RegExp(/" + mobile + "/),regexText:'" + mobileMessage + "',";
	
	public static String userName = "^\\w{3,15}[A-Za-z0-9]$";
	public static String userNameMessage = "请输入正确的用户名";
	public static String userName_for_extjs = "regex:new RegExp(/" + userName + "/),regexText:'" + userNameMessage + "',";
	
	public static String password = "^[A-Za-z0-9!@#$%^&*()]{4,16}$";
	public static String passwordMessage = "请输入正确的密码";
	public static String password_for_extjs = "regex:new RegExp(/" + password + "/),regexText:'" + passwordMessage + "',";
	
	public static String cardId = "^[1-9](\\d{14}||(\\d{16}([0-9]|X)))$";
	public static String cardIdMessage = "请输入正确的身份证号码";
	public static String cardId_for_extjs = "regex:new RegExp(/" + cardId + "/),regexText:'" + cardIdMessage + "',";
	
	public static List fileTypeList=Arrays.asList("zip");
	public static List jpgFile=Arrays.asList("jpg");
	public static List photoTypeList=Arrays.asList("jpg","jpeg","bmp","gif");
	public static String FILE_PATH = "/incoming/photo/";
	public static int LOG_LENGTH = 2000; //日志消息的长度
	
	public static String checkdate = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[1-9])|(1[0-2]))\\:([0-5][0-9])((\\s)|(\\:([0-5][0-9])\\s))([AM|PM|am|pm]{2,2})))?$";
	
	// 李冰增加
	
// public static String telephone =
// "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[3]\\d{9}$)";
// // 固定电话
	// 电话号码，匹配格式： 11位手机号码 3-4位区号，7-8位直播号码，1－4位分机号
	// 如：12345678901、1234-12345678-1234
	public static String telephone = "^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
	//public static String telephone = "^((d{3,4})|d{3,4}-)?d{7,8}$";
	public static String telephoneMessage = "输入格式：3至4位区号-7至8位直播号码-1至4位分机号";
	public static String telephone_for_extjs = "regex:new RegExp(/" + telephone + "/),regexText:'" + telephoneMessage + "',";
		
	public static String zip = "^\\d{6}$"; // 邮编
	public static String zipMessage = "输入格式：6位数字";
	public static String zip_for_extjs = "regex:new RegExp(/" + zip + "/),regexText:'" + zipMessage + "',";
	
	public static String coursecode = "^\\d{6}$"; // 邮编
	public static String coursecodeMessage = "输入格式：长度为6位";
	public static String coursecode_for_extjs = "regex:new RegExp(/" + coursecode + "/),regexText:'" + coursecodeMessage + "',";
	
	public static String code = "^\\d{3}$"; // 邮编
	public static String codeMessage = "输入格式：企业编号长度为3位";
	public static String code_for_extjs = "regex:new RegExp(/" + code + "/),regexText:'" + codeMessage + "',";
	
	public static String number = "^\\d+$"; // 数字
	public static String numberMessage = "输入格式：数字";
	public static String number_for_extjs = "regex:new RegExp(/" + number + "/),regexText:'" + numberMessage + "',";
	
	public static String matriculateNum = "^.*\\d{4}$"; // 以4位数字结尾
	public static String matriculateNumMessage = "输入格式：以4位数字结尾";
	public static String matriculateNum_for_extjs = "regex:new RegExp(/" + matriculateNum + "/),regexText:'" + matriculateNumMessage + "',";
	
	public static String oneNum = "^\\d$"; // 输入1位数字
	public static String oneNumMessage = "输入格式：1位数字";
	public static String oneNum_for_extjs = "regex:new RegExp(/" + oneNum + "/),regexText:'" + oneNumMessage + "',";
	
	public static String twoNum = "^\\d{2}$"; // 输入1位数字
	public static String twoNumMessage = "输入格式：2位数字";
	public static String twoNum_for_extjs = "regex:new RegExp(/" + twoNum + "/),regexText:'" + twoNumMessage + "',";
	
	public static String fiveNum = "^(\\d{5})$";
	public static String fiveNumMessage = "输入格式：5位数字";
	public static String fiveNum_for_extjs = "regex:new RegExp(/"+fiveNum+"/),regexText:'"+fiveNumMessage+"',";
	
	public static String twoNum2 = "^(([1-9]\\d?))$"; // 输入<100的数字
	public static String twoNumMessage2 = "输入格式：小于100的数字";
	public static String twoNum_for_extjs2 = "regex:new RegExp(/" + twoNum2 + "/),regexText:'" + twoNumMessage2 + "',";
		
	public static String scale = "^(0\\.[1-9])$|^[01]$"; // 成绩比例
	public static String scaleMessage = "输入格式：0至1之间的1位小数或0或1";
	public static String scale_for_extjs = "regex:new RegExp(/" + scale + "/),regexText:'" + scaleMessage + "',";
	
	public static String year = "^20\\d{2}$"; // 年份
	public static String yearMessage = "四位年份,输入格式：20XX";
	public static String year_for_extjs = "regex:new RegExp(/" + year + "/),regexText:'" + yearMessage + "',";
	
	public static String twoNumandSpace = "^(\\d+$|\\s)$"; // 输入<100的数字
	public static String twoNumandSpaceMessage = "输入格式：小于100的数字";
	public static String twoNumandSpace_for_extjs = "regex:new RegExp(/" + twoNumandSpace + "/),regexText:'" + twoNumandSpaceMessage + "',";
	
	public static String serverRoot;		//项目部署根路径
	public static final String jobFilePath="/incoming/jobFile/";//任务资料存储路径
	public static final String meetingFilePath="/incoming/meetFile/";//会议资料存储路径
	/**
	 * 比较日期的年月日 忽略时间 当 date2年月日 >= date1年月日 返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(Date date1, Date date2){
		
		return date2.getTime() > date1.getTime()-86400000;
	}
	
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
	 BigDecimal b1 = new BigDecimal(Double.toString(v1));
	 BigDecimal b2 = new BigDecimal(Double.toString(v2));
	 return b1.add(b2).doubleValue();
	}
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
	 BigDecimal b1 = new BigDecimal(Double.toString(v1));
	 BigDecimal b2 = new BigDecimal(Double.toString(v2));
	 return b1.subtract(b2).doubleValue();
	}
	

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static void main(String[] args) {
		System.out.println(div(10d,1d,1));
	}
	/**
	 * 产生0至max-1之间的随机整数
	 * 
	 * @param max
	 * @return
	 */
	public static int getRandInt(int max){
		 Random rd = new Random();
		 return Math.abs(rd.nextInt()%max);
	}
	/**
	 * 获取当前年份
	 * @return
	 */
	public static String getYear(){
		return new Integer(Calendar.getInstance().get(Calendar.YEAR)).toString();
	}
	
	public static java.sql.Date getNextDate(java.util.Date date){ 
		Calendar calendar=Calendar.getInstance(); 
		calendar.setTime(date); 
		int day=calendar.get(Calendar.DATE); 
		calendar.set(Calendar.DATE,day+1); 
		return getSqlDate(calendar.getTime()); 
		} 
	public static java.sql.Date getSqlDate(java.util.Date date){ 
		return new java.sql.Date(date.getTime()); 
		} 


}
