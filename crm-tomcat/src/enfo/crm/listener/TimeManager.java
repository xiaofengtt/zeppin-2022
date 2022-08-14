/*
 * �������� 2012-3-21
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class TimeManager {
	 //ʱ����
	 private ServletContextEvent context = null;
	 private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	 private Timer timer = null;
	 private int hour,minute,second;
	 
	 //��ʱд�� ����ÿ��7:00ִ�з���
	 public TimeManager(ServletContextEvent context) throws BusiException{	
	 	  this.context = context;	 	  
	 	  this.init();	
	 	  
	 	  Calendar calendar = Calendar.getInstance();  

		  calendar.set(Calendar.HOUR_OF_DAY, this.hour);
		  calendar.set(Calendar.MINUTE, this.minute);
		  calendar.set(Calendar.SECOND, this.second);
		  
		  //��һ��ִ�ж�ʱ�����ʱ��
		  Date date=calendar.getTime(); 
		  
		  //�����һ��ִ�ж�ʱ�����ʱ�� С�� ��ǰ��ʱ��
		  //��ʱҪ�� ��һ��ִ�ж�ʱ�����ʱ�� ��һ�죬�Ա���������¸�ʱ���ִ�С��������һ�죬���������ִ�С�
		  if (date.before(new Date())) {
		     date = this.addDay(date, 1);
		  }

		  this.timer = new Timer();		  
		  SmsTimeTask task = new SmsTimeTask();
		  //����ָ����������ָ����ʱ�俪ʼ�����ظ��Ĺ̶��ӳ�ִ�С�
		  context.getServletContext().log("ִ���Զ����Ͷ�������");
		  System.out.println("----ִ���Զ����Ͷ�������");
		  timer.schedule(task,date,PERIOD_DAY);
	 }
	 
	 //���ӻ��������
	 public Date addDay(Date date, int num) {
		  Calendar startDT = Calendar.getInstance();
		  startDT.setTime(date);
		  startDT.add(Calendar.DAY_OF_MONTH, num);
		  return startDT.getTime();
	 }
	 
	 //���ٶ�ʱ��
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
