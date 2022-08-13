package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Grade;

public abstract class RecruitBatch implements Items {

	private String id = "";

	private String title = "";

	private TimeDef planTime; // 上报招生计划的时间段

	private TimeDef signTime; // 报名的时间段

	private boolean isSignTime;// 是否在报名的时间段内

	private TimeDef examTime; // 考试的时间段

	private Grade grade;

	private String note = "";

	private String creatTime = "";

	private boolean active = false;

	private String simpleNote = "";
	
	private String reg_startdate ="";   //学生开始注册时间
	
	private String reg_enddate = "";   //学生结束时间

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
	 * 设置本招生批次为激活
	 * 
	 * @return 0为不成功；1为成功
	 */
	public abstract int setActive();

	/**
	 * 设置本招生批次为不激活
	 * 
	 * @return 0为不成功;1为成功
	 */
	public abstract int cancelActive();

	/**
	 * 为本招生批次添加招生计划
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void addPlans(List planList) throws PlatformException;

	/**
	 * 确认分站上报的本招生批次的招生计划
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void confirmPlans(List planList) throws PlatformException;

	/**
	 * 取消确认分站上报的本招生批次的招生计划
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void unConfirmPlans(List planList) throws PlatformException;

	/**
	 * 删除本招生批次的招生计划
	 * 
	 * @param planList
	 * @throws PlatformException
	 */
	public abstract void deletePlans(List planList) throws PlatformException;

	/**
	 * 为本招生批次添加学生
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addStudents(List studentList) throws PlatformException;

	/**
	 * 删除本招生批次的学生
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void deleteStudents(List studentList)
			throws PlatformException;

	/**
	 * 为本招生批次添加考试计划
	 * 
	 * @param testBatchList
	 * @throws PlatformException
	 */
	public abstract void addTestBatchs(List testBatchList)
			throws PlatformException;

	/**
	 * 删除本招生批次的考试计划
	 * 
	 * @param testBatchList
	 * @throws PlatformException
	 */
	public abstract void deleteTestBatchs(List testBatchList)
			throws PlatformException;

	/**
	 * 该招生批次下面是否有招生计划
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasPlans();

	/**
	 * 该招生批次下面是否有学生
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasStudents();

	/**
	 * 该招生批次下面是否有考试计划
	 * 
	 * @return 0为没有；大于0则有
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
