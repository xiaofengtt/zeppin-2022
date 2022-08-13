package com.cmos.china.mobile.media.core.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utlity {
	
	public static final String basePath = "E:/apache-tomcat-8.0.30/webapps/nmgs";
	
	public static String timeSpanToString(Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(ts);
	}
	
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
}
