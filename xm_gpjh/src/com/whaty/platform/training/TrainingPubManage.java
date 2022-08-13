/**
 * 
 */
package com.whaty.platform.training;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.leaveword.LeaveWordNormalManage;
import com.whaty.platform.training.user.TrainingUser;

/**
 * @author chenjian
 * 
 */
public abstract class TrainingPubManage {

	public abstract TrainingUser getTrainingUser(String userId, String type)
			throws PlatformException;
	
	public abstract LeaveWordNormalManage getLeaveWordNormalManage()
	throws PlatformException;


}
