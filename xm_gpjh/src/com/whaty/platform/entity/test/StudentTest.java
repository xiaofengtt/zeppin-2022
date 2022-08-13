
package com.whaty.platform.entity.test;

import com.whaty.platform.entity.user.Student;

/**
 * @author chenjian
 *
 */
public abstract class StudentTest {
	
	private String id;
	
	private  Student student;
	
	private TestBatch testBatch;
	
	private TestSequence testSequence;
	
	private TestCourse testCourse;
	
	private TestSite testSite;
	
	private TestRoom testRoom;
	
	private TestDesk testDesk;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public TestBatch getTestBatch() {
		return testBatch;
	}

	public void setTestBatch(TestBatch testBatch) {
		this.testBatch = testBatch;
	}

	public TestCourse getTestCourse() {
		return testCourse;
	}

	public void setTestCourse(TestCourse testCourse) {
		this.testCourse = testCourse;
	}

	public TestDesk getTestDesk() {
		return testDesk;
	}

	public void setTestDesk(TestDesk testDesk) {
		this.testDesk = testDesk;
	}

	public TestRoom getTestRoom() {
		return testRoom;
	}

	public void setTestRoom(TestRoom testRoom) {
		this.testRoom = testRoom;
	}

	public TestSequence getTestSequence() {
		return testSequence;
	}

	public void setTestSequence(TestSequence testSequence) {
		this.testSequence = testSequence;
	}

	public TestSite getTestSite() {
		return testSite;
	}

	public void setTestSite(TestSite testSite) {
		this.testSite = testSite;
	}

}
