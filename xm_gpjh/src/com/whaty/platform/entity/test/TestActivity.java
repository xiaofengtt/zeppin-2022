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
	
	/**安排考场，确定学生，考点、考场、课程、场次和座位号
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
