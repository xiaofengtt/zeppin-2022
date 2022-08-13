package com.whaty.platform.sms;

import com.whaty.platform.GlobalProperties;
import com.whaty.util.log.Log;

public abstract class SmsFactory {
	private static String className = "com.whaty.platform.sms.imp.OracleSmsFactory";

	private static Object initLock = new Object();

	private static SmsFactory factory;

	/** Creates a new instance of UserFactory */
	public SmsFactory() {
	}

	/**
	 * ����PlatformFactoryʵ��
	 * 
	 * @return PlatformFactoryʵ��
	 */
	public static SmsFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getFactoryClass("SmsFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						Log.setDebug("new instance of factory");
						factory = (SmsFactory) c.newInstance();
					} catch (Exception e) {
						System.err.println("Failed to load SmsFactory class "
								+ className + ".system function normally.");
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return factory;
	}

	public abstract SmsManage creatSmsManage();


}
