/**
 * 
 */
package com.whaty.platform.listener.sms;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wq
 * 
 */
public class SendReSMSListener implements ServletContextListener {

	private Timer timer = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("�ط����Ŷ�ʱ������");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */ 
	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		event.getServletContext().log("�ط����Ŷ�ʱ��������");
		// ������ÿ��һ������ѯһ�ζ��ŷ�������,����������Ƚϴ�Ļ���������ֵ��Ĵ�һ��,��������ֵ�����Ӱ����趨�Ĵ�������.
		timer.schedule(new SendReSMSTask(), 0, 3600 * 1000);
		event.getServletContext().log("�Ѿ����������ȱ�");
	}
}
