package com.makati.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * @author eden
 * @create 2017-11-18 16:01
 **/
@Slf4j
public class DateUtil {
    private static Map<String,ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    public final static String MDHMSS = "MMddHHmmssSSS";
    public final static String YMD = "yyyyMMdd";
    public final static String YMD_ = "yyyy-MM-dd";
    public final static String HMS = "HHmmss";
    public final static String YMDHMS = "yyyyMMddHHmmss";
    public final static String YM = "yyMM";
    public final static String YMDHMS_ = "yyyy-MM-dd HH:mm:ss";



    public static Date getYestoday(){
        Calendar calendaradd = Calendar.getInstance();
        calendaradd.add(Calendar.DAY_OF_MONTH,-1);
        return calendaradd.getTime();
    }



    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern SimpleDateFormat规则
     * @return 该实例
     */
    private static SimpleDateFormat getSdf(final String pattern){
        ThreadLocal<SimpleDateFormat> t = sdfMap.get(pattern);
        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (t == null){
            synchronized (DateUtil.class){
                // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                log.debug("put new sdf of pattern " + pattern + " to map");
                // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                t = sdfMap.get(pattern);
                if (t == null){
                    t = new ThreadLocal<SimpleDateFormat>(){
                        @Override
                        protected SimpleDateFormat initialValue() {
                            log.debug("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                }
                sdfMap.put(pattern,t);
            }
        }
        return t.get();
    }

    /**
     * 为指定时间按照相应日历字段增加时间
     * @param date 初始时间
     * @param time 要增加的时间
     * @param filed 日历字段 参考Calendar的静态字段
     * @return 修改后的时间
     */
    public static Date addDate(Date date, int time, int filed) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        calendar.add(filed, time);
        return calendar.getTime();
    }
    /**
     * 格局给定的SDF格式化时间
     * @param date 时间
     * @param sdf 指定转换格式
     * @return 转换后的串
     */
    public static String format(Date date, String sdf) {
        if(date == null) return null;
        return getSdf(sdf).format(date);
    }

    /**
     * 把字符串按照指定格式转换
     * @param str 时间串
     * @param sdf 给定转换格式
     * @return 转换后的时间
     */
    public static Date parse(String str, String sdf) {

        Date date = null;
        try {
            date = parseCanThrow(str, sdf);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 把字符串按照指定格式转换,需要主动抛异常情况下使用
     * @param str 时间串
     * @param sdf 给定转换格式
     * @return 转换后的时间
     */
    public static Date parseCanThrow(String str, String sdf) throws ParseException {
        return getSdf(sdf).parse(str);
    }

    /**
     * 判断这个时间是否在当前之前
     * @param date 判断的时间
     * @return true已过期
     */
    public static Boolean isExpire(Date date) {
        return date.before(new Date());
    }

    /**
     * 将毫秒时间转换为指定格式的时间
     * @param time
     * @param sdf
     * @return
     */
    public static String longToString(long time, String sdf) {
        return getSdf(sdf).format(new Date(time));
    }

    /**
     * 将字符串类型的日期转换为毫秒数
     * @param dateStr
     * @return
     */
    public static long stringToLong(String dateStr) {
        dateStr = dateStr.trim();
        if (dateStr.length() == 19 || dateStr.length() == 23) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        Integer.parseInt(dateStr.substring(8, 10)),
                        Integer.parseInt(dateStr.substring(11, 13)),
                        Integer.parseInt(dateStr.substring(14, 16)),
                        Integer.parseInt(dateStr.substring(17, 19)));
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }

        } else if (dateStr.length() == 16) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        Integer.parseInt(dateStr.substring(8, 10)),
                        Integer.parseInt(dateStr.substring(11, 13)),
                        Integer.parseInt(dateStr.substring(14, 16)));
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }

        } else if (dateStr.length() == 14) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(4, 6)) - 1,
                        Integer.parseInt(dateStr.substring(6, 8)),
                        Integer.parseInt(dateStr.substring(8, 10)),
                        Integer.parseInt(dateStr.substring(10, 12)),
                        Integer.parseInt(dateStr.substring(12, 14)));
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }
        } else if (dateStr.length() == 10 || dateStr.length() == 11) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(5, 7)) - 1,
                        Integer.parseInt(dateStr.substring(8, 10)), 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }
        } else if (dateStr.length() == 8 ) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateStr.substring(0, 4)),
                        Integer.parseInt(dateStr.substring(4, 6)) - 1,
                        Integer.parseInt(dateStr.substring(6, 8)), 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return (cal.getTime().getTime());
            } catch (Exception e) {
                return 0;
            }
        } else {
            try {
                return Long.parseLong(dateStr);
            } catch (Exception e) {
                return 0;
            }

        }
    }

    /**
     * 获取今日开始时间
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今日结束时间
     * @return
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


    /**
     * 获取昨天的开始时间
     * @return
     */
    public static Date getYsdStartTime() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE,-1);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }

    /**
     * 获取昨日的结束时间
     * @return
     */
    public static Date getYsdEndTime() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE,-1);
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 59);
        date.set(Calendar.MILLISECOND, 999);
        return date.getTime();
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /*public static void main(String[] args) {
        System.out.println(DateUtil.isEffectiveDate(DateUtil.parse(DateUtil.format(new Date(), "HH:mm:ss"), "HH:mm:ss"), DateUtil.parse("00:00:00", "HH:mm:ss"), DateUtil.parse("16:00:00", "HH:mm:ss")));

        System.out.println(getYsdStartTime());

        System.out.println(getYsdEndTime());

    }*/

    public static Date getEnlishDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss a",Locale.ENGLISH);
        Date d2 = null;
        try {
            d2 = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d2;
    }


}
