/**
 * 
 */
package com.cmos.china.mobile.media.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeConvert {

	/**
	 * 字符串转时间戳
	 */
	public static Timestamp stringToTimeStamp(String dateTimeString) {
		return Timestamp.valueOf(dateTimeString);
	}

	public static Timestamp stringToTimeStamp(String dateString, String timeString) {
		return Timestamp.valueOf(dateString + timeString);
	}
	
	/**
	 * 格式化字符串转时间戳
	 */
	public static Timestamp getCurrentTime(String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!"".equals(format)) {
			defaultFormat = format;
		}
		SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	
	/**
	 * 时间戳转字符串
	 */
	public static String timespanToString(Timestamp ts, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(ts);
	}

	/**
	 * 格式化时间转字符串
	 */
	public static String DateToString(Date date, String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!"".equals(format)) {
			defaultFormat = format;
		}
		java.text.DateFormat format1 = new java.text.SimpleDateFormat(defaultFormat);
		String s = format1.format(date);

		return s;
	}

	/**
	 * 字符串转日期
	 */
	public static Date stringToDatetime(String s, String format) {
		String defaultFormat = "yyyy-MM-dd";
		if (!"".equals(format)) {
			defaultFormat = format;
		}
		DateFormat fmt = new SimpleDateFormat(defaultFormat);
		Date date = null;
		try {
			date = fmt.parse(s);
		} catch (ParseException e) {
		}
		return date;
	}
	
	/**
	 * 格式化时间转秒数
	 */
	public static String getSecondTime(String time){
		String[] times = time.split(":");
		if(times.length==2){
			Double minute = Double.valueOf(times[0]);
			Double second = Double.valueOf(times[1]);
			Double doubleTime = minute * 60 + second;
			return doubleTime.toString();
		}else if(times.length==3){
			Double hour = Double.valueOf(times[0]);
			Double minute = Double.valueOf(times[1]);
			Double second = Double.valueOf(times[2]);
			Double doubleTime = hour*3600 + minute * 60 + second;
			return doubleTime.toString();
		}else{
			return "0";
		}
	}
	
	/**
	 * 秒数转格式化时间
	 */
	public static String getFormatTime(String time){
		int seconds = (int)(100*Double.valueOf(time));
		int hour = seconds/360000;
		seconds = seconds - hour * 360000;
		int minute = seconds/6000;
		seconds = seconds - minute * 6000;
		int second = seconds/100;
		seconds = seconds- second * 100;
		String hourString = hour>=10?String.valueOf(hour):"0"+String.valueOf(hour);
		String minuteString = minute>=10?String.valueOf(minute):"0"+String.valueOf(minute);
		String secondString = second>=10?String.valueOf(second):"0"+String.valueOf(second);
		String secondsString = seconds>=10?String.valueOf(seconds):"0"+String.valueOf(seconds);
		return hourString+":"+minuteString+":"+secondString+"."+secondsString;
	}
	
	/**
	 * 根据毫秒计算间隔天数
	 */
	public static int getDayDate(Date date){
		Date now = new Date();
		long ei = date.getTime()-now.getTime();
		return (int)(ei/(1000*60*60*24)); 
	}

}
