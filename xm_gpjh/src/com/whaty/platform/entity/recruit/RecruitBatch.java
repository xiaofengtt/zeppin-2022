package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Grade;

public abstract class RecruitBatch implements Items {

	private String id = "";

	private String title = "";

	private TimeDef planTime; // �ϱ������ƻ���ʱ���

	private TimeDef signTime; // ������ʱ���

	private boolean isSignTime;// �Ƿ��ڱ�����ʱ�����

	private TimeDef examTime; // ���Ե�ʱ���

	private Grade grade;

	private String note = "";

	private String creatTime = "";

	private boolean active = false;

	private String simpleNote = "";
	
	private String reg_startdate ="";   //ѧ����ʼע��ʱ��
	
	private String reg_enddate = "";   //ѧ������ʱ��

	public String getSimpleNote() {
		return simpleNote;
	}

	public void setSimpleNote(String simpleNote) {
		this.simpleNote = simpleNote;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TimeDef getExamTime() {
		return examTime;
	}

	public void setExamTime(TimeDef examTime) {
		this.examTime = examTime;
	}

	public TimeDef getPlanTime() {
		return planTime;
	}

	public void setPlanTime(TimeDef planTime) {
		this.planTime = planTime;
	}

	public TimeDef getSignTime() {
		return signTime;
	}

	public void setSignTime(TimeDef signTime) {
		this.signTime = signTime;
	}

	public   boolean isSignTime() {
		return isSignTime;
	}

	public void setSignTime(boolean isSignTime) {
		this.isSignTime = isSignTime;
	}

	/**
	 * ���ñ���������Ϊ����
	 * 
	 * @return 0Ϊ���ɹ���1Ϊ�ɹ�
	 */
	public abstract int setActive();

	/**
	 * ���ñ���������Ϊ������
	 * 
	 * @return 0Ϊ���ɹ�;1Ϊ�ɹ�
	 */
	public abstract int cancelActive();

	/**
	 * Ϊ������������������ƻ�
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void addPlans(List planList) throws PlatformException;

	/**
	 * ȷ�Ϸ�վ�ϱ��ı��������ε������ƻ�
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void confirmPlans(List planList) throws PlatformException;

	/**
	 * ȡ��ȷ�Ϸ�վ�ϱ��ı��������ε������ƻ�
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void unConfirmPlans(List planList) throws PlatformException;

	/**
	 * ɾ�����������ε������ƻ�
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void deletePlans(List planList) throws PlatformException;

	/**
	 * Ϊ�������������ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addStudents(List studentList) throws PlatformException;

	/**
	 * ɾ�����������ε�ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void deleteStudents(List studentList)
			throws PlatformException;

	/**
	 * Ϊ������������ӿ��Լƻ�
	 * 
	 * @param testBatchList
	 * @throws PlatformException
	 */
	public abstract void addTestBatchs(List testBatchList)
			throws PlatformException;

	/**
	 * ɾ�����������εĿ��Լƻ�
	 * 
	 * @param testBatchList
	 * @throws PlatformException
	 */
	public abstract void deleteTestBatchs(List testBatchList)
			throws PlatformException;

	/**
	 * ���������������Ƿ��������ƻ�
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasPlans();

	/**
	 * ���������������Ƿ���ѧ��
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasStudents();

	/**
	 * ���������������Ƿ��п��Լƻ�
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasTestBatchs();

	public String getReg_enddate() {
		return reg_enddate;
	}

	public void setReg_enddate(String reg_enddate) {
		this.reg_enddate = reg_enddate;
	}

	public String getReg_startdate() {
		return reg_startdate;
	}

	public void setReg_startdate(String reg_startdate) {
		this.reg_startdate = reg_startdate;
	}

}
