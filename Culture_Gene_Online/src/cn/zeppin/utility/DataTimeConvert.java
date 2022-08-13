/**
 * 
 */
package cn.zeppin.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeConvert {

	public static Timestamp stringToTimeStamp(String dateTimeString) {
		return Timestamp.valueOf(dateTimeString);
	}

	public static Timestamp stringToTimeStamp(String dateString, String timeString) {
		return Timestamp.valueOf(dateString + timeString);
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

	public static String timespanToString(Timestamp ts, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(ts);
	}

	public static String DateToString(Date date, String format) {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		if (!format.equals("")) {
			defaultFormat = format;
		}
		java.text.DateFormat format1 = new java.text.SimpleDateFormat(defaultFormat);
		String s = format1.format(date);

		return s;
	}

	public static Date stringToDatetime(String s, String format) {

		String defaultFormat = "yyyy-MM-dd";
		if (!format.equals("")) {
			defaultFormat = format;
		}
		DateFormat fmt = new SimpleDateFormat(defaultFormat);
		Date date = null;
		try {
			date = fmt.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
