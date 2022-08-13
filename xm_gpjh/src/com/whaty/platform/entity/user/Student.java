/*
 * Student.java
 *
 * Created on 2004��9��30��, ����10:56
 */

package com.whaty.platform.entity.user;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.util.RedundanceData;

/**
 * Student�࣬����ѧ���û�
 * 
 * @author Ligang
 */
public abstract class Student extends EntityUser implements
		com.whaty.platform.Items {

	private HumanNormalInfo normalInfo = null;

	private StudentEduInfo studentInfo = null;

	private HumanPlatformInfo platformInfo = null;

	private RedundanceData redundace = null;
	
	private RecruitStudent recruitStudent = null;

	/** Creates a new instance of Student */
	public Student() {
		this.setType(EntityUserType.STUDENT);
	}

	public HumanNormalInfo getNormalInfo() {
		return normalInfo;
	}

	public void setNormalInfo(HumanNormalInfo normalInfo) {
		this.normalInfo = normalInfo;
	}

	public HumanPlatformInfo getPlatformInfo() {
		return platformInfo;
	}

	public void setPlatformInfo(HumanPlatformInfo platformInfo) {
		this.platformInfo = platformInfo;
	}

	public StudentEduInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentEduInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

	public abstract Student getSimpleInfo(String student_id);

	public RedundanceData getRedundace() {
		return redundace;
	}

	public void setRedundace(RedundanceData redundace) {
		this.redundace = redundace;
	}

	public abstract int uploadImage(String card_no, String filename)
			throws PlatformException;

	public RecruitStudent getRecruitStudent() {
		return recruitStudent;
	}

	public void setRecruitStudent(RecruitStudent recruitStudent) {
		this.recruitStudent = recruitStudent;
	}

}
