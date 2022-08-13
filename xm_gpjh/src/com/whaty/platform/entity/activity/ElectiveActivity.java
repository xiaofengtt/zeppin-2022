/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author Administrator
 * 
 */
public abstract class ElectiveActivity {

	/**
	 * 该属性用map描述了学生与课程的信息 该map以student为键，该学生选择的教学班Id列表为值
	 * 
	 */
	private Map studentTeachClassMap;

	public Map getStudentTeachClassMap() {
		return studentTeachClassMap;
	}

	public void setStudentTeachClassMap(Map studentTeachClassMap) {
		this.studentTeachClassMap = studentTeachClassMap;
	}

	/**
	 * 该方法为studentTeachClassMap中的学生选中对应的教学班列表
	 * 
	 * @throws PlatformException
	 */
	public abstract void selectTeachClass() throws PlatformException;

	/**
	 * 该方法为studentTeachClassMap中的学生退选对应的教学班列表
	 * 
	 * @return TODO
	 * @throws PlatformException
	 */
	public abstract int unSelectTeachClass() throws PlatformException;

	/**
	 * 该方法确认学生预选的教学班列表
	 * 
	 * @throws PlatformException
	 */
	public abstract void checkSelect(List electiveList)
			throws PlatformException;

	/**
	 * 该方法取消确认学生预选的教学班列表
	 * 
	 * @throws PlatformException
	 */
	public abstract void unCheckSelect(List electiveList)
			throws PlatformException;

	public abstract void electiveCoursesByUserId(String[] idSet,
			String semester_id, String student_id);

	public abstract void electiveCourses(String regNo);

	public abstract int checkElectiveByFee(String regNo, String ssoUserId);

	/**
	 * 按照教学站、专业、年级、层次批量选课,同时进行选课确认并扣费
	 * 如果要退某门课,可以将selectIds置成空,allIds为要退课程的teachclass_id
	 * @param idSet
	 * @param semester_id
	 * @param site_id
	 * @param edu_type_id
	 * @param major_id
	 * @param grade_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String site_id, String edu_type_id,
			String major_id, String grade_id) throws PlatformException;

	/**
	 * 给单个学生选课,同时进行选课确认并扣费
	 * 如果要退某门课,可以将selectIds置成空,allIds为要退课程的teachclass_id
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * 给单个学生进行选课,未进行选课确认故不扣费
	 * 如果要退某门课,可以将selectIds置成空,allIds为要退课程的teachclass_id
	 * @param selectIds
	 * @param allIds
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithoutFee(String[] selectIds,
			String[] allIds, String semester_id, String student_id)
			throws PlatformException;

	/**
	 * 确认学生选课并扣费
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract int confirmElective(String[] selectIds,	String semester_id, String student_id) throws PlatformException;
	/**
	 * 取消确认学生选课并退费
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract int unConfirmElective(String[] selectIds, String semester_id, String student_id) throws PlatformException;
}
