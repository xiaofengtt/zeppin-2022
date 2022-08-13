/**
 * 
 */
package com.whaty.platform.resource;

import com.whaty.platform.GlobalProperties;

/**
 * @author chenjian
 *
 */
public abstract class ResourceFactory {
	
	private static String className = "com.whaty.platform.database.oracle.standard.resource.OracleResourceFactory";
    private static Object initLock = new Object();
    private static ResourceFactory factory;
    /** Creates a new instance of UserFactory */
    public ResourceFactory() {
    }
    
   
    
    /**
     * ����PlatformFactoryʵ��
     * @return PlatformFactoryʵ��
     */
    public static ResourceFactory getInstance() {
       if (factory == null) {
            synchronized(initLock) {
                if (factory == null) {
                    String classNameProp =
                        GlobalProperties.getUserFactoryClass("ResourceFactory.className");
                    if (classNameProp != null) {
                        className = classNameProp;
                    }
                    try {
                        //Load the class and create an instance.
                        Class c = Class.forName(className);
                       factory=(ResourceFactory)c.newInstance();
                    }
                    catch (Exception e) {
                        System.err.println("Failed to load PlatformFactory class "
                        + className + ".system function normally.");
                        
                        return null;
                    }
                }
            }
        }
       return factory;
    }
    
    public abstract BasicResourceManage creatBasicResourceManage();
}
