/**
 * 
 */
package com.whaty.platform.entity.test;

import com.whaty.platform.entity.user.Student;

/**该类包装了平台的Student对象
 * @author chenjian
 *
 */
public abstract class TestStudent {

	private Student student;
	private String studentId;
	private String loginId;
	private String studentName;
	

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		this.setStudentId(student.getId());
		this.setLoginId(student.getLoginId());
		this.setStudentName(student.getName());

	}
	
	

}
