/**
 * 
 */
package com.whaty.platform.sso;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.Exception.PlatformException;
//import com.whaty.platform.rss.RssFactory;

/**
 * @author chenjian
 *
 */
public abstract class SsoFactory {

	private static String className = "com.whaty.platform.database.oracle.standard.sso.OracleSsoFactory";
    private static Object initLock = new Object();
    private static SsoFactory factory;
    /** Creates a new instance of UserFactory */
    public SsoFactory() {
    }
    
   
    
    /**
     * ����PlatformFactoryʵ��
     * @return PlatformFactoryʵ��
     */
    public static SsoFactory getInstance() {
       if (factory == null) {
            synchronized(initLock) {
                if (factory == null) {
                    String classNameProp =
                        GlobalProperties.getUserFactoryClass("SsoFactory.className");
                    if (classNameProp != null) {
                        className = classNameProp;
                    }
                    try {
                        //Load the class and create an instance.
                        Class c = Class.forName(className);
                       factory=(SsoFactory)c.newInstance();
                    }
                    catch (Exception e) {
                        System.err.println("Failed to load SsoFactory class "
                        + className + ".system function normally.");
                        
                        return null;
                    }
                }
            }
        }
       return factory;
    }
    
    public abstract SsoManage creatSsoManage() throws PlatformException;;
    
    public abstract SsoUserPriv creatSsoUserPriv(String userId) throws PlatformException;
    
    public abstract SsoManagerPriv creatSsoManagerPriv(String ManagerId) throws PlatformException;
    
    public abstract SsoManagerPriv creatSsoManagerPriv() throws PlatformException;
    
    
    public abstract SsoManage creatSsoManage(SsoManagerPriv ssoManagerPriv) throws PlatformException;
    
    public abstract SsoUserOperation creatSsoUserOperation() throws PlatformException;;
    
    public abstract SsoUserOperation creatSsoUserOperation(SsoUserPriv ssoUserPriv) throws PlatformException;
}
