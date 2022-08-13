/**
 * 
 */
package com.whaty.platform.training.activity;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author wq
 *
 */
public abstract class TrainingSelectClassActivity {

	/**为学生申请培训班，其中Map的key为class，value为申请该培训班的学生列表
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void selectClasses(Map studentClasses)  throws PlatformException;
	
	/**退出培训班，其中Map的key为class，value为申请该培训班的学生列表
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unSelectClasses(Map studentClasses)  throws PlatformException;
	
	
	/**审核
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void checkSelectClasses(Map studentClasses)  throws PlatformException;
	
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unCheckSelectClasses(Map studentClasses)  throws PlatformException;
	
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unSelectClasses(List studentClasses)  throws PlatformException;
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void checkSelectClasses(List studentClasses)  throws PlatformException;
}
