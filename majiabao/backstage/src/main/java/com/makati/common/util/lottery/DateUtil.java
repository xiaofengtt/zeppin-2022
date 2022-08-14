package com.makati.common.util.lottery;

import java.text.DateFormat;
import java.util.*;

/**
 * 
 * @author jim
 *
 */
public class DateUtil {

	/**
	 * 根据当前日期获得所在周的日期区间（周一和周日日期）
	 * @param date
	 * @return
	 */
	public static List<Date> getTimeInterval(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		DateFormat sdf = DateFormat.getDateInstance();
		//String imptimeBegin = sdf.format(cal.getTime());
		Date imptimeBegin = cal.getTime();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(imptimeBegin);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		imptimeBegin = calendar.getTime();
		//System.out.println("开始时间："+calendar.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		//String imptimeEnd = sdf.format(cal.getTime());
		Date imptimeEnd = cal.getTime();
		calendar.setTime(imptimeEnd);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		imptimeEnd = calendar.getTime();
		//System.out.println("结束时间："+calendar.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		List<Date> dates = new ArrayList<Date>();
			dates.add(imptimeBegin);
			dates.add( imptimeEnd);
		return dates;
	}

	public static String getCurrentTimeByCalendar(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份
		int month=cal.get(Calendar.MONTH)+1;//获取月份
		int day=cal.get(Calendar.DATE);//获取日
		//int hour=cal.get(Calendar.HOUR);//小时
		//int minute=cal.get(Calendar.MINUTE);//分
		//int second=cal.get(Calendar.SECOND);//秒
		//int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
		return year+"年"+month+"月"+day+"日";
	}

}
