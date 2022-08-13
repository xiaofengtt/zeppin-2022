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

	/**Ϊѧ��������ѵ�࣬����Map��keyΪclass��valueΪ�������ѵ���ѧ���б�
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void selectClasses(Map studentClasses)  throws PlatformException;
	
	/**�˳���ѵ�࣬����Map��keyΪclass��valueΪ�������ѵ���ѧ���б�
	 * @param studentClasses
	 * @throws PlatformException
	 */
	public abstract void unSelectClasses(Map studentClasses)  throws PlatformException;
	
	
	/**���
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
