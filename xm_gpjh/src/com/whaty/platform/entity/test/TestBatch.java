/**
 * 
 */
package com.whaty.platform.entity.test;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**该类描述了考试批次,每次考试必定属于某个批次
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
	
	
	/**该方法得到该批次下的考试课程
	 * @return返回TestCourse的列表
	 * @throws PlatformException
	 */
	public abstract List getCourses() throws PlatformException;
	
	/**该方法设置该批次下的考试课程
	 * @return返回TestCourse的列表
	 * @throws PlatformException
	 */
	public abstract List setCourses() throws PlatformException;
	
	/**该方法得到该批次下某个考点的考试课程
	 * @return返回TestCourse的列表
	 * @throws PlatformException
	 */
	public abstract List getCourses(TestSite site) throws PlatformException;
	
	/**该方法设置该批次下某个考点的考试课程
	 * @return返回TestCourse的列表
	 * @throws PlatformException
	 */
	public abstract List setCourses(TestSite site) throws PlatformException;
}
