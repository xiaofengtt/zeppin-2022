/*
 * 创建日期 2012-3-21
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import enfo.crm.tools.Utility;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TimeManager {
	 //时间间隔
	 private ServletContextEvent context = null;
	 private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	 private Timer timer = null;
	 private int hour,minute,second;
	 
	 //暂时写死 定制每日7:00执行方法
	 public TimeManager(ServletContextEvent context) throws BusiException{	
	 	  this.context = context;	 	  
	 	  this.init();	
	 	  
	 	  Calendar calendar = Calendar.getInstance();  

		  calendar.set(Calendar.HOUR_OF_DAY, this.hour);
		  calendar.set(Calendar.MINUTE, this.minute);
		  calendar.set(Calendar.SECOND, this.second);
		  
		  //第一次执行定时任务的时间
		  Date date=calendar.getTime(); 
		  
		  //如果第一次执行定时任务的时间 小于 当前的时间
		  //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		  if (date.before(new Date())) {
		     date = this.addDay(date, 1);
		  }

		  this.timer = new Timer();		  
		  SmsTimeTask task = new SmsTimeTask();
		  //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		  context.getServletContext().log("执行自动发送短信任务");
		  System.out.println("----执行自动发送短信任务");
		  timer.schedule(task,date,PERIOD_DAY);
	 }
	 
	 //增加或减少天数
	 public Date addDay(Date date, int num) {
		  Calendar startDT = Calendar.getInstance();
		  startDT.setTime(date);
		  startDT.add(Calendar.DAY_OF_MONTH, num);
		  return startDT.getTime();
	 }
	 
	 //销毁定时器
	 public void cancel(){
	 	if(timer!=null){
	 		timer.cancel();
	 	}
	 }
	 
	 public void init() throws BusiException{
	 	this.hour = 7;
	 	this.minute = 0;
	 	this.second = 0;
	 	List rsList = null;	
	 	Map map = null;
	 	Integer type_value;
	 	Integer type_content;
		String sqlStr = "SELECT * FROM TDICTPARAM WHERE TYPE_ID = 8005";
		
		rsList = CrmDBManager.listBySql(sqlStr);
		
		if(rsList!=null){
			for(int i=0; i<rsList.size();i++){
				map = (Map) rsList.get(i);
				type_value = Utility.parseInt(Utility.trimNull(map.get("TYPE_VALUE")),new Integer(0));
				
				if(type_value.intValue()==800501){
					type_content = Utility.parseInt(Utility.trimNull(map.get("TYPE_CONTENT")),new Integer(7));
					this.hour = type_content.intValue();
				}
				else if(type_value.intValue()==800502){
					type_content = Utility.parseInt(Utility.trimNull(map.get("TYPE_CONTENT")),new Integer(0));
					this.minute = type_content.intValue();
				}
				else if(type_value.intValue()==800503){
					type_content = Utility.parseInt(Utility.trimNull(map.get("TYPE_CONTENT")),new Integer(0));
					this.second = type_content.intValue();
				}
			}
		}		
	 }
}
