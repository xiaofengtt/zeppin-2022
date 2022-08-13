/**
 * 
 */
package com.whaty.platform.training.basic;


import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.standard.aicc.Exception.AiccException;



/**
 * @author chenjian
 *
 */
/**
 * @author chenjian
 *
 */
/**
 * @author chenjian
 *
 */
/**
 * @author chenjian
 *
 */
public abstract class TrainingCourse implements Items{
	
	/**
	 * �γ̱��
	 */
	private String id;
	
	/**
	 * �γ����
	 */
	private String name;
	
	/**
	 * �γ�ѧ��
	 */
	private float credit;
	
	/**
	 * �γ�ѧʱ
	 */
	private float studyTime;
		
	/**
	 * �γ�˵��
	 */
	private String note;
		
	/**
	 * ��ѵ�γ�����
	 */
	private TrainingCourseType trainingCourseType;
	
	/**
	 * �γ�ͨ�����
	 */
	private	TrainingCoursePassCondition  trainingCoursePassCondition;
	
	private String coursewareType;
	
	private String coursewareId;
	
	private String coursewareCode;  //sce add; 课件编号； 20090623
	
	private String scormCourseId;	//scorm课件的id
	
	
	/**����γ̵�״̬�ֶΣ�Ϊ8λ�ַ�'00000000',1����ǣ�0����
	 * ��1λ����Ƿ�ÿγ̿���
	 * ��2λ����Ƿ�ÿγ��ڿγ̳�����ɼ�
	 * ��3λ����Ƿ������ڿγ̳�����ѡ��ÿ�
	 * ......
	 */
	private String status;
	
	
	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
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

	public String getNote() {
		if(note == null || note.length() == 0)
			return "Ŀǰû������";
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(float studyTime) {
		this.studyTime = studyTime;
	}

	public TrainingCourseType getTrainingCourseType() {
		return trainingCourseType;
	}

	public void setTrainingCourseType(TrainingCourseType trainingCourseType) {
		this.trainingCourseType = trainingCourseType;
	}

	public TrainingCoursePassCondition getTrainingCoursePassCondition() {
		return trainingCoursePassCondition;
	}

	public void setTrainingCoursePassCondition(
			TrainingCoursePassCondition trainingCoursePassCondition) {
		this.trainingCoursePassCondition = trainingCoursePassCondition;
	}

	
	public String getCoursewareId() {
		return coursewareId;
	}

	public void setCoursewareId(String coursewareId) {
		this.coursewareId = coursewareId;
	}

	public String getCoursewareType() {
		return coursewareType;
	}

	public void setCoursewareType(String coursewareType) {
		this.coursewareType = coursewareType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**�õ��γ̵Ŀμ�
	 * @return
	 * @throws PlatformException
	 * @throws AiccException 
	 */
	public abstract TrainingCourseware  getTrainingCourseware() throws PlatformException, AiccException;

	/**�жϸÿγ��Ƿ����
	 * @return
	 * @throws PlatformException
	 */
	public abstract  boolean getIsActive() throws PlatformException;

	/**���øÿγ��Ƿ����
	 * @param isActive
	 * @throws PlatformException
	 */
	public abstract void setActive(boolean isActive) throws PlatformException;
	
	/**�жϸÿγ��ڿγ̳������Ƿ�ɼ�
	 * @return
	 * @throws PlatformException
	 */
	public abstract boolean getIsPubView() throws PlatformException;
	
	/**���øÿγ��ڿγ̳������Ƿ�ɼ�
	 * @param isPubView
	 * @return
	 * @throws PlatformException
	 */
	public abstract void setPubView(boolean isPubView)  throws PlatformException;
	
	/**�жϸÿγ��ڿγ̳������Ƿ��ѡ
	 * @return
	 * @throws PlatformException
	 */
	public abstract boolean getIsPubSelect() throws PlatformException;
	
	/**���øÿγ��ڿγ̳������Ƿ��ѡ
	 * @param isPubSelect
	 * @return
	 * @throws PlatformException
	 */
	public abstract void setPubSelect(boolean isPubSelect)  throws PlatformException;
	
	public abstract List getSelectedStudents() throws PlatformException;
	
	public abstract int getSelectedStudentsNum() throws PlatformException;
	
	public abstract List getUndercheckStudents() throws PlatformException;
	
	public abstract int getUndercheckStudentsNum() throws PlatformException;

	public String getCoursewareCode() {
		return coursewareCode;
	}

	public void setCoursewareCode(String coursewareCode) {
		this.coursewareCode = coursewareCode;
	}

	public String getScormCourseId() {
		return scormCourseId;
	}

	public void setScormCourseId(String scormCourseId) {
		this.scormCourseId = scormCourseId;
	}
	
		
}
