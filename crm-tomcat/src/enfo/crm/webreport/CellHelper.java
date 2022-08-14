/**
 * <p>Title: CellHelper</p> 
 * <p>CreateTime: 2005-4-15</p>
 * @author <a href="mailto:caiyuan@singlee.com.cn">Cai Yuan</a>
 * @version 1.0 
 * 
 */
//-------------------其他功能类------------------------------------
package enfo.crm.webreport;

import java.util.*;

public class CellHelper 
{
	
	public static String[] splitString(String str, String sign)
	{
		int count = getStrCount(str, sign);
		int j = 0;
		String[] arr = new String[count];
		for (int i = 0; i < count; i++)
		{
			arr[i] = "";
			if (str.indexOf(sign) != -1)
			{
				j = str.indexOf(sign);
				arr[i] = str.substring(0, j);
				str = str.substring(j + 1);
			}
			else
			{
				arr[i] = str;
			}

		}
		return arr;
	}
  
	public static int getStrCount(String str, String sign)
	{
		if (str == null)
			return 0;
		StringTokenizer s = new StringTokenizer(str, sign);
		return s.countTokens();
	}
  
	public static String trimNull(Object o)
	{
		if (o == null)
			return "";
		return o.toString();
	}
	
	public static String trimNull(Object o, String vdefault)
	{
		if (o == null)
			return vdefault;
		return o.toString();
	}
	
	public static int parseInt(String s, int defaultValue)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}	
	
	// 得到整型日期	
	public static int getDateInt(java.sql.Timestamp date)
	{
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(new Date(date.getTime()));
		return dStart.get(Calendar.YEAR) * 10000 + (dStart.get(Calendar.MONTH) + 1) * 100 + dStart.get(Calendar.DATE);
	}
	
	// 中文格式化日期
	public static String getDateCn(int date)
	{
		if (date <= 19000000)
			return "";
		StringBuffer strTime = new StringBuffer(15);
		String strDate = Integer.toString(date);
		String strYear = strDate.substring(0, 4);
		strTime.append(strYear);
		strTime.append("年");
		String strMonth = strDate.substring(4, 6);
		strTime.append(strMonth);
		strTime.append("月");
		String str = strDate.substring(6, 8);
		strTime.append(str);
		strTime.append("日");
		return strTime.toString();
	}
	
	// 得到当前的中文格式化日期
	public static String getDateCn()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());		
		return getDateCn(getDateInt(date));
	}
	
	// 得到当前整型日期
	public static int getDateInt()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());		
		return getDateInt(date);
	}
	
	// 得到当前年和月YYYYMM
	public static int getYMInt()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(new Date(date.getTime()));
		return dStart.get(Calendar.YEAR) * 100 + (dStart.get(Calendar.MONTH) + 1);
	}
	
	//得到当前年YYYY
	public static int getYearInt()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(new Date(date.getTime()));
		return dStart.get(Calendar.YEAR) ;
	}
	
	//得到当前月
	public static int getMonthInt()
	{
		java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
		Calendar dStart = Calendar.getInstance();
		dStart.setTime(new Date(date.getTime()));
		return dStart.get(Calendar.MONTH);
	}

}
