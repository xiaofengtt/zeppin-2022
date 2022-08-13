package com.whaty.platform.campus;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.user.CampusInfoManagerPriv;

/**
 * @author chenjian
 * 
 */
public abstract class CampusInfoFactory {
	private static String className = "com.whaty.platform.database.oracle.standard.campus.OracleCampusInfoFactory";

	private static Object initLock = new Object();

	private static CampusInfoFactory factory;

	/** Creates a new instance of UserFactory */
	public CampusInfoFactory() {
	}

	/**
	 * ����PlatformFactoryʵ��
	 * 
	 * @return PlatformFactoryʵ��
	 */
	public static CampusInfoFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass("CampusInfoFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						factory = (CampusInfoFactory) c.newInstance();
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

	/**
	 * ��ְ̨ҵ��չ
	 * 
	 * @param managerpriv
	 * @return
	 * @throws PlatformException
	 */
	public abstract CampusInfoManage creatCampusInfoManage(
			CampusInfoManagerPriv campusInfoManagerPriv) throws PlatformException;

	/**
	 * �Ż�ְҵ��չ
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract CampusNormalInfoManage creatCampusNormalInfoManage()
			throws PlatformException;

	/**
	 * ��ְ̨ҵ��չȨ��
	 * 
	 * @param managerId
	 * @return
	 * @throws PlatformException
	 */
	public abstract CampusInfoManagerPriv getCampusInfoManagerPriv(
			String managerId) throws PlatformException;

}
