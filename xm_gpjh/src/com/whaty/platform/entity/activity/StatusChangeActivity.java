package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class StatusChangeActivity {

	/**
	 * 描述学生的学籍异动
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
	 * 复学
	 * 
	 * @param rid
	 *            退学或休学的记录ID
	 * @param statusChangeType
	 *            ABORT为退学，SUSPEND为休学
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
