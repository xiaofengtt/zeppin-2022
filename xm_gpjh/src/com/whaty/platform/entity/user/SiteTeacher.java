/*
 * Teacher.java
 *
 * Created on 2004��9��30��, ����11:08
 */

package com.whaty.platform.entity.user;

import java.util.List;

import com.whaty.platform.util.RedundanceData;

/**
 * �����ʦ����
 * 
 * @author Administrator
 */
public abstract class SiteTeacher extends EntityUser implements
		com.whaty.platform.Items {

	private String siteId = null;
	
	private String confirmStatus = "0";

	private String confirmNote = "";
	
	private HumanNormalInfo normalInfo = null;

	private TeacherEduInfo teachereduInfo = null;

	private HumanPlatformInfo platformInfo = null;

	private RedundanceData redundace = null;
    
	public SiteTeacher() {
		this.setType(EntityUserType.SITETEACHER);
	}

	/**
	 * ��ý�ʦ�ν̵����пγ��б�
	 * 
	 * @return �γ��б�
	 */
	public abstract List getCourses();

	/**
	 * @return
	 */
	public abstract List getStudentClasses();

	public abstract List getTeachCourse();
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

	public RedundanceData getRedundace() {
		return redundace;
	}

	public void setRedundace(RedundanceData redundace) {
		this.redundace = redundace;
	}

	public TeacherEduInfo getTeacherInfo() {
		return teachereduInfo;
	}

	public void setTeacherInfo(TeacherEduInfo teachereduInfo) {
		this.teachereduInfo = teachereduInfo;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public TeacherEduInfo getTeachereduInfo() {
		return teachereduInfo;
	}

	public void setTeachereduInfo(TeacherEduInfo teachereduInfo) {
		this.teachereduInfo = teachereduInfo;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getConfirmNote() {
		return confirmNote;
	}

	public void setConfirmNote(String confirmNote) {
		this.confirmNote = confirmNote;
	}

}
