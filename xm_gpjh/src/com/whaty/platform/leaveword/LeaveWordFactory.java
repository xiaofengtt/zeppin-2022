package com.whaty.platform.leaveword;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.util.log.Log;

public abstract class LeaveWordFactory {
	private static String className = "com.whaty.platform.database.oracle.standard.leaveword.OracleLeaveWordFactory";

	private static Object initLock = new Object();

	private static LeaveWordFactory factory;

	/** Creates a new instance of UserFactory */
	public LeaveWordFactory() {
	}

	/**
	 * ����PlatformFactoryʵ��
	 * 
	 * @return PlatformFactoryʵ��
	 */
	public static LeaveWordFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass("LeaveWordFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						Log.setDebug("new instance of factory");
						factory = (LeaveWordFactory) c.newInstance();
					} catch (Exception e) {
						System.err
								.println("Failed to load LeaveWordFactory class "
										+ className
										+ ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}

	public abstract LeaveWordManage creatLeaveWordManage(
			LeaveWordManagerPriv amanagerpriv);

	public abstract LeaveWordNormalManage creatLeaveWordNormalManage(
			LeaveWordManagerPriv amanagerpriv);

	public abstract LeaveWordNormalManage creatLeaveWordNormalManage();

	public abstract LeaveWordManagerPriv getLeaveWordManagerPriv(
			java.lang.String id);
}
