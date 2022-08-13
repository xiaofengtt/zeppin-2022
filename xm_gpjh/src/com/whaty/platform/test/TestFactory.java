/**
 * 
 */
package com.whaty.platform.test;

import com.whaty.platform.GlobalProperties;

/**
 * @author wq
 *
 */
public abstract class TestFactory {
	 private static String className = "com.whaty.platform.database.oracle.standard.test.OracleTestFactory";

		private static Object initLock = new Object();

		private static TestFactory factory;

		/** Creates a new instance of BasicEntityFactory */
		public TestFactory() {
		}

		/**
		 * InteractionFactoryʵ
		 * 
		 * @return InteractionFactoryʵ
		 */
		public static TestFactory getInstance() {
			if (factory == null) {
				synchronized (initLock) {
					if (factory == null) {
						// Note, the software license expressely forbids
						// tampering with this check.
						// LicenseManager.validateLicense("Jive Forums Basic",
						// "2.0");

						String classNameProp = GlobalProperties
								.getBasicEntityClass("TestFactory.className");
						if (classNameProp != null) {
							className = classNameProp;
						}
						try {
							// Load the class and create an instance.
							Class c = Class.forName(className);
							factory = (TestFactory) c.newInstance();
						} catch (Exception e) {
							System.err
									.println("Failed to load TestFactory class "
											+ className
											+ ".system function normally.");
							
							return null;
						}
					}
				}
			}
			return factory;
		}
		
		public abstract TestPriv getTestPriv();
		
		public abstract TestManage creatTestManage(
				TestPriv testPriv);
		public abstract TestManage creatExpendTestManage(
				TestPriv testPriv);
}
