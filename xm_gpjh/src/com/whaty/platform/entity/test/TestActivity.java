/**
 * 
 */
package com.whaty.platform.entity.test;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *2006-4-25
 */
public abstract class TestActivity {
	
	private TestBatch testBatch;
	
	/**���ſ�����ȷ��ѧ�������㡢�������γ̡����κ���λ��
	 * @throws PlatformException
	 */
	public abstract void arrangeTest() throws PlatformException;
	
	public abstract void arrangeTest(TestSite testSite) throws PlatformException;
	
	public abstract void arrangeTest(TestSequence testSequence) throws PlatformException;
	
	public abstract void arrangeTest(TestCourse testCourse) throws PlatformException;
	
	public abstract void arrangeTest(TestCourse testCourse,TestSite testSite) throws PlatformException;
	
	public abstract void arrangeTest(TestSequence testSequence,TestSite testSite) throws PlatformException;

	public TestBatch getTestBatch() {
		return testBatch;
	}

	public void setTestBatch(TestBatch testBatch) {
		this.testBatch = testBatch;
	}

}
