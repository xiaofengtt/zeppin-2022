/**
 * 
 */
package com.whaty.platform.logmanage;

import com.whaty.platform.GlobalProperties;
import com.whaty.util.log.Log;

/**
 * @author wq
 * 
 */
public abstract class LogFactory {
	private static String className = "com.whaty.platform.database.oracle.standard.logmanage.OracleLogFactory";

	private static Object initLock = new Object();

	private static LogFactory factory;

	/** Creates a new instance of LogFactory */
	public LogFactory() {
	}

	/**
	 * LogFactoryʵ
	 * 
	 * @return LogFactoryʵ
	 */
	public static LogFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass(LogFactory.className);
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						Log.setDebug("new instance of factory");
						factory = (LogFactory) c.newInstance();
					} catch (Exception e) {
						System.err
								.println("Failed to load LogFactory class "
										+ className
										+ ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}

	public abstract LogManage creatLogManage();
}
