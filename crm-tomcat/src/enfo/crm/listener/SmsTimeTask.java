/*
 * �������� 2012-3-21
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class SmsTimeTask extends TimerTask{

	/* ���� Javadoc��
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		//�����ֶα���	
		SmsSendThread thread = null;
		synchronized(this){
			try {
				//��ö���		
				SmsSendAction smsSendAction = SmsSendAction.getInstance();
				List smsAutoTaskList = null;
				Map map = null;			
					
				//�ѳ��Զ����Ͷ�������
				smsAutoTaskList = smsSendAction.querySmsAutoTask();

				//ѭ��������
				if(smsAutoTaskList!=null&&smsAutoTaskList.size()>0){
					for(int i = 0; i<smsAutoTaskList.size();i++){
						map = (Map)smsAutoTaskList.get(i);
						thread = new SmsSendThread(map);
						thread.start();						
					}
				}	
			} catch (Exception e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}	
		}	
	}
}
