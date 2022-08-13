package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;

/**
 * ����ĳһ��ҵ�������µĲ��רҵ��Ϣ.
 * 
 * @author Administrator
 * 
 */
public abstract class PiciLimit implements Items {
	private String id;

	private Pici piCi;

	private Major major;

	private EduType eduType;

	private Grade grade;

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Pici getPiCi() {
		return piCi;
	}

	public void setPiCi(Pici piCi) {
		this.piCi = piCi;
	}
   //********************************************
	public abstract int IsExsit(String batchId) throws PlatformException ;
	
	public abstract int IsExistStudent(String id) throws PlatformException  ;
		 
}
