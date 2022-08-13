package com.whaty.platform.campus;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.util.log.Log;

public abstract class CampusFactory {
	private static String className = "com.whaty.platform.database.oracle.standard.campus.OracleCampusFactory";

	private static Object initLock = new Object();

	private static CampusFactory factory;

	/** Creates a new instance of UserFactory */
	public CampusFactory() {
	}

	/**
	 * PlatformFactoryʵ
	 * 
	 * @return PlatformFactoryʵ
	 */
	public static CampusFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass("CampusFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						Log.setDebug("new instance of factory");
						factory = (CampusFactory) c.newInstance();
					} catch (Exception e) {
						System.err
								.println("Failed to load CampusFactory class "
										+ className
										+ ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}

	public abstract CampusManage creatCampusManage(
			CampusManagerPriv amanagerpriv);

	public abstract CampusNormalManage creatCampusNormalManage(
			CampusManagerPriv amanagerpriv);

	public abstract CampusNormalManage creatCampusNormalManage();

	public abstract CampusManagerPriv getCampusManagerPriv(
			java.lang.String id);
	public abstract CampusManagerPriv getNormalPriv();
	
	public abstract CampusManagerPriv getManagerPriv();
}
