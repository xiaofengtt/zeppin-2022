package cn.zeppin.utility.sms;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class SendCheck {

	//主要增加单位时间内的发送次数限制
	//主要算法逻辑：
	/*
	 * 发送短信前先查询session中是否有对应的手机号信息
	 * 若有则获取出该信息当前发送次数与受限次数做判断，符合条件的则发送，否则提示发送频率太快，请稍候
	 * 若超过单位时间限制，则直接清空session，发送短信
	 * 若无则直接发送，发送后，无论成功与否，累积记录一次发送记录
	 */
	
	/**
	 * 校验分钟
	 * @param line_minutes
	 * @param times
	 * @param timelength
	 * @return
	 */
	public static String checkMinutes(int line_minutes, int times, long timelength) {
		return check(line_minutes, 0, 0, times, timelength);
	}
	
	/**
	 * 校验分钟
	 * @param line_minutes
	 * @param times
	 * @param timelength
	 * @return
	 */
	public static String checkHours(int line_hours, int times, long timelength) {
		return check(0, line_hours, 0, times, timelength);
	}
	
	/**
	 * 通用校验方法
	 * @return
	 */
	public static String check(int line_minutes, int line_hours, int line_days, int times, long timelength) {
		String result = "0_ok";
		long realtime = System.currentTimeMillis();
		long checktime = realtime-timelength;
		
		//验证分钟
		if(line_minutes > 0 && inMinutes(checktime)) {
			if(times >= line_minutes) {//
				return "1_每分钟仅可获取"+line_minutes+"次短信验证码。";
			} else {
				return "1_ok";
			}
		}
		
		//验证小时-并增加是否清空计数标识0-不校验-清空   1-计数不清空 2-计数清空
		if(line_hours > 0 && inHours(checktime)) {
			if(times >= line_hours) {//
				return "1_每小时仅可获取"+line_hours+"次短信验证码。";
			} else {
				return "1_ok";
			}
		}
		
		return result;
	}
	
	/**
	 * 是否在一分钟内
	 * 
	 * @param times
	 * @return true-是；false-否
	 */
	private static boolean inMinutes(long times) {
		return times < (60*1000);
	}
	
	/**
	 * 是否在一小时内
	 * 
	 * @param times
	 * @return true-是；false-否
	 */
	private static boolean inHours(long times) {
		return times < (60*60*1000);
	}
	
	
	
	// 获得某天最大时间 2021-07-27 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2021-07-27 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
