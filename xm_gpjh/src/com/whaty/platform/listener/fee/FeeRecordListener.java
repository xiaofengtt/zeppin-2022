/**
 * 
 */
package com.whaty.platform.listener.fee;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wq
 * 
 */
public class FeeRecordListener implements ServletContextListener {

	private Timer timer = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("���ü�¼����ʱ������");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		//timer = new Timer(true);
		//event.getServletContext().log("���ü�¼����ʱ��������");
		// ������ÿ��һ������ѯһ�ζ��ŷ�������,����������Ƚϴ�Ļ���������ֵ��Ĵ�һ��,��������ֵ�����Ӱ����趨�Ĵ�������.
		//timer.schedule(new DealFeeRecordTask(), 0, 60 * 1000);
		//event.getServletContext().log("�Ѿ����������ȱ�");
	}
}
