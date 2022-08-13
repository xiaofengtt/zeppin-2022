/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * ������������ѧ��ѧλ������صĹ��
 * 
 * @author chenjian
 * 
 */
public abstract class DegreeActivity {

	private List studentList;

	public List getStudentList() {
		return studentList;
	}

	public void setStudentList(List studentList) {
		this.studentList = studentList;
	}

	/**
	 * ��studentList�е�ѧ�����ѧλ�ʸ��趨�����ط�������ѧ���б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List checkDegree(List studentList, HashMap condition)
			throws PlatformException;

	/**
	 * �÷�����major_idרҵ��grade_id�꼶��edutype_id����µ�ѧ��
	 * ���studentList,����studentList����checkDegree��
	 * �Է��ص�studentList�е�ѧ����Ϊ���ѧλ�����ѧ��,����degree_status״̬��Ϊ1
	 * 
	 * @throws PlatformException
	 */
	public abstract int setDegreeStatus(String major_id, String grade_id,
			String edutype_id) throws PlatformException;

	/**
	 * �÷�����studentList�е�ѧ����Ϊѧλѧ��
	 * 
	 * @throws PlatformException
	 */
	public abstract int setDegreed(List studentList, String operatorId,
			String operatorName) throws PlatformException;

	public abstract int cancleDegreed(List studentList)
			throws PlatformException;

	public abstract int setDegreeScore(List idList) throws PlatformException;

	public abstract int setDegreeRelease(List idList, List releaseList)
			throws PlatformException;
}
