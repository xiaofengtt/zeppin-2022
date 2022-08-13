package com.whaty.platform.courseware;

import com.whaty.platform.GlobalProperties;
import com.whaty.util.log.Log;





/**
 * ƽ̨ڵĳ󹤳
 * 
 * @author chenjian
 */
public abstract class CoursewareFactory {

		private static String className = "com.whaty.platform.database.oracle.standard.courseware.OracleCoursewareFactory";

		private static Object initLock = new Object();

		private static CoursewareFactory factory;

		/** Creates a new instance of UserFactory */
		public CoursewareFactory() {
		}

		/**
		 * PlatformFactoryʵ
		 * 
		 * @return PlatformFactoryʵ
		 */
		public static CoursewareFactory getInstance() {
			if (factory == null) {
				synchronized (initLock) {
					if (factory == null) {
						String classNameProp = GlobalProperties
								.getUserFactoryClass("CoursewareFactory.className");
						if (classNameProp != null) {
							className = classNameProp;
						}
						try {
							// Load the class and create an instance.
							Class c = Class.forName(className);
							Log.setDebug("new instance of factory");
							factory = (CoursewareFactory) c.newInstance();
						} catch (Exception e) {
							System.err
									.println("Failed to load CoursewareFactory class "
											+ className
											+ ".system function normally.");
							
							return null;
						}
					}
				}
			}
			return factory;
		}

		
		public abstract CoursewareManage creatCoursewareManage(
				CoursewareManagerPriv amanagerpriv);

		public abstract CoursewareManagerPriv getCoursewareManagerPriv(java.lang.String id);
		
		public abstract FileManagerPriv getFileManagerPriv();
		
		public abstract FileManagerPriv getFileManagerPriv(String id);
}
