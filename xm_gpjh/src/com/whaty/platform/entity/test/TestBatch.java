/**
 * 
 */
package com.whaty.platform.entity.test;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**���������˿�������,ÿ�ο��Աض�����ĳ������
 * @author chenjian
 *2006-4-25
 */
public abstract class TestBatch {
	
	private String id;
	
	private String title;
	
	private String note;
	
	private String startDate;
	
	private String endDate;
	
	private String status;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	/**�÷����õ��������µĿ��Կγ�
	 * @return����TestCourse���б�
	 * @throws PlatformException
	 */
	public abstract List getCourses() throws PlatformException;
	
	/**�÷������ø������µĿ��Կγ�
	 * @return����TestCourse���б�
	 * @throws PlatformException
	 */
	public abstract List setCourses() throws PlatformException;
	
	/**�÷����õ���������ĳ������Ŀ��Կγ�
	 * @return����TestCourse���б�
	 * @throws PlatformException
	 */
	public abstract List getCourses(TestSite site) throws PlatformException;
	
	/**�÷������ø�������ĳ������Ŀ��Կγ�
	 * @return����TestCourse���б�
	 * @throws PlatformException
	 */
	public abstract List setCourses(TestSite site) throws PlatformException;
}
