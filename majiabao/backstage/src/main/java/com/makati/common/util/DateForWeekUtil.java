package com.makati.common.util;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateForWeekUtil{

  /*public static void main(String[] args) throws  Exception {

        String start_time = "2018-08-11";

        String end_time = "2018-10-22";

       // getDateOfWeek(start_time, end_time);

        System.out.println(getDateOfWeek(start_time, end_time));


      String  today  =  DateUtil.format(DateUtil.addDate(new Date(), 0, Calendar.DATE), DateUtil.YMD_);
      String  endDay = DateForWeekUtil.getLastDayOfWeek(today);

      String  beginDay = DateForWeekUtil.getDay(endDay,-69);

      System.out.println(beginDay);

    }*/



    /**

     * @Title: getDateOfWeek

     * @Description: 获取两个时间内所有周之间的时间段，并且获得是今年第几周（同一年内）

     * @param start_time    开始时间

     * @param end_time      结束时间

     */

    public static  List<Map<String,String>> getDateOfWeek(String start_time, String end_time) {

        List<Map<String,String>>  weekList  = new ArrayList<Map<String, String>>();

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar s_c = Calendar.getInstance();

            //Calendar e_c = Calendar.getInstance();

            String first_week = getFirstDayOfWeek(start_time);//取得开始日期指定日期所在周的第一天

            String last_week = getLastDayOfWeek(end_time);//取得结束日期指定日期所在周的最后一天

            String beginWeek = first_week;
            while(compareDate(beginWeek, last_week)){

                Map<String,String>  map = new HashMap<String, String>();

                map.put("beginDate",beginWeek);

                Date beginTime = sdf.parse(beginWeek);
                s_c.setTime(beginTime);

                s_c.add(Calendar.DATE, 6);

                String endDate = sdf.format(s_c.getTime());

                map.put("endDate",endDate);

                s_c.add(Calendar.DATE, 1);

                weekList.add(map);

                beginWeek = sdf.format(s_c.getTime());

            }

            return weekList;

        } catch (Exception e) {

            e.printStackTrace();
            return  weekList;

        }

    }



    /**

     * 取得指定日期所在周的第一天

     */

    public static String getFirstDayOfWeek(String date) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date time = sdf.parse(date);

            Calendar c = new GregorianCalendar();

          //  c.setFirstDayOfWeek(Calendar.MONDAY);

            c.setFirstDayOfWeek(Calendar.SUNDAY);

            c.setTime(time);

            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday

            return sdf.format(c.getTime());

        } catch (ParseException e) {

            e.printStackTrace();

            return "";

        }





    }



    /**

     * 取得指定日期所在周的最后一天

     */

    public static String getLastDayOfWeek(String date) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date time = sdf.parse(date);

            Calendar c = new GregorianCalendar();

            c.setFirstDayOfWeek(Calendar.SUNDAY);

            c.setTime(time);

            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

            //加入是否大于今天的判断
            Date  today   = new Date();
            String  todayStr = sdf.format(today);
            String  returnValue  = sdf.format(c.getTime());
            if(!compareDateTwo(returnValue,todayStr)){

                c.add(Calendar.DATE, -7);
                returnValue =  sdf.format(c.getTime());
            }


            return returnValue;

        } catch (ParseException e) {

            e.printStackTrace();

            return "";

        }

    }

    /**

     * 取得指定日期9周之前的一天

     */
    public static String getDay(String day,int num) throws  Exception{
        SimpleDateFormat sfd=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(sfd.parse(day));

        calendar.add(Calendar.DATE, num);

        return sfd.format(calendar.getTime());
    }


    /**

     * compareDate方法

     * <p>方法说明：

     * 		比较endDate是否是晚于startDate；

     * 			如果是，返回true， 否则返回false

     * </p>

     */

    public static boolean compareDate(String startDate, String endDate) {

        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date date1 = dateFormat.parse(startDate);

            java.util.Date date2 = dateFormat.parse(endDate);

            if (date1.getTime() > date2.getTime())

                return false;

            return true; //startDate时间上早于endDate



        } catch (Exception e) {

            return false;

        }

    }


    /**
     * 包括等于
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean compareDateTwo(String startDate, String endDate) {

        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date date1 = dateFormat.parse(startDate);

            java.util.Date date2 = dateFormat.parse(endDate);

            if (date1.getTime() >= date2.getTime())

                return false;

            return true; //startDate时间上早于endDate



        } catch (Exception e) {

            return false;

        }

    }

}
