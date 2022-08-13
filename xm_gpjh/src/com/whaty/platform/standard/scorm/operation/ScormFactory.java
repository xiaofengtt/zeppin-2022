package com.whaty.platform.standard.scorm.operation;


import com.whaty.platform.GlobalProperties;

import com.whaty.platform.standard.scorm.LMSManifestHandler;

public abstract class ScormFactory {
	 	private static String className = "com.whaty.platform.database.oracle.standard.standard.scorm.operation.OracleScormFactory";
	    private static Object initLock = new Object();
	    private static ScormFactory factory;
	    /** Creates a new instance of UserFactory */
	    public ScormFactory() {
	    }
	    
	   
	    
	    /**
	     * 创建PlatformFactory实例
	     * @return PlatformFactory实例
	     */
	    public static ScormFactory getInstance() {
	       if (factory == null) {
	            synchronized(initLock) {
	                if (factory == null) {
	                    // Note, the software license expressely forbids
	                    // tampering with this check.
	                    //LicenseManager.validateLicense("Jive Forums Basic", "2.0");

	                    String classNameProp =
	                        GlobalProperties.getUserFactoryClass("ScormFactory.className");
	                    if (classNameProp != null) {
	                        className = classNameProp;
	                    }
	                    try {
	                        //Load the class and create an instance.
	                        Class c = Class.forName(className);
	                       factory=(ScormFactory)c.newInstance();
	                    }
	                    catch (Exception e) {
	                        System.err.println("Failed to load PlatformFactory class "
	                        + className + ".system function normally.");
	                        e.printStackTrace();
	                        return null;
	                    }
	                }
	            }
	        }
	       return factory;
	    }
	   
	    public abstract ScormManage creatScormManage();
	    
	    public abstract LMSManifestHandler creatLMSManifestHandler();
	    
	    public abstract ScormStudentManage creatScormStudentManage(ScormStudentPriv studentPriv);
	   
	    public abstract ScormStudentPriv creatScormStudentPriv();
}
