package com.whaty.platform.entity.graduatedesign;

import com.whaty.platform.Items;
import com.whaty.platform.entity.recruit.TimeDef;

/**
 * �������ҵ�����ε�����
 * 
 * @author zhangliang
 * 
 */
public abstract class Pici implements Items {

	private String id; // ��ҵ���ID

	private String name; // ��ҵ������

	private TimeDef graduateDesignTime; // ��ҵ���ʱ��

	private TimeDef subjectTime; // ѡ��ʱ��

	private TimeDef openSubTime; // ����ʱ��

	private TimeDef subSubTime; // �ύ����ʱ��
	
	private TimeDef writeDiscourseTiem ;//׫д����ʱ��

	private TimeDef reJoinTime; // �������ʱ��

	private TimeDef reportGradeTime; // �ɼ��ϱ�ʱ��
	
	private TimeDef freeApplyTime;//��������ʱ��;

	private String status; // ���õ�ǰ���1.Ϊ���0Ϊδ����
	
	private boolean active = false;
	
	private String semester_id ;
	
	private String semester_name ;

	public String getSemester_name() {
		return semester_name;
	}

	public void setSemester_name(String semester_name) {
		this.semester_name = semester_name;
	}

	public String getSemester_id() {
		return semester_id;
	}

	public void setSemester_id(String semester_id) {
		this.semester_id = semester_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public TimeDef getGraduateDesignTime() {
		return graduateDesignTime;
	}

	public void setGraduateDesignTime(TimeDef graduateDesignTime) {
		this.graduateDesignTime = graduateDesignTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TimeDef getOpenSubTime() {
		return openSubTime;
	}

	public void setOpenSubTime(TimeDef openSubTime) {
		this.openSubTime = openSubTime;
	}

	public TimeDef getReJoinTime() {
		return reJoinTime;
	}

	public void setReJoinTime(TimeDef reJoinTime) {
		this.reJoinTime = reJoinTime;
	}

	public TimeDef getReportGradeTime() {
		return reportGradeTime;
	}

	public void setReportGradeTime(TimeDef reportGradeTime) {
		this.reportGradeTime = reportGradeTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TimeDef getSubjectTime() {
		return subjectTime;
	}

	public void setSubjectTime(TimeDef subjectTime) {
		this.subjectTime = subjectTime;
	}

	public TimeDef getSubSubTime() {
		return subSubTime;
	}

	public void setSubSubTime(TimeDef subSubTime) {
		this.subSubTime = subSubTime;
	}

	// ****************************************************
	public abstract int setActive();

	public abstract int cancelActive();
	
	public abstract int graduateDesignTimeSect();
	
	public abstract int subjectResearchTimeSect();
	
	public abstract int regBgCourseTimeSect();
	
	public abstract int discourseSumbitTimeSect();
	
	public abstract int rejoinRequesTimeSect();
	
	public abstract int reportGraduTimeSect();
	
	public abstract int witeDiscourseTimeSect();

	public TimeDef getWriteDiscourseTiem() {
		return writeDiscourseTiem;
	}

	public void setWriteDiscourseTiem(TimeDef writeDiscourseTiem) {
		this.writeDiscourseTiem = writeDiscourseTiem;
	}

	public TimeDef getFreeApplyTime() {
		return freeApplyTime;
	}

	public void setFreeApplyTime(TimeDef freeApplyTime) {
		this.freeApplyTime = freeApplyTime;
	}
	
}
