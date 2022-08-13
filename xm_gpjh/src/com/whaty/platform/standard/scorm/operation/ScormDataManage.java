/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

import com.whaty.platform.standard.scorm.datamodels.SCODataManager;

/**
 * @author Administrator
 *
 */
public abstract class ScormDataManage {
	
	public abstract void putToDB(SCODataManager inSCOData,String userId,String CourseId,String scoID);
	public abstract SCODataManager getFromDB(String userId,String CourseId,String scoID);
}
