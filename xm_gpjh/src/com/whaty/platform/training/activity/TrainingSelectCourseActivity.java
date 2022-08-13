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

	/**为学生选课，其中Map的key为course，value为选该课的学生列表
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void selectCourses(Map studentCourses)  throws PlatformException;
	
	/**退课，其中Map的key为course，value为选该课的学生列表
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void unSelectCourses(Map studentCourses)  throws PlatformException;
	
	/**审核课程 ，其中Map的key为course，value为选该课的学生列表
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void checkSelectCourses(Map studentCourses)  throws PlatformException;
	
	/**取消审核课程 ，其中Map的key为course，value为选该课的学生列表
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void unCheckSelectCourses(Map studentCourses)  throws PlatformException;
	
	
	/**为学生退选，studentCourses里为选课ID列表
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void unSelectCourses(List studentCourses)  throws PlatformException;
	
	/**为学生审核，studentCourses里为选课ID列表
	 * @param studentCourses
	 * @throws PlatformException
	 */
	public abstract void checkSelectCourses(List studentCourses)  throws PlatformException;
}
