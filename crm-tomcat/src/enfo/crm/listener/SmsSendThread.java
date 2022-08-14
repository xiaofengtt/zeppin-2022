/*
 * 创建日期 2012-3-22
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ServiceTaskVO;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SmsSendThread extends Thread {	
	private Map map;
	
	public SmsSendThread(Map map){
		this.map = map;
	}
	
	public void run(){
		String service_type_name = "";
		String executeTime = "";
		String result = "";
		Integer serial_no_detail = new Integer(0);
		Integer service_status = new Integer(0);
		Integer needFeedback = new Integer(0);
		Integer satifaction = new Integer(0);
		Integer service_way = new Integer(0);
		Integer target_custid = new Integer(0);
		String mobile = "";
		String SmsContent = "";
		ServiceTaskVO vo_treat = new ServiceTaskVO();
		
		String ret = "";
		String[] arr = null;
		java.util.Date currentDateTime = new java.util.Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = null;
		
		SmsSendAction smsSendAction = SmsSendAction.getInstance();
		
		datestr=sdf.format(new Date());					
		
		serial_no_detail = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),null);					
		needFeedback = Utility.parseInt(Utility.trimNull(map.get("NeedFeedback")), new Integer(0));					
		service_way = Utility.parseInt(Utility.trimNull(map.get("ServiceWay")), new Integer(0));
		satifaction = Utility.parseInt(Utility.trimNull(map.get("Satisfaction")), new Integer(0));
		mobile = Utility.trimNull(map.get("Mobile"));
		SmsContent = Utility.trimNull(map.get("SmsContent"));
		
		ret = smsSendAction.sendSmsWithSigln(mobile,SmsContent,new Integer(-3),new Integer(888));					
		arr = Utility.splitString(ret,"|");
	    		
	    if(needFeedback.intValue()==1){
	    	service_status = new Integer(3);
	    	result = arr[1];
	    }
	    else{
	    	service_status = new Integer(4);
	    	result = arr[1];
	    }				    
	    
		vo_treat.setSerial_no(serial_no_detail);
		vo_treat.setServiceWay("110905");		
		vo_treat.setExecutorTime(Utility.trimNull(datestr));
		vo_treat.setStatus(service_status);
		vo_treat.setResult(result);
		vo_treat.setNeedFeedBack(needFeedback);
		vo_treat.setSatisfaction(satifaction);
		vo_treat.setInputMan(new Integer(888));
		
		System.out.println("--------------自动发送处理完毕");
		try {
			smsSendAction.treat_details(vo_treat);
		} catch (BusiException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}		
	}
}
