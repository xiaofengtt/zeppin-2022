/*
 * 创建日期 2012-3-21
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import enfo.crm.dao.BusiException;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SysContextListener implements ServletContextListener {
	private TimeManager timeManager = null;
	
	public void contextInitialized(ServletContextEvent event){
		event.getServletContext().log("定时器已启动");		
		try {
			timeManager = new TimeManager(event);
			System.out.println("-----定时器已启动_1`");
		} catch (BusiException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().log("定时器销毁");
		if(timeManager!=null){
			System.out.println("-----定时器销毁_2");
			timeManager.cancel();
		}		
	}
}
