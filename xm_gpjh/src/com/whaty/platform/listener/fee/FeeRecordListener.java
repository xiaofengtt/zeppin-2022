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
		event.getServletContext().log("费用记录处理定时器销毁");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		//timer = new Timer(true);
		//event.getServletContext().log("费用记录处理定时器已启动");
		// 在这里每隔一秒钟轮询一次短信发送任务,如果任务间隔比较大的话建议把这个值设的大一点,但此设置值将间接影响可设定的触发精度.
		//timer.schedule(new DealFeeRecordTask(), 0, 60 * 1000);
		//event.getServletContext().log("已经添加任务调度表");
	}
}
