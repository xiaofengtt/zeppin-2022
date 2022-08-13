package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class StatusChangeActivity {

	/**
	 * ����ѧ����ѧ���춯
	 */
	private List studentList;

	public List getStudentList() {
		return studentList;
	}

	public void setStudentList(List studentList) {
		this.studentList = studentList;
	}

	public abstract int changeMajorStatus(String newmajor_id,
			String newedu_type_id, String newgrade_id, String newsite_id)
			throws PlatformException;

	public abstract int changeStatus(String statusChangeType)
			throws PlatformException;

	public abstract int cancelChangeStatus(String statusChangeType)
			throws PlatformException;

	/**
	 * ��ѧ
	 * 
	 * @param rid
	 *            ��ѧ����ѧ�ļ�¼ID
	 * @param statusChangeType
	 *            ABORTΪ��ѧ��SUSPENDΪ��ѧ
	 * @return
	 * @throws PlatformException
	 */
	public abstract int cancelChangeStatus(String rid, String statusChangeType)
			throws PlatformException;

	public abstract int getStatusChangeStudentsNum(String statusChangeType)
			throws PlatformException;

	public abstract List getStatusChangeStudents(String statusChangeType)
			throws PlatformException;

	public abstract List getStatusChangeStudents(Page page,
			String statusChangeType) throws PlatformException;

	public abstract int getChangeMajorStudentsNum() throws PlatformException;

	public abstract List getChangeMajorStudents() throws PlatformException;

	public abstract List getChangeMajorStudents(Page page)
			throws PlatformException;

	public abstract int getStatusChangeStudentRecodsNum(List searchProperty,
			String statusChangeType) throws PlatformException;

	public abstract int abortRecordsNum(List searchProperty);

	public abstract int suspendRecordsNum(List searchProperty);

	public abstract List getStatusChangeStudentRecods(Page page,
			List searchProperty, List orderProperty, String statusChangeType)
			throws PlatformException;

	public abstract List abortRecordsList(Page page, List searchProperty,
			List orderProperty);

	public abstract List suspendRecordsList(Page page, List searchProperty,
			List orderProperty);

	public abstract int getChangeMajorStudentsNum(List searchProperty)
			throws PlatformException;

	public abstract List getChangeMajorStudents(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

}
