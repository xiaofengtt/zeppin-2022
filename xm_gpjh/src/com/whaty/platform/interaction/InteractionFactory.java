/*
 * AnnounceFactory.java
 *
 * Created on 2005��1��6��, ����2:15
 */

package com.whaty.platform.interaction;

import com.whaty.platform.GlobalProperties;

/**
 * 
 * @author Administrator
 */
public abstract class InteractionFactory {

	 private static String className = "com.whaty.platform.database.oracle.standard.interaction.OracleInteractionFactory";

	private static Object initLock = new Object();

	private static InteractionFactory factory;

	/** Creates a new instance of BasicEntityFactory */
	public InteractionFactory() {
	}

	/**
	 * ����InteractionFactoryʵ��
	 * 
	 * @return InteractionFactoryʵ��
	 */
	public static InteractionFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					// Note, the software license expressely forbids
					// tampering with this check.
					// LicenseManager.validateLicense("Jive Forums Basic",
					// "2.0");

					String classNameProp = GlobalProperties
							.getBasicEntityClass("InteractionFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						factory = (InteractionFactory) c.newInstance();
					} catch (Exception e) {
						System.err
								.println("Failed to load InteractionFactory class "
										+ className
										+ ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}
	
	public abstract InteractionUserPriv getInteractionUserPriv(String id);

	public abstract InteractionManage creatInteractionManage(
			InteractionUserPriv interactionUserPriv);

}
