/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author Administrator
 * 
 */
public abstract class GraduateActivity {

	private List studentList;

	public List getStudentList() {
		return studentList;
	}

	public void setStudentList(List studentList) {
		this.studentList = studentList;
	}

	/**
	 * 该方法判断studentList中的学生是否具有毕业资格，返回具有毕业资格的学生列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List checkGraduate(String graduateType)
			throws PlatformException;

	/**
	 * 对studentList中的学生进行毕业资格设定，返回符合条件的学生列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List checkGraduate(List studentList, HashMap condition)
			throws PlatformException;

	/**
	 * 该方法将major_id专业，grade_id年级，edutype_id层次下的学生，
	 * 生成studentList,并将studentList传入checkGraduate，
	 * 对返回的studentList中的学生设为符合毕业条件的学生,将其graduate_status状态设为1
	 * 
	 * @throws PlatformException
	 */
	public abstract int setGraduateStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException;

	/**
	 * 该方法将studentList中的学生设为毕业学生
	 * 
	 * @throws PlatformException
	 */
	public abstract int setGraduated(List studentList, String operatorId,
			String operatorName) throws PlatformException;

	public abstract int cancleGraduated(List studentList)
			throws PlatformException;

	public abstract int setGraduateNo(List idList) throws PlatformException;

	/**
	 * 
	 * 
	 * @throws PlatformException
	 */
	public abstract void setGraduateStatus(String graduateType)
			throws PlatformException;

	/**
	 * 该方法将studentList中的学生设为毕业学生
	 * 
	 * @throws PlatformException
	 */
	public abstract void setGraduated(String graduateType)
			throws PlatformException;

	/**
	 * 获得已毕业的学生列表
	 * 
	 * @param site_id
	 * @param grade_id
	 * @param major_id
	 * @param edu_type_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateStudents(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	/**
	 * 获得已毕业的学生数
	 * 
	 * @param searchproperty
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getGraduateStudentsNum(List searchproperty)
			throws PlatformException;
}
