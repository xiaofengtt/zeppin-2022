/**
 * 
 */
package com.whaty.platform.standard.scorm.util;


import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;


/**
 * @author chenjian
 *
 */
public class ScormUtil {

	/*
	 * time的格式为 14:22:14
	 */
	public static String TimeAdd(String time1,String time2) throws ScormException
	{
		//System.out.println("time1:"+time1);
		//System.out.println("time2:"+time2);
		if(time1==null || time1.indexOf(":")<0)
			time1="00:00:00.0";
		if(time2==null || time2.indexOf(":")<0)
			time2="00:00:00.0";
	
		if(time1.indexOf(".")<0)
			time1=time1+".0";
		if(time2.indexOf(".")<0)
			time2=time2+".0";
		
		//gaoyuan modify 09/09/22
		String microsecond1="0"+time1.substring(time1.indexOf("."),time1.length());
		String microsecond2="0"+time2.substring(time2.indexOf("."),time2.length());
		
		time1=time1.substring(0,time1.indexOf("."));
		time2=time2.substring(0,time2.indexOf("."));
		
		String[] time1Values=time1.split(":");
		String[] time2Values=time2.split(":");
		int[] timeTotalValues=new int[2];
		int  timeSecond=0;
		int timeAdd=0;
		
		double microSecond=0;
		microSecond=(double)(Double.parseDouble(microsecond1)*100+Double.parseDouble(microsecond2)*100)/100;
		//java.text.DecimalFormat df=new java.text.DecimalFormat("#.00"); 
		//microSecond=new Double(df.format(microSecond));

		String tmpSec=new Double(microSecond).toString();
		String intSec="0";
		String nintSec="0";
		if(tmpSec!=null &&tmpSec.indexOf(".")>0)
		{
			intSec=tmpSec.substring(0,tmpSec.indexOf("."));
			nintSec=tmpSec.substring(tmpSec.indexOf(".")+1,tmpSec.length());
		}
		
		//System.out.println("microSecond:"+microSecond);
		//System.out.println("intSec:"+intSec);
		
		if(microSecond>=1)
		{
			timeAdd=Integer.parseInt(intSec);
		}
		
		//System.out.println("timeAdd:"+timeAdd);
		//microSecond=new Double(microSecond);
		
	//	if(nintSec!=null && nintSec.indexOf(".")>0)
	//		nintSec=nintSec.substring(nintSec.indexOf(".")+1,nintSec.length());
		
		timeSecond=Integer.parseInt(time1Values[2])+Integer.parseInt(time2Values[2])+timeAdd;
		timeAdd=0;
		if(timeSecond>=60)
		{
			timeAdd=1;
			timeSecond=timeSecond-60;
		}
		else
		{
			timeAdd=0;
		}
		
		timeTotalValues[1]=Integer.parseInt(time1Values[1])+Integer.parseInt(time2Values[1])+timeAdd;
		if(timeTotalValues[1]>=60)
		{
			timeAdd=1;
			timeTotalValues[1]=timeTotalValues[1]-60;
		}
		else
		{
			timeAdd=0;
		}
		timeTotalValues[0]=Integer.parseInt(time1Values[0])+Integer.parseInt(time2Values[0])+timeAdd;
		String hour=new Integer(timeTotalValues[0]).toString();
		String minute=new Integer(timeTotalValues[1]).toString();
		String second=new Integer(timeSecond).toString();
		StrManage strManage=StrManageFactory.creat(second);
		
		String[] temp = second.split("\\.");
		if(temp!=null && temp.length>1 && temp[1].length()>=2){
			second = temp[0];
		}
//		try {
//			second=strManage.formatNumber("00.0");
//		} catch (WhatyUtilException e) {
//			throw new ScormException("time second format error!");
//		}
		
		if(hour.length()==1)
		{
			hour="0"+hour;
			
		}
		if(minute.length()==1)
		{
			minute="0"+minute;
		}
		if(second.indexOf(".")<0)
		{
			if(second.length()==1)
			{
				second="0"+second;
			}
		}
		else
		{
			if(second.substring(0,second.indexOf(".")).length()==1)
			{
				second="0"+second;
			}
		}
		if(nintSec!=null && nintSec.length()>2)
		{
			nintSec=nintSec.substring(0,2);
		}
		//System.out.println("aaaaaaa:"+hour+":"+minute+":"+second+"."+nintSec);
		return(hour+":"+minute+":"+second+"."+nintSec);
	}
	
	 public static void main(String[] args) {
	       try
	       {
	    	   String time1="32:52:55.4";
	    	   String time2="1:7:25.3";
	       
	           System.out.println(ScormUtil.TimeAdd(time1, time2));
	       }
	       catch(Exception e)
	       {
	           System.out.println(e.getMessage());
	       }
	    }
}
