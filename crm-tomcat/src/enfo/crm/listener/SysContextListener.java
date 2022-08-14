/*
 * �������� 2012-3-21
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import enfo.crm.dao.BusiException;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class SysContextListener implements ServletContextListener {
	private TimeManager timeManager = null;
	
	public void contextInitialized(ServletContextEvent event){
		event.getServletContext().log("��ʱ��������");		
		try {
			timeManager = new TimeManager(event);
			System.out.println("-----��ʱ��������_1`");
		} catch (BusiException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().log("��ʱ������");
		if(timeManager!=null){
			System.out.println("-----��ʱ������_2");
			timeManager.cancel();
		}		
	}
}
