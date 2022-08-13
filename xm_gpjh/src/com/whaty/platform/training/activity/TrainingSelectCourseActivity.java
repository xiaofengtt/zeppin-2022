/**
 * 
 */
package com.whaty.platform.training.activity;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public abstract class TrainingSelectCourseActivity {

	/**Ϊѧ��ѡ�Σ�����Map��keyΪcourse��valueΪѡ�ÿε�ѧ���б�
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void selectCourses(Map studentCourses)  throws PlatformException;
	
	/**�˿Σ�����Map��keyΪcourse��valueΪѡ�ÿε�ѧ���б�
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void unSelectCourses(Map studentCourses)  throws PlatformException;
	
	/**��˿γ� ������Map��keyΪcourse��valueΪѡ�ÿε�ѧ���б�
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void checkSelectCourses(Map studentCourses)  throws PlatformException;
	
	/**ȡ����˿γ� ������Map��keyΪcourse��valueΪѡ�ÿε�ѧ���б�
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void unCheckSelectCourses(Map studentCourses)  throws PlatformException;
	
	
	/**Ϊѧ����ѡ��studentCourses��Ϊѡ��ID�б�
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void unSelectCourses(List studentCourses)  throws PlatformException;
	
	/**Ϊѧ����ˣ�studentCourses��Ϊѡ��ID�б�
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void checkSelectCourses(List studentCourses)  throws PlatformException;
}
