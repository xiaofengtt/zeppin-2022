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
 * @author Administrator
 */
public abstract class Teacher extends EntityUser implements com.whaty.platform.Items {
    
	private HumanNormalInfo normalInfo=null;
    
    private TeacherEduInfo teachereduInfo=null;
    
    private HumanPlatformInfo platformInfo=null;
    
    private RedundanceData redundace=null;
    
	
    public Teacher() {
		this.setType(EntityUserType.TEACHER);
	}

	/**
     * ��ý�ʦ�ν̵����пγ��б�
     * @return �γ��б�
     */
    public abstract List getCourses() ;
   
    /**
     * @return
     */
    public abstract  List getStudentClasses() ;

    
	

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
    
    
}
