/**
 * 
 */
package com.whaty.platform.training;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.training.user.TrainingClassManagerPriv;
import com.whaty.platform.training.user.TrainingManagerPriv;
import com.whaty.platform.training.user.TrainingStudentPriv;

/**
 * @author chenjian
 *
 */
public abstract class TrainingFactory {
	
	private static String className = "com.whaty.platform.database.oracle.standard.training.OracleTrainingFactory";
    private static Object initLock = new Object();
    private static TrainingFactory factory;
    /** Creates a new instance of UserFactory */
    public TrainingFactory() {
    }
    
   
    
    /**
     * PlatformFactoryʵ
     * @return PlatformFactoryʵ
     */
    public static TrainingFactory getInstance() {
       if (factory == null) {
            synchronized(initLock) {
                if (factory == null) {
                    String classNameProp =
                        GlobalProperties.getUserFactoryClass("TrainingFactory.className");
                    if (classNameProp != null) {
                        className = classNameProp;
                    }
                    try {
                        //Load the class and create an instance.
                        Class c = Class.forName(className);
                       factory=(TrainingFactory)c.newInstance();
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
    
    public abstract TrainingPubManage creatTrainingPubManage();
    
    public abstract TrainingManage creatTrainingManage(TrainingManagerPriv managerpriv);
    
    public abstract TrainingClassManage creatTrainingClassManage(TrainingClassManagerPriv classmanagerpriv);
    
    public abstract TrainingStudentOperationManage creatTrainingUserOperationManage(TrainingStudentPriv trainingStudentpriv);
   
    public abstract TrainingManagerPriv creatTrainingManagerPriv(java.lang.String id);
    
    public abstract TrainingStudentPriv creatTrainingStudentPriv(java.lang.String id);
    
    public abstract TrainingClassManagerPriv creatTrainingClassManagerPriv(java.lang.String id);

}
