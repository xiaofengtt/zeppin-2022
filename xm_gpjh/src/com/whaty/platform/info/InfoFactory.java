/**
 * 
 */
package com.whaty.platform.info;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.info.user.InfoManagerPriv;

/**
 * @author chenjian
 * 
 */
public abstract class InfoFactory {
	private static String className = "com.whaty.platform.database.oracle.standard.info.OracleInfoFactory";

	private static Object initLock = new Object();

	private static InfoFactory factory;

	/** Creates a new instance of UserFactory */
	public InfoFactory() {
	}

	/**
	 * 创建PlatformFactory实例
	 * 
	 * @return PlatformFactory实例
	 */
	public static InfoFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass("InfoFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						factory = (InfoFactory) c.newInstance();
					} catch (Exception e) {
						System.err.println("Failed to load InfoFactory class "
								+ className + ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}

	public abstract InfoManage creatInfoManage(InfoManagerPriv managerpriv,
			String newsTypeId) throws PlatformException;

	public abstract InfoManage creatInfoManage(InfoManagerPriv managerpriv)
			throws PlatformException;

	public abstract NormalInfoManage creatNormalInfoManage()
			throws PlatformException;

	public abstract InfoManagerPriv getInfoManagerPriv(String managerId)
			throws PlatformException;

	public abstract InfoManagerPriv getInfoManagerPriv(String managerId,
			String newsTypeId) throws PlatformException;
	
	/**
	 * 通过平台管理权限构建信息管理权限
	 */
	public abstract InfoManagerPriv getInfoManagerPriv(ManagerPriv priv) throws PlatformException;
	
	/**
	 * submanager
	 */
	public abstract InfoManagerPriv getInfoManagerPriv(SiteManagerPriv priv) throws PlatformException;
}
