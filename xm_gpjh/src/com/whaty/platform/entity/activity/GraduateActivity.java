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
	 * �÷����ж�studentList�е�ѧ���Ƿ���б�ҵ�ʸ񣬷��ؾ��б�ҵ�ʸ��ѧ���б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List checkGraduate(String graduateType)
			throws PlatformException;

	/**
	 * ��studentList�е�ѧ�����б�ҵ�ʸ��趨�����ط���������ѧ���б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List checkGraduate(List studentList, HashMap condition)
			throws PlatformException;

	/**
	 * �÷�����major_idרҵ��grade_id�꼶��edutype_id����µ�ѧ����
	 * ����studentList,����studentList����checkGraduate��
	 * �Է��ص�studentList�е�ѧ����Ϊ���ϱ�ҵ������ѧ��,����graduate_status״̬��Ϊ1
	 * 
	 * @throws PlatformException
	 */
	public abstract int setGraduateStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException;

	/**
	 * �÷�����studentList�е�ѧ����Ϊ��ҵѧ��
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
	 * �÷�����studentList�е�ѧ����Ϊ��ҵѧ��
	 * 
	 * @throws PlatformException
	 */
	public abstract void setGraduated(String graduateType)
			throws PlatformException;

	/**
	 * ����ѱ�ҵ��ѧ���б�
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
	 * ����ѱ�ҵ��ѧ����
	 * 
	 * @param searchproperty
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getGraduateStudentsNum(List searchproperty)
			throws PlatformException;
}
