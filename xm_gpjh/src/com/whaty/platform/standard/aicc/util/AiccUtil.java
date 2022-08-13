package com.whaty.platform.standard.aicc.util;


public class AiccUtil {
	
	/*
	 * time�ĸ�ʽΪ 14:22:14
	 */
	public static String TimeAdd(String time1,String time2)
	{
		if(time1==null)
			time1="00:00:00";
		if(time2==null)
			time2="00:00:00";
		String[] time1Values=time1.split(":");
		String[] time2Values=time2.split(":");
		if(time1Values[2].indexOf(".")>=0) time1Values[2]=time1.substring(0,time1.indexOf("."));
		if(time1Values[2].indexOf(".")>=0) time1Values[2]=time1.substring(0,time2.indexOf("."));
		int[] timeTotalValues=new int[3];
		int timeAdd=0;
		timeTotalValues[2]=Integer.parseInt(time1Values[2])+Integer.parseInt(time2Values[2]);
		if(timeTotalValues[2]>=60)
		{
			timeAdd=1;
			timeTotalValues[2]=timeTotalValues[2]-60;
		}
		timeTotalValues[1]=Integer.parseInt(time1Values[1])+Integer.parseInt(time2Values[1])+timeAdd;
		if(timeTotalValues[1]>=60)
		{
			timeAdd=1;
			timeTotalValues[2]=timeTotalValues[2]-60;
		}
		else
		{
			timeAdd=0;
		}
		timeTotalValues[0]=Integer.parseInt(time1Values[0])+Integer.parseInt(time2Values[0])+timeAdd;
		String hour=new Integer(timeTotalValues[0]).toString();
		String minute=new Integer(timeTotalValues[1]).toString();
		String second=new Integer(timeTotalValues[2]).toString();
		if(hour.length()==1)
		{
			hour="0"+hour;
		}
		if(minute.length()==1)
		{
			minute="0"+minute;
		}
		if(second.length()==1)
		{
			second="0"+second;
		}
		return(hour+":"+minute+":"+second);
	}
	/**
	 * ת��ʱ���ʽΪ134:34:42Ϊ����
	 * @param time
	 * @return 
	 */
	public static int getSecondsOfTime(String time)
	{
		if(time==null)
				return 0;
		else
		{
			String[] timeValues=time.split(":");
			int seconds=Integer.parseInt(timeValues[0])*60*60+Integer.parseInt(timeValues[1])*60+Integer.parseInt(timeValues[2]);
			return seconds;
		}
	}
	
	/**
	 * ת��ʱ���ʽΪ134:34:42Ϊ����
	 * @param time
	 * @return 
	 */
	public static String getTimeStrOfSecond(int seconds)
	{
		int hour=0;
		int minute=0;
		int second=0;
		hour=(int)(seconds/3600);
		minute=(int)((seconds-hour*3600)/60);
		second=(seconds-3600*hour-60*minute);
		String hourStr=new Integer(hour).toString();
		String minuteStr=new Integer(minute).toString();
		String secondStr=new Integer(second).toString();
		if(hourStr.length()==1)
		{
			hourStr="0"+hourStr;
		}
		if(minuteStr.length()==1)
		{
			minuteStr="0"+minuteStr;
		}
		if(secondStr.length()==1)
		{
			secondStr="0"+secondStr;
		}
		return ""+hourStr+":"+minuteStr+":"+secondStr;
		
	}
	
	public static  String getFirstAccessDate(String core_lesson)
	{
		String firstData=core_lesson.substring(core_lesson.indexOf("FirstDate=")+10,core_lesson.indexOf("FirstDateUP")).trim();
		return firstData;
	}
	
	public static  String getLastAccessDate(String core_lesson)
	{
		String lastData=core_lesson.substring(core_lesson.indexOf("LastDate=")+9,core_lesson.indexOf("LastDateUP")).trim();
		return lastData;
	}
	
	 public static void main(String[] args) {
	       try
	       {
	    	   String str="TotalTime=0:9:30 TotalTimeUP\r\nReadTimes=8 ReadTimesUP\r\nFirstDate=Tue Apr 11 15:25:52 UTC+0800 2006 FirstDateUP" +
	       		"\r\nLastDate=Wed Apr 12 08:58:29 UTC+0800 2006 LastDateUP\r\nLessonTitle= LessonTitleUP";
	       
	           System.out.println(AiccUtil.getFirstAccessDate(str));
	       }
	       catch(Exception e)
	       {
	           System.out.println(e.getMessage());
	       }
	    }
}
