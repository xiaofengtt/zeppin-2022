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
public abstract class TrainingSelectSkillActivity {
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void selectSkill(Map studentClasses)  throws PlatformException;
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unSelectSkill(Map studentClasses)  throws PlatformException;
	
	
	/**…Û∫À
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void checkSelectSkill(Map studentClasses)  throws PlatformException;
	
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unCheckSelectSkill(Map studentClasses)  throws PlatformException;
	
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unSelectSkill(List studentClasses)  throws PlatformException;
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void checkSelectSkill(List studentClasses)  throws PlatformException;
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void targetSkill(Map studentClasses)  throws PlatformException;
	
	/**
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unTargetSkill(Map studentClasses)  throws PlatformException;
}
