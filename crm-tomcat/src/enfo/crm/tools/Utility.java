package enfo.crm.tools;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 *  
 * @author Felix
 * @since 2008-5-20
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class Utility { 
	private static boolean isDebug = true;
	public static String[] chineseDigits =
		new String[] { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };

	/*public static void assert(boolean b) throws RuntimeException {
		if (!b)
			throw new RuntimeException("Assert error.");
	}*/

	// ���� :��ֹͣ��Ļ�������ȴ��û�����
	// ���� :  N/A
	// ��� :  N/A
	public static void waitPause() throws Exception {
		System.in.read();
	}

	// ���� :����Unicode�ַ���ת��GBK�ֽ�����
	// ���� :  Unicode�ַ���
	// ��� :  �ֽ�����
	public static byte[] toChineseBytes(String s)
		throws UnsupportedEncodingException {
		return s.getBytes("GBK");
	}

	// ���� :����Unicode������ַ���ת��GB2312������ַ���
	// ���� :  Unicode�ַ���
	// ��� :  GB2312�ַ���
	public static String toChinese(String s)
		throws UnsupportedEncodingException {
		if (s == null || s.equals(""))
			return "";
		return new String(s.getBytes("ISO8859_1"), "gb2312");
	}

	// ���� :����GB2312������ַ���ת��Unicode������ַ���
	// ���� :  GB2312�ַ���
	// ��� :  Unicode�ַ���
	public static String toUnicode(String s)
		throws UnsupportedEncodingException {
		if (s == null || s.equals(""))
			return "";
		return new String(s.getBytes("gb2312"), "ISO8859_1");
	}
	//���� :�������� ����ӻ��з�
	// ���� :  GB2312�ַ���
	// ��� :  Unicode�ַ���
	public static String InsertBrTag(String s)
		throws UnsupportedEncodingException {
		if (s == null || s.equals(""))
			return "";

		//String temStr=toUnicode(s);
		StringBuffer stemBf = new StringBuffer(s);

		if (stemBf.length() > 30)
			stemBf.insert(30, "<BR>");

		return stemBf.toString();
	}

	// ���� :�����ʼ��
	// ���� :  �����ַ���
	// ��� :  �Ƿ�ɹ���ʼ��
	public static boolean ensureLoaded(String name) throws Exception {
		Class.forName(name).newInstance();
		return true;
	}

	// ���� :���ָ��ַ���(��Сд����)
	// ���� :  ���ָ��ַ������ָ��
	// ��� :  �ָ����ַ�������
	// 
	/**
	 * ADD BY TSG 2009-03-23
	 * �Էָ���ƴ���ַ���ע�⣺���磺String a = "",b = "",c = "";
	 * String s_all = a + " $ " + b + " $ " + c;  ---�ָ���$ǰ��ӿո񣬷���a,b,c��һ��Ϊ""����ϵ��ַ����������������������
	 * String[] s = Utility.splitString(s,"$");
	 * Utility.debugln("s.length:" + s.length); ---��a,b,cΪ""(������һΪ""),�ָ���$ǰ��û�ӿո�s.length = 0(s.length = 1...)
	 * for(int i = 0;i<s.length;i++)
	 *   Utility.debugln("s[" + i + "] = " + s[i].trim);---s[i]�����޳��ո�
	 * */
	public static String[] splitString(String str, String sign) {
		int count = getStrCount(str, sign);
		int j = 0;
		String[] arr = new String[count];
		for (int i = 0; i < count; i++) {
			arr[i] = "";
			if (str.indexOf(sign) != -1) {
				j = str.indexOf(sign);
				arr[i] = str.substring(0, j);
				str = str.substring(j + 1);
			} else {
				arr[i] = str;
			}

		}
		return arr;
	}
	
	public static String[][] splitString(String str,String sign1,String sign2){
		
		String[][] arr;
		String[] temparr = Utility.splitString(str,sign1);
		arr = new String[temparr.length][];

		for(int i=0;i<arr.length;i++){
			arr[i] = Utility.splitString(temparr[i],sign2);
		}
			
		return arr;
	}
	
	/**
	 * add by ³���� for 20090810
	 * implement java split action copyright to function splitString
	 * @param str
	 * @param sign
	 * @return java object array
	 */
	public static String[] splitString_01(String str, String sign){
		//init
		int j = 0;
		if(str.equals("")){ return new String[0]; }
		if(sign.equals("")){ sign = "$"; }
		
		int count = getStrCount(str, sign);
		String[] arr = new String[ count + 1 ];
		for (int i = 0; i <= count; i++) {
			arr[i] = "";
			if (str.indexOf(sign) != -1) {
				j = str.indexOf(sign);
				arr[i] = str.substring(0, j);
				str = str.substring(j + 1);
			} else {
				arr[i] = str;str = "";
			}
		}
		return arr;
	}
	
	

	/**
	 * �ַ����滻
	 * @param src
	 * @param fnd
	 * @param rep
	 * @return
	 * @throws Exception
	 */
	public static String replaceAll(String src, String fnd, String rep) {
		if (src == null || src.equals("")) {
			return "";
		}
		//����fnd = "" ʱ����ѭ��
		if (fnd == null || fnd.equals(""))
			return src;
		String dst = src;

		int idx = dst.indexOf(fnd);

		while (idx >= 0) {
			dst =
				dst.substring(0, idx)
					+ rep
					+ dst.substring(idx + fnd.length(), dst.length());
			idx = dst.indexOf(fnd, idx + rep.length());
		}

		return dst;
	}

	public static int getStrCount(String str, String sign) {
		if (str == null)
			return 0;
		
		while(str.indexOf(sign+sign)!=-1){
			str = Utility.replaceAll(str,sign+sign,sign+" "+sign);
		}
		StringTokenizer s = new StringTokenizer(str, sign);
		return s.countTokens();
	}

	public static String numToChinese2(String input) {
		String temp = "";
		String result = "";
		boolean bZero = false;
		if (input == null)
			return "�����ִ��������ִ�ֻ�ܰ��������ַ���'0'��'9'��'.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";

		temp = input.trim();

		double f;
		try {
			f = Double.parseDouble(temp);

		} catch (Exception e) {
			return "�����ִ��������ִ�ֻ�ܰ��������ַ�('0'~'9','.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";
		}
		result = amountToChinese(f);

		return result;
	}
	public static String digitToChinese(String input) {
		String s1 = "��Ҽ��������½��ƾ�";
		String temp = "";
		String result = "";
		boolean bZero = false;
		if (input == null)
			return "�����ִ��������ִ�ֻ�ܰ��������ַ���'0'��'9')��";

		temp = input.trim();
		int len = 0;

		int n1, n2 = 0;
		String num = "";
		String unit = "";

		for (int i = 0; i < temp.length(); i++) {
			if (i > len) {
				break;
			}

			n1 = Integer.parseInt(String.valueOf(temp.charAt(i)));
			num = s1.substring(n1, n1 + 1);
			n2 = len - i + 2;

			result = result.concat(num);

		}
		return result;
	}

	public static String numToChinese1(String input) {
		String temp = "";
		String result = "";
		boolean bZero = false;
		if (input == null)
			return "�����ִ��������ִ�ֻ�ܰ��������ַ���'0'��'9'��'.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";

		temp = input.trim();

		double f;
		try {
			f = Double.parseDouble(temp);

		} catch (Exception e) {
			return "�����ִ��������ִ�ֻ�ܰ��������ַ�('0'~'9','.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";
		}
		result = amountToChinese(f);
		return result;
	}
	public static String numToChinese(String input) {
		String temp = "";
		String result = "";
		boolean bZero = false;
		if (input == null)
			return "�����ִ��������ִ�ֻ�ܰ��������ַ���'0'��'9'��'.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";

		temp = input.trim();
		//int iDex1 = temp.indexOf('-');

		//if (iDex1 != -1)
		//temp = temp.substring(iDex1 + 1);

		double f;
		try {
			f = Double.parseDouble(temp);

		} catch (Exception e) {
			return "�����ִ��������ִ�ֻ�ܰ��������ַ�('0'~'9','.')�������ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ��";
		}
		result = amountToChinese(f);
		return result;
	}

	public static int parseInt(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Integer parseInt(String s, Integer defaultValue) {
		try {
			if (isEmpty(s))
				return defaultValue;
			else
				return new Integer(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * �������ֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static Integer parseInt(Integer value, Integer defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * �������ֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static Integer parseInt(Integer value, int defaultValue) {
		if (value == null)
			return new Integer(defaultValue);
		else
			return value;
	}

	public static int parseInt(int s, int defaultValue) {
		try {
			return s;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * ���valueֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static BigDecimal parseBigDecimal(
		BigDecimal value,
		BigDecimal defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * �������ֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static Long parseLong(Long value, Long defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * �������ֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static Long parseLong(Long value, long defaultValue) {
		if (value == null)
			return new Long(defaultValue);
		else
			return value;
	}

	/**
	 * �������ֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static Double parseDouble(Double value, Double defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * �������ֵΪ�գ��򷵻�Ĭ��ֵ
	 * @param value ����ֵ
	 * @param defaultValue Ĭ��ֵ
	 * @return
	 */
	public static Double parseDouble(Double value, double defaultValue) {
		if (value == null)
			return new Double(defaultValue);
		else
			return value;
	}

	public static java.sql.Timestamp parseTimestamp(
		String s,
		java.sql.Timestamp defaultValue) {
		try {
			return new java.sql.Timestamp(Utility.parseLong(s, 0));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	public static java.math.BigDecimal parseDecimal(
		String s,
		java.math.BigDecimal defaultValue) {
		try {
			return new java.math.BigDecimal(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 
	 * ����һ�� BigDecimal������Ϊָ��ֵ����Ǳ��ֵͨ���� BigDecimal �ķǱ��ֵ���Ի����ʮ���ʵ�������ȷ������ά������ֵ��
	 * ����ò������ٱ�ȣ���Ǳ��ֵ���뱻���������ǳˣ������Ҹ�ֵ���Ը��ģ�����������£���ָ��������ģʽӦ�õ������С�
	 * */
	public static java.math.BigDecimal parseDecimal(
		String s,
		java.math.BigDecimal defaultValue,
		int scale,
		String amp) {
		java.math.BigDecimal d;
		try {
			d = new java.math.BigDecimal(s);
			d = d.setScale(scale, java.math.BigDecimal.ROUND_HALF_UP);
			if (amp != null)
				d = d.multiply(new java.math.BigDecimal(amp));
			return d;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static short parseShort(String s, short defaultValue) {
		try {
			return Short.parseShort(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Short parseShort(String s, Short defaultValue) {
		try {
			return new Short(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static long parseLong(String s, long defaultValue) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Long parseLong(String s, Long defaultValue) {
		try {
			return new Long(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static void debug(Object s) {
		if (isDebug)
			System.out.println(s);
	}

	public static void debugln(Object s) {
		if (isDebug)
			System.out.println(s);
	}

	public static int getDateInt(Date date) {
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(date);
		return dStart.get(Calendar.YEAR) * 10000
			+ (dStart.get(Calendar.MONTH) + 1) * 100
			+ dStart.get(Calendar.DATE);
	}
	public static int getTimestampInt(java.sql.Timestamp date) {
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(date);
		return dStart.get(Calendar.YEAR) * 10000
			+ (dStart.get(Calendar.MONTH) + 1) * 100
			+ dStart.get(Calendar.DATE);
	}
	public static java.sql.Date getDateByInt(Integer date) {
		String s = Format.formatDate(date.intValue());
		s = s.replace('/', '-');
		return java.sql.Date.valueOf(s);
	}

	public static int getCurrentDate() {
		return getDateInt(new Date());
	}
	public static String getCurrentDate2(){//���ظ�ʽ�磺2008-08-08
		String currentDate = getCurrentDate()+"";
		return currentDate.substring(0,4) + "-" + currentDate.substring(4,6) + "-" + currentDate.substring(6,8) ;
	}

	public static int getTimePart(int type) {
		int date = getCurrentDate();
		String strDate = Integer.toString(date);
		String strYear = "";
		if (type == 1)
			strYear = strDate.substring(0, 4);
		else if (type == 2) {
			strYear = strDate.substring(4, 6);
			if (strYear.charAt(1) == '0')
				strYear = strDate.substring(5, 6);
		} else if (type == 3) {
			strYear = strDate.substring(6, 8);
			if (strYear.charAt(1) == '0')
				strYear = strDate.substring(7, 8);
		}
		return parseInt(strYear, 0);
	}

	public static int getDatePart(int date, int type) {
		String sDate = Integer.toString(date);
		String sResult = "";
		if (type == 1)
			sResult = sDate.substring(0, 4);
		else if (type == 2)
			sResult = sDate.substring(4, 6);
		else if (type == 3)
			sResult = sDate.substring(6, 8);
		else if (type == 4)
			sResult = sDate.substring(0, 6);
		return parseInt(sResult, 1);
	}

	public static int getDateValue(ServletRequest request, String name) {
		String sYear, sMonth, sDay, sAll;
		sYear = request.getParameter(name + "_year");
		sMonth = request.getParameter(name + "_month");
		sDay = request.getParameter(name + "_day");
		if (sYear.length() != 4)
			return 0;
		if (sMonth.length() == 1)
			sMonth = "0" + sMonth;
		if (sDay.length() == 1)
			sDay = "0" + sDay;
		sAll = sYear + sMonth + sDay;
		return Utility.parseInt(sAll, 0);
	}

	public static String trimNull(Object o) {
		if (o == null)
			return "";
		return o.toString();
	}

	public static String trimNull(Object o, String vdefault) {
		if (o == null)
			return vdefault;
		return o.toString();
	}
	public static int trimNull(Object o, int iDefault) {
		if (o == null)
			return iDefault;
		return Integer.valueOf(o.toString()).intValue();
	}
	public static int getCurrentYear() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

		int year;

		Calendar dStart = Calendar.getInstance();
		dStart.setTime(date);
		year = dStart.get(Calendar.YEAR);
		return year;
	}

	public static int getCurrentMonth() {
		java.util.Date date = new java.util.Date(System.currentTimeMillis());

		int month;

		Calendar dStart = Calendar.getInstance();
		dStart.setTime(date);
		month = dStart.get(Calendar.MONTH) + 1;
		return month;
	}

	public static int getMonthDays(int month, int curyear) //ȡ��ĳһ��ĳһ�µ�ʵ������
	{
		int returndays = 31;
		boolean leapYear = false;
		if ((curyear % 400 == 0)
			|| ((curyear % 4 == 0) && (curyear % 100 != 0)))
			leapYear = true;
		switch (month) {
			case 2 :
				{
					if (leapYear)
						returndays = 29;
					else
						returndays = 28;
				}
			case 4 :
				returndays = 30;
			case 6 :
				returndays = 30;
			case 9 :
				returndays = 30;
			case 11 :
				returndays = 30;
			default :
				returndays = 31;
		}
		return returndays;
	}
	public static String trimZero(Object object) {
		if (object == null)
			return "";
		if ("".equals(object))
			return "";
		if ("0".equals(object.toString())) //TSG 2007-07-05
			return "0";

		if (object instanceof BigDecimal && ((BigDecimal)object).doubleValue()==0.0) 
			return "0.0";
			
		StringBuffer tempstr = new StringBuffer(object.toString());

		int itmepIndex = (object.toString()).lastIndexOf(".");

		for (int j = tempstr.length() - 1; j >= itmepIndex; j--) {
			if (tempstr.charAt(j) == '0' || tempstr.charAt(j) == '.')
				tempstr.deleteCharAt(j);
			else
				break;
		}
		return tempstr.toString();
	}

	public static int getCurrentDay() {
		java.util.Date date = new java.util.Date(System.currentTimeMillis());

		int day;

		Calendar dStart = Calendar.getInstance();
		dStart.setTime(date);
		day = dStart.get(Calendar.DATE);
		return day;
	}

	public static String getstrCurrentDate() {
		Locale locale = Locale.CHINA;

		java.util.Date date = new java.util.Date(System.currentTimeMillis());

		String strDate =
			DateFormat.getDateInstance(DateFormat.DEFAULT, locale).format(date);
		return strDate;
	}
	public static String getstrCurrentTime() {
		Locale locale = Locale.CHINA;

		java.util.Date date = new java.util.Date(System.currentTimeMillis());

		String strDate =
			DateFormat.getTimeInstance(DateFormat.DEFAULT, locale).format(date);
		return strDate;
	}
	
	public static String timeSpanToString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}

	public static java.sql.Timestamp getCurrentTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	public static java.sql.Timestamp parseTimestamp(String strdate) {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	public static java.math.BigDecimal stringToDouble(String strdouble) {
		if (strdouble == null)
			strdouble = "0";
		if (strdouble.equals(""))
			strdouble = "0";
		return new java.math.BigDecimal(strdouble);
	}

	public static java.math.BigDecimal checkDecimal(
		java.math.BigDecimal rate,
		int decmialength) {
		if (rate == null)
			return new java.math.BigDecimal(0);
		String strrate = rate.toString();
		int idex = strrate.indexOf(".");
		String d = strrate.substring(idex + 1, strrate.length());
		if (Utility.parseInt(d, 0) > 0)
			rate = rate.setScale(decmialength);
		else if (Utility.parseInt(d, 0) == 0)
			rate = rate.setScale(0);
		return rate;
	}

	public static String getFileNameFromPath(String filepath) {
		int pos = 0;
		String shtable = "";
		pos = filepath.lastIndexOf("\\");
		if (pos != -1)
			shtable = filepath.substring(pos + 1);
		return shtable;
	}
	public static String DropNull(String field) {
		String tempStr = Utility.trimNull(field);
		if (tempStr.toString().toLowerCase().equals("null")) {
			return "";
		} else {
			return tempStr.trim();
		}
	}

	public static int getDateInt(java.sql.Timestamp date) {
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(new Date(date.getTime()));
		return dStart.get(Calendar.YEAR) * 10000
			+ (dStart.get(Calendar.MONTH) + 1) * 100
			+ dStart.get(Calendar.DATE);
	}

	/**
	 * ����������ڼ���������
	 * @param start_date ��ʼ����
	 * @param end_date ��������
	 * @return int ����
	 */
	public static int getDays(Integer start_date, Integer end_date) {
		java.util.Date star = Utility.getDateByInt(start_date);
		java.util.Date endda = Utility.getDateByInt(end_date);
		int days =
			enfo.crm.util.DateDiff.getDateDiff(
				java.util.Calendar.DAY_OF_MONTH,
				endda,
				star);
		return days;
	}

	/**
	 * �õ��������ڼ���������,ֻ�����㣬��ʹʵ��ʱ����С��1��
	 * @param date1 ��ʼ����
	 * @param date2 ��������
	 * @return int ����
	 */
	public static int getBetweenDays(Date date1, Date date2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = dateFormat.parse(dateFormat.format(date1));
			date2 = dateFormat.parse(dateFormat.format(date2));
		} catch (Exception e) {
			return -9999;
		}
		long diff = date2.getTime() - date1.getTime();
		long days = diff / 24 / 3600 / 1000;
		return new Long(days).intValue();
	}

	public static String amountToChinese(double amount) {
		if (amount > 99999999999999.99 || amount < -99999999999999.99)
			throw new IllegalArgumentException("����ֵ��������Χ (-99999999999999.99 �� 99999999999999.99)��");

		boolean negative = false;
		if (amount < 0) {
			negative = true;
			amount = amount * (-1);
		}

		long temp = Math.round(amount * 100);
		int numFen = (int) (temp % 10); // ��
		temp = temp / 10;
		int numJiao = (int) (temp % 10); //��
		temp = temp / 10;
		//temp Ŀǰ�ǽ�����������

		int[] parts = new int[20]; // ���е�Ԫ���ǰ�ԭ������������ַָ�Ϊֵ�� 0~9999 ֮������ĸ�������
		int numParts = 0; // ��¼��ԭ������������ַָ�Ϊ�˼������֣�ÿ���ֶ��� 0~9999 ֮�䣩
		for (int i = 0;; i++) {
			if (temp == 0)
				break;
			int part = (int) (temp % 10000);
			parts[i] = part;
			numParts++;
			temp = temp / 10000;
		}

		boolean beforeWanIsZero = true; // ��־��������һ���ǲ��� 0

		boolean beforeYiIsZero = true; // ��־���ڡ�����һ���ǲ��� 0

		String chineseStr = "";
		for (int i = 0; i < numParts; i++) {
			String partChinese = partTranslate(parts[i]);

			if (i % 2 == 0) {
				if ("".equals(partChinese))
					beforeWanIsZero = true;
				else
					beforeWanIsZero = false;
			}

			if (i != 0) {
				if (i % 2 == 0) {

					///////////////�����ں����
					if (parts[i - 1] < 1000 && parts[i - 1] > 0)
						chineseStr = "��" + chineseStr;
					chineseStr = "��" + chineseStr;
					///////////////�����ں����
				} else {
					if ("".equals(partChinese)
						&& !beforeWanIsZero) // ������򡱶�Ӧ�� part Ϊ 0������������һ����Ϊ 0���򲻼ӡ��򡱣����ӡ��㡱
						chineseStr = "��" + chineseStr;
					else {
						if (parts[i - 1] < 1000 && parts[i - 1] > 0)
							// ���"��"�Ĳ��ֲ�Ϊ 0, ��"��"ǰ��Ĳ���С�� 1000 ���� 0�� �������Ӧ�ø����㡱
							chineseStr = "��" + chineseStr;
						chineseStr = "��" + chineseStr;
					}
				}
			}
			chineseStr = partChinese + chineseStr;
		}
		int tempIndex = chineseStr.indexOf("����");
		if (tempIndex != -1) {
			StringBuffer a = new StringBuffer(chineseStr);
			a.delete(tempIndex + 1, tempIndex + 2);
			chineseStr = a.toString();
		}

		if ("".equals(chineseStr)) // ��������Ϊ 0, ����Ϊ"��Ԫ"
			chineseStr = chineseDigits[0];
		if (!"".equals(chineseStr))
			chineseStr = chineseStr + "Ԫ";
		else if (negative) // �������ֲ�Ϊ 0, ����ԭ���Ϊ����
			chineseStr = "��" + chineseStr;
		if (chineseStr.equals("��Ԫ"))
			chineseStr = "";
		if (numFen == 0 && numJiao == 0) {
			chineseStr = chineseStr + "��";
		} else if (numFen == 0) { // 0 �֣�������Ϊ 0
			chineseStr = chineseStr + chineseDigits[numJiao] + "��" + "��";
		} else { // ���֡�����Ϊ 0

			if (numJiao == 0) {
				if (chineseStr.equals(""))
					chineseStr = chineseStr + chineseDigits[numFen] + "��";
				else
					chineseStr = chineseStr + "��" + chineseDigits[numFen] + "��";
			} else
				chineseStr =
					chineseStr
						+ chineseDigits[numJiao]
						+ "��"
						+ chineseDigits[numFen]
						+ "��";
		}
		if (chineseStr.equals("��"))
			chineseStr = "��Ԫ��";
		return chineseStr;
	}

	/**
	 * ��һ�� 0~9999 ֮�������ת��Ϊ���ֵ��ַ���������� 0 �򷵻� ""
	 * @param amountPart
	 * @return
	 */
	private static String partTranslate(int amountPart) {

		if (amountPart < 0 || amountPart > 10000) {
			throw new IllegalArgumentException("���������Ǵ��ڵ��� 0��С�� 10000 ��������");
		}

		String[] units = new String[] { "", "ʰ", "��", "Ǫ" };

		int temp = amountPart;

		String amountStr = new Integer(amountPart).toString();
		int amountStrLength = amountStr.length();
		boolean lastIsZero = true; //�ڴӵ�λ����λѭ��ʱ����¼��һλ�����ǲ��� 0
		String chineseStr = "";

		for (int i = 0; i < amountStrLength; i++) {
			if (temp == 0) // ��λ��������
				break;
			int digit = temp % 10;
			if (digit == 0) { // ȡ��������Ϊ 0
				if (!lastIsZero) //ǰһ�����ֲ��� 0�����ڵ�ǰ���ִ�ǰ�ӡ��㡱��;
					chineseStr = "��" + chineseStr;
				lastIsZero = true;
			} else { // ȡ�������ֲ��� 0
				chineseStr = chineseDigits[digit] + units[i] + chineseStr;
				lastIsZero = false;
			}
			temp = temp / 10;
		}
		return chineseStr;
	}

	/**
	 * �жϴ�����ַ��������Ƿ�ΪNULL��գ����򷵻�true�����򷵻�false
	 * @param s ������ַ�������
	 * @return boolean 
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		else
			return false;
	}

	//������ҳ��ˢ�µı�����棡�������FLAG=1�Ļ���ҳ���ʱ���Ǵ����ݿ���Ľ�������ˢ��ҳ��!=1����ʾrequest.parameter("")�Ľ��
	public static String selectdata(String temp, String temp1, String flag) {
		if (flag == null)
			return temp1;
		return flag.equals("1") ? temp : temp1;
	}

	//������ҳ��ˢ�µı�����棡�������FLAG=1�Ļ���ҳ���ʱ���Ǵ����ݿ���Ľ�������ˢ��ҳ��!=1����ʾrequest.parameter("")�Ľ��
	public static Integer selectdata(
		Integer temp,
		Integer temp1,
		String flag) {
		if (flag == null)
			return temp1;
		return flag.equals("1") ? temp : temp1;
	}

	public static String getClobtoString(Clob content) throws SQLException {
		if (content == null)
			return "";
		Reader input = content.getCharacterStream();
		int len = (int) content.length();
		String info_STR = "";
		char[] by = new char[len];
		int i;

		try {
			while ((i = input.read(by, 0, by.length)) > 0) {
				input.read(by, 0, i);
			}
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}
		info_STR = new String(by);
		return info_STR;
	}

	public static boolean parseBoolean(Integer value) {
		if (value != null) {
			if (value.intValue() == 1)
				return true;
			else
				return false;
		}
		return false;
	}

	/**
		* TSG 2007-09-01
		* */
	public static int parseBoolean(boolean value) {

		if (value)
			return 1;
		else
			return 0;

	}

	/**
	 * ���value ֵΪtrue���򷵻�1
	 * ���value ֵΪfalse���򷵻�0
	 * @param value ����ֵ
	 * @return
	 */
	public static Integer parseBit(String value) {
		Integer i = new Integer(-1);
		if (value == null || value.equals(""))
			return new Integer(-1);
		if ("true".equals(value))
			i = new Integer(1);
		if ("false".equals(value))
			i = new Integer(0);
		return i;
	}

	public static int booleanBit(String value) {
		int i;
		if (value.equals("true")) {

			return i = 1;
		} else {

			return i = 0;
		}
	}

	/**
	 * ADD BY TSG 20080622
	 * �ַ���ת��Ϊ2����
	 * */
	public static String stringToBinary(String str) {
		StringBuffer sb = new StringBuffer("");
		char[] charArray = str.toCharArray();
		char c = ' ';
		for (int i = 0; i < charArray.length; i++) {
			c = charArray[i];
			sb.append(Integer.toBinaryString((int) c)).append(" ");
		}
		return sb.toString();
	}

	/**
		 * ADD BY TSG 20080622
		 * 2����ת��Ϊ�ַ���
		 * */

	public static String binaryToString(String binStr)
		throws NumberFormatException {
		StringBuffer sb = new StringBuffer("");
		String[] strArray = splitString(binStr, " ");
		String s = "";
		for (int i = 0; i < strArray.length; i++) {
			s = strArray[i];
			sb.append((char) (Integer.parseInt(s, 2)));
		}
		return sb.toString();
	}

	/**
	 * add by tsg 2008-11-21
	 * ��ȡ�³�
	 * */
	public static int getCurrentMonthStart() {
		return getCurrentYear() * 10000 + getCurrentMonth() * 100 + 1;
	}
	/**
	 * add by lk 2009-6-1
	 * ��ȡ���
	 * */
	public static int getCurrentYearStart() {
		return getCurrentYear() * 10000 + 100 + 1;
	}
	public static void main(String argus[])
		throws UnsupportedEncodingException {
	
	}

	public static String intercepetCharLength(String s, int length) {
		if (s == null)
			return " ";
		if (s.length() <= length) {
			return s;
		}
		s = s.substring(0, length) + "...";
		return s;
	}

	/**
	 * add by tsg 2008-11-21
	 * ��ȡ��ĩ
	 * */
	public static int getCurrentMonthEnd() {

		int current_year = getCurrentYear();
		int current_month = getCurrentMonth();
		int current_month_end = 0;
		if (current_month == 1 //����
			|| current_month == 3
			|| current_month == 5
			|| current_month == 7
			|| current_month == 8
			|| current_month == 10
			|| current_month == 12) {
			current_month_end = 31;
		} else if (current_month == 2) { //����ƽ
			current_month_end = 28;
		} else {
			current_month_end = 30;
		}
		return current_year * 10000 + current_month * 100 + current_month_end;

	}

	/**   
			   *   �ж��ַ����ı���   
			   *   
			   *   @param   str   
			   *   @return   
			   */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		encode = "BIG5";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s4 = encode;
				return s4;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * ADD BY TSG 2009-04-13
	 * �滻�ַ����е�','Ϊ''
	 * */
	public static String trimComma(String str) {
		String return_str = "";
		if (str == null)
			return return_str;
		return Utility.replaceAll(str, ",", "");

	}

	/**
	 * add by tsg 2009-04-23
	 * ��ȡ��ǰ��������³�
	 * */
	public static Integer getCurrentAcctMonthStart(Integer currenct_month) {
		if (currenct_month == null)
			return new Integer(0);
		int year_month = currenct_month.intValue();
		int year_month_day = year_month * 100 + 1;
		return new Integer(year_month_day);
	}

	/**
	 * add by tsg 2009-04-23
	 * ��ȡ��ǰ���������ĩ
	 * */
	public static Integer getCurrentAcctMonthEnd(Integer currenct_month) {
		if (currenct_month == null)
			return new Integer(0);
		int year_month = currenct_month.intValue();
		int current_year = year_month / 100;
		int current_month = year_month % 100;
		int current_month_end = 0;
		if (current_month == 1 //����
			|| current_month == 3
			|| current_month == 5
			|| current_month == 7
			|| current_month == 8
			|| current_month == 10
			|| current_month == 12) {
			current_month_end = 31;
		} else if (current_month == 2) { //����ƽ
			current_month_end = 28;
		} else {
			current_month_end = 30;
		}
		int year_month_day =
			current_year * 10000 + current_month * 100 + current_month_end;
		return new Integer(year_month_day);

	}
	
	//ͼ����ʾ% ��Ҫ�á�%25�����滻��
	public static String trimPercent(Object o) {
		if (o == null)
			return "";  
		int i = o.toString().indexOf("%");
		//Utility.debug(new Integer(i));
		if( i != -1)
			return o.toString().substring(0,i+1) + "25" + o.toString().substring(i+1,o.toString().length()) ;
		else
			return o.toString();
	}
	/**
	 * add by ���Ǿ� for 2009-10-30
	 * date format
	 * @param item
	 * @return java String
	 */
	public static String getDateFormat(Object item)
	{
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(item);
	}
	
	public static String getTimeZoneDate(Date date,String TimeZoneId){
		
		DateFormat format = new SimpleDateFormat(); 
		TimeZone timeZone = TimeZone.getTimeZone(TimeZoneId);      
		format.setTimeZone(timeZone); 
		return format.format(date);
	}
	/**
	 * �绰��������
	 * @param custTel
	 * @return
	 */
	public static String encryCustTel(String custTel){
		StringBuffer sb = new StringBuffer();
		int len = custTel.length()<=4?custTel.length():custTel.length()-4;
		for(int i=0;i<len;i++){
			sb.append("*");
		}
		if(custTel.length()>4){
			sb.append(custTel.substring(custTel.length()-4,custTel.length()));
		}
		return sb.toString();
	}
	/**
	 * �����ʼ�����
	 * @param email
	 * @return
	 */
	public static String encryEmail(String email){
		StringBuffer sb = new StringBuffer();
		if(email.length()>0){
			int len = email.indexOf("@");
			for(int i=0;i<len;i++){
				sb.append("*");
			}
			sb.append(email.substring(len,email.length()));
		}
		return sb.toString();
	}
	
	public static String trimValue(String o, String vdefault) {
		if (o == null ||  "".equalsIgnoreCase(o) )
			return vdefault;
		return o.toString();
	}
	
	/**
	 * �ָ��ַ���(��Сд����)
	 * @param ���ָ��ַ������ָ��
	 * @return�ָ����ַ�������,�������ַ�
	 */
	public static Vector splitString2(String sourceString,String sign) 
	{
		 Vector splitArrays = new Vector();
		 int i = 0;
		 int j = 0;
		 if (sourceString.length()==0) {return splitArrays;}
		 while (i <= sourceString.length()) {
				j = sourceString.indexOf(sign, i);
				if (j < 0) {j = sourceString.length();}
				splitArrays.addElement(sourceString.substring(i, j));
				i = j + 1;
		 }
		 return splitArrays;
	}
	/**
	 * �����ʺ���ʾ:ÿ4λ�м��пո�
	 * @param custTel
	 * @return
	 */
	public static String ShowBankAcct(String bank_acct){
		//��ȥ���ո�
		bank_acct = Utility.replaceAll(Utility.trimNull(bank_acct)," ","");
		StringBuffer sb = new StringBuffer(bank_acct);
		int len = Utility.trimNull(bank_acct).length();
		int count = 0;
		for(int i=4;i<len;i=i+4){
			sb.insert(i+count," ");
			count++;
		}
		return sb.toString();
	}
    
    /**
     * ȥ�ո�
     * @param s
     * @return
     */
    public static String removeSpaces(String s){
        if(s == null || s == ""){
            return "";
        }else{
            StringTokenizer st = new StringTokenizer(s.trim()," ",false);   
            String t = "";   
            while (st.hasMoreElements()) {   
                t += st.nextElement();   
            }   
            return t;   
        }
    }
    
    public static String getQueryString(HttpServletRequest req, String[] paramNames){
        if (paramNames==null)
            return "";
        
        StringBuffer sb = new StringBuffer(); 
        for (int i=0; i<paramNames.length; i++) {
            String name = paramNames[i];
            String[] values = req.getParameterValues(name);
            if (values != null)
                for (int j=0; j<values.length; j++) {
                    String value = values[j];
                    sb.append("&");
                    sb.append(name);
                    sb.append("=");
                    sb.append(value);
                }
        }
        
        /*
        �׳��� StringIndexOutOfBoundsException 
        - ��� start �� end Ϊ��������� length()��
            ��� start ���� end��
         */
        if (sb.length()>0)
            return sb.substring(1, sb.length());
        else 
        	return "";             
    }
    
    public static String separateQueryString(HttpServletRequest req, String separator, String[] paramNames){
        if (paramNames==null) return "";
        
        StringBuffer sb = new StringBuffer(); 
        for (int i=0; i<paramNames.length; i++) {
            String value = req.getParameter(paramNames[i]);
            sb.append(separator);
            sb.append(value==null?"":value);
        }
        
        /*
        �׳��� StringIndexOutOfBoundsException 
        - ��� start �� end Ϊ��������� length()��
            ��� start ���� end��
         */
        return sb.length()>0? sb.substring(separator.length(),sb.length()): "";
    }
    
    public static String escapeSqlStr(String s) {
    	return s==null? "null"
    				   : "'"+s.replaceAll("'","''")+"'";
    }
    
    // fmt: YYYY�꣬MM�£�DD�죬hhСʱ��mm�֣�ss��
    public static String formatDate(String fmt, Date d) {
    	fmt = fmt==null || fmt.equals("")? "yyyy-MM-dd HH:mm:ss"
    			: fmt.replaceAll("Y","y").replaceAll("D","d").replaceAll("h","H");
		
		return new SimpleDateFormat(fmt).format(d);
	}
    //ͨ��IP��ַ�������MAC��ַ
    public static String getClientMacAddr(String ip){
    	String smac="";
    	try{
    		UdpGetClientMacAddr umac = new UdpGetClientMacAddr(ip);
    		smac = umac.GetRemoteMacAddr();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return smac;
    }
    //ͨ��request���󣬻����MAC��ַ
    public static String getClientMacAddr(HttpServletRequest request){
    	String smac="";
    	String sip = request.getHeader("x-forwarded-for");
    	if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
    		sip = request.getHeader("Proxy-Client-IP");
    	}
    	if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
    		 sip = request.getHeader("WL-Proxy-Client-IP"); 
    	}
    	if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
    		sip = request.getRemoteAddr();  
    	}
    	try{
    		UdpGetClientMacAddr umac = new UdpGetClientMacAddr(sip);
    		smac = umac.GetRemoteMacAddr();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return smac;
    }
}
