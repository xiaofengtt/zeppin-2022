/*
 * UserFactory.java
 *
 * Created on 2004��11��25��, ����12:03
 */

package com.whaty.platform.standard.aicc.operation;
import com.whaty.platform.GlobalProperties;
/**
 * ƽ̨��ڵĳ��󹤳�
 * @author  chenjian
 */
public abstract class AiccFactory {
    
    private static String className = "com.whaty.platform.database.oracle.standard.standard.aicc.operation.OracleAiccFactory";
    private static Object initLock = new Object();
    private static AiccFactory factory;
    /** Creates a new instance of UserFactory */
    public AiccFactory() {
    }
    
   
    
    /**
     * ����PlatformFactoryʵ��
     * @return PlatformFactoryʵ��
     */
    public static AiccFactory getInstance() {
       if (factory == null) {
            synchronized(initLock) {
                if (factory == null) {
                    // Note, the software license expressely forbids
                    // tampering with this check.
                    //LicenseManager.validateLicense("Jive Forums Basic", "2.0");

                    String classNameProp =
                        GlobalProperties.getUserFactoryClass("AiccFactory.className");
                    if (classNameProp != null) {
                        className = classNameProp;
                    }
                    try {
                        //Load the class and create an instance.
                        Class c = Class.forName(className);
                       factory=(AiccFactory)c.newInstance();
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
   
    public abstract AiccDataManage creatAiccDataManage();
    public abstract AiccFileManage creatAiccFileManage();
    public abstract AiccCourseManage creatAiccCourseManage();
    public abstract AiccUserDataManage creatAiccUserDataManage(AiccUserDataManagePriv userPriv);
    public abstract AiccUserDataManage creatAiccUserDataManage();
    public abstract AiccUserDataManagePriv creatAiccUserDataManagePriv();
}
