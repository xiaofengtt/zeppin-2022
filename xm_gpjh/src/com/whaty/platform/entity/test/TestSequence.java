/**
 * 
 */
package com.whaty.platform.entity.test;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**����������ĳ�����µĳ�����Ϣ
 * @author chenjian
 *
 */
public abstract class TestSequence {

	private String id;
	
	private String title;
	
	private String note;
	
	private TestBatch testBatch;
	
	private TestTimeDef testTime;
	
	/**�����ñ����ο��ԵĿγ�
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTestCourse() throws PlatformException;
	
	/**������ӱ����ο��ԵĿγ�
	 * @return
	 * @throws PlatformException
	 */
	public abstract void addTestCourse(TestCourse testCourse) throws PlatformException;

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

	public TestBatch getTestBatch() {
		return testBatch;
	}

	public void setTestBatch(TestBatch testBatch) {
		this.testBatch = testBatch;
	}

	public TestTimeDef getTestTime() {
		return testTime;
	}

	public void setTestTime(TestTimeDef testTime) {
		this.testTime = testTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
