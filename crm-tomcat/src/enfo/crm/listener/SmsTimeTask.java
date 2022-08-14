/*
 * 创建日期 2012-3-21
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.listener;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import enfo.crm.tools.Utility;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SmsTimeTask extends TimerTask{

	/* （非 Javadoc）
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		//声明字段变量	
		SmsSendThread thread = null;
		synchronized(this){
			try {
				//获得对象		
				SmsSendAction smsSendAction = SmsSendAction.getInstance();
				List smsAutoTaskList = null;
				Map map = null;			
					
				//搜出自动发送短信任务
				smsAutoTaskList = smsSendAction.querySmsAutoTask();

				//循环发短信
				if(smsAutoTaskList!=null&&smsAutoTaskList.size()>0){
					for(int i = 0; i<smsAutoTaskList.size();i++){
						map = (Map)smsAutoTaskList.get(i);
						thread = new SmsSendThread(map);
						thread.start();						
					}
				}	
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}	
		}	
	}
}
